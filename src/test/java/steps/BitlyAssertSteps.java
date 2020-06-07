package steps;

import io.cucumber.java8.En;
import io.restassured.path.json.JsonPath;
import util.ConnectUtils;
import util.TestUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static util.TestUtils.Nodes.*;

public class BitlyAssertSteps extends BitlyBaseSteps implements En {

    public BitlyAssertSteps(TestState testState) {
        super(testState);

        Then("the Response Status Code is {int}", (Integer expectedStatus) -> {
            assertThat(testState.response.getStatusCode()).isEqualTo(expectedStatus);
        });

        Then("the Response conforms to the Schema {string}", (String schema) -> {
            testState.response.then().assertThat().body(matchesJsonSchemaInClasspath("schema/" + schema));
        });

        Then("the {string} parameter is equal to {string}", (String parameter, String expected) -> {
            if(expected.equals(TestUtils.MY_GROUP)) {
                expected = TestUtils.getValueByKey(TestUtils.MY_GROUP);
            }

            JsonPath jsonPath = new JsonPath(testState.response.asString());
            assertThat(jsonPath.get(parameter).toString()).contains(expected);
        });

        Then("the Response Content is equal to {string}", (String expectedResponse) -> {
            assertThat(testState.response.asString().split("\n")[0]).isEqualTo(expectedResponse);
        });

        Then("the ID in the Response matches the Link", () -> {
            JsonPath jsonPath = new JsonPath(testState.response.asString());
            String linkHttpsRemoved = jsonPath.get(LINK.toString()).toString().split("https://")[1];
            assertThat(jsonPath.get(ID.toString()).toString()).isEqualTo(linkHttpsRemoved);
        });

        Then("the Link correctly redirects to the expected URL", () -> {
            JsonPath jsonPath = new JsonPath(testState.response.asString());
            String actualLink = jsonPath.get(LINK.toString()).toString();
            String expectedUrl = jsonPath.get(LONG_URL.toString()).toString();

            String actualUrl = ConnectUtils.getUrlFromConnection(actualLink);
            assertThat(actualUrl).isEqualTo(expectedUrl);
        });

        Then("the Sorted Links are Sorted by Clicks", () -> {
            JsonPath jsonPath = new JsonPath(testState.response.asString());
            ArrayList<LinkedHashMap<String, Object>> sortedLinks = jsonPath.get(SORTED_LINKS.toString());

            Integer previousClicks = null;
            for(LinkedHashMap<String, Object> link : sortedLinks) {
                if(previousClicks==null) {
                    previousClicks = (Integer)link.get(CLICKS.toString());
                } else {
                    assertThat((Integer)link.get(CLICKS.toString())).isLessThan(previousClicks);
                }
            }
        });

        Then("the number of Links is equal to number of Sorted Links", () -> {
            JsonPath jsonPath = new JsonPath(testState.response.asString());
            ArrayList<LinkedHashMap<String, Object>> linksList = jsonPath.get(LINKS.toString());
            ArrayList<LinkedHashMap<String, Object>> sortedLinksList = jsonPath.get(SORTED_LINKS.toString());

            assertThat(linksList.size()).isEqualTo(sortedLinksList.size());
        });
    }
}
