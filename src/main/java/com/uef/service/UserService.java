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

    public USER getByEmail(String email) {
        Query query = entityManager.createQuery("SELECT u FROM USER u WHERE u.email = :email", USER.class);
        query.setParameter("email", email);
        try {
            return (USER) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean authenticate(String email, String password) {
        Query query = entityManager.createQuery("SELECT u FROM USER u WHERE u.email = :email AND u.password = :password", USER.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<USER> users = query.getResultList();
        return !users.isEmpty();
    }

    @Transactional
    public void updatePassword(String email, String newPassword) {
        USER user = getByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            entityManager.merge(user);
        }
    }
}