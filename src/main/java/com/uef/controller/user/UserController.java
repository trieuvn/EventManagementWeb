package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("body", "/WEB-INF/views/layout/introduction.jsp?login=true");
        return "layout/main";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model, RedirectAttributes ra) {
        logger.info("Login attempt for email: {}", email);
        if (userService.authenticate(email, password)) {
            session.setAttribute("user", userService.getByEmail(email));
            ra.addFlashAttribute("msg", "Đăng nhập thành công!");
            return "redirect:/";
        }
        logger.warn("Login failed for email: {}", email);
        ra.addFlashAttribute("error", "Email hoặc mật khẩu không đúng.");
        return "redirect:/?login=true";
    }
    

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("userForm", new USER());
        return "redirect:/?signup=true";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("userForm") USER user, BindingResult result, RedirectAttributes ra) {
        logger.info("Received signup request for email: {}", user.getEmail());

        if (result.hasErrors()) {
            logger.error("Validation errors: {}", result.getAllErrors());
            ra.addFlashAttribute("error", "Vui lòng kiểm tra lại thông tin.");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);
            ra.addFlashAttribute("userForm", user);
            return "redirect:/?signup=true";
        }

        try {
            boolean exists;
            if (userService.getByEmail(user.getEmail()) == null)    exists = false;
            else exists = true;
            if (exists) {
                logger.warn("Email already exists: {}", user.getEmail());
                ra.addFlashAttribute("error", "Email đã được đăng ký.");
                return "redirect:/?signup=true";
            }
            userService.set(user);
            logger.info("User registered successfully: {}", user.getEmail());
            ra.addFlashAttribute("msg", "Đăng ký tài khoản thành công!");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Error during signup process: ", e);
            ra.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại.");
            return "redirect:/?signup=true";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPassword(Model model) {
        // Placeholder: Bạn có thể thêm logic xử lý quên mật khẩu (ví dụ: gửi email reset)
        model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp"); // Tạo file này nếu cần
        return "layout/main";
    }
}