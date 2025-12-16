package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Service.ProductService; // Inject the dedicated ProductService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/FreshFarm/Admin")
public class AdminController {

    @Autowired private ProductService productService; // Use the dedicated service

    /**
     * GET /FreshFarm/Admin/addProduct: Displays the product addition form.
     */

    @GetMapping("/addProduct")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddProductForm(org.springframework.ui.Model model) {
        // Send a new, empty product object for Thymeleaf binding (AddProduct.html)
        model.addAttribute("product", new Model());
        return "AddProduct";
    }

    /**
     * POST /FreshFarm/Admin/saveProduct: Handles the form submission for product and image.
     */
    @PostMapping("/saveProduct")
    public String saveProduct(
            @ModelAttribute("product") Model product,
            @RequestParam("productImage") MultipartFile file, // Captures the image file
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product image is required.");
            return "redirect:/FreshFarm/Admin/addProduct";
        }

        try {
            // Call the service method that handles compression and saving
            productService.saveProductWithImage(product, file);
            redirectAttributes.addFlashAttribute("successMessage", "Product saved successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to process image: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save product: " + e.getMessage());
        }

        return "redirect:/FreshFarm/Admin/addProduct";
    }


}