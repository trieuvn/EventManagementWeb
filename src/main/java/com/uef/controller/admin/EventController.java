package com.uef.controller.admin;

import com.uef.model.CHANGE;
import com.uef.model.EVENT;
import com.uef.model.ORGANIZER;
import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.ChangeService;
import com.uef.service.EventService;
import com.uef.service.OrganizerService;
import com.uef.service.ParticipantService;
import com.uef.service.UserService;
import jakarta.validation.Valid;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerService organizerService;
    
    @Autowired
    private ChangeService changeService;
    
    @Autowired
    private UserService userService;

    @GetMapping
    public String listEvents(Model model) {
        // Fetch event list
        // TODO: Replace with actual call to eventService.getAllEvents()
        List<EVENT> eventList;
        eventList = eventService.getAll();
        // Example data structure (replace with service call)
        // eventList.add(new Event("Spring Boot Workshop", "2025-07-10", "ĐH Bách Khoa", "Chuẩn bị", "upcoming"));
        // eventList.add(new Event("Hackathon 2025", "2025-06-28", "Saigon Innovation Hub", "Đang diễn ra", "ongoing"));
        model.addAttribute("eventList", eventList);

        // Fetch guest list
        // TODO: Replace with actual call to guestService.getAllGuests()
        List<ORGANIZER> guestList;
        guestList = organizerService.getAll();
        model.addAttribute("guestList", guestList);

        List<USER> topStudents;
        topStudents = userService.getAll();
        topStudents.sort(Comparator.comparingLong(USER::getTotalParticipated).reversed());
        
        model.addAttribute("topStudents", topStudents);

        // Fetch notification list
        // TODO: Replace with actual call to notificationService.getNotifications()
        List<CHANGE> changes = new ArrayList<>();
        changes = changeService.getAll();
        model.addAttribute("changes", changes);

        // Fetch stats for quick stats section
        // TODO: Replace with actual call to eventService.getUpcomingCount()
        model.addAttribute("upcomingCount", 7); // Example value
        // TODO: Replace with actual call to eventService.getOngoingCount()
        model.addAttribute("ongoingCount", 2); // Example value
        // TODO: Replace with actual call to eventService.getEndedCount()
        model.addAttribute("endedCount", 12); // Example value
        // TODO: Replace with actual call to notificationService.getNotificationCount()
        model.addAttribute("notificationCount", 3); // Example value
        
        
        model.addAttribute("body", "/WEB-INF/views/admin/event/event-management.jpg");
        return "admin/event/event-management";
    }

    @PostMapping("/save/{id}")
    public String saveEditedEvent(
            @PathVariable("id") int eventId,
            @Valid @ModelAttribute("event") EVENT event,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "redirect:/admin/events/edit/"+eventId; // Return to form with errors
        }

        // Ensure the event ID is set
        event.setId(eventId);

        // Handle image upload
        try {
            if (!imageFile.isEmpty()) {
                // TODO: Validate image file (size, type, etc.) if needed
                event.setImage(imageFile.getBytes());
            } else {
                // TODO: If no new image is uploaded, retain the existing image
                // Event existingEvent = eventService.getEventById(eventId);
                // if (existingEvent != null && existingEvent.getImage() != null) {
                //     event.setImage(existingEvent.getImage());
                // }
            }
        } catch (Exception e) {
            // TODO: Handle file upload errors (e.g., add error message to model)
            model.addAttribute("error", "Lỗi khi tải lên hình ảnh: " + e.getMessage());
            return "redirect:/admin/events/edit/"+eventId;
        }

        // TODO: Save the updated event to the database
        // eventService.saveEvent(event);
        eventService.set(event);
        // Redirect to dashboard after successful save
        return "redirect:/admin/events/edit/"+eventId;
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id, RedirectAttributes redirectAttributes) {
        EVENT event = eventService.getById(id);
        if (event != null) {
            try{
                eventService.delete(event);
            }catch(IllegalStateException e){
                redirectAttributes.addFlashAttribute("message", e.getMessage());
                return "redirect:/admin/events";
            }
        }
        
        CHANGE change = new CHANGE();
        change.setDate(Date.valueOf(LocalDate.now()));
        change.setTime(Time.valueOf(LocalTime.now()));
        change.setDescription("Đã xóa " + event.getName());
        change.setSubject("Đã xóa " + event.getName());
        changeService.set(change);
        return "redirect:/admin/events";
    }

    @PostMapping("/create")
    public String createEvent() {
        // TODO: Implement logic to redirect to event creation form
        EVENT event = new EVENT();
        event.setTarget("Sinh viên UEF");
        event.setContactInfo("trieu123ok@gmail.com");
        event.setName("Sự kiện mới");
        event.setType("Hybrid");
        eventService.set(event);
        int id = event.getId();
        return "redirect:/admin/events/edit/" + String.valueOf(id);
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