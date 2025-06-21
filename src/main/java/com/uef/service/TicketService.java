package com.uef.service;

import com.uef.model.TICKET;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TicketService {
    @PersistenceContext
    private EntityManager entityManager;

    // Lấy tất cả vé
    public List<TICKET> getAll() {
        return entityManager.createQuery("SELECT t FROM TICKET t", TICKET.class).getResultList();
    }

    // Lấy vé theo ID (mục 88)
    public TICKET getById(int id) {
        return entityManager.find(TICKET.class, id);
    }

    // Thêm hoặc cập nhật vé (mục 110)
    public boolean set(TICKET ticket) {
        // Kiểm tra type của vé khớp với type của sự kiện (BR-24)
        if (ticket.getEvent() != null) {
            String eventType = ticket.getEvent().getType();
            String ticketType = ticket.getType();
            if (eventType.equals("online") && !ticketType.equals("online") ||
                eventType.equals("offline") && !ticketType.equals("offline") ||
                !eventType.equals("hybrid") && !ticketType.equals(eventType)) {
                throw new IllegalArgumentException("Ticket type does not match event type");
            }
        }
        // Kiểm tra các trường bắt buộc (BR-23)
        if (ticket.getName() == null || ticket.getDate() == null || ticket.getDuration() == null ||
            ticket.getType() == null || ticket.getEvent() == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }
        try {
            if (ticket.getId() == 0) {
                entityManager.persist(ticket);
            } else {
                entityManager.merge(ticket);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa vé
    public void deleteById(int id) {
        TICKET ticket = getById(id);
        if (ticket != null) {
            // Kiểm tra nếu vé có người tham gia
            Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.ticket.id = :ticketId");
            query.setParameter("ticketId", id);
            if (!query.getResultList().isEmpty()) {
                ticket.setStatus(2); // Đánh dấu completed thay vì xóa
                set(ticket);
            } else {
                entityManager.remove(ticket);
            }
        }
    }

    // Lấy vé theo sự kiện
    public List<TICKET> getByEvent(int eventId) {
        Query query = entityManager.createQuery(
            "SELECT t FROM TICKET t WHERE t.event.id = :eventId", TICKET.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}