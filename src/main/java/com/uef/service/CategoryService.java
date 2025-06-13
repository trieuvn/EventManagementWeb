package com.uef.service;

import com.uef.model.CATEGORY;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CATEGORY> getAll() {
        return entityManager.createQuery("SELECT c FROM CATEGORY c", CATEGORY.class).getResultList();
    }

    public CATEGORY getById(int id) {
        return entityManager.find(CATEGORY.class, id);
    }

   

    public void deleteById(int id) {
        CATEGORY category = getById(id);
        if (category != null) {
            entityManager.remove(category);
        }
    }

    public List<CATEGORY> searchByName(String name) {
        String jpql = "SELECT c FROM CATEGORY c WHERE c.name LIKE :name";
        Query query = entityManager.createQuery(jpql, CATEGORY.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}