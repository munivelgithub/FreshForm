package com.mycompany.freefarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Repository extends JpaRepository<Model,Long> {

    List<Model> findByCategory(String category);

    @Query("select m from Model m where m.brand= :keyword or m.name=:keyword or m.category= : keyword")
    List<Model> searchByKeyword(@Param("keyword") String keyword);


}
