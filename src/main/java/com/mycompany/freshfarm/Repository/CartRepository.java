package com.mycompany.freshfarm.Repository;

import com.mycompany.freshfarm.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  long count();
}
