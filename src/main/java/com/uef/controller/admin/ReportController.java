package com.uef.controller.admin;

import com.uef.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String viewReports(
            @RequestParam(value = "reportType", required = false) String reportType,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            Model model) {

        List<Object[]> reportData = null;

        if (reportType != null) {
            switch (reportType) {
                case "eventByCategory":
                    reportData = reportService.getEventsByCategory(fromDate, toDate);
                    break;
                case "eventByType":
                    reportData = reportService.getEventsByType(fromDate, toDate);
                    break;
                case "eventByStatus":
                    reportData = reportService.getEventsByStatus(fromDate, toDate);
                    break;
                case "participantsByEvent":
                    reportData = reportService.getParticipantsByEvent(fromDate, toDate);
                    break;
                case "ratings":
                    reportData = reportService.getRatingsReport(fromDate, toDate);
                    break;
            }
        }

        model.addAttribute("reportType", reportType);
        model.addAttribute("reportData", reportData);
        model.addAttribute("chartData", reportData); // Dữ liệu cho biểu đồ
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "admin/report";
    }

    @GetMapping("/export")
    public void exportReport(
            @RequestParam(value = "type") String reportType,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            HttpServletResponse response) throws IOException {

        List<Object[]> reportData = null;
        String fileName = "report_" + reportType + ".csv";

        switch (reportType) {
            case "eventByCategory":
                reportData = reportService.getEventsByCategory(fromDate, toDate);
                break;
            case "eventByType":
                reportData = reportService.getEventsByType(fromDate, toDate);
                break;
            case "eventByStatus":
                reportData = reportService.getEventsByStatus(fromDate, toDate);
                break;
            case "participantsByEvent":
                reportData = reportService.getParticipantsByEvent(fromDate, toDate);
                break;
            case "ratings":
                reportData = reportService.getRatingsReport(fromDate, toDate);
                break;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        StringBuilder csvContent = new StringBuilder();
        if (reportType.equals("eventByCategory") || reportType.equals("eventByType") || reportType.equals("eventByStatus")) {
            csvContent.append("Label,Event Count\n");
            for (Object[] data : reportData) {
                csvContent.append(String.format("%s,%d\n", data[0], ((Long) data[1]).intValue()));
            }
        } else if (reportType.equals("participantsByEvent")) {
            csvContent.append("Event Name,Participant Count,Attendance Rate\n");
            for (Object[] data : reportData) {
                csvContent.append(String.format("%s,%d,%.2f\n", data[0], ((Long) data[1]).intValue(), (Double) data[2]));
            }
        } else if (reportType.equals("ratings")) {
            csvContent.append("Event Name,Average Rating,Comments\n");
            for (Object[] data : reportData) {
                csvContent.append(String.format("%s,%.2f,%s\n", data[0], (Double) data[1], data[2] != null ? data[2] : ""));
            }
        }

        response.getWriter().write(csvContent.toString());
    }
}