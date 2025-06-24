<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Event Image Hexadecimal</title>
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
        .hex-container {
            background-color: #f8f8f8;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            word-wrap: break-word;
            max-height: 300px;
            overflow-y: auto;
            font-family: monospace;
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
        <h2>Hexadecimal Image Data for Event: ${event.name}</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <h3>Hexadecimal String</h3>
        <div class="hex-container">
            <c:out value="${hexString}"/>
        </div>

        <p>Copy the above string (including '0x') into your SQL INSERT statement.</p>
        <p>Example: <code>INSERT INTO [EventManagement].[dbo].[EVENT] (id, name, type, target, image) VALUES (1, 'Event Name', 'Type', 'Target', ${hexString});</code></p>

        <a href="/events" class="back-link">Back to Events</a>
    </div>
</body>
</html>