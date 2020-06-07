package steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import request.RequestBuiler;

public class TestState {

    public RequestBuiler request;
    public RequestSpecification requestSpecification;
    public Response response;
}
