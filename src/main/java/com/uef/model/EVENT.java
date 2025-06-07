package com.uef.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "EVENT")
public class EVENT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "contactInfo")
    private String contactInfo;

    @Column(name = "target")
    private String target;

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "id")
    private LOCATION location;

    @OneToMany(mappedBy = "event")
    private List<TAG> tags;

    @OneToMany(mappedBy = "event")
    private List<TICKET> tickets;

    @OneToMany(mappedBy = "event")
    private List<CHANGE> changes;

    @ManyToOne
    @JoinColumn(name = "organizer", referencedColumnName = "id")
    private ORGANIZER organizer;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public LOCATION getLocation() {
        return location;
    }

    public void setLocation(LOCATION location) {
        this.location = location;
    }

    public List<TAG> getTags() {
        return tags;
    }

    public void setTags(List<TAG> tags) {
        this.tags = tags;
    }

    

    public List<TICKET> getTickets() {
        return tickets;
    }

    public void setTickets(List<TICKET> tickets) {
        this.tickets = tickets;
    }

    public List<CHANGE> getChanges() {
        return changes;
    }

    public void setChanges(List<CHANGE> changes) {
        this.changes = changes;
    }

   

    public ORGANIZER getOrganizer() {
        return organizer;
    }

    public void setOrganizer(ORGANIZER organizer) {
        this.organizer = organizer;
    }

}
