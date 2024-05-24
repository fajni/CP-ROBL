package com.cloudcomputing.myApp.controller;

import com.cloudcomputing.myApp.Service.ProductService;
import com.cloudcomputing.myApp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {

    @Autowired
    private ProductService productService;

    /*  ENDPOINTS USED FOR POSTMAN - @RestController */

    @GetMapping("/json")
    public List<Product> getAllProductsJson(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public boolean deleteProductPostman(@PathVariable("id") Long id){
        return productService.deleteProduct(id);
    }

    @PostMapping("/")
    public Product createProductPostman(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping({"/{id}"})
    public Optional<Product> updateProductPostman(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

}
