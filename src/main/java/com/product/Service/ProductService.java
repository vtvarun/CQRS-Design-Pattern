package com.product.Service;

import com.product.Entity.Product;
import com.product.Repository.ProductRepo;
import com.product.dto.ProductEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;


    public void addProducts(ProductEvent productEvent) {
        productRepo.save(productEvent.getProduct());

        //i want to publish this event to the kafka broker so that it can be listened to
        // by the kafka consumer or productquery database
        kafkaTemplate.send("product-event-topic",productEvent);

    }

    public Product updateProduct(int id, ProductEvent productEvent){
        Product existingProduct = productRepo.findById(id).get();
        existingProduct.setDescription(productEvent.getProduct().getDescription());
        existingProduct.setPrice(productEvent.getProduct().getPrice());
        existingProduct.setQuantity(productEvent.getProduct().getQuantity());

        productRepo.save(existingProduct);

        kafkaTemplate.send("product-event-topic",productEvent);

        return existingProduct;

    }
}
