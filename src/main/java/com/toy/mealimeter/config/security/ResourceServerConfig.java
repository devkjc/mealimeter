package com.toy.mealimeter.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final String secretKey = "KzUWksdB5Eu66LNLhub7B2s4";
    private final String resourceId = "56019965047-0t3qrkpa4420if93b557lp6ub32ah2e4.apps.googleusercontent.com";

    private final CustomAccessTokenConverter customAccessTokenConverter;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public ResourceServerConfig(CustomAccessTokenConverter customAccessTokenConverter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessTokenConverter = customAccessTokenConverter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter defaultConverter = new DefaultAccessTokenConverter();
        defaultConverter.setUserTokenConverter(customAccessTokenConverter);
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(defaultConverter);
        converter.setSigningKey(secretKey);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers("/swagger-ui.html","/swagger-resources/**","/v2/api-docs", "/configuration/**", "/webjars/**").permitAll()
            .antMatchers("/oauth/**","/csrf").permitAll()
            .antMatchers("/").permitAll()
//                .antMatchers("/hello").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) throws Exception {
        config.resourceId(resourceId).stateless(false).tokenServices(tokenService());
    }
}
