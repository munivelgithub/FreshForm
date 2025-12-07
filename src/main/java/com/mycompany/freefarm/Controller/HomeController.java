package com.mycompany.freefarm.Controller;

import com.mycompany.freefarm.Model;
import com.mycompany.freefarm.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
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


}
