package util;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {

    public enum Nodes {
        LINK("link"),
        ID("id"),
        LONG_URL("long_url"),
        LINKS("links"),
        SORTED_LINKS("sorted_links"),
        CLICKS("clicks");

        public final String node;

        Nodes(String node) {
            this.node = node;
        }

        @Override
        public String toString() {
            return node;
        }
    }

    public static final String CORRECT_AUTHORIZATION = "CORRECT_AUTHORIZATION";
    public static final String MY_GROUP = "MY_GROUP";

    private static final String authToken = System.getProperty("AUTH_TOKEN");
    private static final String groupId = System.getProperty("GROUP_ID");

    private static final Map<String, String> testValues = new HashMap<String, String>() {{
        put(CORRECT_AUTHORIZATION, authToken);
        put(MY_GROUP, groupId);
    }};

    public static String getValueByKey(String key) {
        return testValues.get(key);
    }
}
