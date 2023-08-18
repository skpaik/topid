package io.goribco.apis.controller;


import io.goribco.apis.dto.Product;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.service.impl.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STAFF_MEMBER')")
    public Collection<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ASSISTANT_MANAGER', 'MANAGER', 'ADMIN')")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public void removeProduct(@PathVariable long id) {
        productService.deleteProductById(id);
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserData userInfo) {
        return productService.addUser(userInfo);
    }
}
