<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<h2>Đăng ký tài khoản</h2>
<form method="post" action="${pageContext.request.contextPath}/signup">
    <p>
        <label>Tên đăng nhập:</label><br/>
        <input type="text" name="username" required/>
    </p>
    <p>
        <label>Mật khẩu:</label><br/>
        <input type="password" name="password" required/>
    </p>
    <p>
        <label>Email:</label><br/>
        <input type="email" name="email" required/>
    </p>
    <p><button type="submit">Đăng ký</button></p>
</form>