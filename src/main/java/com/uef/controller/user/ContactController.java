package com.uef.controller.user;

import com.uef.model.USER;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
@Controller
public class ContactController {

    @GetMapping("/contacts")
    public String showContact(Model model, HttpSession session) {
        USER user = (USER) session.getAttribute("user");
        model.addAttribute("userForm", new USER());
        model.addAttribute("user", user);
        model.addAttribute("body", "/WEB-INF/views/layout/contacts.jsp");
        if (user == null) {
            return "layout/main";
        }
        else 
            return "layout/main2";
    }
}
