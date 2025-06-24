package com.uef.service;

import com.uef.model.CATEGORY;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    @PersistenceContext
    private EntityManager entityManager;

    // Lấy tất cả danh mục
    public List<CATEGORY> getAll() {
        return entityManager.createQuery("SELECT c FROM CATEGORY c", CATEGORY.class).getResultList();
    }

    // Lấy danh mục theo ID
    public CATEGORY getById(String name) {
        return entityManager.find(CATEGORY.class, name);
    }

    // Thêm hoặc cập nhật danh mục (mục 105)
    public boolean set(CATEGORY category) {
        // Kiểm tra tên danh mục duy nhất (BR-27)
        if (category.getName() != null && getById(category.getName()) != null && !getById(category.getName()).equals(category)) {
            throw new IllegalArgumentException("Category name already exists");
        }
        try {
            if (getById(category.getName()) == null) {
                entityManager.persist(category);
            } else {
                entityManager.merge(category);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa danh mục (mục 106)
    public boolean delete(CATEGORY category) {
        CATEGORY existing = getById(category.getName());
        if (existing != null) {
            // Xóa các tag liên quan trước (BR-26, BR-27)
            Query query = entityManager.createQuery("DELETE FROM TAG t WHERE t.category.name = :categoryName");
            query.setParameter("categoryName", category.getName());
            query.executeUpdate();
            entityManager.remove(existing);
            return true;
        }
        return false;
    }
}