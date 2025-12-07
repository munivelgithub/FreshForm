package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FreshFarm")
public class HomeController {

  @Autowired
  private Service service;
    @GetMapping("/Home")
    public String freeFarm(){
        return "Home";
    }
    @GetMapping("/Category")
    public String category(){
        return "Category";
    }
    @GetMapping("/Cart")
    public String cart(){
        return "Category";
    }

    @GetMapping({"/Category/{name}","Fruit/{name}"})
    public List<Model> getcategoryname(@PathVariable String name){
        List<Model> ls=service.getCategoryProducts(name);
        return ls;
    }

    @GetMapping({"/Search/{category_name}/{keyword}"})
    public List<Model> search(@PathVariable String category_name,@PathVariable String keyword){
        return  service.searchEverything(category_name,keyword);
    }


}
