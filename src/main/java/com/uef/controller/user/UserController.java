package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new USER());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute USER user, Model model) {
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already registered");
            return "user/register";
        }
        userService.save(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (userService.authenticate(email, password)) {
            session.setAttribute("user", userService.findByEmail(email));
            return "redirect:/user/events";
        }
        model.addAttribute("error", "Invalid email or password");
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        USER user = (USER) session.getAttribute("user");
        if (user == null) return "redirect:/user/login";
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute USER user, HttpSession session) {
        USER currentUser = (USER) session.getAttribute("user");
        if (currentUser != null) {
            user.setId(currentUser.getId());
            userService.save(user);
            session.setAttribute("user", user);
        }
        return "redirect:/user/profile";
    }
}