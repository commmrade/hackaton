package com.hackathon.authentication.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;


    public JwtAuthFilter(JwtUtil jwt) {
        jwtUtil = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

            Optional<Cookie> cookie = Arrays.stream(cookies).filter(auth -> auth.getName().equals("token")).findAny();
            String token = cookie.map(Cookie::getValue).orElse(null);

            if (token != null && jwtUtil.validToken(token)) {
                String name = jwtUtil.extractUsername(token);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, null, null);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }


        filterChain.doFilter(request, response);
    }


}
