package com.mycompany.freefarm;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private Repository repository;

    public List<Model> searchEverything(String keyword) {
        if(keyword == null || keyword.equals("null")){
            return repository.findAll();
        }
        return  repository.searchByKeyword(keyword);
    }
    public List<Model> getAll() {
        return repository.findAll();
    }


    public List<Model> getCategoryProducts(String name) {
        return repository.findByCategory(name);
    }
}
