<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhóm phát triển</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .main-content {
            display: block;
            width: 100%;
        }

        /* Gỡ ảnh hưởng container từ header.jsp */
        .main-content > .container {
            all: unset;
            display: block;
            margin: 120px auto 0 auto;
            padding-left: 12px;
            padding-right: 12px;
            max-width: 1200px;
        }

        .member-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
            padding: 20px;
            text-align: center;
            transition: transform 0.2s;
            height: 100%;
        }

        .member-card:hover {
            transform: translateY(-5px);
        }

        .member-avatar {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 50%;
            margin-bottom: 12px;
        }

        .member-name {
            font-weight: 600;
            font-size: 18px;
            margin-bottom: 5px;
        }

        .member-role {
            font-size: 14px;
            color: #6c757d;
        }

        .intro {
            max-width: 800px;
            margin: 40px auto 30px;
            text-align: center;
            color: #444;
            font-size: 18px;
        }

        .intro em {
            font-style: italic;
            color: #5a5a5a;
        }
    </style>
</head>
<body>

<div class="main-content">
    <div class="container py-5">
        <h2 class="text-center mb-4">Thành viên phát triển hệ thống</h2>
        <div class="intro">
            FESTIVO không chỉ là một đồ án học thuật, mà còn là bước đệm đầu tiên của chúng tôi trên hành trình trở thành những lập trình viên chuyên nghiệp.<br/>
            <em>Cảm ơn thầy cô và bạn bè đã đồng hành, đóng góp và hỗ trợ chúng tôi trong suốt quá trình thực hiện dự án này!</em>
        </div>

        <div class="row g-4 justify-content-center">
            <div class="col-6 col-sm-4 col-md-3 col-lg-2 d-flex">
                <div class="member-card w-100">
                    <img src="${pageContext.request.contextPath}/assets/img/members/trieu.png" class="member-avatar" alt="Nguyễn Ngọc Triệu">
                    <div class="member-name">Nguyễn Ngọc Triệu</div>
                    <div class="member-role">Leader / Back-end</div>
                </div>
            </div>
            <div class="col-6 col-sm-4 col-md-3 col-lg-2 d-flex">
                <div class="member-card w-100">
                    <img src="${pageContext.request.contextPath}/assets/img/members/sang.png" class="member-avatar" alt="Trần Tấn Sang">
                    <div class="member-name">Trần Tấn Sang</div>
                    <div class="member-role">Back-end</div>
                </div>
            </div>
            <div class="col-6 col-sm-4 col-md-3 col-lg-2 d-flex">
                <div class="member-card w-100">
                    <img src="${pageContext.request.contextPath}/assets/img/members/anh.png" class="member-avatar" alt="Tô Thụy Trâm Anh">
                    <div class="member-name">Tô Thụy Trâm Anh</div>
                    <div class="member-role">Front-end Developer</div>
                </div>
            </div>
            <div class="col-6 col-sm-4 col-md-3 col-lg-2 d-flex">
                <div class="member-card w-100">
                    <img src="${pageContext.request.contextPath}/assets/img/members/phuc.png" class="member-avatar" alt="Nguyễn Hoàng Phúc">
                    <div class="member-name">Nguyễn Hoàng Phúc</div>
                    <div class="member-role">Front-end Developer</div>
                </div>
            </div>
            <div class="col-6 col-sm-4 col-md-3 col-lg-2 d-flex">
                <div class="member-card w-100">
                    <img src="${pageContext.request.contextPath}/assets/img/members/huy.png" class="member-avatar" alt="Nguyễn Tấn Thiện Huy">
                    <div class="member-name">Nguyễn Tấn Thiện Huy</div>
                    <div class="member-role">Back-end & Tester</div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
