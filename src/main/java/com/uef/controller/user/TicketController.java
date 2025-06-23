/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.user;

import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.CategoryService;
import com.uef.service.EventService;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import com.uef.utils.Image;
import com.uef.utils.Map;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private EventService eventService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private ParticipantService participantService;
    
    @GetMapping("/{ticket_id}")
    public String TicketDetail(@RequestParam int ticket_id, RedirectAttributes ra, Model model) throws Exception {
        TICKET ticket = ticketService.getById(ticket_id);
        EVENT event = ticket.getEvent();
        if (ticket == null) {
            ra.addFlashAttribute("message", "Sự kiện không tồn tại.");
            return "redirect:/";
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("event", event);
        model.addAttribute("event_image", Image.convertByteToBase64(event.getImage()));
        model.addAttribute("map", Map.showMap(model, null, null, Double.valueOf(ticket.getLocation().getLatitude()), Double.valueOf(ticket.getLocation().getLongitude()), null, null));
        model.addAttribute("userForm", new USER());
        model.addAttribute("body", "/WEB-INF/views/user/tickets/detail.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        model.addAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
        return "layout/main";
    }
    
    @PostMapping("/register/{ticket_id}")
    public String registerTicket(@RequestParam int ticket_id, RedirectAttributes ra, HttpSession session) {
        USER user = (USER) session.getAttribute("user");
        if (user == null){
            //redirect Dang nhap
            ra.addFlashAttribute("message", "Hãy đăng nhập trước khi đăng ký sự kiện.");
            return "redirect:/login";
        }
        TICKET ticket = ticketService.getById(ticket_id);
        if (ticket == null) {
            ra.addFlashAttribute("message", "Sự kiện không tồn tại.");
            return "redirect:/";
        }
        
        //fix sau khi them service
        PARTICIPANT participant = null;
        if (participant != null){
            ra.addFlashAttribute("message", "Bạn đã đăng ký sự kiện này rồi!.");
            return "redirect:/ticket/"+ticket_id;
        }
        
        participant = new PARTICIPANT();
        participant.setUser(user);
        participant.setTicket(ticket);
        participant.setStatus(0);
        
        //Thêm participant vào database (Sang chưa code à? Chịu luôn)
        ra.addFlashAttribute("message", "Thêm sự kiện thành công.");
        return "redirect:/ticket/"+ticket_id;
    }
}
