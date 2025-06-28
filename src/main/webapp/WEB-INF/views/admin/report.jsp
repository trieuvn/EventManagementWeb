<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Báo cáo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .chart-container { width: 100%; max-width: 800px; margin: 20px auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Báo cáo thống kê</h1>
        <form action="${pageContext.request.contextPath}/admin/reports" method="get" class="mb-3">
            <div class="row">
                <div class="col-md-4">
                    <label>Loại báo cáo</label>
                    <select name="reportType" class="form-control">
                        <option value="eventByCategory">Sự kiện theo danh mục</option>
                        <option value="eventByType">Sự kiện theo loại</option>
                        <option value="eventByStatus">Sự kiện theo trạng thái</option>
                        <option value="participantsByEvent">Người tham gia theo sự kiện</option>
                        <option value="ratings">Đánh giá trung bình</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label>Từ ngày</label>
                    <input type="date" name="fromDate" class="form-control" value="${fromDate}">
                </div>
                <div class="col-md-4">
                    <label>Đến ngày</label>
                    <input type="date" name="toDate" class="form-control" value="${toDate}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Xem báo cáo</button>
            <a href="${pageContext.request.contextPath}/admin/reports/export?type=${reportType}&fromDate=${fromDate}&toDate=${toDate}" class="btn btn-success mt-3">Xuất CSV</a>
        </form>

        <!-- Hiển thị bảng kết quả -->
        <c:if test="${not empty reportData}">
            <table class="table">
                <thead>
                    <tr>
                        <c:if test="${reportType == 'eventByCategory'}">
                            <th>Danh mục</th>
                            <th>Số sự kiện</th>
                        </c:if>
                        <c:if test="${reportType == 'eventByType'}">
                            <th>Loại sự kiện</th>
                            <th>Số sự kiện</th>
                        </c:if>
                        <c:if test="${reportType == 'eventByStatus'}">
                            <th>Trạng thái</th>
                            <th>Số sự kiện</th>
                        </c:if>
                        <c:if test="${reportType == 'participantsByEvent'}">
                            <th>Sự kiện</th>
                            <th>Số người tham gia</th>
                            <th>Tỷ lệ tham gia (%)</th>
                        </c:if>
                        <c:if test="${reportType == 'ratings'}">
                            <th>Sự kiện</th>
                            <th>Đánh giá trung bình</th>
                            <th>Bình luận</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="data" items="${reportData}">
                        <tr>
                            <c:if test="${reportType == 'eventByCategory' || reportType == 'eventByType'}">
                                <td>${data[0]}</td>
                                <td>${data[1]}</td>
                            </c:if>
                            <c:if test="${reportType == 'eventByStatus'}">
                                <td>
                                    <c:choose>
                                        <c:when test="${data[0] == 0}">Sắp tới</c:when>
                                        <c:when test="${data[0] == 1}">Đang diễn ra</c:when>
                                        <c:when test="${data[0] == 2}">Hoàn thành</c:when>
                                    </c:choose>
                                </td>
                                <td>${data[1]}</td>
                            </c:if>
                            <c:if test="${reportType == 'participantsByEvent'}">
                                <td>${data[0]}</td>
                                <td>${data[1]}</td>
                                <td>${data[2]}</td>
                            </c:if>
                            <c:if test="${reportType == 'ratings'}">
                                <td>${data[0]}</td>
                                <td>${data[1]}</td>
                                <td>${data[2]}</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <!-- Biểu đồ -->
        <c:if test="${not empty chartData}">
            <div class="chart-container">
                <canvas id="reportChart"></canvas>
            </div>
            <script>
                const ctx = document.getElementById('reportChart').getContext('2d');
                new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: [<c:forEach var="data" items="${chartData}">'${data[0]}',</c:forEach>],
                        datasets: [{
                            label: '${reportType}',
                            data: [<c:forEach var="data" items="${chartData}">${data[1]},</c:forEach>],
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: { beginAtZero: true }
                        }
                    }
                });
            </script>
        </c:if>

        <c:if test="${empty reportData and not empty reportType}">
            <p>Không có dữ liệu cho tiêu chí đã chọn.</p>
        </c:if>
    </div>
</body>
</html>