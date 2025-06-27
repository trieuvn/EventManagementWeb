/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.user;

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

@Controller
public class QRCodeController {

    @GetMapping("/test")
    public String showQRForm(Model model) {
        return "qr-result-content";
    }
    
    @PostMapping("/decode")
    public String decodeImage(Model model, @RequestParam("imageFile") MultipartFile imageFile) {
        // Validate file
        if (imageFile == null || imageFile.isEmpty()) {
            model.addAttribute("error", "Please upload an image file.");
            return "qr-result"; // JSP view to display result or error
        }

        // Validate file type (optional: restrict to image types)
        String contentType = imageFile.getContentType();
        if (!contentType.startsWith("image/")) {
            model.addAttribute("error", "Uploaded file must be an image (e.g., PNG, JPEG).");
            return "qr-result";
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
        } catch (IOException e) {
            // Handle other potential errors (e.g., corrupted image)
            model.addAttribute("error", "Error processing the image: " + e.getMessage());
        }

        return "qr-result"; // JSP view to display result or error
    }
}
