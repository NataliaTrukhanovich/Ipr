package api;

import api.core.Specifications;
import io.restassured.specification.RequestSpecification;

public class BaseApi {

    protected Specifications spec = new Specifications();

    protected RequestSpecification requestSpec;

    public BaseApi() {
        this.requestSpec = spec.requestSpec();
    }
}