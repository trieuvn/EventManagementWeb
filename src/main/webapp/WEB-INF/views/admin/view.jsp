<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Event Management View</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Event List</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Action</th>
        </tr>
        <c:forEach var="event" items="${events}">
            <tr>
                <td>${event.id}</td>
                <td>${event.name}</td>
                <td>${event.type}</td>
                <td>
                    <a href="<c:url value='/admin/events/edit/${event.id}'/>">Edit</a> |
                    <a href="<c:url value='/admin/events/delete/${event.id}'/>">Delete</a> |
                    <a href="<c:url value='/admin/events/detail/${event.id}'/>">View Detail</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <a href="<c:url value='/admin/events/add'/>">Add New Event</a>
</body>
</html>