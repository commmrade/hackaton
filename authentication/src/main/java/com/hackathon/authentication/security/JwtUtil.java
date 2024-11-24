package com.hackathon.authentication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hackathon.authentication.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    private final String secretKey = "vladivostok2000";


    public String genToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 36_000_000))
                .sign(algorithm);
    }

    public boolean validToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid Token: " + e.getMessage());
            return false;
        }
    }
    public String extractUsername(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            System.out.println("Error extracting username: " + e.getMessage());
            return null;
        }
    }
}