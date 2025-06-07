package com.uef.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "TICKET")
public class TICKET {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "date")
    private Date date;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "regDeadline")
    private Date regDeadline;

    @Column(name = "slots")
    private int slots;

    @Column(name = "status")
    private int status;

    @Column(name = "confirmCode")
    private int confirmCode;

    @Column(name = "qrCode")
    private String qrCode;

    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id")
    private EVENT event;

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "id")
    private LOCATION location;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Time getDuration() { return duration; }
    public void setDuration(Time duration) { this.duration = duration; }
    public Date getRegDeadline() { return regDeadline; }
    public void setRegDeadline(Date regDeadline) { this.regDeadline = regDeadline; }
    public int getSlots() { return slots; }
    public void setSlots(int slots) { this.slots = slots; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public int getConfirmCode() { return confirmCode; }
    public void setConfirmCode(int confirmCode) { this.confirmCode = confirmCode; }
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public EVENT getEvent() {
        return event;
    }

    public void setEvent(EVENT event) {
        this.event = event;
    }

    public LOCATION getLocation() {
        return location;
    }

    public void setLocation(LOCATION location) {
        this.location = location;
    }
}