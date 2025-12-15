package com.mycompany.freshfarm.Service;

import com.mycompany.freshfarm.Model.Cart;
import com.mycompany.freshfarm.Model.Model; // Assuming this is your Product Model
import com.mycompany.freshfarm.Repository.CartRepository;
import com.mycompany.freshfarm.Repository.Repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartService {
  @Autowired private CartRepository repository;
  @Autowired private Repository rep;

  // --- Utility to get all items for the CURRENT user ---
  public List<Cart> getall(String username) {
    return repository.findByUsername(username); // USE CUSTOM REPOSITORY METHOD
  }

  // --- Add to Cart (Requires username) ---
  public void addProducttocart(long id, String username) {
    Model m = rep.findById(id).orElse(null);
    if (m != null) {

      // Check if item already exists for this user (to increment quantity instead of creating a new row)
      // You would typically have a specific query for this, but for simplicity, we'll just check if it exists globally.
      // **BETTER LOGIC (requires another repository method): repository.findByUsernameAndProductId(username, m.getId())**

      Cart cart = new Cart();
      cart.setUsername(username); // SET USERNAME
      cart.setBrand(m.getBrand());
      cart.setCost(m.getCost());
      cart.setCategory(m.getCategory());
      cart.setItemweight(m.getItemweight());
      cart.setDescription(m.getDescription());
      cart.setName(m.getName());
      cart.setQuantity(1); // Set initial quantity to 1
      repository.save(cart);
    }
  }

  // --- Count (Requires username) ---
  public long countofcart(String username) {
    return repository.countByUsername(username); // USE CUSTOM REPOSITORY METHOD
  }

  // --- Update Quantity (CRITICAL: Must verify item belongs to user) ---
  public void updateQuantity(long productId, String action, String username) {

    // Retrieve item AND check ownership
    Optional<Cart> cartItemOptional = repository.findByIdAndUsername(productId, username);

    if (cartItemOptional.isPresent()) {
      Cart cartItem = cartItemOptional.get();
      long currentQuantity = cartItem.getQuantity();

      if ("increase".equalsIgnoreCase(action)) {
        cartItem.setQuantity(currentQuantity + 1);

      } else if ("decrease".equalsIgnoreCase(action)) {
        if (currentQuantity > 1) {
          cartItem.setQuantity(currentQuantity - 1);
        } else {
          // If quantity is 1 and we decrease, delete the item
          repository.delete(cartItem);
          return; // Exit after deleting
        }
      }

      repository.save(cartItem);
    }
  }

  // --- Delete (CRITICAL: Must verify item belongs to user) ---
  public void delete(long id, String username) {
    // Check if the item exists and belongs to the user before deleting
    repository.findByIdAndUsername(id, username).ifPresent(repository::delete);
  }

  // --- Grand Total (Requires username) ---
  public long grandtotal(String username) {
    List<Cart> total = getall(username); // Get only the current user's cart
    long grandTotal = 0;

    for (Cart c : total) {
      grandTotal += c.getCost() * c.getQuantity();
    }

    return grandTotal;
  }
}