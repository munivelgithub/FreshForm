package com.mycompany.freshfarm.Model;

import jakarta.persistence.*; // Important: Need these imports for @Lob and @Column
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product_Model") // Renaming the table for clarity
public class Model {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  // --- IMAGE FIELDS (BIT CODE STORAGE) ---
  private String image_name;
  private String image_type;

  @Lob // Denotes a Large Object field
  @Column(name = "product_image_data", columnDefinition = "LONGBLOB")
  private byte[] image_data;
  // ---------------------------------------

  private String name;
  private String brand;
  private String itemweight;
  private String category;
  private long cost;
  private String description;
  private long quantity = 0;
}