<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zxx">

    <header>
        <jsp:include page="/WEB-INF/views/admin/layout/header.jsp" />
    </header>

    <body>
        <jsp:include page="${body}" />
    </body>

    <footer>
        <jsp:include page="/WEB-INF/views/admin/layout/footer.jsp" />
    </footer>

</html>