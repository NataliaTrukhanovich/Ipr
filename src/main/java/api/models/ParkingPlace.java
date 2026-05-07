package api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingPlace {
    private Integer id;
    private Boolean isWarm;
    private Boolean isCovered;
    private Integer placesCount;

    public ParkingPlace(Boolean isWarm, Boolean isCovered, Integer placesCount) {
        this.isWarm = isWarm;
        this.isCovered = isCovered;
        this.placesCount = placesCount;
    }
}
