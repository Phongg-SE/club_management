<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.entity.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html leng="en">
    <head>
        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <!-- DESCRIPTION -->
        <meta name="description" content="EduChamp : Education HTML Template" />

        <!-- OG -->
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="EduChamp : Education HTML Template" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="assets/img/logofpt.png" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/fpt.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>Profile</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">

        <!-- Thêm Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

    </head>

    <body id="bg">

        <jsp:include page="../component/header.jsp"></jsp:include>

            <div class="page-wraper">

                <!-- Content -->
                <div class="page-content bg-white">
                    <!-- inner page banner END -->
                    <div class="content-block">
                        <!-- About Us -->
                        <!--                        <div class="section-area section-sp1">-->
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-3 col-md-4 col-sm-12 m-b30">
                                    <div class="profile-bx text-center">

                                        <div class="user-profile-container">
                                            <div class="user-profile-thumb">
                                                <img id="avatarImage" src="${account.avatar_url}" alt="Avatar"/>
                                        </div>
                                        <div class="list-group shadow-sm rounded-3 overflow-hidden" id="avatarMenu">
                                            <a href="#" id="viewAvatar" 
                                               class="list-group-item list-group-item-action d-flex align-items-center py-2 border-0 text-dark hover-effect">
                                                <i class="bi bi-eye-fill fs-5 me-4"></i> 
                                                <span class="fs-6 fw-semibold ms-1">Preview Avatar</span>
                                            </a>
                                            <a href="#" id="changeAvatar" 
                                               class="list-group-item list-group-item-action d-flex align-items-center py-2 border-0 text-dark hover-effect">
                                                <i class="bi bi-image-fill fs-5 me-4"></i> 
                                                <span class="fs-6 fw-semibold ms-1">Update Avatar</span>
                                            </a>
                                        </div>
                                    </div>

                                    <form id="avatarForm" action="ChangeAvatar" method="POST" enctype="multipart/form-data">
                                        <!-- Input ẩn để tải ảnh lên -->
                                        <input type="file" id="uploadAvatar" name="avatar" style="display: none;" accept="image/*"/>
                                    </form>

                                    <!-- start infor of user-->
                                    <div class="profile-info">
                                        <h4>${account.full_name}</h4>
                                        <span>${account.email}</span>
                                    </div>
                                    <!-- end infor of user-->

                                    <!--start social-->
                                    <div class="profile-social">
                                        <ul class="list-inline m-a0">
                                            <li><a href="${account.facebook}" target="_blank"><i class="fa fa-facebook"></i></a></li>
                                            <li><a href="${account.instargram}" target="_blank"><i class="fa fa-instagram"></i></a></li>
                                        </ul>
                                    </div>
                                    <!--end social-->
                                    <div class="profile-tabnav">
                                        <ul class="nav nav-tabs">
                                            <li class="nav-item">
                                                <a class="nav-link active" data-toggle="tab" href="#courses"><i class="ti-book"></i>Post</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" data-toggle="tab" href="#quiz-results"><i class="ti-bookmark-alt"></i>Club Activities</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" data-toggle="tab" href="#edit-profile"><i class="ti-pencil-alt"></i>Edit Profile</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" data-toggle="tab" href="#change-password"><i class="ti-lock"></i>Change Password</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-9 col-md-8 col-sm-12 m-b30">
                                <div class="profile-content-bx">
                                    <div class="tab-content">
                                        <!--post-->
                                        <div class="tab-pane active" id="courses">
                                            <div class="profile-head">
                                                <h3>My Post</h3>
                                                <div class="feature-filters style1 ml-auto">
                                                    <ul class="filters" data-toggle="buttons">
                                                        <li data-filter="" class="btn active">
                                                            <input type="radio">
                                                            <a href="#"><span>All</span></a> 
                                                        </li>
                                                        <li data-filter="Published" class="btn">
                                                            <input type="radio">
                                                            <a href="#"><span>Publish</span></a> 
                                                        </li>
                                                        <li data-filter="Draft" class="btn">
                                                            <input type="radio">
                                                            <a href="#"><span>Draft</span></a> 
                                                        </li>
                                                        <li data-filter="Private" class="btn">
                                                            <input type="radio">
                                                            <a href="#"><span>Private</span></a> 
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="courses-filter">
                                                <div class="clearfix">
                                                    <ul id="masonry" class="ttr-gallery-listing magnific-image row">
                                                        <c:forEach var="post" items="${posts}">
                                                            <li class="action-card col-xl-4 col-lg-6 col-md-12 col-sm-6 ${post.status}">
                                                                <div class="cours-bx">
                                                                    <div class="action-box">
                                                                        <img src="${post.imageUrl}" alt="${post.title}">
                                                                        <a href="PostDetail?post_id=${post.postId}" class="btn">View Detail</a>
                                                                    </div>
                                                                    <div class="info-bx text-center">
                                                                        <h5><a href="#">${post.title}</a></h5>
                                                                        <span>${post.clubName}</span>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>

                                        <!--general-->
                                        <div class="tab-pane" id="quiz-results">
                                            <!--select theo club-->
                                            <div class="profile-head d-flex justify-content-between align-items-center">
                                                <h3 class="mb-0">Club Activities</h3>

                                                <!-- Form chọn CLB -->
                                                <form method="get" action="userprofile" class="d-flex align-items-center">
                                                    <select id="clubFilter" class="form-select form-select-sm" name="clubId" onchange="this.form.submit()">
                                                        <option value="">Select Club</option>
                                                        <c:forEach var="club" items="${userClubs}">
                                                            <option value="${club.club_id}" ${club.club_id == selectedClubId ? 'selected' : ''}>${club.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </form>
                                            </div>

                                            <!-- Nội dung Club Activities -->
                                            <div class="courses-filter">
                                                <div class="row">
                                                    <!-- Trái -->
                                                    <div class="col-md-6 col-lg-6">
                                                        <ul class="course-features">
                                                            <li><i class="ti-user"></i> <span class="label">Number Member</span> <span class="value">${clubactivities.numberMember}</span></li>
                                                            <li><i class="ti-calendar"></i> <span class="label">Number Event</span> <span class="value">${clubactivities.numberEvent}</span></li>
                                                            <li><i class="ti-briefcase"></i> <span class="label">Number Project</span> <span class="value">${clubactivities.numberProject}</span></li>
                                                            <li><i class="ti-money"></i> <span class="label">Total Club Expense</span> 
                                                                <span class="value">VND <fmt:formatNumber value="${clubactivities.totalExpense}" type="number" groupingUsed="true"/></span>
                                                            </li>
                                                            <li><i class="ti-wallet"></i> <span class="label">Total Club Income</span> 
                                                                <span class="value">VND <fmt:formatNumber value="${clubactivities.totalIncome}" type="number" groupingUsed="true"/></span>
                                                            </li>
                                                            <li><i class="ti-write"></i> <span class="label">Number Registration</span> <span class="value">${clubactivities.numberRegistration}</span></li>
                                                            <li><i class="ti-na"></i> <span class="label">Number Rejection</span> <span class="value">${clubactivities.numberRejection}</span></li>
                                                        </ul>
                                                    </div>


                                                    <!-- Phải -->
                                                    <div class="col-md-6 col-lg-6">
                                                        <ul class="course-features">
                                                            <li><i class="ti-wallet"></i> <span class="label">My Donation</span> 
                                                                <span class="value">VND <fmt:formatNumber value="${useractivities.myDonation}" type="number" groupingUsed="true"/></span>
                                                            </li>
                                                            <li><i class="ti-list"></i> <span class="label">Number of Tasks</span> <span class="value">${useractivities.numberTask}</span></li>
                                                            <li><i class="ti-check"></i> <span class="label">Completed Tasks</span> <span class="value">${useractivities.doneTask}</span></li>
                                                            <li><i class="ti-timer"></i> <span class="label">In-progress Tasks</span> <span class="value">${useractivities.doingTask}</span></li>
                                                            <li><i class="ti-reload"></i> <span class="label">Pending Tasks</span> <span class="value">${useractivities.assignedTask}</span></li>
                                                            <li><i class="ti-calendar"></i> <span class="label">Event Participation</span> <span class="value">${useractivities.eventParticipation}</span></li>
                                                            <li><i class="ti-briefcase"></i> <span class="label">Project Participation</span> <span class="value">${useractivities.projectParticipation}</span></li>
                                                        </ul>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>

                                        <!--edit general profile-->
                                        <div class="tab-pane" id="edit-profile">

                                            <div class="profile-head">
                                                <h3>Edit Profile</h3>
                                            </div>
                                            <%
                                                Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
                                                User userInput = (User) session.getAttribute("userInput");

//                                                    session.removeAttribute("errors");
//                                                    session.removeAttribute("userInput");
                                            %>
                                            <!--form-->
                                            <form action="updateprofile" method="post" class="edit-profile">
                                                <input type="hidden" name="user_id" value="${info.user_id}" required="">

                                                <div class="">
                                                    <div class="form-group row">
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-10 ml-auto">
                                                            <h3>1. Personal Details</h3>
                                                        </div>
                                                    </div>

                                                    <!-- Full Name -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Full Name</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="full_name"
                                                                   value="${userInput.full_name != null ? userInput.full_name : info.full_name}" required>
                                                            <small class="text-danger">${errors.full_name}</small>
                                                        </div>
                                                    </div>

                                                    <!-- Email -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Email</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="email" value="${userInput.email != null ? userInput.email : info.email}">
                                                            <small class="text-danger">${errors.email}</small>

                                                        </div>
                                                    </div>

                                                    <!-- Date of Birth -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Date Of Birth</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="date" name="date_of_birth"
                                                                   value="${userInput.date_of_birth != null ? userInput.date_of_birth : info.date_of_birth}" required>
                                                            <small class="text-danger">${errors.date_of_birth}</small>
                                                        </div>
                                                    </div>

                                                    <!-- Phone Number -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Phone Number</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="phone_number"
                                                                   value="${userInput.phone_number != null ? userInput.phone_number : info.phone_number}" required>
                                                            <small class="text-danger">${errors.phone_number}</small>
                                                        </div>
                                                    </div>

                                                    <div class="seperator"></div>

                                                    <div class="form-group row">
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-10 ml-auto">
                                                            <h3>2. Address</h3>
                                                        </div>
                                                    </div>

                                                    <!-- Address -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Address</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="address"
                                                                   value="${userInput.address != null ? userInput.address : info.address}" required>
                                                            <small class="text-danger">${errors.address}</small>
                                                        </div>
                                                    </div>

                                                    <!-- City -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">City</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="city"
                                                                   value="${userInput.city != null ? userInput.city : info.city}" required>
                                                            <small class="text-danger">${errors.city}</small>
                                                        </div>
                                                    </div>

                                                    <!-- District -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">District</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="district"
                                                                   value="${userInput.district != null ? userInput.district : info.district}" required>
                                                            <small class="text-danger">${errors.district}</small>
                                                        </div>
                                                    </div>

                                                    <div class="m-form__seperator m-form__seperator--dashed m-form__seperator--space-2x"></div>

                                                    <div class="form-group row">
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-10 ml-auto">
                                                            <h3 class="m-form__section">3. Social Links</h3>
                                                        </div>
                                                    </div>

                                                    <!-- Facebook -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Facebook</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="facebook"
                                                                   value="${userInput.facebook != null ? userInput.facebook : info.facebook}" required>
                                                            <small class="text-danger">${errors.facebook}</small>
                                                        </div>
                                                    </div>

                                                    <!-- Instagram -->
                                                    <div class="form-group row">
                                                        <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Instagram</label>
                                                        <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                            <input class="form-control" type="text" name="instargram"
                                                                   value="${userInput.instargram != null ? userInput.instargram : info.instargram}" required>
                                                            <small class="text-danger">${errors.instargram}</small>
                                                        </div>
                                                    </div>

                                                </div>

                                                <!-- Submit & Cancel -->
                                                <div class="">
                                                    <div class="">
                                                        <div class="row">
                                                            <div class="col-12 col-sm-3 col-md-3 col-lg-2"></div>
                                                            <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                                <button type="submit" class="btn">Save changes</button>
                                                                <button type="reset" class="btn-secondry">Cancel</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                        <!--change password-->
                                        <div class="tab-pane" id="change-password">

                                            <div class="profile-head">
                                                <h3>Change Password</h3>
                                            </div>

                                            <form id="changePasswordForm" class="edit-profile">
                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">Current Password</label>
                                                    <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                        <div class="input-group">
                                                            <input id="oldPassword" class="form-control" type="password">
                                                            <div class="input-group-append">
                                                                <button type="button" class="btn btn-outline-secondary toggle-password" data-target="oldPassword">
                                                                    👁️
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <span class="error-message text-danger" id="error-oldPassword"></span>
                                                    </div>
                                                </div>


                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">New Password</label>
                                                    <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                        <div class="input-group">
                                                            <input id="newPassword" class="form-control" type="password">
                                                            <div class="input-group-append">
                                                                <button type="button" class="btn btn-outline-secondary toggle-password" data-target="newPassword">
                                                                    👁️
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <div class="error-message text-danger" id="error-newPassword"></div>
                                                        <div class="error-message text-danger" id="error-length"></div>
                                                        <div class="error-message text-danger" id="error-uppercase"></div>
                                                        <div class="error-message text-danger" id="error-lowercase"></div>
                                                        <div class="error-message text-danger" id="error-digit"></div>
                                                        <div class="error-message text-danger" id="error-specialChar"></div>
                                                    </div>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">Re Type New Password</label>
                                                    <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                        <div class="input-group">
                                                            <input id="reNewPassword" class="form-control" type="password">
                                                            <div class="input-group-append">
                                                                <button type="button" class="btn btn-outline-secondary toggle-password" data-target="reNewPassword">
                                                                    👁️
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <span class="error-message text-danger" id="error-reNewPassword"></span>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-12 col-sm-4 col-md-4 col-lg-3"></div>
                                                    <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                        <button type="button" id="savePasswordBtn" class="btn btn-primary">Save changes</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div> 
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--</div>-->
                <!-- contact area END -->
            </div>
            <jsp:include page="../component/footer.jsp"></jsp:include>
                <button class="back-to-top fa fa-chevron-up" ></button>
            </div>
            <!-- External JavaScripts -->
            <script src="assets/js/jquery.min.js"></script>
            <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
            <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
            <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
            <script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
            <script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
            <script src="assets/vendors/counter/waypoints-min.js"></script>
            <script src="assets/vendors/counter/counterup.min.js"></script>
            <script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
            <script src="assets/vendors/masonry/masonry.js"></script>
            <script src="assets/vendors/masonry/filter.js"></script>
            <script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
            <script src="assets/js/functions.js"></script>
            <script src="assets/js/contact.js"></script>
            <script src='assets/vendors/switcher/switcher.js'></script>

            <script>
                                                        document.addEventListener("DOMContentLoaded", function () {
                                                            const avatarImage = document.getElementById("avatarImage");
                                                            const avatarMenu = document.getElementById("avatarMenu");
                                                            const viewAvatar = document.getElementById("viewAvatar");
                                                            const changeAvatar = document.getElementById("changeAvatar");
                                                            const uploadAvatar = document.getElementById("uploadAvatar");
                                                            const avatarForm = document.getElementById("avatarForm");

                                                            // Ẩn menu khi tải trang
                                                            avatarMenu.style.display = "none";

                                                            // Hiện/Ẩn menu khi click vào avatar
                                                            avatarImage.addEventListener("click", function (event) {
                                                                event.stopPropagation();
                                                                avatarMenu.style.display = (avatarMenu.style.display === "block") ? "none" : "block";
                                                            });

                                                            // Ẩn menu khi click ra ngoài
                                                            document.addEventListener("click", function (event) {
                                                                if (!avatarMenu.contains(event.target) && event.target !== avatarImage) {
                                                                    avatarMenu.style.display = "none";
                                                                }
                                                            });

                                                            // Hiển thị popup modal khi click vào "Xem ảnh"
                                                            const avatarModal = document.getElementById("avatarModal");
                                                            const modalAvatar = document.getElementById("modalAvatar");
                                                            const closeModal = document.querySelector(".modal .close");

                                                            if (viewAvatar) {
                                                                viewAvatar.addEventListener("click", function (event) {
                                                                    event.stopPropagation();
                                                                    event.preventDefault();

                                                                    // Kiểm tra nếu avatar có hình ảnh hợp lệ mới hiển thị
                                                                    if (avatarImage.src && avatarImage.src !== "about:blank") {
                                                                        modalAvatar.src = avatarImage.src;
                                                                        avatarModal.style.display = "flex";
                                                                    } else {
                                                                        alert("Avatar không tồn tại!");
                                                                    }
                                                                });
                                                            }

                                                            // Đóng modal khi click vào nút X hoặc bên ngoài
                                                            closeModal.addEventListener("click", function () {
                                                                avatarModal.style.display = "none";
                                                            });

                                                            avatarModal.addEventListener("click", function (event) {
                                                                if (event.target === avatarModal) {
                                                                    avatarModal.style.display = "none";
                                                                }
                                                            });

                                                            // Khi click "Đổi avatar" - Hiện input chọn ảnh
                                                            changeAvatar.addEventListener("click", function (event) {
                                                                event.stopPropagation();
                                                                event.preventDefault();
                                                                uploadAvatar.click();
                                                            });

                                                            // Xử lý khi chọn ảnh mới
                                                            uploadAvatar.addEventListener("change", function () {
                                                                if (uploadAvatar.files.length > 0) {
                                                                    avatarForm.submit(); // Submit form để upload ảnh
                                                                }
                                                            });
                                                        });

            </script>

            <!-- Popup hiển thị avatar -->
            <div id="avatarModal" class="modal" style="display: none;">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <img id="modalAvatar" src="" alt="Avatar">
                </div>
            </div>

            <style>
                /* CSS cho modal */
                .modal {
                    display: none;
                    position: fixed;
                    z-index: 1000;
                    left: 0;
                    top: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(0, 0, 0, 0.5);
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }

                .modal-content {
                    background-color: white;
                    padding: 20px;
                    border-radius: 8px;
                    position: relative;
                    max-width: 20vw; /* Giới hạn chiều rộng */
                    max-height:50vh; /* Giới hạn chiều cao */
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }

                .modal-content img {
                    max-width: 100%;
                    max-height: 80vh; /* Ảnh không bị quá nhỏ */
                    width: auto; /* Giữ nguyên tỷ lệ */
                    height: auto;
                    border-radius: 8px;
                }


                .close {
                    position: absolute;
                    top: 10px;
                    right: 15px;
                    font-size: 24px;
                    cursor: pointer;
                }
            </style>

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
        <div id="toastContainer" style="position: fixed; top: 20px; right: 20px; z-index: 1000;"></div>

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
                position: fixed;
                bottom: 20px;   /* Đặt toast cách đáy 20px */
                right: 20px;    /* Đặt toast cách phải 20px */
                z-index: 9999;  /* Đảm bảo toast hiển thị trên cùng */
                background: rgba(0, 0, 0, 0.8);
                color: white;
                padding: 10px 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.2);
                animation: fadeInOut 4s ease-in-out; /* Tự động biến mất sau 4s */
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
                transform: translateX(0);
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

                // Tự động ẩn toast sau 3 giây
                setTimeout(() => {
                    toast.classList.remove("show");
                    setTimeout(() => toast.remove(), 500); // Xóa khỏi DOM sau khi hoàn thành hiệu ứng
                }, 3000);
            }

        </script>
        
        <!--change password ajax-->
        <script>
            $("#savePasswordBtn").click(function () {
                let oldPassword = $("#oldPassword").val().trim();
                let newPassword = $("#newPassword").val().trim();
                let reNewPassword = $("#reNewPassword").val().trim();

                console.log("Sending data:", {oldPassword, newPassword, reNewPassword});

                $.ajax({
                    type: "POST",
                    url: "changepassword",
                    data: {oldPassword, newPassword, reNewPassword},
                    dataType: "json",
                    success: function (response) {
                        console.log("Server Response:", response);
                        $(".error-message").text(""); // Xóa lỗi cũ

                        if (response.success) {
                            showToast("Updated Password Successfull", "success"); // Hiển thị toast
                        } else {
                            for (const [key, message] of Object.entries(response)) {
                                let errorField = $("#error-" + key);
                                if (errorField.length) {
                                    errorField.text(message);
                                } else {
                                    console.warn("Không tìm thấy thẻ lỗi cho key:", key);
                                }
                            }
                        }
                    },
                    error: function (xhr) {
                        console.log("Error Response:", xhr.responseText);
                    }
                });
            });
        </script>

        <!--change buttom view hidden pass-->
        <script>
            document.querySelectorAll(".toggle-password").forEach(button => {
                button.addEventListener("click", function () {
                    let input = document.getElementById(this.dataset.target);
                    if (input.type === "password") {
                        input.type = "text";
                        this.innerText = "🙈"; // Đổi icon
                    } else {
                        input.type = "password";
                        this.innerText = "👁️";
                    }
                });
            });
        </script>
    </body>
</html>
