package entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductsWithSales {
    private Long productId;
    private String productName;
    private String category;
    private Integer quantity;
    private BigDecimal price;
}
