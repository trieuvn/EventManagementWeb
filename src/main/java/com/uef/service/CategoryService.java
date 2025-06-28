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

    // Tìm kiếm danh mục theo tên
    public List<CATEGORY> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAll();
        }
        return entityManager.createQuery("SELECT c FROM CATEGORY c WHERE LOWER(c.name) LIKE :name", CATEGORY.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getResultList();
    }

    // Lấy danh mục theo tên
    public CATEGORY getById(String name) {
        return entityManager.find(CATEGORY.class, name);
    }

    // Thêm hoặc cập nhật danh mục (BR-27)
    public boolean set(CATEGORY category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không được để trống.");
        }
        // Kiểm tra tên danh mục duy nhất
        CATEGORY existing = getById(category.getName());
        if (existing != null && !existing.equals(category)) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại.");
        }
        try {
            if (existing == null) {
                entityManager.persist(category);
            } else {
                entityManager.merge(category);
            }
            return true;
        } catch (Exception e) {
            throw new IllegalStateException("Lỗi hệ thống khi lưu danh mục: " + e.getMessage());
        }
    }

    // Xóa danh mục (BR-26)
    public boolean delete(CATEGORY category) {
        CATEGORY existing = getById(category.getName());
        if (existing != null) {
            // Xóa các TAG liên quan trước
            Query query = entityManager.createQuery("DELETE FROM TAG t WHERE t.category.name = :categoryName");
            query.setParameter("categoryName", category.getName());
            query.executeUpdate();
            entityManager.remove(existing);
            return true;
        }
        return false;
    }
}