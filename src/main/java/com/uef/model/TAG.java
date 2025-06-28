package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "[TAG]")
public class TAG {
    @Id
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "name")
    @NotBlank(message = "Category cannot be null")
    private CATEGORY category;

    @Id
    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id")
    @NotBlank(message = "Event cannot be null")
    private EVENT event;

    // Getters and Setters
    public EVENT getEvent() { return event; }
    public void setEvent(EVENT event) { this.event = event; }

    public CATEGORY getCategory() {
        return category;
    }

    public void setCategory(CATEGORY category) {
        this.category = category;
    }

    public TAG(CATEGORY category, EVENT event) {
        this.category = category;
        this.event = event;
    }

    public TAG() {
    }
    
}