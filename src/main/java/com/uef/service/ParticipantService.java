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

    public List<PARTICIPANT> findAllByEvent(int eventId) {
        Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.ticket IN (SELECT t FROM Ticket t WHERE t.event.id = :eventId)", PARTICIPANT.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    public PARTICIPANT findById(int id) {
        return entityManager.find(PARTICIPANT.class, id);
    }

    @Transactional
    public void save(PARTICIPANT participant) {
        if (participant.getId() == 0) {
            entityManager.persist(participant);
        } else {
            entityManager.merge(participant);
        }
    }

    @Transactional
    public void deleteById(int id) {
        PARTICIPANT participant = findById(id);
        if (participant != null) {
            entityManager.remove(participant);
        }
    }

    @Transactional
    public void confirmRegistration(int id, int confirmCode) {
        PARTICIPANT participant = findById(id);
        if (participant != null && participant.getTicket().getConfirmCode() == confirmCode) {
            participant.setStatus(1); // Assuming 1 = confirmed
            save(participant);
        }
    }

    @Transactional
    public void cancelRegistration(int id) {
        PARTICIPANT participant = findById(id);
        if (participant != null && participant.getTicket().getEvent().getType().equals("upcoming")) {
            participant.setStatus(0); // Assuming 0 = canceled
            save(participant);
        }
    }

    public List<PARTICIPANT> findByUser(USER user) {
        Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.user = :user", PARTICIPANT.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}