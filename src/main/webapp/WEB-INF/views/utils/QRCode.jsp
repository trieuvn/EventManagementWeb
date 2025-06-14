<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>QR Code Display</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .image-container {
            margin-top: 20px;
            text-align: center;
        }
        .image-container img {
            max-width: 100%;
            height: auto;
            border: 1px solid #ddd;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="image-container">
            <c:if test="${not empty qrImageBase64}">
                <img src="data:image/png;base64,${qrImageBase64}" alt="QR Code" onerror="this.style.display='none'; alert('Failed to load QR Code.');">
            </c:if>
            <c:if test="${empty qrImageBase64}">
                <p class="text-danger">No QR Code available.</p>
            </c:if>
        </div>
    </div>
</body>
</html>