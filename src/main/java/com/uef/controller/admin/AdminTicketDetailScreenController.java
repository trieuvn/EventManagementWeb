package com.uef.controller.admin;

import com.uef.model.TICKET;
import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.service.TicketService;
import com.uef.service.EventService;
import com.uef.service.LocationService;
import com.uef.service.ParticipantService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin/tickets")
public class AdminTicketDetailScreenController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ParticipantService participantService;

    // Hiển thị danh sách vé
    @GetMapping
    public String listTickets(Model model) {
        List<TICKET> tickets = ticketService.getAll();
        model.addAttribute("tickets", tickets);
        return "admin/tickets/list";
    }

    // Hiển thị form thêm vé mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ticket", new TICKET());
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("locations", locationService.getAll());
        return "admin/tickets/add";
    }

    // Xử lý thêm vé mới
    @PostMapping("/add")
    public String addTicket(@ModelAttribute TICKET ticket, RedirectAttributes redirectAttributes) {
        try {
            EVENT event = eventService.getById(ticket.getEvent().getId());
            if (!isValidTicketType(event.getType(), ticket.getType())) {
                throw new IllegalArgumentException("Loại vé không hợp lệ với loại sự kiện.");
            }
            ticketService.set(ticket);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm vé thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm vé: " + e.getMessage());
        }
        return "redirect:/admin/tickets";
    }

    // Hiển thị form chỉnh sửa vé
    @RequestMapping({"/edit/{id}", "/view/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        TICKET ticket = ticketService.getById(id);
        if (ticket == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vé không tồn tại.");
            return "redirect:/admin/tickets";
        }
        List<PARTICIPANT> participants = participantService.getAll();
        model.addAttribute("ticket", ticket);
        model.addAttribute("participantList", participants);
        model.addAttribute("events", eventService.getAll());
        model.addAttribute("locationList", locationService.getAll());
        return "admin/ticket/ticket-detail";
    }

    @PostMapping("/save/{id}")
    public String saveEditedTicket(
            @PathVariable("id") int ticketId,
            @Valid @ModelAttribute("ticket") TICKET ticket,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        ticket = ticketService.getById(ticketId);
        if (result.hasErrors()) {
            // TODO: Fetch participant list again to repopulate the form
            redirectAttributes.addFlashAttribute("message", result);
            return "redirect:/admin/tickets/edit/"+String.valueOf(ticketId);
        }

        ticket.setId(ticketId);
        try {
            // TODO: Save the updated ticket to the database
            ticketService.set(ticket);
            redirectAttributes.addFlashAttribute("message", "Vé đã được cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi lưu vé: " + e.getMessage());
            return "redirect:/admin/tickets/edit/"+String.valueOf(ticketId);
        }
        return "redirect:/admin/tickets/edit/" + String.valueOf(ticketId);
    }

    // Xử lý cập nhật vé
    @PostMapping("/update")
    public String updateTicket(@ModelAttribute TICKET ticket, RedirectAttributes redirectAttributes) {
        try {
            EVENT event = eventService.getById(ticket.getEvent().getId());
            if (!isValidTicketType(event.getType(), ticket.getType())) {
                throw new IllegalArgumentException("Loại vé không hợp lệ với loại sự kiện.");
            }
            ticketService.set(ticket);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật vé thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật vé: " + e.getMessage());
        }
        return "redirect:/admin/tickets";
    }

    // Xử lý xóa vé
//    @GetMapping("/delete/{id}")
//    public String deleteTicket(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
//        try {
//            TICKET ticket = ticketService.getById(id);
//            if (ticket == null) {
//                throw new IllegalArgumentException("Vé không tồn tại.");
//            }
//            List<PARTICIPANT> participants = participantService.getByTicket(id);
//            if (!participants.isEmpty()) {
//                throw new IllegalStateException("Không thể xóa vé vì đã có người tham gia.");
//            }
//            ticketService.deleteById(id);
//            redirectAttributes.addFlashAttribute("successMessage", "Xóa vé thành công!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa vé: " + e.getMessage());
//        }
//        return "redirect:/admin/tickets";
//    }

    // Kiểm tra tính hợp lệ của loại vé với loại sự kiện (BR-24)
    private boolean isValidTicketType(String eventType, String ticketType) {
        if ("online".equalsIgnoreCase(eventType)) {
            return "online".equalsIgnoreCase(ticketType);
        } else if ("offline".equalsIgnoreCase(eventType)) {
            return "offline".equalsIgnoreCase(ticketType);
        } else if ("hybrid".equalsIgnoreCase(eventType)) {
            return "online".equalsIgnoreCase(ticketType) || 
                   "offline".equalsIgnoreCase(ticketType) || 
                   "hybrid".equalsIgnoreCase(ticketType);
        }
        return false;
    }
}