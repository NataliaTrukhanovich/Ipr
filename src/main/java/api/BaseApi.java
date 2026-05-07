package api;

import api.core.Specifications;
import io.restassured.specification.RequestSpecification;

public class BaseApi {

    Specifications spec = new Specifications();

    protected RequestSpecification requestSpec;

    public BaseApi() {
        this.requestSpec = spec.requestSpec();
    }
}