
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Giới Thiệu - Festivo</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@600&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
            }
            section {
                padding: 60px 0;
            }
            .section-title {
                font-family: 'Playfair Display', serif;
                font-size: 2.5rem;
                margin-bottom: 30px;
            }
            .lead {
                font-size: 1.2rem;
            }
            .feature-card {
                transition: transform 0.3s, box-shadow 0.3s;
            }
            .feature-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
            }
            .blockquote {
                font-size: 1rem;
                color: #555;
                border-left: 4px solid #ccc;
            }
            section h2 {
                font-weight: 700;
            }
            section p strong {
                font-weight: 600;
            }
            section p em {
                font-style: italic;
            }
            .card {
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .card:hover {
                transform: translateY(-8px);
                box-shadow: 0 12px 20px rgba(0, 0, 0, 0.15);
            }
        </style>
    </head>
    <body>

        <!-- Header Section -->
        <header class="bg-light py-4 mb-5 shadow-sm" data-aos="fade-up" >
            <div class="container text-center">
                <h1 class="section-title">🎯Về Chúng Tôi</h1>
                <p>
                    Trong thế giới hiện đại, nơi con người luôn tìm kiếm những trải nghiệm đáng nhớ và những kết nối thực sự,
                    <strong>Festivo</strong> ra đời như một cây cầu kết nối giữa 
                    <em>ý tưởng</em> và <em>hiện thực</em>, giữa <em>người tổ chức</em> và <em>người tham gia</em>.
                </p>
                <p>
                    Chúng tôi là một nền tảng quản lý sự kiện toàn diện – nơi mọi người có thể
                    <strong>tạo ra, khám phá và tham gia</strong> vào các sự kiện một cách nhanh chóng, chuyên nghiệp và tràn đầy cảm hứng.
                    Từ những buổi hội thảo truyền cảm hứng, những sự kiện văn hóa – nghệ thuật đầy màu sắc,
                    cho đến các lớp học kỹ năng, workshop sáng tạo… tất cả đều bắt đầu từ một cú click nhẹ tại <strong>Festivo</strong>.
                </p>
                <blockquote class="blockquote border-start ps-3 mt-4">
                    <p class="mb-0">"Chúng tôi không chỉ tổ chức sự kiện – chúng tôi xây dựng cộng đồng."</p>
                </blockquote>
            </div>
        </header>

        <!-- Giới thiệu -->
        <section class="bg-white py-5" data-aos="fade-up">
            <div class="container">
                <h2 class="section-title text-center mb-4">Chúng tôi là ai?</h2>
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <p class="lead text-center">
                            <strong>Festivo</strong> là nền tảng quản lý sự kiện hiện đại, nơi người tổ chức và người tham gia cùng nhau kiến tạo nên những khoảnh khắc ý nghĩa và truyền cảm hứng.
                        </p>
                        <p class="text-center">
                            Trong thời đại kết nối, chúng tôi hiểu rằng sự kiện không chỉ là điểm đến – mà là hành trình kết nối <strong>ý tưởng, con người và cộng đồng</strong>. Với giao diện trực quan, tính năng mạnh mẽ và hỗ trợ toàn diện, <strong>Festivo</strong> giúp mọi người tạo sự kiện nhanh chóng, quản lý dễ dàng và truyền cảm hứng sâu sắc.
                        </p>
                        <blockquote class="blockquote text-center mt-4">
                            <p class="mb-0"><em>"Chúng tôi không chỉ tạo ra nền tảng – chúng tôi tạo ra trải nghiệm."</em></p>
                        </blockquote>
                    </div>
                </div>
            </div>
        </section>


        <!-- Sứ mệnh & Tầm nhìn dưới dạng Card -->
        <section class="bg-light" data-aos="fade-up">
            <div class="container">
                <h2 class="section-title text-center">Sứ mệnh & Tầm nhìn</h2>
                <div class="row g-4">
                    <div class="col-md-6">
                        <div class="card shadow h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-bullseye fa-2x text-primary mb-3"></i>
                                <h5 class="card-title">Sứ mệnh</h5>
                                <p class="card-text">Mang lại một trải nghiệm liền mạch và đầy cảm hứng cho cả người tổ chức lẫn người tham gia sự kiện. Chúng tôi tin rằng mỗi sự kiện là một cơ hội để học hỏi, kết nối và phát triển – và Festivo chính là người đồng hành tận tụy để hiện thực hóa điều đó.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card shadow h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-eye fa-2x text-success mb-3"></i>
                                <h5 class="card-title">Tầm nhìn</h5>
                                <p class="card-text">Trở thành nền tảng tổ chức sự kiện hàng đầu tại Việt Nam và vươn ra khu vực, nơi mọi ý tưởng đều có thể bùng cháy và lan tỏa đến đúng người, đúng thời điểm, đúng cách.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Người tham gia & Người tổ chức dưới dạng Card -->
        <section class="bg-white" data-aos="fade-up">
            <div class="container">
                <h2 class="section-title text-center">Chúng tôi dành cho ai?</h2>
                <div class="row g-4">
                    <div class="col-md-6">
                        <div class="card shadow h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-users fa-2x text-info mb-3"></i>
                                <h5 class="card-title">Người tham gia sự kiện</h5>
                                <p class="card-text">Bạn đam mê học hỏi, khám phá? Bạn muốn tham gia vào những cộng đồng cùng chí hướng? Tại Festivo, bạn có thể dễ dàng tìm kiếm các sự kiện phù hợp với sở thích, đăng ký chỉ trong vài giây, lưu lại lịch trình và nhận thông báo nhắc nhở – để không bỏ lỡ bất kỳ trải nghiệm nào.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card shadow h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-chalkboard-teacher fa-2x text-warning mb-3"></i>
                                <h5 class="card-title">Người tổ chức sự kiện</h5>
                                <p class="card-text">Bạn có một ý tưởng tuyệt vời và muốn lan tỏa nó? Hãy để chúng tôi giúp bạn biến điều đó thành hiện thực. Chỉ trong vài bước đơn giản, bạn có thể tạo sự kiện, mở bán vé, theo dõi số lượng đăng ký, giao tiếp với người tham dự và đánh giá hiệu quả sau sự kiện – tất cả trong một nền tảng duy nhất.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <!-- Tính năng nổi bật - dạng card với icon và hover -->
        <section class="bg-light" data-aos="fade-up">
            <div class="container">
                <h2 class="section-title text-center">🛠️ Những gì chúng tôi mang lại</h2>
                <p class="lead text-center mb-5">Chúng tôi không chỉ cung cấp công cụ. Chúng tôi tạo ra trải nghiệm.</p>
                <div class="row g-4">
                    <div class="col-md-6 col-lg-3">
                        <div class="card h-100 text-center shadow border-0 feature-card hover-shadow">
                            <div class="card-body">
                                <i class="fas fa-file-invoice-dollar fa-2x text-primary mb-3"></i>
                                <h5 class="card-title">Đăng ký & thanh toán trực tuyến</h5>
                                <p class="card-text">Không còn xếp hàng hay giấy tờ rườm rà.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <div class="card h-100 text-center shadow border-0 feature-card hover-shadow">
                            <div class="card-body">
                                <i class="fas fa-rocket fa-2x text-success mb-3"></i>
                                <h5 class="card-title">Tạo sự kiện đơn giản</h5>
                                <p class="card-text">Giao diện thân thiện, dễ dùng – dù bạn là ai.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <div class="card h-100 text-center shadow border-0 feature-card hover-shadow">
                            <div class="card-body">
                                <i class="fas fa-bell fa-2x text-warning mb-3"></i>
                                <h5 class="card-title">Thông báo & email tự động</h5>
                                <p class="card-text">Giữ kết nối với người tham dự trong suốt hành trình.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <div class="card h-100 text-center shadow border-0 feature-card hover-shadow">
                            <div class="card-body">
                                <i class="fas fa-chart-bar fa-2x text-danger mb-3"></i>
                                <h5 class="card-title">Thống kê thông minh</h5>
                                <p class="card-text">Hiểu rõ sự kiện của bạn đang hoạt động ra sao.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="bg-light" data-aos="fade-up">
            <div class="container">
                <h2 class="section-title text-center">💡Câu chuyện của chúng tôi</h2>
                <p>
                    Chúng tôi là những người trẻ, từng loay hoay tổ chức sự kiện sinh viên, từng quên mất giờ diễn vì không nhận được nhắc nhở, từng phải xử lý thủ công hàng trăm danh sách đăng ký.
                    Chính từ những trải nghiệm thật ấy, chúng tôi hiểu rằng: một nền tảng tốt có thể
                    <strong>giải phóng năng lượng sáng tạo</strong>, và giúp mọi người tập trung vào điều quan trọng nhất – 
                    <strong>chất lượng sự kiện và kết nối con người</strong>.
                </p>
                <blockquote class="blockquote border-start ps-3 mt-4">
                    <p class="mb-0">"Chúng tôi xây dựng <strong>Festivo</strong> không phải từ lý thuyết, mà từ chính những khó khăn thực tế mà người làm sự kiện từng trải qua."</p>
                </blockquote>
            </div>
        </section>


        <!-- Liên hệ -->
        <section class="bg-dark text-white" data-aos="fade-up">
            <div class="container text-center">
                <h2 class="section-title">Liên hệ với chúng tôi</h2>
                <p class="lead">
                    Email: support@tennenentang.vn<br>
                    Hotline: 0123 456 789<br>
                    Địa chỉ: Số 123 Nguyễn Văn Cừ, Quận 1, TP.HCM
                </p>
            </div>
        </section>

        <script src="https://cdn.jsdelivr.net/npm/aos@2.3.4/dist/aos.js"></script>
        <script>
            AOS.init({
                duration: 800, // Thời gian chạy hiệu ứng (ms)
                once: false       // Cho phép chạy lại mỗi khi phần tử xuất hiện lại
            });
        </script>
    </body>
</html>
