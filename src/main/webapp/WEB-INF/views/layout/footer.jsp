<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<footer class="footer-section">
    <div class="row">
        <div class="col-lg-12">
            <div class="footer-text">
                <ul>
                    <li><a href="${pageContext.request.contextPath}">TRANG CHỦ</a></li>
                    <li><a href="${pageContext.request.contextPath}/about">GIỚI THIỆU</a></li>
                    <li><a href="${pageContext.request.contextPath}/checkin">ĐIỂM DANH</a></li>
                    <li><a href="${pageContext.request.contextPath}/history">LỊCH SỬ</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">ĐĂNG NHẬP</a></li>

                </ul>
                <div class="copyright-text"><p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                        Copyright &copy;<script>document.write(new Date().getFullYear());</script>- Ứng dụng Quản lý Sự kiện<i class="fa fa-heart" aria-hidden="true"></i> 

                </div>
            </div>
        </div>
    </div>
</footer>