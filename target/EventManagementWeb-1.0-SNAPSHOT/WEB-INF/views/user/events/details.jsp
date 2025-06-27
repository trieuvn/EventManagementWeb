<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid mt-4">
    <!-- Hình ảnh và thông tin tổ chức -->
    <div class="row mb-5 mt-5"> <!-- ĐÃ THÊM mt-5 ở đây -->
        <div class="col-md-4">
            <div class="card card-custom p-3 bg-light-custom">
                <c:choose>
                    <c:when test="${not empty event.image}">
                        <img src="data:image/jpeg;base64,${event.getBase64Image()}" alt="Event Image" class="img-fluid rounded shadow-sm" style="height: 200px; object-fit: cover;" />
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/assets/img/eventdefault.jpg" alt="Default Event" class="img-fluid rounded shadow-sm" style="height: 200px; object-fit: cover;" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col-md-8">
            <div class="card card-custom p-4 bg-light-custom">
                <div class="row align-items-center">
                    <div class="col-md-3 d-flex justify-content-center">
                        <c:choose>
                            <c:when test="${not empty event.organizer.avatar}">
                                <img src="data:image/jpeg;base64,${event.organizer.getBase64Avatar()}" class="img-fluid organizer-avatar" />
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/assets/img/bussinesspeople.jpg" class="img-fluid organizer-avatar" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-9">
                        <h3 class="text-primary">Organizer Information</h3>
                        <c:choose>
                            <c:when test="${not empty event.organizer}">
                                <p><strong>Name:</strong> 
                                    <c:choose>
                                        <c:when test="${not empty event.organizer.firstName and not empty event.organizer.lastName}">
                                            ${event.organizer.firstName} ${event.organizer.lastName}
                                        </c:when>
                                        <c:otherwise>${event.organizer.lastName}</c:otherwise>
                                    </c:choose>
                                </p>
                                <p><strong>Email:</strong> ${event.organizer.email != null ? event.organizer.email : "N/A"}</p>
                                <p><strong>Phone:</strong> ${event.organizer.phoneNumber != null ? event.organizer.phoneNumber : "N/A"}</p>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted">No organizer information available.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Thông tin sự kiện -->
    <div class="row mt-5">
        <div class="col-md-12">
            <div class="card card-custom p-4 bg-light-custom">
                <h2 class="text-success">${event.name}</h2>
                <p>${event.description}</p>
                <p><strong>Số lượng vé:</strong> ${event.getSlots()}</p>
                <p><strong>Hình thức:</strong> ${event.type}</p>
                <p><strong>Đối tượng tham gia:</strong> ${event.target}</p>
                <p><strong>Contact:</strong> ${event.contactInfo}</p>
            </div>
        </div>
    </div>

    <!-- Danh sách vé -->
    <div class="row mt-5">
        <div class="col-md-12">
            <div class="card card-custom p-4 bg-light-custom">
                <h3 class="text-info">Danh sách vé</h3>
                <table class="table table-bordered table-hover table-custom">
                    <thead>
                        <tr>
                            <th>Tên</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="ticket" items="${event.tickets}">
                            <tr>
                                <td>${ticket.name}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${ticket.price == 0}">
                                            <span class="free-badge">Free</span>
                                        </c:when>
                                        <c:otherwise>${ticket.price}</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${ticket.getAvailableSlots()}/${ticket.slots}</td>
                                <td>
                                    <button type="button" class="btn btn-outline-info btn-sm ms-2 custom-view-btn"
                                            data-bs-toggle="modal" data-bs-target="#ticketModal"
                                            data-ticket-id="${ticket.id}"
                                            data-ticket-date="${ticket.date}"
                                            data-ticket-description="${ticket.description}"
                                            data-ticket-duration="${ticket.duration}"
                                            data-ticket-index="${ticket.index}"
                                            data-ticket-name="${ticket.name}"
                                            data-ticket-price="${ticket.price}"
                                            data-ticket-regdeadline="${ticket.regDeadline}"
                                            data-ticket-slots="${ticket.slots}"
                                            data-ticket-status="${ticket.status}"
                                            data-ticket-type="${ticket.type}"
                                        data-ticket-lat="${ticket.location.latitude}"
                                        data-ticket-lng="${ticket.location.longitude}"
                                        data-ticket-endname="${ticket.location.name}">
                                        <i class="bi bi-eye"></i> View
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <!-- Modal -->
    <div class="modal fade" id="ticketModal" tabindex="-1" aria-labelledby="ticketModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg"> <!-- Kích thước lớn hơn -->
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="ticketModalLabel">Ticket Details</h5>
                    <button type="button" class="btn btn-link text-dark close-btn" data-bs-dismiss="modal">x</button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <!-- Bản đồ bên trái -->
                        <div class="col-md-6">
                            <div id="mapContainer" style="height: 400px; border-radius: 8px; overflow: hidden;">
                                <iframe id="mapIframe"
                                        src=""
                                        style="width: 100%; height: 100%; border: none;"
                                        allowfullscreen loading="lazy"></iframe>
                            </div>
                        </div>

                        <!-- Thông tin vé bên phải -->
                        <div class="col-md-6">
                            <p><strong>Ngày:</strong> <span id="modal-ticket-date"></span></p>
                            <p><strong>Mô tả:</strong> <span id="modal-ticket-description"></span></p>
                            <p><strong>Thời lượng:</strong> <span id="modal-ticket-duration"></span></p>
                            <p><strong>Tên vé:</strong> <span id="modal-ticket-name"></span></p>
                            <p><strong>Giá vé:</strong> <span id="modal-ticket-price"></span></p>
                            <p><strong>Hạn đăng ký:</strong> <span id="modal-ticket-regdeadline"></span></p>
                            <p><strong>Số lượng vé:</strong> <span id="modal-ticket-slots"></span></p>
                            <p><strong>Trạng thái:</strong> <span id="modal-ticket-status"></span></p>
                            <p><strong>Hình thức:</strong> <span id="modal-ticket-type"></span></p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <form id="registerForm" method="post">
                        <c:if test="${not empty _csrf}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </c:if>
                        <button type="submit" class="btn btn-success custom-register-btn">Register</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function () {
            $('.custom-view-btn').click(function () {
                const button = $(this);

                const ticketData = {
                    id: button.data('ticket-id'),
                    date: button.data('ticket-date') || 'N/A',
                    description: button.data('ticket-description') || 'N/A',
                    duration: button.data('ticket-duration') || 'N/A',
                    name: button.data('ticket-name'),
                    price: button.data('ticket-price'),
                    regDeadline: button.data('ticket-regdeadline') || 'N/A',
                    slots: button.data('ticket-slots'),
                    status: button.data('ticket-status') === 1 ? 'Active' : 'Inactive',
                    type: button.data('ticket-type') || 'N/A',
                    lat: button.data('ticket-lat'),
                    lng: button.data('ticket-lng'),
                    endname:button.data('ticket-endname')
                    
                };
                $('#registerForm').attr('action', '${pageContext.request.contextPath}/ticket/register/' + ticketData.id);
                // Gửi ticketId lên controller lưu vào session
                $.ajax({
                    url: '${pageContext.request.contextPath}/event/save-ticket-id',
                    type: 'POST',
                    data: {ticketId: ticketData.id},
                    success: function () {
                        console.log('Saved ticketId to session: ' + ticketData.id);
                    },
                    error: function () {
                        console.error('Error saving ticketId');
                    }
                });

                // Hiển thị dữ liệu vào modal
                $('#modal-ticket-date').text(ticketData.date);
                $('#modal-ticket-description').text(ticketData.description);
                $('#modal-ticket-duration').text(ticketData.duration);
                $('#modal-ticket-name').text(ticketData.name);
                $('#modal-ticket-price').text(ticketData.price === 0 ? 'Free' : ticketData.price);
                $('#modal-ticket-regdeadline').text(ticketData.regDeadline);
                $('#modal-ticket-slots').text(ticketData.slots);
                $('#modal-ticket-status').text(ticketData.status);
                $('#modal-ticket-type').text(ticketData.type);

                // Tạo URL bản đồ với điểm đến mặc định
                const mapUrl = '${pageContext.request.contextPath}/ticket/show-map'
                        + '?endLat=' + ticketData.lat
                        + '&endLng=' + ticketData.lng
                        + '&startName=' + encodeURIComponent('You are here')
                        + '&endName=' + ticketData.endname

                $('#mapIframe').attr('src', mapUrl);




            });
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function openTicketModal(ticket) {
            // Cập nhật thông tin vé trong modal
            document.getElementById('modal-ticket-date').textContent = ticket.date;
            document.getElementById('modal-ticket-description').textContent = ticket.description;
            document.getElementById('modal-ticket-duration').textContent = ticket.duration;
            document.getElementById('modal-ticket-name').textContent = ticket.name;
            document.getElementById('modal-ticket-price').textContent = ticket.price;
            document.getElementById('modal-ticket-regdeadline').textContent = ticket.regdeadline;
            document.getElementById('modal-ticket-slots').textContent = ticket.slots;
            document.getElementById('modal-ticket-status').textContent = ticket.status;
            document.getElementById('modal-ticket-type').textContent = ticket.type;

            // Cập nhật action của form với ticket_id
            const form = document.getElementById('registerForm');
            form.action = `${form.getAttribute('action')}/${ticket.id}`;

                    // Hiển thị modal
                    const modal = new bootstrap.Modal(document.getElementById('ticketModal'));
                    modal.show();
                }
    </script>