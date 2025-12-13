package com.mycompany.freshfarm.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String brand;
  private String itemweight;
  private String category;
  private long cost;
  private String description;
  private long quantity = 0;

  // Calculated field for Thymeleaf usage
  // Note: Lombok handles getters for fields, but you define custom getters for calculated properties.
  public long getTotalCost() {
    return this.cost * this.quantity;
  }

}
