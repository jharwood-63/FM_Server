package model;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Deserialize {
    //change this so that you only need one deserialize function
    public LocationData deserializeLocation() throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("json/locations.json")){
            LocationData locationData = (LocationData) gson.fromJson(reader, LocationData.class);
            return locationData;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Location file not found");
        }
    }
}
