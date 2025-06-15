/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

/**
 *
 * @author sang
 */
import com.uef.model.ORGANIZER;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class OrganizerService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ORGANIZER> getAll() {
        return entityManager.createQuery("SELECT o FROM ORGANIZER o", ORGANIZER.class).getResultList();
    }

    public ORGANIZER getById(int id) {
        return entityManager.find(ORGANIZER.class, id);
    }

    @Transactional
    public void set(ORGANIZER organizer) {
        if (organizer.getId() == 0) {
            entityManager.persist(organizer);
        } else {
            entityManager.merge(organizer);
        }
    }

    @Transactional
    public void deleteById(int id) {
        ORGANIZER organizer = getById(id);
        if (organizer != null) {
            entityManager.remove(organizer);
        }
    }
}