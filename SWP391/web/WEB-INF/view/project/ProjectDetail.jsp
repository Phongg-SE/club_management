<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Project Detail</title>
        <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
        <link rel="icon" href="assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

        <!-- Fonts and icons -->
        <script src="assets/js/plugin/webfont/webfont.min.js"></script>
        <script>
            WebFont.load({
                google: {families: ["Public Sans:300,400,500,600,700"]},
                custom: {
                    families: [
                        "Font Awesome 5 Solid",
                        "Font Awesome 5 Regular",
                        "Font Awesome 5 Brands",
                        "simple-line-icons"
                    ],
                    urls: ["assets/css/fonts.min.css"]
                },
                active: function () {
                    sessionStorage.fonts = true;
                }
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->

                <div class="main-panel">
                    <div class="container">
                        <div class="page-inner">
                            <div class="page-header">
                                <h3 class="fw-bold mb-3">Account Details</h3>
                                <ul class="breadcrumbs mb-3">
                                    <li class="nav-home">
                                        <a href="home"><i class="icon-home"></i></a>
                                    </li>
                                    <li class="separator"><i class="icon-arrow-right"></i></li>
                                    <li class="nav-item"><a href="ProjectList">Project Management</a></li>
                                    <li class="separator"><i class="icon-arrow-right"></i></li>
                                    <li class="nav-item"><a href="#">Project Details</a></li>
                                </ul>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <h4 class="card-title">Project Details</h4>
                                        </div>
                                        <!-- Tabs Navigation -->
                                        <ul class="nav nav-tabs" id="projectTabs">
                                            <li class="nav-item">
                                                <a class="nav-link active" id="details-tab" data-bs-toggle="tab" href="#details">Project Details</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" id="members-tab" data-bs-toggle="tab" href="#members">Project Members</a>
                                            </li>
                                        </ul>

                                        <!-- Tabs Content -->
                                        <div class="tab-content mt-3">
                                            <!-- Project Details Tab -->
                                            <div class="tab-pane fade show active" id="details">
                                                <div class="card">
                                                    <div class="card-body">
                                                        <h4 style="color: red">${msg}</h4>

                                                    <form action="UpdateProject" method="post">

                                                        <input type="hidden" name="project_id" value="${event.event_id}" />

                                                        <!-- Project Name -->
                                                        <div class="form-group mb-3">
                                                            <label for="projectName" class="form-label">Project Name</label>
                                                            <input type="text" class="form-control" id="projectName" name="name" value="${event.name}" />
                                                            <c:if test="${not empty errors.name}">
                                                                <div class="invalid-feedback d-block">${errors.name}</div>
                                                            </c:if> 

                                                        </div>


                                                        <%-- Start Date --%>
                                                        <div class="form-group mb-3">
                                                            <label for="startDate" class="form-label">Start Date</label>
                                                            <input type="date" class="form-control" id="startDate" name="start_time"
                                                                   value="${not empty event.start_time ? 
                                                                            event.start_time : ''}" />
                                                            <c:if test="${not empty errors.start_time}">
                                                                <div class="invalid-feedback d-block">${errors.start_time}</div>
                                                            </c:if>
                                                        </div>

                                                        <%-- End Date --%>
                                                        <div class="form-group mb-3">
                                                            <label for="endDate" class="form-label">End Date</label>
                                                            <input type="date" class="form-control" id="endDate" name="end_time"
                                                                   value="${not empty event.end_time ? 
                                                                            event.end_time : ''}" />
                                                        </div>

                                                        <!-- Project Leader -->

                                                        <div class="form-group mb-3">
                                                            <label for="leader" class="form-label">Project Leader</label>

                                                            <!-- Dropdown chọn leader -->
                                                            <select class="form-control" id="leader" name="leader_id">
                                                                <c:forEach var="member" items="${projectMembers}">
                                                                    <option value="${member.user_id}" ${member.user_id == event.leader_id ? 'selected' : ''}>
                                                                        ${member.full_name}
                                                                    </option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <input type="hidden" class="form-control" id="club_id" name="club_id" value="${not empty event.club_id ? event.club_id : oldEvent.club_id}" readonly />

                                                        <div class="form-group mb-3">

                                                            <label for="createdDate" class="form-label">Created Date</label>



                                                            <input type="text" class="form-control" id="createdDate" value="${event.created_date}" readonly>
                                                        </div>
                                                        <!-- Location -->
                                                        <div class="form-group mb-3">
                                                            <label for="location" class="form-label">Location</label>
                                                            <input type="text" class="form-control" id="location" name="location" value="${event.location}" />
                                                        </div>

                                                        <!-- Status -->
                                                        <div class="form-group mb-3">
                                                            <label class="form-label d-block">Status</label>

                                                            <div class="form-check form-check-inline">
                                                                <input class="form-check-input" type="radio" id="planned" name="status" value="Planned" 
                                                                       ${event.status == 'Planned' ? 'checked' : ''}>
                                                                <label class="form-check-label" for="planned">Planned</label>
                                                            </div>

                                                            <div class="form-check form-check-inline">
                                                                <input class="form-check-input" type="radio" id="ongoing" name="status" value="Ongoing" 
                                                                       ${event.status == 'Ongoing' ? 'checked' : ''}>
                                                                <label class="form-check-label" for="ongoing">Ongoing</label>
                                                            </div>

                                                            <div class="form-check form-check-inline">
                                                                <input class="form-check-input" type="radio" id="completed" name="status" value="Completed" 
                                                                       ${event.status == 'Completed' ? 'checked' : ''}>
                                                                <label class="form-check-label" for="completed">Completed</label>
                                                            </div>
                                                        </div>


                                                        <!-- Description -->
                                                        <div class="form-group mb-3">
                                                            <label for="description" class="form-label">Description</label>
                                                            <textarea class="form-control" id="description" name="description">${event.description} </textarea>
                                                            <c:if test="${not empty errors.description}">
                                                                <div class="invalid-feedback d-block">${errors.description}</div>
                                                            </c:if> 
                                                        </div>

                                                        <!-- Submit Button -->
                                                        <div class="form-group text-begin">
                                                            <button type="submit" class="btn btn-primary">Save</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Project Members Tab -->
                                        <div class="d-flex justify-content-end" style="margin-right: 40px;">
                                            <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addMemberModal">
                                                Add Member
                                            </button>
                                        </div>


                                        <!-- Modal để chọn thành viên -->
                                        <div class="modal fade" id="addMemberModal" tabindex="-1" aria-labelledby="addMemberModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="addMemberModalLabel">Add New Member</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="AddProjectMember" method="post">
                                                            <div class="mb-3">
                                                                <label for="userId" class="form-label">Select Member</label>
                                                                <select class="form-control" id="userid" name="user_id" required>
                                                                    <c:forEach var="member" items="${clubMembers}">
                                                                        <option value="${member.user_id}">${member.full_name} - ${member.email}</option>

                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <input type="hidden" name="event_id" value="${event.event_id}">
                                                            <input type="hidden" name="status" value="Active"> <!-- Mặc định trạng thái là Active -->
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn btn-primary">Add Member</button>
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="tab-pane fade" id="members">
                                            <div class="card">
                                                <div class="card-body">
                                                    <h4>Project Members</h4>

                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th>Member ID</th>
                                                                <th>Status</th>
                                                                <th>Name</th>
                                                                <th>Email</th>

                                                                <th>Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="member" items="${projectMembers}">
                                                                <tr>
                                                                    <td>${member.member_id}</td>

                                                                    <td>${member.status}</td>
                                                                    <td>${member.full_name}</td>
                                                                    <td>${member.email}</td>

                                                                    <td>
                                                                        <form action="DeleteProjectMember" method="post" onsubmit="return confirm('Bạn có chắc chắn muốn xóa thành viên này?');">
                                                                            <input type="hidden" name="member_id" value="${member.member_id}">
                                                                            <input type="hidden" name="event_Id" value="${member.event_id}">
                                                                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                                                        </form>
                                                                    </td>     
                                                                </tr>
                                                            </c:forEach>

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Bootstrap 5 Required JavaScript -->
                                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>



                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <script>
                                                                            function previewAvatar(event) {
                                                                                var reader = new FileReader();
                                                                                reader.onload = function () {
                                                                                    var output = document.getElementById('avatarPreview');
                                                                                    output.src = reader.result;
                                                                                };
                                                                                reader.readAsDataURL(event.target.files[0]);
                                                                            }
                </script>
                <footer class="footer">
                    <div class="container-fluid d-flex justify-content-between">
                        <nav class="pull-left">
                            <ul class="nav">
                                <li class="nav-item"><a class="nav-link" href="http://www.themekita.com">ThemeKita</a></li>
                                <li class="nav-item"><a class="nav-link" href="#">Help</a></li>
                                <li class="nav-item"><a class="nav-link" href="#">Licenses</a></li>
                            </ul>
                        </nav>
                        <div class="copyright">
                            2024, made with <i class="fa fa-heart heart text-danger"></i> by <a href="http://www.themekita.com">ThemeKita</a>.
                        </div>
                        <div>
                            Distributed by <a target="_blank" href="https://themewagon.com/">ThemeWagon</a>.
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <!-- JS Files -->
        <script src="assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>
    </body>
</html>
