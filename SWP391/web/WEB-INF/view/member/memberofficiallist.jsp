<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <!--header-->
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Member Manager</title>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flatpickr.min.css">
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

                <div class="main-panel">
                    <div class="top-header">
                        <div class="header-bar d-flex justify-content-between border-bottom">
                            <!--search-->
                            <div class="d-flex align-items-center">

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
                                                <small class="text-muted">${userRole}</small>
                                            </div>
                                        </a>
                                        <!--                                    <a class="dropdown-item text-dark" href="index.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span> Dashboard</a>-->
                                        <a class="dropdown-item text-dark" href="userprofile"><span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings</a>
                                        <div class="dropdown-divider border-top"></div>
                                        <a class="dropdown-item text-dark" href="logout"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--right side-->
                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="row">
                            <div class="d-flex flex-column gap-3 py-3">
                                <!-- Breadcrumb + Button -->
                                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                                    <!-- Breadcrumb -->
                                    <nav aria-label="breadcrumb" class="mb-0">
                                        <ol class="breadcrumb bg-transparent p-0 mb-0">
                                            <li class="breadcrumb-item">
                                                <a href="home" class="text-dark">
                                                    <i class="icon-home"></i>
                                                </a>
                                            </li>
                                            <li class="breadcrumb-item">
                                                <a href="clublist" class="text-dark">Club</a>
                                            </li>
                                            <li class="breadcrumb-item active text-primary" aria-current="page">
                                                Member
                                            </li>
                                        </ol>
                                    </nav>

                                </div>
                            </div>
                            <!--end header menu-->
                        </div>
                        <div class="row container-fluid">
                            <!-- Nội dung Tabs -->
                            <div class="tab-content">
                                <div class="mb-3">
                                    <div class="d-flex justify-content-between align-items-center flex-wrap">

                                        <form method="get" action="memberofficiallist" class="d-flex align-items-center">
                                            <select id="clubFilter" 
                                                    class="form-select bg-primary text-white px-3 py-2"
                                                    style="width: auto; border-radius: 0.5rem; padding-right: 2rem;"
                                                    name="clubId" onchange="this.form.submit()" aria-label="Select Club">
                                                <c:forEach var="club" items="${userClubs}">
                                                    <option value="${club.club_id}" 
                                                            ${club.club_id == selectedClubId ? 'selected' : ''}>
                                                        ${club.name}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <a href="ExportMemberCSV?club_id=${selectedClubId}" class="btn btn-success btn-sm ms-2" title="Xuất danh sách thành viên ra CSV">
                                                📥 Xuất CSV
                                            </a>
                                        </form>

                                        <ul class="nav nav-tabs" id="memberTabs">
                                            <li class="nav-item">
                                                <a class="nav-link active" id="officialMembersTab" data-bs-toggle="tab" href="#officialMembers">Members</a>
                                            </li>
                                            <c:if  test="${userRole eq 'Club Leader'}" >
                                                <li class="nav-item">
                                                    <a class="nav-link" id="registeredMembersTab" data-bs-toggle="tab" href="#registeredMembers">Registered Members</a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </div>
                                <!-- Tab: Official Members -->
                                <div class="tab-pane fade show active" id="officialMembers">
                                    <!-- Bộ lọc -->
                                    <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                                        <!-- Bộ lọc bên trái -->
                                        <div class="d-flex align-items-center gap-2 flex-wrap">
                                            <form method="get" action="memberofficiallist" class="d-flex align-items-center gap-2">
                                                <!-- Hidden field to send the selected clubId -->
                                                <input type="hidden" name="clubId" value="${selectedClubId}">

                                                <label for="roleFilter" class="fw-bold">Role:</label>
                                                <select id="roleFilter" class="form-select form-select-sm" name="roleFilter" onchange="this.form.submit()">
                                                    <option value="">All Roles</option>
                                                    <option value="3" ${selectedRole == '3' ? 'selected' : ''}>Member</option>
                                                    <option value="2" ${selectedRole == '2' ? 'selected' : ''}>Event Leader</option>
                                                    <option value="5" ${selectedRole == '5' ? 'selected' : ''}>Project Leader</option>
                                                    <option value="4" ${selectedRole == '4' ? 'selected' : ''}>Club Leader</option>
                                                </select>

                                                <label for="statusFilter" class="fw-bold">Status:</label>
                                                <select id="statusFilter" class="form-select form-select-sm" name="statusFilter" onchange="this.form.submit()">
                                                    <option value="">All Status</option>
                                                    <option value="Active" ${selectedStatus == 'Active' ? 'selected' : ''}>Active</option>
                                                    <option value="Inactive" ${selectedStatus == 'Inactive' ? 'selected' : ''}>Inactive</option>
                                                </select>

                                                <input type="hidden" name="page" value="${currentPage}" />
                                                <input type="hidden" name="sortField" value="${selectedSortField}" />
                                                <input type="hidden" name="sortOrder" value="${selectedSortOrder}" />
                                            </form>
                                        </div>

                                        <!-- Thanh tìm kiếm bên phải -->
                                        <div class="d-flex align-items-center">
                                            <form role="search" method="get" action="searchMembers" class="d-flex align-items-center gap-2">
                                                <input type="hidden" name="page" value="${currentPage}">
                                                <input type="hidden" name="club_id" value="${club.club_id}">
                                                <input type="text" class="form-control form-control-sm border rounded-pill" 
                                                       name="search" id="searchInput" 
                                                       value="${param.search}"
                                                       placeholder="Search by name..." autocomplete="off">
                                                <button type="submit" class="btn btn-sm btn-primary">Search</button>
                                            </form>
                                        </div>
                                    </div>
                                    <!--card member-->
                                    <table id="" class="display table">
                                        <thead>
                                            <tr>
                                                <th>Avatar</th>
                                                <th>Name
                                                    <a href="?clubId=${selectedClubId}&roleFilter=${not empty selectedRole ? selectedRole : ''}&statusFilter=${not empty selectedStatus ? selectedStatus : ''}&page=${currentPage}&sortField=name&sortOrder=${(param.sortField == 'name' && param.sortOrder == 'asc') ? 'desc' : 'asc'}" 
                                                       class="sort">
                                                        <i class="fas fa-sort${param.sortField == 'name' ? (param.sortOrder == 'asc' ? '-up' : '-down') : ''}"></i>
                                                    </a>
                                                </th>
                                                <th>Email</th>
                                                <th>Gender</th>
                                                <th>Phone Number</th>
                                                <th>Role</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="member" items="${members}">
                                                <tr>
                                                    <td><img src="${member.avatar_url}" class="img-fluid" alt="ảnh" style="width: 50px; height: 50px; border-radius: 50%;"></td>
                                                    <td>
                                                        <a href="memberofficialdetail?id=${member.user_id}&club_id=${selectedClubId}" class="text-primary fw-bold text-decoration-none">
                                                            <i class="bi bi-person-circle"></i> ${member.full_name}
                                                        </a>
                                                    </td>
                                                    <td>${member.email}</td>
                                                    <td>${member.gender}</td>
                                                    <td>${member.phone_number}</td>
                                                    <td>
                                                        <c:forEach var="role" items="${member.role_names}">
                                                            <span>${role}</span><br>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${userRole eq 'Club Leader'}">
                                                                <c:if test="${member.value ne 'Club Leader' and member.user_id ne sessionScope.account.user_id}">
                                                                    <a href="#" 
                                                                       class="btn btn-sm ${member.status eq 'Active' ? 'btn-success' : 'btn-danger'}"
                                                                       data-bs-toggle="modal"
                                                                       data-bs-target="#confirmModal${member.user_id}"
                                                                       title="${member.status eq 'Active' ? 'Deactivate' : 'Activate'}">
                                                                        <i class="fas ${member.status eq 'Active' ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                                                    </a>
                                                                </c:if>
                                                                <c:if test="${member.value eq 'Club Leader' or member.user_id eq sessionScope.account.user_id}">
                                                                    <span class="btn btn-sm ${member.status eq 'Active' ? 'btn-success' : 'btn-danger'}" style="cursor: default;">
                                                                        <i class="fas ${member.status eq 'Active' ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                                                    </span>
                                                                </c:if>
                                                            </c:when>

                                                            <c:otherwise>
                                                                <span class="btn btn-sm ${member.status eq 'Active' ? 'btn-success' : 'btn-danger'}" style="cursor: default;">
                                                                    <i class="fas ${member.status eq 'Active' ? 'fa-check-circle' : 'fa-times-circle'}"></i>
                                                                </span>
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </td>

                                                    <!-- Modal Xác nhận riêng cho từng thành viên -->
                                            <div class="modal fade" id="confirmModal${member.user_id}" tabindex="-1" aria-labelledby="confirmModalLabel${member.user_id}" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="confirmModalLabel${member.user_id}">Xác nhận thay đổi trạng thái</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            ${member.status eq 'Active' ? 'Bạn có chắc muốn hủy kích hoạt thành viên?': 'Bạn có chắc muốn kích hoạt thành viên này?'}
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                            <a href="changestatus?id=${member.user_id}&club_id=${club.club_id}&status=${member.status}"
                                                               class="btn ${member.status eq 'Active' ? 'btn-danger' : 'btn-success'}">
                                                                ${member.status eq 'Active' ? 'Hủy kích hoạt' : 'Kích hoạt'}
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <!--paging-->
                                    <div class="pagination-wrapper">
                                        <c:if test="${totalPages > 1}">
                                            <ul class="pagination justify-content-center">
                                                <!-- Previous Button -->
                                                <c:if test="${currentPage > 1}">
                                                    <li class="page-item">
                                                        <a href="<c:choose>
                                                               <c:when test='${not empty param.search}'>
                                                                   ?page=${currentPage - 1}&club_id=${club.club_id}&search=${param.search}
                                                               </c:when>
                                                               <c:otherwise>
                                                                   ?page=${currentPage - 1}&clubId=${club.club_id}&roleFilter=${not empty selectedRole ? selectedRole : ''}&statusFilter=${not empty selectedStatus ? selectedStatus : ''}&sortField=${not empty sortField ? sortField : ''}&sortOrder=${not empty sortOrder ? sortOrder : ''}
                                                               </c:otherwise>
                                                           </c:choose>"
                                                           class="page-link" aria-label="Previous">
                                                            <span aria-hidden="true">&laquo;</span>
                                                        </a>
                                                    </li>
                                                </c:if>
                                                <!-- Các số trang -->
                                                <c:forEach var="i" begin="1" end="${totalPages}" step="1">
                                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                        <a href="<c:choose>
                                                               <c:when test='${not empty param.search}'>
                                                                   ?page=${i}&club_id=${club.club_id}&search=${param.search}
                                                               </c:when>
                                                               <c:otherwise>
                                                                   ?page=${i}&clubId=${club.club_id}&roleFilter=${not empty selectedRole ? selectedRole : ''}&statusFilter=${not empty selectedStatus ? selectedStatus : ''}&sortField=${not empty sortField ? sortField : ''}&sortOrder=${not empty sortOrder ? sortOrder : ''}
                                                               </c:otherwise>
                                                           </c:choose>"
                                                           class="page-link">${i}</a>
                                                    </li>
                                                </c:forEach>
                                                <!-- Next Button -->
                                                <c:if test="${currentPage < totalPages}">
                                                    <li class="page-item">
                                                        <a href="<c:choose>
                                                               <c:when test='${not empty param.search}'>
                                                                   ?page=${currentPage + 1}&club_id=${club.club_id}&search=${param.search}
                                                               </c:when>
                                                               <c:otherwise>
                                                                   ?page=${currentPage + 1}&clubId=${club.club_id}&roleFilter=${not empty selectedRole ? selectedRole : ''}&statusFilter=${not empty selectedStatus ? selectedStatus : ''}&sortField=${not empty sortField ? sortField : ''}&sortOrder=${not empty sortOrder ? sortOrder : ''}
                                                               </c:otherwise>
                                                           </c:choose>"
                                                           class="page-link" aria-label="Next">
                                                            <span aria-hidden="true">&raquo;</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                            </ul>
                                        </c:if>
                                    </div>
                                    <!--end card member-->
                                </div>

                                <!-- Tab: Registered Members -->
                                <div class="tab-pane fade" id="registeredMembers">
                                    <div id="registeredMembersContent">
                                        <div class="table-responsive">
                                            <table id="" class="display table">
                                                <thead>
                                                    <tr>
                                                        <th>Avatar</th>
                                                        <th>Full Name</th>
                                                        <th>Email</th>
                                                        <th>Gender</th>
                                                        <th>Phone Number</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="clubResults">
                                                    <c:forEach var="member" items="${membersre}">
                                                        <tr>
                                                            <td><img src="${member.avatar_url}" alt="${member.avatar_url} Logo" width="50"></td>
                                                            <td>${member.full_name}</td>
                                                            <td>${member.email}</td>
                                                            <td>${member.gender}</td>
                                                            <td>${member.phone_number}</td>
                                                            <!-- Các nút hành động -->
                                                            <td>
                                                                <a href="#" class="btn btn-sm btn-success mb-1"
                                                                   data-bs-toggle="modal"
                                                                   data-bs-target="#confirmModal"
                                                                   data-action="processregisted?clubId=${club.club_id}&userId=${member.user_id}&action=approve"
                                                                   data-message="Bạn có chắc muốn chấp nhận thành viên này không?"
                                                                   title="Approve">
                                                                    <i class="fas fa-check"></i>
                                                                </a>

                                                                <a href="#" class="btn btn-sm btn-danger mb-1"
                                                                   data-bs-toggle="modal"
                                                                   data-bs-target="#confirmModal"
                                                                   data-action="processregisted?clubId=${club.club_id}&userId=${member.user_id}&action=reject"
                                                                   data-message="Bạn có chắc muốn từ chối thành viên này không?"
                                                                   title="Reject">
                                                                    <i class="fas fa-times"></i>
                                                                </a>

                                                            </td>

                                                            <!-- Modal xác nhận -->
                                                    <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title" id="confirmModalLabel">Xác nhận hành động</h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body" id="confirmMessage">
                                                                    Bạn có chắc chắn muốn thực hiện hành động này không?
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                    <a href="#" id="confirmAction" class="btn btn-primary">OK</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- Phân trang sử dụng Bootstrap -->
                                        <div class="pagination-wrapper">
                                            <c:if test="${totalPagesRegistration > 1}">
                                                <ul class="pagination justify-content-center">
                                                    <!-- Previous Button -->
                                                    <c:if test="${currentPageRegistration > 1}">
                                                        <li class="page-item">
                                                            <a href="?pageRegistration=${currentPageRegistration - 1}&clubId=${club.club_id}" class="page-link" aria-label="Previous">
                                                                <span aria-hidden="true">&laquo;</span>
                                                            </a>
                                                        </li>
                                                    </c:if>

                                                    <!-- Các số trang -->
                                                    <c:forEach var="i" begin="1" end="${totalPagesRegistration}" step="1">
                                                        <li class="page-item ${i == currentPageRegistration ? 'active' : ''}">
                                                            <a href="?pageRegistration=${i}&clubId=${club.club_id}" class="page-link">${i}</a>
                                                        </li>
                                                    </c:forEach>

                                                    <!-- Next Button -->
                                                    <c:if test="${currentPageRegistration < totalPagesRegistration}">
                                                        <li class="page-item">
                                                            <a href="?pageRegistration=${currentPageRegistration + 1}&clubId=${club.club_id}" class="page-link" aria-label="Next">
                                                                <span aria-hidden="true">&raquo;</span>
                                                            </a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--                <footer class="footer">
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
                                </footer>-->
            </div>
        </div>

        <!--   Core JS Files   -->
        <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Kaiadmin JS -->
        <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>

        <!--sort-->
        <script>
                                                    document.addEventListener("DOMContentLoaded", function () {
                                                        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                                                        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                                                            return new bootstrap.Tooltip(tooltipTriggerEl)
                                                        })
                                                    });
        </script>
        <style>
            #clubFilter {
                -webkit-appearance: none; /* Safari */
                -moz-appearance: none;    /* Firefox */
                appearance: none;         /* Standard */
                background-image: url('data:image/svg+xml;charset=UTF-8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="white" d="M7 10l5 5 5-5z"/></svg>');
                background-repeat: no-repeat;
                background-position: right 10px center;
            }
        </style>

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

        <!-- Script để cập nhật đường dẫn động -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let confirmModal = document.getElementById("confirmModal");
                let confirmAction = document.getElementById("confirmAction");
                let confirmMessage = document.getElementById("confirmMessage");

                confirmModal.addEventListener("show.bs.modal", function (event) {
                    let button = event.relatedTarget;
                    if (!button)
                        return;

                    let actionUrl = button.getAttribute("data-action");
                    let message = button.getAttribute("data-message") || "Bạn có chắc chắn muốn thực hiện hành động này không?";

                    confirmMessage.textContent = message;
                    confirmAction.setAttribute("href", actionUrl);
                });
            });
        </script>

        <!-- Client-side Instant Multi-criteria Filter -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const searchInput = document.getElementById("searchInput");
                if (searchInput) {
                    searchInput.addEventListener("input", function (e) {
                        const term = e.target.value.toLowerCase().trim();
                        const rows = document.querySelectorAll("table.display tbody tr");
                        rows.forEach(function (row) {
                            const text = row.innerText.toLowerCase();
                            row.style.display = text.includes(term) ? "" : "none";
                        });
                    });
                }
            });
        </script>
    </body>
</html>
