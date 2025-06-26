package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "[CHANGE]")
public class CHANGE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "subject", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    @NotNull(message = "Subject cannot be null")
    @Size(max = 50, message = "Subject must not exceed 50 characters")
    private String subject;

    @Column(name = "description", length = 100, nullable = true, columnDefinition = "NVARCHAR(50)")
    @Size(max = 100, message = "Description must not exceed 100 characters")
    private String description;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    private Date date;

    @Column(name = "time", nullable = false)
    @NotNull(message = "Time cannot be null")
    private Time time;
    
    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id")
    private EVENT event;

    public CHANGE(int id, String subject, String description, Date date, Time time, EVENT event) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.date = date;
        this.time = time;
        this.event = event;
    }

    public CHANGE() {
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
    public EVENT getEvent() { return event; }
    public void setEvent(EVENT event) { this.event = event; }
}