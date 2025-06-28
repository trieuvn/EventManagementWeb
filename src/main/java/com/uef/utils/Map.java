/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.ui.Model;

/**
 *
 * @author Administrator
 */
public class Map {

    /*Cách sử dụng
    model: model của controller hiện tại
    startLat: tọa độ Lat của điểm bắt đầu (null: vị trí Lat hiện tại dựa trên IP)
    startLng: tọa độ Lng của điểm bắt đầu (null: vị trí Lng hiện tại dựa trên IP)
    endLat: tọa độ Lat của điểm kết thúc
    endLng: tọa độ Lng của điểm kết thúc
    startName: tên điểm bắt đầu (null: You are here)
    endName: tên điểm kết thúc (null: Destination)
    return: jsp bản đồ
     */
    public static String showMap(Model model, Double startLat, Double startLng, Double endLat, Double endLng, String startName, String endName) throws Exception {
        if (startLat == null || startLng == null) {
            Double[] diachi = getCoordinates();
            startLat = diachi[0];
            startLng = diachi[1];
        }

        model.addAttribute("startLat", startLat);
        model.addAttribute("startLng", startLng);
        model.addAttribute("endLat", endLat);
        model.addAttribute("endLng", endLng);

        if (startName == null) {
            startName = "📍 Vị trí hiện tại của bạn";
        }
        if (endName == null) {
            endName = "📍 Điểm đến";
        }

        model.addAttribute("nameA", startName);
        model.addAttribute("nameB", endName);

        return "utils/openstreetmap";
    }

    //[0] = lat, [1] = lon
    public static Double[] getCoordinates() throws Exception {
        URL url = new URL("http://ip-api.com/json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        double lat = jsonResponse.has("lat") ? jsonResponse.get("lat").getAsDouble() : 0.0;
        double lon = jsonResponse.has("lon") ? jsonResponse.get("lon").getAsDouble() : 0.0;

        return new Double[]{lat, lon};
    }

}
