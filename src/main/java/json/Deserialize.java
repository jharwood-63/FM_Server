package json;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Deserialize {
    //the powerpoint says to use static classes but im not sure if this is what it means
    public static class Location {
        private static float latitude;
        private static float longitude;
        private static String city;
        private static String country;


    }

    public static class LocationData {
        private static Location[] data;
    }

    public static LocationData deserialize() throws IOException {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("locations.json");
            LocationData locationData = (LocationData)gson.fromJson(reader, LocationData.class);

            return locationData;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Location file not found");
        }
    }
}
