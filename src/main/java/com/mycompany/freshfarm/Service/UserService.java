package com.mycompany.freshfarm.Service;

import com.mycompany.freshfarm.Model.RegistrationForm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service; // ADD THIS IMPORT

@Service // ADD THIS ANNOTATION
public class UserService {
    // Spring Security's manager interface (JdbcUserDetailsManager)
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(RegistrationForm form) throws RuntimeException {
        // Validation for mismatch is typically in the Controller, but we can double-check here
        if (!form.passwordsMatch()) {
            throw new RuntimeException("Passwords do not match.");
        }

        String username = form.getEmail();

        // 1. Check if the user already exists
        if (userDetailsManager.userExists(username)) {
            throw new RuntimeException("User with email " + username + " already exists.");
        }

        // 2. Encode the password
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        // 3. Create the UserDetails object
        UserDetails newUser = User.withUsername(username)
                .password(encodedPassword)
                .roles("USER") // Assign the default role
                .disabled(false)
                .build();

        // 4. Save the user to the database
        userDetailsManager.createUser(newUser);
    }
}