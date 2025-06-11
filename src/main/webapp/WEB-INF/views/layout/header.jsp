<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        <style>
            .header-section {
                position: relative;
                height: 90px;
                background-color: #fff;
                padding: 8px 0;
                z-index: 1000;
            }

            .container {
                align-items: center;
                justify-content: space-between;
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
                flex-wrap: wrap;
            }

            .logo img {
                max-height: 100px;
                height: auto;
                width: auto;
                display: block;
                margin-top: -40px;
                margin-left: -60px;
            }

            .nav-menu {
                flex-grow: 1;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .nav-menu ul {
                list-style: none;
                display: flex;
                margin: 0;
                padding: 0;
                gap: 25px;
                justify-content: center; /* Ensure centering */
                align-items:center;
            }

            .nav-menu li a {
                text-decoration: none;
                color: #333;
                font-weight: 500;
                font-size: 16px;
                margin-top: -15px;
                display: inline-block;
                text-align: center;
            }

            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .nav-menu ul {
                    flex-direction: column;
                    gap: 10px;
                    justify-content: flex-start;
                }

            }

        </style>
    </head>
    <body>
        <header class="header-section">
            <div class="container">
                <!-- Logo -->
                <div class="logo">
                    <a href="${pageContext.request.contextPath}">
                        <img src="${pageContext.request.contextPath}/assets/img/event-list.png" alt="Events">
                    </a>
                </div>

                <!-- Menu -->
                <div class="nav-menu">
                    <nav class="mainmenu mobile-menu">
                        <ul>
                            <li class="active"><a href="${pageContext.request.contextPath}">TRANG CHỦ</a></li>
                            <li><a href="${pageContext.request.contextPath}/about">GIỚI THIỆU</a></li>
                            <li><a href="${pageContext.request.contextPath}/checkin">ĐIỂM DANH</a></li>
                            <li><a href="${pageContext.request.contextPath}/history">LỊCH SỬ</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#loginModal">ĐĂNG NHẬP</a></li>

                        </ul>
                    </nav>
                </div>

                <div id="mobile-menu"></div>
            </div>
        </header>
    </body>
</html>