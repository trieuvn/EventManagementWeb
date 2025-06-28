package com.uef.service;

import com.uef.model.CATEGORY;
import com.uef.model.EVENT;
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
import java.util.HashSet;
import java.util.List;
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
        if (fromDate == null && toDate == null) {
            fromDate = "1900-01-01";
            toDate = "2100-01-01";
        } else if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.");
        }

        StringBuilder jpql = new StringBuilder("SELECT e.type, COUNT(e) FROM EVENT e ");
        jpql.append("JOIN e.tickets tick WHERE tick.date BETWEEN :fromDate AND :toDate ");
        jpql.append("GROUP BY e.type");

        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("fromDate", Date.valueOf(fromDate));
        query.setParameter("toDate", Date.valueOf(toDate));

        return query.getResultList();
    }

    // Báo cáo sự kiện theo trạng thái (BR-32)
    public List<Object[]> getEventsByStatus(String fromDate, String toDate) {
        if (fromDate == null && toDate == null) {
            fromDate = "1900-01-01";
            toDate = "2100-01-01";
        } else if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.");
        }

        StringBuilder jpql = new StringBuilder("SELECT t.status, COUNT(e) FROM EVENT e JOIN e.tickets t ");
        jpql.append("WHERE t.date BETWEEN :fromDate AND :toDate ");
        jpql.append("GROUP BY t.status");

        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("fromDate", Date.valueOf(fromDate));
        query.setParameter("toDate", Date.valueOf(toDate));

        return query.getResultList();
    }

    // Báo cáo người tham gia theo sự kiện (BR-33)
    public List<Object[]> getParticipantsByEvent(String fromDate, String toDate) {
        if (fromDate == null && toDate == null) {
            fromDate = "1900-01-01";
            toDate = "2100-01-01";
        } else if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.");
        }

        StringBuilder jpql = new StringBuilder("SELECT e.name, COUNT(p), (COUNT(p) * 100.0 / t.slots) "
                + "FROM EVENT e JOIN e.tickets t JOIN t.participants p ");
        jpql.append("WHERE t.date BETWEEN :fromDate AND :toDate ");
        jpql.append("GROUP BY e.name, t.slots");

        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("fromDate", Date.valueOf(fromDate));
        query.setParameter("toDate", Date.valueOf(toDate));

        return query.getResultList();
    }

    // Báo cáo đánh giá trung bình (BR-34)
    public List<Object[]> getRatingsReport(String fromDate, String toDate) {
        if (fromDate == null && toDate == null) {
            fromDate = "1900-01-01";
            toDate = "2100-01-01";
        } else if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.");
        }

        StringBuilder sql = new StringBuilder(
                "SELECT e.name, AVG(CAST(p.rate AS FLOAT)), STRING_AGG(p.comment, ', ') "
                + "FROM [EVENT] e "
                + "JOIN [TICKET] t ON e.id = t.event "
                + "JOIN PARTICIPANT p ON t.id = p.ticket "
                + "WHERE t.date BETWEEN :fromDate AND :toDate "
                + "GROUP BY e.name"
        );

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("fromDate", Date.valueOf(fromDate));
        query.setParameter("toDate", Date.valueOf(toDate));

        return query.getResultList();
    }
}
