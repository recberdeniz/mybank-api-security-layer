package com.mybankapp.mybank.security;

import com.mybankapp.mybank.model.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain filterChain(HttpSecurity security, HandlerMappingIntrospector interceptor)
    throws Exception{
        MvcRequestMatcher.Builder mvcRequestMatchers = new MvcRequestMatcher.Builder(interceptor);

        security.headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf -> csrf.ignoringRequestMatchers(mvcRequestMatchers.pattern("/v1/public/**"))
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //CookieCsrfTokenRepository enabled
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                .authorizeHttpRequests(x->
                        x.requestMatchers(mvcRequestMatchers.pattern("/v1/public/**")).permitAll()
                                .requestMatchers(mvcRequestMatchers.pattern("/v1/customer/**")).hasAnyRole(Role.ROLE_USER.getValue(), Role.ROLE_ADMIN.getValue())
                                .requestMatchers(mvcRequestMatchers.pattern(HttpMethod.DELETE, "/v1/customer/**")).hasAnyRole(Role.ROLE_USER.getValue(), Role.ROLE_ADMIN.getValue())
                                .requestMatchers(mvcRequestMatchers.pattern("/v1/account/**")).hasAnyRole(Role.ROLE_USER.getValue(), Role.ROLE_ADMIN.getValue())
                                .requestMatchers(mvcRequestMatchers.pattern("/v1/admin/**")).hasRole("ADMIN")
                                .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(x-> x.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        return security.build();
    }

}
