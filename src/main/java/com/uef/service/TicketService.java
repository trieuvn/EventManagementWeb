package com.uef.service;

import com.uef.model.TICKET;
import com.uef.model.LOCATION;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TicketService {
    @PersistenceContext
    private EntityManager entityManager;

    // Lấy tất cả vé
    public List<TICKET> getAll() {
        return entityManager.createQuery("SELECT t FROM TICKET t", TICKET.class).getResultList();
    }

    // Lấy vé theo ID (mục 88)
    public TICKET getById(int id) {
        return entityManager.find(TICKET.class, id);
    }

    // Thêm hoặc cập nhật vé (mục 110)
    @Transactional(rollbackOn = Exception.class)
    public boolean set(TICKET ticket) {
        System.out.println("Setting ticket at " + java.time.LocalDateTime.now() + ": Name = " + ticket.getName() + 
                           ", Event ID = " + (ticket.getEvent() != null ? ticket.getEvent().getId() : "null") + 
                           ", Type = " + ticket.getType() + ", QrCode = " + ticket.getQrCode() + 
                           ", Location ID = " + (ticket.getLocation() != null ? ticket.getLocation().getId() : "null") + 
                           ", Latitude = " + (ticket.getLocation() != null ? ticket.getLocation().getLatitude() : "null") + 
                           ", Longitude = " + (ticket.getLocation() != null ? ticket.getLocation().getLongitude() : "null"));

        // Đảm bảo qrCode không null
        if (ticket.getQrCode() == null || ticket.getQrCode().trim().isEmpty()) {
            ticket.setQrCode("QR_DEFAULT_" + System.currentTimeMillis());
            System.out.println("QrCode was null, set to: " + ticket.getQrCode());
        }
        // Kiểm tra type của vé khớp với type của sự kiện (BR-24)
        if (ticket.getEvent() != null && ticket.getEvent().getType() != null) {
            String eventType = ticket.getEvent().getType().toLowerCase();
            String ticketType = ticket.getType().toLowerCase();
            if ("online".equals(eventType) && !"online".equals(ticketType) ||
                "offline".equals(eventType) && !"offline".equals(ticketType) ||
                "hybrid".equals(eventType) && !("online".equals(ticketType) || "offline".equals(ticketType) || "hybrid".equals(ticketType))) {
                throw new IllegalArgumentException("Ticket type " + ticketType + " does not match event type " + eventType);
            }
        }
        // Kiểm tra các trường bắt buộc (BR-23)
        if (ticket.getName() == null || ticket.getName().trim().isEmpty() ||
            ticket.getDate() == null ||
            ticket.getDuration() == null ||
            ticket.getType() == null || ticket.getType().trim().isEmpty() ||
            ticket.getEvent() == null) {
            throw new IllegalArgumentException("Required fields are missing");
        }

        // Xử lý location trước khi validate và lưu
        LOCATION location = ticket.getLocation();
        if (location != null) {
            // Gán giá trị mặc định nếu latitude hoặc longitude không hợp lệ
            if (Float.isNaN(location.getLatitude()) || location.getLatitude() == 0) {
                location.setLatitude(10.7769f); // Hà Nội
                System.out.println("Latitude was invalid or NaN, set to default: 10.7769");
            }
            if (Float.isNaN(location.getLongitude()) || location.getLongitude() == 0) {
                location.setLongitude(106.7009f); // Hà Nội
                System.out.println("Longitude was invalid or NaN, set to default: 106.7009");
            }
            if (location.getName() == null || location.getName().trim().isEmpty()) {
                location.setName("Default Location");
                System.out.println("Location name was null or empty, set to: Default Location");
            }
            // Kiểm tra nếu location chưa được lưu (transient)
            if (location.getId() == 0) {
                entityManager.persist(location); // Lưu location mới trước
                System.out.println("Persisted new location with ID: " + location.getId());
            } else {
                entityManager.merge(location); // Cập nhật nếu đã tồn tại
                System.out.println("Merged existing location with ID: " + location.getId());
            }
        } else {
            location = new LOCATION();
            location.setName("Default Location");
            location.setLatitude(10.7769f); // Hà Nội
            location.setLongitude(106.7009f); // Hà Nội
            ticket.setLocation(location);
            entityManager.persist(location); // Lưu location mới
            System.out.println("Created and persisted new location: Name=" + location.getName() + ", Latitude=" + location.getLatitude() + ", Longitude=" + location.getLongitude());
        }

        try {
            if (ticket.getId() == 0) {
                entityManager.persist(ticket);
                System.out.println("Ticket persisted with ID: " + ticket.getId() + " at " + java.time.LocalDateTime.now());
            } else {
                entityManager.merge(ticket);
                System.out.println("Ticket merged with ID: " + ticket.getId() + " at " + java.time.LocalDateTime.now());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error in TicketService.set at " + java.time.LocalDateTime.now() + ": " + e.getMessage());
            throw e; // Ném lại ngoại lệ để controller xử lý
        }
    }

    // Xóa vé
    public void deleteById(int id) {
        TICKET ticket = getById(id);
        if (ticket != null) {
            // Kiểm tra nếu vé có người tham gia
            Query query = entityManager.createQuery("SELECT p FROM PARTICIPANT p WHERE p.ticket.id = :ticketId");
            query.setParameter("ticketId", id);
            if (!query.getResultList().isEmpty()) {
                ticket.setStatus(2); // Đánh dấu completed thay vì xóa
                set(ticket);
            } else {
                if (ticket.getLocation() != null) {
                    entityManager.remove(ticket.getLocation()); // Xóa location trước
                }
                entityManager.remove(ticket);
            }
        }
    }

    // Lấy vé theo sự kiện
    public List<TICKET> getByEvent(int eventId) {
        Query query = entityManager.createQuery(
            "SELECT t FROM TICKET t WHERE t.event.id = :eventId", TICKET.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }
}