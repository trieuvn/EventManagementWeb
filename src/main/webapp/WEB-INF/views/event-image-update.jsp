<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Event Image</title>
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
        .success {
            color: green;
            margin-bottom: 10px;
        }
        img {
            max-width: 300px;
            margin: 20px 0;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Update Image for Event: ${event.name}</h2>

        <c:if test="${param.success}">
            <div class="success">Image updated successfully!</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <h3>Current Image</h3>
        <c:choose>
            <c:when test="${not empty base64Image}">
                <img src="${base64Image}" alt="Event Image"/>
            </c:when>
            <c:otherwise>
                <p>No image available</p>
            </c:otherwise>
        </c:choose>

        <h3>Upload New Image</h3>
        <form method="post" action="${pageContext.request.contextPath}/events/${event.id}/image" enctype="multipart/form-data">
            <input type="file" name="imageFile" accept="image/*" required/>
26
            <br><br>
            <input type="submit" value="Upload Image"/>
        </form>

        <br>
        <a href="/events" class="back-link">Back to Events</a>
    </div>
</body>
</html>