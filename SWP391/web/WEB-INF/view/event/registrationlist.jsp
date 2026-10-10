<%-- 
    Document   : registrationlist
    Created on : Mar 16, 2025, 4:27:16 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
     <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Registration Event Detail</title>
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
                                Registration Management
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
                                    <a href="registrationEventList">Registration Management</a>
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
                                                Registration List
                                            </h4>
                                            ${requestScope.error}
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="row mb-3">
                                            <form action="registrationEventList" method="get" class="row align-items-center">
                                                <!-- Filter Club -->
                                                <div class="col-md-3">
                                                    <select id="clubid" name="clubid" class="form-select" onchange="this.form.submit()">
                                                        <option value="">Select Club</option>
                                                        <c:forEach var="club" items="${clubsToFilter}">
                                                            <option value="${club.getClub_id()}" ${param.clubid eq club.getClub_id().toString() ? 'selected' : ''}>
                                                                ${club.getName()}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Filter Event -->
                                                <div class="col-md-3">
                                                    <select id="eventId" name="eventId" class="form-select" onchange="this.form.submit()">
                                                        <option value="">Select Event</option>
                                                        <c:forEach var="event" items="${eventsToFilter}">
                                                            <option value="${event.getEvent_id()}" ${param.eventId eq event.getEvent_id().toString() ? 'selected' : ''}>
                                                                ${event.getName()}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Filter Status -->
                                                <div class="col-md-3">
                                                    <select id="statusFilter" name="statusFilter" class="form-select" onchange="this.form.submit()">
                                                        <option value="Select Status" ${empty param.statusFilter ? 'selected' : ''}>Select Status</option>
                                                        <option value="Pending" ${param.statusFilter eq 'Pending' ? 'selected' : ''}>Pending</option>
                                                        <option value="Confirmed" ${param.statusFilter eq 'Confirmed' ? 'selected' : ''}>Confirmed</option>
                                                        <option value="Rejected" ${param.statusFilter eq 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                    </select>
                                                </div>

                                                <!-- Search Box -->
                                                <div class="col-md-3">
                                                    <div class="input-group">
                                                        <input type="text" name="search" value="${param.search}" placeholder="Search..." class="form-control">
                                                        <button type="submit" class="btn btn-primary">
                                                            <i class="fa fa-search"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <!--start table-->
                                        <!-- Place this inside the card-body div, where the "start table" comment is -->

                                        <div class="table-responsive">
                                            <table class="display table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Event</th>
                                                        <th>Registration</th>
                                                        <th>Phone Number</th>
                                                        <th>Email</th>
                                                        <th>Status</th>
                                                        <th>Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="registration" items="${registrationEvents}">
                                                        <tr id="registrationEventList">
                                                            <td>${registration.registration_id}</td>
                                                            <th>${registration.eventName}</th>
                                                            <td>${registration.full_name}</td>
                                                            <td>${registration.mobile}</td>
                                                            <td>${registration.email}</td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${registration.status eq 'Pending'}">
                                                                        <span class="badge bg-warning">${registration.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${registration.status eq 'Approved'}">
                                                                        <span class="badge bg-success">${registration.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${registration.status eq 'Rejected'}">
                                                                        <span class="badge bg-danger">${registration.status}</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-secondary">${registration.status}</span>
                                                                    </c:otherwise>
                                                                </c:choose> 
                                                            </td>
                                                            <td>
                                                                <div class="d-flex gap-2">
                                                                    <a href="registrationDetail?id=${registration.registration_id}" class="btn btn-sm btn-primary"><i class="fas fa-edit"></i></a>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- Phân trang -->
                                        <div class="pagination-container mt-3">
                                            <nav>
                                                <ul class="pagination justify-content-center">
                                                    <!-- Nút Previous -->
                                                    <c:if test="${currentPage > 1}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="?page=${currentPage - 1}&clubid=${param.clubid}&eventId=${param.eventId}&statusFilter=${param.statusFilter}&search=${param.search}">
                                                                Previous
                                                            </a>
                                                        </li>
                                                    </c:if>

                                                    <!-- Hiển thị số trang -->
                                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                            <a class="page-link" href="?page=${pageNum}&clubid=${param.clubid}&eventId=${param.eventId}&statusFilter=${param.statusFilter}&search=${param.search}">
                                                                ${pageNum}
                                                            </a>
                                                        </li>
                                                    </c:forEach>

                                                    <!-- Nút Next -->
                                                    <c:if test="${currentPage < totalPages}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="?page=${currentPage + 1}&clubid=${param.clubid}&eventId=${param.eventId}&statusFilter=${param.statusFilter}&search=${param.search}">
                                                                Next
                                                            </a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <!--footer-->
            </div>
        </div>

        <!-- Required JavaScript Files -->
        <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Kaiadmin JS -->
        <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>
        <!-- Initialize Bootstrap components -->
    </body>
</html>