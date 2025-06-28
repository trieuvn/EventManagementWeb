package com.uef.controller.user;

import com.uef.annotation.RoleRequired;
import com.uef.model.PARTICIPANT;
import com.uef.model.USER;
import com.uef.service.ParticipantService;
import com.uef.service.UserService;
import com.uef.utils.Email;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("userUserController")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ParticipantService participantService;
    @RoleRequired({"user", "admin"})
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        USER user = (USER) session.getAttribute("user");
        List<PARTICIPANT> participants = participantService.getByUser(user);
        model.addAttribute("userForm", new USER());
        model.addAttribute("user", user);
        model.addAttribute("participants", participants);
        model.addAttribute("body", "/WEB-INF/views/user/profile.jsp");
        return "layout/main2";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                              @RequestParam String password,
                              RedirectAttributes ra,
                              HttpSession session) {
        USER user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            ra.addFlashAttribute("msg", "Đăng nhập thành công!");
            //admin
            if (user.getRole() == 0){
                return "redirect:/admin/events";
            }
            return "redirect:/";
        } else {
            ra.addFlashAttribute("error", "Email hoặc mật khẩu không đúng.");
            return "redirect:/?login=true";
        }
    }

    @PostMapping("/signup")
    public String processSignup(
            @Valid @ModelAttribute("userForm") USER user,
            BindingResult result,
            RedirectAttributes ra) {
        user.setRole(1);
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
    
    
    @PostMapping("/send-otp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendOtp(@RequestParam(value = "email", required = false) String email,
                                                      HttpSession session) {
        logger.info("Received /send-otp request with email: {}", email);
        Map<String, Object> response = new HashMap<>();

        if (email == null || email.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Vui lòng cung cấp địa chỉ email.");
            logger.warn("Empty or null email provided");
            return ResponseEntity.badRequest().body(response);
        }

        USER user = userService.getByEmail(email);
        if (user == null) {
            response.put("success", false);
            response.put("message", "Email không tồn tại.");
            logger.warn("Email not found: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        String otp = generateConfirmationCode();
        session.setAttribute("otp", otp);
        session.setAttribute("resetEmail", email);
        session.setAttribute("otpExpiry", System.currentTimeMillis() + 5 * 60 * 1000);
        logger.info("Generated OTP: {} for email: {}", otp, email);

        try {
            Email.EmailResult result = Email.sendEmail(
                    "Password Reset",
                    email,
                    "Please use the following verification code to reset your password.",
                    otp
            );
            if (!result.isSuccess()) {
                logger.error("Failed to send OTP to {}: {}", email, result.getMessage());
                response.put("success", false);
                response.put("message", result.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            logger.error("Unexpected error sending OTP to {}: {}", email, e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Đã xảy ra lỗi khi gửi OTP. Vui lòng thử lại.");
            return ResponseEntity.status(500).body(response);
        }

        response.put("success", true);
        response.put("message", "Mã OTP đã được gửi đến email của bạn");
        logger.info("OTP sent successfully to: {}", email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestParam(value = "email", required = false) String email,
                                                        @RequestParam(value = "otp", required = false) String submittedOtp,
                                                        HttpSession session) {
        logger.info("Verifying OTP for email: {}, OTP: {}", email, submittedOtp);
        Map<String, Object> response = new HashMap<>();

        String sessionEmail = (String) session.getAttribute("resetEmail");
        String sessionOtp = (String) session.getAttribute("otp");
        Long otpExpiry = (Long) session.getAttribute("otpExpiry");

        if (email == null || !email.equals(sessionEmail)) {
            response.put("success", false);
            response.put("message", "Email không hợp lệ hoặc phiên đã hết hạn.");
            logger.warn("Invalid email or session expired: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        if (otpExpiry == null || System.currentTimeMillis() > otpExpiry) {
            response.put("success", false);
            response.put("message", "Mã OTP đã hết hạn.");
            logger.warn("OTP expired for email: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        if (submittedOtp == null || !submittedOtp.equals(sessionOtp)) {
            response.put("success", false);
            response.put("message", "Mã OTP không hợp lệ.");
            logger.warn("Invalid OTP for email: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", true);
        logger.info("OTP verified successfully for email: {}", email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestParam(value = "email", required = false) String email,
                                                            @RequestParam(value = "newPassword", required = false) String newPassword,
                                                            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
                                                            HttpSession session) {
        logger.info("Reset password request for email: {}", email);
        Map<String, Object> response = new HashMap<>();

        String sessionEmail = (String) session.getAttribute("resetEmail");

        if (email == null || !email.equals(sessionEmail)) {
            response.put("success", false);
            response.put("message", "Email không hợp lệ hoặc phiên đã hết hạn.");
            logger.warn("Invalid email or session expired: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            response.put("success", false);
            response.put("message", "Mật khẩu không khớp.");
            logger.warn("Password mismatch for email: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        USER existingUser = userService.getByEmail(email);
        if (existingUser == null) {
            response.put("success", false);
            response.put("message", "Người dùng không tồn tại.");
            logger.warn("User not found: {}", email);
            return ResponseEntity.badRequest().body(response);
        }

        existingUser.setPassword(newPassword);
        try {
            userService.set(existingUser);
            response.put("success", true);
            response.put("message", "Mật khẩu đã được đặt lại thành công!");
            logger.info("Password reset successfully for email: {}", email);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi khi đặt lại mật khẩu. Vui lòng thử lại.");
            logger.error("Failed to reset password for {}: {}", email, e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        session.removeAttribute("otp");
        session.removeAttribute("resetEmail");
        session.removeAttribute("otpExpiry");

        return ResponseEntity.ok(response);
    }

    private String generateConfirmationCode() {
        return "CONF-" + (int) (Math.random() * 10000);
    }

    @PostMapping("/user/profile")
    public String updateUserNameAndPhone(
            @ModelAttribute("userForm") USER userForm,
            HttpSession session,
            RedirectAttributes ra) {

        USER sessionUser = (USER) session.getAttribute("user");

        sessionUser.setFirstName(userForm.getFirstName());
        sessionUser.setLastName(userForm.getLastName());
        sessionUser.setPhoneNumber(userForm.getPhoneNumber());

        try {
            boolean success = userService.updateNameAndPhone(sessionUser);
            if (success) {
                session.setAttribute("user", sessionUser);
                ra.addFlashAttribute("msg", "Cập nhật thông tin thành công!");
            } else {
                ra.addFlashAttribute("error", "Lỗi khi cập nhật.");
            }
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/profile";
    }
}