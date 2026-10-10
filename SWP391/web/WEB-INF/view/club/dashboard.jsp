<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>Club Analytics Dashboard - SWP391</title>
    <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

    <!-- Fonts and icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />

    <style>
        .kpi-card {
            border-radius: 12px;
            box-shadow: 0 4px 14px rgba(0,0,0,0.06);
            transition: transform 0.2s ease, box-shadow 0.2s ease;
            background: #fff;
            border: none;
        }
        .kpi-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 24px rgba(0,0,0,0.12);
        }
        .kpi-icon {
            width: 52px;
            height: 52px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }
        .progress-thin {
            height: 10px;
            border-radius: 6px;
        }
        .dark-mode {
            background-color: #121212 !important;
            color: #e0e0e0 !important;
        }
        .dark-mode .card, .dark-mode .main-panel, .dark-mode .kpi-card {
            background-color: #1e1e1e !important;
            color: #e0e0e0 !important;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <!-- Sidebar -->
        <jsp:include page="../component/Sidebar.jsp"></jsp:include>

        <div class="main-panel">
            <div class="container-fluid">
                <div class="page-inner">
                    <!-- Page Header -->
                    <div class="d-flex align-items-left align-items-md-center flex-column flex-md-row pt-2 pb-4">
                        <div>
                            <h3 class="fw-bold mb-1">📊 Club Leader Analytics Dashboard</h3>
                            <h6 class="text-muted mb-0">Tổng quan tình hình hoạt động, nhân sự, tài chính và tiến độ công việc của Câu lạc bộ</h6>
                        </div>
                        <div class="ms-md-auto py-2 py-md-0">
                            <a href="ExportMemberCSV" class="btn btn-label-success btn-round me-2">
                                <i class="fas fa-file-excel me-1"></i> Xuất Báo Cáo CSV
                            </a>
                            <a href="bulk-email" class="btn btn-primary btn-round">
                                <i class="fas fa-paper-plane me-1"></i> Gửi Thông Báo CLB
                            </a>
                        </div>
                    </div>

                    <!-- 4 Macro KPI Cards -->
                    <div class="row">
                        <!-- KPI 1 -->
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round kpi-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="kpi-icon bg-primary text-white">
                                                <i class="fas fa-users"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ms-3 ms-sm-0">
                                            <div class="numbers">
                                                <p class="card-category text-muted mb-1">Tổng thành viên</p>
                                                <h4 class="card-title fw-bold">${totalMembers}</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- KPI 2 -->
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round kpi-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="kpi-icon bg-warning text-white">
                                                <i class="fas fa-tasks"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ms-3 ms-sm-0">
                                            <div class="numbers">
                                                <p class="card-category text-muted mb-1">Công việc đang làm</p>
                                                <h4 class="card-title fw-bold">${activeTasks}</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- KPI 3 -->
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round kpi-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="kpi-icon bg-info text-white">
                                                <i class="fas fa-calendar-check"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ms-3 ms-sm-0">
                                            <div class="numbers">
                                                <p class="card-category text-muted mb-1">Sự kiện học kỳ</p>
                                                <h4 class="card-title fw-bold">${upcomingEvents}</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- KPI 4 -->
                        <div class="col-sm-6 col-md-3">
                            <div class="card card-stats card-round kpi-card">
                                <div class="card-body">
                                    <div class="row align-items-center">
                                        <div class="col-icon">
                                            <div class="kpi-icon bg-success text-white">
                                                <i class="fas fa-wallet"></i>
                                            </div>
                                        </div>
                                        <div class="col col-stats ms-3 ms-sm-0">
                                            <div class="numbers">
                                                <p class="card-category text-muted mb-1">Số dư quỹ hiện tại</p>
                                                <h4 class="card-title fw-bold text-success">${fundBalance}</h4>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Row 2: Task Progress & Financial Health -->
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <div class="card card-round shadow-sm">
                                <div class="card-header border-0 pb-0">
                                    <h4 class="card-title fw-bold">🎯 Tỷ Lệ Tiến Độ Công Việc (Tasks)</h4>
                                    <p class="text-muted">Theo dõi hiệu suất thực thi công việc của các ban chuyên môn</p>
                                </div>
                                <div class="card-body">
                                    <div class="mb-3">
                                        <div class="d-flex justify-content-between mb-1">
                                            <span class="text-success fw-bold"><i class="fas fa-check-circle me-1"></i> Đã hoàn thành</span>
                                            <span class="fw-bold">${taskCompletedRate}% (17 tasks)</span>
                                        </div>
                                        <div class="progress progress-thin">
                                            <div class="progress-bar bg-success" style="width: ${taskCompletedRate}%"></div>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <div class="d-flex justify-content-between mb-1">
                                            <span class="text-warning fw-bold"><i class="fas fa-spinner me-1"></i> Đang thực hiện</span>
                                            <span class="fw-bold">${taskInProgressRate}% (5 tasks)</span>
                                        </div>
                                        <div class="progress progress-thin">
                                            <div class="progress-bar bg-warning" style="width: ${taskInProgressRate}%"></div>
                                        </div>
                                    </div>

                                    <div class="mb-2">
                                        <div class="d-flex justify-content-between mb-1">
                                            <span class="text-danger fw-bold"><i class="fas fa-exclamation-triangle me-1"></i> Quá hạn (Overdue)</span>
                                            <span class="fw-bold">${taskOverdueRate}% (3 tasks)</span>
                                        </div>
                                        <div class="progress progress-thin">
                                            <div class="progress-bar bg-danger" style="width: ${taskOverdueRate}%"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="card card-round shadow-sm">
                                <div class="card-header border-0 pb-0">
                                    <h4 class="card-title fw-bold">💰 Tổng Quan Thu Chi Trong Tháng</h4>
                                    <p class="text-muted">Minh bạch dòng tiền và ngân sách hoạt động</p>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table align-items-center mb-0">
                                            <tbody>
                                                <tr>
                                                    <td><i class="fas fa-arrow-circle-down text-success fa-lg me-2"></i> Tổng thu quỹ tháng này:</td>
                                                    <td class="text-end fw-bold text-success">+${monthlyIncome}</td>
                                                </tr>
                                                <tr>
                                                    <td><i class="fas fa-arrow-circle-up text-danger fa-lg me-2"></i> Tổng chi tiêu sự kiện:</td>
                                                    <td class="text-end fw-bold text-danger">-${monthlyExpense}</td>
                                                </tr>
                                                <tr class="table-light">
                                                    <td class="fw-bold">Số dư lũy kế khả dụng:</td>
                                                    <td class="text-end fw-bold text-primary">${fundBalance}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="mt-3 text-end">
                                        <a href="FinanceList" class="btn btn-sm btn-outline-primary">Xem Chi Tiết Sổ Quỹ <i class="fas fa-arrow-right ms-1"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JS -->
    <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
</body>
</html>
