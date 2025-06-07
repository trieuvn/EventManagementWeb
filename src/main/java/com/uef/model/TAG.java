package com.uef.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TAG")
public class TAG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id")
    private EVENT event;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public EVENT getEvent() { return event; }
    public void setEvent(EVENT event) { this.event = event; }
}