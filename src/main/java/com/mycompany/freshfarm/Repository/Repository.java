package com.mycompany.freshfarm.Repository;

import com.mycompany.freshfarm.Model.Model;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Model, Long> {

  List<Model> findByCategory(String category);

  // Used to retrieve a product (and its image data) by name
  Optional<Model> findByName(String name);

  @Query("select m from Model m where m.category=:c_name and m.name=:keyword")
  List<Model> findByNameCategory(
      @Param("c_name") String category_name, @Param("keyword") String keyword);
}
