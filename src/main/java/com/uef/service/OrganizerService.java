package com.uef.service;

import com.uef.model.EVENT;
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

    // Lấy tất cả nhà tổ chức
    public List<ORGANIZER> getAll() {
        return entityManager.createQuery("SELECT o FROM ORGANIZER o", ORGANIZER.class).getResultList();
    }

    // Lấy nhà tổ chức theo ID
    public ORGANIZER getById(int id) {
        return entityManager.find(ORGANIZER.class, id);
    }

    // Thêm hoặc cập nhật nhà tổ chức
    public boolean set(ORGANIZER organizer) {
        // Kiểm tra email duy nhất với điều kiện loại trừ ID hiện tại nếu có
        StringBuilder jpql = new StringBuilder("SELECT COUNT(o) FROM ORGANIZER o WHERE o.email = :email");
        if (organizer.getId() > 0) {
            jpql.append(" AND o.id != :id");
        }
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("email", organizer.getEmail());
        if (organizer.getId() > 0) {
            query.setParameter("id", organizer.getId());
        }

        Long count = (Long) query.getSingleResult();
        if (count > 0) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Kiểm tra các trường bắt buộc
        if (organizer.getEmail() == null || organizer.getLastName() == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }

        try {
            if (organizer.getId() == 0) {
                entityManager.persist(organizer);
            } else {
                entityManager.merge(organizer);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa nhà tổ chức
    public boolean deleteById(int id) {
        ORGANIZER organizer = getById(id);
        if (organizer != null) {
            // Kiểm tra nếu nhà tổ chức có sự kiện liên quan
            Query query = entityManager.createQuery("SELECT e FROM EVENT e WHERE e.organizer.id = :organizerId");
            query.setParameter("organizerId", id);
            if (!query.getResultList().isEmpty()) {
                throw new IllegalStateException("Cannot delete organizer with associated events");
            }
            entityManager.remove(organizer);
            return true;
        }
        return false;
    }

    // Lấy danh sách sự kiện theo nhà tổ chức (mục 67)
    public List<EVENT> getEvents(int organizerId) {
        Query query = entityManager.createQuery(
            "SELECT e FROM EVENT e WHERE e.organizer.id = :organizerId", EVENT.class);
        query.setParameter("organizerId", organizerId);
        return query.getResultList();
    }

    // Tạo sự kiện mới cho nhà tổ chức (mục 68)
    public EVENT createEvent(ORGANIZER organizer, EVENT event) {
        // Kiểm tra nhà tổ chức tồn tại
        ORGANIZER existingOrganizer = getById(organizer.getId());
        if (existingOrganizer == null) {
            throw new IllegalArgumentException("Organizer does not exist");
        }
        // Kiểm tra các trường bắt buộc của sự kiện
        if (event.getName() == null || event.getType() == null || event.getTarget() == null) {
            throw new IllegalArgumentException("Required event fields are missing");
        }
        event.setOrganizer(existingOrganizer);
        entityManager.persist(event);
        return event;
    }
}