package com.ll.surl.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        {
                            authorizeRequests
                                    .requestMatchers("/gen/**")
                                    .permitAll()
                                    .requestMatchers("/resource/**")
                                    .permitAll()
                                    .requestMatchers("/h2-console/**")
                                    .permitAll();

                            if (AppConfig.isProd()) authorizeRequests
                                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                                    .hasRole("ADMIN");

                            authorizeRequests
                                    .anyRequest()
                                    .permitAll();
                        }
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin()
                                )
                )
                .csrf(
                        csrf ->
                                csrf.disable()
                )
                .formLogin(
                        formLogin ->
                                formLogin
                                        .loginPage("/member/login")
                                        .permitAll()
                )
                .logout(
                        logout ->
                                logout
                                        .logoutRequestMatcher(
                                                new AntPathRequestMatcher("/member/logout")
                                        )
                );

        return http.build();
    }
}