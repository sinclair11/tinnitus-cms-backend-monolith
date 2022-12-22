package com.tinnitussounds.cms.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.tinnitussounds.cms.config.Constants;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public Jwt generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(Constants.tokenDuration, ChronoUnit.MILLIS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        Jwt tokenObj = jwtEncoder.encode(JwtEncoderParameters.from(claims));

        return tokenObj;
    }

    public Jwt generateToken(String token) {
        Instant now = Instant.now();
        Jwt jwt = jwtDecoder.decode(token);
        Map<String, Object> claims = jwt.getClaims();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
        .issuer(claims.get("iss").toString())
        .issuedAt(now)
        .expiresAt(now.plus(Constants.tokenDuration, ChronoUnit.MILLIS))
        .subject(claims.get("sub").toString())
        .claim("scope", claims.get("scope"))
        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet));
    }

}
