<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>${category.name == null ? 'Thêm' : 'Sửa'} danh mục</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>${category.name == null ? 'Thêm' : 'Sửa'} danh mục</h1>
        <form:form modelAttribute="category" method="post" action="${pageContext.request.contextPath}/admin/categories/save">
            <form:hidden path="name"/>
            <div class="mb-3">
                <label for="name">Tên danh mục</label>
                <form:input path="name" class="form-control" readonly="${category.name != null}" required="true"/>
            </div>
            <div class="mb-3">
                <label for="description">Mô tả</label>
                <form:textarea path="description" class="form-control"/>
            </div>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>
            <button type="submit" class="btn btn-primary">Lưu</button>
            <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-secondary">Hủy</a>
        </form:form>
    </div>
</body>
</html>