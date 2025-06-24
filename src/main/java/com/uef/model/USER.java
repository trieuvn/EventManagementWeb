package com.uef.model;

import jakarta.persistence.*;
import java.time.LocalDate;
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
    
    public boolean joinEvent(String eventId) {
        try {
            // Giả định bạn có một service/repository để lấy EVENT và TICKET
            // Ví dụ: EventRepository.findById(eventId), TicketRepository.findAvailableTicket(eventId)
            EVENT event = getEventById(eventId); // Placeholder method
            if (event == null) {
                return false; // Sự kiện không tồn tại
            }

            // Lấy vé hợp lệ (còn chỗ và chưa hết hạn đăng ký)
            TICKET ticket = getAvailableTicket(event);
            if (ticket == null) {
                return false; // Không có vé hợp lệ
            }

            // Kiểm tra hạn đăng ký
            LocalDate currentDate = LocalDate.now();
            if (ticket.getRegDeadline().toLocalDate().isBefore(currentDate)) {
                return false; // Hết hạn đăng ký
            }

            // Giảm số lượng chỗ còn lại
            if (ticket.getSlots() <= 0) {
                return false; // Hết chỗ
            }
            ticket.setSlots(ticket.getSlots() - 1);

            // Tạo PARTICIPANT mới
            PARTICIPANT participant = new PARTICIPANT();
            participant.setUser(this); // Gán người dùng hiện tại
            participant.setTicket(ticket); // Gán vé
            participant.setStatus(1); // Giả định 1 là trạng thái "đã tham gia"

            // Thêm vào danh sách participants
            this.participants.add(participant);

            // Lưu thay đổi (giả định có repository/service)
            // Ví dụ: ticketRepository.save(ticket); participantRepository.save(participant);
            return true; // Thành công
        } catch (Exception e) {
            // Xử lý lỗi (ví dụ: lỗi lưu dữ liệu)
            return false; // Thất bại
        }
    }

    // Placeholder method to simulate getting an event by ID
    private EVENT getEventById(String eventId) {
        // Đây là phương thức giả lập, bạn cần triển khai logic thực tế
        // Ví dụ: Truy vấn database để lấy EVENT theo eventId
        return null; // Thay bằng logic thực tế
    }

    // Placeholder method to simulate getting an available ticket
    private TICKET getAvailableTicket(EVENT event) {
        // Đây là phương thức giả lập, bạn cần triển khai logic thực tế
        // Ví dụ: Lấy vé đầu tiên còn chỗ từ danh sách tickets của event
        if (event.getTickets() != null && !event.getTickets().isEmpty()) {
            for (TICKET ticket : event.getTickets()) {
                if (ticket.getSlots() > 0) {
                    return ticket; // Trả về vé đầu tiên còn chỗ
                }
            }
        }
        return null; // Không có vé hợp lệ
    }
}