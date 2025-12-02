package com.sou.eCom.controller;

import com.sou.eCom.model.Product;
import com.sou.eCom.repo.ProductRepo;
import com.sou.eCom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:5173")
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> products()
    {
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> product(@PathVariable long id){
        Product product= productService.getProduct(id);

        if(product.getId()!=-1){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable long id){
        Product product= productService.getProduct(id);
        if(product.getId()!=-1&&product.getImageData()!=null){
            return ResponseEntity.ok()
                    .header("Content-Type", product.getImageType())
                    .body(product.getImageData());
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        return new ResponseEntity<>(productService.search(keyword),HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestPart Product product, @RequestPart(required = false) MultipartFile image){
        try{
            Product product1=productService.addOrUpdateProduct(product, image);
            return new ResponseEntity<>(product1,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestPart Product product, @RequestPart(required = false) MultipartFile image){
        try{
            Product product1=productService.addOrUpdateProduct(product, image);
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    
}
