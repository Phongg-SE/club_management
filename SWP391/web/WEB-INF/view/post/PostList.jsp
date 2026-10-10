<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Task Manager</title>
        <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
        <link rel="icon" href="${pageContext.request.contextPath}/assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

        <!-- Fonts and icons -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/webfont/webfont.min.js"></script>
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
                    urls: ["${pageContext.request.contextPath}/assets/css/fonts.min.css"],
                },
                active: function () {
                    sessionStorage.fonts = true;
                },
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/toast.css" />


        <style>
            .toast .btn-close-white {
                filter: invert(1) grayscale(100%) brightness(200%);
            }
            .container-fluid, .container {
                padding-left: 0 !important;
                margin-left: 0 !important;
            }
            header {
                width: 100%;
                max-width: 1200px;
                margin: 0 auto;
                padding: 10px 0;
                position: fixed;
                background-color: white; /* Đảm bảo có màu nền nếu cần */
                z-index: 1000; /* Giá trị vừa đủ để không bị nội dung khác đè lên */
            }
            #toast-container {
                position: fixed;
                top: 20px; /* Điều chỉnh vị trí để không bị header che */
                right: 20px;
                z-index: 9999; /* Giá trị cao hơn header để toast luôn hiển thị trên cùng */
            }

            table {
                width: 100%;
                border-collapse: collapse; /* Đảm bảo đường viền gọn gàng */

            }
            .container {
                width: 100%;
                max-width: 100%;
                padding: 0;
            }


        </style>
        <!-- Additional CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/simplebar.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/select2.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flatpickr.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery.timepicker.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/remixicon.css" />
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v3.0.6/css/line.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.min.css" id="theme-opt" />
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
                        <li class="list-inline-item mb-0 ms-1">
                            <div class="dropdown dropdown-primary">
                                <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${account.avatar_url}" class="avatar avatar-ex-small rounded-circle" alt=""></button>
                                <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                                    <a class="dropdown-item d-flex align-items-center text-dark" href="https://shreethemes.in/doctris/layouts/admin/profile.html">
                                        <img src="${account.avatar_url}" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                        <div class="flex-1 ms-2">
                                            <span class="d-block mb-1">${account.full_name}</span>
    <!--                                        <small class="text-muted">${account.role_name}</small>-->
                                        </div>
                                    </a>
                                    <a class="dropdown-item text-dark" href="userprofile"><span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings</a>
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
                                    Post List
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
                                        <a href="PostList?user_Id=${sessionScope.account.getUser_id()}">Post List</a>
                                    </li>
                                </ul>
                            </div>
                            <!-- Card -->
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h4 class="card-title">
                                                    Post List
                                                </h4>
                                                ${requestScope.error}
                                                <a href="AddNewPost" 
                                                   class="btn btn-primary d-flex align-items-center justify-content-center shadow-lg custom-btn">
                                                    <i class="fas fa-edit text-white"> add new post</i>
                                                </a>

                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <div class="row mb-3">
                                                <!-- Filter -->
                                                <div class="col-md-6">
                                                    <form method="get" action="PostList" class="d-flex align-items-center gap-3">
                                                        <!-- Status Filter -->
                                                        <div class="col-md-4">
                                                            <label class="fw-bold">Status</label>
                                                            <select id="statusFilter" class="form-select" name="statusFilter" onchange="this.form.submit()">
                                                                <option value="" ${empty param.statusFilter ? 'selected' : ''}>All</option>
                                                                <option value="Published" ${param.statusFilter eq 'Published' ? 'selected' : ''}>Published</option>
                                                                <option value="Unpublished" ${param.statusFilter eq 'Unpublished' ? 'selected' : ''}>Unpublished</option>
                                                                <option value="Draft" ${param.statusFilter eq 'Draft' ? 'selected' : ''}>Draft</option>
                                                            </select>
                                                        </div>

                                                        <!-- Category Filter -->
                                                        <div class="col-md-4">
                                                            <label class="fw-bold">Category</label>
                                                            <select id="categoryFilter" class="form-select" name="categoryFilter" onchange="this.form.submit()">
                                                                <option value="">All</option>
                                                                <c:forEach var="category" items="${categories}">
                                                                    <option value="${category}" ${param.categoryFilter eq category ? 'selected' : ''}>${category}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </form>
                                                </div>

                                                <!-- Search by Club Name -->
                                                <div class="col-md-6">
                                                    <form action="PostList" method="get" class="d-flex justify-content-end">
                                                        <div class="input-group" style="max-width: 300px;">
                                                            <input id="searchInput" value="${param.clubNameSearch}" name="clubNameSearch" type="text" class="form-control" placeholder="Search by club name...">
                                                            <button type="submit" class="btn btn-primary">
                                                                <i class="fa fa-search"></i>
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>

                                            <!--start table-->
                                            <!-- Place this inside the card-body div, where the "start table" comment is -->
                                            <div class="container-fluid">
                                                 <div class="table-responsive">
                                                    <table class="table table-striped align-middle">
                                                        <thead class="table-light">
                                                            <tr>
                                                                <th>ID</th>
                                                                <th>Title</th>
                                                                <th>Club Name</th>
                                                                <th>Category</th>
                                                                <th>Author</th>
                                                                <th>Posted At</th>
                                                                <th>Status</th>
                                                                <th>Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="post" items="${posts}">
                                                                <tr>
                                                                    <td>${post.postId}</td>
                                                                    <td>
                                                                        <a href="PostDetail?post_id=${post.postId}" class="text-decoration-none fw-bold">
                                                                            ${post.title}
                                                                        </a>
                                                                    </td>
                                                                    <td>${post.clubName}</td>
                                                                    <td>${post.category}</td>
                                                                    <td>${post.author}</td>

                                                                    <td>
                                                                        <fmt:formatDate value="${post.postedAt}" pattern="dd-MM-yyyy HH:mm"/>
                                                                    </td>
                                                                    <td>${post.status}</td>
                                                                    <td>
                                                                        <div class="d-flex gap-2">
                                                                            <a href="PostDetail?post_id=${post.postId}" 
                                                                               class="btn btn-primary d-flex align-items-center justify-content-center shadow-lg custom-btn">
                                                                                <i class="fas fa-edit text-white"></i>
                                                                            </a>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>

                                                        <div class="position-fixed" style="top: 100px; right: 10px; z-index: 9999;">

                                                            <div id="toastSuccess" class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                                                                <div class="d-flex">
                                                                    <div class="toast-body">
                                                                        Post added successfully!
                                                                    </div>
                                                                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        </tbody>


                                                    </table>
                                                    <% 
    int currentPage = (request.getAttribute("currentPage") != null) ? (int) request.getAttribute("currentPage") : 1;
    int totalPages = (request.getAttribute("totalPages") != null) ? (int) request.getAttribute("totalPages") : 1;
                                                    %>

                                                    <nav aria-label="Page navigation">
                                                        <ul class="pagination justify-content-center">
                                                            <!-- Nút Previous -->
                                                            <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                                                                <a class="page-link" href="?page=<%= currentPage - 1 %>" aria-label="Previous">
                                                                    <span aria-hidden="true">&laquo;</span>
                                                                </a>
                                                            </li>

                                                            <!-- Các số trang -->
                                                            <% for (int i = 1; i <= totalPages; i++) { %>
                                                            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                                                <a class="page-link" href="?page=<%= i %>"><%= i %></a>
                                                            </li>
                                                            <% } %>

                                                            <!-- Nút Next -->
                                                            <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                                                                <a class="page-link" href="?page=<%= currentPage + 1 %>" aria-label="Next">
                                                                    <span aria-hidden="true">&raquo;</span>
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </nav>

                                                </div>
                                            </div>
                                        </div>
                                        <script>
                                            document.addEventListener("DOMContentLoaded", function () {
                                                const urlParams = new URLSearchParams(window.location.search);
                                                if (urlParams.get("toastSuccess") === "true") {
                                                    const toast = new bootstrap.Toast(document.getElementById("toastSuccess"));
                                                    toast.show();
                                                    setTimeout(() => {
                                                        window.location.href = "PostList"; // Điều hướng sau 2 giây
                                                    }, 2000);
                                                }
                                            });
                                        </script>

                                        <!--paging-->
                                        <div id="paginationControls" class="mt-3">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination justify-content-center">
                                                    <!-- Pagination will be populated by JavaScript -->
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>




            <!--footer-->
            <footer class="footer">
            </footer>
        </div>

        <!-- Required JavaScript Files -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/simplebar.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/select2.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/flatpickr.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.timepicker.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>

        <!-- Initialize Bootstrap components -->

    </body>
</html>