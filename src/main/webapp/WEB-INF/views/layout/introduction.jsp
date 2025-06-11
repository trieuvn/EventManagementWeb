<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

<style>
    .modal-dialog { max-width: 70vw; margin: auto; }
    .custom-modal-content { background-color: #fff; color: #000; border-radius: 10px; padding: 40px; text-align: center; position: relative; box-shadow: 0 0 25px rgba(0, 0, 0, 0.2); }
    .custom-title { font-size: 36px; font-weight: bold; margin-bottom: 15px; }
    .custom-subtext { font-size: 18px; color: #555; margin-bottom: 30px; }
    .option-card { background-color: #f9f9f9; border-radius: 10px; padding: 25px 20px; width: 48%; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05); }
    .option-card p { margin-bottom: 15px; font-size: 16px; color: #333; }
    .custom-btn { display: inline-block; padding: 12px 20px; font-size: 15px; font-weight: bold; border: none; border-radius: 5px; min-width: 160px; transition: 0.3s; text-decoration: none; }
    .btn-blue { background-color: #33c3f0; color: #000; border: 2px solid #28b0d8; }
    .btn-blue:hover { background-color: #28b0d8; }
    .btn-gold { background-color: #d4af37; color: #fff; border: 2px solid #c89b2c; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
    .btn-gold:hover { background-color: #c89b2c; transform: translateY(-2px); }
    .close-btn-topright { position: absolute; top: 15px; right: 20px; background-color: #d4af37; border: none; width: 36px; height: 36px; font-size: 24px; font-weight: bold; border-radius: 4px; cursor: pointer; }
    .forgot-password { text-align: right; margin-top: 10px; }
    @media (max-width: 768px) { .modal-dialog { max-width: 95vw; } .custom-title { font-size: 26px; } .custom-subtext { font-size: 15px; } .option-card { width: 100%; margin-bottom: 15px; } }
</style>

<!-- Hiển thị thông báo -->
<c:if test="${not empty msg}">
    <div class="alert alert-success">${msg}</div>
    <c:remove var="msg" scope="session"/>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content custom-modal-content">
            <button type="button" class="close-btn-topright" data-dismiss="modal" aria-label="Đóng">×</button>
            <h2 class="custom-title">CHUẨN BỊ SẴN SÀNG ĐỂ THAM GIA</h2>
            <p class="custom-subtext">Hãy chọn một hành động để bắt đầu hành trình của bạn</p>
            <div class="d-flex justify-content-between flex-wrap">
                <div class="option-card text-center">
                    <p><strong>Chưa có tài khoản?</strong></p>
                    <button type="button" class="custom-btn btn-blue" data-toggle="modal" data-target="#signupModal" data-dismiss="modal">
                        ĐĂNG KÝ
                    </button>
                </div>
                <div class="option-card text-center">
                    <p><strong>Đã có tài khoản?</strong></p>
                    <button type="button" class="custom-btn btn-gold" data-toggle="modal" data-target="#loginModal2" data-dismiss="modal">
                        ĐĂNG NHẬP
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="signupModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form:form method="post" action="${pageContext.request.contextPath}/signup" modelAttribute="userForm">
                <div class="modal-header">
                    <h5 class="custom-title" id="signupModalLabel">TẠO TÀI KHOẢN</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <form:input path="email" type="email" cssClass="form-control" id="email"/>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                    <div class="form-group">
                        <label for="firstName">Họ</label>
                        <form:input path="firstName" cssClass="form-control" id="firstName"/>
                        <form:errors path="firstName" cssClass="text-danger"/>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Tên</label>
                        <form:input path="lastName" cssClass="form-control" id="lastName"/>
                        <form:errors path="lastName" cssClass="text-danger"/>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Số điện thoại</label>
                        <form:input path="phoneNumber" cssClass="form-control" id="phoneNumber"/>
                        <form:errors path="phoneNumber" cssClass="text-danger"/>
                    </div>
                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <form:password path="password" cssClass="form-control" id="password"/>
                        <form:errors path="password" cssClass="text-danger"/>
                    </div>
                    <div class="form-group">
                        <label for="role">Bạn là</label>
                        <form:select path="role" cssClass="form-control" id="role">
                            <form:option value="0" label="Người tổ chức"/>
                            <form:option value="1" label="Người tham gia" selected="true"/>
                        </form:select>
                        <form:errors path="role" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success" style="padding: 12px 20px; font-size: 15px;">ĐĂNG KÝ</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<div class="modal fade" id="loginModal2" tabindex="-1" role="dialog" aria-labelledby="loginModal2Label" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content custom-modal-content">
            <button type="button" class="close-btn-topright" data-dismiss="modal" aria-label="Đóng">×</button>
            <h2 class="custom-title">ĐĂNG NHẬP</h2>
            <p class="custom-subtext">Vui lòng nhập thông tin để tiếp tục</p>
            <form method="post" action="${pageContext.request.contextPath}/login" id="loginForm">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="loginEmail">Email</label>
                        <input type="email" class="form-control" id="loginEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="loginPassword">Mật khẩu</label>
                        <input type="password" class="form-control" id="loginPassword" name="password" required>
                    </div>
                    <div class="forgot-password">
                        <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success" style="padding: 12px 20px; font-size: 15px;">ĐĂNG NHẬP</button>
                </div>
            </form>
        </div>
    </div>
</div>

<c:if test="${not empty error && !empty param.signup}">
    <script>
        $(document).ready(function() {
            $('#signupModal').modal('show');
        });
    </script>
</c:if>
<c:if test="${not empty error && !empty param.login}">
    <script>
        $(document).ready(function() {
            $('#loginModal2').modal('show');
        });
    </script>
</c:if>