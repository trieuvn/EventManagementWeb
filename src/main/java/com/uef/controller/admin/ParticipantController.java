package com.uef.controller.admin;

import com.uef.annotation.RoleRequired;
import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.EventService;
import com.uef.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    @RoleRequired({"admin"})
    @GetMapping("/{eventId}")
    public String listParticipants(@PathVariable int eventId, Model model) {
        model.addAttribute("participants", participantService.getAllByEvent(eventId));
        model.addAttribute("eventId", eventId);
        model.addAttribute("event", eventService.getById(eventId));
        model.addAttribute("body", "admin/participants");
        return "admin/layout/main";
    }

    @RoleRequired({"admin"})
    @PostMapping("/confirm/{userEmail}/{ticketId}")
    public String confirmParticipant(@PathVariable String userEmail, @PathVariable int ticketId, @RequestParam int eventId) {
        USER user = new USER();
        user.setEmail(userEmail);
        TICKET ticket = new TICKET();
        ticket.setId(ticketId);
        PARTICIPANT participant = new PARTICIPANT(user, ticket, 1, 0, null);
        participantService.setRate(user, ticket, 0, null); // Cập nhật trạng thái
        return "redirect:/admin/participants/" + eventId;
    }

    @RoleRequired({"admin"})
    @PostMapping("/cancel/{userEmail}/{ticketId}")
    public String cancelParticipant(@PathVariable String userEmail, @PathVariable int ticketId, @RequestParam int eventId) {
        USER user = new USER();
        user.setEmail(userEmail);
        TICKET ticket = new TICKET();
        ticket.setId(ticketId);
        PARTICIPANT participant = new PARTICIPANT(user, ticket, 0, 0, null);
        participantService.delete(participant);
        return "redirect:/admin/participants/" + eventId;
    }
}
