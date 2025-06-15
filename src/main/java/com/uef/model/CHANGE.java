package com.uef.model;

import jakarta.persistence.*;

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
    private String subject;

    @Column(name = "description", length = 100, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String description;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id")
    private EVENT event;

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