package com.sou.eCom.service;

import com.sou.eCom.model.Product;
import com.sou.eCom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product getProduct(long productId) {
        return productRepo.findByProductId(productId).orElse(new Product((long) -1));
    }

    public Product addOrUpdateProduct(Product product, MultipartFile img)throws IOException {
        if(img != null && !img.isEmpty()) {
            product.setImageName(img.getOriginalFilename());
            product.setImageType(img.getContentType());
            product.setImageData(img.getBytes());
        }else if(product.getId() != -1){
            Product old = productRepo.findByProductId(product.getId()).orElse(null);
            if(old != null){
                product.setImageName(old.getImageName());
                product.setImageType(old.getImageType());
                product.setImageData(old.getImageData());
            }
        }
        return  productRepo.save(product);
    }

    public List<Product> findAll() {return productRepo.findAll();}

    public List<Product> search(String keyword) {return productRepo.search(keyword);}

    public void delete(long productId) {
        productRepo.deleteById(productId);
    }
}