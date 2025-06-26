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
import java.util.Set;
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
            RedirectAttributes ra,
            HttpSession session) {
        USER user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("user", user); // Lưu vào session
            ra.addFlashAttribute("msg", "Đăng nhập thành công!");
            return "redirect:/";
        } else {
            ra.addFlashAttribute("error", "Email hoặc mật khẩu không đúng.");
            return "redirect:/?login=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/";
    }
    
    
    @PostMapping("/signup")
    public String processSignup(
            @Valid @ModelAttribute("userForm") USER user,
            BindingResult result,
            RedirectAttributes ra) {
        if (result.hasErrors()) {
            ra.addFlashAttribute("error", "Vui lòng kiểm tra lại thông tin.");
            ra.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);
            ra.addFlashAttribute("userForm", user);
            return "redirect:/?signup=true";
        }

        try {
            USER exists = userService.getByEmail(user.getEmail());
            if (exists != null) {
                ra.addFlashAttribute("error", "Email đã được đăng ký.");
                ra.addFlashAttribute("userForm", user);
                return "redirect:/?signup=true";
            }

            userService.set(user);
            ra.addFlashAttribute("msg", "Đăng ký tài khoản thành công!");
            return "redirect:/?login=true";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại.");
            return "redirect:/?signup=true";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPassword(Model model, 
                                    @RequestParam(value = "email", required = false) String email,
                                    HttpSession session) {
        // Kiểm tra email có được cung cấp hay không
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("error", "Please provide an email address.");
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        // Kiểm tra email có tồn tại trong hệ thống hay không
        USER user = userService.getByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email not found.");
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        // Tạo OTP và lưu vào session
        String otp = generateConfirmationCode();
        session.setAttribute("otp", otp);
        session.setAttribute("resetEmail", email); // Lưu email để sử dụng trong reset password

        // Gửi OTP qua email
//        EmailUtils.EmailResult result = EmailUtils.sendEmail(
//            "Password Reset",
//            email,
//            "Please use the following verification code to reset your password.",
//            otp
//        );
//
//        if (result.isSuccess()) {
//            model.addAttribute("message", result.getMessage());
//        } else {
//            model.addAttribute("error", result.getMessage());
//        }

        model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
        return "layout/main";
    }
    
    @PostMapping("/save-password")
    public String resetPassword(Model model, 
                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "password", required = false) String password,
                                    @RequestParam(value = "comfirm-password", required = false) String confirmPassword,
                                    @RequestParam(value = "otp", required = false) String submittedOtp,
                                    HttpSession session, @Valid USER user, BindingResult bindingResult) {
        String sessionEmail = (String) session.getAttribute("resetEmail");
        String sessionOtp = (String) session.getAttribute("otp");

        if (email == null || !email.equals(sessionEmail)) {
            model.addAttribute("error", "Invalid email or session expired.");
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        if (submittedOtp == null || !submittedOtp.equals(sessionOtp)) {
            model.addAttribute("error", "Invalid OTP.");
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        // Kiểm tra validation của USER object
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Invalid input data: " + bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        // Cập nhật mật khẩu
        USER existingUser = userService.getByEmail(email);
        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            model.addAttribute("body", "/WEB-INF/views/layout/forgot-password.jsp");
            return "layout/main";
        }

        existingUser.setPassword(password);
        userService.set(existingUser);

        // Xóa OTP và email khỏi session
        session.removeAttribute("otp");
        session.removeAttribute("resetEmail");

        model.addAttribute("message", "Password reset successfully!");
        model.addAttribute("body", "/WEB-INF/views/layout/login.jsp");
        return "layout/main";
    }

    private String generateConfirmationCode() {
        return "CONF-" + (int) (Math.random() * 10000);
    }

}
