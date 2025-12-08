package com.mycompany.freshfarm.Controller;

import com.mycompany.freshfarm.Model.Model;
import com.mycompany.freshfarm.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/Category/{name}")
    public String getcategoryname(org.springframework.ui.Model model, @PathVariable String name){
        List<Model> ls=service.getCategoryProducts(name);
        model.addAttribute("products",ls);
        return "Display";
    }

    @GetMapping({"/Search/{category_name}/{keyword}"})
    public List<Model> search(@PathVariable String category_name,@PathVariable String keyword){
        return  service.searchEverything(category_name,keyword);
    }


}
