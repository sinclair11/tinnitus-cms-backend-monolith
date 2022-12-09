package com.tinnitussounds.cms.auth;


import com.tinnitussounds.cms.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateAdmin(Authentication auth) {
        return ResponseEntity.status(200).body(tokenService.generateToken(auth));
    }

    @GetMapping("/api/admin/test")
    public String home() {
        return "Bravo ai ajuns aici. Acum poti sa o sugi.";
    }
}
