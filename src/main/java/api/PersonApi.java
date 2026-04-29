package api;

import api.models.Person;

import static io.restassured.RestAssured.given;

public class PersonApi extends BaseApi{

    public Person createPerson(Person person){
        return given()
                .spec(requestSpec)
                .body(person)
                .when()
                .post("/user")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(Person.class);
    }

    public Person getPersonById(Integer id){
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get("/user/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Person.class);
    }

    public Person buyCar(String personId, String carId){
        return given()
                .spec(requestSpec)
                .pathParams("userId", personId, "carId", carId)
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Person.class);
    }
}
