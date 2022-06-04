package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class APIConfig {
    static {
        RequestSpecification reqSpec = new RequestSpecBuilder().setUrlEncodingEnabled(true).build();
        RestAssured.requestSpecification = reqSpec;
    }

}