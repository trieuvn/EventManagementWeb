<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh sửa nhà tổ chức</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-style.css">
</head>
<body>
    <div class="container">
        <div class="main-content">
            <div class="card">
                <h2>Chỉnh sửa nhà tổ chức</h2>
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>
                <form:form modelAttribute="organizer" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/organizers/save/${organizer.id}">
                    <form:hidden path="id" />

                    <div class="form-group">
                        <label for="firstName">Tên</label>
                        <form:input path="firstName" cssClass="form-control" maxlength="50" />
                        <form:errors path="firstName" cssClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="lastName">Họ</label>
                        <form:input path="lastName" cssClass="form-control" maxlength="30" required="true" />
                        <form:errors path="lastName" cssClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Số điện thoại</label>
                        <form:input path="phoneNumber" cssClass="form-control" maxlength="15" />
                        <form:errors path="phoneNumber" cssClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <form:input path="email" cssClass="form-control" maxlength="50" required="true" />
                        <form:errors path="email" cssClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="avatar">Ảnh đại diện</label>
                        <input type="file" name="avatarFile" accept="image/*" class="form-control" />
                        <c:if test="${not empty organizer.avatar}">
                            <p>Ảnh hiện tại: <img src="data:image/jpeg;base64,${organizer.getBase64Avatar()}" alt="Organizer Avatar" style="max-width: 200px;" /></p>
                        </c:if>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn-primary">Lưu thay đổi</button>
                        <a href="${pageContext.request.contextPath}/admin/events" class="btn-secondary">Hủy</a>
                    </div>
                </form:form>
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