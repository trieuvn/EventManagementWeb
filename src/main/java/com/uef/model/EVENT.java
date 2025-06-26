package com.uef.model;

import jakarta.persistence.*;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "[EVENT]")
public class EVENT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(name = "description", nullable = true, columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "type", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    private String type;

    @Column(name = "contactInfo", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    private String contactInfo;

    @Column(name = "target", length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String target;

    @Column(name = "image", nullable = true, columnDefinition = "VARBINARY(MAX)")
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
    public boolean addTag(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return false;
        }
        // Kiểm tra xem TAG đã tồn tại với categoryName và event hiện tại chưa
        for (TAG tag : tags) {
            if (tag.getCategory().getName().equals(categoryName) && tag.getEvent().equals(this)) {
                return false; // Tránh trùng lặp
            }
        }
        // Tạo mới CATEGORY nếu cần (ở đây giả định CATEGORY đã tồn tại trong DB)
        CATEGORY category = new CATEGORY();
        category.setName(categoryName);
        TAG newTag = new TAG(category, this);
        tags.add(newTag);
        return true;
    }

    public boolean removeTag(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return false;
        }
        boolean removed = tags.removeIf(tag -> tag.getCategory().getName().equals(categoryName) && tag.getEvent().equals(this));
        return removed;
    }

    public boolean addChange() {
        try {
            CHANGE newChange = new CHANGE();
            newChange.setEvent(this);
            java.util.Date currentDate = new java.util.Date(); // 08:51 PM +07, 20/06/2025
            newChange.setDate(new java.sql.Date(currentDate.getTime()));
            newChange.setTime(new java.sql.Time(currentDate.getTime()));
            changes.add(newChange);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<PARTICIPANT> getParticipants() {
        if (tickets == null) {
            return new ArrayList<>();
        }
        return tickets.stream()
                .filter(ticket -> ticket.getParticipants() != null)
                .flatMap(ticket -> ticket.getParticipants().stream())
                .collect(Collectors.toList());
    }

    public List<Integer> getRates() {
    if (tickets == null) {
        return new ArrayList<>();
    }
    return tickets.stream()
            .filter(ticket -> ticket.getParticipants() != null)
            .flatMap(ticket -> ticket.getParticipants().stream())
            .filter(participant -> {
                Integer rate = participant.getRate();
                return rate != null && rate.intValue() >= 0;
            })
            .map(PARTICIPANT::getRate)
            .collect(Collectors.toList());
}

    public float getAvgRate() {
        List<Integer> rates = getRates();
        if (rates.isEmpty()) {
            return 0.0f;
        }
        return (float) rates.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public boolean getStatus() {
        if (tickets == null) {
            return false;
        }
        return tickets.stream()
                .anyMatch(ticket -> ticket.getSlots() > 0);
    }
}
