package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.EventService;
import com.uef.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("userEventController")
public class EventController {
    @Autowired
    private EventService eventService;

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private List<EVENT> events = eventService.getAll();

    public EventController() {
        //events.add(new EVENT(1, "Hội thảo Công nghệ 2025", "Sự kiện về công nghệ", "2025-07-01", 2, "Công nghệ", "2025-06-25", 0, "email@example.com", 50, "Mở"));
        //events.add(new EVENT(2, "Hòa nhạc Mùa hè", "Buổi hòa nhạc ngoài trời", "2025-08-15", 1, "Âm nhạc", "2025-08-01", 100, "music@example.com", 100, "Mở"));
        //events.add(new EVENT(3, "Triển lãm Nghệ thuật", "Triển lãm các tác phẩm nghệ thuật", "2025-06-30", 3, "Nghệ thuật", "2025-06-20", 50, "art@example.com", 0, "Đóng"));
    }

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "category", required = false) String category) {
        List<String> categories = events.stream()
            .map(EVENT::getType)
            .distinct()
            .toList();
        List<EVENT> filteredEvents = new ArrayList<>(events);
        if (keyword != null && !keyword.trim().isEmpty()) {
            filteredEvents = filteredEvents.stream()
                .filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
        }
        if (category != null && !category.isEmpty()) {
            filteredEvents = filteredEvents.stream()
                .filter(e -> e.getType().equals(category))
                .toList();
        }
        model.addAttribute("events", filteredEvents);
        model.addAttribute("categories", categories);
        model.addAttribute("userForm", new USER());
        model.addAttribute("body", "/WEB-INF/views/user/events/list.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        return "layout/main";
    }
    
    @GetMapping("/about")
    public String aboutUs() {
        // Trả về tên view (about.jsp)
        return "event/about";
    }

    /* UPDATE LATER
    @PostMapping("/register")
    public String registerEvent(@RequestParam int eventId, RedirectAttributes ra) {
        EVENT event = events.stream().filter(e -> e.getId() == eventId).findFirst().orElse(null);
        if (event == null) {
            ra.addFlashAttribute("error", "Sự kiện không tồn tại.");
            return "redirect:/";
        }
        if (!"Mở".equalsIgnoreCase(event.getStatus())) {
            ra.addFlashAttribute("error", "Đăng ký đã đóng: Hạn đăng ký đã hết.");
            return "redirect:/";
        }
        if (event.getSlots() <= 0) {
            ra.addFlashAttribute("error", "Đăng ký thất bại: Sự kiện đã đầy.");
            return "redirect:/";
        }
        return "redirect:/";
    }*/

}