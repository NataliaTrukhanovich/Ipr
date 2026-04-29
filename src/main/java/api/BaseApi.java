package api;

import api.core.Specifications;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseApi {

    protected RequestSpecification requestSpec;
   // protected ResponseSpecification responseSpec200;

    public BaseApi() {
        this.requestSpec = Specifications.requestSpec();
       // this.responseSpec200 = Specifications.responseSpec200();
    }
}