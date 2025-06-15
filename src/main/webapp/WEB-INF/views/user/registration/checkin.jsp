<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Check-In</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Check-In</h1>
        <p>${message}</p>
        <a href="${pageContext.request.contextPath}/user/profile" class="btn btn-primary">Back to Profile</a>
    </div>
</body>
</html>