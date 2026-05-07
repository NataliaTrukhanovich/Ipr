package api.core;

import api.AuthApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static services.ConfigProvider.config;

public class Specifications {

    String token = AuthApi.getToken(config.getString("api.api_username"), config.getString("api.api_password"));

    public RequestSpecification requestSpec() {

        System.out.println("API CONFIG: " + config.getConfig("api"));
        System.out.println("BASE URI: " + config.getString("api.base_uri"));

        return new RequestSpecBuilder()
                .setBaseUri(config.getString("api.base_uri"))
                .setContentType("application/json")
                .setAccept("application/json")
                .addHeader("Authorization", "Bearer " + token)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }
}
