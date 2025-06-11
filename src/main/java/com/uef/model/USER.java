package com.uef.model;

import jakarta.validation.constraints.*;

public class USER {
    @NotNull(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotNull(message = "Họ không được để trống")
    private String firstName;

    @NotNull(message = "Tên không được để trống")
    private String lastName;

    @NotNull(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    private String phoneNumber;

    @Min(value = 0, message = "Role phải là 0 hoặc 1")
    private int role = 1; // Mặc định là participant (1)

    public USER() {}
    public USER(String email, String firstName, String lastName, String password, String phoneNumber, int role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getEmail() { return email; }
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
}