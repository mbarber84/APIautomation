package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class JsonReader {

    private static JSONObject jsonObject;

    static {
        try {
            File file = new File("resources/TestData/testData.json");
            String json = FileUtils.readFileToString(file, "UTF-8");

            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(json);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data", e);
        }
    }

    public static String getTestData(String key) {

        // Check root first
        if (jsonObject.containsKey(key)) {
            return (String) jsonObject.get(key);
        }

        // Check inside "auth"
        JSONObject authObject = (JSONObject) jsonObject.get("auth");

        if (authObject != null && authObject.containsKey(key)) {
            return (String) authObject.get(key);
        }

        // Fail FAST with clear error
        throw new RuntimeException("Key not found in test data: " + key);
    }
}