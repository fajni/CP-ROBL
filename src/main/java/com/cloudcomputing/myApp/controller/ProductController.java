package com.cloudcomputing.myApp.controller;

import com.cloudcomputing.myApp.Service.ProductService;
import com.cloudcomputing.myApp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/products"})
    public ModelAndView getAllProducts(){

        ModelAndView model = new ModelAndView("products");
        model.addObject("products", productService.getPage(1));
        model.addObject("product", new Product());
        model.addObject("updateProduct", new Product());
        model.addObject("currentPage", 1);
        model.addObject("totalPages", productService.getPage(1).getTotalPages());
        model.addObject("totalItems", productService.getPage(1).getTotalElements());

        return model;
    }

    @GetMapping({"/{field}", "/products/{field}"})
    public ModelAndView getAllSortedProducts(@PathVariable(required = false, name = "field") String field){
        ModelAndView model = new ModelAndView("products");
        model.addObject("product", new Product());
        model.addObject("updateProduct", new Product());

        if(field != null){
            model.addObject("products", productService.getProductsWithSorting(field));
        }

        return model;
    }

    // TODO: sorting
    @GetMapping("/page/{pageNumber}/{field}")
    public ModelAndView getPageSort(@PathVariable("pageNumber") int currentPage, @PathVariable("field") String field){
        ModelAndView model = new ModelAndView("products");
        model.addObject("product", new Product());
        model.addObject("updateProduct", new Product());

        Page<Product> page = productService.getPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Product> products = productService.getProductsWithSortingAndPagination(field, currentPage, 5);

        model.addObject("products", products);
        model.addObject("currentPage", currentPage);
        model.addObject("totalPages", totalPages);
        model.addObject("totalItems", totalItems);

        return model;
    }

    @GetMapping({"/page/{pageNumber}"})
    public ModelAndView getPage(@PathVariable("pageNumber") int currentPage){
        ModelAndView model = new ModelAndView("products");
        model.addObject("product", new Product());
        model.addObject("updateProduct", new Product());

        Page<Product> page = productService.getPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Product> products = page.getContent();

        model.addObject("products", products);
        model.addObject("currentPage", currentPage);
        model.addObject("totalPages", totalPages);
        model.addObject("totalItems", totalItems);

        return model;
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ModelAndView getAllProductsPagination(@PathVariable int offset, @PathVariable int pageSize){
        ModelAndView model = new ModelAndView("products");
        model.addObject("products", productService.getProductsWithPagination(offset, pageSize));
        model.addObject("product", new Product());
        model.addObject("updateProduct", new Product());
        return model;
    }

    @PostMapping({"/create"})
    public String createProduct(@ModelAttribute Product product, RedirectAttributes r){
        productService.createProduct(product);
        r.addFlashAttribute("add","Product "+product.getTitle()+" was added!");
        return "redirect:/";
    }

    //@DELETE, since html can't send delete request for DELETES
    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable("id") Long id, RedirectAttributes r){
        r.addFlashAttribute("delete","Product " + productService.getProductById(id).getTitle() + " was added!");
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
            @RequestParam(value = "type") String type,
            RedirectAttributes r
    ){

        System.out.println(productService.getProductById(id));
        r.addFlashAttribute("update","Product "+ title + " was updated!");
        productService.updateProduct2(id, title, price, number, description, type);

        return "redirect:/";
    }
}
