package com.uef.controller.admin;

import com.uef.model.*;
import com.uef.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("adminEventController")
@RequestMapping("admin/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "admin/events/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("event", new EVENT());
        return "admin/events/form";
    }

    @PostMapping("/add")
    public String addEvent(@ModelAttribute EVENT event) {
        eventService.save(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("event", eventService.findById(id));
        return "admin/events/form";
    }

    @PostMapping("/update")
    public String updateEvent(@ModelAttribute EVENT event) {
        eventService.save(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id) {
        eventService.deleteById(id);
        return "redirect:/admin/events";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam int id, @RequestParam String status) {
        eventService.updateStatus(id, status);
        return "redirect:/admin/events";
    }
}