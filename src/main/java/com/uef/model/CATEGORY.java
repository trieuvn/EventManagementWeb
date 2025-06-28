package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "[CATEGORY]")
public class CATEGORY {
    @Id
    @Column(name = "name", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    @NotBlank(message = "Name cannot be null")
    @Size(max = 30, message = "Name must not exceed 30 characters")
    private String name;

    @Column(name = "description", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    @Size(max = 50, message = "Description must not exceed 50 characters")
    private String description;
    
    @OneToMany(mappedBy = "category")
    private List<TAG> tags;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<TAG> getTags() {
        return tags;
    }

    public void setTags(List<TAG> tags) {
        this.tags = tags;
    }
    

    public CATEGORY(String name, String description, List<TAG> tags) {
        this.name = name;
        this.description = description;
        this.tags = tags;
    }

    public CATEGORY() {
    }
}