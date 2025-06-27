/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.uef.model.PARTICIPANT;
import com.uef.model.QRDataDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class QRCode {

    /*Cách sử dụng
    text: đoạn chuỗi ký tự tùy ý
    return: chuỗi hình ảnh dạng Base64 để hiển thị jsp
     */
    public static String convertFromStringToBase64String(String text) throws UnsupportedEncodingException, IOException {
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        try {
            BitMatrix matrix = new QRCodeWriter().encode(
                    new String(text.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);

            // Tạo BufferedImage từ BitMatrix
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF); // Đen hoặc Trắng
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

            return base64Image;
        } catch (WriterException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    public static PARTICIPANT convertFromStringToParticipant(String text) throws UnsupportedEncodingException, IOException {
        PARTICIPANT participant = new PARTICIPANT();
        QRDataDTO data = parseQRData(text);
        String userEmail = data.getEmail();
        Integer ticketId = data.getTicketId();
        String confirmCode = data.getConfirmCode();
        

        return null;
    }

    public static int generateRandomInt(int min, int max) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(max - min + 1) + min; // Sinh số trong khoảng [min, max]
    }

    public static QRDataDTO parseQRData(String qrData) throws IllegalArgumentException {
        if (qrData == null || qrData.isEmpty()) {
            throw new IllegalArgumentException("QR data cannot be null or empty");
        }

        String[] parts = qrData.split("/");
        if (parts.length != 4 || parts[3].isEmpty()) {
            throw new IllegalArgumentException("Invalid QR data format. Expected: email/ticketId/confirmCode/");
        }

        String email = parts[0];
        String ticketIdStr = parts[1];
        String confirmCode = parts[2];

        // Validate email format
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        // Parse ticketId
        int ticketId;
        try {
            ticketId = Integer.parseInt(ticketIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ticket ID format: " + ticketIdStr);
        }

        // Validate confirmCode (assuming it should not be empty)
        if (confirmCode.isEmpty()) {
            throw new IllegalArgumentException("Confirm code cannot be empty");
        }

        return new QRDataDTO(email, ticketId, confirmCode);
    }
}
