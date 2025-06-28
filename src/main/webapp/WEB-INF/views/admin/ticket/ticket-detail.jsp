<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh sửa vé</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-style.css">
    </head>

    <body>
        <div class="container">
            <div class="main-content">
                <div class="card">
                    <h2>Chỉnh sửa vé</h2>
                    <form:form modelAttribute="ticket" method="post" action="${pageContext.request.contextPath}/admin/tickets/save/${ticket.id}">
                        <form:hidden path="id" />

                        <div class="form-group">
                            <label for="name">Tên vé</label>
                            <form:input path="name" cssClass="form-control" maxlength="30" required="true" />
                            <form:errors path="name" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="description">Mô tả</label>
                            <form:textarea path="description" cssClass="form-control" maxlength="100" />
                            <form:errors path="description" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="price">Giá vé (VNĐ)</label>
                            <form:input path="price" type="number" cssClass="form-control" min="0" required="true" />
                            <form:errors path="price" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="date">Ngày diễn ra</label>
                            <form:input path="date" type="date" cssClass="form-control" required="true" />
                            <form:errors path="date" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="duration">Thời lượng (hh:mm:ss)</label>
                            <form:input path="duration" type="time" cssClass="form-control" required="true" />
                            <form:errors path="duration" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="regDeadline">Hạn đăng ký</label>
                            <form:input path="regDeadline" type="date" cssClass="form-control" required="true" />
                            <form:errors path="regDeadline" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="slots">Số lượng vé (-1 nếu không giới hạn)</label>
                            <form:input path="slots" type="number" cssClass="form-control" min="-1" required="true" />
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


                        <div class="form-group">
                            <button type="submit" class="btn-primary">Lưu thay đổi</button>
                            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn-secondary">Hủy</a>
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
                                                <span class="status upcoming">Đã tham giá</span>
                                            </c:when>
                                            <c:when test="${participant.status == 2}">
                                                <span class="status upcoming">Đã tham giá</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status ended">Đã hủy</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${participant.status == 0}">
                                                <span class="status upcoming">Chưa checkin</span>
                                            </c:when>
                                            <c:when test="${participant.status == 1}">
                                                <span class="status upcoming">Đã checkin</span>
                                            </c:when>
                                            <c:when test="${participant.status == 2}">
                                                <span class="status upcoming">Đã checkin</span>
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
        
    </body>

</html>