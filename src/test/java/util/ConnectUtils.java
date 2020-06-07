package util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectUtils {

    public static String getUrlFromConnection(String url) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setInstanceFollowRedirects(false);
            con.connect();
            String actualUrl = con.getHeaderField("Location");
            con.disconnect();
            return actualUrl;
        } catch (IOException exception) {
            return null;
        }
    }
}
