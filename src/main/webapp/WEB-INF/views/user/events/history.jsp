<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch Sử Sự Kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #f9f9f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .main-content {
            margin-top: 100px;
            padding: 0 10px;
            max-width: 1200px;
            margin-left: auto;
            margin-right: auto;
        }

        .main-content h2 {
            margin-bottom: 30px;
            font-size: 32px;
            color: #333;
            text-align: center;
        }

        table {
            width: 100%;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
            overflow: hidden;
            table-layout: fixed;
        }

        table thead {
            background-color: #007bff;
            color: white;
        }

        table th, table td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
            word-wrap: break-word;
        }

        .status {
            padding: 5px 10px;
            border-radius: 6px;
            font-weight: bold;
        }

        .status.upcoming { background-color: #e0f7fa; color: #00796b; }
        .status.completed { background-color: #e8f5e9; color: #2e7d32; }
        .status.canceled { background-color: #ffebee; color: #c62828; }

        .btn-icon {
            padding: 6px 12px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .btn-view { background-color: #28a745; color: white; }
        .btn-view:hover { background-color: #218838; }

        .btn-rate { background-color: #ffc107; color: black; }
        .btn-rate:hover { background-color: #e0a800; color: black; }

        .bi { cursor: pointer; }
    </style>
</head>

<body>
<div class="main-content">
    <h2>Lịch Sử Sự Kiện Của Tôi</h2>
    <table>
        <thead>
        <tr>
            <th>Sự kiện</th>
            <th>Loại vé</th>
            <th>Ngày</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${participants}">
            <tr>
                <td>${p.ticket.event.name}</td>
                <td>${p.ticket.event.type}</td>
                <td><fmt:formatDate value="${p.ticket.date}" pattern="dd/MM/yyyy" /></td>
                <td>
                    <c:choose>
                        <c:when test="${p.status == 0}">
                            <span class="status upcoming">Đã Đăng ký</span>
                        </c:when>
                        <c:when test="${p.status == 1}">
                            <span class="status completed">Đã tham gia</span>
                        </c:when>
                        <c:when test="${p.status == 2}">
                            <span class="status completed">Đã tham gia</span>
                        </c:when>
                        <c:when test="${p.status == -1}">
                            <span class="status completed">Đã hủy</span>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <div class="d-flex justify-content-center gap-2">
                        <button class="btn-icon btn-view view-qr-btn" data-ticket-id="${p.ticket.id}">Xem QR</button>
                        <button class="btn-icon btn-rate rate-btn" data-ticket-id="${p.ticket.id}">Đánh giá</button>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- QR Modal -->
<div class="modal fade" id="qrModal" tabindex="-1" aria-labelledby="qrModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Mã QR Của Bạn</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body p-0">
                <iframe id="qrIframe" src="" width="100%" height="400" style="border:none;"></iframe>
            </div>
        </div>
    </div>
</div>

<!-- Rate Modal -->
<div class="modal fade" id="rateModal" tabindex="-1" aria-labelledby="rateModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Đánh Giá Sự Kiện</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body text-center">
                <div id="stars" class="mb-3">
                    <i class="bi bi-star fs-3 text-secondary" data-star="1"></i>
                    <i class="bi bi-star fs-3 text-secondary" data-star="2"></i>
                    <i class="bi bi-star fs-3 text-secondary" data-star="3"></i>
                    <i class="bi bi-star fs-3 text-secondary" data-star="4"></i>
                    <i class="bi bi-star fs-3 text-secondary" data-star="5"></i>
                </div>
                <button class="btn btn-warning" id="submitRating">Gửi đánh giá</button>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const qrButtons = document.querySelectorAll(".view-qr-btn");
        const rateButtons = document.querySelectorAll(".rate-btn");

        let selectedStar = 0;
        let currentTicketId = null;

        qrButtons.forEach(btn => {
            btn.addEventListener("click", function () {
                const ticketId = btn.getAttribute("data-ticket-id");
                document.getElementById("qrIframe").src = "${pageContext.request.contextPath}/user/qr?ticket_id=" + ticketId;
                const modal = new bootstrap.Modal(document.getElementById("qrModal"));
                modal.show();
            });
        });

        rateButtons.forEach(btn => {
            btn.addEventListener("click", function () {
                currentTicketId = btn.getAttribute("data-ticket-id");
                selectedStar = 0;
                document.querySelectorAll("#stars i").forEach(star => {
                    star.classList.remove("bi-star-fill", "text-warning");
                    star.classList.add("bi-star", "text-secondary");
                });
                const modal = new bootstrap.Modal(document.getElementById("rateModal"));
                modal.show();
            });
        });

        document.querySelectorAll('#stars i').forEach(star => {
            star.addEventListener('click', function () {
                selectedStar = parseInt(this.getAttribute('data-star'));
                document.querySelectorAll('#stars i').forEach(s => {
                    const value = parseInt(s.getAttribute('data-star'));
                    if (value <= selectedStar) {
                        s.classList.remove('bi-star', 'text-secondary');
                        s.classList.add('bi-star-fill', 'text-warning');
                    } else {
                        s.classList.remove('bi-star-fill', 'text-warning');
                        s.classList.add('bi-star', 'text-secondary');
                    }
                });
            });
        });

        document.getElementById("submitRating").addEventListener("click", function () {
            if (selectedStar === 0 || currentTicketId == null) {
                alert("Vui lòng chọn số sao để đánh giá.");
                return;
            }

            fetch("${pageContext.request.contextPath}/user/rate?ticket_id=" + currentTicketId + "&rate=" + selectedStar)
                .then(response => {
                    if (response.ok) {
                        alert("Gửi đánh giá thành công.");
                        bootstrap.Modal.getInstance(document.getElementById("rateModal")).hide();
                    } else {
                        alert("Có lỗi khi gửi đánh giá.");
                    }
                });
        });
    });
</script>
</body>
</html>
