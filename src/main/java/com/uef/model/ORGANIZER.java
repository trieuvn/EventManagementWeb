package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "[ORGANIZER]")
public class ORGANIZER {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "firstName", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Column(name = "lastName", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    @NotNull(message = "Last name cannot be null")
    @Size(max = 30, message = "Last name must not exceed 30 characters")
    private String lastName;

    @Column(name = "phoneNumber", length = 15, nullable = true, columnDefinition = "NVARCHAR(15)")
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String phoneNumber;

    @Column(name = "email", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    @NotNull(message = "Email cannot be null")
    @Size(max = 50, message = "Email must not exceed 50 characters")
    private String email;

    @Column(name = "avatar", nullable = true, columnDefinition = "VARBINARY(MAX)")
    private byte[] avatar;
    
    @OneToMany(mappedBy = "organizer")
    private List<EVENT> events;

    public List<EVENT> getEvents() {
        return events;
    }

    public void setEvents(List<EVENT> events) {
        this.events = events;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public ORGANIZER(int id, String firstName, String lastName, String phoneNumber, String email, byte[] avatar, List<EVENT> events) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.avatar = avatar;
        this.events = events;
    }

    public ORGANIZER() {
    }

    public EVENT createEvent(String eventName, String description, String type, String contactInfo, String target) {
        if (eventName == null || type == null || target == null) {
            throw new IllegalArgumentException("Event name, type, and target are required.");
        }

        EVENT event = new EVENT();
        event.setName(eventName);
        event.setDescription(description);
        event.setType(type);
        event.setContactInfo(contactInfo);
        event.setTarget(target);
        event.setOrganizer(this); // Gán organizer hiện tại (this là ORGANIZER)
        events.add(event); // Thêm sự kiện vào danh sách events
        return event;
    }
}
