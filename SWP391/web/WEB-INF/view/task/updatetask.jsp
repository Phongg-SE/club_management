<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Update Task</title>
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
            <c:set var="eventLeader" value="2"/>
            <c:set var="clubMember" value="3"/>
            <c:set var="clubLeader" value="4"/>
            <c:set var="projectLeader" value="5"/>

            <c:set var="Permission" value="false"/>
            <c:forEach var="role" items="${clubroles}">
                <c:if test="${role == clubLeader or role == eventLeader or role == projectLeader}">
                    <c:set var="Permission" value="true" />
                </c:if>
            </c:forEach>

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
                                Update Task
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
                                    <a href="taskDetail?id=${task.getTaskId()}">Task Details</a>
                                </li>
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="#">Update Task</a>
                                </li>
                            </ul>
                        </div>

                        <!-- Update Task Form Card -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h4 class="card-title"> 
                                                Update Task #${task.getTaskId()}
                                            </h4>
                                            <div>
                                                <a href="taskDetail?id=${task.getTaskId()}" class="btn btn-secondary btn-sm">
                                                    <i class="fas fa-arrow-left"></i> Back to Task Details
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <form action="updateTask" method="POST">
                                            <input type="hidden" name="id" value="${task.getTaskId()}">
                                            <!-- Basic Task Information -->
                                            <div class="card shadow-sm mb-4">
                                                <div class="card-header bg-light">
                                                    <h5 class="card-subtitle mb-0">Basic Information</h5>
                                                </div>
                                                <div class="card-body">
                                                    <c:choose>
                                                        <c:when test="${account.getUser_id() == task.getAssignerId() || Permission eq 'true'}">
                                                            <div class="row">
                                                                <div class="col-md-12 mb-3">
                                                                    <label for="title" class="form-label fw-bold">Task Title <span class="text-danger">*</span></label>
                                                                    <input type="text" class="form-control" id="title" name="title" value="${task.getTitle()}" required>
                                                                    <%
     String errorTitle = (String) session.getAttribute("errorTitle");
     if (errorTitle != null) {
                                                                    %>
                                                                    <div class="text-danger mt-1">${errorTitle}</div>
                                                                    <%
             session.removeAttribute("errorTitle");
         }
                                                                    %>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="club" class="form-label fw-bold">Club <span class="text-danger">*</span></label>
                                                                    <input type="text" class="form-control" id="club" name="club" value="${task.getClubname()}" readonly>    
                                                                </div>
                                                                <div class="col-md-6 mb-3">
                                                                    <c:if test="${task.getType() == 'Event'}">
                                                                        <label for="eventId" class="form-label fw-bold">Event <span class="text-danger">*</span></label>
                                                                    </c:if>
                                                                    <c:if test="${task.getType() == 'Project'}">
                                                                        <label for="eventId" class="form-label fw-bold">Project <span class="text-danger">*</span></label>
                                                                    </c:if>
                                                                    <input type="hidden" class="form-control" id="eventId" name="eventId" value="${task.getEventId()}">    
                                                                    <select class="form-select" id="eventId" name="eventId" disabled>
                                                                        <c:forEach var="event" items="${allevent}">
                                                                            <option value="${event.getEvent_id()}">${event.getName()}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="deadline" class="form-label fw-bold">Deadline <span class="text-danger">*</span></label>
                                                                    <input type="datetime-local" class="form-control" id="deadline" name="deadline" value="${task.getDeadline()}" required>    
                                                                </div>
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="status" class="form-label fw-bold">Status <span class="text-danger">*</span></label>                                                        
                                                                    <select class="form-select" id="status" name="status" required
                                                                            ${((task.getStatus() == 'Rejected') || 
                                                                              (task.getStatus() == 'Overdue' || task.getStatus() == 'Pending' && !Permission)) ? 'disabled' : ''}>

                                                                        <c:if test="${Permission eq 'true' || task.getStatus() == 'Pending'}">
                                                                            <option value="Pending" ${task.getStatus() == 'Pending' ? 'selected' : ''}>Pending</option>
                                                                        </c:if>

                                                                        <option value="In Progress" ${task.getStatus() == 'In Progress' ? 'selected' : ''}>In Progress</option>

                                                                        <c:if test="${Permission eq 'true' || task.getStatus() == 'Overdue'}">
                                                                            <option value="Overdue" ${task.getStatus() == 'Overdue' ? 'selected' : ''}>Overdue</option>
                                                                        </c:if>

                                                                        <option value="Completed" ${task.getStatus() == 'Completed' ? 'selected' : ''}>Completed</option>
                                                                        <option value="Rejected" ${task.getStatus() == 'Rejected' ? 'selected' : ''}>Rejected</option>
                                                                    </select>   

                                                                    <c:if test="${task.getStatus() == 'Rejected' || task.getStatus() == 'Completed' || task.getStatus() == 'Pending'}">
                                                                        <input type="hidden" name="status" value="${task.getStatus()}">
                                                                    </c:if>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="row">
                                                                <div class="col-md-12 mb-3">
                                                                    <label for="title" class="form-label fw-bold">Task Title <span class="text-danger">*</span></label>
                                                                    <input style="height: 10px; padding: 3px 12px;" type="text" class="form-control" id="title" name="title" value="${task.getTitle()}" readonly>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="club" class="form-label fw-bold">Club <span class="text-danger">*</span></label>
                                                                    <input type="text" class="form-control" id="club" name="club" value="${task.getClubname()}" readonly>    
                                                                </div>
                                                                <div class="col-md-6 mb-3">
                                                                    <c:if test="${task.getType() == 'Event'}">
                                                                        <label for="eventId" class="form-label fw-bold">Event <span class="text-danger">*</span></label>
                                                                    </c:if>
                                                                    <c:if test="${task.getType() == 'Project'}">
                                                                        <label for="eventId" class="form-label fw-bold">Project <span class="text-danger">*</span></label>
                                                                    </c:if>                                                                    <select class="form-select" id="eventId" name="eventId" disabled>
                                                                        <c:forEach var="event" items="${allevent}">
                                                                            <option value="${event.getEvent_id()}">${event.getName()}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                    <input type="hidden" class="form-control" id="eventId" name="eventId" value="${task.getEventId()}">    
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="deadline" class="form-label fw-bold">Deadline <span class="text-danger">*</span></label>
                                                                    <input type="datetime-local" class="form-control" id="deadline" name="deadline" value="${task.getDeadline()}" readonly>    
                                                                </div>
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="status" class="form-label fw-bold">Status <span class="text-danger">*</span></label>                                                        
                                                                    <select class="form-select" id="status" name="status" required
                                                                            ${(!Permission && task.getStatus() == 'Pending') ? 'disabled' : ''}>
                                                                        <c:if test="${Permission eq 'true' || task.getStatus() == 'Pending'}">
                                                                            <option value="Pending" ${task.getStatus() == 'Pending' ? 'selected' : ''}>Pending</option>
                                                                        </c:if>
                                                                        <option value="In Progress" ${task.getStatus() == 'In Progress' ? 'selected' : ''}>In Progress</option>
                                                                        <option value="Completed" ${task.getStatus() == 'Completed' ? 'selected' : ''}>Completed</option>
                                                                        <option value="Overdue" ${task.getStatus() == 'Overdue' ? 'selected' : ''}>Overdue</option>
                                                                    </select>   
                                                                    <c:if test="${Permission eq 'false' && task.getStatus() == 'Pending'}">
                                                                        <input type="hidden" name="status" value="${task.getStatus()}">
                                                                    </c:if>
                                                                </div> 
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <c:choose>
                                                    <c:when test="${account.getUser_id() == task.getAssignerId() || Permission eq 'true'}">
                                                        <div class="row">                                                           
                                                            <div class="col-md-12 mb-3">
                                                                <label for="description" class="form-label fw-bold">Description <span class="text-danger">*</span></label>
                                                                <textarea style="width: 96%; margin-left: 20px" class="form-control" id="description" name="description" rows="4" required>${task.getDescription()}</textarea>
                                                                <%
    String errorTitle = (String) session.getAttribute("errordescription");%>
                                                                <div class="text-danger mt-1">${errordescription}</div>
                                                                <%if (errorTitle != null) {
                                                                session.removeAttribute("errordescription");
                                                                }
                                                                %>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="row">                                                           
                                                            <div class="col-md-12 mb-3">
                                                                <label for="description" class="form-label fw-bold">Description <span class="text-danger">*</span></label>
                                                                <textarea style="width: 96%; margin-left: 20px" class="form-control" id="description" name="description" rows="4" readonly>${task.getDescription()}</textarea>
                                                            </div>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>  
                                            <!-- Assignment Information -->
                                            <div class="card shadow-sm mb-4">
                                                <div class="card-header bg-light">
                                                    <h5 class="card-subtitle mb-0">Assignment Information</h5>
                                                </div>
                                                <div class="card-body">
                                                    <div class="row">
                                                        <c:choose>
                                                            <c:when test="${Permission eq 'true'}">
                                                                <div class="col-md-6 mb-3">
                                                                    <label for="assignerId" class="form-label fw-bold">Assigner <span class="text-danger">*</span></label>
                                                                    <select class="form-select" id="assignerId" name="assignerId" required>
                                                                        <option value="${task.getAssignerId()}">${task.getAssignerName()}</option>
                                                                        <c:forEach var="staff" items="${alluser}">
                                                                            <option value="${staff.getUserId()}">${staff.getFullname()}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="col-md-6 mb-3">
                                                                        <label for="assignerId" class="form-label fw-bold">Assigner <span class="text-danger">*</span></label>
                                                                        <select class="form-select" id="assignerId" name="assignerId" disabled>
                                                                            <option value="${task.getAssignerId()}">${task.getAssignerName()}</option>
                                                                            <c:forEach var="staff" items="${alluser}">
                                                                                <option value="${staff.getUserId()}">${staff.getFullname()}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                        <input type="hidden" class="form-control" id="assignerId" name="assignerId" value="${task.getAssignerId()}">    
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <c:choose>
                                                                <c:when test="${account.getUser_id() == task.getAssignerId() || Permission eq 'true'}">
                                                                    <div class="col-md-6 mb-3">
                                                                        <label for="assigneeId" class="form-label fw-bold">Assignee <span class="text-danger">*</span></label>
                                                                        <select class="form-select" id="assigneeId" name="assigneeId" required>
                                                                            <option value="${task.getAssigneeId()}">${task.getAssigneeName()}</option>
                                                                            <c:forEach var="staff" items="${alluser1}">
                                                                                <option value="${staff.getUserId()}">${staff.getFullname()}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div class="col-md-6 mb-3">
                                                                        <label for="assigneeId" class="form-label fw-bold">Assignee <span class="text-danger">*</span></label>
                                                                        <select class="form-select" id="assigneeId" name="assigneeId" disabled>
                                                                            <option value="${task.getAssigneeId()}">${task.getAssigneeName()}</option>
                                                                            <c:forEach var="staff" items="${alluser1}">
                                                                                <option value="${staff.getUserId()}">${staff.getFullname()}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                        <input type="hidden" class="form-control" id="assigneeId" name="assigneeId" value="${task.getAssigneeId()}">    
                                                                    </div>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-12 mb-3">
                                                                <label for="note" class="form-label fw-bold">Note <span class="text-danger">*</span></label>
                                                                <textarea class="form-control" id="note" name="note" rows="4" required>${task.getNote()}</textarea>
                                                                <%
        String errorTitle = (String) session.getAttribute("errornote");
        if (errorTitle != null) {
                                                                %>
                                                                <div class="text-danger mt-1">${errornote}</div>
                                                                <%
        session.removeAttribute("errornote"); 
        }
                                                                %>

                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-12 text-end">
                                                                <button type="submit" class="btn btn-primary">
                                                                    <i class="fas fa-save"></i> Save Changes
                                                                </button>
                                                                <a href="taskDetail?id=${task.getTaskId()}" class="btn btn-secondary">
                                                                    <i class="fas fa-times"></i> Cancel
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Submit Button -->
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
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Initialize modal
                var myModal = new bootstrap.Modal(document.getElementById('addTaskModal'));

                // Initialize select boxes with Select2 if available
                if (typeof $.fn.select2 !== 'undefined') {
                    $('#eventId, #assigneeId, #status').select2({
                        dropdownParent: $('#addTaskModal')
                    });
                }

                // Initialize datetime picker if flatpickr is available
                if (typeof flatpickr !== 'undefined') {
                    flatpickr("#deadline", {
                        enableTime: true,
                        dateFormat: "Y-m-d H:i",
                    });
                }
            });

            // Function for search by name
            function searchByName(input) {
                // Implement your search function here if needed
            }
        </script>

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