package com.product.Service;

import com.product.Entity.Product;
import com.product.Repository.ProductRepo;
import com.product.dto.ProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @KafkaListener(topics = "product-event-topic",groupId = "1")
    public void consumer(ProductEvent productEvent){

        if(productEvent.getEventType().equalsIgnoreCase("create")){
            productRepo.save(productEvent.getProduct());
        } else if(productEvent.getEventType().equalsIgnoreCase("update")){
            Product existingProduct = productRepo.findById(productEvent.getProduct().getProductId()).get();
            existingProduct.setDescription(productEvent.getProduct().getDescription());
            existingProduct.setPrice(productEvent.getProduct().getPrice());
            existingProduct.setQuantity(productEvent.getProduct().getQuantity());
            productRepo.save(existingProduct);
        }

    }
}
