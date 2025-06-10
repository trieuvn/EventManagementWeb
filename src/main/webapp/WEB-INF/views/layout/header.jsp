<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        <style>
            .header-section {
                height: 90px;
                background-color: #fff;
                padding: 8px 0;
            }

            .container {
                display: flex;
                align-items: center; /* ⭐ Giúp logo và menu nằm giữa theo chiều dọc */
                justify-content: space-between; /* ⭐ Tách logo và menu về 2 bên */
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 20px;
            }

            .logo img {
                max-height: 80px; /* ⭐ Điều chỉnh kích thước logo để không quá cao */
                height: auto;
                width: auto;
                display: block;
            }

            .nav-menu ul {
                list-style: none;
                display: flex;
                margin: 0;
                padding: 0;
                gap: 25px;
            }

            .nav-menu li a {
                text-decoration: none;
                color: #333;
                font-weight: 500;
                font-size: 16px;
            }

            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                    align-items: flex-start;
                }

                .nav-menu ul {
                    flex-direction: column;
                    gap: 10px;
                }
            }
        </style>
    </head>
    <body>
        <header class="header-section">
            <div class="container">
                <div class="logo">
                    <a href="${pageContext.request.contextPath}">
                        <img src="${pageContext.request.contextPath}/assets/img/event-list.png" alt="Logo">
                    </a>
                </div>
                <div class="nav-menu">
                    <nav class="mainmenu mobile-menu">
                        <ul>
                            <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li><a href="${pageContext.request.contextPath}/about-us">About</a></li>
                            <li><a href="${pageContext.request.contextPath}/schedule">Schedule</a></li>
                            <li><a href="${pageContext.request.contextPath}/blog">Blog</a></li>
                            <li><a href="${pageContext.request.contextPath}/contact">Contacts</a></li>
                        </ul>
                    </nav>
                </div>
                <div id="mobile-menu-wrap"></div>
            </div>
        </header>
    </body>
</html>
