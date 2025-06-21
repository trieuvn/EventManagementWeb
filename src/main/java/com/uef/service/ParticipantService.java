package com.uef.service;

import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParticipantService {
    @PersistenceContext
    private EntityManager entityManager;

    // Lấy danh sách người tham gia theo sự kiện (mục 107)
    public List<PARTICIPANT> getAllByEvent(int eventId) {
        Query query = entityManager.createQuery(
            "SELECT p FROM PARTICIPANT p WHERE p.ticket.event.id = :eventId", PARTICIPANT.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    // Lấy người tham gia theo user (mục 97)
    public List<PARTICIPANT> getByUser(USER user) {
        Query query = entityManager.createQuery(
            "SELECT p FROM PARTICIPANT p WHERE p.user = :user", PARTICIPANT.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    // Xóa người tham gia (mục 89)
    public void delete(PARTICIPANT participant) {
        PARTICIPANT existing = entityManager.find(PARTICIPANT.class, new PARTICIPANT(participant.getUser(), participant.getTicket(), 0, 0, null));
        if (existing != null) {
            entityManager.remove(existing);
        }
    }

    // Đặt đánh giá cho vé (mục 98)
    public void setRate(USER user, TICKET ticket, int rate, String comment) {
        // Kiểm tra điều kiện đánh giá (BR-17, BR-18, BR-19)
        Query query = entityManager.createQuery(
            "SELECT p FROM PARTICIPANT p WHERE p.user = :user AND p.ticket = :ticket AND p.status = 1", PARTICIPANT.class);
        query.setParameter("user", user);
        query.setParameter("ticket", ticket);
        PARTICIPANT participant = (PARTICIPANT) query.getSingleResult();
        if (participant == null) {
            throw new IllegalStateException("User has not checked in for this event");
        }
        if (participant.getRate() != 0) {
            throw new IllegalStateException("User has already rated this event");
        }
        if (rate < 0 || rate > 5) {
            throw new IllegalArgumentException("Rate must be between 0 and 5");
        }
        participant.setRate(rate);
        participant.setComment(comment);
        entityManager.merge(participant);
    }
}