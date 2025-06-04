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
            min-height: 100vh; /* Đảm bảo body chiếm toàn bộ chiều cao để footer nằm dưới cùng */
            display: flex;
            flex-direction: column;
        }
        hr {
            border: 0;
            border-top: 1px solid #ddd;
            margin: 0;
        }
        footer {
            background-color: #2e3b4e; /* Màu xám đậm giống trong hình */
            padding: 10px 20px;
            width: 100%;
            box-sizing: border-box;
            color: #fff;
            font-size: 14px;
            margin-top: auto; /* Đẩy footer xuống dưới cùng */
            min-height: 40px; /* Đảm bảo footer có chiều cao tối thiểu */
            display: flex;
            align-items: center; /* Căn giữa nội dung theo chiều dọc */
        }
    </style>
</head>
<body>
    <hr/>
    <footer>
        <span>© 2025 - Ứng dụng Quản lý Sự kiện</span>
    </footer>
</body>
</html>