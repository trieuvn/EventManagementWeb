<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

<!-- CSRF Token Meta Tags (for Spring Security) -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

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
    <div id="flash-msg" class="alert alert-success alert-dismissible fade show" role="alert">
        ${msg}
        <button type="button" class="close" data-dismiss="alert" aria-label="Đóng">
            <span aria-hidden="true">×</span>
        </button>
    </div>
    <c:remove var="msg" scope="session"/>
</c:if>

<c:if test="${not empty error}">
    <div id="flash-error" class="alert alert-danger alert-dismissible fade show" role="alert">
        ${error}
        <button type="button" class="close" data-dismiss="alert" aria-label="Đóng">
            <span aria-hidden="true">×</span>
        </button>
    </div>
    <c:remove var="error" scope="session"/>
</c:if>

<!-- Initial Modal -->
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

<!-- Signup Modal -->
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
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success" style="padding: 12px 20px; font-size: 15px;">ĐĂNG KÝ</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<!-- Login Modal -->
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
                        <a href="#" data-toggle="modal" data-target="#forgotPasswordModal" data-dismiss="modal">Quên mật khẩu?</a>
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

<!-- Forgot Password Modal -->
<div class="modal fade" id="forgotPasswordModal" tabindex="-1" role="dialog" aria-labelledby="forgotPasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content custom-modal-content">
            <button type="button" class="close-btn-topright" data-dismiss="modal" aria-label="Đóng">×</button>
            <h2 class="custom-title">QUÊN MẬT KHẨU</h2>
            <p class="custom-subtext">Nhập email của bạn để nhận mã OTP</p>
            <form id="forgotPasswordForm">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="forgotEmail">Email</label>
                        <input type="email" class="form-control" id="forgotEmail" name="email" required>
                    </div>
                    <div class="form-group" id="otpSection" style="display: none;">
                        <label for="otpCode">Mã OTP</label>
                        <input type="text" class="form-control" id="otpCode" name="otp" required>
                    </div>
                    <div id="otpMessage" class="text-danger" style="display: none;"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="button" class="btn btn-success" id="sendOtpBtn" style="padding: 12px 20px; font-size: 15px;">GỬI OTP</button>
                    <button type="submit" class="btn btn-success" id="verifyOtpBtn" style="display: none; padding: 12px 20px; font-size: 15px;">XÁC NHẬN OTP</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Reset Password Modal -->
<div class="modal fade" id="resetPasswordModal" tabindex="-1" role="dialog" aria-labelledby="resetPasswordModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content custom-modal-content">
            <button type="button" class="close-btn-topright" data-dismiss="modal" aria-label="Đóng">×</button>
            <h2 class="custom-title">ĐẶT LẠI MẬT KHẨU</h2>
            <p class="custom-subtext">Nhập mật khẩu mới của bạn</p>
            <form id="resetPasswordForm" action="${pageContext.request.contextPath}/reset-password" method="post">
                <div class="modal-body">
                    <input type="hidden" name="email" id="resetEmail">
                    <div class="form-group">
                        <label for="newPassword">Mật khẩu mới</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    <div id="resetMessage" class="text-danger" style="display: none;"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-success" style="padding: 12px 20px; font-size: 15px;">LƯU MẬT KHẨU</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
$(document).ready(function () {
    // Fade out flash messages after 10 seconds
    setTimeout(function () {
        $('#flash-msg').fadeOut(1000);
        $('#flash-error').fadeOut(1000);
    }, 10000);

    // Show signup modal on error
    <c:if test="${not empty error && !empty param.signup}">
        $('#signupModal').modal('show');
    </c:if>

    // Show login modal on error
    <c:if test="${not empty error && !empty param.login}">
        $('#loginModal2').modal('show');
    </c:if>

    // Handle "Forgot Password" link click
    $('a[data-target="#forgotPasswordModal"]').on('click', function (e) {
        e.preventDefault();
        $('#loginModal2').modal('hide');
        $('#forgotPasswordModal').modal('show');
    });

    // Handle "Send OTP" button click
    $('#sendOtpBtn').on('click', function () {
        console.log('Send OTP button clicked');
        var email = $('#forgotEmail').val();
        console.log('Email:', email, 'URL:', '${pageContext.request.contextPath}/send-otp');
        if (!email) {
            $('#otpMessage').text('Vui lòng nhập email').show();
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/send-otp',
            type: 'POST',
            data: { email: email },
            beforeSend: function(xhr) {
                var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
                var csrfToken = $('meta[name="_csrf"]').attr('content');
                if (csrfHeader && csrfToken) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                    console.log('CSRF header set:', csrfHeader, csrfToken);
                } else {
                    console.warn('CSRF token or header not found, skipping header set');
                }
            },
            success: function (response) {
                console.log('AJAX success:', response);
                if (response.success) {
                    $('#otpSection').show();
                    $('#sendOtpBtn').hide();
                    $('#verifyOtpBtn').show();
                    $('#otpMessage').text('Mã OTP đã được gửi đến email của bạn')
                        .removeClass('text-danger').addClass('text-success').show();
                } else {
                    $('#otpMessage').text(response.message || 'Không thể gửi OTP. Vui lòng thử lại.').show();
                }
            },
            error: function (xhr, status, error) {
                console.error('AJAX error:', status, error, 'Response:', xhr.responseText);
                $('#otpMessage').text('Đã xảy ra lỗi: ' + (xhr.responseText || 'Vui lòng thử lại.')).show();
            }
        });
    });

    // Handle OTP verification
    $('#forgotPasswordForm').on('submit', function (e) {
        e.preventDefault();
        var email = $('#forgotEmail').val();
        var otp = $('#otpCode').val();

        if (!otp) {
            $('#otpMessage').text('Vui lòng nhập mã OTP').show();
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/verify-otp',
            type: 'POST',
            data: { email: email, otp: otp },
            beforeSend: function(xhr) {
                var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
                var csrfToken = $('meta[name="_csrf"]').attr('content');
                if (csrfHeader && csrfToken) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                }
            },
            success: function (response) {
                if (response.success) {
                    $('#forgotPasswordModal').modal('hide');
                    $('#resetEmail').val(email);
                    $('#resetPasswordModal').modal('show');
                } else {
                    $('#otpMessage').text(response.message || 'Mã OTP không hợp lệ. Vui lòng thử lại.').show();
                }
            },
            error: function (xhr, status, error) {
                console.error('AJAX error:', status, error, 'Response:', xhr.responseText);
                $('#otpMessage').text('Đã xảy ra lỗi: ' + (xhr.responseText || 'Vui lòng thử lại.')).show();
            }
        });
    });

    // Handle reset password form submission
    $('#resetPasswordForm').on('submit', function (e) {
        e.preventDefault();
        var email = $('#resetEmail').val();
        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();

        if (newPassword !== confirmPassword) {
            $('#resetMessage').text('Mật khẩu xác nhận không khớp').show();
            return;
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/reset-password',
            type: 'POST',
            data: { email: email, newPassword: newPassword, confirmPassword: confirmPassword },
            beforeSend: function(xhr) {
                var csrfHeader = $('meta[name="_csrf_header"]').attr('content');
                var csrfToken = $('meta[name="_csrf"]').attr('content');
                if (csrfHeader && csrfToken) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                    console.log('CSRF header set:', csrfHeader, csrfToken);
                } else {
                    console.warn('CSRF token or header not found, skipping header set');
                }
            },
            success: function (response) {
                console.log('AJAX success:', response);
                if (response.success) {
                    $('#resetPasswordModal').modal('hide');
                    $('#flash-msg').text('Mật khẩu đã được đặt lại thành công').show();
                    $('#loginModal2').modal('show');
                } else {
                    $('#resetMessage').text(response.message || 'Không thể đặt lại mật khẩu. Vui lòng thử lại.').show();
                }
            },
            error: function (xhr, status, error) {
                console.error('AJAX error:', status, error, 'Response:', xhr.responseText);
                $('#resetMessage').text('Đã xảy ra lỗi: ' + (xhr.responseText || 'Vui lòng thử lại.')).show();
            }
        });
    });

    // Reset forgot password modal when closed
    $('#forgotPasswordModal').on('hidden.bs.modal', function () {
        $('#forgotEmail').val('');
        $('#otpCode').val('');
        $('#otpSection').hide();
        $('#sendOtpBtn').show();
        $('#verifyOtpBtn').hide();
        $('#otpMessage').hide().text('');
    });

    // Reset reset password modal when closed
    $('#resetPasswordModal').on('hidden.bs.modal', function () {
        $('#resetEmail').val('');
        $('#newPassword').val('');
        $('#confirmPassword').val('');
        $('#resetMessage').hide().text('');
    });
});
</script>