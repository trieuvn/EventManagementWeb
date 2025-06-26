<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid mt-4">
    <!-- Phần trên: Hình ảnh và Organizer -->
    <div class="row mb-5">
        <div class="col-md-4">
            <div class="card card-custom p-3 bg-light-custom">
                <c:choose>
                    <c:when test="${not empty event.image}">
                        <img src="${event.image}" alt="Event Image" class="img-fluid rounded shadow-sm" style="height: 200px; object-fit: cover;" />
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/assets/img/eventdefault.jpg" alt="Event Default Image" class="img-fluid rounded shadow-sm" style="height: 200px; object-fit: cover;" />
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col-md-8">
            <div class="card card-custom p-4 bg-light-custom">
                <div class="row align-items-center">
                    <div class="col-md-3 d-flex justify-content-center">
                        <!-- Avatar của organizer -->
                        <c:choose>
                            <c:when test="${not empty event.organizer.avatar}">
                                <img src="data:image/jpeg;base64,${event.organizer.avatarBase64}" 
                                     alt="Organizer Avatar" 
                                     class="img-fluid organizer-avatar" />
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/assets/img/bussinesspeople.jpg" 
                                     alt="Default Avatar" 
                                     class="img-fluid organizer-avatar" />
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-9">
                        <h3 class="text-primary">Organizer Information</h3>
                        <c:choose>
                            <c:when test="${not empty event.organizer}">
                                <p class="mb-1"><strong>Name:</strong> 
                                    <c:choose>
                                        <c:when test="${not empty event.organizer.firstName and not empty event.organizer.lastName}">
                                            ${event.organizer.firstName} ${event.organizer.lastName}
                                        </c:when>
                                        <c:otherwise>
                                            ${event.organizer.lastName}
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p class="mb-1"><strong>Email:</strong> 
                                    <c:choose>
                                        <c:when test="${not empty event.organizer.email}">
                                            ${event.organizer.email}
                                        </c:when>
                                        <c:otherwise>
                                            N/A
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <p class="mb-0"><strong>Phone:</strong> 
                                    <c:choose>
                                        <c:when test="${not empty event.organizer.phoneNumber}">
                                            ${event.organizer.phoneNumber}
                                        </c:when>
                                        <c:otherwise>
                                            N/A
                                        </c:otherwise>
                                    </c:choose>
                                </p>
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

    <!-- Phần dưới: Thông tin sự kiện -->
    <div class="row mt-5">
        <div class="col-md-12">
            <div class="card card-custom p-4 bg-light-custom">
                <h2 class="text-success">${event.name}</h2>
                <p><strong>Description:</strong> ${event.description}</p>
                <p><strong>Slots:</strong> ${totalSlots}</p>
                <p><strong>Type:</strong> ${event.type}</p>
                <p><strong>Target:</strong> ${event.target}</p>
                <p><strong>Contact:</strong> ${event.contactInfo}</p>
            </div>
        </div>
    </div>

    <!-- Bảng danh sách vé -->
    <div class="row mt-5">
        <div class="col-md-12">
            <div class="card card-custom p-4 bg-light-custom">
                <h3 class="text-info">Available Tickets</h3>
                <table class="table table-bordered table-hover table-custom">
                    <thead>
                        <tr>
                            <th scope="col">Ticket Name</th>
                            <th scope="col">Price</th>
                            <th scope="col">Slot</th>
                            <th scope="col">Action</th>
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
                                        <c:otherwise>
                                            ${ticket.price}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${ticket.slots}</td>
                                <td>
                                    <!-- Nút View với data attributes để truyền dữ liệu -->
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
                                            data-ticket-type="${ticket.type}">
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

    <!-- Bootstrap Modal -->
    <div class="modal fade" id="ticketModal" tabindex="-1" aria-labelledby="ticketModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="ticketModalLabel">Ticket Details</h5>
                    <button type="button" class="btn btn-link text-dark close-btn" data-bs-dismiss="modal" aria-label="Close">x</button>
                </div>
                <div class="modal-body">
                    <p><strong>ID:</strong> <span id="modal-ticket-id"></span></p>
                    <p><strong>Date:</strong> <span id="modal-ticket-date"></span></p>
                    <p><strong>Description:</strong> <span id="modal-ticket-description"></span></p>
                    <p><strong>Duration:</strong> <span id="modal-ticket-duration"></span></p>
                    <p><strong>Index:</strong> <span id="modal-ticket-index"></span></p>
                    <p><strong>Name:</strong> <span id="modal-ticket-name"></span></p>
                    <p><strong>Price:</strong> <span id="modal-ticket-price"></span></p>
                    <p><strong>Reg Deadline:</strong> <span id="modal-ticket-regdeadline"></span></p>
                    <p><strong>Slots:</strong> <span id="modal-ticket-slots"></span></p>
                    <p><strong>Status:</strong> <span id="modal-ticket-status"></span></p>
                    <p><strong>Type:</strong> <span id="modal-ticket-type"></span></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success custom-register-btn">Register</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Include Bootstrap JS and jQuery if not already included -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // JavaScript to populate modal with ticket details
    $('#ticketModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var ticketId = button.data('ticket-id');
        var ticketDate = button.data('ticket-date') || 'N/A';
        var ticketDescription = button.data('ticket-description') || 'N/A';
        var ticketDuration = button.data('ticket-duration') || 'N/A';
        var ticketIndex = button.data('ticket-index') || 'N/A';
        var ticketName = button.data('ticket-name');
        var ticketPrice = button.data('ticket-price');
        var ticketRegDeadline = button.data('ticket-regdeadline') || 'N/A';
        var ticketSlots = button.data('ticket-slots');
        var ticketStatus = button.data('ticket-status') === 1 ? 'Active' : 'Inactive';
        var ticketType = button.data('ticket-type') || 'N/A';

        // Update modal content
        $('#modal-ticket-id').text(ticketId);
        $('#modal-ticket-date').text(ticketDate);
        $('#modal-ticket-description').text(ticketDescription);
        $('#modal-ticket-duration').text(ticketDuration);
        $('#modal-ticket-index').text(ticketIndex);
        $('#modal-ticket-name').text(ticketName);
        $('#modal-ticket-price').text(ticketPrice === 0 ? 'Free' : ticketPrice);
        $('#modal-ticket-regdeadline').text(ticketRegDeadline);
        $('#modal-ticket-slots').text(ticketSlots);
        $('#modal-ticket-status').text(ticketStatus);
        $('#modal-ticket-type').text(ticketType);
    });
</script>

<style>
    .card-custom {
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: all 0.3s ease;
    }
    .card-custom:hover {
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        transform: translateY(-2px);
    }
    .bg-light-custom {
        background-color: #f8f9fa;
    }
    .table-custom th {
        background-color: #007bff;
        color: white;
    }
    .table-custom td {
        vertical-align: middle;
    }
    .custom-register-btn {
        padding: 10px 20px; /* Tăng padding để phóng to nút */
        font-size: 1.1rem; /* Tăng kích thước chữ */
        min-width: 120px; /* Tăng chiều rộng tối thiểu */
    }
    .free-badge {
        color: #28a745;
        font-weight: bold;
        font-size: 1.1em;
    }
    .organizer-avatar {
        width: 150px;
        height: 150px;
        object-fit: cover;
    }
    @media (max-width: 768px) {
        .organizer-avatar {
            width: 100px;
            height: 100px;
        }
        .col-md-3 {
            flex: 0 0 100%;
            max-width: 100%;
            display: flex;
            justify-content: center;
            margin-bottom: 15px;
        }
    }
    .modal-header .close-btn {
        padding: 0;
        font-size: 1.5rem;
        line-height: 1;
        background: none;
        border: none;
        outline: none;
        color: #000;
    }
    .modal-header .close-btn:hover {
        color: #dc3545; /* Màu đỏ khi hover */
    }
</style>