<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin - Events</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Event Management</h1>
        <a href="${pageContext.request.contextPath}/admin/events/add" class="btn btn-primary mb-3">Add Event</a>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td>${event.id}</td>
                        <td>${event.name}</td>
                        <td>${event.type}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/events/edit/${event.id}" class="btn btn-warning btn-sm">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/events/delete/${event.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                            <form action="${pageContext.request.contextPath}/admin/events/updateStatus" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${event.id}">
                                <select name="status" onchange="this.form.submit()">
                                    <option value="upcoming" ${event.type == 'upcoming' ? 'selected' : ''}>Upcoming</option>
                                    <option value="ongoing" ${event.type == 'ongoing' ? 'selected' : ''}>Ongoing</option>
                                    <option value="completed" ${event.type == 'completed' ? 'selected' : ''}>Completed</option>
                                </select>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>