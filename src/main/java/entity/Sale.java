package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Sale {
    private Long saleId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;

    public Sale(Integer productId, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
