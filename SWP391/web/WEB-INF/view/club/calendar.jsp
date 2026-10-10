<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Lịch Hoạt Động & Sự Kiện CLB - SWP391</title>
    <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

    <!-- Fonts and icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />

    <style>
        .calendar-grid {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            gap: 8px;
        }
        .calendar-header {
            font-weight: 700;
            text-align: center;
            padding: 10px 0;
            background: #f8f9fa;
            border-radius: 8px;
            color: #495057;
        }
        .calendar-cell {
            min-height: 110px;
            background: #ffffff;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 8px;
            transition: all 0.2s ease;
        }
        .calendar-cell:hover {
            border-color: #007bff;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }
        .calendar-cell.today {
            background-color: #f0f7ff;
            border: 2px solid #007bff;
        }
        .calendar-date {
            font-weight: 700;
            font-size: 14px;
            margin-bottom: 6px;
            color: #333;
        }
        .event-badge {
            font-size: 11px;
            padding: 4px 6px;
            border-radius: 6px;
            margin-bottom: 4px;
            cursor: pointer;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            display: block;
            text-decoration: none;
            transition: opacity 0.15s;
        }
        .event-badge:hover {
            opacity: 0.85;
        }
        .badge-event {
            background: #e7f1ff;
            color: #0d6efd;
            border-left: 3px solid #0d6efd;
        }
        .badge-task {
            background: #fff3cd;
            color: #664d03;
            border-left: 3px solid #ffc107;
        }
        .badge-deadline {
            background: #f8d7da;
            color: #842029;
            border-left: 3px solid #dc3545;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <jsp:include page="../component/Sidebar.jsp"></jsp:include>

        <div class="main-panel">
            <div class="container-fluid">
                <div class="page-inner">
                    <!-- Page Header -->
                    <div class="d-flex align-items-center justify-content-between pt-2 pb-4">
                        <div>
                            <h3 class="fw-bold mb-1">📅 Lịch Hoạt Động & Deadline Công Việc</h3>
                            <h6 class="text-muted mb-0">Theo dõi trực quan thời gian diễn ra sự kiện, lịch tập và hạn chót nộp báo cáo</h6>
                        </div>
                        <div class="d-flex align-items-center gap-2">
                            <span class="badge badge-event px-2 py-1"><i class="fas fa-calendar-alt me-1"></i> Sự kiện CLB</span>
                            <span class="badge badge-task px-2 py-1"><i class="fas fa-tasks me-1"></i> Công việc</span>
                            <span class="badge badge-deadline px-2 py-1"><i class="fas fa-fire me-1"></i> Deadline</span>
                        </div>
                    </div>

                    <div class="card card-round shadow-sm">
                        <div class="card-header d-flex justify-content-between align-items-center bg-white border-bottom">
                            <h5 class="fw-bold mb-0 text-primary">Tháng 10 / 2026</h5>
                            <div class="btn-group btn-group-sm">
                                <button class="btn btn-outline-secondary"><i class="fas fa-chevron-left"></i></button>
                                <button class="btn btn-outline-primary active">Hôm nay</button>
                                <button class="btn btn-outline-secondary"><i class="fas fa-chevron-right"></i></button>
                            </div>
                        </div>

                        <div class="card-body p-3">
                            <!-- 7 Days header -->
                            <div class="calendar-grid mb-2">
                                <div class="calendar-header">Thứ Hai</div>
                                <div class="calendar-header">Thứ Ba</div>
                                <div class="calendar-header">Thứ Tư</div>
                                <div class="calendar-header">Thứ Năm</div>
                                <div class="calendar-header">Thứ Sáu</div>
                                <div class="calendar-header">Thứ Bảy</div>
                                <div class="calendar-header text-danger">Chủ Nhật</div>
                            </div>

                            <!-- Calendar days -->
                            <div class="calendar-grid">
                                <div class="calendar-cell text-muted"><div class="calendar-date text-muted">28</div></div>
                                <div class="calendar-cell text-muted"><div class="calendar-date text-muted">29</div></div>
                                <div class="calendar-cell text-muted"><div class="calendar-date text-muted">30</div></div>
                                <div class="calendar-cell"><div class="calendar-date">1</div></div>
                                <div class="calendar-cell"><div class="calendar-date">2</div></div>
                                <div class="calendar-cell"><div class="calendar-date">3</div></div>
                                <div class="calendar-cell"><div class="calendar-date">4</div></div>

                                <div class="calendar-cell"><div class="calendar-date">5</div></div>
                                <div class="calendar-cell"><div class="calendar-date">6</div></div>
                                <div class="calendar-cell"><div class="calendar-date">7</div></div>
                                <div class="calendar-cell"><div class="calendar-date">8</div></div>
                                <div class="calendar-cell">
                                    <div class="calendar-date">9</div>
                                    <a href="javascript:;" onclick="showEventDetails('Họp Ban Truyền Thông', 'Phòng 302, Tòa Alpha', 'Thống nhất kế hoạch truyền thông cho sự kiện Chào Tân Sinh Viên', '09:00 - 11:00')" class="event-badge badge-task">
                                        <i class="fas fa-users me-1"></i> Họp Truyền Thông
                                    </a>
                                </div>
                                <div class="calendar-cell today">
                                    <div class="calendar-date text-primary">10 (Hôm nay)</div>
                                    <a href="javascript:;" onclick="showEventDetails('Hội Thao CLB 2026', 'Sân Vận Động FPT', 'Giải đấu bóng đá và kéo co giao lưu các CLB trường', '08:00 - 17:00')" class="event-badge badge-event">
                                        <i class="fas fa-trophy me-1"></i> Hội Thao CLB 2026
                                    </a>
                                    <a href="javascript:;" onclick="showEventDetails('Deadline nộp báo cáo thu chi', 'Hệ thống Quỹ CLB', 'Hạn chót các ban gửi danh sách hóa đơn thanh toán kỳ vừa rồi', '23:59')" class="event-badge badge-deadline">
                                        <i class="fas fa-exclamation-circle me-1"></i> Deadline báo cáo
                                    </a>
                                </div>
                                <div class="calendar-cell">
                                    <div class="calendar-date">11</div>
                                    <a href="javascript:;" onclick="showEventDetails('Gala Chào Tân Sinh Viên', 'Hội trường Trịnh Công Sơn', 'Đêm nhạc acoustic và chào đón K21 gia nhập CLB', '18:30 - 21:30')" class="event-badge badge-event">
                                        <i class="fas fa-music me-1"></i> Gala Chào Tân SV
                                    </a>
                                </div>

                                <div class="calendar-cell"><div class="calendar-date">12</div></div>
                                <div class="calendar-cell"><div class="calendar-date">13</div></div>
                                <div class="calendar-cell"><div class="calendar-date">14</div></div>
                                <div class="calendar-cell"><div class="calendar-date">15</div></div>
                                <div class="calendar-cell"><div class="calendar-date">16</div></div>
                                <div class="calendar-cell"><div class="calendar-date">17</div></div>
                                <div class="calendar-cell"><div class="calendar-date">18</div></div>

                                <div class="calendar-cell"><div class="calendar-date">19</div></div>
                                <div class="calendar-cell"><div class="calendar-date">20</div></div>
                                <div class="calendar-cell"><div class="calendar-date">21</div></div>
                                <div class="calendar-cell"><div class="calendar-date">22</div></div>
                                <div class="calendar-cell"><div class="calendar-date">23</div></div>
                                <div class="calendar-cell"><div class="calendar-date">24</div></div>
                                <div class="calendar-cell"><div class="calendar-date">25</div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Event Detail Modal -->
    <div class="modal fade" id="eventModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold" id="modalEventTitle">Chi tiết sự kiện</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <p class="mb-2"><strong><i class="fas fa-clock text-primary me-2"></i> Thời gian:</strong> <span id="modalEventTime"></span></p>
                    <p class="mb-2"><strong><i class="fas fa-map-marker-alt text-danger me-2"></i> Địa điểm:</strong> <span id="modalEventLocation"></span></p>
                    <hr>
                    <p class="mb-0 text-muted" id="modalEventDesc"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
    <script>
        function showEventDetails(title, loc, desc, time) {
            document.getElementById('modalEventTitle').innerText = title;
            document.getElementById('modalEventLocation').innerText = loc;
            document.getElementById('modalEventDesc').innerText = desc;
            document.getElementById('modalEventTime').innerText = time;
            new bootstrap.Modal(document.getElementById('eventModal')).show();
        }
    </script>
</body>
</html>
