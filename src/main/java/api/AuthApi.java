package api;

import api.models.Auth;

import static io.restassured.RestAssured.given;
import static services.ConfigProvider.config;

public class AuthApi extends BaseApi{



    public static String getToken(String username, String password) {

        return
                given()
                        .baseUri(config.getString("base_uri"))
                        .contentType("application/json")
                        .body(new Auth(username, password))
                        .log().all()
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(202)
                        .extract()
                        .jsonPath().getString("access_token");
    }
}
