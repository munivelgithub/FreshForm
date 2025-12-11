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
}
