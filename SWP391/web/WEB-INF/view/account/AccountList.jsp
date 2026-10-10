
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Club Manager</title>
        <meta
            content="width=device-width, initial-scale=1.0, shrink-to-fit=no"
            name="viewport"
            />
        <link
            rel="icon"
            href="assets/img/kaiadmin/favicon.ico"
            type="image/x-icon"
            />

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

        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="assets/css/demo.css" />
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
                                    <li class="nav-item"><a href="AccountList">Accounts Management</a></li>
                                </ul>
                            </div>
                            <!-- Toast Thông Báo Thành Công -->
                            <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 1050">
                                <div id="successToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                                    <div class="d-flex">
                                        <div class="toast-body">
                                            Add successfully!!
                                        </div>
                                        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                                    </div>
                                </div>
                            </div>


                            <!-- Products Table Card -->
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h4 class="card-title">

                                                </h4>
                                                <a href="" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#addAccountModal">
                                                    <i class="fas fa-plus"></i> Add Account
                                                </a>
                                            </div>
                                        </div>
                                        <!-- Bộ lọc -->

                                        <div class="card-body">
                                            <form id="filterForm" method="GET" action="AccountList">
                                                <div class="row">
                                                    <!-- Search by Username -->
                                                    <div class="col-md-4">
                                                        <label for="username">Username:</label>
                                                        <div class="input-group">
                                                            <input type="text" name="username" id="username" class="form-control" value="${param.username}">
                                                        <button type="button" class="btn btn-primary" onclick="applyFilter()">
                                                            <i class="fas fa-search"></i>
                                                        </button>
                                                    </div>
                                                </div>

                                                <!-- Filter by Role -->
                                                <div class="col-md-4">
                                                    <label for="role">Role:</label>
                                                    <select name="role" id="role" class="form-control" onchange="applyFilter()">
                                                        <option value="">-- Select Role --</option>
                                                        <option value="Admin" ${param.role == 'Admin' ? 'selected' : ''}>Admin</option>

                                                        <option value="Web User" ${param.role == 'Web User' ? 'selected' : ''}>Web User</option>
                                                    </select>
                                                </div>

                                                <!-- Filter by Status -->
                                                <div class="col-md-4">
                                                    <label for="status">Status:</label>
                                                    <select name="status" id="status" class="form-control" onchange="applyFilter()">
                                                        <option value="">--Select Status--</option>
                                                        <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                                                        <option value="Inactive" ${param.status == 'Inactive' ? 'selected' : ''}>Inactive</option>

                                                    </select>
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <script>
                                        function applyFilter() {
                                            let url = new URL(window.location.href);

                                            let username = document.getElementById('username')?.value.trim() || "";
                                            let role = document.getElementById('role')?.value || "";
                                            let status = document.getElementById('status')?.value || "";

                                            url.searchParams.set("username", username);
                                            url.searchParams.set("role", role);
                                            url.searchParams.set("status", status);
                                            url.searchParams.set("page", "1"); // Reset về trang đầu tiên khi lọc

                                            window.location.href = url.toString(); // Reload trang với bộ lọc
                                        }


                                    </script>


                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table id="" class="display table table-striped table-hover ">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Username</th>
                                                        <th>Gender</th>
                                                        <th>Gmail</th>
                                                        <th>Avatar</th>
                                                        <th>Role</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:choose>
                                                        <c:when test="${empty accounts}">
                                                            <tr>
                                                                <td colspan="7" class="text-center">No users found!</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach items="${accounts}" var="o">
                                                                <tr>
                                                                    <td>${o.user_id}</td>
                                                                    <td>${o.username}</td>
                                                                    <td>${o.gender}</td>
                                                                    <td>${o.email}</td>
                                                                    <td>
                                                                        <img src="${o.avatar_url}" alt="Avatar" width="50" height="50" style="border-radius: 50%;">

                                                                    </td>




                                                                    <td>${o.role_name}</td>
                                                                    <td>${o.status}</td>
                                                                    <td>
                                                                        <a href="accountdetail?user_Id=${o.user_id}" class="btn btn-info btn-sm mb-1">
                                                                            <i class="fas fa-edit"></i> Update
                                                                        </a>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tbody>
                                            </table>
                                        </div>

                                    </div>

                                </div>
                            </div>
                            <!-- Pagination -->
                            <div class="pagination d-flex justify-content-center mt-3">
                                <nav>
                                    <ul class="pagination">
                                        <c:if test="${currentPage > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="AccountList?page=${currentPage - 1}&status=${status}&role=${role_name}&username=${username}">Previous</a>
                                            </li>
                                        </c:if>

                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="AccountList?page=${i}&status=${status}&role=${role_name}&username=${username}">${i}</a>
                                            </li>
                                        </c:forEach>

                                        <c:if test="${currentPage < totalPages}">
                                            <li class="page-item">
                                                <a class="page-link" href="AccountList?page=${currentPage + 1}&status=${status}&role=${role_name}&username=${username}">Next</a>
                                            </li>
                                        </c:if>                                    </ul>
                                </nav>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
        </div>





        <!-- Add Account Modal -->
        <div class="modal fade" id="addAccountModal" tabindex="-1" aria-labelledby="addAccountModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addAccountModalLabel">Add New Account</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Alert hiển thị lỗi chung -->
                        <div id="errorAlert" class="alert alert-danger d-none"></div>

                        <form id="addAccountForm" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" id="username" name="username" class="form-control">
                                <small class="error-message text-danger" id="msgUsernameError"></small>
                            </div>

                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" id="fullName" name="fullName" class="form-control">
                                <small class="error-message text-danger" id="msgFullNameError"></small>
                            </div>

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" class="form-control">
                                <small class="error-message text-danger" id="msgEmailError"></small>
                            </div>

                            <div class="form-group">
                                <label for="phonenumber">Phone Number</label>
                                <input type="text" id="phonenumber" name="phonenumber" class="form-control">
                                <small class="error-message text-danger" id="msgPhoneNumberError"></small>
                            </div>

                            <div class="form-group">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option value="Active">Active</option>
                                    <option value="Inactive">Inactive</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="gender" class="form-label">Gender</label>
                                <select class="form-select" id="gender" name="gender" required>
                                    <option value="">Select Gender</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="role" class="form-label">Role</label>
                                <select class="form-select" id="role" name="role" required>
                                    <option value="">Select Role</option>
                                    <option value="1">Administrator Role</option>

                                    <option value="6">Web User Role</option>
                                </select>
                            </div>

                            <!-- Avatar Upload -->
                            <div class="form-group">
                                <label for="avatar" class="form-label">Upload Avatar</label>
                                <input type="file" class="form-control" id="avatar" name="avatar" accept="image/*">
                                <small class="error-message text-danger" id="msgAvatarError"></small>
                            </div>

                            <div class="modal-footer d-flex justify-content-end">
                                <button type="button" class="btn btn-secondary me-2" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" id="submitBtn" class="btn btn-primary">Add Account</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>



    </div>
    <!--   Core JS Files   -->
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>

    <!-- jQuery Scrollbar -->
    <script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
    <!-- Datatables -->
    <script src="assets/js/plugin/datatables/datatables.min.js"></script>
    <!-- Kaiadmin JS -->
    <script src="assets/js/kaiadmin.min.js"></script>
    <!-- Kaiadmin DEMO methods, don't include it in your project! -->
    <script src="assets/js/setting-demo2.js"></script>
    <script>
                                        $(document).ready(function () {
                                            $("#addAccountForm").off("submit").on("submit", function (event) {
                                                event.preventDefault();

                                                $("#submitBtn").prop("disabled", true);
                                                $(".error-message").text("").hide(); // Xóa lỗi cũ và ẩn đi
                                                $("#errorAlert").addClass("d-none").text("");

                                                var formData = new FormData(this);

                                                $.ajax({
                                                    type: "POST",
                                                    url: "addUser",
                                                    data: formData,
                                                    contentType: false,
                                                    processData: false,
                                                    dataType: "json",
                                                    success: function (response) {
                                                        $("#submitBtn").prop("disabled", false);

                                                        if (response.status === "success") {
                                                            $("#addAccountModal").modal("hide");
                                                            $("#addAccountForm")[0].reset();

                                                            // Hiển thị Toast thông báo thành công
                                                            const toast = new bootstrap.Toast(document.getElementById("successToast"));
                                                            $("#successToast .toast-body").text(response.message);
                                                            toast.show();

                                                            setTimeout(() => {
                                                                location.reload(true);
                                                            }, 1000);
                                                        } else {
                                                            if (response.errors) {
                                                                console.log("Errors received from server: ", response.errors);

                                                                $(".error-message").text("").hide(); // Xóa lỗi cũ

                                                                $.each(response.errors, function (field, message) {
                                                                    let errorElement = $("#msg" + field.charAt(0).toUpperCase() + field.slice(1) + "Error");

                                                                    if (errorElement.length) {
                                                                        errorElement.text(message).show(); // Hiển thị lỗi tại input
                                                                    } else {
                                                                        $("#errorAlert").removeClass("d-none").append("<p>" + message + "</p>");
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    },
                                                    error: function (xhr) {
                                                        console.log("AJAX Error: ", xhr.responseText);
                                                        $("#submitBtn").prop("disabled", false);
                                                        $("#errorAlert").removeClass("d-none").text("An unexpected error occurred. Please try again.");
                                                    }
                                                });
                                            });
                                        });

    </script>
</body>
</html>
