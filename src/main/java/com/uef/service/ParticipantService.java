package com.uef.service;

import com.uef.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PARTICIPANT> getAllByEvent(int eventId) {
        Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.ticket IN (SELECT t FROM TICKET t WHERE t.event.id = :eventId)", PARTICIPANT.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    public PARTICIPANT getById(int id) {
        return entityManager.find(PARTICIPANT.class, id);
    }


    @Transactional
    public void confirmRegistration(int participantId, int confirmCode) {
        PARTICIPANT participant = entityManager.find(PARTICIPANT.class, participantId);
        if (participant != null && participant.getTicket().getConfirmCode() == confirmCode) {
            participant.setStatus(1); // Assuming 1 means "Confirmed"
            entityManager.merge(participant);
        }
    }

    @Transactional
    public boolean registerParticipant(PARTICIPANT participant) {
        TICKET ticket = participant.getTicket();
        if (ticket.getSlots() > 0) {
            participant.setStatus(0); // Assuming 0 means "Pending"
            entityManager.persist(participant);
            ticket.setSlots(ticket.getSlots() - 1);
            entityManager.merge(ticket);
            return true;
        }
        return false;
    }

    public int getParticipantCount(int eventId) {
        String jpql = "SELECT COUNT(p) FROM PARTICIPANT p WHERE p.ticket.event.id = :eventId";
        Query query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("eventId", eventId);
        return ((Long) query.getSingleResult()).intValue();
    }

    @Transactional
    public void cancelRegistration(int participantId) {
        PARTICIPANT participant = entityManager.find(PARTICIPANT.class, participantId);
        if (participant != null) {
            TICKET ticket = participant.getTicket();
            if (ticket != null) {
                ticket.setSlots(ticket.getSlots() + 1);  // Tăng slot vé trở lại
                entityManager.merge(ticket);
            }
            entityManager.remove(participant);  // Xóa người tham gia
        }
    }
}
