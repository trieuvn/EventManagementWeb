<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<footer class="footer-section">
    <div class="footer-container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="footer-text">
                    <ul class="list-inline">
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/user-page">TRANG CHỦ</a></li>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/user-page/checkin">ĐIỂM DANH</a></li>
                        <li class="list-inline-item"><a href="${pageContext.request.contextPath}/user-page/history">LỊCH SỬ</a></li>
                    </ul>
                    <div class="copyright-text mt-3">
                        <p>Copyright ©<script>document.write(new Date().getFullYear());</script> - Ứng dụng Quản lý Sự kiện 
                            <i class="fa fa-heart" style="color: red;" aria-hidden="true"></i>
                        </p>
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
    flex: 0 0 auto; /* Giữ nguyên chiều cao, không bị co giãn do flex */
    margin-top: 40px;
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
    color: #007bff;
}

.copyright-text p {
    margin: 0;
    font-size: 14px;
}

@media (max-width: 768px) {
    .footer-text ul li {
        display: block;
        margin: 5px 0;
    }
}
</style>
