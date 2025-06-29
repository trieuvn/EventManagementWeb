<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>QR Code</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f8f9fa;
        }
        .qr-container {
            text-align: center;
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        .qr-title {
            font-size: 22px;
            font-weight: 600;
            margin-bottom: 20px;
        }
        .qr-image {
            width: 250px;
            height: 250px;
            border: 4px solid #dee2e6;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="qr-container">
        <img src="data:image/png;base64, ${qrBase64}" alt="QR Code" class="qr-image" />
    </div>
</body>
</html>
