<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>QR Code Decoder</title>
        <!-- Google Font & Icons -->
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;900&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .error {
                color: red;
            }
            .success {
                color: green;
            }
        </style>
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
        <c:if test="${not empty message}">
            <div class="overlay show"></div>
            <div class="popup show">
                <div class="popup-header">Thông báo</div>
                <div class="popup-content">${message}</div>
                <button class="popup-close" onclick="closePopup()">Đóng</button>
            </div>
        </c:if>
        <div class="container">
            <h2 class="text-center mb-4">QR Code Decoder</h2>
            <form action="${pageContext.request.contextPath}/admin/decode" method="post" enctype="multipart/form-data" class="qr-form">
                <div class="form-group">
                    <label for="imageFile" class="qr-label">Upload QR Code Image</label>
                    <input type="file" name="imageFile" id="imageFile" accept="image/*" required class="form-control qr-input">
                </div>
                <button type="submit" class="btn btn-primary vsubmit">Decode QR Code</button>
            </form>
            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3 error">${error}</div>
            </c:if>
            
        </div>
        <script>
            function closePopup() {
                document.querySelector('.popup').classList.remove('show');
                document.querySelector('.overlay').classList.remove('show');
            }
        </script>
    </body>
</html>