<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Event Details</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <!-- Cột trái: hình ảnh -->
            <div class="col-md-4">
                <img src="${event.imageUrl}" alt="Event Image" class="img-fluid rounded shadow-sm" />
            </div>

            <!-- Cột phải: thông tin sự kiện -->
            <div class="col-md-8">
                <h2>${event.name}</h2>
                <p><strong>Description:</strong> ${event.description}</p>
                <p><strong>Location:</strong> ${event.location.name}</p>
                <p><strong>Slots:</strong> ${totalSlots}</p>
                <p><strong>Organizer:</strong> ${event.organizer}</p>
                <p><strong>Type:</strong> ${event.type}</p>

                <p><strong>Deadline:</strong>
                    <c:choose>
                        <c:when test="${not empty firstTicketDeadline}">
                            ${firstTicketDeadline}
                        </c:when>
                        <c:otherwise>
                            N/A
                        </c:otherwise>
                    </c:choose>
                </p>

                <p><strong>Price:</strong>
                    <c:choose>
                        <c:when test="${firstTicketPrice == 0}">
                            Free
                        </c:when>
                        <c:otherwise>
                            ${firstTicketPrice}
                        </c:otherwise>
                    </c:choose>
                </p>

                <p><strong>Target:</strong> ${event.target}</p>
                <p><strong>Contact:</strong> ${event.contactInfo}</p>

                <c:if test="${event.type == 'upcoming'}">
                    <a href="${pageContext.request.contextPath}/user/registration/register/${event.id}" class="btn btn-primary mt-3">Register</a>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
