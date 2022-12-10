package com.tinnitussounds.cms.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.bmc.objectstorage.model.PreauthenticatedRequest;
import com.tinnitussounds.cms.services.ObjectStorageService;
import com.tinnitussounds.cms.services.TokenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminAuthRepository;

    private final TokenService tokenService;
    private final ObjectStorageService objectStorageService;

    public AuthController(TokenService tokenService, ObjectStorageService objectStorageService) {
        this.tokenService = tokenService;
        this.objectStorageService = objectStorageService;
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> authenticateAdmin(Authentication auth) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        String token = tokenService.generateToken(auth);
        String preauthReq = "";
        List<Admin> admins = adminAuthRepository.findAll();
        for(Admin admin : admins) {
            if(auth.getName().equals(admin.getUser())) {
                preauthReq = admin.getStoragePreauth(); 
                break;
            }
        }
        map.put("token", token);
        map.put("preauthreq", preauthReq);
        return ResponseEntity.status(200).body(map);
    }

    @PostMapping("/api/objectstorage/preauth")
    public ResponseEntity<?> createObjectStoragePreauthReq(@RequestBody String credentials)
            throws BadCredentialsException {

        //Extract JSON to map
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<String, String>();
        try {
            map = mapper.readValue(credentials, new TypeReference<Map<String, String>>() {
            });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String userId = map.get("userId");
        Optional<User> temp = userRepository.findById(userId);

        //Check if user exists
        if (temp.isPresent()) {
            User user = temp.get();
            Optional<PreauthenticatedRequest> preauthReq = objectStorageService.createPreauthRequest("resources",
                    userId);
            if (preauthReq.isPresent()) {
                //Insert pre-authenticated request field
                user.setStoragePreauth(preauthReq.get());
                userRepository.save(user);
                return ResponseEntity.ok().body("Pre-authenticated request created");
            } else {
                return ResponseEntity.internalServerError()
                        .body("Could not create pre-authenticated request for user");
            }
        } else {
            return ResponseEntity.status(401).body("User not found");
        }
    }
}
