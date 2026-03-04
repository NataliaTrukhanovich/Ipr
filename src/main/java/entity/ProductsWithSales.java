package entity;

import lombok.Data;

@Data
public class ProductsWithSales {
    private Long productId;
    private String productName;
    private String category;
    private Integer quantity;
    private Double price;
}
