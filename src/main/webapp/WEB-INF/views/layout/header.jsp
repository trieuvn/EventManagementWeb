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

        /* ✅ Nút ADMIN LOGIN tách riêng, tuyệt đối top-right */
        .admin-login-btn {
            position: absolute;
            top: 28px;
            right: 30px;
        }

        .admin-login-btn a {
            display: inline-block;
            background-color: #00bfff;
            color: white;
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: bold;
            font-size: 14px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .admin-login-btn a:hover {
            background-color: #0099cc;
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

            .admin-login-btn {
                position: static;
                margin-top: 10px;
                align-self: flex-end;
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
                        <li class="active"><a href="${pageContext.request.contextPath}">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/about">About</a></li>
                        <li><a href="${pageContext.request.contextPath}/schedule">Schedule</a></li>
                        <li><a href="${pageContext.request.contextPath}/blog">Blog</a></li>
                        <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    </ul>
                </nav>
            </div>

            <!-- ✅ Nút ADMIN LOGIN (tuyệt đối) -->
            <div class="admin-login-btn">
                <a href="${pageContext.request.contextPath}/login">ADMIN LOGIN</a>
            </div>

            <div id="mobile-menu"></div>
        </div>
    </header>
</body>
</html>