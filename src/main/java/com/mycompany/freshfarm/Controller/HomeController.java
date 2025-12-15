package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Contact;
import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Service.ProductService;
import com.mycompany.freshfarm.Service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/FreshFarm")
public class HomeController {
  // CRITICAL: Rename field to use ProductService
  @Autowired private ProductService productService;
  @Autowired private Service service;
// --- NEW PUBLIC IMAGE ENDPOINT ---
  /**
   * GET /FreshFarm/image/{productId}: Endpoint to serve the decompressed image byte data.
   * This is used by <img th:src="..."> in templates like Display.html and ProductDetails.html.
   */
  @GetMapping("/image/{productId}")
  public ResponseEntity<byte[]> downloadProductImage(@PathVariable long productId) {
    // Use the ProductService to get the decompressed bytes
    byte[] imageBytes = productService.downloadProductImage(productId);

    if (imageBytes != null) {
      // You should dynamically determine the content type from the database
      // (product.getImage_type()) if possible, but default is acceptable for now.
      return ResponseEntity.status(HttpStatus.OK)
              .contentType(MediaType.IMAGE_PNG) // Set correct content type
              .body(imageBytes);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @GetMapping("/Home")
  public String freeFarm() {
    return "Home";
  }

  @GetMapping("/Category")
  public String category() {
    return "Category";
  }

  @GetMapping("/Category/{name}")
  public String getcategoryname(org.springframework.ui.Model model, @PathVariable String name) {
    List<Model> ls = service.getCategoryProducts(name);
    model.addAttribute("products", ls);
    model.addAttribute("category", name);
    return "Display";
  }

  @GetMapping("/Search/{category_name}")
  public String search(
      @PathVariable String category_name,
      @RequestParam String keyword,
      org.springframework.ui.Model model) {

    List<Model> searched_data = service.searchEverything(category_name, keyword);

    model.addAttribute("products", searched_data); // show results
    model.addAttribute("category", category_name); // keep category for next search
    return "Display"; // reload same page
  }

  @GetMapping("/ProductDetails/{id}")
  public String one(org.springframework.ui.Model model, @PathVariable long id) {
    Model m = service.getProduct(id);
    String category = m.getCategory();
    model.addAttribute("single_product_details", m);
    model.addAttribute("category", category);
    return "ProductDetails";

  }

  @GetMapping("/Contact")
  public String contact(org.springframework.ui.Model model) {
    Contact contact = new Contact();
    model.addAttribute("contact", contact);
    return "Contact";
  }

  @PostMapping("/ContactSave")
  public String save(
      @ModelAttribute("contact") Contact contact, RedirectAttributes redirectAttributes) {
    service.saveContact(contact);
    redirectAttributes.addFlashAttribute(
        "successMessage", "Your message has been sent successfully!");
    return "redirect:/FreshFarm/Contact";
  }

  @GetMapping("/AboutUs")
  public String about() {
    return "About";
  }

  @GetMapping("/Service")
  public String service() {
    return "Service";
  }
}
