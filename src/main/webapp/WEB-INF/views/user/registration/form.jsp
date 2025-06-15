<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register for Event</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Register for Event</h1>
        <form action="${pageContext.request.contextPath}/user/registration/register/${eventId}" method="post">
            <button type="submit" class="btn btn-primary">Confirm Registration</button>
        </form>
    </div>
</body>
</html>