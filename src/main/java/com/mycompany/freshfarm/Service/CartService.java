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

    Model product = rep.findById(id).orElse(null);
    if (product == null) {
      return; // product not found
    }

    // Find existing cart item for this user + product
    Cart existingCart = repository.findByNameAndUsername(product.getName(), username);

    if (existingCart != null) {
      // Product already in cart → update quantity
      existingCart.setQuantity(existingCart.getQuantity() + 1);
      repository.save(existingCart);
      return;
    }

    // Product NOT in cart → add new item
    Cart cart = new Cart();
    cart.setUsername(username);
    cart.setBrand(product.getBrand());
    cart.setCost(product.getCost());
    cart.setCategory(product.getCategory());
    cart.setItemweight(product.getItemweight());
    cart.setDescription(product.getDescription());
    cart.setName(product.getName());
    cart.setQuantity(1);

    repository.save(cart);
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