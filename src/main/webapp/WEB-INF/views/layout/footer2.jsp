<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<footer class="footer-section">
    <div class="footer-container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="footer-text">
                    <ul class="list-inline mb-2">
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/">TRANG CHỦ</a></li>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/checkin">ĐIỂM DANH</a></li>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/history">LỊCH SỬ</a></li>
                    </ul>
                    <div class="copyright-text">
                        <p class="text-light">
                            Copyright ©<script>document.write(new Date().getFullYear());</script>
                            - Ứng dụng Quản lý Sự kiện <i class="fa fa-heart text-danger"></i>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<style>
/* FOOTER FIX KHÔNG DÍNH MAIN FLEX */
.footer-section {
    background-color: #1a1a1a;
    color: white;
    padding: 20px 0;
    width: 100%;
    flex-shrink: 0;
    margin-top: auto;
    position: relative;
    z-index: 1;
}

.footer-container {
    max-width: 1140px;
    margin: 0 auto;
    padding: 0 15px;
}

.footer-text ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.footer-text ul li {
    display: inline-block;
    margin: 0 15px;
}

.footer-text ul li a {
    color: white;
    text-decoration: none;
    font-weight: 500;
}

.footer-text ul li a:hover {
    color: #0d6efd;
}

.copyright-text p {
    margin: 0;
    font-size: 14px;
    color: #aaa;
}

@media (max-width: 768px) {
    .footer-text ul li {
        display: block;
        margin: 5px 0;
        text-align: center;
    }
}
</style>
