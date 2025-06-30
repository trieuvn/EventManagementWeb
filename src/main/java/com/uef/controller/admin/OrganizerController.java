package com.uef.controller.admin;

import com.uef.annotation.RoleRequired;
import com.uef.model.CHANGE;
import com.uef.model.ORGANIZER;
import com.uef.service.ChangeService;
import com.uef.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @Autowired
    private ChangeService changeService;

    @RoleRequired({"admin"})
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("organizer", new ORGANIZER());
        model.addAttribute("body", "admin/organizer/organizer-detail");
        return "admin/layout/main";
    }

    @RoleRequired({"admin"})
    @PostMapping("/save/{id}")
    public String saveOrganizer(
            @PathVariable("id") int organizerId,
            @Valid @ModelAttribute("organizer") ORGANIZER organizer,
            BindingResult result,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Dữ liệu không hợp lệ: " + result.getAllErrors());
            if (organizerId == 0) {
                return "redirect:/admin/organizers/create";
            }
            return "redirect:/admin/organizers/edit/" + organizerId;
        }

        organizer.setId(organizerId);

        try {
            if (!avatarFile.isEmpty()) {
                organizer.setAvatar(avatarFile.getBytes());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải lên ảnh: " + e.getMessage());
            if (organizerId == 0) {
                return "redirect:/admin/organizers/create";
            }
            return "redirect:/admin/organizers/edit/" + organizerId;
        }

        try {
            organizerService.set(organizer);
            if (organizerId == 0) {
                redirectAttributes.addFlashAttribute("successMessage", "Tạo nhà tổ chức thành công!");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Lưu thông tin nhà tổ chức thành công!");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            if (organizerId == 0) {
                return "redirect:/admin/organizers/create";
            }
            return "redirect:/admin/organizers/edit/" + organizerId;
        }

        return "redirect:/admin/events";
    }

    @RoleRequired({"admin"})
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        ORGANIZER organizer = organizerService.getById(id);
        if (organizer == null) {
            model.addAttribute("errorMessage", "Nhà tổ chức không tồn tại.");
            return "redirect:/admin/events";
        }
        model.addAttribute("organizer", organizer);
        model.addAttribute("events", organizer.getEvents());
        model.addAttribute("body", "admin/organizer/organizer-detail");
        return "admin/layout/main";
    }

    @RoleRequired({"admin"})
    @GetMapping("/view/{id}")
    public String viewOrganizer(@PathVariable int id, Model model) {
        ORGANIZER organizer = organizerService.getById(id);
        if (organizer == null) {
            model.addAttribute("errorMessage", "Nhà tổ chức không tồn tại.");
            return "redirect:/admin/events";
        }
        model.addAttribute("organizer", organizer);
        model.addAttribute("events", organizer.getEvents());
        model.addAttribute("body", "admin/organizer/organizer-view");
        return "admin/layout/main";
    }

    @RoleRequired({"admin"})
    @PostMapping("/delete/{id}")
    public String deleteOrganizer(@PathVariable int id, RedirectAttributes redirectAttributes) {
        ORGANIZER organizer = organizerService.getById(id);
        if (organizer == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nhà tổ chức không tồn tại.");
            return "redirect:/admin/events";
        }

        try {
            organizerService.deleteById(id);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/events";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa nhà tổ chức: " + e.getMessage());
            return "redirect:/admin/events";
        }

        CHANGE change = new CHANGE();
        change.setSubject("Xóa nhà tổ chức");
        change.setDescription("Đã xóa nhà tổ chức: " + organizer.getLastName());
        change.setDate(Date.valueOf(LocalDate.now())); // 03:51 AM +07, June 30, 2025
        change.setTime(Time.valueOf(LocalTime.now()));

        try {
            saveChangeLog(change, redirectAttributes);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi ghi log thay đổi: " + e.getMessage());
        }

        redirectAttributes.addFlashAttribute("successMessage", "Xóa nhà tổ chức thành công!");
        return "redirect:/admin/events";
    }

    @RoleRequired({"admin"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveChangeLog(CHANGE change, RedirectAttributes redirectAttributes) {
        try {
            changeService.set(change);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Lỗi khi lưu log thay đổi: " + e.getMessage(), e);
        }
    }

    @RoleRequired({"admin"})
    @GetMapping("/create")
    public String createOrganizer(Model model) {
        model.addAttribute("organizer", new ORGANIZER());
        model.addAttribute("body", "admin/organizer/organizer-detail");
        return "admin/layout/main";
    }
}