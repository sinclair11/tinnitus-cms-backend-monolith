package com.tinnitussounds.cms.auth;

import com.tinnitussounds.cms.config.Constants;
import com.tinnitussounds.cms.services.TokenService;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminAuthRepository;

    private Jwt jwtToken;

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateAdmin(Authentication auth) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        jwtToken = tokenService.generateToken(auth);
        Token token = new Token(jwtToken.getTokenValue(), Constants.tokenDuration);
        map.put("token", token);
        return ResponseEntity.status(200).body(map);
    }

    @GetMapping("/api/auth/refresh")
    public ResponseEntity<Token> refreshToken(@RequestHeader("Authorization") String token) {
        Jwt newTokenJwt = tokenService.generateToken(token);
        Token newToken = new Token(newTokenJwt.getTokenValue(), Constants.tokenDuration);
        
        return ResponseEntity.ok().body(newToken);
    }
}
