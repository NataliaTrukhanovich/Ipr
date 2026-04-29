package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {
    private Integer id;
    private Integer floorCount;
    private BigDecimal price;
    private ArrayList<ParkingPlace> parkingPlaces;
    private ArrayList<Person> lodgers;

    public House(Integer floorCount, BigDecimal price, ArrayList<ParkingPlace> parkingPlaces, ArrayList<Person> lodgers) {
        this.floorCount = floorCount;
        this.price = price;
        this.parkingPlaces = parkingPlaces;
        this.lodgers = lodgers;
    }
}
