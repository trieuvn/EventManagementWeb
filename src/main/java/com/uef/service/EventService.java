package com.uef.service;

import com.uef.model.EVENT;
import com.uef.model.ORGANIZER;
import com.uef.model.TAG;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class EventService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrganizerService organizerService;

    // Lấy tất cả sự kiện (mục 84)
    public List<EVENT> getAll() {
        return entityManager.createQuery("SELECT e FROM EVENT e", EVENT.class).getResultList();
    }

    // Lấy sự kiện theo ID (mục 87, 103)
    public EVENT getById(int id) {
        return entityManager.find(EVENT.class, id);
    }

    // Thêm hoặc cập nhật sự kiện (mục 100)
    public void set(EVENT event) {
        // Kiểm tra các trường bắt buộc (BR-23)
//        if (event.getName() == null || event.getType() == null || event.getTarget() == null || event.getOrganizer() == null) {
//            throw new IllegalArgumentException("Required fields are missing");
//        }
        if (event.getId() == 0) {
            entityManager.persist(event);
        } else {
            entityManager.merge(event);
        }
    }

    // Xóa sự kiện (mục 101, đã sửa theo yêu cầu trước)
    public boolean delete(EVENT event) {
        EVENT existingEvent = getById(event.getId());
        if (existingEvent != null) {
            // Kiểm tra nếu sự kiện có người tham gia (BR-22, BR-25)
            Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.ticket.event.id = :eventId");
            query.setParameter("eventId", event.getId());
            if (!query.getResultList().isEmpty()) {
                throw new IllegalStateException("Không thể xóa sự kiện vì đã có người tham gia.");
            } else {
                // Xóa các tag liên quan trước (BR-26, BR-27)
                Query tagQuery = entityManager.createQuery("DELETE FROM TAG t WHERE t.event.id = :eventId");
                tagQuery.setParameter("eventId", event.getId());
                tagQuery.executeUpdate();
                entityManager.remove(existingEvent);
                return true;
            }
        }
        return false;
    }

    // Cập nhật trạng thái sự kiện (mục 102)
    public boolean setStatus(EVENT event) {
        EVENT existingEvent = getById(event.getId());
        if (existingEvent != null) {
            existingEvent.setType(event.getType()); // Cập nhật trạng thái (BR-25)
            set(existingEvent);
            return true;
        }
        return false;
    }

    // Lấy sự kiện theo danh mục (mục 86)
    public List<EVENT> getByCategory(String category) {
        Query query = entityManager.createQuery(
                "SELECT e FROM EVENT e JOIN e.tags t WHERE t.category.name = :category", EVENT.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    // Lấy sự kiện sắp tới (mục 82)
    public List<EVENT> getUpcomingEvents() {
        return entityManager.createQuery(
                "SELECT e FROM EVENT e JOIN e.tickets t WHERE t.date > CURRENT_DATE AND t.status = 0", EVENT.class)
                .getResultList();
    }

    // Lấy sự kiện đã qua (mục 83)
    public List<EVENT> getPastEvents() {
        return entityManager.createQuery(
                "SELECT e FROM EVENT e JOIN e.tickets t WHERE t.date < CURRENT_DATE AND t.status = 2", EVENT.class)
                .getResultList();
    }

    // Tìm kiếm sự kiện (mục 85)
    public List<EVENT> searchEvents(String name, String category, Date date) {
        StringBuilder jpql = new StringBuilder("SELECT e FROM EVENT e LEFT JOIN e.tags t WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            jpql.append(" AND LOWER(e.name) LIKE :name");
        }
        if (category != null && !category.isEmpty()) {
            jpql.append(" AND t.category.name = :category");
        }
        if (date != null) {
            jpql.append(" AND EXISTS (SELECT t FROM TICKET t WHERE t.event = e AND t.date = :date)");
        }
        Query query = entityManager.createQuery(jpql.toString(), EVENT.class);
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name.toLowerCase() + "%");
        }
        if (category != null && !category.isEmpty()) {
            query.setParameter("category", category);
        }
        if (date != null) {
            query.setParameter("date", date);
        }
        return query.getResultList();
    }

    // Tạo sự kiện mới cho nhà tổ chức (mục 68)
    public EVENT createEvent(ORGANIZER organizer, EVENT event) {
        // Kiểm tra nhà tổ chức tồn tại
        ORGANIZER existingOrganizer = organizerService.getById(organizer.getId());
        if (existingOrganizer == null) {
            throw new IllegalArgumentException("Nhà tổ chức không tồn tại");
        }
        // Kiểm tra các trường bắt buộc của sự kiện (BR-23)
        if (event.getName() == null || event.getType() == null || event.getTarget() == null) {
            throw new IllegalArgumentException("Các trường bắt buộc của sự kiện bị thiếu");
        }
        event.setOrganizer(existingOrganizer);
        entityManager.persist(event);
        return event;
    }
}
