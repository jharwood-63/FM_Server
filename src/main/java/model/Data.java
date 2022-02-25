package model;

import java.io.IOException;

public class Data {
    private LocationData locationData;
    private NameData fNames;
    private NameData mNames;
    private NameData sNames;

    public Data() {
        Deserializer deserializer = new Deserializer();
        try {
            locationData = deserializer.deserializeLocation();
            fNames = deserializer.deserializeNames("json/fnames.json");
            mNames = deserializer.deserializeNames("json/mnames.json");
            sNames = deserializer.deserializeNames("json/snames.json");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public int getLocationDataLength() {
        return locationData.getData().length;
    }

    public NameData getfNames() {
        return fNames;
    }

    public int getFNameLength() {
        return fNames.getData().length;
    }

    public void setfNames(NameData fNames) {
        this.fNames = fNames;
    }

    public NameData getmNames() {
        return mNames;
    }

    public int getMNameLength() {
        return mNames.getData().length;
    }

    public void setmNames(NameData mNames) {
        this.mNames = mNames;
    }

    public NameData getsNames() {
        return sNames;
    }

    public int getSNameLength() {
        return sNames.getData().length;
    }

    public void setsNames(NameData sNames) {
        this.sNames = sNames;
    }
}
