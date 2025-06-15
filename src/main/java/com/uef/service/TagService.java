/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

/**
 *
 * @author sang
 */
import com.uef.model.TAG;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class TagService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<TAG> getAll() {
        return entityManager.createQuery("SELECT t FROM TAG t", TAG.class).getResultList();
    }

    public TAG getById(int eventId, String categoryName) {
        Query query = entityManager.createQuery("SELECT t FROM TAG t WHERE t.event.id = :eventId AND t.category.name = :categoryName", TAG.class);
        query.setParameter("eventId", eventId);
        query.setParameter("categoryName", categoryName);
        return (TAG) query.getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public void set(TAG tag) {
        if (tag.getEvent() == null || tag.getCategory() == null) {
            throw new IllegalArgumentException("Event and Category must be set for TAG");
        }
        entityManager.merge(tag);
    }

    @Transactional
    public void deleteById(int eventId, String categoryName) {
        TAG tag = getById(eventId, categoryName);
        if (tag != null) {
            entityManager.remove(tag);
        }
    }
}