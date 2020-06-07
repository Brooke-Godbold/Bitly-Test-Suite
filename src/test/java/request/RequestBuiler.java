package request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class RequestBuiler {

    private final Map<String, String> jsonMap;

    public RequestBuiler() {
        jsonMap = new HashMap<String, String>();
    }

    public void setParameter(String parameter, String value) {
        jsonMap.put(parameter, value);
    }

    public String build() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(jsonMap);
    }
}
