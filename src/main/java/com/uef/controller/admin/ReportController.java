package com.uef.controller.admin;

import com.uef.service.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

        try {
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
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/report"; // Trang để chọn lại ngày
        }
    }

    @GetMapping("/export")
    public void exportReport(
            @RequestParam(value = "type") String reportType,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            HttpServletResponse response) throws IOException {

        try {
            List<Object[]> reportData = null;
            String fileName = "report_" + reportType + ".xlsx";

            // Lấy dữ liệu báo cáo dựa trên loại báo cáo
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

            // Thiết lập header cho tệp XLSX
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // Tạo workbook và sheet
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Report");

            // Thêm tiêu đề, phụ đề và khoảng thời gian
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Báo cáo thống kê");

            Row subtitleRow = sheet.createRow(1);
            Cell subtitleCell = subtitleRow.createCell(0);
            subtitleCell.setCellValue("Loại báo cáo: " + getReportTypeName(reportType));

            Row fromDateRow = sheet.createRow(2);
            Cell fromDateCell = fromDateRow.createCell(0);
            fromDateCell.setCellValue("Từ ngày: " + (fromDate != null ? fromDate : "1900-01-01 (mặc định)"));

            Row toDateRow = sheet.createRow(3);
            Cell toDateCell = toDateRow.createCell(0);
            toDateCell.setCellValue("Đến ngày: " + (toDate != null ? toDate : "2100-01-01 (mặc định)"));

            // Thêm tiêu đề bảng dựa trên loại báo cáo
            Row headerRow = sheet.createRow(5);
            if (reportType.equals("eventByCategory") || reportType.equals("eventByType") || reportType.equals("eventByStatus")) {
                headerRow.createCell(0).setCellValue("Danh mục");
                headerRow.createCell(1).setCellValue("Số sự kiện");
                headerRow.createCell(2).setCellValue("Số lượng đăng ký");
                headerRow.createCell(3).setCellValue("Số lượng tham gia");
            } else if (reportType.equals("participantsByEvent")) {
                headerRow.createCell(0).setCellValue("Tên sự kiện");
                headerRow.createCell(1).setCellValue("Số người tham gia");
                headerRow.createCell(2).setCellValue("Tỷ lệ tham dự");
            } else if (reportType.equals("ratings")) {
                headerRow.createCell(0).setCellValue("Tên sự kiện");
                headerRow.createCell(1).setCellValue("Điểm trung bình");
                headerRow.createCell(2).setCellValue("Bình luận");
            }

            // Điền dữ liệu vào bảng
            int rowNum = 6;
            for (Object[] data : reportData) {
                Row row = sheet.createRow(rowNum++);
                if (reportType.equals("eventByCategory") || reportType.equals("eventByType") || reportType.equals("eventByStatus")) {
                    row.createCell(0).setCellValue((String) data[0]);
                    row.createCell(1).setCellValue(((Long) data[1]).intValue());
                    row.createCell(1).setCellValue(((Long) data[2]).intValue());
                    row.createCell(1).setCellValue(((Long) data[3]).intValue());
                } else if (reportType.equals("participantsByEvent")) {
                    row.createCell(0).setCellValue((String) data[0]);
                    row.createCell(1).setCellValue(((Long) data[1]).intValue());
                    row.createCell(2).setCellValue((Double) data[2]);
                } else if (reportType.equals("ratings")) {
                    row.createCell(0).setCellValue((String) data[0]);
                    row.createCell(1).setCellValue((Double) data[1]);
                    row.createCell(2).setCellValue(data[2] != null ? (String) data[2] : "");
                }
            }

            // Tự động điều chỉnh kích thước cột
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi dữ liệu ra response
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IllegalArgumentException e) {
            response.setContentType("text/plain");
            response.getWriter().write("Lỗi: " + e.getMessage());
        }
    }

    // Phương thức hỗ trợ lấy tên loại báo cáo
    private String getReportTypeName(String reportType) {
        switch (reportType) {
            case "eventByCategory":
                return "Sự kiện theo danh mục";
            case "eventByType":
                return "Sự kiện theo loại";
            case "eventByStatus":
                return "Sự kiện theo trạng thái";
            case "participantsByEvent":
                return "Người tham gia theo sự kiện";
            case "ratings":
                return "Đánh giá trung bình";
            default:
                return "Báo cáo";
        }
    }
}