package com.uef.service;

import com.uef.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class EventService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<EVENT> getAll() {
        return entityManager.createQuery("SELECT e FROM EVENT e", EVENT.class).getResultList();
    }

    public EVENT getById(int id) {
        return entityManager.find(EVENT.class, id);
    }

    @Transactional
    public void set(EVENT event) {
        if (event.getId() == 0) {
            entityManager.persist(event);
        } else {
            entityManager.merge(event);
        }
    }

    @Transactional
    public void deleteById(int id) {
        EVENT event = getById(id);
        if (event != null) {
            entityManager.remove(event);
        }
    }

    @Transactional
    public void setStatus(int id, String status) {
        EVENT event = getById(id);
        if (event != null) {
            event.setType(status);
            set(event);
        }
    }

    public List<EVENT> getByCategory(String category) {
        Query query = entityManager.createQuery("SELECT e FROM EVENT e JOIN e.tags t WHERE t.category = :category", EVENT.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<EVENT> getUpcomingEvents() {
        return entityManager.createQuery("SELECT e FROM EVENT e WHERE e.type = 'upcoming'", EVENT.class).getResultList();
    }

    public List<EVENT> getPastEvents() {
        return entityManager.createQuery("SELECT e FROM EVENT e WHERE e.type = 'completed'", EVENT.class).getResultList();
    }

    public List<EVENT> searchEvents(String name, String category, Date date) {
        StringBuilder jpql = new StringBuilder("SELECT e FROM EVENT e LEFT JOIN e.tags t WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            jpql.append(" AND e.name LIKE :name");
        }
        if (category != null && !category.isEmpty()) {
            jpql.append(" AND t.category = :category");
        }
        if (date != null) {
            jpql.append(" AND e.date = :date");
        }
        Query query = entityManager.createQuery(jpql.toString(), EVENT.class);
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (category != null && !category.isEmpty()) {
            query.setParameter("category", category);
        }
        if (date != null) {
            query.setParameter("date", date);
        }
        return query.getResultList();
    }

    public List<EVENT> getEventsByDate(Date date) {
        String jpql = "SELECT e FROM EVENT e JOIN e.tickets t WHERE t.date = :date";
        Query query = entityManager.createQuery(jpql, EVENT.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    public List<TICKET> getTicketsByEvent(int eventId) {
        Query query = entityManager.createQuery("SELECT t FROM TICKET t WHERE t.event.id = :eventId", TICKET.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    public ORGANIZER getOrganizerByEvent(int eventId) {
        EVENT event = entityManager.find(EVENT.class, eventId);
        return event != null ? event.getOrganizer() : null;
    }

}