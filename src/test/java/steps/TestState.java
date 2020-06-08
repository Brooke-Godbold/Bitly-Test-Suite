package steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import request.RequestBuilder;

public class TestState {

    public RequestBuilder request;
    public RequestSpecification requestSpecification;
    public Response response;
}
