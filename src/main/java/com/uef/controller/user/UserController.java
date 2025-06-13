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

   

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (userService.authenticate(email, password)) {
            session.setAttribute("user", userService.getByEmail(email));
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

    
}