package com.uef.eventModel;

public class Event {

    private int id;
    private String name;
    private String category;
    private String eventDate;
    private int availableSlots;
    private boolean registrationOpen;

    public Event() {
    }

    public Event(int id, String name, String category, String eventDate, int availableSlots, boolean registrationOpen) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.eventDate = eventDate;
        this.availableSlots = availableSlots;
        this.registrationOpen = registrationOpen;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public boolean isRegistrationOpen() {
        return registrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        this.registrationOpen = registrationOpen;
    }
}
