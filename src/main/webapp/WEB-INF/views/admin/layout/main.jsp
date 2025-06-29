<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Sự kiện</title>
        <!-- Liên kết CSS từ template -->
        <link href="https://fonts.googleapis.com/css?family=Work+Sans:400,500,600,700,800,900&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/elegant-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/slicknav.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <!-- CSS tùy chỉnh cho file này -->
        <style>
            .container-fluid {
                margin-bottom: 20px;
                /* Thêm khoảng cách giữa nội dung body và footer */
            }

            .header-custom {
                background: #171822;
                padding: 15px 0;
                border-bottom: 1px solid #2f3039;
                position: relative;
                z-index: 1000;
                margin-bottom: 20px;
            }

            .header-custom .logo a {
                color: #fff;
                font-size: 24px;
                font-weight: 700;
            }

            .header-custom .nav-menu ul li a {
                color: #a0a1b5;
                padding: 10px 20px;
            }

            .header-custom .nav-menu ul li a:hover {
                color: #f44949;
            }

            /* Nút thông báo (chuông) */
            .notification-button {
                position: fixed;
                bottom: 20px;
                right: 20px;
                padding: 10px;
                background: #f44949;
                color: #fff;
                border-radius: 50%;
                cursor: pointer;
                font-size: 18px;
                width: 50px;
                height: 50px;
                text-align: center;
                line-height: 30px;
                z-index: 1000;
            }

            .notification-button i {
                vertical-align: middle;
            }

            .notification-button:hover .notification-tooltip {
                display: block;
            }

            .notification-tooltip {
                display: none;
                position: absolute;
                bottom: 60px;
                /* Đặt trên nút */
                right: 0;
                background: #fff;
                color: #171822;
                padding: 10px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 200px;
                text-align: left;
                z-index: 1000;
            }

            .notification-tooltip::before {
                content: '';
                position: absolute;
                bottom: -5px;
                right: 10px;
                border-width: 5px;
                border-style: solid;
                border-color: #fff transparent transparent transparent;
            }

            .notification-tooltip ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }

            .notification-tooltip ul li {
                color: #f44949;
                margin-bottom: 5px;
                padding-left: 0;
                /* Bỏ dot đầu dòng */
            }

            .filter-section {
                background: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
                position: sticky;
                top: 20px;
                z-index: 900;
            }

            .filter-section .form-group {
                margin-bottom: 15px;
            }

            .filter-section .form-control {
                border-radius: 5px;
            }

            .filter-section .input-group-append .btn {
                background: #f44949;
                color: #fff;
                border: none;
            }

            /* Style cho dashboard stats (card) - Trang trí với màu pastel */
            .stat-card {
                background: linear-gradient(135deg, #AED9E0 0%, #D4E4ED 100%);
                /* Pastel xanh */
                padding: 20px;
                text-align: center;
                border-radius: 10px;
                margin-bottom: 15px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .stat-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
            }

            .stat-card:nth-child(2) {
                background: linear-gradient(135deg, #F7C8D7 0%, #FAD2E1 100%);
                /* Pastel hồng */
            }

            .stat-card:nth-child(3) {
                background: linear-gradient(135deg, #FFF3B0 0%, #FFF9D6 100%);
                /* Pastel vàng */
            }

            .stat-card h4 {
                font-size: 30px;
                color: #171822;
                margin-bottom: 5px;
                font-weight: 700;
            }

            .stat-card p {
                color: #6a6b7c;
                font-size: 16px;
                font-weight: 600;
                text-transform: uppercase;
            }

            .table-section h3 {
                margin-bottom: 20px;
            }

            /* Style cho bảng - Căn giữa nội dung */
            .table-custom {
                background: #fff;
                border-radius: 5px;
                overflow: hidden;
            }

            .table-custom th {
                background: #f4f6f8;
                color: #171822;
                font-weight: 600;
                text-align: center;
            }

            .table-custom td {
                vertical-align: middle;
                text-align: center;
            }

            .table-custom .btn-info {
                background: #17a2b8;
                border: none;
            }

            .table-custom .btn-info:hover {
                background: #138496;
            }

            /* Style cho alert section */
            .alert-section {
                background: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-top: 20px;
            }

            .alert-section h4 {
                color: #171822;
                margin-bottom: 10px;
            }

            .alert-section ul {
                list-style: none;
                padding: 0;
            }

            .alert-section ul li {
                color: #f44949;
                margin-bottom: 5px;
            }

            /* Style cho footer tùy chỉnh */
            .footer-custom {
                background: #171822;
                padding: 30px 0;
                text-align: center;
            }

            .footer-custom .footer-nav ul li a {
                color: #a0a1b5;
                margin: 0 15px;
            }

            .footer-custom .footer-nav ul li a:hover {
                color: #f44949;
            }

            .footer-custom .copyright {
                color: #a0a1b5;
                font-size: 14px;
                margin-top: 10px;
            }

            /* Thanh phần trăm cho cột Tỷ lệ trong bảng Thống kê theo khoa */
            .progress-bar {
                height: 10px;
                border-radius: 5px;
                background: #e9ecef;
                margin-top: 5px;
            }

            .progress-bar-fill {
                height: 100%;
                border-radius: 5px;
                background: linear-gradient(90deg, #17a2b8 0%, #f44949 100%);
                transition: width 0.3s ease;
            }
        </style>
    </head>

    <body>
        <!-- Header tùy chỉnh -->
        <header class="header-custom">
            <!-- Language Selector -->
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="header_content d-flex flex-row align-items-center justify-content-between">
                            <div class="logo">
                                <a href="#">Event<span>Management</span></a>
                            </div>
                            <nav class="nav-menu">
                                <ul class="d-flex flex-row align-items-center justify-content-start">
                                    <li><a href="${pageContext.request.contextPath}/admin/events">SỰ KIỆN</a></li>
                                    <li><a href="${pageContext.request.contextPath}/admin/categories">THỂ LOẠI</a></li>
                                    <li><a href="${pageContext.request.contextPath}/admin/participants">NGƯỜI THAM GIA</a></li>
                                    <li><a href="${pageContext.request.contextPath}/admin/reports">BÁO CÁO</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Nội dung chính -->
        <jsp:include page="/WEB-INF/views/${body}.jsp" />

        <!-- Footer tùy chỉnh -->
        <footer class="footer-custom">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <nav class="footer-nav">
                            <ul class="d-flex flex-row align-items-center justify-content-center">
                                <li><a href="${pageContext.request.contextPath}/admin/events">SỰ KIỆN</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/categories">THỂ LOẠI</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/participants">NGƯỜI THAM GIA</a></li>
                                <li><a href="${pageContext.request.contextPath}/admin/reports">BÁO CÁO</a></li>
                            </ul>
                        </nav>
                        <div class="copyright"> Copyright © - Ứng dụng Quản lý sự kiện </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Nút thông báo (chuông) cố định dưới cùng bên phải -->
        <div class="notification-button">
            <i class="fa fa-bell"></i>
            <div class="notification-tooltip">
                <ul>
                    <li>Sự kiện "Hackathon" bắt đầu sau 24h.</li>
                    <li>Sự kiện "UX Meetup" còn thiếu 5 người tham gia.</li>
                    <li>Sự kiện "Spring Boot Workshop" vừa được tạo.</li>
                </ul>
            </div>
        </div>
        <!-- Liên kết JavaScript từ CDN -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.slicknav.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.nav-menu').slicknav();
            });
        </script>
        <script>
            function googleTranslateElementInit() {
                new google.translate.TranslateElement(
                        {pageLanguage: "vi"},
                        "google_translate_element"
                        );
            }
        </script>

        <script src="https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
    </body>

</html>