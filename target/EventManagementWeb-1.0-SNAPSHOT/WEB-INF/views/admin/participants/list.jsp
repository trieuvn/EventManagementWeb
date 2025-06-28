<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Admin - Participants</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-4">
            <h2 class="mb-4">Quản lý người tham gia</h2>

            <!-- Bộ lọc theo sự kiện -->
            <form method="get" action="${pageContext.request.contextPath}/admin/participants" class="row g-3 mb-4">
                <div class="col-md-6">
                    <label for="eventId" class="form-label">Chọn sự kiện:</label>
                    <select name="eventId" id="eventId" class="form-select" onchange="this.form.submit()">
                        <option value="">-- Tất cả sự kiện --</option>
                        <c:forEach var="event" items="${events}">
                            <option value="${event.id}" <c:if test="${event.id == param.eventId}">selected</c:if>>
                                ${event.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </form>

            <!-- Bảng participants -->
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                    <tr>
                        <th>#</th>
                        <th>User Email</th>
                        <th>Event</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="participant" items="${participants}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${participant.user.email}</td>
                            <td>${participant.ticket.event.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${participant.status == 1}">Đã checkin</c:when>
                                    <c:when test="${participant.status == 2}">Đã checkin</c:when>
                                    <c:otherwise>Chưa checkin</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/participants/confirm" method="post" style="display:inline;">
                                    <input type="hidden" name="ticketId" value="${participant.ticket.id}" />
                                    <input type="hidden" name="userEmail" value="${participant.user.email}" />
                                    <button type="submit" class="btn btn-success btn-sm"
                                            <c:if test="${participant.status == 1}">disabled</c:if>>Confirm</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/admin/participants/cancel" method="post" style="display:inline;">
                                    <input type="hidden" name="ticketId" value="${participant.ticket.id}" />
                                    <input type="hidden" name="userEmail" value="${participant.user.email}" />
                                    <button type="submit" class="btn btn-danger btn-sm"
                                            <c:if test="${participant.status == 0}">disabled</c:if>>Cancel</button>
                                    </form>
                                </td>
                            </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
