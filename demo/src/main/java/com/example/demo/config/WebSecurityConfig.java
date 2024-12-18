package com.example.demo.config;

import com.example.demo.Services.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;
    private final FilterUserUpdated filterUserUpdated;
    private final SessionRegistry sessionRegistry;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public WebSecurityConfig(UserService userService,
                                 FilterUserUpdated filterUserUpdated,
                                 SessionRegistry sessionRegistry,
                                 AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userService = userService;
        this.filterUserUpdated = filterUserUpdated;
        this.sessionRegistry = sessionRegistry;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register/**", "/login/**", "/webjars/**", "/api/notifications/**").permitAll()
                        .requestMatchers("/user/**", "/notification/**", "/profile/**").hasRole("USER")
                        .requestMatchers("/admin/**", "/dashboard/**").hasRole("ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/ws/**", "/").permitAll()
                        .anyRequest().authenticated()

                )

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login")
                        .sessionRegistry(sessionRegistry)
                )
                .addFilterAfter(filterUserUpdated, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(authenticationSuccessHandler)
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}