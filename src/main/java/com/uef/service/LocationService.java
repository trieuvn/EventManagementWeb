/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.service;

/**
 *
 * @author sang
 */
import com.uef.model.LOCATION;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class LocationService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<LOCATION> getAll() {
        return entityManager.createQuery("SELECT l FROM LOCATION l", LOCATION.class).getResultList();
    }

    public LOCATION getById(int id) {
        return entityManager.find(LOCATION.class, id);
    }

    @Transactional
    public void set(LOCATION location) {
        if (location.getId() == 0) {
            entityManager.persist(location);
        } else {
            entityManager.merge(location);
        }
    }

    @Transactional
    public void deleteById(int id) {
        LOCATION location = getById(id);
        if (location != null) {
            entityManager.remove(location);
        }
    }
}