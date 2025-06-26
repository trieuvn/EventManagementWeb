<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Festivo Template">
    <meta name="keywords" content="Festivo, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Festivo</title>
    <link rel="icon" href="https://cdn-icons-png.freepik.com/512/12769/12769433.png" type="image/png">

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Work+Sans:400,500,600,700,800,900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/benefit.css" type="text/css">
</head>

<body>
    <div id="preloder">
        <div class="loader"></div>
    </div>

    <!-- Header -->
    <jsp:include page="/WEB-INF/views/layout/header2.jsp" />

    <!-- Introduction Section -->
    <jsp:include page="/WEB-INF/views/layout/introduction.jsp" />

    <!-- Hero Section -->
    <c:if test="${not empty hero}">
        <jsp:include page="${hero}" />
    </c:if>

    <!-- Dynamic Content -->
    <div class="container content">
        <jsp:include page="${body}" />
    </div>

    <!-- Advantage Section (nếu có) -->
    <c:if test="${not empty advantage}">
        <jsp:include page="${advantage}" />
    </c:if>

    <!-- Footer -->
    <jsp:include page="/WEB-INF/views/layout/footer2.jsp" />

    <!-- JavaScript Files -->
    <script src="${pageContext.request.contextPath}/assets/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.countdown.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/jquery.slicknav.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>

<style>
    .content {
        min-height: 50vh; /* Đảm bảo nội dung đủ cao để footer đẩy xuống dưới */
        padding-bottom: 60px; /* Khoảng cách cho footer */
    }
</style>