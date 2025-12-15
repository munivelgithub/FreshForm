package com.mycompany.freshfarm.Repository;

import com.mycompany.freshfarm.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  // 1. CRITICAL: Method to fetch ALL cart items belonging ONLY to this username (for display)
  // Query: SELECT * FROM cart WHERE username = ?
  List<Cart> findByUsername(String username);

  // 2. CRITICAL: Method to count items belonging ONLY to this username (for product count)
  // Query: SELECT COUNT(*) FROM cart WHERE username = ?
  long countByUsername(String username);

  // 3. IMPORTANT: Method to find a specific item by its ID AND verify ownership by username
  // This is used for updates and deletions to prevent User A from modifying User B's cart.
  // Query: SELECT * FROM cart WHERE id = ? AND username = ?
  Optional<Cart> findByIdAndUsername(long id, String username);

  // Note: The original 'long count()' method is implicitly covered by JpaRepository's count(),
  // but the findByUsername methods are essential for user isolation.
}