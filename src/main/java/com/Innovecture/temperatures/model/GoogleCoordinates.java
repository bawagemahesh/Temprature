package com.Innovecture.temperatures.model;

public class GoogleCoordinates {
    private String latitute;
    private String longitute;

    @Override
    public String toString() {
        return "GoogleCoordinates{" +
                "latitute='" + latitute + '\'' +
                ", longitute='" + longitute + '\'' +
                '}';
    }
    public String getLatitute() {
        return latitute;
    }
    public void setLatitute(String latitute) {
        this.latitute = latitute;
    }

    public String getLongitute() {
        return longitute;
    }
    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }
}
