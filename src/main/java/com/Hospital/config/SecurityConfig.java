package com.Hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/doctors/**").hasRole("DOCTOR")
                .requestMatchers("/recption/**").hasAnyRole("DOCTOR", "RECPTION")
                .requestMatchers("/", "/about", "/ouserService","/ouserDoctors","/contacts","/appointment","/bookAppointment","/CSS/**", "/js/**","/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                //.loginPage("/login")
                //.defaultSuccessUrl("/doctors/dashboard", true)
                .defaultSuccessUrl("/recption/recptionDashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
            
        // Apply your custom configurer using apply() method
        http.apply(new CustomSecurityConfigurer());
        
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
