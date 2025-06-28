package com.uef.service;

import com.uef.model.CATEGORY;
import com.uef.model.EVENT;
import com.uef.model.PARTICIPANT;
import com.uef.model.TAG;
import com.uef.model.TICKET;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class ReportService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CategoryService categoryService;

    // Báo cáo sự kiện theo danh mục (BR-32)
    public List<Object[]> getEventsByCategory(String fromDate, String toDate) {
        // Default date range
        if (fromDate == null || fromDate.equals("")) {
            fromDate = "1900-01-01";
        }
        if (toDate == null || toDate.equals("")) {
            toDate = "2100-01-01";
        }

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Get all categories
        List<CATEGORY> categories = categoryService.getAll();
        List<Object[]> result = new ArrayList<>();

        // Process each category
        for (CATEGORY category : categories) {
            String categoryName = category.getName(); // Assuming CATEGORY has getName()
            long eventCount = 0;
            long registrationCount = 0;
            long participantCount = 0;
            Set<Long> uniqueEvents = new HashSet<>(); // Track unique events to avoid duplicates

            // Process tags in category
            List<TAG> tags = category.getTags();
            if (tags != null) {
                for (TAG tag : tags) {
                    EVENT event = tag.getEvent();
                    if (event != null && !uniqueEvents.contains(event.getId())) {
                        uniqueEvents.add(Long.valueOf(event.getId()));

                        // Check tickets for date range
                        List<TICKET> tickets = event.getTickets();
                        boolean eventInRange = false;
                        if (tickets != null) {
                            for (TICKET ticket : tickets) {
                                Date ticketDate = ticket.getDate(); // Returns java.util.Date
                                if (ticketDate != null) {
                                    // Convert Date to LocalDate
                                    LocalDate localTicketDate = ticketDate.toLocalDate();
                                    if (!localTicketDate.isBefore(startDate) && !localTicketDate.isAfter(endDate)) {
                                        eventInRange = true;
                                        registrationCount += ticket.getRegisteredParticipant().size();
                                        participantCount += ticket.getJoinedParticipant().size();
                                    }
                                }
                            }
                        }
                        if (eventInRange) {
                            eventCount++;
                        }
                    }
                }

            }
            // Add to result if there are events in the category
            if (eventCount > 0) {
                result.add(new Object[]{categoryName, eventCount, registrationCount, participantCount});
            }
        }
        return result;
    }
    // Báo cáo sự kiện theo loại (BR-32)

    public List<Object[]> getEventsByType(String fromDate, String toDate) {
        // Default date range
        if (fromDate == null || fromDate.equals("")) {
            fromDate = "1900-01-01";
        }
        if (toDate == null || toDate.equals("")) {
            toDate = "2100-01-01";
        }

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Get all events
        List<EVENT> events = eventService.getAll();
        Map<String, Object[]> resultMap = new HashMap<>();

        for (EVENT event : events) {
            String eventType = event.getType();
            List<TICKET> tickets = event.getTickets();
            boolean eventInRange = false;
            long registrationCount = 0;
            long participantCount = 0;

            if (tickets != null) {
                for (TICKET ticket : tickets) {
                    Date ticketDate = ticket.getDate();
                    if (ticketDate != null) {
                        LocalDate localTicketDate = ticketDate.toLocalDate();
                        if (!localTicketDate.isBefore(startDate) && !localTicketDate.isAfter(endDate)) {
                            eventInRange = true;
                            registrationCount += ticket.getRegisteredParticipant().size();
                            participantCount += ticket.getJoinedParticipant().size();
                        }
                    }
                }
            }

            if (eventInRange) {
                Object[] existingData = resultMap.getOrDefault(eventType, new Object[]{eventType, 0L, 0L, 0L});
                existingData[1] = (Long) existingData[1] + 1; // Increment event count
                existingData[2] = (Long) existingData[2] + registrationCount;
                existingData[3] = (Long) existingData[3] + participantCount;
                resultMap.put(eventType, existingData);
            }
        }

        return new ArrayList<>(resultMap.values());
    }

    // Báo cáo sự kiện theo trạng thái (BR-32)
    public List<Object[]> getEventsByStatus(String fromDate, String toDate) {
        // Default date range
        if (fromDate == null || fromDate.isEmpty()) {
            fromDate = "1900-01-01";
        }
        if (toDate == null || toDate.isEmpty()) {
            toDate = "2100-01-01";
        }

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Get all events
        List<EVENT> events = eventService.getAll();
        Map<String, Object[]> resultMap = new HashMap<>();

        for (EVENT event : events) {
            List<TICKET> tickets = event != null ? event.getTickets() : null;
            if (tickets != null) {
                for (TICKET ticket : tickets) {
                    if (ticket != null) {
                        // Giả sử getStatus() trả về int, xử lý null và ánh xạ sang String
                        Integer statusCode = ticket.getStatus(); // Sử dụng Integer để xử lý null
                        String status = (statusCode != null) ? mapStatusToString(statusCode) : "Unknown";
                        Date ticketDate = ticket.getDate();
                        if (ticketDate != null) {
                            LocalDate localTicketDate = ticketDate.toLocalDate();
                            if (!localTicketDate.isBefore(startDate) && !localTicketDate.isAfter(endDate)) {
                                Object[] existingData = resultMap.getOrDefault(status, new Object[]{status, 0L, 0L, 0L});
                                existingData[1] = (Long) existingData[1] + 1; // Increment event count
                                existingData[2] = (Long) existingData[2] + (ticket.getRegisteredParticipant() != null ? ticket.getRegisteredParticipant().size() : 0);
                                existingData[3] = (Long) existingData[3] + (ticket.getJoinedParticipant() != null ? ticket.getJoinedParticipant().size() : 0);
                                resultMap.put(status, existingData);
                            }
                        }
                    }
                }
            }
        }

        return new ArrayList<>(resultMap.values());
    }

// Hàm ánh xạ trạng thái từ int sang String (định nghĩa đầy đủ)
    private String mapStatusToString(int statusCode) {
        switch (statusCode) {
            case 0:
                return "Pending";
            case 1:
                return "Completed";
            case 2:
                return "Cancelled";
            default:
                return "Unknown"; // Xử lý giá trị không xác định
        }
    }

    // Báo cáo người tham gia theo sự kiện (BR-33)
    public List<Object[]> getParticipantsByEvent(String fromDate, String toDate) {
        // Default date range
        if (fromDate == null || fromDate.equals("")) {
            fromDate = "1900-01-01";
        }
        if (toDate == null || toDate.equals("")) {
            toDate = "2100-01-01";
        }

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Get all events
        List<EVENT> events = eventService.getAll();
        List<Object[]> result = new ArrayList<>();

        for (EVENT event : events) {
            String eventName = event.getName();
            long participantCount = 0;
            long totalSlots = 0;
            boolean eventInRange = false;

            List<TICKET> tickets = event.getTickets();
            if (tickets != null) {
                for (TICKET ticket : tickets) {
                    Date ticketDate = ticket.getDate();
                    if (ticketDate != null) {
                        LocalDate localTicketDate = ticketDate.toLocalDate();
                        if (!localTicketDate.isBefore(startDate) && !localTicketDate.isAfter(endDate)) {
                            eventInRange = true;
                            participantCount += ticket.getJoinedParticipant().size();
                            totalSlots += ticket.getSlots();
                        }
                    }
                }
            }

            if (eventInRange && totalSlots > 0) {
                double attendanceRate = (participantCount * 100.0) / totalSlots;
                result.add(new Object[]{eventName, participantCount, attendanceRate});
            }
        }

        return result;
    }

    // Báo cáo đánh giá trung bình (BR-34)
    public List<Object[]> getRatingsReport(String fromDate, String toDate) {
        // Default date range
        if (fromDate == null || fromDate.equals("")) {
            fromDate = "1900-01-01";
        }
        if (toDate == null || toDate.equals("")) {
            toDate = "2100-01-01";
        }

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        // Get all events
        List<EVENT> events = eventService.getAll();
        List<Object[]> result = new ArrayList<>();

        for (EVENT event : events) {
            String eventName = event.getName();
            List<Integer> ratings = new ArrayList<>();
            List<String> comments = new ArrayList<>();
            boolean eventInRange = false;

            List<TICKET> tickets = event.getTickets();
            if (tickets != null) {
                for (TICKET ticket : tickets) {
                    Date ticketDate = ticket.getDate();
                    if (ticketDate != null) {
                        LocalDate localTicketDate = ticketDate.toLocalDate();
                        if (!localTicketDate.isBefore(startDate) && !localTicketDate.isAfter(endDate)) {
                            eventInRange = true;
                            List<PARTICIPANT> participants = ticket.getJoinedParticipant();
                            if (participants != null) {
                                for (PARTICIPANT participant : participants) {
                                    if (participant.getRate() != 0) {
                                        ratings.add(participant.getRate());
                                    }
                                    if (participant.getComment() != null && !participant.getComment().isEmpty()) {
                                        comments.add(participant.getComment());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (eventInRange && !ratings.isEmpty()) {
                Double averageRating;
                Integer sum = 0;
                Integer count = 0;
                for (Integer rate : ratings) {
                    sum += rate;
                    count++;
                }

                averageRating = Double.valueOf(sum) / count;
                String commentString = String.join(", ", comments);
                result.add(new Object[]{eventName, averageRating, commentString});
            }
        }

        return result;
    }
}
