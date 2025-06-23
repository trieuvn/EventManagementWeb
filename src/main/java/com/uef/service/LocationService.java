package com.uef.service;

import com.uef.model.LOCATION;
import com.uef.model.TICKET;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LocationService {
    @PersistenceContext
    private EntityManager entityManager;

    // Lấy tất cả địa điểm
    public List<LOCATION> getAll() {
        return entityManager.createQuery("SELECT l FROM LOCATION l", LOCATION.class).getResultList();
    }

    // Lấy địa điểm theo ID
    public LOCATION getById(int id) {
        return entityManager.find(LOCATION.class, id);
    }

    // Thêm hoặc cập nhật địa điểm
    public boolean set(LOCATION location) {
        // Kiểm tra các trường bắt buộc
        if (location.getName() == null || location.getLatitude() == 0 || location.getLongitude() == 0) {
            throw new IllegalArgumentException("Required fields are missing");
        }
        try {
            if (location.getId() == 0) {
                entityManager.persist(location);
            } else {
                entityManager.merge(location);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa địa điểm
    public boolean deleteById(int id) {
        LOCATION location = getById(id);
        if (location != null) {
            // Kiểm tra nếu địa điểm có vé liên quan (BR-09)
            Query query = entityManager.createQuery("SELECT t FROM TICKET t WHERE t.location.id = :locationId");
            query.setParameter("locationId", id);
            if (!query.getResultList().isEmpty()) {
                throw new IllegalStateException("Cannot delete location with associated tickets");
            }
            entityManager.remove(location);
            return true;
        }
        return false;
    }

    // Lấy danh sách vé theo địa điểm (mục 64)
    public List<TICKET> getTickets(int locationId) {
        Query query = entityManager.createQuery(
            "SELECT t FROM TICKET t WHERE t.location.id = :locationId", TICKET.class);
        query.setParameter("locationId", locationId);
        return query.getResultList();
    }

    // Lấy đường dẫn bản đồ (mục 65)
    public String getRoadMap(double latitude, double longitude) {
        // Giả lập trả về đường dẫn JSP cho bản đồ (tích hợp MapTiler Cloud API)
        return "utils/openstreetmap"; // Trả về tên JSP như trong utils.Map
    }
}