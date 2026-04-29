package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Integer id;
    private String engineType;
    private String mark;
    private String model;
    private BigDecimal price;

    public Car(String engineType, String mark, String model, BigDecimal price) {
        this.engineType = engineType;
        this.mark = mark;
        this.model = model;
        this.price = price;
    }
}
