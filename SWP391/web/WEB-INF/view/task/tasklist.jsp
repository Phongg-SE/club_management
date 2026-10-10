<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Task Manager</title>
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
                <div class="">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">
                                Task Management
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
                            </ul>
                        </div>
                        <!-- Card -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h4 class="card-title">
                                                Task List
                                            </h4>
                                            ${requestScope.error}
                                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addTaskModal">
                                                <i class="fas fa-plus"></i> Add New Task
                                            </button>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="row mb-3">
                                            <!--filter-->
                                            <div class="col-md-6">
                                                <form method="get" action="taskList" class="d-flex align-items-center gap-2">
                                                    <input type="hidden" id="taskType" name="type" value="${empty param.type ? 'Event' : param.type}">
                                                    <div class="btn-group" role="group">                                                        
                                                        <button type="button" 
                                                                class="btn ${taskType eq 'Event' ? 'btn-primary' : 'btn-outline-primary'}"
                                                                onclick="setTaskTypeAndSubmit('Event')">
                                                            Event
                                                        </button>
                                                        <button type="button" 
                                                                class="btn ${taskType eq 'Project' ? 'btn-primary' : 'btn-outline-primary'}"
                                                                onclick="setTaskTypeAndSubmit('Project')">
                                                            Project
                                                        </button>

                                                    </div>
                                                    <!-- Status filter -->
                                                    <div class="col-md-3">
                                                        <select id="statusFilter" class="form-select" name="statusFilter" onchange="this.form.submit()">
                                                            <option value="" ${empty param.statusFilter ? 'selected' : ''}>Select Status</option>
                                                            <option value="Pending" ${param.statusFilter eq 'Pending' ? 'selected' : ''}>Pending</option>
                                                            <option value="In Progress" ${param.statusFilter eq 'In Progress' ? 'selected' : ''}>In Progress</option>
                                                            <option value="Completed" ${param.statusFilter eq 'Completed' ? 'selected' : ''}>Completed</option>
                                                            <option value="Overdue" ${param.statusFilter eq 'Overdue' ? 'selected' : ''}>Overdue</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <select id="clubid" class="form-select" name="clubid" onchange="this.form.submit()">
                                                            <option value="">Select Club</option>
                                                            <c:forEach var="club" items="${clubs}">
                                                                <option value="${club.getClub_id()}" ${param.clubid eq club.getClub_id().toString() ? 'selected' : ''}>
                                                                    ${club.getName()}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="col-md-6">
                                                        <select id="eventid" class="form-select" name="eventid" onchange="this.form.submit()">
                                                            <c:if test="${param.type == 'Event' || param.type == null}">
                                                                <option value="">Select Event</option>
                                                            </c:if>
                                                            <c:if test="${param.type == 'Project'}">
                                                                <option value="">Select Project</option>
                                                            </c:if>
                                                            <c:forEach var="event" items="${events}">
                                                                <option value="${event.event_id}" 
                                                                        <c:if test="${param.eventid eq event.event_id.toString()}">selected</c:if>>
                                                                    ${event.name}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="col-md-3">
                                                        <select id="assigneeeid" class="form-select" name="assigneeeid" onchange="this.form.submit()">
                                                            <option value="">Select Assignee</option>
                                                            <c:forEach var="assignee" items="${assignees}">
                                                                <option value="${assignee.getUserId()}" 
                                                                        <c:if test="${param.assigneeeid eq assignee.getUserId().toString()}">selected</c:if>>
                                                                    ${assignee.getFullname()}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-6" style="margin-left: 80px">
                                                        <div class="input-group" style="max-width: 300px;">
                                                            <input id="searchInput" oninput="searchByName(this)" value="${param.txt}" name="txt" type="text" class="form-control" placeholder="Search task...">
                                                            <button type="submit" class="btn btn-primary">
                                                                <i class="fa fa-search"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>

                                        </div>
                                        <!--start table-->
                                        <!-- Place this inside the card-body div, where the "start table" comment is -->
                                        <div class="table-responsive">
                                            <table class="display table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Club</th>
                                                            <c:if test="${param.type == null || param.type == 'Event'}">
                                                            <th>Event</th>
                                                            </c:if>
                                                            <c:if test="${param.type == 'Project'}">
                                                            <th>Project</th>
                                                            </c:if>
                                                        <th>Title</th>                                                          
                                                        <th>Assigner</th>
                                                        <th>Assignee</th>
                                                        <th>Deadline</th>
                                                        <th>Status</th>
                                                        <th>Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="task" items="${tasks}">
                                                        <tr id="taskList">
                                                            <td>${task.taskId}</td>
                                                            <th>${task.clubname}</th>
                                                            <td>${task.eventname}</td>
                                                            <td>${task.title}</td>
                                                            <td>${task.assignerName}</td>
                                                            <td>${task.assigneeName}</td>
                                                            <td>
                                                                <fmt:formatDate value="${task.deadline}" pattern="yyyy-MM-dd" />
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${task.status eq 'Pending'}">
                                                                        <span class="badge bg-warning">${task.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${task.status eq 'Completed'}">
                                                                        <span class="badge bg-success">${task.status}</span>
                                                                    </c:when>
                                                                    <c:when test="${task.status eq 'Overdue' || task.status eq 'Rejected'}">
                                                                        <span class="badge bg-danger">${task.status}</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge bg-secondary">${task.status}</span>
                                                                    </c:otherwise>
                                                                </c:choose> 
                                                            </td>
                                                            <td>
                                                                <div class="d-flex gap-2">
                                                                    <a href="taskDetail?id=${task.getTaskId()}" class="btn btn-sm btn-info"><i class="fas fa-eye"></i></a>
                                                                    <a href="updateTask?id=${task.getTaskId()}" class="btn btn-sm btn-primary"><i class="fas fa-edit"></i></a>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!--paging-->
                                        <c:if test="${param.type == null}">
                                            <div id="paginationControls" class="mt-3">
                                                <nav aria-label="Page navigation">
                                                    <ul class="pagination justify-content-center">
                                                        <c:if test="${currentPage > 1}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="?page=${currentPage - 1}&pageSize=${pageSize}">Previous</a>
                                                            </li>
                                                        </c:if>

                                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="?page=${i}&pageSize=${pageSize}">${i}</a>
                                                            </li>
                                                        </c:forEach>

                                                        <c:if test="${currentPage < totalPages}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="?page=${currentPage + 1}&pageSize=${pageSize}">Next</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </c:if>
                                        <c:if test="${param.type != null}">
                                            <div id="paginationControls" class="mt-3">
                                                <nav aria-label="Page navigation">
                                                    <ul class="pagination justify-content-center">
                                                        <c:if test="${currentPage > 1}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="?page=${currentPage - 1}&pageSize=${pageSize}&type=${param.type}&statusFilter=${param.statusFilter}&clubid=${param.clubid}&eventid=${param.eventid}&assigneeeid=${param.assigneeeid}&txt=${param.txt}">Previous</a>
                                                            </li>
                                                        </c:if>

                                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                <a class="page-link" href="?page=${i}&pageSize=${pageSize}&type=${param.type}&statusFilter=${param.statusFilter}&clubid=${param.clubid}&eventid=${param.eventid}&assigneeeid=${param.assigneeeid}&txt=${param.txt}">${i}</a>
                                                            </li>
                                                        </c:forEach>

                                                        <c:if test="${currentPage < totalPages}">
                                                            <li class="page-item">
                                                                <a class="page-link" href="?page=${currentPage + 1}&pageSize=${pageSize}&type=${param.type}&statusFilter=${param.statusFilter}&clubid=${param.clubid}&eventid=${param.eventid}&assigneeeid=${param.assigneeeid}&txt=${param.txt}">Next</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Modal for adding a new task -->
                <div class="modal fade" id="addTaskModal" tabindex="-1" aria-labelledby="addTaskModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addTaskModalLabel">Add New Task</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="taskList" method="post">
                                    <div class="row">
                                        <div class="col-md-12 mb-3">
                                            <label for="title" class="form-label">Title</label>
                                            <input type="text" class="form-control" id="title" name="title" required>
                                            <div class="text-danger mt-1">${errorTitle}</div>
                                        </div>
                                    </div>

                                    <!-- Thêm phần radio button chọn Event/Project -->
                                    <div class="row mb-3">
                                        <div class="col-md-6 mb-3">
                                            <label for="clubId" class="form-label">Club</label>
                                            <select class="form-select" id="clubId" name="clubId" required>
                                                <option value="">Select Club</option>
                                                <c:forEach var="club" items="${clubs}">
                                                    <option value="${club.getClub_id()}">${club.getName()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <div><label class ="form-label">Task Type</label> </div>
                                            <div class="form-check form-check-inline" style="margin-left: 10px">
                                                <input class="form-check-input" type="radio" name="taskType" id="eventOption" value="Event" required>
                                                <label class="form-check-label" for="eventOption">Event</label>
                                            </div>
                                            <div class="form-check form-check-inline" style="margin-left: 20px">
                                                <input class="form-check-input" type="radio" name="taskType" id="projectOption" value="Project">
                                                <label class="form-check-label" for="projectOption">Project</label>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Điều chỉnh phần Event và Assignee để hiển thị/ẩn dựa theo lựa chọn -->
                                    <div class="row" id="eventSection">
                                        <div class="col-md-6 mb-3">
                                            <label for="eventId" class="form-label">Event</label>
                                            <select class="form-select" id="eventId" name="eventId">
                                                <option value="">Select Event</option>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="assigneeId" class="form-label">Assignee</label>
                                            <select class="form-select" id="assigneeId" name="assigneeId" required>
                                                <option value="">Select Assignee</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row" id="projectSection" style="display:none;">
                                        <div class="col-md-6 mb-3">
                                            <label for="projectId" class="form-label">Project</label>
                                            <select class="form-select" id="projectId" name="projectId">
                                                <option value="">Select Project</option>
                                                <!-- Thêm danh sách project ở đây -->
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="projectAssigneeId" class="form-label">Assignee</label>
                                            <select class="form-select" id="projectAssigneeId" name="projectAssigneeId">
                                                <option value="">Select Assignee</option>
                                                <!-- Thêm danh sách assignee cho project -->
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="deadline" class="form-label">Deadline</label>
                                            <input type="datetime-local" class="form-control" id="deadline" name="deadline" required>
                                            <div class="text-danger mt-1">${errorDeadline}</div>
                                        </div>
                                        <div class="col-md-6 mb-3" id="statusField" style="display: none;">
                                            <label for="eventId" class="form-label">Status</label>
                                            <select class="form-select" id="statusId" name="status" required>
                                                <option value="In Progress">In Progress</option>
                                                <option value="Completed">Completed</option>
                                                <option value="Overdue">Overdue</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="description" class="form-label">Description</label>
                                        <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                                        <div class="text-danger mt-1">${errorDescription}</div>
                                    </div>
                                    <div class="text-end">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-primary">Add Task</button>
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
        <script>
                                                            $(document).ready(function () {
                                                                function fetchEvents() {
                                                                    var clubId = $("#clubId").val();
                                                                    var taskType = $("input[name='taskType']:checked").val();

                                                                    $("#assigneeId").empty().append('<option value="">Select Assignee</option>');
                                                                    $("#statusField").hide();
                                                                    
                                                                    if (clubId && taskType) {
                                                                        $.ajax({
                                                                            url: "getClubDetails",
                                                                            type: "GET",
                                                                            data: {clubId: clubId, taskType: taskType}, // Gửi cả clubId và taskType
                                                                            dataType: "json",
                                                                            success: function (response) {
                                                                                var eventSelect = $("#eventId");
                                                                                eventSelect.empty().append('<option value="">Select Event</option>');

                                                                                $.each(response.events, function (index, event) {
                                                                                    eventSelect.append('<option value="' + event.event_id + '">' + event.name + '</option>');
                                                                                });

                                                                                if (response.role.includes(4)) {
                                                                                    $("#statusField").show(); // Hiện status nếu là leader
                                                                                } else {
                                                                                    $("#statusField").hide(); // Ẩn nếu là member
                                                                                }
                                                                            },
                                                                            error: function (xhr, status, error) {
                                                                                console.error("Error: " + error);
                                                                            }
                                                                        });
                                                                    } else {
                                                                        $("#eventId").empty().append('<option value="">Select Event</option>');
                                                                        $("#statusField").hide();
                                                                    }
                                                                }

                                                                function fetchAssignees() {
                                                                    var eventId = $("#eventId").val();
                                                                    var taskType = $("input[name='taskType']:checked").val();
                                                                    var clubId = $("#clubId").val();

                                                                    if (eventId) {
                                                                        $.ajax({
                                                                            url: "getAssignees",
                                                                            type: "GET",
                                                                            data: {eventId: eventId, taskType: taskType, clubId: clubId},
                                                                            dataType: "json",
                                                                            success: function (response) {
                                                                                var assigneeSelect = $("#assigneeId");
                                                                                assigneeSelect.empty().append('<option value="">Select Assignee</option>');

                                                                                $.each(response.members, function (index, member) {
                                                                                    assigneeSelect.append('<option value="' + member.user_id + '">' + member.fullname + '</option>');
                                                                                });

                                                                                if (response.leader || response.role1.contains(4)) {
                                                                                    $("#statusField").show();
                                                                                } else {
                                                                                    $("#statusField").hide();
                                                                                }
                                                                            },
                                                                            error: function (xhr, status, error) {
                                                                                console.error("Error: " + error);
                                                                            }
                                                                        });
                                                                    } else {
                                                                        $("#assigneeId").empty().append('<option value="">Select Assignee</option>');
                                                                        $("#statusField").hide();
                                                                    }
                                                                }


                                                                // Gọi fetchEvents khi có thay đổi ở Club hoặc Task Type
                                                                $("#clubId").change(fetchEvents);
                                                                $("input[name='taskType']").change(fetchEvents);

                                                                $("#eventId").change(fetchAssignees);

                                                            });
        </script>

        <%
     String message = request.getParameter("message");
        %>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
                                                            document.addEventListener("DOMContentLoaded", function () {
                                                                var message = "<%= message != null ? message : "" %>";
                                                                if (message) {
                                                                    Swal.fire({
                                                                        icon: 'success',
                                                                        title: 'Success!',
                                                                        text: message,
                                                                        showConfirmButton: false,
                                                                        timer: 2000
                                                                    });
                                                                }
                                                            });
        </script>

        <script>
            <% if (request.getParameter("showAdd") != null) { %>
            var addTaskModal = new bootstrap.Modal(document.getElementById('addTaskModal'));
            addTaskModal.show();
            <% } %>
        </script>
        <script>
            function setTaskTypeAndSubmit(type) {
                document.getElementById("taskType").value = type;
                document.querySelector("form").submit();
            }
        </script>
    </body>
</html>