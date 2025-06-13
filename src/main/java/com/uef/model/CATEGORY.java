package com.uef.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "[CATEGORY]")
public class CATEGORY {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "category")
    private List<TAG> tags;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}