package api.core;

import api.AuthApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static services.ConfigProvider.config;
public class Specifications {

   String token = AuthApi.getToken(config.getString("api_username"), config.getString("api_password"));

    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(config.getString("base_uri"))
                .setContentType("application/json")
                .setAccept("application/json")
                .addHeader("Authorization", "Bearer " + token)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

//    public static ResponseSpecification responseSpec200() {
//        return new ResponseSpecBuilder()
//                .expectStatusCode(200)
//                .log(io.restassured.filter.log.LogDetail.ALL)
//                .build();
//    }
}
