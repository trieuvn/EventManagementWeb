<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm vé mới</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="main-content">
            <div class="card">
                <h2>Thêm vé mới</h2>
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>
                <form:form modelAttribute="ticket" method="post" action="${pageContext.request.contextPath}/admin/tickets/add">
                    <form:hidden path="event.id"/> <!-- Giữ giá trị event.id -->
                    <form:hidden path="id"/>
                    <form:hidden path="qrCode" value="${ticket.qrCode}"/> <!-- Giữ giá trị qrCode -->

                    <div class="form-group">
                        <label for="name">Tên vé</label>
                        <form:input path="name" cssClass="form-control" maxlength="30" required="true"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="description">Mô tả</label>
                        <form:textarea path="description" cssClass="form-control" maxlength="100"/>
                        <form:errors path="description" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="price">Giá vé (VNĐ)</label>
                        <form:input path="price" type="number" cssClass="form-control" min="0" required="true"/>
                        <form:errors path="price" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="date">Ngày diễn ra</label>
                        <form:input path="date" type="date" cssClass="form-control" required="true"/>
                        <form:errors path="date" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="duration">Thời lượng (hh:mm:ss, ví dụ: 02:00:00)</label>
                        <form:input path="duration" type="time" cssClass="form-control" required="true" pattern="([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]" placeholder="HH:mm:ss"/>
                        <form:errors path="duration" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="regDeadline">Hạn đăng ký</label>
                        <form:input path="regDeadline" type="date" cssClass="form-control" required="true"/>
                        <form:errors path="regDeadline" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="slots">Số lượng vé (-1 nếu không giới hạn)</label>
                        <form:input path="slots" type="number" cssClass="form-control" min="-1" required="true"/>
                        <form:errors path="slots" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="status">Trạng thái</label>
                        <form:select path="status" cssClass="form-control" required="true">
                            <form:option value="0">Upcoming</form:option>
                            <form:option value="1">Ongoing</form:option>
                            <form:option value="2">Completed</form:option>
                        </form:select>
                        <form:errors path="status" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="type">Loại vé</label>
                        <form:select path="type" cssClass="form-control" required="true">
                            <form:option value="online">Online</form:option>
                            <form:option value="offline">Offline</form:option>
                            <form:option value="hybrid">Hybrid</form:option>
                        </form:select>
                        <form:errors path="type" cssClass="error"/>
                    </div>

                    <!-- Thêm trường location -->
                    <div class="form-group">
                        <label for="location.name">Tên địa điểm</label>
                        <form:input path="location.name" cssClass="form-control" maxlength="50"/>
                        <form:errors path="location.name" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="location.latitude">Latitude</label>
                        <form:input path="location.latitude" type="number" cssClass="form-control" step="any" min="-90" max="90" value="10.7769"/>
                        <form:errors path="location.latitude" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <label for="location.longitude">Longitude</label>
                        <form:input path="location.longitude" type="number" cssClass="form-control" step="any" min="-180" max="180" value="106.7009"/>
                        <form:errors path="location.longitude" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn-primary">Lưu vé</button>
                        <a href="${pageContext.request.contextPath}/admin/events/edit/${ticket.event.id}" class="btn-secondary">Hủy</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>