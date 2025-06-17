<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Event Details</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .card-custom {
            border: 1px solid #e0e0e0;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }
        .card-custom:hover {
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
            transform: translateY(-2px);
        }
        .bg-light-custom {
            background-color: #f8f9fa;
        }
        .table-custom th {
            background-color: #007bff;
            color: white;
        }
        .table-custom td {
            vertical-align: middle;
        }
        .custom-register-btn {
            padding: 2px 10px;
            font-size: 0.875rem;
            min-width: 70px;
        }
        .free-badge {
            color: #28a745;
            font-weight: bold;
            font-size: 1.1em;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <!-- Phần trên: Hình ảnh và Organizer -->
        <div class="row">
            <div class="col-md-4">
                <div class="card card-custom p-3 bg-light-custom">
                    <img src="${event.imageUrl}" alt="Event Image" class="img-fluid rounded shadow-sm" />
                </div>
            </div>
            <div class="col-md-8">
                <div class="card card-custom p-4 bg-light-custom">
                    <h3 class="text-primary">Organizer Information</h3>
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
                            <p class="text-muted">No organizer information available.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Phần dưới: Thông tin sự kiện -->
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card card-custom p-4 bg-light-custom">
                    <h2 class="text-success">${event.name}</h2>
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
        </div>

        <!-- Bảng danh sách vé -->
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card card-custom p-4 bg-light-custom">
                    <h3 class="text-info">Available Tickets</h3>
                    <table class="table table-bordered table-hover table-custom">
                        <thead>
                            <tr>
                                <th scope="col">Ticket Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Slot</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticket" items="${event.tickets}">
                                <tr>
                                    <td>${ticket.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${ticket.price == 0}">
                                                <span class="free-badge">Free</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${ticket.price}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${ticket.slot}</td>
                                    <td>
                                        <c:if test="${event.type == 'upcoming' and ticket.status == 1}">
                                            <a href="${pageContext.request.contextPath}/user/registration/register/ticket/${ticket.id}" class="btn btn-primary btn-sm custom-register-btn">Register</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>