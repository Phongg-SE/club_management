<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Gửi Thông Báo Email Hàng Loạt - SWP391</title>
    <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

    <!-- Fonts and icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
</head>
<body>
    <div class="wrapper">
        <jsp:include page="../component/Sidebar.jsp"></jsp:include>

        <div class="main-panel">
            <div class="container-fluid">
                <div class="page-inner">
                    <!-- Page Header -->
                    <div class="pt-2 pb-4">
                        <h3 class="fw-bold mb-1">✉️ Gửi Thông Báo Email Hàng Loạt</h3>
                        <h6 class="text-muted mb-0">Hỗ trợ gửi email tự động tới các ban chuyên môn, thành viên CLB kèm mẫu soạn sẵn</h6>
                    </div>

                    <!-- Alerts -->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="fas fa-check-circle me-2"></i> ${successMessage}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="fas fa-exclamation-circle me-2"></i> ${errorMessage}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>

                    <div class="card card-round shadow-sm">
                        <div class="card-body p-4">
                            <form id="bulkEmailForm" action="bulk-email" method="POST">
                                <input type="hidden" name="csrfToken" value="${csrfToken}">

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label fw-bold">Nhóm người nhận:</label>
                                        <select class="form-select" id="recipientGroup" name="recipientGroup">
                                            <option value="Toàn bộ thành viên CLB">Toàn bộ thành viên CLB (48 thành viên)</option>
                                            <option value="Ban Chủ Nhiệm">Ban Chủ Nhiệm (4 thành viên)</option>
                                            <option value="Ban Chuyên Môn">Ban Chuyên Môn (18 thành viên)</option>
                                            <option value="Ban Truyền Thông">Ban Truyền Thông (14 thành viên)</option>
                                            <option value="Ban Hậu Cần">Ban Hậu Cần (12 thành viên)</option>
                                        </select>
                                    </div>

                                    <div class="col-md-6 mb-3">
                                        <label class="form-label fw-bold">Chọn mẫu email có sẵn (Template):</label>
                                        <select class="form-select" id="emailTemplate" onchange="applyTemplate()">
                                            <option value="">-- Chọn mẫu thông báo --</option>
                                            <option value="meeting">Thư mời họp ban chuyên môn định kỳ</option>
                                            <option value="fee">Thông báo đóng quỹ sinh hoạt tháng</option>
                                            <option value="event">Lịch tổng duyệt sự kiện sắp tới</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label fw-bold">Tiêu đề email:</label>
                                    <input type="text" class="form-control" id="emailSubject" name="subject" placeholder="Nhập tiêu đề thông báo..." required>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label fw-bold">Nội dung chi tiết:</label>
                                    <textarea class="form-control" id="emailContent" name="messageContent" rows="6" placeholder="Soạn nội dung gửi đến các thành viên..." required></textarea>
                                </div>

                                <div class="d-flex justify-content-end gap-2">
                                    <button type="reset" class="btn btn-outline-secondary">Làm mới</button>
                                    <button type="button" class="btn btn-primary" onclick="confirmSend()">
                                        <i class="fas fa-paper-plane me-1"></i> Gửi Email Thông Báo
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Confirm Modal -->
    <div class="modal fade" id="confirmModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Xác nhận gửi email hàng loạt</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body p-4">
                    <p>Bạn có chắc chắn muốn phát tán email thông báo này tới nhóm <strong><span id="confirmGroup"></span></strong> không?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
                    <button type="button" class="btn btn-success" onclick="document.getElementById('bulkEmailForm').submit()">
                        Xác nhận gửi ngay
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
    <script>
        const templates = {
            meeting: {
                subject: '[CLB] Thông báo lịch họp ban chuyên môn định kỳ',
                content: 'Chào các bạn thành viên,\n\nBan Chủ Nhiệm thông báo lịch họp ban định kỳ vào 19h00 tối thứ Bảy tuần này tại phòng 302, Tòa Alpha.\nNội dung chính: Phân công công việc sự kiện tháng tới.\n\nYêu cầu các thành viên có mặt đúng giờ và chuẩn bị trước báo cáo tiến độ cá nhân.\n\nTrân trọng,\nBan Chủ Nhiệm CLB'
            },
            fee: {
                subject: '[CLB] Nhắc nhở hoàn thành đóng quỹ sinh hoạt tháng',
                content: 'Chào các bạn,\n\nThủ quỹ xin gửi thông báo hạn nộp quỹ hoạt động tháng này là ngày 15.\nSố tiền: 50.000 VNĐ / thành viên.\nHình thức nộp: Chuyển khoản qua số tài khoản CLB hoặc đóng trực tiếp cho Thủ quỹ.\n\nCảm ơn sự đóng góp đầy đủ của các bạn!\n\nTrân trọng,\nThủ Quỹ CLB'
            },
            event: {
                subject: '[CLB] Lịch tổng duyệt chương trình Gala Chào Tân Sinh Viên',
                content: 'Chào toàn thể ekip và các ban,\n\nBuổi tổng duyệt cuối cùng cho Gala sẽ diễn ra vào 14h00 Chủ Nhật tại Hội trường Trịnh Công Sơn.\nCác ban Hậu Cần, Kỹ Thuật âm thanh ánh sáng và Tiết Mục vui lòng có mặt trước 30 phút để set up.\n\nChúc chương trình của chúng ta thành công rực rỡ!\n\nTrân trọng,\nTrưởng Ban Tổ Chức'
            }
        };

        function applyTemplate() {
            const val = document.getElementById('emailTemplate').value;
            if (templates[val]) {
                document.getElementById('emailSubject').value = templates[val].subject;
                document.getElementById('emailContent').value = templates[val].content;
            }
        }

        function confirmSend() {
            const subj = document.getElementById('emailSubject').value.trim();
            const msg = document.getElementById('emailContent').value.trim();
            if (!subj || !msg) {
                alert('Vui lòng nhập đầy đủ tiêu đề và nội dung email!');
                return;
            }
            const grp = document.getElementById('recipientGroup').value;
            document.getElementById('confirmGroup').innerText = grp;
            new bootstrap.Modal(document.getElementById('confirmModal')).show();
        }
    </script>
</body>
</html>
