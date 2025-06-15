package com.uef.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "[USER]")
public class USER {
    @Id
    @Column(name = "email", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String email;

    @Column(name = "firstName", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String firstName;

    @Column(name = "lastName", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String lastName;

    @Column(name = "password", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String password;

    @Column(name = "phoneNumber", length = 15, nullable = false, columnDefinition = "NVARCHAR(15)")
    private String phoneNumber;

    @Column(name = "role")
    private int role;

    @OneToMany(mappedBy = "user")
    private List<PARTICIPANT> participants;
    
    // Getters and Setters
    public String getEmail() { return email; }

    public List<PARTICIPANT> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PARTICIPANT> participants) {
        this.participants = participants;
    }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }

    public USER(String email, String firstName, String lastName, String password, String phoneNumber, int role, List<PARTICIPANT> participants) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.participants = participants;
    }

    public USER() {
    }
    
}