package com.uef.service;

import com.uef.model.CATEGORY;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CATEGORY> findAll() {
        return entityManager.createQuery("SELECT c FROM CATEGORY c", CATEGORY.class).getResultList();
    }

    public CATEGORY findById(int id) {
        return entityManager.find(CATEGORY.class, id);
    }

    public void save(CATEGORY category) {
        if (category.getId() == 0) {
            entityManager.persist(category);
        } else {
            entityManager.merge(category);
        }
    }

    public void deleteById(int id) {
        CATEGORY category = findById(id);
        if (category != null) {
            entityManager.remove(category);
        }
    }
}