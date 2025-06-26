package com.uef.service;

import com.uef.model.CATEGORY;
import com.uef.model.EVENT;
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

    // Lấy tất cả tag
    public List<TAG> getAll() {
        return entityManager.createQuery("SELECT t FROM TAG t", TAG.class).getResultList();
    }

    // Lấy tag theo ID (eventId, categoryName) (mục 71-73)
    public TAG getById(int eventId, String categoryName) {
        Query query = entityManager.createQuery(
            "SELECT t FROM TAG t WHERE t.event.id = :eventId AND t.category.name = :categoryName", TAG.class);
        query.setParameter("eventId", eventId);
        query.setParameter("categoryName", categoryName);
        return (TAG) query.getResultList().stream().findFirst().orElse(null);
    }

    // Thêm hoặc cập nhật tag
    public boolean set(TAG tag) {
        // Kiểm tra event và category tồn tại
        if (tag.getEvent() == null || tag.getCategory() == null) {
            throw new IllegalArgumentException("Event and Category must be set for TAG");
        }
        // Kiểm tra tag đã tồn tại
        TAG existing = getById(tag.getEvent().getId(), tag.getCategory().getName());
        if (existing != null && !existing.equals(tag)) {
            throw new IllegalArgumentException("Tag already exists for this event and category");
        }
        try {
            if (existing == null) {
                entityManager.persist(tag);
            } else {
                entityManager.merge(tag);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa tag (mục 71)
    public boolean deleteById(int eventId, String categoryName) {
        TAG tag = getById(eventId, categoryName);
        if (tag != null) {
            entityManager.remove(tag);
            return true;
        }
        return false;
    }

    // Lấy sự kiện từ tag (mục 72)
    public EVENT getEvent(int eventId, String categoryName) {
        TAG tag = getById(eventId, categoryName);
        return tag != null ? tag.getEvent() : null;
    }

    // Lấy danh mục từ tag (mục 73)
    public CATEGORY getCategory(int eventId, String categoryName) {
        TAG tag = getById(eventId, categoryName);
        return tag != null ? tag.getCategory() : null;
    }
}