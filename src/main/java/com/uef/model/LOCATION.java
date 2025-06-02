/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author sang
 */

public class LOCATION {
    private int id;
    private float latitude;
    private float longitude;
    private String name;
    private String placeAt;

    // Constructors
    public LOCATION() {}
    public LOCATION(int id, float latitude, float longitude, String name, String placeAt) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.placeAt = placeAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }
    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPlaceAt() { return placeAt; }
    public void setPlaceAt(String placeAt) { this.placeAt = placeAt; }
}