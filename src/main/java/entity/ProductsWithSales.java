package entity;

import lombok.Data;

@Data
public class ProductsWithSales {
    private int productId;
    private String productName;
    private String category;
    private int quantity;
    private double price;
}
