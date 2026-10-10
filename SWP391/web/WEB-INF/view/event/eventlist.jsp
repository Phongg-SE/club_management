

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.entity.Club" %>
<%@ page import="model.entity.Event" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Event List</title>
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
                <div class="container-fluid">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">
                                Event Management
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
                                    <a href="#">Event Management</a>
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
                                                Event List
                                            </h4>
                                            <!-- Nút mở modal -->
                                            <c:if test="${isLeader}">
                                                <a href="#" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addEventModal">
                                                    <i class="fas fa-plus"></i> Add Event
                                                </a>
                                            </c:if>


                                        </div>
                                    </div>

                                    <!-- Modal -->
                                    <div class="modal fade" id="addEventModal" tabindex="-1" aria-labelledby="addEventModalLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-xl">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="addEventModalLabel">Add New Event</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <form action="${pageContext.request.contextPath}/AddEvent" method="post" enctype="multipart/form-data">

                                                        <!-- Event Name -->
                                                        <div class="mb-3">
                                                            <label for="eventName" class="form-label">Event Name</label>
                                                            <input type="text" class="form-control" id="eventName" name="name" required>
                                                        </div>

                                                        <!-- Club & Leader -->
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="form-group mb-3">
                                                                    <label for="leaderID" class="form-label">Event Leader</label>
                                                                    <select class="form-control" id="leaderID" name="leader_id">
                                                                        <option value="">-- Event Leader --</option>
                                                                        <c:forEach var="user" items="${users}">
                                                                            <option value="${user.user_id}">${user.full_name}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Event Image -->
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <div class="form-group mb-3">
                                                                    <label for="eventImage" class="form-label">Upload New Image</label>
                                                                    <input type="file" class="form-control" id="eventImage" name="eventImage" accept="image/*">
                                                                    <img id="previewImage" src="#" alt="Preview Image" style="display: none; max-width: 200px; margin-top: 10px;">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Image Preview Script -->
                                                        <script>
                                                            document.getElementById('eventImage').addEventListener('change', function (event) {
                                                                var file = event.target.files[0]; // Lấy file được chọn
                                                                var preview = document.getElementById('previewImage');
                                                                if (file) {
                                                                    var reader = new FileReader();
                                                                    reader.onload = function (e) {
                                                                        preview.src = e.target.result; // Hiển thị ảnh mới
                                                                        preview.style.display = "block"; // Hiện ảnh xem trước
                                                                    };
                                                                    reader.readAsDataURL(file);
                                                                } else {
                                                                    preview.style.display = "none"; // Ẩn nếu không có file
                                                                }
                                                            });
                                                        </script>

                                                        <!-- Guest & Location -->
                                                        <div class="row">
                                                            <div class="col-md-6 mb-3">
                                                                <label for="guest" class="form-label">Guest</label>
                                                                <input type="text" class="form-control" id="guest" name="guest">
                                                            </div>
                                                            <div class="col-md-6 mb-3">
                                                                <label for="location" class="form-label">Location</label>
                                                                <input type="text" class="form-control" id="location" name="location" required>
                                                            </div>
                                                        </div>

                                                        <!-- Start Time & End Time -->
                                                        <div class="row">
                                                            <div class="col-md-6 mb-3">
                                                                <label for="startTime" class="form-label">Start Date</label>
                                                                <input type="datetime-local" class="form-control" id="startTime" name="start_time" required>
                                                            </div>
                                                            <div class="col-md-6 mb-3">
                                                                <label for="endTime" class="form-label">End Date</label>
                                                                <input type="datetime-local" class="form-control" id="endTime" name="end_time" required>
                                                            </div>
                                                        </div>

                                                        <!-- Status & Is Public -->
                                                        <div class="row">
                                                            <div class="col-md-6 mb-3">
                                                                <label class="form-label">Status</label>
                                                                <div>
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="form-check-input" type="radio" name="status" id="statusPlanned" value="Planned" required>
                                                                        <label class="form-check-label" for="statusPlanned">Planned</label>
                                                                    </div>
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="form-check-input" type="radio" name="status" id="statusOngoing" value="Ongoing">
                                                                        <label class="form-check-label" for="statusOngoing">Ongoing</label>
                                                                    </div>
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="form-check-input" type="radio" name="status" id="statusCompleted" value="Completed">
                                                                        <label class="form-check-label" for="statusCompleted">Completed</label>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="col-md-6 mb-3">
                                                                <label class="form-label">Is Public</label>
                                                                <div>
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="form-check-input" type="radio" name="is_public" id="isPublicYes" value="Yes" required>
                                                                        <label class="form-check-label" for="isPublicYes">Public</label>
                                                                    </div>
                                                                    <div class="form-check form-check-inline">
                                                                        <input class="form-check-input" type="radio" name="is_public" id="isPublicNo" value="No" required>
                                                                        <label class="form-check-label" for="isPublicNo">Private</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <!-- Description -->
                                                        <div class="mb-3">
                                                            <label for="description" class="form-label">Description</label>
                                                            <textarea class="form-control" id="description" name="description" rows="4"></textarea>
                                                        </div>

                                                        <!-- Buttons -->
                                                        <div class="text-end">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                            <button type="submit" class="btn btn-primary">Add Event</button>
                                                        </div>

                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="card-body">
                                        <c:if test="${not empty sessionScope.errorMessage}">
                                            <div class="alert alert-danger" id="errorAlert">
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


                                        <script>
                                            // Ẩn thông báo sau 3 giây
                                            setTimeout(function () {
                                                let errorAlert = document.getElementById("errorAlert");
                                                if (errorAlert) {
                                                    errorAlert.style.display = "none";
                                                }
                                            }, 3000);
                                        </script>

                                        <div class="card-body">
                                            <!-- Bộ lọc Club và Status -->
                                            <form method="get" action="EventList" class="row g-3 align-items-center">
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

                                                <!-- Bộ lọc isPublic -->
                                                <div class="col-md-2">
                                                    <label for="isPublicFilter" class="form-label">Public</label>
                                                    <select id="isPublicFilter" class="form-select" name="isPublic" onchange="this.form.submit()">
                                                        <option value="">All</option>
                                                        <option value="Yes" <%= "Yes".equals(request.getAttribute("isPublic")) ? "selected" : "" %>>Public</option>
                                                        <option value="No" <%= "No".equals(request.getAttribute("isPublic")) ? "selected" : "" %>>Private</option>
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
                                        <div class="table-responsive">
                                            <table id="eventTable" class="display table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Name</th>
                                                        <th>Event Leader</th>
                                                        <th>Start Date</th>
                                                        <th>End Date</th>
                                                        <th>Location</th>
                                                        <th>isPublic</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="eventBody">
                                                    <c:forEach items="${events}" var="e">
                                                        <tr>
                                                            <td>${e.event_id}</td>
                                                            <td>${e.name}</td>
                                                            <td>${e.leader_name}</td>
                                                            <td>${e.start_time}</td>
                                                            <td>${e.end_time}</td>
                                                            <td>${e.location}</td>
                                                            <td>
                                                                <span class="badge badge-pill
                                                                      ${e.is_public eq 'No' ? 'badge-danger' : 
                                                                        (e.is_public eq 'Yes' ? 'badge-success' : 'badge-danger')}">
                                                                          ${e.is_public}
                                                                      </span>
                                                                </td>
                                                                <td>${e.status}</td>
                                                                <td>
                                                                    <a href="EventDetail?eventID=${e.event_id}" class="btn btn-info btn-sm mb-1">
                                                                        <i class="fas fa-edit"></i> Detail
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>

                                                <!-- PHÂN TRANG -->
                                                <div class="d-flex justify-content-center mt-3">
                                                    <ul class="pagination">
                                                        <%
                                                            int currentPage = (Integer) request.getAttribute("currentPage");
                                                            int totalPages = (Integer) request.getAttribute("totalPages");

                                                            // Xử lý giá trị null để tránh "null" xuất hiện trên URL
                                                            String clubId = request.getParameter("clubId");
                                                            String status = request.getParameter("status");
                                                            String isPublic = request.getParameter("isPublic");
                                                            String searchName = request.getParameter("searchName");

                                                            clubId = (clubId == null || "null".equalsIgnoreCase(clubId.trim())) ? "" : clubId;
                                                            status = (status == null || "null".equalsIgnoreCase(status.trim())) ? "" : status;
                                                            isPublic = (isPublic == null || "null".equalsIgnoreCase(isPublic.trim())) ? "" : isPublic;
                                                            searchName = (searchName == null || "null".equalsIgnoreCase(searchName.trim())) ? "" : searchName;

                                                            String queryString = "clubId=" + clubId + "&status=" + status + "&isPublic=" + isPublic + "&searchName=" + searchName;
                                                        %>

                                                        <!-- Nút Previous -->
                                                        <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                                                            <a class="page-link" href="EventList?page=<%= currentPage - 1 %>&<%= queryString %>">Previous</a>
                                                        </li>

                                                        <!-- Số trang -->
                                                        <% for (int i = 1; i <= totalPages; i++) { %>
                                                        <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                                            <a class="page-link" href="EventList?page=<%= i %>&<%= queryString %>"><%= i %></a>
                                                        </li>
                                                        <% } %>

                                                        <!-- Nút Next -->
                                                        <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                                                            <a class="page-link" href="EventList?page=<%= currentPage + 1 %>&<%= queryString %>">Next</a>
                                                        </li>
                                                    </ul>
                                                </div>
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

