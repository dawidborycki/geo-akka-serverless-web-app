package com.akka.weatherdemo;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Geolocation {    
    @JsonProperty("userId")
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    int userId;
    
    @JsonProperty("lat")
    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    @NotNull
    Double lat;
    
    @JsonProperty("long")
    public Double getLng() {
        return this.lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
    @NotNull
    Double lng;
}