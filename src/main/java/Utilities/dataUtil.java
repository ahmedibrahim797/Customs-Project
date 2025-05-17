package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class dataUtil {
    //TODO:Read data from Json file

    private static final String test_data_path = "src/test/resources/TestData/";
    public static  String getJsonData(String filename , String field) throws FileNotFoundException {
        FileReader reader = new FileReader(test_data_path+filename+".json");

        JsonElement element = JsonParser.parseReader(reader);
        return element.getAsJsonObject().get(field).getAsString();
    }
    //TODO:Read data from Properties file
    public static String getPropertyValue(String filename,String key) throws IOException {
        Properties pro = new Properties();
        pro.load(new FileInputStream(test_data_path+filename+".properties"));
        return pro.getProperty(key);
    }
}
