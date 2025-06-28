package com.uef.service;

import com.uef.model.PARTICIPANT;
import com.uef.model.TICKET;
import com.uef.model.USER;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParticipantService {

    @PersistenceContext
    private EntityManager entityManager;

    // Lấy tất cả người tham gia
    public List<PARTICIPANT> getAll() {
        return entityManager.createQuery("SELECT p FROM PARTICIPANT p", PARTICIPANT.class).getResultList();
    }

    // Lấy danh sách người tham gia theo sự kiện (mục 107)
    public List<PARTICIPANT> getAllByEvent(int eventId) {
        Query query = entityManager.createQuery(
                "SELECT p FROM PARTICIPANT p WHERE p.ticket.event.id = :eventId", PARTICIPANT.class);
        query.setParameter("eventId", eventId);
        return query.getResultList();
    }

    // Lấy người tham gia theo user (mục 97)
    public List<PARTICIPANT> getByUser(USER user) {
        Query query = entityManager.createQuery(
                "SELECT p FROM PARTICIPANT p "
                + "JOIN FETCH p.ticket t "
                + "JOIN FETCH t.event e "
                + "WHERE p.user = :user", PARTICIPANT.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    // Lấy người tham gia theo ticket.id và user.email
    public PARTICIPANT getById(int ticketId, String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email người dùng không được để trống");
        }
        if (ticketId <= 0) {
            throw new IllegalArgumentException("ID vé không hợp lệ");
        }
        Query query = entityManager.createQuery(
                "SELECT p FROM PARTICIPANT p WHERE p.ticket.id = :ticketId AND p.user.email = :userEmail", PARTICIPANT.class);
        query.setParameter("ticketId", ticketId);
        query.setParameter("userEmail", userEmail);
        try {
            return (PARTICIPANT) query.getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy bản ghi
        }
    }

    // Thêm hoặc cập nhật người tham gia
    public boolean set(PARTICIPANT participant) {
        // Kiểm tra các trường bắt buộc (BR-10, BR-11)
        if (participant.getUser() == null || participant.getTicket() == null) {
            throw new IllegalArgumentException("Thông tin người dùng hoặc vé không được để trống");
        }
        try {
            PARTICIPANT existing = getById(participant.getTicket().getId(), participant.getUser().getEmail());
            if (existing == null) {
                entityManager.persist(participant);
            } else {
                existing.setStatus(participant.getStatus());
                existing.setRate(participant.getRate());
                existing.setComment(participant.getComment());
                entityManager.merge(existing);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Thêm hoặc cập nhật người tham gia theo ticket.id và user.email
    public boolean setById(int ticketId, String userEmail, int status, int rate, String comment) {
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("Email người dùng không được để trống");
        }
        if (ticketId <= 0) {
            throw new IllegalArgumentException("ID vé không hợp lệ");
        }
        // Kiểm tra rate hợp lệ (BR-19: 0-5)
        if (rate < 0 || rate > 5) {
            throw new IllegalArgumentException("Điểm đánh giá phải từ 0 đến 5");
        }
        try {
            PARTICIPANT existing = getById(ticketId, userEmail);
            if (existing == null) {
                // Tạo mới
                Query userQuery = entityManager.createQuery("SELECT u FROM USER u WHERE u.email = :email", USER.class);
                userQuery.setParameter("email", userEmail);
                USER user = (USER) userQuery.getSingleResult();
                if (user == null) {
                    throw new IllegalArgumentException("Người dùng không tồn tại");
                }
                TICKET ticket = entityManager.find(TICKET.class, ticketId);
                if (ticket == null) {
                    throw new IllegalArgumentException("Vé không tồn tại");
                }
                // Kiểm tra slot còn trống (BR-11)
                Query slotQuery = entityManager.createQuery(
                        "SELECT COUNT(p) FROM PARTICIPANT p WHERE p.ticket.id = :ticketId");
                slotQuery.setParameter("ticketId", ticketId);
                long participantCount = (long) slotQuery.getSingleResult();
                if (ticket.getSlots() != -1 && participantCount >= ticket.getSlots()) {
                    throw new IllegalStateException("Không còn slot trống cho vé này");
                }
                PARTICIPANT participant = new PARTICIPANT(user, ticket, status, rate, comment);
                entityManager.persist(participant);
            } else {
                // Cập nhật
                existing.setStatus(status);
                existing.setRate(rate);
                existing.setComment(comment);
                entityManager.merge(existing);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa người tham gia (mục 89)
    public void delete(PARTICIPANT participant) {
        PARTICIPANT existing = entityManager.find(PARTICIPANT.class,
                new PARTICIPANT(participant.getUser(), participant.getTicket(), 0, 0, null));
        if (existing != null) {
            entityManager.remove(existing);
        }
    }

    // Xóa người tham gia theo ticket.id và user.email
    public boolean deleteById(int ticketId, String userEmail) {
        PARTICIPANT participant = getById(ticketId, userEmail);
        if (participant != null) {
            entityManager.remove(participant);
            return true;
        }
        return false;
    }

    // Đặt đánh giá cho vé (mục 98)
    public void setRate(USER user, TICKET ticket, int rate, String comment) {
        // Kiểm tra điều kiện đánh giá (BR-17, BR-18, BR-19)
        Query query = entityManager.createQuery(
                "SELECT p FROM PARTICIPANT p WHERE p.user = :user AND p.ticket = :ticket AND p.status = 1", PARTICIPANT.class);
        query.setParameter("user", user);
        query.setParameter("ticket", ticket);
        PARTICIPANT participant = (PARTICIPANT) query.getSingleResult();
        if (participant == null) {
            throw new IllegalStateException("Người dùng chưa check-in cho sự kiện này");
        }
        if (participant.getRate() != 0) {
            throw new IllegalStateException("Người dùng đã đánh giá sự kiện này");
        }
        if (rate < 0 || rate > 5) {
            throw new IllegalArgumentException("Điểm đánh giá phải từ 0 đến 5");
        }
        participant.setRate(rate);
        participant.setComment(comment);
        entityManager.merge(participant);
    }
}
