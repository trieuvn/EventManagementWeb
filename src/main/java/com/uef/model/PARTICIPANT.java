package com.uef.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PARTICIPANT")
public class PARTICIPANT {
    @Id
    @ManyToOne
    @JoinColumn(name = "[user]", referencedColumnName = "email")
    private USER user;

    @Id
    @ManyToOne
    @JoinColumn(name = "ticket", referencedColumnName = "id")
    private TICKET ticket;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "rate", nullable = true)
    private int rate;

    @Column(name = "comment", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String comment;

    // Getters and Setters

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