package com.uef.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "[CATEGORY]")
public class CATEGORY {
    @Id
    @Column(name = "name", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String name;

    @Column(name = "description", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String description;
    
    @OneToMany(mappedBy = "category")
    private List<TAG> tags;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public CATEGORY(String name, String description, List<TAG> tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public CATEGORY() {
    }
}