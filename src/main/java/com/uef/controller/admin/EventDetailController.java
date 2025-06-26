/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.admin;

import com.uef.model.EVENT;
import com.uef.model.ORGANIZER;
import com.uef.model.TAG;
import com.uef.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/events/detail")
public class EventDetailController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public String viewEventDetail(@PathVariable int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            model.addAttribute("event", event);
            List<TAG> tags = event.getTags();
            model.addAttribute("tags", tags);
            ORGANIZER organizer = event.getOrganizer();
            model.addAttribute("organizer", organizer);
            List<TAG> eventTags = event.getTags();
            model.addAttribute("eventTags", eventTags);
            return "admin/event-detail";
        }
        return "redirect:/admin/events/list";
    }
}
