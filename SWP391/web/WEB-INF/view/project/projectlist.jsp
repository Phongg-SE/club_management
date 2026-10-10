

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.entity.Club" %>
<%@ page import="model.entity.Event" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Project List</title>
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
                                Project Management
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
                                    <a href="ProjectList">Project Management</a>
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
                                                Project List
                                            </h4>

                                            <a href="${isLeader ? 'AddProject' : '#'}" 
                                               class="btn btn-primary btn-sm ${isLeader ? '' : 'disabled'}">
                                                <i class="fas fa-plus"></i> Add Project
                                            </a>

                                        </div>
                                    </div>

                                    <div class="card-body">
                                        <c:if test="${not empty sessionScope.errorMessage}">
                                            <div class="alert alert-danger">
                                                ${sessionScope.errorMessage}
                                            </div>
                                            <c:remove var="errorMessage" scope="session"/>
                                        </c:if>
                                        <%
    String successMessage = (String) session.getAttribute("successMessage");
    String errorMessage = (String) session.getAttribute("errorMessage");
                                        %>

                                        <!-- Thông báo sẽ hiển thị ở góc trên bên phải -->
                                        <div id="notification" style="
                                             display: none;
                                             position: fixed;
                                             top: 10px;
                                             right: 10px;
                                             padding: 10px 20px;
                                             color: white;
                                             font-size: 16px;
                                             border-radius: 5px;
                                             z-index: 1000;
                                             box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);">
                                        </div>

                                        <div class="card-body">
                                            <div class="col-md-12">
                                                <!-- Bộ lọc Club và Status -->
                                                <form method="get" action="ProjectList" class="row g-3 align-items-center">
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
                                                            <option value="<%= club.getClub_id() %>" <%= (selectedClub.getClub_id() == club.getClub_id()) ? "selected" : "" %>>
                                                                <%= club.getName() %>
                                                            </option>
                                                            <% } %>
                                                            <% } else { %>
                                                            <option value="">Không có câu lạc bộ nào</option>
                                                            <% } %>
                                                        </select>
                                                    </div>

                                                    <!-- Bộ lọc Status -->
                                                    <div class="col-md-2">
                                                        <label for="statusFilter" class="form-label">Status</label>
                                                        <select id="statusFilter" class="form-select" name="status" onchange="this.form.submit()">
                                                            <option value="">All Status</option>
                                                            <option value="Planned" <%= "Planned".equals(request.getAttribute("status")) ? "selected" : "" %>>Planned</option>
                                                            <option value="Ongoing" <%= "Ongoing".equals(request.getAttribute("status")) ? "selected" : "" %>>Ongoing</option>
                                                            <option value="Completed" <%= "Completed".equals(request.getAttribute("status")) ? "selected" : "" %>>Completed</option>
                                                        </select>
                                                    </div>

                                                    <!-- Ô tìm kiếm -->
                                                    <div class="col-md-5 ms-auto">
                                                        <label for="searchName" class="form-label">Search</label>
                                                        <div class="input-group">
                                                            <input type="text" id="searchName" class="form-control" name="searchName" placeholder="Search by Name" 
                                                                   value="<%= request.getAttribute("searchName") != null ? request.getAttribute("searchName") : "" %>">
                                                            <button type="submit" class="btn btn-primary px-4 py-2 fs-5">
                                                                Search
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>

                                            <!-- Bảng hiển thị dữ liệu -->
                                            <div class="table-responsive">
                                                <table id="eventTable" class="display table table-striped table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>ID</th>
                                                            <th>Name</th>
                                                            <th>Project Leader</th>
                                                            <th>Start Date</th>
                                                            <th>End Date</th>
                                                            <th>Total Members</th>
                                                            <th>Status</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="eventBody">
                                                        <c:forEach items="${projects}" var="pro">
                                                            <tr>
                                                                <td>${pro.event_id}</td>
                                                                <td>${pro.name}</td>
                                                                <td>${pro.leader_name}</td>
                                                                <td>${pro.start_time}</td>
                                                                <td>${pro.end_time}</td>
                                                                <td>${pro.total_member}</td>
                                                                <td>${pro.status}</td>
                                                                <td>
                                                                    <a href="ProjectDetail?event_id=${pro.event_id}&club_id=${pro.club_id}" class="btn btn-info btn-sm mb-1">
                                                                        <i class="fas fa-edit"></i> Detail
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>

                                            <!-- PHÂN TRANG -->
                                            <div class="d-flex justify-content-center mt-3">
                                                <ul class="pagination">
                                                    <%
                                                        int currentPage = (Integer) request.getAttribute("currentPage");
                                                        int totalPages = (Integer) request.getAttribute("totalPages");

                                                        // Xử lý giá trị null để tránh "null" xuất hiện trên URL
                                                        String clubId = request.getParameter("clubId");
                                                        String status = request.getParameter("status");
                                                        String searchName = request.getParameter("searchName");

                                                        clubId = (clubId == null || "null".equalsIgnoreCase(clubId.trim())) ? "" : clubId;
                                                        status = (status == null || "null".equalsIgnoreCase(status.trim())) ? "" : status;
                                                        searchName = (searchName == null || "null".equalsIgnoreCase(searchName.trim())) ? "" : searchName;

                                                        String queryString = "clubId=" + clubId + "&status=" + status + "&searchName=" + searchName;
                                                    %>

                                                    <!-- Nút Previous -->
                                                    <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                                                        <a class="page-link" href="ProjectList?page=<%= currentPage - 1 %>&<%= queryString %>">Previous</a>
                                                    </li>

                                                    <!-- Số trang -->
                                                    <% for (int i = 1; i <= totalPages; i++) { %>
                                                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                                        <a class="page-link" href="ProjectList?page=<%= i %>&<%= queryString %>"><%= i %></a>
                                                    </li>
                                                    <% } %>

                                                    <!-- Nút Next -->
                                                    <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                                                        <a class="page-link" href="ProjectList?page=<%= currentPage + 1 %>&<%= queryString %>">Next</a>
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


        <style>
            #pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 10px;
                margin-top: 30px;
            }

            #pagination button {
                background: linear-gradient(135deg, #f1f1f1, #e0e0e0);
                color: #333;
                border: none;
                padding: 10px 16px;
                font-size: 16px;
                border-radius: 12px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease-in-out;
                box-shadow: 2px 4px 8px rgba(0, 0, 0, 0.15);
            }

            #pagination button:hover {
                background: linear-gradient(135deg, #007bff, #0056b3);
                color: white;
                transform: translateY(-2px);
                box-shadow: 4px 6px 12px rgba(0, 0, 0, 0.2);
            }

            #pagination button.active {
                background: linear-gradient(135deg, #ff9800, #ff7300);
                color: white;
                box-shadow: 0 5px 10px rgba(255, 152, 0, 0.4);
                transform: scale(1.1);
            }

            #pagination button:disabled {
                background: #ddd;
                color: #aaa;
                cursor: not-allowed;
                box-shadow: none;
            }

            /* Hover cho nút chuyển trang */
            #pagination button.prev-next {
                background: linear-gradient(135deg, #4CAF50, #388E3C);
                color: white;
            }

            #pagination button.prev-next:hover {
                background: linear-gradient(135deg, #45a049, #2E7D32);
            }

            /* Responsive */
            @media (max-width: 600px) {
                #pagination {
                    flex-wrap: wrap;
                    gap: 6px;
                }

                #pagination button {
                    font-size: 14px;
                    padding: 8px 12px;
                    border-radius: 10px;
                }
            }


        </style>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let message = "";
                let type = "";

            <% if (successMessage != null) { %>
                message = "<%= successMessage %>";
                type = "success";
            <% session.removeAttribute("successMessage"); %>
            <% } %>

            <% if (errorMessage != null) { %>
                message = "<%= errorMessage %>";
                type = "error";
            <% session.removeAttribute("errorMessage"); %>
            <% } %>

                if (message) {
                    showNotification(message, type);
                }
            });

            function showNotification(message, type) {
                let notification = document.getElementById("notification");
                notification.textContent = message;
                notification.style.backgroundColor = (type === "success") ? "#4CAF50" : "#D9534F";
                notification.style.display = "block";

                setTimeout(() => {
                    notification.style.opacity = "0";
                    setTimeout(() => {
                        notification.style.display = "none";
                        notification.style.opacity = "1";
                    }, 500);
                }, 3000);
            }
        </script>


    </body>
</html>

