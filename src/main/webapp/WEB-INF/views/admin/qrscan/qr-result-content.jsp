
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>QR Code Decoder</title>
        <style>
            .error {
                color: red;
            }
            .success {
                color: green;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center mb-4">QR Code Decoder</h2>
            <form action="${pageContext.request.contextPath}/decode" method="post" enctype="multipart/form-data" class="qr-form">
                <div class="form-group">
                    <label for="imageFile" class="qr-label">Upload QR Code Image</label>
                    <input type="file" name="imageFile" id="imageFile" accept="image/*" required class="form-control qr-input">
                </div>
                <button type="submit" class="btn btn-primary vsubmit">Decode QR Code</button>
            </form>
            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3 error">${error}</div>
            </c:if>
            <c:if test="${not empty decodedText}">
                <div class="alert alert-success mt-3 success">Decoded QR Code: ${decodedText}</div>
            </c:if>
        </div>
    </body>
</html>