<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>QR Code Decoder</title>
    <style>
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
    <h2>QR Code Decoder</h2>

    <!-- Form to upload image -->
    <form action="${pageContext.request.contextPath}/decode" method="post" enctype="multipart/form-data">
        <input type="file" name="imageFile" accept="image/*" required>
        <button type="submit">Decode QR Code</button>
    </form>

    <!-- Display result or error -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:if test="${not empty decodedText}">
        <p class="success">Decoded QR Code: ${decodedText}</p>
    </c:if>
</body>
</html>