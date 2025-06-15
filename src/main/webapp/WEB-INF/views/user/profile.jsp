<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Profile</h1>
        <form action="${pageContext.request.contextPath}/user/profile" method="post">
            <div class="mb-3">
                <label>Email</label>
                <input type="email" name="email" value="${user.email}" class="form-control" readonly>
            </div>
            <div class="mb-3">
                <label>Full Name</label>
                <input type="text" name="firstName" value="${user.firstName}" class="form-control">
            </div>
            <div class="mb-3">
                <label>Phone Number</label>
                <input type="text" name="phoneNumber" value="${user.phoneNumber}" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">Update</button>
        </form>
        <h2>Registration History</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>Event</th>
                    <th>Status</th>
                    <c:if test="${user.role == 1}"><th>Actions</th></c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="participant" items="${participants}">
                    <tr>
                        <td>${participant.ticket.event.name}</td>
                        <td>${participant.status == 1 ? 'Confirmed' : 'Pending'}</td>
                        <c:if test="${user.role == 1}">
                            <td>
                                <form action="${pageContext.request.contextPath}/user/registration/cancel/${participant.id}" method="post">
                                    <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>