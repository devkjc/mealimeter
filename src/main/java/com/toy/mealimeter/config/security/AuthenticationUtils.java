package com.toy.mealimeter.config.security;

import com.toy.mealimeter.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtils.class);

    private AuthenticationUtils() {
        // empty cause this is static class
    }

    public static User getUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }

        throw new BadCredentialsException("Bad Credentials");
    }
}
