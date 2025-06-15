<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Event Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>${event.name}</h1>
        <p><strong>Description:</strong> ${event.description}</p>
        <p><strong>Location:</strong> ${event.location.name}</p>
        <p><strong>Slots:</strong> ${totalSlots}</p>
        <p><strong>Organizer:</strong> ${event.organizer}</p>
        <p><strong>Type:</strong> ${event.type}</p>
        <p><strong>Deadline:</strong> ${firstTicketDeadline != null ? firstTicketDeadline : 'N/A'}</p>
        <p><strong>Price:</strong> ${firstTicketPrice == 0 ? 'Free' : firstTicketPrice}</p>
        <p><strong>Target:</strong> ${event.target}</p>
        <p><strong>Contact:</strong> ${event.contactInfo}</p>
        <c:if test="${event.type == 'upcoming'}">
            <a href="${pageContext.request.contextPath}/user/registration/register/${event.id}" class="btn btn-primary">Register</a>
        </c:if>
    </div>
</body>
</html>