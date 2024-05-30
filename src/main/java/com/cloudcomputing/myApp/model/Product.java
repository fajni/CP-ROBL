package com.cloudcomputing.myApp.model;

import jakarta.persistence.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int number;

    @Column(nullable = true, columnDefinition = "varchar(255) default '/'")
    private String description = "/";

    @Column(nullable = true, columnDefinition = "varchar(255) default '/'")
    private String imageUrl = "/";

    @Column(nullable = true, columnDefinition = "varchar(255) default '/'")
    private String type = "none";

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "TITLE: " + title + "\n" +
                "PRICE: " + price + "\n" +
                "NUMBER: " + number + "\n" +
                "DESCRIPTION: " + description + "\n" +
                "TYPE: " + type + "\n" +
                "IMAGE URL: " + imageUrl + "\n";
    }

    public String getImageUrl(String type) {
        ClassLoader classLoader = Product.class.getClassLoader();
        HashMap<String, String> urls = new HashMap<>();

        try {

            File images = new File(classLoader.getResource("images.txt").toURI());
            Scanner reader = new Scanner(images);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();
                int index = line.indexOf(":");
                String value = line.substring(index + 1).trim();
                String key = line.substring(0, index).trim();

                urls.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!urls.containsKey(type)){
            return urls.get("Other");
        }

        return urls.get(type);
    }

    public Product() {
    }

    public Product(Long id, String title, double price, int number, String description, String type, String imageUrl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.number = number;
        this.description = description;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public Product(String title, double price, int number, String description, String type, String imageUrl) {
        this.title = title;
        this.price = price;
        this.number = number;
        this.description = description;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public Product(String title, double price, int number, String type, String imageUrl) {
        this.title = title;
        this.price = price;
        this.number = number;
        this.type = type;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {

        this.price = price;

        if (price < 0)
            this.price = 0;
    }

    public void setNumber(int number) {
        this.number = number;

        if (number < 0)
            this.number = 0;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageUrl(String type) {
        this.imageUrl = getImageUrl(type);
    }
}
