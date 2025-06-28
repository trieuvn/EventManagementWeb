package com.uef.service;

import com.uef.model.USER;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    // Xác thực người dùng (mục 91)
    public USER authenticate(String email, String password) {
        Query query = entityManager.createQuery(
                "SELECT u FROM USER u WHERE u.email = :email AND u.password = :password", USER.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return (USER) query.getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy hoặc thông tin không khớp
        }
    }

    // Lấy người dùng theo email (mục 93)
    public USER getByEmail(String email) {
        Query query = entityManager.createQuery(
                "SELECT u FROM USER u WHERE u.email = :email", USER.class);
        query.setParameter("email", email);
        try {
            return (USER) query.getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy
        }
    }

    // Thêm hoặc cập nhật người dùng (mục 94)
    public boolean set(USER user) {
        // Kiểm tra email duy nhất (BR-02)
        if (user.getEmail() != null && getByEmail(user.getEmail()) != null && !getByEmail(user.getEmail()).equals(user)) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Kiểm tra định dạng email và các trường bắt buộc (BR-01, BR-05)
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")
                || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null
                || user.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Invalid user data");
        }
        try {
            if (getByEmail(user.getEmail()) == null) {
                entityManager.persist(user);
            } else {
                entityManager.merge(user);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateNameAndPhone(USER user) {
        if (user == null || user.getEmail() == null
                || user.getFirstName() == null || user.getLastName() == null
                || user.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Thông tin không hợp lệ.");
        }

        try {
            USER existing = entityManager.find(USER.class, user.getEmail()); // tìm theo email
            if (existing == null) {
                throw new IllegalArgumentException("Người dùng không tồn tại.");
            }

            // Cập nhật thông tin được phép
            existing.setFirstName(user.getFirstName());
            existing.setLastName(user.getLastName());
            existing.setPhoneNumber(user.getPhoneNumber());

            entityManager.merge(existing);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
