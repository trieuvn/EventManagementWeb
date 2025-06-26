package com.uef.service;

import com.uef.model.CHANGE;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ChangeService {
    @PersistenceContext
    private EntityManager entityManager;

    // Lấy tất cả thay đổi
    public List<CHANGE> getAll() {
        return entityManager.createQuery("SELECT c FROM CHANGE c", CHANGE.class).getResultList();
    }

    // Lấy thay đổi theo ID
    public CHANGE getById(int id) {
        return entityManager.find(CHANGE.class, id);
    }

    // Thêm hoặc cập nhật thay đổi (mục 61-62)
    public boolean set(CHANGE change) {
        // Kiểm tra các trường bắt buộc
        if (change.getSubject() == null || change.getEvent() == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }
        try {
            if (change.getId() == 0) {
                entityManager.persist(change);
            } else {
                entityManager.merge(change);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa thay đổi
    public boolean deleteById(int id) {
        CHANGE change = getById(id);
        if (change != null) {
            entityManager.remove(change);
            return true;
        }
        return false;
    }

    // Lấy thay đổi theo sự kiện (mục 61)
    public List<CHANGE> getByEvent(int eventId) {
        Query query = entityManager.createQuery(
            "SELECT c FROM CHANGE c WHERE c.event.id = :eventId", CHANGE.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    // Gửi email thông báo thay đổi (mục 62)
    public boolean sendEmail(CHANGE change) {
        // Giả lập gửi email (tích hợp JavaMail sẽ được xử lý ở tầng controller hoặc util)
        try {
            // Kiểm tra sự kiện tồn tại
            if (change.getEvent() == null || getById(change.getId()) == null) {
                return false;
            }
            // Logic gửi email sẽ được thêm sau khi tích hợp JavaMail (BR-30)
            return true; // Giả lập thành công
        } catch (Exception e) {
            return false;
        }
    }
}