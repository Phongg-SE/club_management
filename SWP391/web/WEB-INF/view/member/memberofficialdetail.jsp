<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <!--header-->
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/tiny-slider.css" />

        <!-- Favicon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico.png">

        <!-- Components -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/simplebar.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/select2.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flatpickr.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery.timepicker.min.css" />

        <!-- Icons -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/materialdesignicons.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/remixicon.css" />
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v3.0.6/css/line.css" />

        <!-- Main Style -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.min.css" id="theme-opt" />

    </head>

    <body>

        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->
                <div class="main-panel">
                    <div class="header-bar d-flex justify-content-between border-bottom">
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
                                    <a class="dropdown-item text-dark" href="userprofile"><span class="mb-0 d-inline-block me-1"><i class="uil uil-setting align-middle h6"></i></span> Profile Settings</a>
                                    <div class="dropdown-divider border-top"></div>
                                    <a class="dropdown-item text-dark" href="logout"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>

                <div class="container-fluid">
                    <div class="d-md-flex justify-content-between">
                        <h5 class="mb-0">Member Profile & Settings</h5>
                        <nav aria-label="breadcrumb" class="d-inline-block mt-4 mt-sm-0">
                            <ul class="breadcrumb bg-transparent rounded mb-0 p-0">
                                <li class="breadcrumb-item"><a href="clublist">Club</a></li>
                                <li class="breadcrumb-item"><a href="memberofficiallist">Member</a></li>
                                <li class="breadcrumb-item active" aria-current="#">Detail</li>
                            </ul>
                        </nav>
                    </div>
                    <div class="card bg-white rounded shadow overflow-hidden mt-4 border-0">
                        <div class="p-5 bg-primary bg-gradient"></div>
                        <!--header name card-->
                        <div class="avatar-profile d-flex margin-nagative mt-n5 position-relative ps-3">
                            <img src="${member.avatar_url}" class="rounded-circle shadow-md avatar avatar-medium" alt="">
                            <div class="mt-4 ms-3 pt-3">
                                <h5 class="mt-3 mb-1">${member.full_name}</h5>
                                <p class="text-muted mb-0">${member.value}</p>
                            </div>
                        </div>
                        <!--end header name card-->

                        <div class="row">
                            <div class="col-12 mt-4">
                                <div class="card border-0 rounded-0 p-4">
                                    <ul class="nav nav-pills nav-justified flex-column flex-sm-row rounded shadow overflow-hidden bg-light"
                                        id="pills-tab" role="tablist">

                                        <li class="nav-item">
                                            <a class="nav-link rounded-0 shadow-none active border-0" id="experience-tab" data-bs-toggle="pill"
                                               href="#pills-experience" role="tab" aria-controls="pills-experience"
                                               aria-selected="false">
                                                <div class="text-center pt-1 pb-1">
                                                    <h4 class="title fw-normal mb-0">Post List</h4>
                                                </div>
                                            </a><!--end nav link-->
                                        </li><!--end nav item-->

                                        <li class="nav-item">
                                            <a class="nav-link rounded-0 shadow-none  border-0" id="review-tab" data-bs-toggle="pill"
                                               href="#pills-review" role="tab" aria-controls="pills-review"
                                               aria-selected="false">
                                                <div class="text-center pt-1 pb-1">
                                                    <h4 class="title fw-normal mb-0">Task List</h4>
                                                </div>
                                            </a><!--end nav link-->
                                        </li><!--end nav item-->

                                        <li class="nav-item">
                                            <a class="nav-link rounded-0 shadow-none border-0" id="settings-tab" data-bs-toggle="pill"
                                               href="#pills-settings" role="tab" aria-controls="pills-settings"
                                               aria-selected="false">
                                                <div class="text-center pt-1 pb-1">
                                                    <h4 class="title fw-normal mb-0">Update</h4>
                                                </div>
                                            </a><!--end nav link-->
                                        </li><!--end nav item-->
                                    </ul><!--end nav pills-->

                                    <div class="tab-content mt-2" id="pills-tabContent">
                                        <!-- ✅ Tab Posts (Hiển thị dạng slide) -->
                                        <div class="tab-pane fade show active" id="pills-experience" role="tabpanel" aria-labelledby="experience-tab">
                                            <div class="row justify-content-center">
                                                <div class="col-lg-8 text-center">
                                                    <div class="client-review-slider">
                                                        <c:forEach var="post" items="${posts}">
                                                            <div class="tiny-slide text-center">
                                                                <!-- Nội dung bài viết -->
                                                                <p class="text-muted h6 fw-normal fst-italic">
                                                                    "${post.description}"
                                                                </p>

                                                                <!-- Hình ảnh đại diện -->
                                                                <img src="${post.imageUrl}"
                                                                     class="img-fluid avatar avatar-small rounded-circle mx-auto shadow my-3"
                                                                     alt="Post Image">

                                                                <!-- Tiêu đề & Ngày đăng -->
                                                                <h6 class="text-primary">
                                                                    <a href="PostDetail?post_id=${post.postId}">${post.title}</a>
                                                                    <span class="badge ${post.status eq 'Published' ? 'bg-success' : 'bg-secondary'} ms-2">
                                                                        ${post.status}
                                                                    </span>
                                                                </h6>

                                                                <!-- Ngày đăng -->
                                                                <p class="text-muted">
                                                                    <i class="ri-calendar-line"></i> Posted on: ${post.post_at}
                                                                </p>
                                                            </div><!-- end tiny-slide -->
                                                        </c:forEach>
                                                    </div><!--end client-review-slider-->
                                                </div><!--end col-->
                                            </div><!--end row-->
                                        </div><!--end tab-pane-->

                                        <!-- ✅ Tab Tasks (Hiển thị dạng danh sách như bài viết trước đây) -->
                                        <div class="tab-pane fade" id="pills-review" role="tabpanel" aria-labelledby="review-tab">
                                            <h5 class="mb-3 fw-bold">Task List</h5>

                                            <div id="task-list-container"> <!-- ✅ Bọc toàn bộ danh sách task -->
                                                <c:forEach var="task" items="${tasks}">
                                                    <div class="task p-3 mb-3 border rounded shadow-sm bg-light"> <!-- ✅ Thêm class task -->
                                                        <!-- Task Title -->
                                                        <h4 class="text-primary fw-bold mb-2">${task.title}</h4>

                                                        <!-- Deadline & Status -->
                                                        <p class="text-muted mb-3">
                                                            <i class="ri-calendar-line"></i> <strong>Deadline:</strong> ${task.deadline}
                                                            <span class="badge ${task.status eq 'Completed' ? 'bg-success' : 'bg-warning'} ms-3">
                                                                ${task.status}
                                                            </span>
                                                        </p>

                                                        <!-- Assigner & Assignee Info -->
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <p class="text-secondary fw-bold">Assigned by:</p>
                                                                <p class="mb-1"><strong>Name:</strong> ${task.assignerName}</p>
                                                                <p class="mb-1"><strong>Email:</strong> ${task.assignerEmail}</p>
                                                                <p class="mb-3"><strong>Role:</strong> ${task.assignerRole}</p>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <p class="text-secondary fw-bold">Assigned to:</p>
                                                                <p class="mb-1"><strong>Name:</strong> ${task.assigneeName}</p>
                                                                <p class="mb-1"><strong>Email:</strong> ${task.assigneeEmail}</p>
                                                                <p class="mb-3"><strong>Role:</strong> ${task.assigneeRole}</p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>

                                            <div id="task-pagination-container" class="mt-4"></div> <!-- ✅ Đặt pagination đúng chỗ -->
                                        </div> <!--end tab-pane-->

                                        <div class="tab-pane fade" id="pills-settings" role="tabpanel" aria-labelledby="settings-tab">
                                            <c:if  test="${userRole eq 'Club Leader' and leaderRole ne 'Club Leader'}" >
                                                <div class="row">
                                                    <h5 class="mb-3">Update Member Information</h5>
                                                    <div class="col-lg-6">
                                                        <div class="card shadow-sm">
                                                            <div class="card-header bg-primary text-white text-center">
                                                                <h6 class="mb-0">Personal Information</h6>
                                                            </div>

                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <!-- Full Name -->
                                                                    <div class="col-md-12">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Full Name</label>
                                                                            <input type="text" class="form-control" value="${member.full_name}" readonly>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Gender -->
                                                                    <div class="col-md-6">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Gender</label>
                                                                            <input type="text" class="form-control" value="${member.gender}" readonly>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Date of Birth -->
                                                                    <div class="col-md-6">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Date of Birth</label>
                                                                            <input type="text" class="form-control" value="${member.date_of_birth}" readonly>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Email -->
                                                                    <div class="col-md-12">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Email</label>
                                                                            <input type="email" class="form-control" value="${member.email}" readonly>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Phone Number -->
                                                                    <div class="col-md-12">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Phone No.</label>
                                                                            <input type="text" class="form-control" value="${member.phone_number}" readonly>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Address -->
                                                                    <div class="col-md-12">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Address</label>
                                                                            <input type="text" class="form-control" value="${member.address}, ${member.district}, ${member.city}" readonly>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Social Contact -->
                                                                    <div class="col-md-12">
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Social Contact</label>
                                                                            <div class="d-flex align-items-center gap-3">
                                                                                <a href="${member.facebook}" target="_blank" class="btn btn-outline-primary">
                                                                                    <i class="bi bi-facebook"></i> Facebook
                                                                                </a>
                                                                                <a href="${member.instargram}" target="_blank" class="btn btn-outline-danger">
                                                                                    <i class="bi bi-instagram"></i> Instagram
                                                                                </a>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                </div>
                                                            </div>
                                                        </div> <!-- End Card -->
                                                    </div> <!-- End col-lg-6 -->

                                                    <!-- Form Update Role & Status -->
                                                    <div class="col-lg-6">
                                                        <div class="card shadow-sm">
                                                            <div class="card-header bg-secondary text-white text-center">
                                                                <h6 class="mb-0">Update Role & Status</h6>
                                                            </div>
                                                            <div class="card-body">
                                                                <form method="post" action="memberofficialdetail" id="updateForm">
                                                                    <input type="hidden" name="id" value="${member.user_id}">
                                                                    <input type="hidden" name="club_id" value="${selectedClub}">
                                                                    <div class="mb-3">
                                                                        <label class="form-label">Roles</label>
                                                                        <div class="form-check">
                                                                            <input class="form-check-input" type="checkbox" id="role_2" name="roles" value="2"
                                                                                   <c:if test="${fn:contains(member.role_ids, '2')}">checked</c:if>>
                                                                                   <label class="form-check-label" for="role_2">Event Leader</label>
                                                                            </div>

                                                                            <div class="form-check">
                                                                                <input class="form-check-input" type="checkbox" id="role_3" name="roles" value="3" checked disabled>
                                                                                <label class="form-check-label" for="role_3">Club Member</label>
                                                                            </div>
                                                                            <input type="hidden" name="roles" value="3">


                                                                            <div class="form-check">
                                                                                <input class="form-check-input" type="checkbox" id="role_5" name="roles" value="5"
                                                                                <c:if test="${fn:contains(member.role_ids, '5')}">checked</c:if>>
                                                                                <label class="form-check-label" for="role_5">Project Leader</label>
                                                                            </div>
                                                                        </div>
                                                                        <div class="mb-3">
                                                                            <label class="form-label">Status</label>
                                                                            <div>
                                                                                <div class="form-check form-check-inline">
                                                                                    <input class="form-check-input" type="radio" id="activeStatus" name="status" value="Active" ${member.status == 'Active' ? 'checked' : ''} required>
                                                                                <label class="form-check-label" for="activeStatus">Active</label>
                                                                            </div>
                                                                            <div class="form-check form-check-inline">
                                                                                <input class="form-check-input" type="radio" id="inactiveStatus" name="status" value="Inactive" ${member.status == 'Inactive' ? 'checked' : ''} required>
                                                                                <label class="form-check-label" for="inactiveStatus">Inactive</label>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <!-- Submit Button -->
                                                                    <div class="text-center">
                                                                        <button type="button" class="btn btn-success px-4" data-bs-toggle="modal" data-bs-target="#confirmUpdateModal">
                                                                            Save Changes
                                                                        </button>
                                                                    </div>
                                                                </form>

                                                                <!-- Modal Xác nhận -->
                                                                <div class="modal fade" id="confirmUpdateModal" tabindex="-1" aria-labelledby="confirmUpdateModalLabel" aria-hidden="true">
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <h5 class="modal-title" id="confirmUpdateModalLabel">Xác nhận cập nhật</h5>
                                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                Bạn có chắc muốn cập nhật thông tin thành viên này không?
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                                <button type="button" class="btn btn-success" id="confirmUpdateBtn">Xác nhận</button> 
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <c:if test="${userRole ne 'Club Leader' and leaderRole ne 'Club Leader'}">
                                                    <div class="row">
                                                        <div class="col-lg-12">
                                                            <div class="card shadow-sm">
                                                                <div class="card-body">
                                                                    <div class="row">
                                                                        <!-- Full Name -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Full Name</label>
                                                                                <input type="text" class="form-control" value="${member.full_name}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Gender -->
                                                                        <div class="col-md-6">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Gender</label>
                                                                                <input type="text" class="form-control" value="${member.gender}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Date of Birth -->
                                                                        <div class="col-md-6">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Date of Birth</label>
                                                                                <input type="text" class="form-control" value="${member.date_of_birth}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Email -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Email</label>
                                                                                <input type="email" class="form-control" value="${member.email}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Phone Number -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Phone No.</label>
                                                                                <input type="text" class="form-control" value="${member.phone_number}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Address -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Address</label>
                                                                                <input type="text" class="form-control" value="${member.address}, ${member.district}, ${member.city}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Social Contact -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Social Contact</label>
                                                                                <div class="d-flex align-items-center gap-3">
                                                                                    <a href="${member.facebook}" target="_blank" class="btn btn-outline-primary">
                                                                                        <i class="bi bi-facebook"></i> Facebook
                                                                                    </a>
                                                                                    <a href="${member.instargram}" target="_blank" class="btn btn-outline-danger">
                                                                                        <i class="bi bi-instagram"></i> Instagram
                                                                                    </a>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </div> <!-- End Card -->
                                                        </div> <!-- End col-lg-6 -->
                                                    </div>
                                                </c:if>

                                                <c:if test="${userRole ne 'Club Leader' and leaderRole eq 'Club Leader'}">
                                                    <div class="row">
                                                        <div class="col-lg-12">
                                                            <div class="card shadow-sm">
                                                                <div class="card-body">
                                                                    <div class="row">
                                                                        <!-- Full Name -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Full Name</label>
                                                                                <input type="text" class="form-control" value="${member.full_name}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Gender -->
                                                                        <div class="col-md-6">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Gender</label>
                                                                                <input type="text" class="form-control" value="${member.gender}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Date of Birth -->
                                                                        <div class="col-md-6">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Date of Birth</label>
                                                                                <input type="text" class="form-control" value="${member.date_of_birth}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Email -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Email</label>
                                                                                <input type="email" class="form-control" value="${member.email}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Phone Number -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Phone No.</label>
                                                                                <input type="text" class="form-control" value="${member.phone_number}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Address -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Address</label>
                                                                                <input type="text" class="form-control" value="${member.address}, ${member.district}, ${member.city}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Social Contact -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Social Contact</label>
                                                                                <div class="d-flex align-items-center gap-3">
                                                                                    <a href="${member.facebook}" target="_blank" class="btn btn-outline-primary">
                                                                                        <i class="bi bi-facebook"></i> Facebook
                                                                                    </a>
                                                                                    <a href="${member.instargram}" target="_blank" class="btn btn-outline-danger">
                                                                                        <i class="bi bi-instagram"></i> Instagram
                                                                                    </a>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                            </div> <!-- End Card -->
                                                        </div> <!-- End col-lg-6 -->
                                                    </div>
                                                </c:if>

                                                <c:if test="${userRole eq 'Club Leader' and leaderRole eq 'Club Leader'}">
                                                    <div class="row">
                                                        <div class="col-lg-12">
                                                            <div class="card shadow-sm">
                                                                <div class="card-body">
                                                                    <div class="row">
                                                                        <!-- Full Name -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Full Name</label>
                                                                                <input type="text" class="form-control" value="${member.full_name}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Gender -->
                                                                        <div class="col-md-6">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Gender</label>
                                                                                <input type="text" class="form-control" value="${member.gender}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Date of Birth -->
                                                                        <div class="col-md-6">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Date of Birth</label>
                                                                                <input type="text" class="form-control" value="${member.date_of_birth}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Email -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Email</label>
                                                                                <input type="email" class="form-control" value="${member.email}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Phone Number -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Phone No.</label>
                                                                                <input type="text" class="form-control" value="${member.phone_number}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Address -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Address</label>
                                                                                <input type="text" class="form-control" value="${member.address}, ${member.district}, ${member.city}" readonly>
                                                                            </div>
                                                                        </div>

                                                                        <!-- Social Contact -->
                                                                        <div class="col-md-12">
                                                                            <div class="mb-3">
                                                                                <label class="form-label">Social Contact</label>
                                                                                <div class="d-flex align-items-center gap-3">
                                                                                    <a href="${member.facebook}" target="_blank" class="btn btn-outline-primary">
                                                                                        <i class="bi bi-facebook"></i> Facebook
                                                                                    </a>
                                                                                    <a href="${member.instargram}" target="_blank" class="btn btn-outline-danger">
                                                                                        <i class="bi bi-instagram"></i> Instagram
                                                                                    </a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div> <!-- End Card -->
                                                        </div> <!-- End col-lg-6 -->
                                                    </div>
                                                </c:if>
                                            </div> <!-- End col-lg-6 -->
                                        </div> <!-- End row -->
                                        <h6 class="mb-0">Other Member: </h6>
                                        <div class="row row-cols-md-2 row-cols-lg-5">
                                            <c:forEach var="member" items="${randomMembers}">
                                                <div class="col mt-4">
                                                    <div class="card team border-0 rounded shadow overflow-hidden">
                                                        <div class="team-person position-relative overflow-hidden">
                                                            <img src="${member.avatar_url}" class="img-fluid" alt="Profile Picture">
                                                        </div>
                                                        <div class="card-body">
                                                            <!-- Tên thành viên -->
                                                            <a href="memberofficialdetail?id=${member.user_id}&club_id=${selectedClubId}" class="title text-dark h5 d-block mb-1">
                                                                ${member.full_name}
                                                            </a>

                                                            <!-- Gender & Role -->
                                                            <div class="d-flex align-items-center mt-2">
                                                                <!-- Gender sát trái -->
                                                                <span class="badge ${member.gender eq 'Male' ? 'bg-primary' : 'bg-danger text-white'} me-2">
                                                                    ${member.gender}
                                                                </span>
                                                                <!-- Role tự dài ra -->
                                                                <span class="badge bg-secondary flex-grow-1">
                                                                    ${member.value}
                                                                </span>
                                                            </div>

                                                            <ul class="list-unstyled mt-3 mb-0">
                                                                <!-- Status -->
                                                                <li class="d-flex align-items-center">
                                                                    <i class="ri-check-double-line text-success"></i>
                                                                    <small class="text-muted ms-2">${member.status}</small>
                                                                </li>

                                                                <!-- Address -->
                                                                <li class="d-flex align-items-center mt-2">
                                                                    <i class="ri-map-pin-line text-danger"></i>
                                                                    <small class="text-muted ms-2">Address</small>
                                                                </li>

                                                                <!-- Email -->
                                                                <li class="d-flex align-items-center mt-2">
                                                                    <i class="ri-mail-line text-primary"></i>
                                                                    <a href="mailto:${member.email}" class="text-muted ms-2">${member.email}</a>
                                                                </li>
                                                            </ul>

                                                            <!-- Nút Xem Chi Tiết -->
                                                            <a href="memberofficialdetail?id=${member.user_id}" class="btn btn-outline-primary btn-sm mt-3">
                                                                <i class="ri-user-line"></i> View Profile
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div><!--end col-->
                                            </c:forEach>
                                        </div><!--end row-->
                                    </div> <!-- End tab-pane -->
                                </div><!--end col-->
                            </div><!--end row-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Core JS Files -->
<script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

<!-- Plugins -->
<script src="${pageContext.request.contextPath}/assets/js/simplebar.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/plugin/datatables/datatables.min.js"></script>

<!-- Slider -->
<script src="${pageContext.request.contextPath}/assets/js/tiny-slider.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/tiny-slider-init.js"></script>

<!-- Icons -->
<script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>

<!-- Main Scripts -->
<script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>

<!--paging-->
<script>
            document.addEventListener("DOMContentLoaded", function () {
                const itemsPerPage = 3; // Số lượng task hiển thị trên mỗi trang
                const tasks = document.querySelectorAll(".task");
                const totalPages = Math.ceil(tasks.length / itemsPerPage);
                let currentPage = 1;

                function showPage(page) {
                    tasks.forEach((task, index) => {
                        task.style.display = (index >= (page - 1) * itemsPerPage && index < page * itemsPerPage) ? "block" : "none";
                    });
                }

                function createPagination() {
                    if (totalPages <= 1)
                        return; // Không tạo phân trang nếu chỉ có 1 trang

                    const pagination = document.createElement("ul");
                    pagination.className = "pagination justify-content-center mt-4";

                    for (let i = 1; i <= totalPages; i++) {
                        const li = document.createElement("li");
                        li.className = "page-item";
                        const a = document.createElement("a");
                        a.className = "page-link";
                        a.href = "#";
                        a.textContent = i;

                        a.addEventListener("click", function (e) {
                            e.preventDefault();
                            currentPage = i;
                            showPage(currentPage);
                            updateActivePage();
                        });

                        li.appendChild(a);
                        pagination.appendChild(li);
                    }

                    document.querySelector("#task-pagination-container").appendChild(pagination);
                }

                function updateActivePage() {
                    document.querySelectorAll(".pagination .page-item").forEach((item, index) => {
                        item.classList.toggle("active", index + 1 === currentPage);
                    });
                }

                showPage(currentPage);
                createPagination();
                updateActivePage();
            });
</script>

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
<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("confirmUpdateBtn").addEventListener("click", function () {
            document.getElementById("updateForm").submit();
        });
    });
</script>
</body>
</html>
