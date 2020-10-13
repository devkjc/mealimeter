package com.toy.mealimeter.config.security;

import com.toy.mealimeter.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomAccessTokenConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Authentication extractAuthentication(Map<String, ?> claims) {
        if (claims.containsKey("userId") && claims.containsKey("user_name")) {
            User user = new User();
            user.setId(((Integer)claims.get("userId")).longValue());
            user.setUsername((String)claims.get("username"));
            return new UsernamePasswordAuthenticationToken(user, "N/A", getAuthorities((List<?>)claims.get("authorities")));
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<?> authorities) {
        return authorities.stream().map(Object::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
