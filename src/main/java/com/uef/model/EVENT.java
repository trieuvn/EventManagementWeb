package com.uef.model;

import java.util.ArrayList;
import java.util.List;

public class EVENT {
    private int id;
    private String name;
    private String description;
    private String date;
    private int duration;
    private String type;
    private String regDeadline;
    private float ticketPrice;
    private String contactInfo;
    private int slots;
    private String status;
    private ORGANIZER organizer;
    private LOCATION location;
    private List<CATEGORY> categories = new ArrayList<>();

    // Constructors
    public EVENT() {}
    public EVENT(int id, String name, String description, String date, int duration, String type,
                 String regDeadline, float ticketPrice, String contactInfo, int slots, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.type = type;
        this.regDeadline = regDeadline;
        this.ticketPrice = ticketPrice;
        this.contactInfo = contactInfo;
        this.slots = slots;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRegDeadline() { return regDeadline; }
    public void setRegDeadline(String regDeadline) { this.regDeadline = regDeadline; }
    public float getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(float ticketPrice) { this.ticketPrice = ticketPrice; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public int getSlots() { return slots; }
    public void setSlots(int slots) { this.slots = slots; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public ORGANIZER getOrganizer() { return organizer; }
    public void setOrganizer(ORGANIZER organizer) { this.organizer = organizer; }
    public LOCATION getLocation() { return location; }
    public void setLocation(LOCATION location) { this.location = location; }
    public List<CATEGORY> getCategories() { return categories; }
    public void setCategories(List<CATEGORY> categories) { this.categories = categories; }
    public void addCategory(CATEGORY category) { this.categories.add(category); }
}