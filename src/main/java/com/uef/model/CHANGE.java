/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author sang
 */

public class CHANGE {
    private int id;
    private String subject;
    private String description;
    private String date;
    private EVENT event;

    // Constructors
    public CHANGE() {}
    public CHANGE(int id, String subject, String description, String date, EVENT event) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.date = date;
        this.event = event;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public EVENT getEvent() { return event; }
    public void setEvent(EVENT event) { this.event = event; }
}