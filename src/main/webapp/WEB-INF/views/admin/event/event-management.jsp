<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Event Admin Dashboard</title>
        <!-- Google Font & Icons -->
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;900&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <!-- Main stylesheet -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin-style.css">
        <style>
            /* Popup styles */
            .popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                z-index: 1000;
                max-width: 400px;
                width: 100%;
                text-align: center;
            }
            .popup.show {
                display: block;
            }
            .popup-header {
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .popup-content {
                margin-bottom: 20px;
            }
            .popup-close {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
            }
            .popup-close:hover {
                background-color: #0056b3;
            }
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 999;
            }
            .overlay.show {
                display: block;
            }
        </style>
    </head>

    <body>
        <!-- Popup for displaying messages -->
        <c:if test="${not empty message}">
            <div class="overlay show"></div>
            <div class="popup show">
                <div class="popup-header">Thông báo</div>
                <div class="popup-content">${message}</div>
                <button class="popup-close" onclick="closePopup()">Đóng</button>
            </div>
        </c:if>

        <div class="dashboard">
            <!-- Sidebar Filters -->
            <aside class="sidebar">
                <h2>Bộ lọc</h2>
                <label>Khoảng thời gian</label>
                <input type="date">
                <input type="date">
                <label>Loại sự kiện</label>
                <select>
                    <option value="">Tất cả</option>
                    <option>Hội nghị</option>
                    <option>Workshop</option>
                    <option>Webinar</option>
                </select>
                <label>Trạng thái</label>
                <select>
                    <option value="">Tất cả</option>
                    <option>Đang mở</option>
                    <option>Đã đóng</option>
                </select>
                <label>Tìm kiếm</label>
                <input type="search" placeholder="Tên sự kiện hoặc địa điểm">
                <button class="btn-primary"><i class="fa fa-search"></i> Tìm</button>
            </aside>

            <!-- Main Content -->
            <main class="main-content">
                <!-- Quick Stats -->
                <section class="stats">
                    <div class="card upcoming">
                        <h3>${upcomingCount}</h3>
                        <p>Sự kiện sắp tới</p>
                    </div>
                    <div class="card ongoing">
                        <h3>${ongoingCount}</h3>
                        <p>Đang diễn ra</p>
                    </div>
                    <div class="card ended">
                        <h3>${endedCount}</h3>
                        <p>Đã kết thúc</p>
                    </div>
                </section>

                <!-- Event List -->
                <section class="event-list">
                    <div class="list-header">
                        <h2>Danh sách sự kiện</h2>
                        <form action="${pageContext.request.contextPath}/admin/events/create" method="post">
                            <button type="submit" class="btn-primary"><i class="fa fa-plus"></i> Tạo mới</button>
                        </form>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>Tên sự kiện</th>
                                <th>Số lượng tham gia</th>
                                <th>Loại</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="event" items="${eventList}">
                                <tr>
                                    <td>${event.name}</td>
                                    <td>${event.participants.size()}</td>
                                    <td>${event.type}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${event.status}">
                                                <span class="status upcoming">Đang mở</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status ended">Đã đóng</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="actions">
                                        <!-- Nút View: Gửi POST đến /admin/events/view/{id} -->
                                        <form action="${pageContext.request.contextPath}/admin/events/view/${event.id}" method="post">
                                            <button type="submit" class="btn-icon"><i class="fa fa-eye"></i></button>
                                        </form>
                                        <!-- Nút Edit: Gửi POST đến /admin/events/edit/{id} -->
                                        <form action="${pageContext.request.contextPath}/admin/events/edit/${event.id}" method="post">
                                            <button type="submit" class="btn-icon"><i class="fa fa-edit"></i></button>
                                        </form>
                                        <!-- Nút Delete: Gửi POST đến /admin/events/delete/{id} -->
                                        <form action="${pageContext.request.contextPath}/admin/events/delete/${event.id}" method="post">
                                            <button type="submit" class="btn-icon"><i class="fa fa-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </section>

                <!-- Lower Panels -->
                <section class="lower-panels">
                    <!-- Khách mời tham gia -->
                    <div class="guests">
                        <h2>Tổ chức</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>Tên</th>
                                    <th>Email</th>
                                    <th>Điện thoại</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="guest" items="${guestList}">
                                    <tr>
                                        <td>${guest.firstName} ${guest.lastName}</td>
                                        <td>${guest.email}</td>
                                        <td>${guest.phoneNumber}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </section>

                <!-- Analytics Panels -->
                <section class="analytics-panels">
                    <!-- Top Students -->
                    <div class="top-students">
                        <h2>Top sinh viên tích cực</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>Email</th>
                                    <th>Họ tên</th>
                                    <th>SĐT</th>
                                    <th>Số sự kiện</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="student" items="${topStudents}">
                                    <tr>
                                        <td>${student.email}</td>
                                        <td>${student.firstName} ${student.lastName}</td>
                                        <td>${student.phoneNumber}</td>
                                        <td>${student.totalParticipated}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </section>
            </main>

            <!-- Notifications -->
            <div class="notifications">
                <button class="btn-icon bell">
                    <i class="fa fa-bell"></i><span class="badge">${notificationCount}</span>
                </button>
                <div class="notify-dropdown">
                    <c:forEach var="change" items="${changes}">
                        <p>${change.event.name}: ${change.description}</p>
                    </c:forEach>
                </div>
            </div>
        </div>

        <script>
            function closePopup() {
                document.querySelector('.popup').classList.remove('show');
                document.querySelector('.overlay').classList.remove('show');
            }
        </script>
    </body>

</html>