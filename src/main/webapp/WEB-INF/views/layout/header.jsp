<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <header class="header-section">
        <div class="container">
            <div class="logo">
                <a href="${pageContext.request.contextPath}">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="Logo">
                </a>
            </div>
            <div class="nav-menu">
                <nav class="mainmenu mobile-menu">
                    <ul>
                        <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/about-us">About</a></li>
                        <li><a href="${pageContext.request.contextPath}/schedule">Schedule</a></li>
                        <li><a href="${pageContext.request.contextPath}/blog">Blog</a></li>
                        <li><a href="${pageContext.request.contextPath}/contact">Contacts</a></li>
                    </ul>
                </nav>
            </div>
            <div id="mobile-menu-wrap"></div>
        </div>
    </header>
</body>
</html>