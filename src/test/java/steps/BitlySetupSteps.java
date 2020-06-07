package steps;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import request.RequestBuiler;
import util.TestUtils;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class BitlySetupSteps extends BitlyBaseSteps implements En {

    public BitlySetupSteps(TestState testState) {
        super(testState);

        Given("I want to connect to an API", () -> {
            RestAssuredConfig config = RestAssured.config().encoderConfig(
                    encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)
            );
            testState.requestSpecification = given().config(config);
        });

        Given("I have a {string} Header with value {string}", (String header, String value) -> {
            if(value.equals(TestUtils.CORRECT_AUTHORIZATION)) {
                value = TestUtils.getValueByKey(TestUtils.CORRECT_AUTHORIZATION);
            }
            testState.requestSpecification.header(header, value);
        });

        Given("I have a {string} Query with Parameter {string}", (String query, String parameter) -> {
            if(!query.isEmpty()) {
                String[] queries = query.split(",");
                String[] parameters = parameter.split(",");

                for(int i = 0; i < queries.length; i++) {
                    testState.requestSpecification.queryParam(queries[i], parameters[i]);
                }
            }
        });

        Given("I have a Request", () -> {
            testState.request = new RequestBuiler();
        });

        Given("my Request has a {string} of {string}", (String parameter, String value) -> {
            testState.request.setParameter(parameter, value);
        });
    }
}
