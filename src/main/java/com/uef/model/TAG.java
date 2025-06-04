/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author sang
 */

public class TAG {
    private CATEGORY category;
    private EVENT event;

    // Constructors
    public TAG() {}
    public TAG(CATEGORY category, EVENT event) {
        this.category = category;
        this.event = event;
    }

    // Getters and Setters
    public CATEGORY getCategory() { return category; }
    public void setCategory(CATEGORY category) { this.category = category; }
    public EVENT getEvent() { return event; }
    public void setEvent(EVENT event) { this.event = event; }
}