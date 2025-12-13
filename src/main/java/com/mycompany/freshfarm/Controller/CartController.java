package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Cart;
import com.mycompany.freshfarm.Service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Carts")
public class CartController {
  @Autowired private CartService cartService;

  @GetMapping("/Display")
  public String display(Model model) {
    List<Cart> carts = cartService.getall();
    model.addAttribute("carts", carts);
    // 2. Fetch the total count of distinct products (total rows in the 'cart' table)
    long countOfProducts = cartService.countofcart();
    long calculatedGrandTotal = cartService.grandtotal();
    model.addAttribute("grand_total", calculatedGrandTotal);
    // 3. Add the count to the model under a specific name (e.g., "productCount")
    model.addAttribute("productCount", countOfProducts);
    return "Cart";
  }

  @GetMapping("/Particular/{id}")
  public String cartid(@PathVariable long id) {
    cartService.addProducttocart(id);
    return "redirect:/Carts/Display";
  }

  @PostMapping("/update")
  public String updateCart(
      @RequestParam("productId") long productId, @RequestParam("action") String action) {

    // Delegate the actual quantity update logic to the service layer
    cartService.updateQuantity(productId, action); // Logic remains the same

    // Redirect back to the cart display page to show the updated cart
    return "redirect:/Carts/Display";
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("productId") long id) {
    cartService.delete(id);
    return "redirect:/Carts/Display";
  }
}
