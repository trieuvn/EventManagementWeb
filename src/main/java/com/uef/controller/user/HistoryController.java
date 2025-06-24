/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.user;

import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.CategoryService;
import com.uef.service.EventService;
import com.uef.service.ParticipantService;
import com.uef.service.TicketService;
import com.uef.utils.Image;
import com.uef.utils.Map;
import com.uef.utils.QRCode;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private EventService eventService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private ParticipantService participantService;
    
    @GetMapping("/")
    public String History(@RequestParam int ticket_id, RedirectAttributes ra, Model model, HttpSession session){
        USER user = (USER) session.getAttribute("user");
        if (user == null){
            //redirect Dang nhap
            ra.addFlashAttribute("message", "Hãy đăng nhập để xem lịch sử.");
            return "redirect:/login";
        }
        List<PARTICIPANT> participants = participantService.getByUser(user);
        //PARTICIPANT có thể lấy được USER và TICKET
        //Tự code filter ở view
        model.addAttribute("participants", participants);
        model.addAttribute("userForm", new USER());
        model.addAttribute("body", "/WEB-INF/views/user/tickets/detail.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        model.addAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
        return "layout/main";
    }
    
    @RequestMapping("/")
    public String HistoryDetail(@RequestParam int ticket_id, RedirectAttributes ra, Model model, HttpSession session) throws IOException{
        USER user = (USER) session.getAttribute("user");
        if (user == null){
            //redirect Dang nhap
            ra.addFlashAttribute("message", "Hãy đăng nhập để xem lịch sử.");
            return "redirect:/login";
        }
        TICKET ticket = ticketService.getById(ticket_id);
        //Dùng tạm
        PARTICIPANT participant = new PARTICIPANT();
        participant.setUser(user);
        participant.setTicket(ticket);
        participant.setStatus(0);

        
        model.addAttribute("participant", participant);
        model.addAttribute("ticket", ticket);
        model.addAttribute("event", ticket.getEvent());
        model.addAttribute("event_image", Image.convertByteToBase64(ticket.getEvent().getImage()));
        //qrcode có dạng là string base64 để đặt hình ảnh trong jsp
        model.addAttribute("qrcode", QRCode.convertFromStringToBase64String("ahihi"));
        model.addAttribute("userForm", new USER());
        model.addAttribute("body", "/WEB-INF/views/user/tickets/detail.jsp");
        model.addAttribute("advantage", "/WEB-INF/views/layout/benefit.jsp");
        model.addAttribute("introPicture", "/WEB-INF/assets/img/hero.jpg");
        return "layout/main";
    }
}
