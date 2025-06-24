package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.CategoryService;
import com.uef.service.EventService;
import com.uef.service.TicketService;
import com.uef.service.UserService;
import com.uef.utils.Image;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("userEventController")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TicketService ticketService;

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping("/")
    public String home(Model model,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "date", required = false) Date date,
            HttpSession session) {
        // Sửa lỗi logic: sử dụng keyword cho name, bỏ category để tránh lỗi kiểu dữ liệu
        List<String> categories = categoryService.getAll().stream()
                .map(CATEGORY::getName)
                .collect(Collectors.toList());
        List<EVENT> filteredEvents = eventService.searchEvents(keyword, category, date);
        model.addAttribute("events", filteredEvents);
        model.addAttribute("categories", categories);
        model.addAttribute("userForm", new USER());
        model.addAttribute("hero", "/WEB-INF/views/layout/hero.jsp");
        model.addAttribute("body", "/WEB-INF/views/user/events/list.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        model.addAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
        return "layout/main";
    }

    @RequestMapping("/event/{id}")
    public String getEventDetails(@PathVariable int id, Model model) {
        // Fetch event by ID
        EVENT event = eventService.getById(id);
        if (event == null) {
            return "error/404"; // Return 404 page if event not found
        }
        ORGANIZER organizer = event.getOrganizer();
        List<TICKET> tickets = event.getTickets();

        // Calculate total slots
        int totalSlots = 50;

        // Add attributes to model
        model.addAttribute("body", "/WEB-INF/views/user/events/details.jsp");
        model.addAttribute("event", event);
        //Ảnh dạng string base64 để up ảnh lên jsp
        model.addAttribute("event_image", Image.convertByteToBase64(event.getImage()));
        model.addAttribute("organizer", organizer);
        //Ảnh dạng string base64 để up ảnh lên jsp
        model.addAttribute("organizer_avatar", Image.convertByteToBase64(organizer.getAvatar()));
        model.addAttribute("tickets", tickets);
        model.addAttribute("totalSlots", totalSlots);
        model.addAttribute("userForm", new USER());
        //model.addAttribute("body", "/WEB-INF/views/user/events/details.jsp");
        //model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        //model.addAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
        return "layout/main";
    }

    @GetMapping("/about")
    public String aboutUs(Model model) {
        model.addAttribute("userForm", new USER());
        model.addAttribute("body", "/WEB-INF/views/user/events/about.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        model.addAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
        return "layout/main";
    }


    /*@PostMapping("/register")
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
