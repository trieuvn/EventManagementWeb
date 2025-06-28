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

    @RequestMapping({"/edit/{id}", "/view/{id}"})
    public String showEditForm(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            model.addAttribute("event", event);
            return "admin/event/event-detail";
        }
        return "admin/event/event-management";
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
