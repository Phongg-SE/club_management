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
        <title>Finance Detail</title>
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
                <div class="container">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">Finance Details</h3>
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
                                    <a href="FinanceList">Finance Management</a>
                                </li>
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="#">Finance Details</a>
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
                                        <h4 class="card-title">Finance Details </h4>

                                        <div class="card-body">

                                            <!-- Form cập nhật Finance-->
                                            <form action="UpdateFinance" method="post">
                                                <!-- Hiển thị lỗi nếu có -->
                                                <c:if test="${not empty sessionScope.errorMessage}">
                                                    <div class="alert alert-danger mb-3">
                                                        ${sessionScope.errorMessage}
                                                    </div>
                                                    <c:remove var="errorMessage" scope="session"/>
                                                </c:if>

                                                <input type="hidden" name="financeId" value="${finance.financeId}">
                                                <input type="hidden" name="clubId" value="${finance.clubId}">

                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label for="financeName" class="form-label">Finance Name</label>
                                                        <input type="text" class="form-control" id="financeName" name="financeName" value="${finance.finance_name}" placeholder="Nhập tên tài chính" required/>
                                                    </div>

                                                    <div class="col-md-6 mb-3">
                                                        <c:set var="eventName" value="Unknown Event" />
                                                        <c:forEach var="event" items="${events}">
                                                            <c:if test="${event.event_id == finance.eventId}">
                                                                <c:set var="eventName" value="${event.name}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <label for="eventName" class="form-label">Event Name</label>
                                                        <input type="text" class="form-control" id="eventName" value="${eventName}" readonly />
                                                        <input type="hidden" name="eventId" value="${finance.eventId}" />
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <c:set var="memberName" value="Không xác định" /> 
                                                        <c:forEach var="user" items="${users}">
                                                            <c:if test="${user.user_id == finance.memberId}">
                                                                <c:set var="memberName" value="${user.full_name}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <label for="memberName" class="form-label">Member</label>
                                                        <input type="text" class="form-control" id="memberName" value="${finance.memberName}" readonly />
                                                        <input type="hidden" name="memberId" value="${finance.memberId}" />
                                                    </div>

                                                    <div class="col-md-6 mb-3">
                                                        <c:set var="approverName" value="No Approver" />
                                                        <c:forEach var="user" items="${users}">
                                                            <c:if test="${user.user_id == finance.approverId}">
                                                                <c:set var="approverName" value="${user.full_name}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <label for="approverName" class="form-label"></label>
                                                        <input type="hidden" type="text" class="form-control" id="approverName" value="${approverName}" readonly />
                                                        <input type="hidden" name="approverId" value="${finance.approverId}" />
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label for="amount" class="form-label">Amount</label>
                                                        <input type="text" class="form-control" id="amount" name="amount" value="${finance.amount}" placeholder="Nhập số tiền" />
                                                    </div>

                                                    <div class="col-md-6 mb-3">
                                                        <label class="form-label">Is Expense</label>
                                                        <div class="d-flex gap-3"> 
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" id="expenseYes" name="isExpense" value="Yes" ${finance.isExpense == 'Yes' ? 'checked' : ''} required>
                                                                <label class="form-check-label" for="expenseYes">Expense</label>
                                                            </div>
                                                            <div class="form-check">
                                                                <input class="form-check-input" type="radio" id="expenseNo" name="isExpense" value="No" ${finance.isExpense == 'No' ? 'checked' : ''} required>
                                                                <label class="form-check-label" for="expenseNo">Income</label>
                                                            </div>
                                                        </div>
                                                    </div>



                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <label for="financeDate" class="form-label">Finance Date</label>
                                                            <input type="date" class="form-control" id="financeDate" name="financeDate" value="${finance.financeDate}" required/>
                                                        </div>

                                                        <div class="col-md-6 mb-3">
                                                            <label for="lastUpdated" class="form-label">Last Updated</label>
                                                            <input type="datetime" class="form-control" id="lastUpdated" name="lastUpdated" value="${finance.last_updated}" readonly />
                                                        </div>
                                                    </div>

                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <label class="form-label">Status</label>
                                                            <div class="d-flex gap-3"> 
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="statusApprove" name="status" value="Approve"
                                                                           ${finance.status == 'Approve' ? 'checked' : ''}
                                                                           ${isClubLeader ? '' : 'disabled'}>
                                                                    <label class="form-check-label" for="statusApprove">Approve</label>
                                                                </div>
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="statusPending" name="status" value="Pending"
                                                                           ${finance.status == 'Pending' ? 'checked' : ''}
                                                                           ${isClubLeader ? '' : 'disabled'}>
                                                                    <label class="form-check-label" for="statusPending">Pending</label>
                                                                </div>
                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio" id="statusReject" name="status" value="Reject"
                                                                           ${finance.status == 'Reject' ? 'checked' : ''}
                                                                           ${isClubLeader ? '' : 'disabled'}>
                                                                    <label class="form-check-label" for="statusReject">Reject</label>
                                                                </div>
                                                            </div>
                                                        </div>


                                                        <div class="row">
                                                            <div class="col-md-12 mb-3">
                                                                <label for="details" class="form-label">Details</label>
                                                                <textarea class="form-control" id="description" name="details" rows="4">${finance.details}</textarea>
                                                            </div>
                                                        </div>

                                                        <!-- Nút Submit -->
                                                        <div class="form-group mt-3">
                                                            <a href="${pageContext.request.contextPath}/FinanceList" class="btn btn-secondary">Cancel</a>
                                                            <button type="submit" class="btn btn-primary">Update Finance</button>

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