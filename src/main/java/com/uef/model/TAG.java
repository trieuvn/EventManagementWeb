package com.uef.model;

import jakarta.persistence.*;

@Entity
@Table(name = "[TAG]")
public class TAG {
    @Id
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "name")
    private CATEGORY category;

    @Id
    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id")
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
    
}