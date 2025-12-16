package com.mycompany.freshfarm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Configuration {
    @Autowired
    private DataSource dataSource;
    /**
     * Defines the security rules for HTTP requests.
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1. Disable CSRF for simpler development (reconsider for production)
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/FreshFarm/Register", "/FreshFarm/Login","/FreshFarm/Admin").permitAll()
                        .requestMatchers("/FreshFarm/Home").permitAll()

                        // Protect all other URLs (e.g., checkout, profile, etc.)
                        // User must be authenticated to access these pages.
                        .anyRequest().authenticated()
        );

        // 2. Configure Form Login
        http.formLogin(form ->
                form
                        .loginPage("/FreshFarm/Login") // Your custom login page URL
                        .loginProcessingUrl("/FreshFarm/Login") // URL where Spring Security processes the form POST
                        .defaultSuccessUrl("/FreshFarm/Home", true) // Redirect after successful login
                        .permitAll()
        );

        // 3. Configure Logout
        http.logout(logout ->
                logout
                        .logoutUrl("/FreshFarm/Logout") // URL to trigger logout
                        .logoutSuccessUrl("/FreshFarm/Home") // Redirect after successful logout
                        .permitAll()
        );

        // Optional: Enable HTTP Basic for API testing (if needed)
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif",
                        "/**/*.css", "/**/*.js", "/webjars/**");
    }


    @Bean
    public UserDetailsManager userDetailsService() {
        // JdbcUserDetailsManager uses the standard 'users' and 'authorities' tables
        // and is required for saving new users via its createUser() method.
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
