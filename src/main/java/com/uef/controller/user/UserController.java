package com.uef.controller.user;

import com.uef.model.USER;
import com.uef.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        USER user = userService.authenticate(email, password);
        if (user != null) {
            // Lưu thông tin user vào session (giả lập)
            model.addAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "user/login";
        }
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new USER());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute USER user, Model model) {
        try {
            user.setRole(1); // Mặc định là user
            boolean success = userService.set(user);
            if (success) {
                return "redirect:/user/login";
            } else {
                model.addAttribute("error", "Registration failed");
                return "user/register";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "user/register";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        // Giả lập lấy user từ session
        USER user = userService.getByEmail("nguyenvana@uef.edu.vn"); // Thay bằng logic session thực tế
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute USER user, Model model) {
        try {
            boolean success = userService.set(user);
            if (success) {
                return "redirect:/user/profile";
            } else {
                model.addAttribute("error", "Update failed");
                return "user/profile";
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "user/profile";
        }
    }
}