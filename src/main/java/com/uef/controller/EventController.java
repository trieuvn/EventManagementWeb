package com.uef.controller;

import com.uef.model.EVENT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventController {

    // Mô phỏng dữ liệu sự kiện
    private List<EVENT> events = new ArrayList<>();

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
            .collect(Collectors.toList());

        List<EVENT> filteredEvents = new ArrayList<>(events);

        if (keyword != null && !keyword.trim().isEmpty()) {
            filteredEvents = filteredEvents.stream()
                .filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        }

        if (category != null && !category.isEmpty()) {
            filteredEvents = filteredEvents.stream()
                .filter(e -> e.getType().equals(category))
                .collect(Collectors.toList());
        }

        model.addAttribute("events", filteredEvents);
        model.addAttribute("categories", categories);
        model.addAttribute("body", "/WEB-INF/views/event/list.jsp");
       

        return "layout/main";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("body", "/WEB-INF/views/dashboard/login.jsp");
        return "layout/main";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               RedirectAttributes ra) {
        // Mô phỏng xác thực người dùng
        if ("user".equals(username) && "password".equals(password)) {
            ra.addFlashAttribute("msg", "Đăng nhập thành công!");
            return "redirect:/";
        } else {
            ra.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("body", "/WEB-INF/views/dashboard/signup.jsp");
        return "layout/main";
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

        // Mô phỏng đăng ký thành công
        event.setSlots(event.getSlots() - 1);
        ra.addFlashAttribute("msg", "Đăng ký thành công! Mã xác nhận: " + generateConfirmationCode());
        return "redirect:/";
    }

    private String generateConfirmationCode() {
        return "CONF-" + (int)(Math.random() * 10000);
    }
}
