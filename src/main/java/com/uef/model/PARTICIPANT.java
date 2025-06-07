package com.uef.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PARTICIPANT")
public class PARTICIPANT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "email")
    private USER user;

    @ManyToOne
    @JoinColumn(name = "ticket", referencedColumnName = "id")
    private TICKET ticket;

    @Column(name = "status")
    private int status;

    @Column(name = "rate")
    private int rate;

    @Column(name = "comment")
    private String comment;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public USER getUser() {
        return user;
    }

    public void setUser(USER user) {
        this.user = user;
    }

    public TICKET getTicket() {
        return ticket;
    }

    public void setTicket(TICKET ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}