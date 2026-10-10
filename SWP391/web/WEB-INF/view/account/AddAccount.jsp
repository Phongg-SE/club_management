<%-- 
    Document   : addNewProduct
    Created on : Oct 14, 2024
    Author     : TNO
--%>

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
            <jsp:include page="../common/component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->

                <div class="main-panel">
                    <div class="container">
                        <div class="page-inner">
                            <div class="page-header">
                                <h3 class="fw-bold mb-3">Add New Account</h3>
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
                                        <a href="accountlist">Account Management</a>
                                    </li>
                                    <li class="separator">
                                        <i class="icon-arrow-right"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#">Add New Account</a>
                                    </li>
                                </ul>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div>
                                        <c:if test="${not empty sessionScope.msg}">
                                            <h4 style="color: red">${sessionScope.msg}</h4>
                                            <c:remove var="msg" scope="session"/>
                                        </c:if>
                                    </div>
                                    <div class="card-header">
                                        <h4 class="card-title">Add Account</h4>
                                    </div>
                                    <div class="card-body">
                                        <c:if test="${not empty errorMsg}">
                                            <div class="alert alert-danger">${errorMsg}</div>
                                        </c:if>
                                        <form action="addaccount" method="post">
                                            <div class="row">
                                                <div class="col-md-12 ">
                                                    <div class="form-group">
                                                        <label for="username" class="form-label">User Name</label>
                                                        <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="Enter user name" required>
                                                    </div>
                                                </div>
                                                <div class="col-md-">
                                                    <div class="form-group">
                                                        <label for="gmail" class="form-label">Gmail</label>
                                                        <input type="email" class="form-control" id="gmail" name="gmail" value="${gmail}" placeholder="Enter gmail" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row ">
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <label for="phonenumber" class="form-label">Phone Number</label>
                                                        <input type="text" class="form-control" id="phonenumber" name="phonenumber" value="${phonenumber}" placeholder="Enter phone number" required>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="gender" class="form-label">Gender</label>
                                                        <select class="form-select" id="gender" name="gender" required>
                                                            <option value="male">Male</option>
                                                            <option value="female">Female</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row ">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label for="role" class="form-label">Role</label>
                                                        <select class="form-select" id="role" name="role" required>
                                                            <option value="user">USER</option>
                                                            <option value="admin">ADMIN</option>
                                                            <option value="member">CLUB MEMBER</option>
                                                            <option value="club leader">CLUB LEADER</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row ">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <div class="d-flex ">
                                                            <button type="submit" class="btn btn-primary px-5">Add Account</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </form>
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
            <script src="assets/js/core/jquery-3.7.1.min.js"></script>
            <script src="assets/js/core/popper.min.js"></script>
            <script src="assets/js/core/bootstrap.min.js"></script>
    </body>
</html>
