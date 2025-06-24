<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Event Image</title>
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            text-align: center;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
        img {
            max-width: 500px;
            margin: 20px 0;
            border: 2px solid #ddd;
            border-radius: 5px;
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
        <h2>Image for Event: ${event.name}</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <c:choose>
            <c:when test="${not empty base64Image}">
                <img src="${base64Image}" alt="Event Image"/>
            </c:when>
            <c:otherwise>
                <p>No image available for this event.</p>
            </c:otherwise>
        </c:choose>

        <a href="/events" class="back-link">Back to Events</a>
    </div>
</body>
</html>