/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.admin;

/**
 *
 * @author Administrator
 */
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.uef.annotation.RoleRequired;
import com.uef.model.PARTICIPANT;
import com.uef.model.QRDataDTO;
import com.uef.model.TICKET;
import com.uef.model.USER;
import com.uef.service.TicketService;
import com.uef.service.ParticipantService;
import com.uef.service.UserService;
import static com.uef.utils.QRCode.parseQRData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/admin")
@RequestMapping("/admin")
public class QRCodeController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @RoleRequired({"admin"})
    @GetMapping("/qrscan")
    public String showQRForm(Model model) {
        model.addAttribute("body", "admin/qrscan/qr-result-content");
        return "admin/layout/main";
    }

    @RoleRequired({"admin"})
    @PostMapping("/decode")
    public String decodeImage(Model model, @RequestParam("imageFile") MultipartFile imageFile) {
        // Validate file
        if (imageFile == null || imageFile.isEmpty()) {
            model.addAttribute("message", "Please upload an image file.");
            model.addAttribute("body", "admin/qrscan/qr-result-content");
            return "admin/layout/main";
        }

        // Validate file type (optional: restrict to image types)
        String contentType = imageFile.getContentType();
        if (!contentType.startsWith("image/")) {
            model.addAttribute("message", "Uploaded file must be an image (e.g., PNG, JPEG).");
            model.addAttribute("body", "admin/qrscan/qr-result-content");
            return "admin/layout/main";
        }

        try {
            // Convert MultipartFile to BufferedImage
            BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());

            // Decode QR code using ZXing
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);

            // Add decoded string to model
            model.addAttribute("decodedText", result.getText());
            QRDataDTO data = new QRDataDTO();
            try {
                data = parseQRData(result.getText());
            } catch (Exception e) {
                model.addAttribute("message", "Mã QR không hợp lệ.");
                model.addAttribute("body", "admin/qrscan/qr-result-content");
                return "admin/layout/main";
            }

            String userEmail = data.getEmail();
            Integer ticketId = data.getTicketId();
            String confirmCode = data.getConfirmCode();

            TICKET ticket = ticketService.getById(ticketId);
            USER user = userService.getByEmail(userEmail);
            PARTICIPANT participant = participantService.getById(ticketId, userEmail);

            if (ticket.getConfirmCode() != Integer.parseInt(confirmCode)) {
                model.addAttribute("message", "QRCode and ticket not matched.");
                model.addAttribute("body", "admin/qrscan/qr-result-content");
                return "admin/layout/main";
            }

            if (participant == null) {
                participant = new PARTICIPANT();
                participant.setUser(user);
                participant.setTicket(ticket);
                participant.setStatus(2);
                participantService.set(participant);
            } else {
                participant.setStatus(1);
                participantService.set(participant);
            }
            model.addAttribute("message", "Checkin thành công\n" + user.getFirstName() + " " + user.getLastName() + "\nSự kiện: " + ticket.getEvent().getName());
            model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("eventname", ticket.getEvent().getName());
        } catch (NotFoundException e) {
            // Handle case where image is not a QR code
            model.addAttribute("message", "The uploaded image is not a valid QR code.");
            model.addAttribute("body", "admin/qrscan/qr-result-content");
            return "admin/layout/main";
        } catch (IOException e) {
            // Handle other potential errors (e.g., corrupted image)
            model.addAttribute("message", "Error processing the image: " + e.getMessage());
            model.addAttribute("body", "admin/qrscan/qr-result-content");
            return "admin/layout/main";
        }

        model.addAttribute("body", "admin/qrscan/qr-result-content");
        return "admin/layout/main";
    }
}
