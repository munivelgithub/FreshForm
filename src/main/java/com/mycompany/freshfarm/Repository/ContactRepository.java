package com.mycompany.freshfarm.Repository;

import com.mycompany.freshfarm.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
