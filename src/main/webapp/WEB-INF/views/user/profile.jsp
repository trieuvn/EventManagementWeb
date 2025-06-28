<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- BODY CONTENT -->
<div class="profile-wrapper container mt-5 mb-5">
    <h1 class="mb-4">Thông tin cá nhân</h1>

    <!-- Form cập nhật thông tin -->
    <form action="${pageContext.request.contextPath}/user/profile" method="post">
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" value="${user.email}" class="form-control" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Họ</label>
            <input type="text" name="firstName" value="${user.firstName}" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Tên</label>
            <input type="text" name="lastName" value="${user.lastName}" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input type="text" name="phoneNumber" value="${user.phoneNumber}" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật</button>
    </form>

    <!-- Lịch sử đăng ký -->
    <h2 class="mt-5 mb-3">Lịch sử đăng ký sự kiện</h2>
    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>Sự kiện</th>
                <th>Trạng thái</th>
            </tr>
            </thea
        <tbody>
            <c:forEach var="participant" items="${participants}">
                <tr>
                    <td>${participant.ticket.event.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${participant.status == 1}">Đã xác nhận</c:when>
                            <c:otherwise>Chờ xác nhận</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>


<!-- FIX layout bị ngang do header -->
<style>
    .profile-wrapper {
        display: block;
        width: 100%;
    }

    body {
        padding-top: 0px; /* tránh bị header che */
    }
</style>
