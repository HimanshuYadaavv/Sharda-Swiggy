package org.example;

public class Location {
    String restroID;
    double latitude;
    double longitude;

    Location(String r,float lat,float lon){
        this.restroID=r;
        this.latitude=lat;
        this.longitude=lon;
    }

    public String getRestroID(){
    return restroID;
    }

    public double getLatitude() {

        return latitude;
    }

    public double getLongitude() {

        return longitude;
    }
}
