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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/admin")
@RequestMapping("/admin")
public class QRCodeController {

    @GetMapping("/qrscan")
    public String showQRForm(Model model) {
        model.addAttribute("body", "admin/qrscan/qr-result-content");
        return "admin/layout/main";
    }
    
    @PostMapping("/decode")
    public String decodeImage(Model model, @RequestParam("imageFile") MultipartFile imageFile) {
        // Validate file
        if (imageFile == null || imageFile.isEmpty()) {
            model.addAttribute("error", "Please upload an image file.");
            return "redirect:/admin/qrscan"; // JSP view to display result or error
        }

        // Validate file type (optional: restrict to image types)
        String contentType = imageFile.getContentType();
        if (!contentType.startsWith("image/")) {
            model.addAttribute("error", "Uploaded file must be an image (e.g., PNG, JPEG).");
            return "redirect:/admin/qrscan";
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
        } catch (NotFoundException e) {
            // Handle case where image is not a QR code
            model.addAttribute("error", "The uploaded image is not a valid QR code.");
            return "redirect:/admin/qrscan";
        } catch (IOException e) {
            // Handle other potential errors (e.g., corrupted image)
            model.addAttribute("error", "Error processing the image: " + e.getMessage());
            return "redirect:/admin/qrscan";
        }
        model.addAttribute("body", "admin/qrscan/qr-result");  
        return "admin/layout/main"; // JSP view to display result or error
    }
}
