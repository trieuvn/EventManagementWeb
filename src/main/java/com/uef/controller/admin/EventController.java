package com.uef.controller.admin;

import com.uef.model.EVENT;
import com.uef.service.EventService;
import com.uef.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerService organizerService;

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAll());
        return "admin/events";
    }

    @GetMapping("/add")
    public String addEventForm(Model model) {
        model.addAttribute("event", new EVENT());
        model.addAttribute("organizers", organizerService.getAll());
        return "admin/edit_event";
    }

    @GetMapping("/edit/{id}")
    public String editEventForm(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event == null) {
            return "redirect:/admin/events";
        }
        model.addAttribute("event", event);
        model.addAttribute("organizers", organizerService.getAll());
        return "admin/edit_event";
    }

    @PostMapping({"/add", "/update"})
    public String saveEvent(@ModelAttribute EVENT event) {
        eventService.set(event);
        return "redirect:/admin/events";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            eventService.delete(event);
        }
        return "redirect:/admin/events";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam String status) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            event.setType(status);
            eventService.setStatus(event);
        }
        return "redirect:/admin/events";
    }
    @GetMapping("/details")
    public String eventDetails(Model model)
    {
        model.addAttribute("event", eventService.getAll());
        return "admin/events/details";
    }
}