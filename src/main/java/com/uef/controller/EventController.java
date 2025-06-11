package com.uef.controller;

import com.uef.model.EVENT;
import com.uef.model.USER;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private List<EVENT> events = new ArrayList<>();
    private List<USER> users = new ArrayList<>();

    public EventController() {
        events.add(new EVENT(1, "Hội thảo Công nghệ 2025", "Sự kiện về công nghệ", "2025-07-01", 2, "Công nghệ", "2025-06-25", 0, "email@example.com", 50, "Mở"));
        events.add(new EVENT(2, "Hòa nhạc Mùa hè", "Buổi hòa nhạc ngoài trời", "2025-08-15", 1, "Âm nhạc", "2025-08-01", 100, "music@example.com", 100, "Mở"));
        events.add(new EVENT(3, "Triển lãm Nghệ thuật", "Triển lãm các tác phẩm nghệ thuật", "2025-06-30", 3, "Nghệ thuật", "2025-06-20", 50, "art@example.com", 0, "Đóng"));
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
        model.addAttribute("body", "/WEB-INF/views/event/list.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        return "layout/main";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("body", "/WEB-INF/views/dashboard/login.jsp");
        return "layout/main";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes ra) {
        USER matchedUser = users.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
            .findFirst()
            .orElse(null);
        if (matchedUser != null) {
            ra.addFlashAttribute("msg", "Đăng nhập thành công!");
            return "redirect:/";
        } else {
            ra.addFlashAttribute("error", "Email hoặc mật khẩu không đúng.");
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("userForm", new USER());
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("userForm") USER user, BindingResult result, RedirectAttributes ra) {
        logger.info("Received signup request for email: {}", user.getEmail());

        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
            ra.addFlashAttribute("error", "Vui lòng kiểm tra lại thông tin.");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);
            ra.addFlashAttribute("userForm", user);
            return "redirect:/";
        }

        try {
            boolean exists = users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));
            if (exists) {
                logger.warn("Email already exists: {}", user.getEmail());
                ra.addFlashAttribute("error", "Email đã được đăng ký.");
                return "redirect:/";
            }
            users.add(user);
            logger.info("User registered successfully: {}", user.getEmail());
            ra.addFlashAttribute("msg", "Đăng ký tài khoản thành công!");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Error during signup process: ", e);
            ra.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại.");
            return "redirect:/";
        }
    }

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
        event.setSlots(event.getSlots() - 1);
        ra.addFlashAttribute("msg", "Đăng ký thành công! Mã xác nhận: " + generateConfirmationCode());
        return "redirect:/";
    }

    private String generateConfirmationCode() {
        return "CONF-" + (int)(Math.random() * 10000);
    }
}