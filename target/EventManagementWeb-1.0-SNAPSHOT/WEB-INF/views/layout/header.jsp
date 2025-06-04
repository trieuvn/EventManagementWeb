<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #f5efff;
            padding: 10px 20px;
            border-bottom: 1px solid #ddd;
            width: 100%; /* Đảm bảo header chiếm toàn bộ chiều ngang */
            box-sizing: border-box; /* Đảm bảo padding không làm tăng kích thước */
        }
        .header-left img {
            height: 40px;
        }
        .header-right {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .header-right .notification {
            display: flex;
            align-items: center;
            gap: 5px;
            padding: 5px 15px;
            border: 1px solid #ccc;
            border-radius: 20px;
            background-color: #fff;
            font-size: 14px;
            color: #333;
            text-decoration: none;
        }
        .header-right .profile {
            width: 30px;
            height: 30px;
            background-color: #ff4500;
            color: #fff;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            font-weight: bold;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <header>
        <div class="header-left">
            <img src="src/main/resources/META-INF/logo_uef.png" alt="UEF Logo">
        </div>
        <div class="header-right">
            <a href="#" class="notification">
                🔔 Nguyễn Hoàng Phúc
            </a>
            <a href="#" class="profile">N</a>
        </div>
    </header>
</body>
</html>