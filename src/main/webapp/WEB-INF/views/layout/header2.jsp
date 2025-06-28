<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%
    String uri = request.getRequestURI();
    String context = request.getContextPath();
%>

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
            display: flex;
            align-items: center;
            justify-content: space-between;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            flex-wrap: wrap;
            margin-top: -30px;
        }

        .logo img {
            max-height: 100px;
            height: auto;
            width: auto;
            display: block;
            margin-top: -10px;
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
            justify-content: center;
            align-items: center;
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

        .nav-menu li.active a {
            color: #007bff;
            font-weight: bold;
            border-bottom: 2px solid #007bff;
        }

        .user-info {
            position: relative;
            display: flex;
            align-items: center;
            gap: 10px;
            margin-top: -10px;
            cursor: pointer;
        }

        .avatar {
            width: 40px;
            height: 40px;
            background-color: #4CAF50;
            color: white;
            font-size: 18px;
            font-weight: bold;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            text-transform: uppercase;
            font-family: Arial, sans-serif;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            top: 50px;
            right: 0;
            background-color: white;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            min-width: 160px;
            z-index: 999;
            padding: 10px 0;
        }

        .dropdown-menu a {
            display: block;
            padding: 10px 20px;
            text-decoration: none;
            color: #333;
            font-size: 14px;
        }

        .dropdown-menu a:hover {
            background-color: #f2f2f2;
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
            <a href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/assets/img/event-list.png" alt="Events">
            </a>
        </div>

        <!-- Menu -->
        <div class="nav-menu">
            <nav class="mainmenu mobile-menu">
                <ul>
                    <li class="<%= (uri.equals(context) || uri.equals(context + "/")) ? "active" : "" %>">
                        <a href="${pageContext.request.contextPath}/">TRANG CHỦ</a>
                    </li>
                    <li class="<%= uri.startsWith(context + "/about") ? "active" : "" %>">
                        <a href="${pageContext.request.contextPath}/about">GIỚI THIỆU</a>
                    </li>
                    <li class="<%= uri.startsWith(context + "/history") ? "active" : "" %>">
                        <a href="${pageContext.request.contextPath}/history">LỊCH SỬ</a>
                    </li>
                </ul>
            </nav>
        </div>

        <!-- Avatar + Email + Dropdown -->
        <div class="user-info" id="userDropdownToggle">
            <div class="avatar" id="avatar">?</div>
            <span id="email">${user.email}</span>

            <div class="dropdown-menu" id="userDropdownMenu">
                <a href="${pageContext.request.contextPath}/profile">Thông tin cá nhân</a>
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </div>

        <div id="mobile-menu"></div>
    </div>
</header>

<script>
    window.addEventListener("DOMContentLoaded", () => {
        const emailEl = document.getElementById("email");
        const avatarEl = document.getElementById("avatar");
        const dropdownToggle = document.getElementById("userDropdownToggle");
        const dropdownMenu = document.getElementById("userDropdownMenu");

        if (emailEl && avatarEl) {
            const email = emailEl.textContent.trim();
            if (email.length > 0) {
                const firstLetter = email.charAt(0).toUpperCase();
                avatarEl.textContent = firstLetter;
            }
        }

        // Toggle dropdown
        dropdownToggle.addEventListener("click", (e) => {
            e.stopPropagation();
            dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";
        });

        // Close dropdown when clicking outside
        document.addEventListener("click", () => {
            dropdownMenu.style.display = "none";
        });
    });
</script>

</body>
</html>
