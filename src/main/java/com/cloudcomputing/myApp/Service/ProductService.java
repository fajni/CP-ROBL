package com.cloudcomputing.myApp.Service;

import com.cloudcomputing.myApp.model.Product;
import com.cloudcomputing.myApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).get();
    }

    public Product createProduct(Product product) {

        for(Product p: productRepository.findAll()){
            if(Objects.equals(product.getId(), p.getId())){
                throw new IllegalStateException("Product id \"" + product.getId() + "\" already exists!");
            }
        }

        /*if(product.getTitle().isEmpty() || product.getPrice() < 0 || product.getNumber() < 0){
            throw new IllegalStateException("Empty fields not allowed!");
        }*/

        product.setImageUrl(product.getType());
        return productRepository.save(product);
    }

    public boolean deleteProduct(Long id){
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, Product product){

        Optional<Product> optionalProduct = productRepository.findById(id);

        optionalProduct.get().setTitle(product.getTitle());
        optionalProduct.get().setImageUrl(product.getType());
        optionalProduct.get().setPrice(product.getPrice());
        optionalProduct.get().setNumber(product.getNumber());
        optionalProduct.get().setDescription(product.getDescription());

        return optionalProduct;
    }

    @Transactional
    public Optional<Product> updateProduct2(Long id, String title, double price, int number, String description, String type){

        Optional<Product> optionalProduct = productRepository.findById(id);

        optionalProduct.get().setTitle(title);
        optionalProduct.get().setPrice(price);
        optionalProduct.get().setNumber(number);
        optionalProduct.get().setDescription(description);
        optionalProduct.get().setType(type);
        optionalProduct.get().setImageUrl(optionalProduct.get().getType());

        return optionalProduct;
    }
}
