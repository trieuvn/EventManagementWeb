/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.admin;

import com.uef.model.*;
import com.uef.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("adminParticipantController")
@RequestMapping("admin/participants")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    @GetMapping("/event/{eventId}")
    public String listParticipants(@PathVariable int eventId, Model model) {
        model.addAttribute("participants", participantService.getAllByEvent(eventId));
        model.addAttribute("eventId", eventId);
        return "admin/participants/list";
    }


    // Placeholder method to get eventId (implement based on your request handling)
    private int getEventIdFromRequest() {
        // Implement logic to retrieve eventId from session or request
        return 1; // Default value, replace with actual logic
    }
}
