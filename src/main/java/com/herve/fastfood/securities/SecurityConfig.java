package com.herve.fastfood.securities;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(
                        "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/menus").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/menus/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/menus").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/menus/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/menus/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/menus/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/factures").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/commandes").hasAuthority("CLIENT")
                        .requestMatchers(HttpMethod.PATCH, "/api/commandes/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/factures/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling-> handling.accessDeniedHandler(accessDeniedHandler()));
        return httpSecurity.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        System.out.println("CORS Configuration applied for origins: " + configuration.getAllowedOrigins());

        return source;
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé : " + accessDeniedException.getMessage());
        };
    }

}
