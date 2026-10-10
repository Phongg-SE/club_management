<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Club Manager</title>
        <meta
            content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
            name="viewport"
            />
        <link
            rel="icon"
            href="${pageContext.request.contextPath}/assets/img/kaiadmin/favicon.ico"
            type="image/x-icon"
            />

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
                    urls: ["assets/css/fonts.min.css"],
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
                                Club Management
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
                                    <a href="clublist">Club Management</a>
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
                                                Club List
                                            </h4>
                                            <a href="#" class="btn btn-primary btn-sm" onclick="$('#addClubModal').modal('show');">
                                                <i class="fas fa-plus"></i> Add New Club
                                            </a>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <!--filter-->
                                            <div class="col-md-6">
                                                <form method="get" action="clublist" class="d-flex align-items-center w-100 gap-2">
                                                    <input type="hidden" name="page" value="${currentPage}">
                                                    <input type="hidden" name="sortField" value="${sortField}">
                                                    <input type="hidden" name="sortOrder" value="${sortOrder}">

                                                    <!-- Bộ lọc trạng thái -->
                                                    <div class="col-md-4">
                                                        <select id="statusFilter" class="form-select" name="statusFilter" onchange="this.form.submit()">
                                                            <option value="">Select Status</option>
                                                            <option value="Active" ${statusFilter eq 'Active' ? 'selected' : ''}>Active</option>
                                                            <option value="Inactive" ${statusFilter eq 'Inactive' ? 'selected' : ''}>Inactive</option>
                                                        </select>
                                                    </div>

                                                    <!-- Bộ lọc Public -->
                                                    <div class="col-md-4">
                                                        <select id="isPublicFilter" class="form-select" name="isPublicFilter" onchange="this.form.submit()">
                                                            <option value="">Is Public</option>
                                                            <option value="Yes" ${isPublicFilter eq 'Yes' ? 'selected' : ''}>Public</option>
                                                            <option value="No" ${isPublicFilter eq 'No' ? 'selected' : ''}>Private</option>
                                                        </select>
                                                    </div>

                                                    <!-- Bộ lọc Category -->
                                                    <div class="col-md-4">
                                                        <select id="categoryFilter" class="form-select" name="categoryFilter" onchange="this.form.submit()">
                                                            <option value="">Category</option>
                                                            <c:forEach var="category" items="${categories}">
                                                                <option value="${category.settingId}" ${categoryFilter eq category.settingId ? 'selected' : ''}>
                                                                    ${category.value}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </form>

                                            </div>
                                            <div class="col-md-6">
                                                <form action="searchclub" method="get">
                                                    <!-- Thanh tìm kiếm -->
                                                    <div class="col-md-7 ms-auto">
                                                        <div class="input-group">
                                                            <input value="${param.txt}" name="txt" type="text" class="form-control" placeholder="Search...">
                                                            <input type="hidden" name="page" value="${param.page != null ? param.page : 1}">
                                                            <button type="submit" class="btn btn-secondary">
                                                                <i class="fa fa-search"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>

                                        </div>
                                        <!--start table-->
                                        <div class="table-responsive">
                                            <table id="" class="display table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID
                                                            <a href="?page=${param.page}&statusFilter=${param.statusFilter}&isPublicFilter=${param.isPublicFilter}&categoryFilter=${param.categoryFilter}&sortField=id&sortOrder=${param.sortField == 'id' && param.sortOrder == 'asc' ? 'desc' : 'asc'}" class="sort">
                                                                <i class="fas fa-sort${param.sortField == 'id' ? (param.sortOrder == 'asc' ? '-up' : '-down') : ''}"></i>
                                                            </a>
                                                        </th>
                                                        <th>Logo</th>
                                                        <th>Name
                                                            <a href="?page=${param.page}&statusFilter=${param.statusFilter}&isPublicFilter=${param.isPublicFilter}&categoryFilter=${param.categoryFilter}&sortField=name&sortOrder=${param.sortField == 'name' && param.sortOrder == 'asc' ? 'desc' : 'asc'}" class="sort">
                                                                <i class="fas fa-sort${param.sortField == 'name' ? (param.sortOrder == 'asc' ? '-up' : '-down') : ''}"></i>
                                                            </a>
                                                        </th>
                                                        <th>Category
                                                            <a href="?page=${param.page}&statusFilter=${param.statusFilter}&isPublicFilter=${param.isPublicFilter}&categoryFilter=${param.categoryFilter}&sortField=category&sortOrder=${param.sortField == 'category' && param.sortOrder == 'asc' ? 'desc' : 'asc'}" class="sort">
                                                                <i class="fas fa-sort${param.sortField == 'category' ? (param.sortOrder == 'asc' ? '-up' : '-down') : ''}"></i>
                                                            </a>
                                                        </th>

                                                        <th>Club Leader</th>
                                                        <th>Is Public</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="clubResults">
                                                    <c:forEach var="club" items="${clubs}">
                                                        <tr>
                                                            <td>${club.club_id}</td>
                                                            <td><img src="${club.club_logo_url}" alt="${club.name} Logo" width="50"></td>
                                                            <td>${club.name}</td>
                                                            <td>${club.category_name}</td>
                                                            <td>${club.club_leader}</td>

                                                            <td>
                                                                <a href="changevisibility?id=${club.club_id}" class="btn btn-sm ${club.is_public eq 'Yes' ? 'btn-success' : 'btn-danger'} toggle-visibility" 
                                                                   data-id="${club.club_id}" data-bs-toggle="tooltip" title="${club.is_public eq 'Yes' ? 'Make Private' : 'Make Public'}">
                                                                    <i class="fas ${club.is_public eq 'Yes' ? 'fa-globe' : 'fa-lock'}"></i>
                                                                </a>
                                                            </td>
                                                            <td>
                                                                <a href="changestatusclub?id=${club.club_id}" class="btn btn-sm ${club.status eq 'Active' ? 'btn-success' : 'btn-danger'} toggle-status"
                                                                   data-id="${club.club_id}" data-bs-toggle="tooltip" title="${club.status eq 'Active' ? 'Deactivate' : 'Activate'}">
                                                                    <i class="fas ${club.status eq 'Active' ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                                                </a>
                                                            </td>
                                                            <td>
                                                                <a href="updateclub?id=${club.club_id}" class="btn btn-info btn-sm mb-1" data-bs-toggle="tooltip" title="Update">
                                                                    <i class="fas fa-edit"></i>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>

                                        <div class="pagination-wrapper">
                                            <c:if test="${totalPages > 1}">
                                                <ul class="pagination justify-content-center">
                                                    <c:choose>
                                                        <c:when test="${not empty param.txt}">
                                                            <c:if test="${currentPage > 1}">
                                                                <li class="page-item">
                                                                    <a href="searchclub?txt=${param.txt}&page=${currentPage - 1}" class="page-link" aria-label="Previous">
                                                                        <span aria-hidden="true">&laquo;</span>
                                                                    </a>
                                                                </li>
                                                            </c:if>

                                                            <c:forEach var="i" begin="1" end="${totalPages}" step="1">
                                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                    <a href="searchclub?txt=${param.txt}&page=${i}" class="page-link">${i}</a>
                                                                </li>
                                                            </c:forEach>

                                                            <c:if test="${currentPage < totalPages}">
                                                                <li class="page-item">
                                                                    <a href="searchclub?txt=${param.txt}&page=${currentPage + 1}" class="page-link" aria-label="Next">
                                                                        <span aria-hidden="true">&raquo;</span>
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                        </c:when>

                                                        <c:otherwise>
                                                            <c:if test="${currentPage > 1}">
                                                                <li class="page-item">
                                                                    <a href="?page=${currentPage - 1}&statusFilter=${not empty statusFilter ? statusFilter : ''}&isPublicFilter=${not empty isPublicFilter ? isPublicFilter : ''}&categoryFilter=${not empty categoryFilter ? categoryFilter : ''}&sortField=${not empty sortField ? sortField : ''}&sortOrder=${not empty sortOrder ? sortOrder : ''}" 
                                                                       class="page-link" aria-label="Previous">
                                                                        <span aria-hidden="true">&laquo;</span>
                                                                    </a>
                                                                </li>
                                                            </c:if>

                                                            <c:forEach var="i" begin="1" end="${totalPages}" step="1">
                                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                    <a href="?page=${i}&statusFilter=${not empty statusFilter ? statusFilter : ''}&isPublicFilter=${not empty isPublicFilter ? isPublicFilter : ''}&categoryFilter=${not empty categoryFilter ? categoryFilter : ''}&sortField=${not empty sortField ? sortField : ''}&sortOrder=${not empty sortOrder ? sortOrder : ''}" 
                                                                       class="page-link">${i}</a>
                                                                </li>
                                                            </c:forEach>

                                                            <c:if test="${currentPage < totalPages}">
                                                                <li class="page-item">
                                                                    <a href="?page=${currentPage + 1}&statusFilter=${not empty statusFilter ? statusFilter : ''}&isPublicFilter=${not empty isPublicFilter ? isPublicFilter : ''}&categoryFilter=${not empty categoryFilter ? categoryFilter : ''}&sortField=${not empty sortField ? sortField : ''}&sortOrder=${not empty sortOrder ? sortOrder : ''}" 
                                                                       class="page-link" aria-label="Next">
                                                                        <span aria-hidden="true">&raquo;</span>
                                                                    </a>
                                                                </li>
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Modal for adding a new club -->
                <div class="modal fade" id="addClubModal" tabindex="-1" role="dialog" aria-labelledby="addClubModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addClubModalLabel">Add New Club</h5>
                                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form action="addclub" method="post" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="clubName">Club Name*</label>
                                        <input type="text" class="form-control" id="clubName" name="clubName"
                                               placeholder="Enter Club Name" value="${club != null ? club.getClubName() : ''}" required>
                                    </div>

                                    <div class="form-group">
                                        <label for="categoryName">Category*</label>
                                        <select class="form-select" id="categoryName" name="categoryId" required>
                                            <option value="">Select Category</option>
                                            <c:forEach var="category" items="${categories}">
                                                <option value="${category.setting_id}" ${categoryFilter eq category.setting_id ? 'selected' : ''}>
                                                    ${category.value}
                                                     <c:out value="${category.value}"/>

                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <!-- Select Club Leader -->
                                    <div class="form-group">
                                        <label for="clubLeader">Select Club Leader*</label>
                                        <select class="form-select" id="clubLeader" name="leaderId" required>
                                            <option value="">Select Leader</option>
                                            <c:forEach var="user" items="${user}">
                                                <option value="${user.user_id}">
                                                    ${user.full_name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="clubLogo">Upload Club Logo*</label>
                                        <input type="file" class="form-control" id="clubLogo" name="clubLogo" accept="image/*" required
                                               onchange="previewImage(event)">
                                        <img id="previewImage" src="#" alt="Preview Image"
                                             style="display: none; max-width: 200px; margin-top: 10px;">
                                    </div>

                                    <div class="form-group">
                                        <label for="isPublic">Is Public*</label><br>
                                        <input type="radio" id="publicYes" name="isPublic" value="Yes">
                                        <label for="publicYes" class="me-3">Public</label>
                                        <input type="radio" id="publicNo" name="isPublic" value="No">
                                        <label for="publicNo" class="me-3">Private</label>
                                    </div>

                                    <div class="form-group">
                                        <label for="status">Status*</label><br>
                                        <input type="radio" id="statusActive" name="status" value="Active">
                                        <label for="statusActive" class="me-3">Active</label>
                                        <input type="radio" id="statusInactive" name="status" value="Inactive">
                                        <label for="statusInactive">Inactive</label>
                                    </div>

                                    <div class="form-group text-end">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-primary">Add Club</button>
                                    </div>
                                </form>
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
        <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
        <!-- jQuery Scrollbar -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Kaiadmin JS -->
        <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>


        <!-- Toast Notification -->
        <!--        <div class="toastContainer toast-container position-fixed top-0 end-0 p-3">
                    <div id="toastMessage" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="d-flex">
                            <div class="toast-body">
                                 Nội dung thông báo sẽ thay đổi động 
                                <span id="toastText"></span>
                                <div class="progress mt-2" style="height: 3px;">
                                    <div id="toastProgress" class="progress-bar bg-light" role="progressbar" style="width: 100%; transition: width 3s linear;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>-->

        <!-- Hiển thị thông báo từ session -->
        <c:if test="${not empty sessionScope.toastMessage}">
            <script>
                                                   window.addEventListener("load", function () {
                                                       showToast("<c:out value='${sessionScope.toastMessage}' />", "success");
                                                   });
            </script>
            <% session.removeAttribute("toastMessage"); %>
        </c:if>

        <!-- Toast Notification Container -->
        <div id="toastContainer"></div>

        <style>
            #toastContainer {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 9999; /* Đảm bảo toast hiển thị trên cùng */
                display: flex;
                flex-direction: column;
                align-items: flex-end;
            }

            .toast {
                background: rgba(0, 0, 0, 0.8);
                color: white;
                padding: 8px 15px;  /* Giảm padding để toast nhỏ gọn hơn */
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
                animation: fadeInOut 3s ease-in-out; /* Tự động biến mất sau 3s */
                margin-bottom: 10px; /* Đảm bảo có khoảng cách giữa các toast */
                opacity: 0;
                transform: translateY(20px);
                max-width: 300px;  /* Giới hạn chiều rộng toast */
                word-wrap: break-word;  /* Đảm bảo chữ không bị tràn ra ngoài */
            }

            /* Animation cho toast */
            @keyframes fadeInOut {
                0% {
                    opacity: 0;
                    transform: translateY(20px);
                }
                10% {
                    opacity: 1;
                    transform: translateY(0);
                }
                90% {
                    opacity: 1;
                    transform: translateY(0);
                }
                100% {
                    opacity: 0;
                    transform: translateY(20px);
                }
            }

            .toast.show {
                opacity: 1;
                transform: translateY(0);
            }

        </style>

        <script>
            function showToast(message, type = "success") {
                console.log("showToast called with message:", message);

                const toastContainer = document.getElementById("toastContainer");

                const toast = document.createElement("div");
                toast.classList.add("toast");
                toast.style.backgroundColor = type === "error" ? "#e74c3c" : "#2ecc71";
                toast.innerText = message;

                toastContainer.appendChild(toast);

                // Thêm class "show" sau 100ms để kích hoạt animation
                setTimeout(() => {
                    toast.classList.add("show");
                }, 100);

                // Tự động ẩn toast sau 2 giây
                setTimeout(() => {
                    toast.classList.remove("show");
                    setTimeout(() => toast.remove(), 500); // Xóa khỏi DOM sau khi hoàn thành hiệu ứng
                }, 2000);  /* Giảm thời gian hiển thị xuống 2 giây */
            }
        </script>

        <!--preview image-->
        <script>
            document.getElementById('clubLogo').addEventListener('change', function (event) {
                var file = event.target.files[0]; // Lấy file được chọn
                var preview = document.getElementById('previewImage');

                if (file) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result; // Hiển thị ảnh
                        preview.style.display = "block"; // Hiện ảnh xem trước
                    };
                    reader.readAsDataURL(file);
                } else {
                    preview.style.display = "none"; // Ẩn nếu không có file
                }
            });
        </script>


        <!--sort-->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                    return new bootstrap.Tooltip(tooltipTriggerEl)
                })
            });
        </script>

        <script>
            $(document).ready(function () {
                let actionUrl = "";

                $(".toggle-visibility, .toggle-status").click(function (event) {
                    event.preventDefault();
                    actionUrl = $(this).attr("href"); // Lưu URL cần thực hiện

                    // Hiển thị modal xác nhận
                    $("#confirmModal").modal("show");
                });

                // Khi nhấn "Xác nhận", thực hiện thay đổi
                $("#confirmAction").click(function () {
                    window.location.href = actionUrl; // Điều hướng sau khi xác nhận
                });
            });

        </script>
        <!-- Modal Xác Nhận -->
        <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="confirmModalLabel">Xác Nhận</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Bạn có chắc chắn muốn thực hiện thay đổi này không?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="button" class="btn btn-primary" id="confirmAction">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            <% if (request.getAttribute("openModal") != null) { %>
            $(document).ready(function () {
                $('#addClubModal').modal('show'); // Hiển thị modal khi có lỗi
            });
            <% } %>
        </script>

    </body>
</html>
