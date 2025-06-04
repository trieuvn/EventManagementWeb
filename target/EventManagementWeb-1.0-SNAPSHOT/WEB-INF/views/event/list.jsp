<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<h2>Danh sách Sự kiện</h2>
<div>
    <h3>Giới thiệu</h3>
    <p>Chào mừng đến với Ứng dụng Quản lý Sự kiện! Đăng ký tham gia các sự kiện thú vị, từ hội thảo công nghệ đến hòa nhạc và triển lãm nghệ thuật.</p>
</div>
<form method="get" action="${pageContext.request.contextPath}/">
    <input type="text" name="keyword" value="${param.keyword}" placeholder="Tìm kiếm sự kiện..."/>
    <select name="category">
        <option value="">Tất cả thể loại</option>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat}" ${param.category == cat ? 'selected' : ''}>${cat}</option>
        </c:forEach>
    </select>
    <button type="submit">Tìm</button>
</form>
<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Tên sự kiện</th><th>Thể loại</th><th>Ngày</th><th>Chỗ trống</th><th>Thao tác</th></tr>
            <c:forEach var="e" items="${events}">
        <tr>
            <td>${e.id}</td>
            <td>${e.name}</td>
            <td>${e.category}</td>
            <td>${e.eventDate}</td>
            <td>${e.availableSlots}</td>
            <td>
                <c:choose>
                    <c:when test="${e.registrationOpen && e.availableSlots > 0}">
                        <form action="${pageContext.request.contextPath}/register" method="post">
                            <input type="hidden" name="eventId" value="${e.id}"/>
                            <button type="submit">Đăng ký</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <span>Không thể đăng ký</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>