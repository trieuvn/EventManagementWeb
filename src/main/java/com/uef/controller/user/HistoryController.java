package com.uef.controller.user;

import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.CategoryService;
import com.uef.service.EventService;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import com.uef.utils.Image;
import com.uef.utils.QRCode;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HistoryController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/history")
    public String history(Model model, HttpSession session) {
        USER user = (USER) session.getAttribute("user");
        List<PARTICIPANT> participants = participantService.getByUser(user);
        model.addAttribute("participants", participants);
        model.addAttribute("userForm", new USER());
        model.addAttribute("user", user);
        model.addAttribute("participants", participants);
        model.addAttribute("body", "/WEB-INF/views/user/events/history.jsp");
        return "layout/main2";
    }

    @GetMapping("/user/qr")
    public String showQRCode(@RequestParam("ticket_id") int ticketId, Model model, HttpSession session) {
        // Lấy user từ session
        USER user = (USER) session.getAttribute("user");

        PARTICIPANT p = participantService.getById(ticketId, user.getEmail());

        if (p == null) {
            model.addAttribute("error", "Không tìm thấy vé.");
            return "redirect:/history";
        }

        try {
            // Gọi phương thức tạo chuỗi QR base64
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
            HttpSession session
    ) {
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

//    @GetMapping("/detail")
//    public String historyDetail(@RequestParam("ticket_id") int ticket_id, Model model, HttpSession session) throws IOException {
//        USER user = (USER) session.getAttribute("user");
//        TICKET ticket = ticketService.getById(ticket_id);
//
//        PARTICIPANT participant = new PARTICIPANT();
//        participant.setUser(user);
//        participant.setTicket(ticket);
//        participant.setStatus(0);
//
//        model.addAttribute("participant", participant);
//        model.addAttribute("ticket", ticket);
//        model.addAttribute("event", ticket.getEvent());
//        model.addAttribute("event_image", Image.convertByteToBase64(ticket.getEvent().getImage()));
//        model.addAttribute("qrcode", QRCode.convertFromStringToBase64String("ticket-" + ticket.getId()));
//        model.addAttribute("userForm", new USER());
//        model.addAttribute("body", "/WEB-INF/views/user/tickets/detail.jsp");
//
//        return "layout/main2";
//    }
}
