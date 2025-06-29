/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.user;

import com.uef.annotation.RoleRequired;
import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.CategoryService;
import com.uef.service.EventService;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import com.uef.utils.Email;
import com.uef.utils.Image;
import com.uef.utils.Map;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private EventService eventService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ParticipantService participantService;
    @RoleRequired({"user", "admin"})
    @PostMapping("/event/save-ticket-id")
    @ResponseBody
    public ResponseEntity<String> saveTicketIdToSession(@RequestParam("ticketId") int ticketId, HttpSession session) {
        session.setAttribute("selectedTicketId", ticketId);
        return ResponseEntity.ok("Saved successfully");
    }
    @RoleRequired({"user", "admin"})
    @GetMapping("/show-map")
    public String showMap(Model model,
            @RequestParam(required = false) Double startLat,
            @RequestParam(required = false) Double startLng,
            @RequestParam Double endLat,
            @RequestParam Double endLng,
            @RequestParam(required = false) String startName,
            @RequestParam(required = false) String endName) throws Exception {
        return Map.showMap(model, startLat, startLng, endLat, endLng, startName, endName);
    }
    @RoleRequired({"user", "admin"})
    @PostMapping("/register/{ticket_id}")
    public String registerTicket(@PathVariable int ticket_id, RedirectAttributes ra, HttpSession session) {
        USER user = (USER) session.getAttribute("user");
        TICKET ticket = ticketService.getById(ticket_id);
        if (ticket == null) {
            ra.addFlashAttribute("message", "Sự kiện không tồn tại.");
            return "redirect:/events"; 
        }

        List<PARTICIPANT> participants = participantService.getByUser(user);
        boolean alreadyRegistered = participants.stream()
                .anyMatch(p -> p.getTicket().getId() == ticket_id);

        if (alreadyRegistered) {
            ra.addFlashAttribute("message", "Bạn đã đăng ký sự kiện này rồi!");
            return "redirect:/event/" + ticket.getEvent().getId();
        }
        
        if (ticket.getRegDeadline().before(Date.valueOf(LocalDate.now()))) {
            ra.addFlashAttribute("message", "Sự kiện đã đóng đăng ký!");
            return "redirect:/event/" + ticket.getEvent().getId();
        }

        if (ticket.getSlots() - ticket.getParticipants().size() <= 0){
            ra.addFlashAttribute("message", "Sự kiện đã đầy!");
            return "redirect:/event/" + ticket.getEvent().getId();
        }
        
        
        PARTICIPANT participant = new PARTICIPANT();
        participant.setUser(user);
        participant.setTicket(ticket);
        participant.setStatus(0);
        participant.setRate(5);

        boolean result = participantService.set(participant);
        if (!result) {
            ra.addFlashAttribute("message", "Có lỗi xảy ra, vui lòng thử lại.");
        } else {
            
            Email.sendEmail(ticket.getEvent().getName(), user.getEmail(), "Cảm ơn bạn đã đăng ký tham gia sự kiện " + ticket.getEvent().getName() 
                                                                            + "\nVé: " + ticket.getName(), null);
            ra.addFlashAttribute("message", "Đăng ký sự kiện thành công.");
        }

        return "redirect:/event/" + ticket.getEvent().getId();
    }

}
