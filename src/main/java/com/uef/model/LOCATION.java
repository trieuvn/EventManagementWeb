package com.uef.model;

import com.uef.utils.Map;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.ui.Model;

@Entity
@Table(name = "[LOCATION]")
public class LOCATION {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "latitude", nullable = false)
    @NotNull(message = "Latitude cannot be null")
    private float latitude;

    @Column(name = "longitude", nullable = false)
    @NotNull(message = "Longitude cannot be null")
    private float longitude;

    @Column(name = "name", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    @NotNull(message = "Name cannot be null")
    @Size(max = 50, message = "Name must not exceed 50 characters")
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

    public LOCATION(int id, float latitude, float longitude, String name, List<TICKET> tickets) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.tickets = tickets;
    }

    public LOCATION() {
    }
    
    public String getRoadMap(Model model, Double latitude, Double longitude) throws Exception{
        return Map.showMap(model, latitude, longitude, Double.valueOf(this.latitude), Double.valueOf(this.longitude), null, name);
    }
}