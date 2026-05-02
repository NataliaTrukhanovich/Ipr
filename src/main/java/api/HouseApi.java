package api;

import api.models.House;

import static io.restassured.RestAssured.given;

public class HouseApi extends BaseApi{

    public House createHouse(House house){
        return given()
                .spec(requestSpec)
                .log().all()
                .body(house)
                .when()
                .post("/house")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(House.class);
    }

    public House getHouse(Integer id){
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get("/house/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(House.class);
    }

    public House settlePerson(Integer houseId, Integer userId){
        return given()
                .spec(requestSpec)
                .pathParams("houseId", houseId, "userId", userId)
                .log().all()
                .when()
                .post("/house/{houseId}/settle/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(House.class);
    }
}
