<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Add New Product - Coffee Shop</title>
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
                                    <li class="nav-item"><a href="AccountList">Accounts Management</a></li>
                                    <li class="separator"><i class="icon-arrow-right"></i></li>
                                    <li class="nav-item"><a href="#">Account Details</a></li>
                                </ul>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <h4 class="card-title">Account Details</h4>
                                        </div>
                                        <div class="card-body">
                                            <h4 style="color: red">${msg}</h4>
                                        <form action="UpdateAccount" method="post" enctype="multipart/form-data">
                                         <input type="hidden" name="userid" value="${not empty user_id ? user_id : (not empty user.user_id ? user.user_id : '')}" readonly />


                                            <div class="row">
                                                <!-- Username -->
                                                <div class="col-md-6">
                                                    <div class="form-group mb-3">
                                                        <label for="username" class="form-label">Username</label>
                                                        <input type="text" class="form-control ${requestScope.msgUsername != null ? 'is-invalid' : ''}" 
                                                               id="username" name="username" 
                                                               value="${requestScope.username != null ? requestScope.username : user.username}" />

                                                        <span style="color:red">${errors.usernameError}</span><br>

                                                    </div>
                                                </div>

                                                <!-- Full Name -->
                                                <div class="col-md-6">
                                                    <div class="form-group mb-3">
                                                        <label for="fullname" class="form-label">Full name</label>
                                                        <input type="text" class="form-control ${requestScope.msgFullname != null ? 'is-invalid' : ''}" 
                                                               id="fullname" name="fullname" 
                                                               value="${requestScope.fullname != null ? requestScope.fullname : user.full_name}" />

                                                        <span style="color:red">${errors.fullNameError}</span><br>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <!-- Email -->
                                                <div class="col-md-6">
                                                    <div class="form-group mb-3">
                                                        <label for="gmail" class="form-label">Gmail</label>
                                                        <input type="text" class="form-control ${requestScope.msgEmail != null ? 'is-invalid' : ''}" 
                                                               id="gmail" name="gmail" 
                                                               value="${requestScope.gmail != null ? requestScope.gmail : user.email}" />

                                                        <span style="color:red">${errors.emailError}</span><br>
                                                    </div>
                                                </div>

                                                <!-- Phone Number -->
                                                <div class="col-md-6">
                                                    <div class="form-group mb-3">
                                                        <label for="phonenumber" class="form-label">Phone number</label>
                                                        <input type="text" class="form-control ${requestScope.msgPhoneNumber != null ? 'is-invalid' : ''}" 
                                                               id="phonenumber" name="phonenumber" 
                                                               value="${requestScope.phonenumber != null ? requestScope.phonenumber : user.phone_number}" />

                                                        <span style="color:red">${errors.phoneNumberError}</span><br>
                                                    </div>
                                                </div>
                                            </div>


                                            <!-- Avatar -->
                                            <div class="form-group mb-3">
                                                <label for="avatarurl" class="form-label">Avatar</label>
                                                <br>
                                                <img id="avatarPreview" src="${requestScope.avatarUrl != null ? requestScope.avatarUrl : user.avatar_url}" 
                                                     alt="User Avatar" class="img-thumbnail" width="150">
                                                <input type="file" class="form-control mt-2" id="avatarurl" name="avatarFile" accept="image/*" onchange="previewAvatar(event)">
                                                <input type="hidden" name="avatarUrl" value="${requestScope.avatarUrl != null ? requestScope.avatarUrl : user.avatar_url}">
                                            </div>


                                            <!-- Status -->
                                            <div class="form-group mb-3">
                                                <label class="form-label d-block">Status</label>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" id="active" name="status" value="Active" 
                                                           ${requestScope.status != null ? (requestScope.status == 'Active' ? 'checked' : '') : (user.status == 'Active' ? 'checked' : '')}>
                                                    <label class="form-check-label" for="active">Active</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" id="inactive" name="status" value="Inactive" 
                                                           ${requestScope.status != null ? (requestScope.status == 'Inactive' ? 'checked' : '') : (user.status == 'Inactive' ? 'checked' : '')}>
                                                    <label class="form-check-label" for="inactive">Inactive</label>
                                                </div>
                                            </div>


                                            <!-- Gender -->
                                            <div class="form-group mb-3">
                                                <label class="form-label d-block">Gender</label>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" id="male" name="gender" value="Male" 
                                                           ${requestScope.gender == 'Male' ? 'checked' : (user.gender == 'Male' ? 'checked' : '')}>
                                                    <label class="form-check-label" for="male">Male</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" id="female" name="gender" value="Female" 
                                                           ${requestScope.gender == 'Female' ? 'checked' : (user.gender == 'Female' ? 'checked' : '')}>
                                                    <label class="form-check-label" for="female">Female</label>
                                                </div>
                                            </div>

                                            <!-- Role Name -->
                                            <div class="form-group mb-3">
                                                <label class="form-label d-block">Role Name</label>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" id="admin" name="role" value="Admin" 
                                                           ${requestScope.role == 'Admin' ? 'checked' : (user.value == 'Admin' ? 'checked' : '')} >
                                                    <label class="form-check-label" for="admin">Admin</label>

                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" id="webuser" name="role" value="Web User" 
                                                           ${requestScope.role == 'Web User' ? 'checked' : (user.value == 'Web User' ? 'checked' : '')} >
                                                    <label class="form-check-label" for="webuser">Web User</label>
                                                </div>
                                            </div>

                                            <!-- Submit Button -->
                                            <div class="form-group text-begin">
                                                <button type="submit" class="btn btn-primary">Save</button>
                                            </div>
                                        </form>
                                    </div>
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
