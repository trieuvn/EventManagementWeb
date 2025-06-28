package com.uef.controller.admin;

import com.uef.model.TICKET;
import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.model.LOCATION;
import com.uef.service.TicketService;
import com.uef.service.EventService;
import com.uef.service.LocationService;
import com.uef.service.ParticipantService;
import jakarta.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin/tickets")
public class AdminTicketDetailScreenController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ParticipantService participantService;

    // Khởi tạo formatter cho Time
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Time.class, new CustomDateEditor(dateFormat, true) {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null && !text.trim().isEmpty()) {
                    try {
                        String formattedTime = text.trim();
                        if (formattedTime.length() == 5) { // "HH:mm"
                            formattedTime += ":00";
                        } else if (formattedTime.length() != 8) { // Không phải "HH:mm:ss"
                            throw new IllegalArgumentException("Invalid time format: " + text + ". Use HH:mm or HH:mm:ss.");
                        }
                        setValue(Time.valueOf(LocalTime.parse(formattedTime)));
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Invalid time format: " + text + ". Use HH:mm or HH:mm:ss. Error: " + e.getMessage());
                    }
                } else {
                    setValue(null);
                }
            }
        });
    }

    // Hiển thị danh sách vé
    @GetMapping
    public String listTickets(Model model) {
        List<TICKET> tickets = ticketService.getAll();
        model.addAttribute("tickets", tickets);
        model.addAttribute("successMessage", model.getAttribute("successMessage"));
        model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
        return "admin/tickets/list";
    }

    // Hiển thị form thêm vé mới
    @GetMapping("/add")
    public String showAddForm(@RequestParam("eventId") int eventId, Model model) {
        EVENT event = eventService.getById(eventId);
        if (event == null) {
            model.addAttribute("errorMessage", "Sự kiện không tồn tại.");
            return "redirect:/admin/events";
        }
        TICKET ticket = new TICKET();
        ticket.setEvent(event);
        ticket.setName("Vé mặc định");
        ticket.setPrice(0);
        ticket.setDate(Date.valueOf(LocalDate.now())); // 29/06/2025
        ticket.setDuration(Time.valueOf(LocalTime.of(2, 0, 0))); // 02:00:00
        ticket.setRegDeadline(Date.valueOf(LocalDate.now().plusDays(7))); // 06/07/2025
        ticket.setSlots(100);
        ticket.setStatus(0); // Upcoming
        ticket.setType(event.getType()); // Loại vé khớp với sự kiện
        ticket.setConfirmCode(123456); // Mã xác nhận mặc định
        ticket.setQrCode("QR_DEFAULT_" + System.currentTimeMillis()); // Giá trị mặc định duy nhất
        model.addAttribute("ticket", ticket);
        return "admin/tickets/add-ticket";
    }

    // Xử lý thêm vé mới
    @PostMapping("/add")
    public String addTicket(@Valid @ModelAttribute TICKET ticket, BindingResult result, RedirectAttributes redirectAttributes) {
        System.out.println("Starting addTicket at " + LocalDate.now() + " " + LocalTime.now() + " for eventId: " + (ticket.getEvent() != null ? ticket.getEvent().getId() : "null"));
        System.out.println("Ticket details: Name=" + ticket.getName() + ", Type=" + ticket.getType() + ", Price=" + ticket.getPrice() + ", QrCode=" + ticket.getQrCode() + ", Duration=" + ticket.getDuration());
        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            redirectAttributes.addFlashAttribute("errorMessage", "Dữ liệu không hợp lệ: " + result.getAllErrors());
            return "redirect:/admin/tickets/add?eventId=" + (ticket.getEvent() != null ? ticket.getEvent().getId() : "unknown");
        }
        // Đảm bảo qrCode và event không null
        if (ticket.getQrCode() == null || ticket.getQrCode().trim().isEmpty()) {
            ticket.setQrCode("QR_DEFAULT_" + System.currentTimeMillis());
            System.out.println("QrCode was null, set to: " + ticket.getQrCode());
        }
        if (ticket.getEvent() == null || ticket.getEvent().getId() <= 0) {
            throw new IllegalArgumentException("Event is required and must be valid.");
        }
        try {
            EVENT event = eventService.getById(ticket.getEvent().getId());
            if (event == null) {
                throw new IllegalArgumentException("Sự kiện không tồn tại với ID: " + ticket.getEvent().getId());
            }
            System.out.println("Event found: " + event.getName() + ", Type=" + event.getType());
            if (!isValidTicketType(event.getType(), ticket.getType())) {
                throw new IllegalArgumentException("Loại vé " + ticket.getType() + " không hợp lệ với loại sự kiện " + event.getType());
            }
            System.out.println("Validating ticket type: " + ticket.getType());
            ticketService.set(ticket);
            System.out.println("Ticket saved successfully: ID = " + ticket.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Thêm vé thành công!");
        } catch (Exception e) {
            System.err.println("Error adding ticket at " + LocalDate.now() + " " + LocalTime.now() + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm vé: " + e.getMessage());
            return "redirect:/admin/tickets/add?eventId=" + (ticket.getEvent() != null ? ticket.getEvent().getId() : "unknown");
        }
        return "redirect:/admin/tickets/edit/" + String.valueOf(ticket.getId()); // Chuyển hướng về danh sách vé
    }

    // Hiển thị form chỉnh sửa vé
    @RequestMapping({"/edit/{id}", "/view/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        TICKET ticket = ticketService.getById(id);
        if (ticket == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vé không tồn tại.");
            return "redirect:/admin/tickets";
        }
        List<PARTICIPANT> participants = ticket.getParticipants();
        model.addAttribute("ticket", ticket);
        model.addAttribute("participantList", participants);
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("locationList", locationService.getAll());
        return "admin/ticket/ticket-detail";
    }

    @PostMapping("/update/{id}")
    public String saveEditedTicket(
            @PathVariable("id") int ticketId,
            @Valid @ModelAttribute("ticket") TICKET ticket,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        System.out.println("Received POST request for /update/" + ticketId + " at " + LocalDate.now() + " " + LocalTime.now());
        System.out.println("Ticket details before save: Name=" + ticket.getName() + ", Type=" + ticket.getType() + 
                          ", Price=" + ticket.getPrice() + ", QrCode=" + ticket.getQrCode() + 
                          ", Duration=" + ticket.getDuration() + ", Location ID=" + (ticket.getLocation() != null ? ticket.getLocation().getId() : "null") + 
                          ", Latitude=" + (ticket.getLocation() != null ? ticket.getLocation().getLatitude() : "null") + 
                          ", Longitude=" + (ticket.getLocation() != null ? ticket.getLocation().getLongitude() : "null"));

        if (result.hasErrors()) {
            System.out.println("Validation errors: " + result.getAllErrors());
            redirectAttributes.addFlashAttribute("message", "Dữ liệu không hợp lệ: " + result.getAllErrors());
            return "redirect:/admin/tickets/edit/" + ticketId;
        }

        ticket.setId(ticketId);
        // Đảm bảo qrCode và event không null
        if (ticket.getQrCode() == null || ticket.getQrCode().trim().isEmpty()) {
            ticket.setQrCode("QR_DEFAULT_" + System.currentTimeMillis());
            System.out.println("QrCode was null, set to: " + ticket.getQrCode());
        }
        if (ticket.getEvent() == null || ticket.getEvent().getId() <= 0) {
            TICKET existingTicket = ticketService.getById(ticketId);
            if (existingTicket != null) {
                ticket.setEvent(existingTicket.getEvent());
                System.out.println("Event was null, set to existing event ID: " + existingTicket.getEvent().getId());
            } else {
                throw new IllegalArgumentException("Event is required and must be valid.");
            }
        }

        // Xử lý location trước khi gửi vào service
        LOCATION location = ticket.getLocation();
        if (location != null) {
            if (location.getId() == 0) { // Location mới
                if (location.getName() == null || location.getName().trim().isEmpty()) {
                    location.setName("Default Location");
                }
                if (Float.isNaN(location.getLatitude()) || location.getLatitude() == 0) {
                    location.setLatitude(10.7769f); // Hà Nội
                }
                if (Float.isNaN(location.getLongitude()) || location.getLongitude() == 0) {
                    location.setLongitude(106.7009f); // Hà Nội
                }
                ticket.setLocation(location); // Cập nhật lại ticket với location mới
            } else { // Location đã tồn tại
                ticket.setLocation(locationService.getById(location.getId())); // Lấy lại từ database
            }
        } else {
            TICKET existingTicket = ticketService.getById(ticketId);
            if (existingTicket != null && existingTicket.getLocation() != null) {
                ticket.setLocation(existingTicket.getLocation());
            } else {
                location = new LOCATION();
                location.setName("Default Location");
                location.setLatitude(10.7769f); // Hà Nội
                location.setLongitude(106.7009f); // Hà Nội
                ticket.setLocation(location);
            }
        }

        try {
            ticketService.set(ticket);
            redirectAttributes.addFlashAttribute("successMessage", "Vé đã được cập nhật thành công!");
            System.out.println("Ticket updated successfully: ID = " + ticket.getId());
        } catch (Exception e) {
            System.err.println("Error saving ticket at " + LocalDate.now() + " " + LocalTime.now() + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Lỗi khi lưu vé: " + e.getMessage());
        }
        return "redirect:/admin/tickets/edit/"+ String.valueOf(ticketId); // Chuyển hướng về danh sách vé
    }

    // Xử lý cập nhật vé
    @PostMapping("/update")
    public String updateTicket(@ModelAttribute TICKET ticket, RedirectAttributes redirectAttributes) {
        System.out.println("Starting updateTicket at " + LocalDate.now() + " " + LocalTime.now() + " for ticketId: " + ticket.getId());
        System.out.println("Ticket details: Name=" + ticket.getName() + ", Type=" + ticket.getType() + ", Price=" + ticket.getPrice() + ", QrCode=" + ticket.getQrCode() + ", Event=" + (ticket.getEvent() != null ? ticket.getEvent().getId() : "null") + ", Duration=" + ticket.getDuration());
        // Đảm bảo qrCode và event không null
        if (ticket.getQrCode() == null || ticket.getQrCode().trim().isEmpty()) {
            ticket.setQrCode("QR_DEFAULT_" + System.currentTimeMillis());
            System.out.println("QrCode was null, set to: " + ticket.getQrCode());
        }
        if (ticket.getEvent() == null || ticket.getEvent().getId() <= 0) {
            TICKET existingTicket = ticketService.getById(ticket.getId());
            if (existingTicket != null) {
                ticket.setEvent(existingTicket.getEvent());
                System.out.println("Event was null, set to existing event ID: " + existingTicket.getEvent().getId());
            } else {
                throw new IllegalArgumentException("Event is required and must be valid.");
            }
        }
        try {
            EVENT event = eventService.getById(ticket.getEvent().getId());
            if (event == null) {
                throw new IllegalArgumentException("Sự kiện không tồn tại với ID: " + ticket.getEvent().getId());
            }
            System.out.println("Event found: " + event.getName() + ", Type=" + event.getType());
            if (!isValidTicketType(event.getType(), ticket.getType())) {
                throw new IllegalArgumentException("Loại vé " + ticket.getType() + " không hợp lệ với loại sự kiện " + event.getType());
            }
            System.out.println("Validating ticket type: " + ticket.getType());
            ticketService.set(ticket);
            System.out.println("Ticket updated successfully: ID = " + ticket.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật vé thành công!");
        } catch (Exception e) {
            System.err.println("Error updating ticket at " + LocalDate.now() + " " + LocalTime.now() + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật vé: " + e.getMessage());
            return "redirect:/admin/tickets/edit/" + ticket.getId(); // Quay lại form chỉnh sửa
        }
        return "redirect:/admin/tickets/edit" + String.valueOf(ticket.getId()); // Chuyển hướng về danh sách vé
    }

    // Xử lý xóa vé (bỏ qua theo yêu cầu)
    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        TICKET ticket = new TICKET();
        try {
            ticket = ticketService.getById(id);
            if (ticket == null) {
                throw new IllegalArgumentException("Vé không tồn tại.");
            }
            List<PARTICIPANT> participants = participantService.getByTicket(id);
            if (!participants.isEmpty()) {
                throw new IllegalStateException("Không thể xóa vé vì đã có người tham gia.");
            }
            ticketService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa vé thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa vé: " + e.getMessage());
        }
        
        return "redirect:/admin/events/edit/" +String.valueOf(ticket.getEvent().getId());
    }

    // Kiểm tra tính hợp lệ của loại vé với loại sự kiện (BR-24)
    private boolean isValidTicketType(String eventType, String ticketType) {
        if ("online".equalsIgnoreCase(eventType)) {
            return "online".equalsIgnoreCase(ticketType);
        } else if ("offline".equalsIgnoreCase(eventType)) {
            return "offline".equalsIgnoreCase(ticketType);
        } else if ("hybrid".equalsIgnoreCase(eventType)) {
            return "online".equalsIgnoreCase(ticketType) || 
                   "offline".equalsIgnoreCase(ticketType) || 
                   "hybrid".equalsIgnoreCase(ticketType);
        }
        return false;
    }
}