package com.uef.controller.admin;

import com.uef.model.EVENT;
import com.uef.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.validation.BindingResult;

@Controller("admin")
@RequestMapping("/admin/events")
public class EventManagementController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public String listEvents(Model model) {
        List<EVENT> events = eventService.getAll();
        model.addAttribute("events", events);
        return "admin/event-list";
}

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("event", new EVENT());
        return "admin/form";
    }

    @PostMapping("/add")
    public String addEvent(@Valid @ModelAttribute EVENT event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/form";
        }
        try {
            eventService.set(event);
            return "redirect:/admin/events/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            model.addAttribute("event", event);
            return "admin/event-form";
        }
        return "redirect:/admin/events/list";
    }

    @PostMapping("/update")
    public String updateEvent(@Valid @ModelAttribute EVENT event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/form";
        }
        try {
            eventService.set(event);
            return "redirect:/admin/events/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            try {
                eventService.delete(event);
                return "redirect:/admin/events/list";
            } catch (IllegalStateException e) {
                model.addAttribute("error", e.getMessage());
                return "admin/list";
            }
        }
        return "redirect:/admin/events/list";
    }

    @GetMapping("/update-status/{id}")
    public String showUpdateStatusForm(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            model.addAttribute("event", event);
            return "admin/event-status-form";
        }
        return "redirect:/admin/events/list";
    }

    @PostMapping("/update-status")
    public String updateStatus(@ModelAttribute EVENT event, Model model) {
        try {
            eventService.setStatus(event);
            return "redirect:/admin/events/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/event-status-form";
        }
    }
    
    
}
