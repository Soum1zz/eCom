package com.sou.eCom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private String brand;
    private String category;
    private int stock;
    private Date releaseDate;
    private boolean available;
    private String status;

    @OneToMany( mappedBy = "product" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;
    //image
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;

    public Product(Long id){this.id=id;}
}
