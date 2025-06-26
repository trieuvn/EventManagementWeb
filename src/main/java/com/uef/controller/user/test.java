/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.user;

import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.model.USER;
import com.uef.service.EventService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author huy59
 */
@Controller
public class test {
    @Autowired
    private EventService eventService;
    
    @GetMapping("/test")
    public String home(Model model) {
        //
        EVENT e = eventService.getById(1);
        List<Integer> test = e.getRates();
        return "layout/main";
    }
}
