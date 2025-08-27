package com.sc.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sc.demo.exception.CustomAccessDeniedHandler;
import com.sc.demo.exception.CustomAuthEntryPoint;
import com.sc.demo.model.User;
import com.sc.demo.repo.UserRepo;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private Jwtfilter jwtfilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,CustomAccessDeniedHandler accessDeniedHandler,
            CustomAuthEntryPoint authEntryPoint) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/api/go/login", "/api/go/refresh").permitAll() // login & refresh stay open
//            		.requestMatchers("/api/go/login").permitAll()
//            	    .requestMatchers("/api/go/home").authenticated()                 // secure home with token
            	    .requestMatchers("/admin/**").hasRole("ADMIN")
            	    .requestMatchers("/api/customers/**").hasRole("USER")
//            	    .requestMatchers("/user/**").hasAnyRole("USER")
            	    .anyRequest().authenticated()
            )
            
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(authEntryPoint)   // for unauthenticated
                    .accessDeniedHandler(accessDeniedHandler)   // for forbidden
                )
            
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner runner(UserRepo repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("rahul").isEmpty()) {
                User u = new User();
                u.setUsername("rahul");
                u.setPassword(encoder.encode("751018"));
//                u.setRoles(List.of("ROLE_ADMIN", "ROLE_USER"));
                u.setRole("ROLE_ADMIN");
                repo.save(u);
            }
        };
    }
}
