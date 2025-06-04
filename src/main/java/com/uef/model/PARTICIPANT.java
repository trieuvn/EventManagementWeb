/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author sang
 */

public class PARTICIPANT {
    private USER user;
    private EVENT event;
    private int status;
    private int rate;
    private String description;

    // Constructors
    public PARTICIPANT() {}
    public PARTICIPANT(USER user, EVENT event, int status, int rate, String description) {
        this.user = user;
        this.event = event;
        this.status = status;
        this.rate = rate;
        this.description = description;
    }

    // Getters and Setters
    public USER getUser() { return user; }
    public void setUser(USER user) { this.user = user; }
    public EVENT getEvent() { return event; }
    public void setEvent(EVENT event) { this.event = event; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public int getRate() { return rate; }
    public void setRate(int rate) { this.rate = rate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}