/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.admin;

/**
 *
 * @author Administrator
 */
import com.uef.model.PARTICIPANT;
import com.uef.model.EVENT;
import com.uef.service.ParticipantService;
import com.uef.service.EventService; // Đảm bảo bạn đã tạo EventService để lấy danh sách sự kiện
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/participants")
public class AdminParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EventService eventService;

    // Lấy danh sách participant theo eventId nếu có
    @GetMapping
    public String viewParticipants(@RequestParam(required = false) Integer eventId, Model model) {
        List<PARTICIPANT> participants;
        if (eventId != null) {
            participants = participantService.getAllByEvent(eventId);
        } else {
            participants = participantService.getAll();
        }

        List<EVENT> events = eventService.getAll(); // Dùng cho dropdown lọc

        model.addAttribute("participants", participants);
        model.addAttribute("events", events);
        model.addAttribute("eventId", eventId); // để giữ selected trong dropdown
        return "admin/participants/list";
    }

    // Xác nhận participant (status = 1)
    @PostMapping("/confirm")
    public String confirmParticipant(@RequestParam int ticketId, @RequestParam String userEmail) {
        PARTICIPANT participant = participantService.getById(ticketId, userEmail);
        if (participant != null) {
            participant.setStatus(1);
            participantService.set(participant);
            return "redirect:/admin/participants?eventId=" + participant.getTicket().getEvent().getId();
        }
        return "redirect:/admin/participants";
    }

    // Hủy xác nhận participant (status = 0)
    @PostMapping("/cancel")
    public String cancelParticipant(@RequestParam int ticketId, @RequestParam String userEmail) {
        PARTICIPANT participant = participantService.getById(ticketId, userEmail);
        if (participant != null) {
            participant.setStatus(0);
            participantService.set(participant);
            return "redirect:/admin/participants?eventId=" + participant.getTicket().getEvent().getId();
        }
        return "redirect:/admin/participants";
    }
}
