package api;

import api.models.Car;

import static io.restassured.RestAssured.given;

public class CarApi extends BaseApi{

    public Car createCar(Car car){
        return given()
                .spec(requestSpec)
                .body(car)
                .when()
                .post("/car")
                .then()
                .log().all()
                //.statusCode(201)
                .extract()
                .as(Car.class);
    }

    public Car getCarById(Integer id){
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get("/car/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Car.class);
    }
}
