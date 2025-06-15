package com.uef.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "[TICKET]")
public class TICKET {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "[index]", nullable = false)
    private int index;
    
    @Column(name = "name", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String name;

    @Column(name = "description", length = 100, nullable = true, columnDefinition = "NVARCHAR(100)")
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "duration", nullable = false)
    private Time duration;

    @Column(name = "regDeadline", nullable = false)
    private Date regDeadline;

    @Column(name = "slots", nullable = false)
    private int slots;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "confirmCode", nullable = false)
    private int confirmCode;

    @Column(name = "qrCode", length = 100, nullable = false, columnDefinition = "NVARCHAR(100)")
    private String qrCode;

    @Column(name = "type", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String type;
    
    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id", nullable = false)
    private EVENT event;

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "id", nullable = true)
    private LOCATION location;
    
    @OneToMany(mappedBy = "ticket")
    private List<PARTICIPANT> participants;

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

    public List<PARTICIPANT> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PARTICIPANT> participants) {
        this.participants = participants;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}