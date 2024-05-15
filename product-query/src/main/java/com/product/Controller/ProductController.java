package com.product.Controller;

import com.product.Entity.Product;
import com.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/get-all")
    public List<Product> getAll(){
        return productService.getAll();
    }
}
