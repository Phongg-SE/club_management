<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Project Member List</title>
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


        <style>
            .toast .btn-close-white {
                filter: invert(1) grayscale(100%) brightness(200%);
            }
            .container-fluid, .container {
                padding-left: 0 !important;
                margin-left: 0 !important;
            }
            header {
                width: 100%;
                max-width: 1200px;
                margin: 0 auto;
                padding: 10px 0;
                position: fixed;
                background-color: white; /* Đảm bảo có màu nền nếu cần */
                z-index: 1000; /* Giá trị vừa đủ để không bị nội dung khác đè lên */
            }
            #toast-container {
                position: fixed;
                top: 20px; /* Điều chỉnh vị trí để không bị header che */
                right: 20px;
                z-index: 9999; /* Giá trị cao hơn header để toast luôn hiển thị trên cùng */
            }

            table {
                width: 100%;
                border-collapse: collapse; /* Đảm bảo đường viền gọn gàng */

            }
            .container {
                width: 100%;
                max-width: 100%;
                padding: 0;
            }


        </style>
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


                <div class="main-panel">                    
                    <div class="container">
                        <div class="page-inner">

                            <!-- Card -->
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h4 class="card-title">
                                                    Project Member List
                                                </h4>


                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <div class="row mb-3">
                                                <!-- Filter -->


                                                <!-- Search by Club Name -->


                                                <!--start table-->
                                                <!-- Place this inside the card-body div, where the "start table" comment is -->
                                                <div class="container-fluid">
                                                     <div class="table-responsive">
                                                        <table class="table table-striped align-middle">
                                                            <thead class="table-light">
                                                                <tr>
                                                                    <th>Member id</th>
                                                                    <th>Status</th>
                                                                    <th>Fullname</th>
                                                                    <th>Email</th>
                                                                    
                                                                    <th>Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                 
                                                            <c:forEach var="member" items="${members}">
                                                                <tr>
                                                                    <td>${member.member_id}</td>
                                                                   
                                                                    <td>${member.status}</td>
                                                                    <td>${member.full_name}</td>
                                                                    <td>${member.email}</td>
                                                            
                                                                    <td>
                                                                        <!-- Nút Xóa -->
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


                                                    <!--paging-->
                                                    <div id="paginationControls" class="mt-3">
                                                        <nav aria-label="Page navigation">
                                                            <ul class="pagination justify-content-center">
                                                                <!-- Pagination will be populated by JavaScript -->
                                                            </ul>
                                                        </nav>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>



                        </div>
                    </div>

                    <!-- Required JavaScript Files -->
                    <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/simplebar.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/select2.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/flatpickr.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/jquery.timepicker.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>

                    <!-- Initialize Bootstrap components -->

                    </body>
                    </html>