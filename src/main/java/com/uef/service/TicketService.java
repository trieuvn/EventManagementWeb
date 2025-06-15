/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

/**
 *
 * @author sang
 */
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

    public List<TICKET> getAll() {
        return entityManager.createQuery("SELECT t FROM TICKET t", TICKET.class).getResultList();
    }

    public TICKET getById(int id) {
        return entityManager.find(TICKET.class, id);
    }

    @Transactional
    public void set(TICKET ticket) {
        if (ticket.getId() == 0) {
            entityManager.persist(ticket);
        } else {
            entityManager.merge(ticket);
        }
    }

    @Transactional
    public void deleteById(int id) {
        TICKET ticket = getById(id);
        if (ticket != null) {
            entityManager.remove(ticket);
        }
    }

    public List<TICKET> getByEvent(int eventId) {
        Query query = entityManager.createQuery("SELECT t FROM TICKET t WHERE t.event.id = :eventId", TICKET.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}