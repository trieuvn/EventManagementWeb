package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.OptionalInt;

@Controller("userEventController")
@RequestMapping("/user/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public String listEvents(@RequestParam(required = false) String type, Model model) {
        if ("upcoming".equals(type)) {
            model.addAttribute("events", eventService.findUpcomingEvents());
        } else if ("past".equals(type)) {
            model.addAttribute("events", eventService.findPastEvents());
        } else {
            model.addAttribute("events", eventService.findAll());
        }
        return "user/events/list";
    }

    @GetMapping("/search")
    public String searchEvents(@RequestParam(required = false) String name, @RequestParam(required = false) String category, @RequestParam(required = false) Date date, Model model) {
        model.addAttribute("events", eventService.searchEvents(name, category, date));
        return "user/events/list";
    }

    @GetMapping("/{id}")
    public String viewEventDetails(@PathVariable("id") int id, Model model) {
        EVENT event = eventService.findById(id);
        if (event != null) {
            int totalSlots = event.getTickets().stream().mapToInt(t -> t.getSlots()).sum();
            model.addAttribute("event", event);
            model.addAttribute("totalSlots", totalSlots); // Add precomputed total slots
            model.addAttribute("firstTicketDeadline", event.getTickets().isEmpty() ? null : event.getTickets().get(0).getRegDeadline());
            model.addAttribute("firstTicketPrice", event.getTickets().isEmpty() ? 0 : event.getTickets().get(0).getPrice());
        }
        return "user/events/details";
    }
}