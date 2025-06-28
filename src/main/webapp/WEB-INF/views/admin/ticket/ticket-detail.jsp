<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh sửa vé</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;900&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-style.css">
        <style>
            /* Popup styles */
            .popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                z-index: 1000;
                max-width: 400px;
                width: 100%;
                text-align: center;
            }
            .popup.show {
                display: block;
            }
            .popup-header {
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .popup-content {
                margin-bottom: 20px;
            }
            .popup-close {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
            }
            .popup-close:hover {
                background-color: #0056b3;
            }
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 999;
            }
            .overlay.show {
                display: block;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty message}">
            <div class="overlay show"></div>
            <div class="popup show">
                <div class="popup-header">Thông báo</div>
                <div class="popup-content">${message}</div>
                <button class="popup-close" onclick="closePopup()">Đóng</button>
            </div>
        </c:if>
        <div class="container">
            <div class="main-content">
                <div class="card">
                    <h2>Chỉnh sửa vé</h2>
                    <form:form modelAttribute="ticket" method="post" action="${pageContext.request.contextPath}/admin/tickets/update/${ticket.id}">
                        <form:hidden path="id" />
                        <form:hidden path="event.id" /> <!-- Giữ giá trị event.id -->
                        <form:hidden path="qrCode" /> <!-- Giữ giá trị qrCode -->

                        <div class="form-group">
                            <label for="name">Tên vé</label>
                            <form:input path="name" cssClass="form-control" maxlength="30" required="true" value="${ticket.name}"/>
                            <form:errors path="name" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="description">Mô tả</label>
                            <form:textarea path="description" cssClass="form-control" maxlength="100" value="${ticket.description}"/>
                            <form:errors path="description" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="price">Giá vé (VNĐ)</label>
                            <form:input path="price" type="number" cssClass="form-control" min="0" required="true" value="${ticket.price}"/>
                            <form:errors path="price" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="date">Ngày diễn ra</label>
                            <form:input path="date" type="date" cssClass="form-control" required="true" value="${ticket.date}"/>
                            <form:errors path="date" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="duration">Thời lượng (hh:mm:ss, ví dụ: 02:00:00)</label>
                            <form:input path="duration" type="time" cssClass="form-control" required="true" pattern="([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]" placeholder="HH:mm:ss" value="${ticket.duration}"/>
                            <form:errors path="duration" cssClass="error"/>
                        </div>

                        <div class="form-group">
                            <label for="regDeadline">Hạn đăng ký</label>
                            <form:input path="regDeadline" type="date" cssClass="form-control" required="true" value="${ticket.regDeadline}"/>
                            <form:errors path="regDeadline" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="slots">Số lượng vé (-1 nếu không giới hạn)</label>
                            <form:input path="slots" type="number" cssClass="form-control" min="-1" required="true" value="${ticket.slots}"/>
                            <form:errors path="slots" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="status">Trạng thái</label>
                            <form:select path="status" cssClass="form-control" required="true">
                                <form:option value="0">Upcoming</form:option>
                                <form:option value="1">Ongoing</form:option>
                                <form:option value="2">Completed</form:option>
                            </form:select>
                            <form:errors path="status" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="type">Loại vé</label>
                            <form:select path="type" cssClass="form-control" required="true">
                                <form:option value="offline">Offline</form:option>
                                <form:option value="online">Online</form:option>
                                <form:option value="hybrid">Hybrid</form:option>
                            </form:select>
                            <form:errors path="type" cssClass="error" />
                        </div>

                        <!-- Thêm trường location -->
                        <div class="form-group">
                            <label for="location.name">Tên địa điểm</label>
                            <form:input path="location.name" cssClass="form-control" maxlength="50" value="${not empty ticket.location ? ticket.location.name : 'Default Location'}"/>
                            <form:errors path="location.name" cssClass="error"/>
                        </div>

                        <div class="form-group">
                            <label for="location.latitude">Latitude</label>
                            <form:input path="location.latitude" type="number" cssClass="form-control" step="any" min="-90" max="90" value="${not empty ticket.location ? ticket.location.latitude : 10.7769}"/>
                            <form:errors path="location.latitude" cssClass="error"/>
                        </div>

                        <div class="form-group">
                            <label for="location.longitude">Longitude</label>
                            <form:input path="location.longitude" type="number" cssClass="form-control" step="any" min="-180" max="180" value="${not empty ticket.location ? ticket.location.longitude : 106.7009}"/>
                            <form:errors path="location.longitude" cssClass="error"/>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn-primary">Lưu thay đổi</button>
                            <a href="${pageContext.request.contextPath}/admin/tickets/edit/${ticket.id}" class="btn-secondary">Hủy</a>
                        </div>
                    </form:form>
                </div>

                <div class="event-list">
                    <div class="list-header">
                        <h3>Người tham gia</h3>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Tên</th>
                                <th>Email</th>
                                <th>Trạng thái</th>
                                <th>Check-in</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="participant" items="${participantList}">
                                <tr>
                                    <td>${participant.user.lastName}</td>
                                    <td>${participant.user.email}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${participant.status == 0}">
                                                <span class="status upcoming">Đã đăng ký</span>
                                            </c:when>
                                            <c:when test="${participant.status == 1}">
                                                <span class="status upcoming">Đã tham gia</span>
                                            </c:when>
                                            <c:when test="${participant.status == 2}">
                                                <span class="status upcoming">Đã tham gia</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status ended">Đã hủy</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${participant.status == 0}">
                                                <span class="status upcoming">Chưa check-in</span>
                                            </c:when>
                                            <c:when test="${participant.status == 1}">
                                                <span class="status upcoming">Đã check-in</span>
                                            </c:when>
                                            <c:when test="${participant.status == 2}">
                                                <span class="status upcoming">Đã check-in</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status ended">Đã hủy</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/admin/participants/confirm/${participant.user.email}" method="post">
                                            <button type="submit" class="btn-icon">Xác nhận</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/admin/participants/cancel/${participant.user.email}" method="post">
                                            <button type="submit" class="btn-icon">Hủy</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <script>
            function closePopup() {
                document.querySelector('.popup').classList.remove('show');
                document.querySelector('.overlay').classList.remove('show');
                window.location.href = "${pageContext.request.contextPath}/admin/tickets";
            }
        </script>
    </body>
</html>