package com.github.maitmus.pcgspring.config;


import com.github.maitmus.pcgspring.common.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final List<String> urlWhitelist = List.of(
        "/v1/auth/**",
        "/h2-console/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/webjars/**",
        "/error",
        "/v1/parking-transaction/**",
        "/health/**",
        "/v1/public/**"
    );

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource,
                          JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> registration(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            urlWhitelist.forEach(url -> {
                web.ignoring().requestMatchers(url);
            });
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(cors ->
                cors.configurationSource(corsConfigurationSource))
            .csrf(CsrfConfigurer::disable)
            .headers(httpHeaders ->
                httpHeaders.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authorizeHttpRequests(auth -> {
                urlWhitelist.forEach(url -> auth.requestMatchers(url).permitAll());
                auth.requestMatchers("/api/v1/admin/**").hasRole("ADMIN");
                auth.anyRequest().authenticated();
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
