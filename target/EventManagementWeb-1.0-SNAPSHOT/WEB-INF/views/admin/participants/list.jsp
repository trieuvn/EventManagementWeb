<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin - Participants</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Participants for Event ${eventId}</h1>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="participant" items="${participants}">
                    <tr>
                        <td>${participant.id}</td>
                        <td>${participant.user}</td>
                        <td>${participant.status == 1 ? 'Confirmed' : 'Pending'}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/participants/confirm/${participant.id}" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-success btn-sm" ${participant.status == 1 ? 'disabled' : ''}>Confirm</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/admin/participants/cancel/${participant.id}" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-danger btn-sm" ${participant.status == 0 ? 'disabled' : ''}>Cancel</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>