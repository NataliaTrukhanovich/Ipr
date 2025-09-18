package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private String category;
    private Integer inStock;

    public Product(String productName, String category, int inStock) {
        this.productName = productName;
        this.category = category;
        this.inStock = inStock;
    }
}
