package steps;

import io.cucumber.java8.En;
import util.RestUtils;
import util.TestUtils;

public class BitlyRequestSteps extends BitlyBaseSteps implements En {

    public BitlyRequestSteps(TestState testState) {
        super(testState);

        When("I make a Post Request to {string}", (String api) -> {
            String request = testState.request.build();

            testState.response =
                    testState.requestSpecification
                        .body(request).
                    when()
                        .post(RestUtils.getBaseUrl() + api);
        });

        When("I make a Post Request to {string} with Raw Content {string}", (String api, String rawContent) -> {
            testState.response =
                    testState.requestSpecification
                        .body(rawContent).
                    when()
                        .post(RestUtils.getBaseUrl() + api);
        });

        When("I make a Get Request to {string}", (String api) -> {
            if(api.contains(TestUtils.MY_GROUP)) {
                api = api.replace(TestUtils.MY_GROUP, TestUtils.getValueByKey(TestUtils.MY_GROUP));
            }

            testState.response =
                    testState.requestSpecification.when().get(RestUtils.getBaseUrl() + api);
        });
    }
}
