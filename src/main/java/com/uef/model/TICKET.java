package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "[TICKET]")
public class TICKET {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "[index]", nullable = false)
    @NotNull(message = "Index cannot be null")
    private int index;

    @Column(name = "name", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    @NotNull(message = "Name cannot be null")
    @Size(max = 30, message = "Name must not exceed 30 characters")
    private String name;

    @Column(name = "description", length = 100, nullable = true, columnDefinition = "NVARCHAR(100)")
    @Size(max = 100, message = "Description must not exceed 100 characters")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be zero or greater")
    private int price;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    private Date date;

    @Column(name = "duration", nullable = false)
    @NotNull(message = "Duration cannot be null")
    private Time duration;

    @Column(name = "regDeadline", nullable = false)
    @NotNull(message = "Registration deadline cannot be null")
    private Date regDeadline;

    @Column(name = "slots", nullable = false)
    @NotNull(message = "Slots cannot be null")
    @Min(value = -1, message = "Slots must be -1 or greater")
    private int slots;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Status cannot be null")
    private int status;

    @Column(name = "confirmCode", nullable = false)
    @NotNull(message = "Confirm code cannot be null")
    private int confirmCode;

    @Column(name = "qrCode", length = 100, nullable = false, columnDefinition = "NVARCHAR(100)")
    @NotNull(message = "QR code cannot be null")
    @Size(max = 100, message = "QR code must not exceed 100 characters")
    private String qrCode;

    @Column(name = "type", length = 30, nullable = false, columnDefinition = "NVARCHAR(30)")
    @NotNull(message = "Type cannot be null")
    @Size(max = 30, message = "Type must not exceed 30 characters")
    private String type;

    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Event cannot be null")
    private EVENT event;

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "id", nullable = true)
    private LOCATION location;

    @OneToMany(mappedBy = "ticket")
    private List<PARTICIPANT> participants;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Date getRegDeadline() {
        return regDeadline;
    }

    public void setRegDeadline(Date regDeadline) {
        this.regDeadline = regDeadline;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(int confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

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

    public TICKET(int id, int index, String name, String description, int price, Date date, Time duration, Date regDeadline, int slots, int status, int confirmCode, String qrCode, String type, EVENT event, LOCATION location, List<PARTICIPANT> participants) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
        this.duration = duration;
        this.regDeadline = regDeadline;
        this.slots = slots;
        this.status = status;
        this.confirmCode = confirmCode;
        this.qrCode = qrCode;
        this.type = type;
        this.event = event;
        this.location = location;
        this.participants = participants;
    }

    public TICKET() {
    }

    public List<Integer> getRates() {
        List<Integer> rates = new ArrayList<>();
        if (this.participants != null) {
            for (PARTICIPANT participant : this.participants) {
                if (participant.getRate() != 0) { // Giả sử 0 là giá trị mặc định hoặc không hợp lệ
                    rates.add(participant.getRate());
                }
            }
        }
        return rates;
    }

    public float getAvgRate() {
        List<Integer> rates = getRates();
        if (rates == null || rates.isEmpty()) {
            return 0.0f;
        }
        int sum = 0;
        for (Integer rate : rates) {
            sum += rate;
        }
        return (float) sum / rates.size();
    }

    public int getAvailableSlots() {
        if (this.participants == null) {
            return this.slots;
        }
        return this.slots - this.participants.size();
    }
    

    public String createQRCode() throws UnsupportedEncodingException, IOException {
    // Tạo chuỗi kết hợp ticket id với thông tin khác (ví dụ: id và confirmCode)
    String qrData = "TicketID:" + this.id + ",ConfirmCode:" + this.confirmCode;
    // Gọi phương thức từ lớp QRCode để tạo chuỗi Base64
    String qrCodeBase64 = com.uef.utils.QRCode.convertFromStringToBase64String(qrData);
    // Lưu chuỗi Base64 vào thuộc tính qrCode
    this.setQrCode(qrCodeBase64);
    return qrCodeBase64;
}
}