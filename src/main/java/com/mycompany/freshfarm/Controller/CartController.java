package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Cart;
import com.mycompany.freshfarm.Service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Carts")
public class CartController {
  @Autowired private CartService cartService;

  // --- Helper to get authenticated username ---
  private String getCurrentUsername() {
    // This retrieves the username (email) of the currently logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
      return null; // Not logged in
    }
    return authentication.getName();
  }

  @GetMapping("/Display")
  public String display(Model model) {
    String username = getCurrentUsername();
    if (username == null) {
      return "redirect:/FreshFarm/Login"; // Redirect if not logged in
    }

    // CRITICAL: Pass username to the service methods
    List<Cart> carts = cartService.getall(username);
    model.addAttribute("carts", carts);

    long countOfProducts = cartService.countofcart(username);
    long calculatedGrandTotal = cartService.grandtotal(username);

    model.addAttribute("grand_total", calculatedGrandTotal);
    model.addAttribute("productCount", countOfProducts);

    return "Cart";
  }

  @GetMapping("/Particular/{id}")
  public String cartid(@PathVariable long id) {
    String username = getCurrentUsername();
    if (username == null) {
      return "redirect:/FreshFarm/Login";
    }
    // CRITICAL: Pass username to the add method
    cartService.addProducttocart(id, username);
    return "redirect:/Carts/Display";
  }

  @PostMapping("/update")
  public String updateCart(
          @RequestParam("productId") long productId, @RequestParam("action") String action) {
    String username = getCurrentUsername();
    if (username == null) {
      return "redirect:/FreshFarm/Login";
    }
    // CRITICAL: Pass username to the update method
    cartService.updateQuantity(productId, action, username);
    return "redirect:/Carts/Display";
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("productId") long id) {
    String username = getCurrentUsername();
    if (username == null) {
      return "redirect:/FreshFarm/Login";
    }
    // CRITICAL: Pass username to the delete method
    cartService.delete(id, username);
    return "redirect:/Carts/Display";
  }
}