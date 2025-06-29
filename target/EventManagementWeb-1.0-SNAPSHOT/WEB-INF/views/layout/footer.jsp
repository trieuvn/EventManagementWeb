<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<footer class="footer-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="footer-text">
                    <ul class="list-inline">
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/">TRANG CHỦ</a></li>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/about">GIỚI THIỆU</a></li>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/contacts">NHÓM PHÁT TRIỂN</a></li>
                    </ul>
                    <div class="copyright-text mt-3">
                        <p>Copyright ©<script>document.write(new Date().getFullYear());</script> - Ứng dụng Quản lý Sự kiện <i class="fa fa-heart" style="color: red;" aria-hidden="true"></i></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<style>
    .footer-section {
        background-color: #1a1a1a;
        color: white;
        padding: 20px 0;
        width: 100%;
        position: relative;
        bottom: 0;
        margin-top: 40px; /* Khoảng cách từ nội dung lên footer */
    }
    .footer-text ul {
        list-style: none;
        padding: 0;
    }
    .footer-text ul li {
        display: inline;
        margin: 0 15px;
    }
    .footer-text ul li a {
        color: white;
        text-decoration: none;
    }
    .footer-text ul li a:hover {
        color: #007bff;
    }
    .copyright-text p {
        margin: 0;
        font-size: 14px;
    }
</style>