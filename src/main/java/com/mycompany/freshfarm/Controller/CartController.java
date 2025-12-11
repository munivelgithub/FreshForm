package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Cart;
import com.mycompany.freshfarm.Service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Cart")
public class CartController {
  @Autowired private CartService cartService;

  @GetMapping("/Particular/{id}")
  public String cartid(@PathVariable long id) {
    cartService.addProducttocart(id);
    return "redirect:/Cart/Display";
  }

  @GetMapping("/Display")
  public String display(Model model) {
    List<Cart> carts = cartService.getall();
    model.addAttribute("carts", carts);
    return "Cart";
  }
}
