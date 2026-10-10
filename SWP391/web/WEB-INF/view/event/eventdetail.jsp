<%-- 
    Document   : eventdetail
    Created on : Feb 3, 2025, 11:28:42 AM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Event Detail</title>
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
                            <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="${pageContext.request.contextPath}/assets/images/doctors/01.jpg" class="avatar avatar-ex-small rounded-circle" alt=""></button>
                            <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                                <a class="dropdown-item d-flex align-items-center text-dark" href="https://shreethemes.in/doctris/layouts/admin/profile.html">
                                    <img src="../assets/images/doctors/01.jpg" class="avatar avatar-md-sm rounded-circle border shadow" alt="">
                                    <div class="flex-1 ms-2">
                                        <span class="d-block mb-1">Calvin Carlo</span>
                                        <small class="text-muted">Orthopedic</small>
                                    </div>
                                </a>
                                <a class="dropdown-item text-dark" href="index.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span> Dashboard</a>
                                <a class="dropdown-item text-dark" href="dr-profile.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings</a>
                                <div class="dropdown-divider border-top"></div>
                                <a class="dropdown-item text-dark" href="lock-screen.html"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="main-panel">                    
                <div class="container-fluid">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">Event Details</h3>
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
                                    <a href="EventList">Event Management</a>
                                </li>
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="#">Event Details</a>
                                </li>
                            </ul>
                        </div>
                        <!--update EVent-->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div>
                                        <h4 style="color: red" >${msg}</h4>
                                    </div>
                                    <div class="card-header">
                                        <h4 class="card-title">Event Details </h4>

                                        <div class="card-body">
                                            <!-- Form cập nhật sự kiện -->
                                            <form action="UpdateEvent" method="post" enctype="multipart/form-data">

                                                <!-- in ra lỗi khi input -->
                                                <c:if test="${not empty sessionScope.errorMessage}">
                                                    <div class="alert alert-danger">
                                                        ${sessionScope.errorMessage}
                                                    </div>
                                                    <c:remove var="errorMessage" scope="session"/>
                                                </c:if>

                                                <input type="hidden" name="event_id" value="${event.event_id}">

                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="eventName">Event Name</label>
                                                            <input type="text" class="form-control" id="eventName" name="name" value="${event.name}" required/>
                                                        </div>
                                                    </div>  
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="location">Location</label>
                                                            <input type="text" class="form-control" id="location" name="location" value="${event.location}" required/>
                                                        </div>
                                                    </div> 
                                                </div>  
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="eventImage">Upload new Image:</label>
                                                            <input type="file" class="form-control" id="eventImage" name="eventImage" accept="image/*">
                                                            <img id="previewImage" src="#" alt="Preview Image" style="display: none; max-width: 200px; margin-top: 10px;">
                                                        </div>
                                                    </div>

                                                    <!-- Hiển thị ảnh hiện tại -->
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label>Event Image:</label><br>
                                                            <img id="imagePreview" src="${event.avatar_url}" alt="Event Image" 
                                                                 style="max-width: 200px; max-height: 200px; ${event.avatar_url == null ? 'display: none;' : ''}">
                                                            <input type="hidden" name="oldEventImage" value="${event.avatar_url}">
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- hien anh preview -->
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



                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="leaderID">Event Leader</label>
                                                        <select class="form-control" id="leaderID" name="leader_id">
                                                            <c:set var="found" value="false" />

                                                            <c:forEach var="user" items="${users}">
                                                                <c:if test="${user.user_id == event.leader_id}">
                                                                    <c:set var="found" value="true" />
                                                                </c:if>
                                                                <option value="${user.user_id}" ${user.user_id == event.leader_id ? 'selected' : ''}>
                                                                    ${user.full_name}
                                                                </option>
                                                            </c:forEach>

                                                            <c:if test="${!found && event.leader_id != null}">
                                                                <option value="${event.leader_id}" selected>
                                                                    ${event.leader_name}
                                                                </option>
                                                            </c:if>
                                                        </select>


                                                    </div>
                                                </div>


                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="startTime">Start Date</label>
                                                            <input type="datetime-local" class="form-control" id="startTime" name="start_time" value="${event.start_time}" required/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="endTime">End Date</label>
                                                            <input type="datetime-local" class="form-control" id="endTime" name="end_time" value="${event.end_time}" required/>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="guest">Guest</label>
                                                            <input type="text" class="form-control" id="guest" name="guest" value="${event.guest}" placeholder="null" />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <!-- Is Public -->
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label>Is Public</label>
                                                            <div class="d-flex gap-3">
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="isPublicYes" name="is_public" value="Yes" ${event.is_public == 'Yes' ? 'checked' : ''} required>
                                                                    <label class="form-check-label" for="isPublicYes">Public</label>
                                                                </div>
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="isPublicNo" name="is_public" value="No" ${event.is_public == 'No' ? 'checked' : ''} required>
                                                                    <label class="form-check-label" for="isPublicNo">Private</label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Status -->
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label>Status</label>
                                                            <div class="d-flex gap-3">
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="statusOngoing" name="status" value="Ongoing" ${event.status == 'Ongoing' ? 'checked' : ''} required>
                                                                    <label class="form-check-label" for="statusOngoing">Ongoing</label>
                                                                </div>
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="statusPlanned" name="status" value="Planned" ${event.status == 'Planned' ? 'checked' : ''} required>
                                                                    <label class="form-check-label" for="statusPlanned">Planned</label>
                                                                </div>
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="statusCompleted" name="status" value="Completed" ${event.status == 'Completed' ? 'checked' : ''} required>
                                                                    <label class="form-check-label" for="statusCompleted">Completed</label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>



                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label for="createdDate">Created Date</label>
                                                            <input type="datetime-local" class="form-control" id="createdDate" name="created_date" value="${event.created_date}" readonly />
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="form-group">
                                                            <label for="description">Description</label>
                                                            <textarea class="form-control" id="description" name="description" rows="4">${event.description}</textarea>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Nút Submit -->
                                                <div class="form-group mt-3">
                                                    <button type="button" class="btn btn-secondary" onclick="window.location.href = '<%= request.getContextPath() %>/EventList';">
                                                        Cancel
                                                    </button>
                                                    <button type="submit" class="btn btn-primary">Update Event</button>
                                                </div>
                                            </form>
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
                                        <a class="nav-link" href="http://www.themekita.com">ThemeKita</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="#">Help</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="#">Licenses</a>
                                    </li>
                                </ul>
                            </nav>
                            <div class="copyright">
                                2024, made with <i class="fa fa-heart heart text-danger"></i> by
                                <a href="http://www.themekita.com">ThemeKita</a>.
                            </div>
                            <div>
                                Distributed by
                                <a target="_blank" href="https://themewagon.com/">ThemeWagon</a>.
                            </div>
                        </div>
                    </footer>
                </div>

            </div>

            <!-- JS Files -->
            <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
    </body>
</html>