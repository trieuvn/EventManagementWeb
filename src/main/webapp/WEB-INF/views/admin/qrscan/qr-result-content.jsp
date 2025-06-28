<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>QR Code Decoder</title>
        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;900&display=swap" rel="stylesheet">
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <!-- Tailwind CSS CDN -->
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Inter', sans-serif;
                background: linear-gradient(135deg, #f3f4f6 0%, #e5e7eb 100%);
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .error {
                color: #dc2626;
                font-weight: 500;
            }
            .success {
                color: #16a34a;
                font-weight: 500;
            }
            /* Popup styles */
            .popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: white;
                padding: 1.5rem;
                border-radius: 0.5rem;
                box-shadow: 0 10px 15px rgba(0, 0, 0, 0.2);
                z-index: 1000;
                max-width: 24rem;
                width: 90%;
                text-align: center;
                transition: all 0.3s ease;
            }
            .popup.show {
                display: block;
            }
            .popup-header {
                font-size: 1.25rem;
                font-weight: 600;
                margin-bottom: 0.75rem;
                color: #1f2937;
            }
            .popup-content {
                margin-bottom: 1.25rem;
                color: #4b5563;
            }
            .popup-close {
                background: #2563eb;
                color: white;
                border: none;
                padding: 0.5rem 1rem;
                border-radius: 0.375rem;
                cursor: pointer;
                transition: background 0.2s ease;
            }
            .popup-close:hover {
                background: #1e40af;
            }
            .overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.6);
                z-index: 999;
            }
            .overlay.show {
                display: block;
            }
            .qr-form {
                background: white;
                padding: 2rem;
                border-radius: 0.5rem;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                max-width: 28rem;
                width: 100%;
            }
            .qr-label {
                font-size: 0.875rem;
                font-weight: 500;
                color: #374151;
                margin-bottom: 0.5rem;
                display: block;
            }
            .qr-input {
                border: 1px solid #d1d5db;
                border-radius: 0.375rem;
                padding: 0.5rem;
                width: 100%;
                transition: border-color 0.2s ease;
            }
            .qr-input:focus {
                outline: none;
                border-color: #2563eb;
                box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
            }
            .vsubmit {
                background: #2563eb;
                color: white;
                font-weight: 500;
                padding: 0.75rem 1.5rem;
                border-radius: 0.375rem;
                width: 100%;
                transition: background 0.2s ease;
            }
            .vsubmit:hover {
                background: #1e40af;
            }
            @media (max-width: 640px) {
                .qr-form {
                    padding: 1.5rem;
                }
                .popup {
                    max-width: 20rem;
                }
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
        <div class="container flex justify-center items-center min-h-screen">
            <div class="qr-form">
                <h2 class="text-2xl font-bold text-center mb-6 text-gray-800">QR Code Decoder</h2>
                <form action="${pageContext.request.contextPath}/admin/decode" method="post" enctype="multipart/form-data" class="space-y-4">
                    <div class="form-group">
                        <label for="imageFile" class="qr-label">Upload QR Code Image</label>
                        <input type="file" name="imageFile" id="imageFile" accept="image/*" required class="qr-input">
                    </div>
                    <button type="submit" class="vsubmit">Decode QR Code</button>
                </form>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-4 error">${error}</div>
                </c:if>
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