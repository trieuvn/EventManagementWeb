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
        Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.ticket IN (SELECT t FROM Ticket t WHERE t.event.id = :eventId)", PARTICIPANT.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    public PARTICIPANT getById(int id) {
        return entityManager.find(PARTICIPANT.class, id);
    }

    

    @Transactional
    public void deleteById(int id) {
        PARTICIPANT participant = getById(id);
        if (participant != null) {
            entityManager.remove(participant);
        }
    }

   


    public List<PARTICIPANT> getByUser(USER user) {
        Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.user = :user", PARTICIPANT.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}