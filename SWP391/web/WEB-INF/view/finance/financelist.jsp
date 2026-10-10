

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<%@ page import="model.entity.Club" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Finance List</title>
        <style>
            /* Thiết lập bảng */
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }

            th, td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }

            /* Header của bảng */
            th {
                background-color: #007bff;
                color: white;
                font-weight: bold;
                text-transform: uppercase;
            }

            /* Hover cho hàng */
            tr:hover {
                background-color: #f8f9fa;
            }

            /* Căn chỉnh cột đầu tiên */
            td:first-child, th:first-child {
                border-left: none;
            }

            /* Căn chỉnh cột cuối cùng */
            td:last-child, th:last-child {
                border-right: none;
            }

            /* Phân trang */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 16px;
            }

            .pagination a {
                padding: 10px 16px;
                margin: 5px;
                text-decoration: none;
                border-radius: 6px;
                color: #007bff;
                border: 1px solid #007bff;
                transition: all 0.3s ease-in-out;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: white;
            }

            /* Trang hiện tại */
            .pagination a.active {
                background-color: #0056b3;
                color: white;
                border-color: #0056b3;
                font-weight: bold;

                .search-bar {
                    display: flex;
                    gap: 5px;
                }

                .search-bar input {
                    padding: 8px;
                    border-radius: 5px;
                    border: 1px solid #ccc;
                }

                .search-bar button {
                    padding: 8px 12px;
                    border: none;
                    background-color: #007bff;
                    color: white;
                    border-radius: 5px;
                    cursor: pointer;
                }

                .search-bar button:hover {
                    background-color: #0056b3;
                }
            }
        </style>
        <meta
            content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
            name="viewport"
            />
        <link
            rel="icon"
            href="assets/img/kaiadmin/favicon.ico"
            type="image/x-icon"
            />

        <!-- Fonts and icons -->
        <script src="assets/js/plugin/webfont/webfont.min.js"></script>
        <script>
            WebFont.load({
                google: {families: ["Public Sans:300,400,500,600,700"]},
                custom: {
                    families: [
                        "Font Awesome 5 Solid",
                        "Font Awesome 5 Regular",
                        "Font Awesome 5 Brands",
                        "simple-line-icons",
                    ],
                    urls: ["assets/css/fonts.min.css"],
                },
                active: function () {
                    sessionStorage.fonts = true;
                },
            });
        </script>
        <!-- CSS Files -->
        <!-- CSS Files -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/toast.css" />

        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />


        <!-- favicon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- simplebar -->
        <link href="${pageContext.request.contextPath}/assets/css/simplebar.css" rel="stylesheet" type="text/css" />
        <!-- Select2 -->
        <link href="${pageContext.request.contextPath}/assets/css/select2.min.css" rel="stylesheet" />
        <!-- Date picker -->
        <link rel="stylesheet" href="../assets/css/flatpickr.min.css">
        <link href="${pageContext.request.contextPath}/assets/css/jquery.timepicker.min.css" rel="stylesheet" type="text/css" />
        <!-- Icons -->
        <link href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/assets/css/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />
    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->

                <div class="header-bar d-flex justify-content-between border-bottom">
                    <div class="d-flex align-items-center">
                        <a href="#" class="logo-icon">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-icon.png" height="30" class="small" alt="">
                        <span class="big">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="logo-light-mode" alt="">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-light.png" height="24" class="logo-dark-mode" alt="">
                        </span>
                    </a>
                    <a id="close-sidebar" class="btn btn-icon btn-pills btn-soft-primary ms-2" href="#">
                        <i class="uil uil-bars"></i>
                    </a>
                </div>

                <!-- header menu -->
                <ul class="list-unstyled mb-0">
                    <li class="list-inline-item mb-0">
                        <div class="dropdown dropdown-primary">
                            <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${pageContext.request.contextPath}/assets/images/language/american.png" class="avatar avatar-ex-small rounded-circle p-2" alt=""></button>
                            <div class="dropdown-menu dd-menu drop-ups dropdown-menu-end bg-white shadow border-0 mt-3 p-2" data-simplebar style="height: 175px;">
                                <a href="javascript:void(0)" class="d-flex align-items-center">
                                    <img src="${pageContext.request.contextPath}/assets/images/language/chinese.png" class="avatar avatar-client rounded-circle shadow" alt="">
                                    <div class="flex-1 text-left ms-2 overflow-hidden">
                                        <small class="text-dark mb-0">Chinese</small>
                                    </div>
                                </a>

                                <a href="javascript:void(0)" class="d-flex align-items-center mt-2">
                                    <img src="${pageContext.request.contextPath}/assets/images/language/european.png" class="avatar avatar-client rounded-circle shadow" alt="">
                                    <div class="flex-1 text-left ms-2 overflow-hidden">
                                        <small class="text-dark mb-0">European</small>
                                    </div>
                                </a>

                                <a href="javascript:void(0)" class="d-flex align-items-center mt-2">
                                    <img src="${pageContext.request.contextPath}/assets/images/language/indian.png" class="avatar avatar-client rounded-circle shadow" alt="">
                                    <div class="flex-1 text-left ms-2 overflow-hidden">
                                        <small class="text-dark mb-0">Indian</small>
                                    </div>
                                </a>

                                <a href="javascript:void(0)" class="d-flex align-items-center mt-2">
                                    <img src="${pageContext.request.contextPath}/assets/images/language/japanese.png" class="avatar avatar-client rounded-circle shadow" alt="">
                                    <div class="flex-1 text-left ms-2 overflow-hidden">
                                        <small class="text-dark mb-0">Japanese</small>
                                    </div>
                                </a>

                                <a href="javascript:void(0)" class="d-flex align-items-center mt-2">
                                    <img src="${pageContext.request.contextPath}/assets/images/language/russian.png" class="avatar avatar-client rounded-circle shadow" alt="">
                                    <div class="flex-1 text-left ms-2 overflow-hidden">
                                        <small class="text-dark mb-0">Russian</small>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </li>

                    <li class="list-inline-item mb-0 ms-1">
                        <div class="dropdown dropdown-primary">
                            <input type="hidden" id="serverErrorMessage" value="${requestScope.error}" />
                            <input type="hidden" id="serverSuccessMessage" value="${requestScope.success}" />
                            <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${account.avatar_url}" class="avatar avatar-ex-small rounded-circle" alt=""></button>
                            <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                                <a class="dropdown-item d-flex align-items-center text-dark" href="profile.html">
                                    <img src="${account.avatar_url}" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                    <div class="flex-1 ms-2">
                                        <span class="d-block mb-1">${account.full_name}</span>
                                        <small class="text-muted">${accountRole.role_name}</small>
                                    </div>
                                </a>
                                <a class="dropdown-item text-dark" href="index.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span> Dashboard</a>
                                <a class="dropdown-item text-dark" href="profile.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings</a>
                                <div class="dropdown-divider border-top"></div>
                                <a class="dropdown-item text-dark" href="logout"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="main-panel">                    
                <div class="container">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">
                                Finance Management
                            </h3>
                            <ul class="breadcrumbs mb-3">
                                <li class="nav-home">
                                    <a href="home">
                                        <i class="icon-home"></i>
                                    </a>
                                </li>
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="#">Finance Management</a>
                                </li>
                            </ul>
                        </div>

                        <!-- Products Table Card -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h4 class="card-title">
                                                Finances List
                                            </h4>
                                            <!-- Nút mở modal -->
                                            <a href="#" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addFinanceModal">
                                                <i class="fas fa-plus"></i> Add Finance
                                            </a>
                                        </div>
                                    </div>

                                    <!-- Modal -->
                                    <div class="modal fade" id="addFinanceModal" tabindex="-1" aria-labelledby="addFinanceModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="addFinanceModalLabel">Add New Finance</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="${pageContext.request.contextPath}/AddFinance" method="post">

                                                        <!-- Finance Name -->
                                                        <div class="mb-3">
                                                            <label for="financeName" class="form-label">Finance Name</label>
                                                            <input type="text" class="form-control" id="financeName" name="financeName" required>
                                                        </div>

                                                        <!-- Event (Optional) -->
                                                        <div class="mb-3">
                                                            <label for="eventId" class="form-label">Event (Optional)</label>
                                                            <select class="form-select" id="eventId" name="eventId">
                                                                <option value="">-- No Event --</option>
                                                                <c:forEach var="event" items="${events}">
                                                                    <option value="${event.event_id}">${event.name}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>

                                                        <!-- Thêm input hidden để giữ giá trị selectedClubId -->
                                                        <input type="hidden" name="clubId" value="${selectedClubId}">


                                                        <!-- Finance Date -->
                                                        <div class="mb-3">
                                                            <label for="financeDate" class="form-label">Finance Date</label>
                                                            <input type="date" class="form-control" id="financeDate" name="financeDate" required>
                                                        </div>

                                                        <!-- Amount (Required) -->
                                                        <div class="mb-3">
                                                            <label for="amount" class="form-label">Amount</label>
                                                            <input type="number" class="form-control" id="amount" name="amount" required>
                                                        </div>

                                                        <!-- Is Expense (Radio Buttons) -->
                                                        <div class="mb-3">
                                                            <label class="form-label">Transaction Type</label>
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="isExpense" id="expense" value="Yes" 
                                                                       <%= "Yes".equals(request.getParameter("isExpense")) ? "checked" : "" %>>
                                                                <label class="form-check-label" for="expense">Expense</label>
                                                            </div>
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" name="isExpense" id="income" value="No"
                                                                       <%= "No".equals(request.getParameter("isExpense")) ? "checked" : "" %>>
                                                                <label class="form-check-label" for="income">Income</label>
                                                            </div>
                                                        </div>


                                                        <!-- Details (Optional) -->
                                                        <div class="mb-3">
                                                            <label for="details" class="form-label">Details (Optional)</label>
                                                            <textarea class="form-control" id="details" name="details" rows="3"></textarea>
                                                        </div>


                                                        <!-- Submit Button -->
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                            <button type="submit" class="btn btn-primary">Add Finance</button>
                                                        </div>

                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="card-body">


                                        <script>
                                            // Ẩn thông báo sau 3 giây
                                            setTimeout(function () {
                                                let errorAlert = document.getElementById("errorAlert");
                                                if (errorAlert) {
                                                    errorAlert.style.display = "none";
                                                }
                                            }, 3000);
                                        </script>

                                        <div id="notification" style="
                                             display: none;
                                             position: fixed;
                                             top: 10px;
                                             right: 10px;
                                             background-color: #4CAF50;
                                             color: white;
                                             padding: 10px 20px;
                                             border-radius: 5px;
                                             z-index: 1000;">
                                        </div>



                                        <!-- FORM BỘ LỌC -->
                                        <form method="GET" action="FinanceList">
                                            <div class="row g-3 align-items-center">

                                                <!-- Bộ lọc Club -->
                                                <%
                                                    List<Club> clubs = (List<Club>) request.getAttribute("clubs");
                                                    Club selectedClub = (Club) request.getAttribute("selectedClub");
                                                %>
                                                <div class="col-md-3">
                                                    <label for="clubIdFilter" class="form-label">Club</label>
                                                    <select id="clubIdFilter" class="form-select" name="clubId" onchange="this.form.submit()">
                                                        <% if (clubs != null && !clubs.isEmpty() && selectedClub != null) { %>
                                                        <% for (Club club : clubs) { %>
                                                        <option value="<%= club.getClub_id() %>" <%= (selectedClub.getClub_id() == club.getClub_id()) ? "selected" : "" %> >
                                                            <%= club.getName() %>
                                                        </option>
                                                        <% } %>
                                                        <% } else { %>
                                                        <option value="">Không có câu lạc bộ nào</option>
                                                        <% } %>
                                                    </select>
                                                </div>

                                                <!-- Bộ lọc isExpense -->
                                                <div class="col-md-2">
                                                    <label for="isExpenseFilter" class="form-label">isExpense</label>
                                                    <select id="isExpenseFilter" name="isExpense" class="form-select" onchange="this.form.submit()">
                                                        <option value="">All</option>
                                                        <option value="Yes" <%= "Yes".equals(request.getParameter("isExpense")) ? "selected" : "" %>>Expense</option>
                                                        <option value="No" <%= "No".equals(request.getParameter("isExpense")) ? "selected" : "" %>>Income</option>
                                                    </select>
                                                </div>

                                                <!-- Bộ lọc Status -->
                                                <div class="col-md-2">
                                                    <label for="statusFilter" class="form-label">Status</label>
                                                    <select id="statusFilter" name="status" class="form-select" onchange="this.form.submit()">
                                                        <option value="">All status</option>
                                                        <option value="Pending" <%= "Pending".equals(request.getParameter("status")) ? "selected" : "" %>>Pending</option>
                                                        <option value="Approve" <%= "Approve".equals(request.getParameter("status")) ? "selected" : "" %>>Approve</option>
                                                        <option value="Rejecte" <%= "Rejecte".equals(request.getParameter("status")) ? "selected" : "" %>>Reject</option>
                                                    </select>
                                                </div>

                                                <!-- Ô tìm kiếm (Chỉ submit khi bấm nút) -->
                                                <div class="col-md-4 ms-auto">
                                                    <label class="form-label">Search</label>
                                                    <div class="input-group">
                                                        <input type="text" name="searchName" class="form-control"
                                                               value="<%= request.getParameter("searchName") != null ? request.getParameter("searchName") : "" %>"
                                                               placeholder="Search by FinanceName">
                                                        <button type="submit" class="btn btn-primary">Search</button>
                                                    </div>
                                                </div>

                                            </div>
                                        </form>



                                        <div class="table-responsive">
                                            <table id="financeTable" class="display table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Finance Name</th>
                                                        <th>Event Name</th>
                                                        <th>Member</th>
                                                        <th>Amount</th>
                                                        <th>Is Expense</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="financeBody">
                                                    <c:forEach items="${finances}" var="fin">
                                                        <tr>
                                                            <td>${fin.financeId}</td>
                                                            <td>${fin.finance_name}</td>
                                                            <td>${fin.eventName}</td>
                                                            <td>${fin.memberName}</td>
                                                            <td>${fin.amount}</td>
                                                            <td>
                                                                <span class="editable-badge badge badge-pill ${fin.isExpense eq 'Yes' ? 'badge-danger' : 'badge-success'}"
                                                                      data-field="is_expense" data-id="${fin.financeId}">
                                                                    ${fin.isExpense eq 'Yes' ? 'Expense' : 'Income'}
                                                                </span>
                                                            </td>
                                                            <td>
                                                                <span class="badge badge-pill
                                                                      ${fin.status eq 'Pending' ? 'badge-warning' : 
                                                                        (fin.status eq 'Approve' ? 'badge-success' : 'badge-danger')}">
                                                                          ${fin.status}
                                                                      </span>
                                                                </td>

                                                                <td>
                                                                    <a href="FinanceDetail?financeId=${fin.financeId}" class="btn btn-info btn-sm mb-1">
                                                                        <i class="fas fa-edit"></i> Detail
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                    <!-- Bootstrap JS (CDN) -->
                                                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                                                </table>
                                            </div>

                                            <!-- PHÂN TRANG -->
                                            <div class="d-flex justify-content-center mt-3">
                                                <ul class="pagination">
                                                    <%
                                                        // Lấy giá trị phân trang từ request
                                                        Integer currentPage = (Integer) request.getAttribute("currentPage");
                                                        Integer totalPages = (Integer) request.getAttribute("totalPages");

                                                        // Đảm bảo currentPage và totalPages không null
                                                        currentPage = (currentPage == null) ? 1 : currentPage;
                                                        totalPages = (totalPages == null) ? 1 : totalPages;

                                                        // Lấy tham số bộ lọc từ request
                                                        String clubId = request.getParameter("clubId");
                                                        String status = request.getParameter("status");
                                                        String searchName = request.getParameter("searchName");
                                                        String isExpense = request.getParameter("isExpense");

                                                        // Xử lý giá trị null để tránh lỗi URL
                                                        clubId = (clubId == null || "null".equalsIgnoreCase(clubId.trim())) ? "" : clubId;
                                                        status = (status == null || "null".equalsIgnoreCase(status.trim())) ? "" : status;
                                                        searchName = (searchName == null || "null".equalsIgnoreCase(searchName.trim())) ? "" : searchName;
                                                        isExpense = (isExpense == null || "null".equalsIgnoreCase(isExpense.trim())) ? "" : isExpense;

                                                        // Chuỗi queryString để giữ lại bộ lọc khi chuyển trang
                                                        String queryString = "clubId=" + clubId + "&status=" + status + "&searchName=" + searchName + "&isExpense=" + isExpense;
                                                    %>

                                                    <!-- Nút Previous -->
                                                    <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                                                        <a class="page-link" href="FinanceList?page=<%= currentPage - 1 %>&<%= queryString %>">Previous</a>
                                                    </li>

                                                    <!-- Số trang -->
                                                    <% for (int i = 1; i <= totalPages; i++) { %>
                                                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                                        <a class="page-link" href="FinanceList?page=<%= i %>&<%= queryString %>"><%= i %></a>
                                                    </li>
                                                    <% } %>

                                                    <!-- Nút Next -->
                                                    <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                                                        <a class="page-link" href="FinanceList?page=<%= currentPage + 1 %>&<%= queryString %>">Next</a>
                                                    </li>
                                                </ul>
                                            </div>

                                        </div>                                   
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>





                    <footer class="footer">
                        <div class="container-fluid d-flex justify-content-between">
                            <nav class="pull-left">
                                <ul class="nav">
                                    <li class="nav-item">
                                        <a class="nav-link" href="http://www.themekita.com">
                                            ThemeKita
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="#"> Help </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="#"> Licenses </a>
                                    </li>
                                </ul>
                            </nav>
                            <div class="copyright">
                                2024, made with <i class="fa fa-heart heart text-danger"></i> by
                                <a href="http://www.themekita.com">ThemeKita</a>
                            </div>
                            <div>
                                Distributed by
                                <a target="_blank" href="https://themewagon.com/">ThemeWagon</a>.
                            </div>
                        </div>
                    </footer>
                </div>

            </div>
            <!--   Core JS Files   -->
            <script src="assets/js/core/jquery-3.7.1.min.js"></script>
            <script src="assets/js/core/popper.min.js"></script>
            <script src="assets/js/core/bootstrap.min.js"></script>

            <!-- jQuery Scrollbar -->
            <script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
            <!-- Datatables -->
            <script src="assets/js/plugin/datatables/datatables.min.js"></script>
            <!-- Kaiadmin JS -->
            <script src="assets/js/kaiadmin.min.js"></script>
            <!-- Kaiadmin DEMO methods, don't include it in your project! -->
            <script src="assets/js/setting-demo2.js"></script>
            <script>
                                                        $(document).ready(function () {
                                                            $("#basic-datatables").DataTable({});

                                                            $("#multi-filter-select").DataTable({
                                                                pageLength: 5,
                                                                initComplete: function () {
                                                                    this.api()
                                                                            .columns()
                                                                            .every(function () {
                                                                                var column = this;
                                                                                var select = $(
                                                                                        '<select class="form-select"><option value=""></option></select>'
                                                                                        )
                                                                                        .appendTo($(column.footer()).empty())
                                                                                        .on("change", function () {
                                                                                            var val = $.fn.dataTable.util.escapeRegex($(this).val());

                                                                                            column
                                                                                                    .search(val ? "^" + val + "$" : "", true, false)
                                                                                                    .draw();
                                                                                        });

                                                                                column
                                                                                        .data()
                                                                                        .unique()
                                                                                        .sort()
                                                                                        .each(function (d, j) {
                                                                                            select.append(
                                                                                                    '<option value="' + d + '">' + d + "</option>"
                                                                                                    );
                                                                                        });
                                                                            });
                                                                },
                                                            });

                                                            // Add Row
                                                            $("#add-row").DataTable({
                                                                pageLength: 5,
                                                            });

                                                            var action =
                                                                    '<td> <div class="form-button-action"> <button type="button" data-bs-toggle="tooltip" title="" class="btn btn-link btn-primary btn-lg" data-original-title="Edit Task"> <i class="fa fa-edit"></i> </button> <button type="button" data-bs-toggle="tooltip" title="" class="btn btn-link btn-danger" data-original-title="Remove"> <i class="fa fa-times"></i> </button> </div> </td>';

                                                            $("#addRowButton").click(function () {
                                                                $("#add-row")
                                                                        .dataTable()
                                                                        .fnAddData([
                                                                            $("#addName").val(),
                                                                            $("#addPosition").val(),
                                                                            $("#addOffice").val(),
                                                                            action,
                                                                        ]);
                                                                $("#addRowModal").modal("hide");
                                                            });
                                                        });


            </script>
            <script>
                window.onload = function () {
                    var successMessage = "<%= session.getAttribute("successMessage") %>";
                    var notification = document.getElementById("notification");

                    if (successMessage !== "null") {
                        notification.innerHTML = successMessage;
                        notification.style.display = "block";
                        notification.style.backgroundColor = "#4CAF50"; // Màu xanh cho thành công
                        setTimeout(function () {
                            notification.style.display = "none";
                        }, 3000);
                <% session.removeAttribute("successMessage"); %>
                    }
                };
            </script>
            <%
    String successMessage = (String) session.getAttribute("successMessage");
    String errorMessage = (String) session.getAttribute("errorMessage");
            %>

            <script>
                document.addEventListener("DOMContentLoaded", function () {
                <% if (successMessage != null) { %>
                    showToast("<%= successMessage %>", "success");
                <% session.removeAttribute("successMessage"); } %>

                <% if (errorMessage != null) { %>
                    showToast("<%= errorMessage %>", "error");
                <% session.removeAttribute("errorMessage"); } %>
                });

                function showToast(message, type) {
                    let toast = document.createElement("div");
                    toast.textContent = message;
                    toast.style.position = "fixed";
                    toast.style.bottom = "20px";
                    toast.style.right = "20px";
                    toast.style.backgroundColor = (type === "success") ? "green" : "red";
                    toast.style.color = "white";
                    toast.style.padding = "10px 20px";
                    toast.style.borderRadius = "5px";
                    toast.style.zIndex = "1000";
                    document.body.appendChild(toast);

                    setTimeout(() => {
                        toast.style.opacity = "0";
                        setTimeout(() => toast.remove(), 500);
                    }, 3000);
                }
            </script>

            
        </body>
    </html>

