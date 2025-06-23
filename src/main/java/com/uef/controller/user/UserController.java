package com.uef.controller.user;

import com.uef.model.*;
import com.uef.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import static org.hibernate.internal.CoreLogging.logger;
import static org.hibernate.internal.HEMLogging.logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("userUserController")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("body", "/WEB-INF/views/layout/introduction.jsp?login=true");
        return "layout/main";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
            @RequestParam String password,
            RedirectAttributes ra, HttpSession session) {
        if (userService.authenticate(email, password) != null) {
            USER user = userService.getByEmail(email);
            session.setAttribute("user", user);
            ra.addFlashAttribute("msg", "Đăng nhập thành công!");
            return "redirect:/";
        } else {
            ra.addFlashAttribute("error", "Email hoặc mật khẩu không đúng.");
            return "redirect:/?login=true";
        }
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("userForm", new USER());
        return "redirect:/?signup=true";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("userForm") USER user, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("error", "Vui lòng kiểm tra lại thông tin.");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);
            ra.addFlashAttribute("userForm", user);
            ra.addFlashAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
            return "redirect:/?signup=true";
        }

        try {
            USER exists = userService.getByEmail(user.getEmail());
            if (exists != null) {
                ra.addFlashAttribute("error", "Email đã được đăng ký.");
                return "redirect:/?signup=true";
            }
            //userService.set(exists);
            ra.addFlashAttribute("msg", "Đăng ký tài khoản thành công!");
            return "redirect:/login";
        } catch (Exception e) {
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
    
    private String generateConfirmationCode() {
        return "CONF-" + (int)(Math.random() * 10000);
    }

}
