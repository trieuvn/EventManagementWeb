<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Events</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Events</h1>
        <div class="mb-3">
            <a href="?type=upcoming" class="btn btn-info">Upcoming</a>
            <a href="?type=past" class="btn btn-info">Past</a>
            <form action="${pageContext.request.contextPath}/user/events/search" method="get" class="d-inline">
                <input type="text" name="name" placeholder="Search by name" class="form-control d-inline" style="width: 200px;">
                <input type="text" name="category" placeholder="Category" class="form-control d-inline" style="width: 150px;">
                <input type="date" name="date" class="form-control d-inline" style="width: 150px;">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td>${event.id}</td>
                        <td>${event.name}</td>
                        <td>${event.type}</td>
                        <td><a href="${pageContext.request.contextPath}/user/events/${event.id}" class="btn btn-info btn-sm">Details</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>