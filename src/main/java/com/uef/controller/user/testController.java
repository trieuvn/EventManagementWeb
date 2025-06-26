package com.uef.controller.user;

import com.uef.model.EVENT;
import com.uef.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/events")
public class testController {

    @Autowired
    private EventService eventService;

    // Display event image as bytes
    @GetMapping("/{id}/image")
    @ResponseBody
    public byte[] getEventImage(@PathVariable("id") int id) {
        EVENT event = eventService.getById(id);
        return event != null && event.getImage() != null ? event.getImage() : new byte[0];
    }

    // Show dedicated page to view event image with Base64 conversion
    @GetMapping("/{id}/image/view")
    public String showEventImageView(@PathVariable("id") int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event == null) {
            model.addAttribute("error", "Event not found");
            return "event-image-view";
        }
        // Convert image to Base64 if it exists
        if (event.getImage() != null && event.getImage().length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(event.getImage());
            model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);
        }
        model.addAttribute("event", event);
        return "event-image-view";
    }

    // Show form to update event image
    @GetMapping("/{id}/image/update")
    public String showUpdateImageForm(@PathVariable("id") int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event == null) {
            return "redirect:/events";
        }
        // Convert image to Base64 for display in update form
        if (event.getImage() != null && event.getImage().length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(event.getImage());
            model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);
        }
        model.addAttribute("event", event);
        return "event-image-update";
    }

    // Handle image update
    @PostMapping("/{id}/image")
    public String updateEventImage(@PathVariable("id") int id, 
                                  @RequestParam("imageFile") MultipartFile imageFile,
                                  Model model) {
        try {
            EVENT event = eventService.getById(id);
            if (event == null) {
                model.addAttribute("error", "Event not found");
                return "event-image-update";
            }

            if (!imageFile.isEmpty()) {
                long maxSize = Long.MAX_VALUE; // 1MB limit
                if (imageFile.getSize() > maxSize) {
                    model.addAttribute("error", "Image size exceeds maximum limit of " + maxSize + " bytes");
                    return "event-image-update";
                }
                event.setImage(imageFile.getBytes());
                eventService.set(event);
            }
            return "redirect:/events/" + id + "/image/update?success=true";
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
            return "event-image-update";
        }
    }
    
    // Get hexadecimal string of event image for SQL INSERT
    @GetMapping("/{id}/image/hex")
    public String getEventImageHex(@PathVariable("id") int id, Model model) {
        EVENT event = eventService.getById(id);
        if (event == null) {
            model.addAttribute("error", "Event not found");
            return "event-image-hex";
        }
        if (event.getImage() != null && event.getImage().length > 0) {
            String hexString = bytesToHex(event.getImage());
            model.addAttribute("hexString", "0x" + hexString);
        } else {
            model.addAttribute("hexString", "No image data available");
        }
        model.addAttribute("event", event);
        return "event-image-hex";
    }

    // Utility method to convert byte[] to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}