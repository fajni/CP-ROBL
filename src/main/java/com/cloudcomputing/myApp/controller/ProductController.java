package com.cloudcomputing.myApp.controller;

import com.cloudcomputing.myApp.Service.ProductService;
import com.cloudcomputing.myApp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping({"/products", "/"})
    public ModelAndView getAllProducts(){
        ModelAndView model = new ModelAndView("products");
        model.addObject("products", productService.getAllProducts());
        model.addObject("product", new Product());
        model.addObject("updateProduct", new Product());
        return model;
    }

    @PostMapping({"/create"})
    public String createProduct(@ModelAttribute Product product){
        productService.createProduct(product);
        return "redirect:/";
    }

    //@DELETE, since html can't send delete request for DELETES
    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }

    //@PUT (terrible structure of request)
    @GetMapping("/update")
    public String updateProductById(
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("price") double price,
            @RequestParam("number") int number,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "type") String type
    ){

        System.out.println(productService.getProductById(id));
        productService.updateProduct2(id, title, price, number, description, type);

        return "redirect:/";
    }
}
