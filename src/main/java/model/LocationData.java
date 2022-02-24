package model;

public class LocationData {
    static Location[] data;

    public LocationData(Location[] data) {
        LocationData.data = data;
    }

    public Location [] getData() {
        return data;
    }

    public void setData(Location[] data) {
        LocationData.data = data;
    }
}
