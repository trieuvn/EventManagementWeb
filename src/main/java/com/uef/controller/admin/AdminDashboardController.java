package com.uef.controller.admin;

import com.uef.model.CATEGORY;
import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.service.CategoryService;
import com.uef.service.EventService;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    @Autowired
    private EventService eventService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<EVENT> events = eventService.getAll();
        model.addAttribute("totalEvents", events.size());

        List<CATEGORY> categories = categoryService.getAll();
        model.addAttribute("totalCategories", categories.size());

        List<PARTICIPANT> participants = participantService.getAll();
        model.addAttribute("totalParticipants", participants.size());

        List<TICKET> tickets = ticketService.getAll();
        model.addAttribute("totalTickets", tickets.size());

        Map<String, Long> eventStatusCount = new HashMap<>();
        long upcomingCount = 0;
        long ongoingCount = 0;
        long completedCount = 0;

        for (EVENT event : events) {
            List<TICKET> eventTickets = ticketService.getByEvent(event.getId());
            if (eventTickets.isEmpty()) {
                continue;
            }

            boolean allUpcoming = eventTickets.stream().allMatch(t -> t.getStatus() == 0);
            boolean allCompleted = eventTickets.stream().allMatch(t -> t.getStatus() == 2);
            boolean hasOngoing = eventTickets.stream().anyMatch(t -> t.getStatus() == 1);

            if (allUpcoming) {
                upcomingCount++;
            } else if (allCompleted) {
                completedCount++;
            } else if (hasOngoing) {
                ongoingCount++;
            }
        }

        eventStatusCount.put("upcoming", upcomingCount);
        eventStatusCount.put("ongoing", ongoingCount);
        eventStatusCount.put("completed", completedCount);
        model.addAttribute("eventStatusCount", eventStatusCount);

        return "admin/dashboard";
    }
}