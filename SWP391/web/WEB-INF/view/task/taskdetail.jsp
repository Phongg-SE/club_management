<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Task Details</title>
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
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
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
                                Task Details
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
                                    <a href="taskList">Task Management</a>
                                </li>
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="#">Task Details</a>
                                </li>
                            </ul>
                        </div>

                        <!-- Task Details Card -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h4 class="card-title">
                                                Task #${task.getTaskId()} - ${task.getTitle()}
                                            </h4>
                                            <div class="text-danger mt-1">${error}</div>
                                            <%     String success = (String) session.getAttribute("success");
                                                    if(success != null) { 
                                            %>
                                            <div class="text-success mt-1">${success}</div>
                                            <% session.removeAttribute("success"); %>
                                            <% } %>
                                            <div>
                                                <a href="updateTask?id=${task.getTaskId()}" class="btn btn-primary btn-sm">
                                                    <i class="fas fa-edit"></i> Update Task
                                                </a>
                                                <a href="taskList" class="btn btn-secondary btn-sm">
                                                    <i class="fas fa-arrow-left"></i> Back to List
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="row">
                                            <!-- Task Information (Left Column) -->
                                            <div class="col-md-6">
                                                <div class="card shadow-sm mb-4">
                                                    <div class="card-header bg-light">
                                                        <h5 class="card-subtitle mb-0">Task Information</h5>
                                                    </div>
                                                    <div class="card-body">
                                                        <div class="row mb-3">
                                                            <div class="col-md-6">
                                                                <h6 class="fw-bold text-primary">Title</h6>
                                                                <p>${task.getTitle()}</p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <h6 class="fw-bold text-primary">Club</h6>
                                                                <p>${task.getClubname()}</p>
                                                            </div>
                                                        </div>

                                                        <div class="row mb-3">
                                                            <c:if test="${task.getType() == 'Event'}">
                                                                <h6 class="fw-bold text-primary">Event</h6>
                                                            </c:if>
                                                            <c:if test="${task.getType() == 'Project'}">
                                                                <h6 class="fw-bold text-primary">Project</h6>
                                                            </c:if>
                                                            <p>${task.getEventname()}</p>
                                                        </div>
                                                        <div class="mb-3">
                                                            <h6 class="fw-bold text-primary">Description</h6>
                                                            <p>${task.getDescription()}</p>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Status Information (Right Column) -->
                                            <div class="col-md-6">
                                                <div class="card shadow-sm mb-4">
                                                    <div class="card-header bg-light">
                                                        <h5 class="card-subtitle mb-0">Status Information</h5>
                                                    </div>
                                                    <div class="card-body">
                                                        <div class="row mb-3">
                                                            <div class="col-md-6">
                                                                <h6 class="fw-bold text-primary">Deadline</h6>
                                                                <p class="${task.getStatus() eq 'Overdue' ? 'text-danger' : ''}">${task.deadline}</p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <h6 class="fw-bold text-primary">Status</h6>
                                                                <p>
                                                                    <span class="badge ${task.getStatus() eq 'Pending' ? 'bg-warning' : 
                                                                                         task.getStatus() eq 'Rejected' ? 'bg-danger' :
                                                                                         task.getStatus() eq 'Completed' ? 'bg-success' : 
                                                                                         task.getStatus() eq 'Overdue' ? 'bg-danger' : 'bg-secondary'}">
                                                                              ${task.getStatus()}
                                                                          </span>
                                                                    </p>
                                                                </div>
                                                            </div>

                                                            <div class="mb-3">
                                                                <h6 class="fw-bold text-primary">Progress</h6>
                                                                <div class="progress mb-2" style="height: 10px;">
                                                                    <div class="progress-bar bg-success" role="progressbar" 
                                                                         style="width: ${task.getStatus() eq 'Completed' ? '100' : 
                                                                                         task.getStatus() eq 'Pending' ? '0' : 
                                                                                         task.getStatus() eq 'Rejected' ? '0' : '50'}%;" 
                                                                         aria-valuenow="${task.getStatus() eq 'Completed' ? '100' : 
                                                                                          task.getStatus() eq 'Pending' ? '0' : 
                                                                                          task.getStatus() eq 'Rejected' ? '0' : '50'}" 
                                                                         aria-valuemin="0" aria-valuemax="100">
                                                                    </div>
                                                                </div>
                                                                <small class="text-muted">
                                                                    ${task.getStatus() eq 'Completed' ? '100' : 
                                                                      task.getStatus() eq 'Pending' ? '0' : 
                                                                      task.getStatus() eq 'Rejected' ? '0' : '50'}% Complete
                                                                </small>
                                                            </div>
                                                            <div class="mb-3">
                                                                <h6 class="fw-bold text-primary">Note</h6>
                                                                <p>${task.getNote()}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Assignment Row -->
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="card shadow-sm mb-4">
                                                        <div class="card-header bg-light">
                                                            <h5 class="card-subtitle mb-0">Assignment Information</h5>
                                                        </div>
                                                        <div class="card-body">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <h6 class="fw-bold text-primary">Assigner</h6>
                                                                    <div class="d-flex align-items-center mb-3">
                                                                        <img src="${task.getAssigner_avatar()}" class="avatar avatar-md-sm rounded-circle border shadow me-2" alt="">
                                                                        <div>
                                                                            <p class="mb-0">${task.getAssignerName()}</p>
                                                                            <small class="text-muted">${task.getAssignerRole()}</small>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <h6 class="fw-bold text-primary">Assignee</h6>
                                                                    <div class="d-flex align-items-center mb-3">
                                                                        <img src="${task.getAssignee_avatar()}" class="avatar avatar-md-sm rounded-circle border shadow me-2" alt="">
                                                                        <div>
                                                                            <p class="mb-0">${task.getAssigneeName()}</p>
                                                                            <small class="text-muted">${task.getAssigneeRole()}</small>                                                                         
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
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
                    </div>
                </div>

                <!-- Core JS Files -->

                <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>

                <!-- jQuery Scrollbar -->
                <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
                <!-- Datatables -->
                <script src="${pageContext.request.contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>
                <!-- Kaiadmin JS -->
                <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>

            </div>
        </body>
    </html>