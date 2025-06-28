/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.model;

/**
 *
 * @author Administrator
 */
public class QRDataDTO {
    private String email;
    private int ticketId;
    private String confirmCode;

    public QRDataDTO(String email, int ticketId, String confirmCode) {
        this.email = email;
        this.ticketId = ticketId;
        this.confirmCode = confirmCode;
    }

    public QRDataDTO() {
    }

    public String getEmail() {
        return email;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getConfirmCode() {
        return confirmCode;
    }
}
