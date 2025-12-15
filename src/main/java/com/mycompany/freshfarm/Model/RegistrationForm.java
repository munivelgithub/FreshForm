package com.mycompany.freshfarm.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling user registration input.
 * The fields map directly to the HTML form fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

    // Note: Spring Security will use 'email' as the username
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // FIELD ADDED TO MATCH YOUR HTML FORM
    private String confirmPassword;

    /**
     * Helper method to check if the passwords match.
     * @return true if password and confirmPassword are equal.
     */
    public boolean passwordsMatch() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }
}