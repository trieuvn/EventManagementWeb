package com.uef.service;

import com.uef.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    public USER findByEmail(String email) {
        Query query = entityManager.createQuery("SELECT u FROM USER u WHERE u.email = :email", USER.class);
        query.setParameter("email", email);
        return (USER) query.getSingleResult();
    }

    @Transactional
    public void save(USER user) {
        if (user.getId() == 0) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    public boolean authenticate(String email, String password) {
        Query query = entityManager.createQuery("SELECT u FROM USER u WHERE u.email = :email AND u.password = :password", USER.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<USER> users = query.getResultList();
        return !users.isEmpty();
    }
}