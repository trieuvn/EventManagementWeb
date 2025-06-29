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
        if (event.getId() == 0) {
            entityManager.persist(event);
        } else {
            entityManager.merge(event);
        }
    }

    // Xóa sự kiện (mục 101, đã sửa để xử lý TICKET, PARTICIPANT, và TAG)
    public boolean delete(EVENT event) {
        EVENT existingEvent = getById(event.getId());
        if (existingEvent != null) {
            try {
                // Xóa các PARTICIPANT liên quan trước
                Query participantQuery = entityManager.createQuery("DELETE FROM PARTICIPANT p WHERE p.ticket.event.id = :eventId");
                participantQuery.setParameter("eventId", event.getId());
                int participantCount = participantQuery.executeUpdate();
                System.out.println("Deleted " + participantCount + " participants for event ID: " + event.getId());

                // Xóa các TICKET liên quan
                Query ticketQuery = entityManager.createQuery("DELETE FROM TICKET t WHERE t.event.id = :eventId");
                ticketQuery.setParameter("eventId", event.getId());
                int ticketCount = ticketQuery.executeUpdate();
                System.out.println("Deleted " + ticketCount + " tickets for event ID: " + event.getId());

                // Xóa các TAG liên quan (BR-26, BR-27)
                Query tagQuery = entityManager.createQuery("DELETE FROM TAG t WHERE t.event.id = :eventId");
                tagQuery.setParameter("eventId", event.getId());
                int tagCount = tagQuery.executeUpdate();
                System.out.println("Deleted " + tagCount + " tags for event ID: " + event.getId());

                // Xóa sự kiện
                entityManager.remove(existingEvent);
                System.out.println("Deleted event with ID: " + event.getId());
                return true;
            } catch (Exception e) {
                System.err.println("Error deleting event ID " + event.getId() + ": " + e.getMessage());
                throw new IllegalStateException("Lỗi khi xóa sự kiện: " + e.getMessage());
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
//    public Long countUpcomingEvents() {
//        return entityManager.createQuery(
//                "SELECT COUNT(DISTINCT e) FROM EVENT e JOIN e.tickets t WHERE t.date > CURRENT_DATE AND t.status = 1", Long.class)
//                .getSingleResult();
//    }
//
//    public Long countPastEvents() {
//        return entityManager.createQuery(
//                "SELECT COUNT(DISTINCT e) FROM EVENT e JOIN e.tickets t WHERE t.date < CURRENT_DATE AND t.status = 0", Long.class)
//                .getSingleResult();
//    }

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
        ORGANIZER existingOrganizer = organizerService.getById(organizer.getId());
        if (existingOrganizer == null) {
            throw new IllegalArgumentException("Nhà tổ chức không tồn tại");
        }
        if (event.getName() == null || event.getType() == null || event.getTarget() == null) {
            throw new IllegalArgumentException("Các trường bắt buộc của sự kiện bị thiếu");
        }
        event.setOrganizer(existingOrganizer);
        entityManager.persist(event);
        return event;
    }
}
