package model;

public class Location {
    static String country;
    static String city;
    static String latitude;
    static String longitude;

    public Location(String country, String city, String latitude, String longitude) {
        Location.country = country;
        Location.city = city;
        Location.latitude = latitude;
        Location.longitude = longitude;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        Location.latitude = latitude;
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(String longitude) {
        Location.longitude = longitude;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        Location.city = city;
    }

    public static String getCountry() {
        return country;
    }

    public static void setCountry(String country) {
        Location.country = country;
    }
}
