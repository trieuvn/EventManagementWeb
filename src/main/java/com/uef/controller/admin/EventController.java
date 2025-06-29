package com.uef.controller.admin;

import com.uef.annotation.RoleRequired;
import com.uef.model.CHANGE;
import com.uef.model.EVENT;
import com.uef.model.ORGANIZER;
import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.ChangeService;
import com.uef.service.EventService;
import com.uef.service.OrganizerService;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import com.uef.service.UserService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ChangeService changeService;

    @Autowired
    private UserService userService;

    @RoleRequired({"admin"})
    @GetMapping
    public String listEvents(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            Model model) {

        List<EVENT> eventList = eventService.getAll();

        // Chuyển đổi ngày nếu có và hợp lệ
        LocalDate from = null;
        LocalDate to = null;

        try {
            if (fromDate != null && !fromDate.isEmpty()) {
                from = LocalDate.parse(fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                to = LocalDate.parse(toDate);
            }
        } catch (DateTimeParseException e) {
            // Có thể log lỗi hoặc gửi message tới model nếu muốn hiển thị thông báo
        }

        // Sử dụng biến final để dùng được trong lambda
        final LocalDate finalFrom = from;
        final LocalDate finalTo = to;

        // Lọc theo khoảng ngày từ ticket
        if (finalFrom != null && finalTo != null) {
            eventList = eventList.stream()
                    .filter(e -> {
                        List<TICKET> tickets = e.getTickets();
                        if (tickets == null || tickets.isEmpty()) {
                            return false;
                        }

                        return tickets.stream().anyMatch(t -> {
                            java.sql.Date ticketDate = t.getDate();
                            if (ticketDate == null) {
                                return false;
                            }

                            LocalDate eventDate = ticketDate.toLocalDate();
                            return (eventDate.isEqual(finalFrom) || eventDate.isAfter(finalFrom))
                                    && (eventDate.isEqual(finalTo) || eventDate.isBefore(finalTo));
                        });
                    })
                    .toList();
        }

        // Lọc theo loại sự kiện
        if (type != null && !type.isEmpty()) {
            eventList = eventList.stream()
                    .filter(e -> e.getType().equalsIgnoreCase(type))
                    .toList();
        }

        // Lọc theo trạng thái ("true"/"false")
        if (status != null && !status.isEmpty()) {
            boolean isOpen = Boolean.parseBoolean(status);
            eventList = eventList.stream()
                    .filter(e -> e.getStatus() == isOpen)
                    .toList();
        }

        // Lọc theo từ khóa tìm kiếm
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.trim().toLowerCase();
            eventList = eventList.stream()
                    .filter(e -> e.getName().toLowerCase().contains(kw)
                    || (e.getContactInfo() != null && e.getContactInfo().toLowerCase().contains(kw)))
                    .toList();
        }

        // Lấy danh sách loại sự kiện duy nhất
        List<String> eventTypes = eventService.getAll().stream()
                .map(EVENT::getType)
                .filter(t -> t != null && !t.isEmpty())
                .distinct()
                .toList();

        // Lấy danh sách trạng thái duy nhất
        List<Boolean> statusList = eventService.getAll().stream()
                .map(EVENT::getStatus)
                .distinct()
                .toList();

        model.addAttribute("eventTypes", eventTypes);
        model.addAttribute("statusList", statusList);
        model.addAttribute("eventList", eventList);
        model.addAttribute("guestList", organizerService.getAll());

        // Top sinh viên
        List<USER> topStudents = userService.getAll().stream()
                .sorted(Comparator.comparingLong(USER::getTotalParticipated).reversed())
                .toList();
        model.addAttribute("topStudents", topStudents);

        // Lịch sử thay đổi
        List<CHANGE> changes = changeService.getAll();
        model.addAttribute("changes", changes);

        // Thống kê số lượng
        model.addAttribute("upcomingCount", eventService.getUpcomingEvents());
        model.addAttribute("ongoingCount", eventService.getUpcomingEvents()); // Nếu có ongoing riêng thì sửa
        model.addAttribute("endedCount", eventService.getPastEvents());
        model.addAttribute("notificationCount", changes.size());

        // Load layout
        model.addAttribute("body", "admin/event/event-management");
        return "admin/layout/main";
    }

    @RoleRequired({"admin"})
    @PostMapping("/save/{id}")
    public String saveEditedEvent(
            @PathVariable("id") int eventId,
            @Valid @ModelAttribute("event") EVENT event,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dữ liệu không hợp lệ: " + result.getAllErrors());
            return "redirect:/admin/events/edit/" + eventId;
        }

        // Ensure the event ID is set
        event.setId(eventId);

        // Handle image upload
        try {
            if (!imageFile.isEmpty()) {
                // TODO: Validate image file (size, type, etc.) if needed
                event.setImage(imageFile.getBytes());
            } else {
                // TODO: If no new image is uploaded, retain the existing image
                // Event existingEvent = eventService.getEventById(eventId);
                // if (existingEvent != null && existingEvent.getImage() != null) {
                //     event.setImage(existingEvent.getImage());
                // }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải lên hình ảnh: " + e.getMessage());
            return "redirect:/admin/events/edit/" + eventId;
        }

        // TODO: Save the updated event to the database
        // eventService.saveEvent(event);
        eventService.set(event);
        redirectAttributes.addFlashAttribute("successMessage", "Lưu thay đổi sự kiện thành công!");
        return "redirect:/admin/events";
    }

    @RoleRequired({"admin"})
    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id, RedirectAttributes redirectAttributes) {
        EVENT event = eventService.getById(id);
        if (event == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sự kiện không tồn tại.");
            return "redirect:/admin/events";
        }

        try {
            eventService.delete(event); // Giao dịch 1: Xóa sự kiện, TICKET, PARTICIPANT
        } catch (Exception e) {
            System.err.println("Error deleting event ID " + id + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa sự kiện: " + e.getMessage());
            return "redirect:/admin/events";
        }

        // Giao dịch 2: Ghi log thay đổi với REQUIRES_NEW
        if (event != null) {
            CHANGE change = new CHANGE();
            change.setEvent(event);
            change.setDate(Date.valueOf(LocalDate.now())); // 01:40 AM +07, 29/06/2025
            change.setTime(Time.valueOf(LocalTime.of(1, 40))); // Thời gian hiện tại
            change.setDescription("Đã xóa sự kiện: " + event.getName());
            change.setSubject("Xóa sự kiện");
            try {
                saveChangeLog(change, redirectAttributes); // Gọi phương thức riêng với giao dịch mới
            } catch (Exception e) {
                System.err.println("Error saving change log for event ID " + id + ": " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi log thay đổi: " + e.getMessage());
            }
        }
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sự kiện thành công!");
        return "redirect:/admin/events"; // Chuyển hướng tức thời, không cần reload
    }

    @RoleRequired({"admin"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveChangeLog(CHANGE change, RedirectAttributes redirectAttributes) {
        try {
            changeService.set(change);
            System.out.println("Change log saved for event ID: " + (change.getEvent() != null ? change.getEvent().getId() : "null"));
        } catch (IllegalArgumentException e) {
            System.err.println("Error in saveChangeLog: " + e.getMessage());
            throw new RuntimeException("Lỗi khi lưu log thay đổi: " + e.getMessage(), e);
        }
    }

    @RoleRequired({"admin"})
    @PostMapping("/create")
    public String createEvent(RedirectAttributes redirectAttributes) {
        EVENT event = new EVENT();
        event.setTarget("Sinh viên UEF");
        event.setContactInfo("trieu123ok@gmail.com");
        event.setName("Sự kiện mới");
        event.setType("Hybrid");
        eventService.set(event);
        int id = event.getId();
        redirectAttributes.addFlashAttribute("successMessage", "Tạo sự kiện thành công!");
        return "redirect:/admin/events/edit/" + id; // Chuyển hướng về trang chỉnh sửa để xem chi tiết
    }

    @RoleRequired({"admin"})
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            event.setType(status);
            eventService.setStatus(event);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái sự kiện thành công!");
        }
        return "redirect:/admin/events";
    }

    @RoleRequired({"admin"})
    @GetMapping("/details")
    public String eventDetails(Model model) {
        model.addAttribute("event", eventService.getAll());
        model.addAttribute("body", "admin/events/details");
        return "admin/layout/main";
    }

    // Hiển thị form chỉnh sửa sự kiện với nút thêm vé
    @RoleRequired({"admin"})
    @GetMapping("/edit/{id}")
    public String showEditEventForm(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event == null) {
            model.addAttribute("errorMessage", "Sự kiện không tồn tại.");
            return "redirect:/admin/events";
        }
        model.addAttribute("event", event);
        model.addAttribute("tickets", event.getTickets());
        model.addAttribute("body", "admin/event/event-detail");
        return "admin/layout/main";
    }

    // Redirect tới form thêm vé
    @RoleRequired({"admin"})
    @GetMapping("/edit/{id}/add-ticket")
    public String showAddTicketForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) throws IOException {
        EVENT event = eventService.getById(id);
        if (event == null) {
            model.addAttribute("errorMessage", "Sự kiện không tồn tại.");
            return "redirect:/admin/events";
        }
        TICKET ticket = new TICKET();
        ticket.setEvent(event);
        ticket.setName("Vé mặc định");
        ticket.setPrice(0);
        ticket.setDate(Date.valueOf(LocalDate.now()));
        ticket.setDuration(Time.valueOf(LocalTime.of(2, 0))); // 2 giờ
        ticket.setRegDeadline(Date.valueOf(LocalDate.now().plusDays(7))); // 7 ngày sau
        ticket.setSlots(100);
        ticket.setStatus(0); // Upcoming
        ticket.setType(event.getType()); // Loại vé khớp với sự kiện
        ticket.setConfirmCode(123456); // Mã xác nhận mặc định
        ticket.setQrCode("QR_DEFAULT"); // QR code mặc định
        model.addAttribute("ticket", ticket);
        model.addAttribute("body", "/admin/tickets/add-ticket");
        return "admin/layout/main";
    }
}
