package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "[EVENT]")
public class EVENT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    @NotNull(message = "Name cannot be null")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @Column(name = "description", length = 100, nullable = true, columnDefinition = "NVARCHAR(50)")
    @Size(max = 100, message = "Description must not exceed 100 characters")
    private String description;

    @Column(name = "type", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    @NotNull(message = "Type cannot be null")
    @Size(max = 30, message = "Type must be 'online', 'offline', or 'hybrid'")
    private String type;

    @Column(name = "contactInfo", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    @Size(max = 50, message = "Contact info must not exceed 50 characters")
    private String contactInfo;

    @Column(name = "target", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    @NotNull(message = "Target cannot be null")
    @Size(max = 50, message = "Target must not exceed 50 characters")
    private String target;

    @Column(name = "image", nullable = true)
    private byte[] image;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public EVENT(int id, String name, String description, String type, String contactInfo, String target, byte[] image, List<TAG> tags, List<TICKET> tickets, List<CHANGE> changes, ORGANIZER organizer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.contactInfo = contactInfo;
        this.target = target;
        this.image = image;
        this.tags = tags;
        this.tickets = tickets;
        this.changes = changes;
        this.organizer = organizer;
    }

    public EVENT() {
    }



}
