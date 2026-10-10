<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    HttpSession sessionObj = request.getSession();
    String successMessage = (String) sessionObj.getAttribute("success");
    if (successMessage != null) {
        sessionObj.removeAttribute("success"); 
    }
%>


<!DOCTYPE html>
<html>
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
                                Registration Event Detail
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
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="registrationDetail?id=${registration.getRegistration_id()}">Registration Event Details</a>
                                </li>
                            </ul>
                        </div>

                        <!-- Update Registration Form Card -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h4 class="card-title">
                                                Registration #${registration.getRegistration_id()}
                                            </h4>
                                            <div>
                                                <a href="registrationEventList" class="btn btn-secondary btn-sm">
                                                    <i class="fas fa-arrow-left"></i> Back to Registration Event List
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <form action="registrationDetail" method="POST">
                                            <input type="hidden" name="id" value="${registration.getRegistration_id()}">
                                            <!-- Event Information Section -->
                                            <div class="card shadow-sm mb-4">
                                                <div class="card-header bg-light">
                                                    <h5 class="card-subtitle mb-0">Event Information</h5>
                                                </div>
                                                <div class="card-body">
                                                    <div class="row">   
                                                        <div class="col-md-12 mb-3">
                                                            <label class="form-label fw-bold">Event Name</label>
                                                            <input type="text" class="form-control" value="${registration.getEventName()}" readonly>
                                                            <input type="hidden" class="form-control" value="${registration.getEventName()}" >

                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label fw-bold">Club Name</label>
                                                            <input type="text" class="form-control" value="${registration.getClubName()}" readonly>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label fw-bold">Event Status</label>
                                                            <input type="text" class="form-control" value="${registration.getEventStatus()}" readonly>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Registrant Information Section -->
                                            <div class="card shadow-sm mb-4">
                                                <div class="card-header bg-light">
                                                    <h5 class="card-subtitle mb-0">Registration Form Information</h5>
                                                </div>
                                                <div class="card-body">
                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label fw-bold">Full Name</label>
                                                            <input type="text" class="form-control" value="${registration.getFull_name()}" readonly>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label fw-bold">Email</label>
                                                            <input type="email" class="form-control" value="${registration.getEmail()}" readonly>
                                                            <input type="hidden" class="form-control" value="${registration.getEmail()}" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label fw-bold">Mobile</label>
                                                            <input type="text" class="form-control" value="${registration.getMobile()}" readonly>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <label for="status" class="form-label fw-bold">Status <span class="text-danger">*</span></label>
                                                            <select class="form-select" id="status" name="status" ${registration.getStatus() == 'Confirmed' || registration.getStatus() == 'Rejected' ? 'disabled' : ''} required>
                                                                <option value="Pending" ${registration.getStatus() == 'Pending' ? 'selected' : ''}>Pending</option>
                                                                <option value="Confirmed" ${registration.getStatus() == 'Confirmed' ? 'selected' : ''}>Confirmed</option>
                                                                <option value="Rejected" ${registration.getStatus() == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                            </select>
                                                            <input type="hidden" name="status" value="${registration.getStatus()}">
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12 mb-3" id="reasonDiv" style="display: none;">
                                                            <label for="reason" class="form-label fw-bold">Reason</label>
                                                            <textarea class="form-control" id="reason" name="reason" rows="2"></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12 mb-3">
                                                            <label for="note" class="form-label fw-bold">Notes</label>
                                                            <textarea  class="form-control" id="note" name="note" rows="3" readonly>${registration.getNote()}</textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12 text-end">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="fas fa-save"></i> Update Registration
                                                    </button>
                                                    <a href="registrationEventList" class="btn btn-secondary">
                                                        <i class="fas fa-times"></i> Cancel
                                                    </a>
                                                </div>
                                            </div>
                                        </form>
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


           
        <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Kaiadmin JS -->
        <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>

            <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Initialize select boxes with Select2 if available
                if (typeof $.fn.select2 !== 'undefined') {
                    $('#status').select2();
                }
            });
            </script>


            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

            <% if (successMessage != null) { %>
            <script>
            $(document).ready(function () {
                Swal.fire({
                    icon: 'success',
                    title: 'Success',
                    text: "<%= successMessage %>",
                    timer: 3000,
                    showConfirmButton: false
                });
            });
            </script>
            <% } %>
            
            <script>
                $(document).ready(function () {
                    $("#status").change(function () {
                        var selectedStatus = $(this).val();

                        $.ajax({
                            type: "POST",
                            url: "UpdateStatusServlet",
                            data: {status: selectedStatus},
                            dataType: "json",
                            success: function (response) {
                                if (response.showReason) {
                                    $("#reasonDiv").show();
                                } else {
                                    $("#reasonDiv").hide();
                                }
                            }
                        });
                    });
                });
            </script>
        </div>
    </body>
</html>