package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class JsonParser {

    public static JSONArray parseJSONArray(String file) {
        InputStream is = JsonParser.class.getClassLoader().getResourceAsStream(file);
        assert is != null;
        return new JSONArray(new JSONTokener(is));
    }
}
