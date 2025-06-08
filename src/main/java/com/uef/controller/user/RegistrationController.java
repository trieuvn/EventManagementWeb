package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.EventService;
import jakarta.persistence.EntityManager;
import com.uef.service.ParticipantService;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Random;

@Controller
@RequestMapping("/user/registration")
public class RegistrationController {
    @Autowired
    private ParticipantService participantService;
    
    @Autowired
    private EventService eventService;
    
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/register/{eventId}")
    public String showRegisterForm(@PathVariable("eventId") int eventId, Model model, HttpSession session) {
        USER user = (USER) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";
        model.addAttribute("eventId", eventId);
        return "user/registration/form";
    }

    @PostMapping("/register/{eventId}")
    public String registerForEvent(@PathVariable("eventId") int eventId, HttpSession session, Model model) {
        USER user = (USER) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";

        // Simulate finding a ticket (in practice, check availability and deadline)
        TICKET ticket = new TICKET();
        ticket.setEvent(eventService.getById(eventId)); // Assuming eventService is autowired
        ticket.setConfirmCode(new Random().nextInt(10000)); // Generate confirmation code
        entityManager.persist(ticket); // Persist ticket (requires EntityManager)

        PARTICIPANT participant = new PARTICIPANT();
        participant.setUser(user);
        participant.setTicket(ticket);
        participant.setStatus(0); // Pending
        participantService.set(participant);

        model.addAttribute("confirmCode", ticket.getConfirmCode());
        return "user/registration/confirm";
    }

    @PostMapping("/checkin/{id}")
    public String checkIn(@PathVariable("id") int id, @RequestParam int confirmCode, Model model) {
        participantService.confirmRegistration(id, confirmCode);
        model.addAttribute("message", "Check-in successful");
        return "user/registration/checkin";
    }

    @PostMapping("/cancel/{id}")
    public String cancelRegistration(@PathVariable("id") int id, Model model) {
        participantService.cancelRegistration(id);
        model.addAttribute("message", "Registration canceled");
        return "redirect:/user/profile";
    }
}