package com.uef.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    @NotBlank(message = "Status cannot be null")
    private int status;

    @Column(name = "rate", nullable = true)
    @Min(value = 0, message = "Rate must be between 0 and 5")
    @Max(value = 5, message = "Rate must be between 0 and 5")
    private int rate;

    @Column(name = "comment", length = 50, nullable = true, columnDefinition = "NVARCHAR(50)")
    @Size(max = 50, message = "Comment must not exceed 50 characters")
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

    public PARTICIPANT(USER user, TICKET ticket, int status, int rate, String comment) {
        this.user = user;
        this.ticket = ticket;
        this.status = status;
        this.rate = rate;
        this.comment = comment;
    }

    public PARTICIPANT() {
    }

    public String getQRCode() throws UnsupportedEncodingException, IOException {
        // Tạo chuỗi kết hợp ticket id với thông tin khác (ví dụ: id và confirmCode)
        String qrData = this.user.getEmail() + "/" + String.valueOf(this.ticket.getId()) + "/" + this.ticket.getConfirmCode() +"/";
        // Gọi phương thức từ lớp QRCode để tạo chuỗi Base64
        String qrCodeBase64 = com.uef.utils.QRCode.convertFromStringToBase64String(qrData);
        return qrCodeBase64;
    }
}