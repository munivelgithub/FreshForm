package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.RegistrationForm;
import com.mycompany.freshfarm.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/FreshFarm")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/Register")
    public String showRegistrationForm(@ModelAttribute("registrationForm") RegistrationForm registrationForm) {
        // 'registrationForm' is added to the model to support Thymeleaf binding
        return "Registerform"; // Maps to src/main/resources/templates/register.html
    }
    @PostMapping("/Register")
    public String registerUser(@ModelAttribute("registrationForm") RegistrationForm registrationForm,
                               RedirectAttributes redirectAttributes) {

        // --- ADDED VALIDATION CHECK ---
        if (!registrationForm.passwordsMatch()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password and Confirm Password must match.");
            return "redirect:/FreshFarm/Register";
        }
        // ------------------------------

        try {
            userService.registerNewUser(registrationForm);

            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/FreshFarm/Login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Preserve form data preservation logic here if needed, but redirect is simple
            return "redirect:/FreshFarm/Register";
        }
    }
    /**
     * GET /FreshFarm/Login: Displays the login form.
     */
    @GetMapping("/Login")
    public String showLoginForm() {
        return "login"; // Maps to src/main/resources/templates/login.html
    }

    // Spring Security automatically handles the POST /FreshFarm/Login request.
}
