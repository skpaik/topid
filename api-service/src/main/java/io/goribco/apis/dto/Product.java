package io.goribco.apis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private long id;
    private int productId;
    private String name;
    private int qty;
    private double price;
    private String description;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
