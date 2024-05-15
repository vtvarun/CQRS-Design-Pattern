package com.product.Controller;

import com.product.Entity.Product;
import com.product.Service.ProductService;
import com.product.dto.ProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/command")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-products")
    public String addProducts(@RequestBody ProductEvent productEvent){
        productService.addProducts(productEvent);
        return "Product has been added";
    }

    @PutMapping("/update-products/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody ProductEvent productEvent){
        return productService.updateProduct(id,productEvent);
    }
}
