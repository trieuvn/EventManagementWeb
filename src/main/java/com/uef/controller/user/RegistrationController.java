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

@Controller("userRegistrationController")
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


}