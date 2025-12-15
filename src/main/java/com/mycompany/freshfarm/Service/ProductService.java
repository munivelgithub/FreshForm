package com.mycompany.freshfarm.Service;

import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Repository.Repository; // Assuming this is your Product Repository
import com.mycompany.freshfarm.imageutil.Image_Util; // Utility for compression/decompression
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

// This service is correctly named ProductService
@Service
public class ProductService {
    @Autowired private Repository repository;
    // NOTE: If you need to include ContactRepository or other dependencies, list them here.

    /**
     * Saves the product entity along with the uploaded image data (compressed).
     */
    public String saveProductWithImage(Model product, MultipartFile file) throws IOException {

        // 1. Set Image Metadata and Compressed Data
        product.setImage_name(file.getOriginalFilename());
        product.setImage_type(file.getContentType());
        // Use the utility to compress the byte array before setting it
        product.setImage_data(Image_Util.compressImage(file.getBytes()));

        // 2. Save the full product entity
        Model savedProduct = repository.save(product);

        if (savedProduct != null) {
            return "Product and image uploaded successfully";
        }
        return null;
    }

    /**
     * Retrieves and decompresses the image data for a given product ID.
     */
    public byte[] downloadProductImage(long productId) {
        Optional<Model> product = repository.findById(productId);

        if (product.isPresent() && product.get().getImage_data() != null) {
            // Get the compressed data and decompress it
            return Image_Util.decompressImage(product.get().getImage_data());
        }
        return null;
    }

    // Placeholder for your existing product retrieval methods
    public Model getProduct(long id) {
        return repository.findById(id).orElse(null);
    }
    // ... other existing methods like getCategoryProducts, searchEverything, etc.
}