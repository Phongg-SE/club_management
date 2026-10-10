<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <link rel="icon" href="assets/img/logofpt.png" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/fpt.png" />
        <!-- CSS Files -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/toast.css" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tiny-slider/2.9.4/tiny-slider.css" />

        <!-- iziToast CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/css/iziToast.min.css">

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
    <body id="bg">
        <jsp:include page="../component/header.jsp"></jsp:include>
            <div class="page-wraper">
                <!--<div id="loading-icon-bx"></div>-->
                <!-- Content -->
                <div class="page-content bg-white">
                    <!-- inner page banner -->
                    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner2.jpg);">
                        <div class="container">
                            <div class="page-banner-entry">
                                <h1 class="text-white">Club Details</h1>
                            </div>
                        </div>
                    </div>
                    <!-- Breadcrumb row -->
                    <div class="breadcrumb-row">
                        <div class="container">
                            <ul class="list-inline">
                                <li><a href="home">Home</a></li>
                                <li>Club Details</li>
                            </ul>
                        </div>
                    </div>                    <!-- Breadcrumb row END -->
                    <div class="content-block">
                        <div class="section-area section-sp1">
                            <div class="container">
                                <div class="row">
                                    <!-- Left part start -->
                                    <div class="col-lg-8 col-xl-8">
                                        <!-- Club Start -->
                                <div class="recent-news blog-lg">
                                            <div>
                                                <!-- Club logo image -->
                                                <img src="${club.club_logo_url}" alt="Club Logo" class="img-fluid rounded shadow-sm w-100 h-auto">
                                        </div>

                                        <div class="info-bx p-4">
                                            <!-- Club Code and Category -->
                                            <ul class="media-post list-inline">
                                                <li class="list-inline-item"><a href="#" class="text-muted"><i class="fa fa-tag"></i> Category: ${club.category_name}</a></li>
                                            </ul>

                                            <!-- Club Name -->
                                            <h3 class="post-title mb-3 font-weight-bold text-primary">${club.name}</h3>

                                            <!-- Club Slogan -->
                                            <h5 class="slogan-text text-muted mb-3">
                                                <span class="font-italic">Slogan: "${club.slogan}"</span>
                                            </h5>

                                            <!-- Club Description -->
                                            <p class="club-description text-muted">${club.description}</p>

                                            <!-- Divider -->
                                            <div class="ttr-divider bg-gray my-3"><i class="icon-dot c-square"></i></div>

                                            <!-- Tags for Status and Is Public -->
                                            <div class="widget_tag_cloud mb-3">
                                                <span class="badge badge-${club.status == 'Active' ? 'success' : 'danger'} mr-2"><i class="fa fa-flag"></i> ${club.status}</span>
                                                <span class="badge badge-info">
                                                    <i class="fa fa-globe"></i> ${club.is_public == 'Yes' ? 'Public' : 'Private'}
                                                </span>
                                            </div>

                                            <!-- Divider -->
                                            <div class="ttr-divider bg-gray my-3"><i class="icon-dot c-square"></i></div>

                                            <!-- Contact Information Section -->
                                            <h5 class="mb-3 text-dark"><i class="fa fa-phone"></i> Contact Information</h5>
                                            <p style="font-style: italic;" class="contact-info">
                                                Email: <a href="mailto:${club.club_email}" class="text-muted">${club.club_email}</a><br>
                                                Fanpage: <a href="${club.club_fanpage_url}" target="_blank" class="text-muted">${club.club_fanpage_url}</a>
                                            </p>

                                            <!-- Divider -->
                                            <div class="ttr-divider bg-gray my-3"><i class="icon-dot c-square"></i></div>

                                            <!-- Social Share Section -->
                                            <h6 class="mb-3">SHARE</h6>
                                            <ul class="list-inline contact-social-bx mb-0">
                                                <li class="list-inline-item"><a href="#" class="btn btn-outline-primary btn-sm rounded-circle"><i class="fa fa-facebook"></i></a></li>
                                                <li class="list-inline-item"><a href="#" class="btn btn-outline-info btn-sm rounded-circle"><i class="fa fa-twitter"></i></a></li>
                                                <li class="list-inline-item"><a href="#" class="btn btn-outline-dark btn-sm rounded-circle"><i class="fa fa-linkedin"></i></a></li>
                                                <li class="list-inline-item"><a href="#" class="btn btn-outline-danger btn-sm rounded-circle"><i class="fa fa-google-plus"></i></a></li>
                                            </ul>
                                        </div>
                                    </div>
                                    <!-- Club End -->

                                    <div class="clear" id="comment-list">
                                        <div class="comments-area" id="comments">
                                            <div class="clearfix m-b20">
                                                <!-- Form -->
                                                <c:choose>
                                                    <c:when test="${not empty sessionScope.account}">
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#registrationModal">
                                                            Register for Club
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="button" class="btn btn-primary" id="loginRequiredBtn">
                                                            Register for Club
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                                <!-- Form -->
                                            </div>

                                            <!-- Add message display here, inside the visible area -->
                                            <c:if test="${not empty message}">
                                                <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                                                    ${message}
                                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- club END -->
                                </div>
                                <!-- Left part END -->

                                <!-- Side bar start -->
                                <div class="col-lg-4 col-xl-4">
                                    <aside class="side-bar sticky-top">
                                        <div class="widget recent-posts-entry">
                                            <h6 class="widget-title text-uppercase font-weight-bold text-center mb-3" 
                                                style="font-size: 18px; letter-spacing: 1px;">
                                                Other Clubs You Might Like
                                            </h6>
                                            <c:choose>
                                                <c:when test="${not empty relatedClubs}">
                                                    <!--start related clubs-->
                                                    <c:forEach var="related" items="${relatedClubs}">
                                                        <div class="widget-post-bx">
                                                            <div class="widget-post clearfix">
                                                                <div>
                                                                    <img src="${related.club_logo_url}" class="card-img-top" alt="" style="width: 5 0%">                                                                </div>
                                                                <div class="ttr-post-info">
                                                                    <div class="ttr-post-header">
                                                                        <!-- Club name in blue -->
                                                                        <h6 class="post-title font-weight-bold text-primary">
                                                                            <a href="PublicClubDetail?id=${related.club_id}">${related.name}</a>
                                                                        </h6>
                                                                    </div>
                                                                    <!-- Club slogan -->
                                                                    <div class="slogan">
                                                                        <small class="text-muted font-italic">"${related.slogan}"</small>
                                                                    </div>
                                                                    <!-- Status and Is Public on the line below -->
                                                                    <div class="mt-2">
                                                                        <span class="badge badge-${related.status == 'Active' ? 'success' : 'danger'}">
                                                                            ${related.status}
                                                                        </span>
                                                                        <span class="badge badge-${related.is_public == 'Yes' ? 'info' : 'secondary'} ml-2">
                                                                            ${related.is_public == 'Yes' ? "Public" : "Private"}
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                    <!--end related clubs-->
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="text-center mt-4">
                                                        <div class="p-4 bg-light border rounded-lg shadow-sm d-inline-block" 
                                                             style="max-width: 500px; border-left: 5px solid #ff9800;">
                                                            <h6 class="font-weight-bold text-dark mb-2">
                                                                <i class="fas fa-info-circle text-warning mr-2"></i> No Matching Clubs
                                                            </h6>
                                                            <p class="text-muted mb-0" style="font-size: 16px;">
                                                                There is no club that has the same category as this club.
                                                            </p>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </aside>
                                </div>

                                <!-- Side bar END -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Registration Modal -->
        <div class="modal fade" id="registrationModal" tabindex="-1" role="dialog" aria-labelledby="registrationModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="registrationModalLabel">Register for Club</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="PublicClubDetail" method="post">
                        <div class="modal-body">
                            <input type="hidden" name="clubId" value="${club.club_id}">
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${account.email}" readonly>
                            </div>

                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" 
                                       value="${account.full_name}" readonly>
                            </div>
                            <div class="form-group">
                                <label for="note">Why do you want to join this club? <small class="text-muted">(Max 255 characters)</small></label>
                                <textarea class="form-control" id="note" name="note" rows="4" maxlength="255" required></textarea>
                                <small id="charCount" class="form-text text-muted">0/255 characters</small>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Submit Application</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../component/footer.jsp"></jsp:include>

            <!-- JavaScript files -->
            <script src="assets/js/jquery.min.js"></script>
            <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
            <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
            <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>

            <!-- iziToast JS -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/js/iziToast.min.js"></script>

            <!-- Toast message display -->
            <script>
                $(document).ready(function () {
                    // Check for toast message in session
                    var toastMessage = "${sessionScope.toastMessage}";
                    var toastType = "${sessionScope.toastType}";

                    if (toastMessage) {
                        // Configure toast based on type
                        iziToast.show({
                            title: toastType === 'success' ? 'Success' :
                                    toastType === 'warning' ? 'Warning' : 'Error',
                            message: toastMessage,
                            position: 'topRight',
                            color: toastType === 'success' ? 'green' :
                                    toastType === 'warning' ? 'yellow' : 'red',
                            timeout: 1000,
                            onClosing: function () {
                                // Remove toast attributes immediately when toast opens
                                fetch('${pageContext.request.contextPath}/remove-toast', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded',
                                    },
                                }).catch(error => {
                                    console.error('Error:', error);
                                });
                            }
                        });
                    }

                    // Login required button click handler
                    $("#loginRequiredBtn").click(function () {
                        iziToast.warning({
                            title: 'Login Required',
                            message: 'Please log in to register for this club',
                            position: 'topRight',
                            timeout: 3000
                        });
                    });

                    // Character counter for note textarea
                    $('#note').on('input', function () {
                        var currentLength = $(this).val().length;
                        var maxLength = 255;
                        $('#charCount').text(currentLength + '/' + maxLength + ' characters');

                        // Visual feedback when approaching limit
                        if (currentLength >= maxLength) {
                            $('#charCount').removeClass('text-muted').addClass('text-danger');
                        } else if (currentLength > maxLength * 0.8) {
                            $('#charCount').removeClass('text-muted').addClass('text-warning');
                        } else {
                            $('#charCount').removeClass('text-danger text-warning').addClass('text-muted');
                        }
                    });

                    // Form validation before submit
                    $('form').on('submit', function (e) {
                        var noteLength = $('#note').val().length;
                        if (noteLength > 255) {
                            e.preventDefault();
                            iziToast.error({
                                title: 'Error',
                                message: 'Your note exceeds the maximum of 255 characters',
                                position: 'topRight',
                                timeout: 3000
                            });
                        }
                    });
                });
        </script>
    </body>
</html>
