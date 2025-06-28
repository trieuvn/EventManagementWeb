<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chỉnh sửa sự kiện</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/event-detail.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-style.css">
    </head>

    <body>
        <div class="container">
            <div class="main-content">
                <div class="card">
                    <h2>Chỉnh sửa sự kiện</h2>
                    <form:form modelAttribute="event" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/events/save/${event.id}">
                        <form:hidden path="id" />

                        <div class="form-group">
                            <label for="name">Tên sự kiện</label>
                            <form:input path="name" cssClass="form-control" maxlength="50" required="true" />
                            <form:errors path="name" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="description">Mô tả</label>
                            <form:textarea path="description" cssClass="form-control" maxlength="100" />
                            <form:errors path="description" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="type">Loại sự kiện</label>
                            <form:select path="type" cssClass="form-control" required="true">
                                <form:option value="online">Online</form:option>
                                <form:option value="offline">Offline</form:option>
                                <form:option value="hybrid">Hybrid</form:option>
                            </form:select>
                            <form:errors path="type" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="contactInfo">Thông tin liên hệ</label>
                            <form:input path="contactInfo" cssClass="form-control" maxlength="50" />
                            <form:errors path="contactInfo" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="target">Mục tiêu</label>
                            <form:input path="target" cssClass="form-control" maxlength="50" required="true" />
                            <form:errors path="target" cssClass="error" />
                        </div>

                        <div class="form-group">
                            <label for="image">Hình ảnh</label>
                            <input type="file" name="imageFile" accept="image/*" class="form-control" />
                            <c:if test="${not empty event.image}">
                                <p>Hình ảnh hiện tại: <img src="data:image/jpeg;base64,${event.getBase64Image()}" alt="Event Image" style="max-width: 200px;" /></p>
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
                        <h3>Vé</h3>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Tên</th>
                                <th>Giá</th>
                                <th>Số lượng</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticket" items="${event.tickets}">
                                <tr>
                                    <td>${ticket.getName()}</td>
                                    <td>${ticket.price} VNĐ</td>
                                    <td>${ticket.getAvailableSlots()} / ${ticket.slots}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ticket.status == 0}">
                                                <span class="status upcoming">Upcoming</span>
                                            </c:when>
                                            <c:when test="${ticket.status == 1}">
                                                <span class="status ongoing">Ongoing</span>
                                            </c:when>
                                            <c:when test="${ticket.status == 2}">
                                                <span class="status completed">Completed</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status unknown">Unknown</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <!-- Đường dẫn: /admin/tickets/view/{id} -->
                                        <form action="${pageContext.request.contextPath}/admin/tickets/view/${ticket.id}" method="post">
                                            <button type="submit" class="btn-icon">Chi tiết</button>
                                        </form>
                                        <!-- Đường dẫn: /admin/tickets/edit/{id} -->
                                        <form action="${pageContext.request.contextPath}/admin/tickets/edit/${ticket.id}" method="post">
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
        <script>(function(){function c(){var b=a.contentDocument||a.contentWindow.document;if(b){var d=b.createElement('script');d.innerHTML="window.__CF$cv$params={r:'956e2d018f1aadde',t:'MTc1MTEyNDEwNi4wMDAwMDA='};var a=document.createElement('script');a.nonce='';a.src='/cdn-cgi/challenge-platform/scripts/jsd/main.js';document.getElementsByTagName('head')[0].appendChild(a);";b.getElementsByTagName('head')[0].appendChild(d)}}if(document.body){var a=document.createElement('iframe');a.height=1;a.width=1;a.style.position='absolute';a.style.top=0;a.style.left=0;a.style.border='none';a.style.visibility='hidden';document.body.appendChild(a);if('loading'!==document.readyState)c();else if(window.addEventListener)document.addEventListener('DOMContentLoaded',c);else{var e=document.onreadystatechange||function(){};document.onreadystatechange=function(b){e(b);'loading'!==document.readyState&&(document.onreadystatechange=e,c())}}}})();</script>
    </body>

</html>