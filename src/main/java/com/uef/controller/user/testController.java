/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.user;

import com.uef.model.CATEGORY;
import com.uef.model.EVENT;
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
 * @author Administrator
 */
@Controller
public class testController {
    @Autowired
    private EventService service;
    
    @GetMapping("/test")
    public String aboutUs(Model model) {
        List<EVENT> list = service.searchEvents("UEF", null, null);
        return "layout/main";
    }
}
