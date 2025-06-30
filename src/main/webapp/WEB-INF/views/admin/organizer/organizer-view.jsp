<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết nhà tổ chức</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-style.css">
</head>
<body>
    <div class="container">
        <div class="main-content">
            <div class="card">
                <h2>Chi tiết nhà tổ chức</h2>
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>
                <div class="form-group">
                    <label>Tên</label>
                    <p>${organizer.firstName}</p>
                </div>
                <div class="form-group">
                    <label>Họ</label>
                    <p>${organizer.lastName}</p>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <p>${organizer.phoneNumber}</p>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <p>${organizer.email}</p>
                </div>
                <div class="form-group">
                    <label>Ảnh đại diện</label>
                    <c:if test="${not empty organizer.avatar}">
                        <p><img src="data:image/jpeg;base64,${organizer.getBase64Avatar()}" alt="Organizer Avatar" style="max-width: 200px;" /></p>
                    </c:if>
                    <c:if test="${empty organizer.avatar}">
                        <p>Không có ảnh</p>
                    </c:if>
                </div>
                <div class="form-group">
                    <a href="${pageContext.request.contextPath}/admin/events" class="btn-secondary">Quay lại</a>
                </div>
            </div>

            <div class="event-list">
                <div class="list-header">
                    <h3>Sự kiện</h3>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Tên sự kiện</th>
                            <th>Loại</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="event" items="${events}">
                            <tr>
                                <td>${event.name}</td>
                                <td>${event.type}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${event.status}">
                                            <span class="status upcoming">Đang mở</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status ended">Đã đóng</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/events/view/${event.id}" method="post">
                                        <button type="submit" class="btn-icon">Chi tiết</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/admin/events/edit/${event.id}" method="post">
                                        <button type="submit" class="btn-icon">Sửa</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>