/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

/**
 *
 * @author sang
 */
import com.uef.model.CHANGE;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class ChangeService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CHANGE> getAll() {
        return entityManager.createQuery("SELECT c FROM CHANGE c", CHANGE.class).getResultList();
    }

    public CHANGE getById(int id) {
        return entityManager.find(CHANGE.class, id);
    }

    @Transactional
    public void set(CHANGE change) {
        if (change.getId() == 0) {
            entityManager.persist(change);
        } else {
            entityManager.merge(change);
        }
    }

    @Transactional
    public void deleteById(int id) {
        CHANGE change = getById(id);
        if (change != null) {
            entityManager.remove(change);
        }
    }

    public List<CHANGE> getByEvent(int eventId) {
        Query query = entityManager.createQuery("SELECT c FROM CHANGE c WHERE c.event.id = :eventId", CHANGE.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}