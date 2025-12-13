package com.mycompany.freshfarm.Service;

import com.mycompany.freshfarm.Model.Cart;
import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Repository.CartRepository;
import com.mycompany.freshfarm.Repository.Repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
  @Autowired private CartRepository repository;
  @Autowired private Repository rep;

  public void addProducttocart(long id) {
    Model m = rep.findById(id).orElse(null);
    Cart cart = new Cart();
    cart.setBrand(m.getBrand());
    cart.setCost(m.getCost());
    cart.setCategory(m.getCategory());
    cart.setItemweight(m.getItemweight());
    cart.setDescription(m.getDescription());
    cart.setName(m.getName());
    cart.setQuantity(m.getQuantity());
    repository.save(cart);
  }

  public List<Cart> getall() {
    return repository.findAll();
  }

  public long countofcart() {
    return repository.count();
  }

  public void updateQuantity(long productId, String action) {

    Cart cartItem = repository.findById(productId).orElse(null);
    if (cartItem != null) {
      long currentQuantity = cartItem.getQuantity();
      if ("increase".equalsIgnoreCase(action)) {
        // 2. Increase the quantity
        cartItem.setQuantity(currentQuantity + 1);

      } else if ("decrease".equalsIgnoreCase(action)) {
        // 3. Decrease the quantity, but never go below 1 (or 0, depending on logic)
        if (currentQuantity > 1) {
          cartItem.setQuantity(currentQuantity - 1);
        } else {
          // OPTIONAL: If quantity hits 0 or less, you might want to remove the item entirely
          // cartRepository.delete(cartItem);
          cartItem.setQuantity(0); // Set to 0 if you want to keep the record
        }
      }

      // 4. Save the updated item back to the database
      repository.save(cartItem);
    }
  }

  public void delete(long id) {
    repository.deleteById(id);
  }

  // Inside com.mycompany.freshfarm.Service.CartService

  public long grandtotal() {
    List<Cart> total = getall(); // Assuming this is defined/implemented in the service
    long grandTotal = 0; // Use descriptive variable name

    for (Cart c : total) {
      // CORRECTED: Calculate (Unit Price * Quantity) for each item
      grandTotal += c.getCost() * c.getQuantity();
    }

    return grandTotal;
  }
}
