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
        <!-- Phần trên: Hình ảnh và Organizer -->
        <div class="row">
            <!-- Cột trái: Hình ảnh sự kiện -->
            <div class="col-md-4">
                <img src="${event.imageUrl}" alt="Event Image" class="img-fluid rounded shadow-sm" />
            </div>

            <!-- Cột phải: Thông tin Organizer -->
            <div class="col-md-8">
                <h3>Organizer Information</h3>
                <c:choose>
                    <c:when test="${not empty event.organizer}">
                        <p><strong>Name:</strong> 
                            <c:choose>
                                <c:when test="${not empty event.organizer.firstName and not empty event.organizer.lastName}">
                                    ${event.organizer.firstName} ${event.organizer.lastName}
                                </c:when>
                                <c:otherwise>
                                    ${event.organizer.lastName}
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p><strong>Email:</strong> 
                            <c:choose>
                                <c:when test="${not empty event.organizer.email}">
                                    ${event.organizer.email}
                                </c:when>
                                <c:otherwise>
                                    N/A
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p><strong>Phone:</strong> 
                            <c:choose>
                                <c:when test="${not empty event.organizer.phoneNumber}">
                                    ${event.organizer.phoneNumber}
                                </c:when>
                                <c:otherwise>
                                    N/A
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <c:if test="${not empty event.organizer.avatar}">
                            <p><strong>Avatar:</strong></p>
                            <img src="data:image/jpeg;base64,${event.organizer.avatarBase64}" alt="Organizer Avatar" class="img-fluid rounded-circle" style="width: 50px; height: 50px;" />
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <p>No organizer information available.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Phần dưới: Thông tin sự kiện -->
        <div class="row mt-4">
            <div class="col-md-12">
                <h2>${event.name}</h2>
                <p><strong>Description:</strong> ${event.description}</p>
                <p><strong>Location:</strong> ${event.location.name}</p>
                <p><strong>Slots:</strong> ${totalSlots}</p>
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
            </div>
        </div>

        <!-- Bảng danh sách vé -->
        <div class="row mt-4">
            <div class="col-md-12">
                <h3>Available Tickets</h3>
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
                            <th scope="col">Ticket Name</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ticket" items="${event.tickets}">
                            <tr>
                                <td>${ticket.name}</td>
                                <td>
                                    <c:if test="${event.type == 'upcoming' and ticket.status == 1}">
                                        <a href="${pageContext.request.contextPath}/user/registration/register/ticket/${ticket.id}" class="btn btn-primary btn-sm">Register</a>
                                   
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