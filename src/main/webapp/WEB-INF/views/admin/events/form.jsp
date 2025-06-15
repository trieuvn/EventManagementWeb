<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin - Edit Event</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>${event.id == 0 ? 'Add' : 'Edit'} Event</h1>
        <form action="${pageContext.request.contextPath}/admin/events/${event.id == 0 ? 'add' : 'update'}" method="post">
            <input type="hidden" name="id" value="${event.id}">
            <div class="mb-3">
                <label>Name</label>
                <input type="text" name="name" value="${event.name}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Description</label>
                <textarea name="description" class="form-control">${event.description}</textarea>
            </div>
            <div class="mb-3">
                <label>Type</label>
                <select name="type" class="form-control">
                    <option value="online" ${event.type == 'online' ? 'selected' : ''}>Online</option>
                    <option value="in-person" ${event.type == 'in-person' ? 'selected' : ''}>In-Person</option>
                    <option value="hybrid" ${event.type == 'hybrid' ? 'selected' : ''}>Hybrid</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
    </div>
</body>
</html>