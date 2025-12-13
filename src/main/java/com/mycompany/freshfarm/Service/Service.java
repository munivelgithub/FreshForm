package com.mycompany.freshfarm.Service;

import com.mycompany.freshfarm.Model.Contact;
import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Repository.ContactRepository;
import com.mycompany.freshfarm.Repository.Repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
  @Autowired private Repository repository;
  @Autowired private ContactRepository contactRepository;

  public List<Model> searchEverything(String category_name, String keyword) {
    if (category_name != null && (keyword == null || keyword.equals("null") || keyword.isBlank())) {
      return repository.findByCategory(category_name);
    }
    return repository.findByNameCategory(category_name, keyword);
  }

  public List<Model> getCategoryProducts(String name) {
    return repository.findByCategory(name);
  }

  public Model getProduct(long i) {
    return repository.findById(i).orElse(null);
  }

  public void saveContact(Contact contact) {
    contactRepository.save(contact);
  }
}
