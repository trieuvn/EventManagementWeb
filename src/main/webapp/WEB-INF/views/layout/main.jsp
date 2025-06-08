<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="zxx">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Manup Template">
        <meta name="keywords" content="Manup, unica, creative, html">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Manup | Template</title>

        <!-- Google Font -->
        <link href="https://fonts.googleapis.com/css?family=Work+Sans:400,500,600,700,800,900&display=swap"
              rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap" rel="stylesheet">


        <!-- Css Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/elegant-icons.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/slicknav.min.css" type="text/css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css" type="text/css">
    </head>

    <body>
        <div id="preloder">
            <div class="loader"></div>
        </div>
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
        <!-- Hero Section Begin -->
        <section class="hero-section set-bg" data-setbg="${pageContext.request.contextPath}/assets/img/hero.jpg">
            <div class="container">
                <div class="row">
                    <div class="col-lg-7">
                        <div class="hero-text">
                            <h2>Change Your Mind<br /> To Become Sucess</h2>
                        </div>
                    </div>
                    <div class="col-lg-5">
                        <img src="${pageContext.request.contextPath}/assets/img/hero-right.png" alt="">
                    </div>
                </div>
            </div>
        </section>
        <jsp:include page="${body}"/>
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
        <script src="${pageContext.request.contextPath}/assets/js/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.countdown.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.slicknav.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>


