<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Sự kiện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&display=swap" rel="stylesheet">
    <style>
        body {
            background: rgba(0, 0, 0, 0);
            min-height: 100vh;
        }
        h2.text-center {
            font-family: 'Playfair Display', serif;
            font-size: 2.5rem;
            font-weight: 600;
            color: #333;
        }
        .category-title {
            text-align: center;
            font-weight: bold;
            color: #2563eb;
            font-size: 20px;
            margin-bottom: 16px;
        }
        .category-buttons {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            margin-bottom: 2rem;
            justify-content: center;
        }
        .category-btn {
            padding: 10px 20px;
            border-radius: 999px;
            background: white;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
            border: none;
            color: #495057;
            font-size: 15px;
            transition: all 0.3s ease;
        }
        .category-btn:hover {
            background: #e0e7ff;
            color: #1d4ed8;
        }
        .category-btn.active {
            background: #6366f1;
            color: white;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="container my-5">
        <h2 class="text-center mb-4">Danh sách Sự kiện</h2>

        <div class="category-buttons">
            <a href="?" class="category-btn ${empty param.category ? 'active' : ''}">Tất cả</a>
            <c:forEach var="cat" items="${categories}">
                <a href="?category=${cat}" class="category-btn ${param.category == cat ? 'active' : ''}">${cat}</a>
            </c:forEach>
        </div>

        <!-- Danh sách sự kiện -->
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="e" items="${events}">
                <div class="col">
                    <div class="card h-100 shadow-sm border-0 position-relative">
                        <c:if test="${e.status == 'Mở'}">
                            <span class="badge bg-danger text-white position-absolute top-0 start-0 m-2">MỞ</span>
                        </c:if>
                        <%--
                        <c:if test="${not empty e.imageUrl}">
                            <img src="${e.imageUrl}" class="card-img-top" style="height: 200px; object-fit: cover;" alt="${e.name}" />
                        </c:if>
                        --%>
                        <div class="card-body">
                            <h5 class="card-title text-center fw-bold">${e.name}</h5>
                            <p><i class="fas fa-info-circle"></i> Mô tả: ${e.description}</p>
                            <p><i class="fas fa-clock"></i> Thời lượng: ${e.duration} giờ</p>
                            <p><i class="fas fa-tags"></i> Thể loại: <span class="badge bg-secondary">${e.type}</span></p>
                            <p><i class="fas fa-calendar-alt"></i> Ngày diễn ra: ${e.date}</p>
                            <p><i class="fas fa-calendar-check"></i> Hạn đăng ký: ${e.regDeadline}</p>
                            <p><i class="fas fa-user-tie"></i> Liên hệ: ${e.contactInfo}</p>
                            <p><i class="fas fa-users"></i> Còn lại: ${e.slots} chỗ</p>
                        </div>
                        <div class="card-footer bg-transparent text-center">
                            <c:choose>
                                <c:when test="${e.status == 'Mở' && e.slots > 0}">
                                    <form action="${pageContext.request.contextPath}/register" method="post">
                                        <input type="hidden" name="eventId" value="${e.id}" />
                                        <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-danger">Không thể đăng ký</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>