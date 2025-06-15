<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Styled Map with MapTiler</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet.fullscreen@2.4.0/Control.FullScreen.css" />
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
    <script src="https://unpkg.com/leaflet.fullscreen@2.4.0/Control.FullScreen.js"></script>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            background: #eaeaea;
        }
        #map {
            height: 100%;
            width: 100%;
            margin: auto;
            margin-top: 20px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }
    </style>
</head>
<body>
<div id="map"></div>

<script>
    // Inject giá trị từ Controller
    const startLat = ${startLat};
    const startLng = ${startLng};
    const endLat = ${endLat};
    const endLng = ${endLng};
    const nameA = "${nameA}";
    const nameB = "${nameB}";

    const start = [startLat, startLng];
    const end = [endLat, endLng];

    const map = L.map('map', {
        fullscreenControl: true
    });

    // Tile layer
    L.tileLayer('https://api.maptiler.com/maps/streets/{z}/{x}/{y}.png?key=Jb0uY7uAw08Ie89yPnA2', {
        attribution: '&copy; <a href="https://www.maptiler.com/copyright/">MapTiler</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    const redIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
        shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    L.marker(start, { icon: redIcon }).addTo(map).bindPopup(nameA).openPopup();
    L.marker(end, { icon: redIcon }).addTo(map).bindPopup(nameB);

    // OSRM routing API
    const url = "https://router.project-osrm.org/route/v1/driving/" + startLng + "," + startLat + ";" + endLng + "," + endLat + "?overview=full&geometries=geojson";

    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("Lỗi kết nối OSRM: " + response.status);
            return response.json();
        })
        .then(data => {
            if (!data.routes || data.routes.length === 0) {
                alert("Không tìm thấy đường đi.");
                return;
            }
            const route = data.routes[0].geometry;
            const routeLayer = L.geoJSON(route, {
                style: { color: 'blue', weight: 5 }
            }).addTo(map);

            map.fitBounds(routeLayer.getBounds());
        })
        .catch(error => {
            console.error("Lỗi tuyến đường:", error);
            alert("Không thể hiển thị đường đi.");
        });
</script>
</body>
</html>
