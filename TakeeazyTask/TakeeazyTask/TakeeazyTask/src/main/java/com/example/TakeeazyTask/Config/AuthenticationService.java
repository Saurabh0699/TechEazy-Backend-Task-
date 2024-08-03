package com.example.TakeeazyTask.Config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationService {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String EXPECTED_TOKEN = "9246e2ee-a026-424f-94f7-e751351ec9a9"; // Your generated security password

    public static Authentication getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            throw new BadCredentialsException("Missing or invalid bearer token");
        }

        String token = authorizationHeader.substring(TOKEN_PREFIX.length());
        // Check if the provided token matches the expected token
        if (!token.equals(EXPECTED_TOKEN)) {
            throw new BadCredentialsException("Invalid bearer token");
        }

        return new ApiKeyAuthentication(token, AuthorityUtils.NO_AUTHORITIES);
    }
}