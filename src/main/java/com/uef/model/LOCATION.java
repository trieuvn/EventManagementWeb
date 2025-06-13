package com.uef.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "[LOCATION]")
public class LOCATION {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "location")
    private List<TICKET> tickets;
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }
    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}