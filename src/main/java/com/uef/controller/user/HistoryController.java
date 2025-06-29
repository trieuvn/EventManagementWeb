package com.uef.controller.user;

import com.uef.model.PARTICIPANT;
import com.uef.model.USER;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HistoryController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/history")
    public String history(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            Model model, HttpSession session) {

        USER user = (USER) session.getAttribute("user");

        List<PARTICIPANT> participants = participantService.getByUser(user);

        // Lấy danh sách loại vé (độc nhất)
        Set<String> ticketTypes = participants.stream()
                .map(p -> p.getTicket().getEvent().getType())
                .filter(typeStr -> typeStr != null && !typeStr.isEmpty())
                .collect(Collectors.toSet());

        // Áp dụng bộ lọc
        List<PARTICIPANT> filtered = participants.stream()
                .filter(p -> type == null || type.isEmpty() || p.getTicket().getEvent().getType().equalsIgnoreCase(type))
                .filter(p -> {
                    LocalDate eventDate = p.getTicket().getDate().toLocalDate();
                    return (fromDate == null || !eventDate.isBefore(fromDate)) &&
                           (toDate == null || !eventDate.isAfter(toDate));
                })
                .collect(Collectors.toList());

        model.addAttribute("participants", filtered);
        model.addAttribute("ticketTypes", ticketTypes); // truyền vào JSP
        model.addAttribute("userForm", new USER());
        model.addAttribute("user", user);
        model.addAttribute("body", "/WEB-INF/views/user/events/history.jsp");

        return "layout/main2";
    }

    @GetMapping("/user/qr")
    public String showQRCode(@RequestParam("ticket_id") int ticketId, Model model, HttpSession session) {
        USER user = (USER) session.getAttribute("user");

        PARTICIPANT p = participantService.getById(ticketId, user.getEmail());

        if (p == null) {
            model.addAttribute("error", "Không tìm thấy vé.");
            return "redirect:/history";
        }

        try {
            String qrBase64 = p.getQRCode();
            model.addAttribute("qrBase64", qrBase64);
            return "user/QRCode";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi khi tạo mã QR.");
            return "redirect:/history";
        }
    }

    @GetMapping("/user/rate")
    @ResponseBody
    public ResponseEntity<String> rate(
            @RequestParam("ticket_id") int ticketId,
            @RequestParam("rate") int rate,
            HttpSession session) {

        USER user = (USER) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        boolean updated = participantService.setById(ticketId, user.getEmail(), 1, rate, null);
        if (updated) {
            return ResponseEntity.ok("Đánh giá thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật đánh giá");
        }
    }
}
