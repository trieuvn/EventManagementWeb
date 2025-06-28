package com.uef.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    // Báo cáo sự kiện theo danh mục (BR-32)
    public List<Object[]> getEventsByCategory(String fromDate, String toDate) {
        StringBuilder jpql = new StringBuilder("SELECT t.category.name, COUNT(e) FROM EVENT e JOIN e.tags t ");
        if (fromDate != null && toDate != null) {
            jpql.append("JOIN e.tickets tick WHERE tick.date BETWEEN :fromDate AND :toDate ");
        }
        jpql.append("GROUP BY t.category.name");
        
        Query query = entityManager.createQuery(jpql.toString());
        if (fromDate != null && toDate != null) {
            query.setParameter("fromDate", Date.valueOf(fromDate));
            query.setParameter("toDate", Date.valueOf(toDate));
        }

        return query.getResultList();
    }

    // Báo cáo sự kiện theo loại (BR-32)
    public List<Object[]> getEventsByType(String fromDate, String toDate) {
        StringBuilder jpql = new StringBuilder("SELECT e.type, COUNT(e) FROM EVENT e ");
        if (fromDate != null && toDate != null) {
            jpql.append("JOIN e.tickets tick WHERE tick.date BETWEEN :fromDate AND :toDate ");
        }
        jpql.append("GROUP BY e.type");
        
        Query query = entityManager.createQuery(jpql.toString());
        if (fromDate != null && toDate != null) {
            query.setParameter("fromDate", Date.valueOf(fromDate));
            query.setParameter("toDate", Date.valueOf(toDate));
        }

        return query.getResultList();
    }

    // Báo cáo sự kiện theo trạng thái (BR-32)
    public List<Object[]> getEventsByStatus(String fromDate, String toDate) {
        StringBuilder jpql = new StringBuilder("SELECT t.status, COUNT(e) FROM EVENT e JOIN e.tickets t ");
        if (fromDate != null && toDate != null) {
            jpql.append("WHERE t.date BETWEEN :fromDate AND :toDate ");
        }
        jpql.append("GROUP BY t.status");
        
        Query query = entityManager.createQuery(jpql.toString());
        if (fromDate != null && toDate != null) {
            query.setParameter("fromDate", Date.valueOf(fromDate));
            query.setParameter("toDate", Date.valueOf(toDate));
        }

        return query.getResultList();
    }

    // Báo cáo người tham gia theo sự kiện (BR-33)
    public List<Object[]> getParticipantsByEvent(String fromDate, String toDate) {
        StringBuilder jpql = new StringBuilder("SELECT e.name, COUNT(p), (COUNT(p) * 100.0 / t.slots) " +
                "FROM EVENT e JOIN e.tickets t JOIN t.participants p ");
        if (fromDate != null && toDate != null) {
            jpql.append("WHERE t.date BETWEEN :fromDate AND :toDate ");
        }
        jpql.append("GROUP BY e.name, t.slots");
        
        Query query = entityManager.createQuery(jpql.toString());
        if (fromDate != null && toDate != null) {
            query.setParameter("fromDate", Date.valueOf(fromDate));
            query.setParameter("toDate", Date.valueOf(toDate));
        }

        return query.getResultList();
    }

    // Báo cáo đánh giá trung bình (BR-34)
    public List<Object[]> getRatingsReport(String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder(
            "SELECT e.name, AVG(CAST(p.rate AS FLOAT)), STRING_AGG(p.comment, ', ') " +
            "FROM [EVENT] e " +
            "JOIN [TICKET] t ON e.id = t.event " +
            "JOIN PARTICIPANT p ON t.id = p.ticket "
        );

        if (fromDate != null && toDate != null) {
            sql.append("WHERE t.date BETWEEN :fromDate AND :toDate ");
        }
        sql.append("GROUP BY e.name");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (fromDate != null && toDate != null) {
            query.setParameter("fromDate", Date.valueOf(fromDate));
            query.setParameter("toDate", Date.valueOf(toDate));
        }

        return query.getResultList();
    }
}