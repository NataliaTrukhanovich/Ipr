package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {
    private Integer id;
    private Integer floorCount;
    private BigDecimal price;
    private List<ParkingPlace> parkingPlaces;
    private List<Person> lodgers;

    public House(Integer floorCount, BigDecimal price, List<ParkingPlace> parkingPlaces, List<Person> lodgers) {
        this.floorCount = floorCount;
        this.price = price;
        this.parkingPlaces = parkingPlaces;
        this.lodgers = lodgers;
    }
}
