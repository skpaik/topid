package io.goribco.apis.service.impl;


import io.goribco.apis.dto.Product;
import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.repository.UserDataRepository;
import io.goribco.apis.utils.UniqueIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {


    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    @Autowired
    private UserDataRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProductService() {
        addProduct(new Product("First Product", "First Product Description"));
        addProduct(new Product("Second Product", "Second Product Description"));
    }

    public Collection<Product> getAllProducts() {
        return productMap.values();
    }

    public Product getProductById(long id) {
        return productMap.get(id);
    }

    public void addProduct(Product product) {
        if (productMap.values().stream().anyMatch(p -> p.getName().equals(product.getName()))) {
            throw new IllegalArgumentException(String.format("Product with name %s already exists", product.getName()));
        }

        product.setId(idCounter.incrementAndGet());
        productMap.put(product.getId(), product);
    }

    public void deleteProductById(long id) {
        if (!productMap.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Product with id %d doesn't exist", id));
        }

        productMap.remove(id);
    }

    public String addUser(UserData userData) {
        userData.setUId(UniqueIdUtils.on().getNanoId());
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        repository.save(userData);
        return "user added to system ";
    }
}
