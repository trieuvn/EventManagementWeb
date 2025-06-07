<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin - Edit Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>${category.id == 0 ? 'Add' : 'Edit'} Category</h1>
        <form action="${pageContext.request.contextPath}/admin/categories/${category.id == 0 ? 'add' : 'update'}" method="post">
            <input type="hidden" name="id" value="${category.id}">
            <div class="mb-3">
                <label>Name</label>
                <input type="text" name="name" value="${category.name}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Description</label>
                <textarea name="description" class="form-control">${category.description}</textarea>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
    </div>
</body>
</html>