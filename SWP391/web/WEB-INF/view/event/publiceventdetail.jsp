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
        <!-- Registration success message -->
        <c:if test="${param.registered eq 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                Your registration has been submitted successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <!-- Registration failure message -->
        <c:if test="${param.registered eq 'false'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                Failed to submit your registration. Please try again later.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <!-- Already registered message -->
        <c:if test="${param.message eq 'already_registered'}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                You have already registered for this event.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <!-- Success message -->
        <c:if test="${param.message eq 'success'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                Your registration has been submitted successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <!-- Failed message -->
        <c:if test="${param.message eq 'failed'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; right: 20px; z-index: 9999;">
                Failed to submit your registration. Please try again later.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>

        <jsp:include page="../component/header.jsp"></jsp:include>
            <div class="page-wraper">
                <div id="loading-icon-bx"></div>
                <!-- Content -->
                <div class="page-content bg-white">
                    <!-- inner page banner -->
                    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner2.jpg);">
                        <div class="container">
                            <div class="page-banner-entry">
                                <h1 class="text-white">Event Details</h1>
                            </div>
                        </div>
                    </div>
                    <!-- Breadcrumb row -->
                    <div class="breadcrumb-row">
                        <div class="container">
                            <ul class="list-inline">
                                <li><a href="home">Home</a></li>
                                <li >Event Details</li>
                            </ul>
                        </div>
                    </div>
                    <!-- Breadcrumb row END -->
                    <div class="content-block">
                        <div class="section-area section-sp1">
                            <div class="container">
                                <div class="d-flex flex-wrap">
                                    <!-- Left Part: Event Details (Main Content) -->
                                    <div class="flex-grow-1 event-main-content" style="width: 65%; padding-right: 20px;">
                                        <div class="recent-news blog-lg">
                                            <div>
                                                <img src="${event.avatar_url}" alt="Event Image" class="img-fluid rounded shadow-sm">
                                        </div>
                                        <div class="info-bx p-4">
                                            <ul class="media-post list-inline">
                                                <li class="list-inline-item"><a href="#" class="text-muted"><i class="fa fa-clock-o"></i> ${event.created_date}</a></li>
                                                <li class="list-inline-item"><a href="#" class="text-muted"><i class="fa fa-users"></i> ${event.club.name}</a></li>
                                            </ul>
                                            <h3 class="post-title mb-3 font-weight-bold text-primary">${event.name}</h3>
                                            <h5 class="organizer-name text-muted mb-3">
                                                <span class="font-italic small">Organized by:</span>
                                                <span class="font-weight-bold font-italic small">${event.user.full_name}</span>
                                            </h5>
                                            <p class="event-description text-muted">${event.description}</p>
                                            <div class="ttr-divider bg-gray my-3"><i class="icon-dot c-square"></i></div>
                                            <div class="event-timing mb-3">
                                                <span class="badge badge-secondary mr-2"><i class="fa fa-calendar"></i> From: ${event.start_time}</span>
                                                <span class="badge badge-secondary"><i class="fa fa-calendar"></i> To: ${event.end_time}</span>
                                            </div>
                                            <div class="widget_tag_cloud mb-3">
                                                <span class="badge badge-info mr-2"><i class="fa fa-user"></i> ${event.guest}</span>
                                                <span class="badge badge-warning mr-2"><i class="fa fa-flag"></i> ${event.status}</span>
                                                <span class="badge badge-success">
                                                    <i class="fa fa-globe"></i> ${event.is_public == 'Yes' ? 'Public' : 'Private'}
                                                </span>
                                            </div>
                                            <div class="location mb-3">
                                                <span class="badge badge-primary"><i class="fa fa-map-marker"></i> ${event.location}</span>
                                            </div>

                                            <!-- Divider -->
                                            <div class="ttr-divider bg-gray my-3"><i class="icon-dot c-square"></i></div>

                                            <!-- Contact Information -->
                                            <h5 class="mb-3 text-dark"><i class="fa fa-phone"></i> Contact Information</h5>
                                            <p style="font-style: italic;" class="contact-info">
                                                Email: <a href="mailto:${event.club.club_email}" class="text-muted">${event.club.club_email}</a><br>
                                                Fanpage: <a href="${event.club.club_fanpage_url}" target="_blank" class="text-muted">${event.club.club_fanpage_url}</a>
                                            </p>

                                            <!-- Divider -->
                                            <div class="ttr-divider bg-gray my-3"><i class="icon-dot c-square"></i></div>

                                            <!-- Register Button -->
                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#registrationModal">
                                                Register for Event
                                            </button>

                                            <!-- Message Display -->
                                            <c:if test="${not empty message}">
                                                <div class="alert alert-${messageType} alert-dismissible fade show mt-3" role="alert">
                                                    ${message}
                                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <!-- Right Part: Relevant Events (Sidebar) -->
                                <div class="event-sidebar" style="width: 35%; padding-left: 20px;">
                                    <aside class="side-bar sticky-top">
                                        <div class="widget recent-posts-entry">
                                            <h6 class="widget-title">Relevant Events</h6>
                                            <c:forEach var="random" items="${random}">
                                                <div class="widget-post-bx" style="display: flex; align-items: center; margin-bottom: 10px;">
                                                    <div style="flex: 0 0 80px; margin-right: 10px;">
                                                        <img src="${random.avatar_url}" class="rounded" 
                                                             style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px;" alt="">
                                                    </div>
                                                    <div style="flex-grow: 1;">
                                                        <h6 class="post-title font-weight-bold text-primary mb-1" style="font-size: 14px;">
                                                            <a href="publiceventdetail?event_id=${random.event_id}">
                                                                ${random.name}
                                                            </a>
                                                        </h6>
                                                        <ul class="media-post list-unstyled mb-1" style="font-size: 12px;">
                                                            <li><i class="fa fa-calendar"></i> ${random.created_date}</li>
                                                        </ul>
                                                        <div>
                                                            <span class="badge badge-${random.status == 'Active' ? 'success' : 'danger'} mr-1" style="font-size: 11px;">
                                                                ${random.status}
                                                            </span>
                                                            <span class="badge badge-${random.is_public == 'Yes' ? 'success' : 'secondary'}" style="font-size: 11px;">
                                                                ${random.is_public == 'Yes' ? "Public" : "Private"}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </aside>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <jsp:include page="../component/footer.jsp"></jsp:include>
                    <!-- Add Modal with conditional fields based on login status -->
                    <div class="modal fade" id="registrationModal" tabindex="-1" role="dialog" aria-labelledby="registrationModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="registrationModalLabel">Event Registration Form</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form id="eventRegistrationForm" method="POST" action="publiceventdetail">
                                        <input type="hidden" name="eventId" value="${event.event_id}">

                                    <c:choose>
                                        <c:when test="${not empty sessionScope.account}">
                                            <!-- Logged in user - show readonly fields -->
                                            <div class="form-group">
                                                <label for="email">Email</label>
                                                <input type="email" class="form-control" id="email" name="email" 
                                                       value="${sessionScope.account.email}" readonly>
                                            </div>

                                            <div class="form-group">
                                                <label for="fullName">Full Name</label>
                                                <input type="text" class="form-control" id="fullName" name="fullName" 
                                                       value="${sessionScope.account.full_name}" readonly >
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Guest user - show editable fields -->
                                            <div class="form-group">
                                                <label for="email">Email</label>
                                                <input type="email" class="form-control" id="email" name="email" required>
                                                <small class="form-text text-muted">We'll never share your email with anyone else.</small>
                                            </div>

                                            <div class="form-group">
                                                <label for="fullName">Full Name</label>
                                                <c:if test="${not empty sessionScope.account}"></c:if>
                                                    <input type="text" class="form-control" id="fullName" name="fullName" required>
                                                </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="form-group">
                                        <label for="mobile">Mobile Number</label>
                                        <input type="text" class="form-control" id="mobile" name="mobile" 
                                               placeholder="Enter 10-digit mobile number" required>
                                        <small id="mobileHelp" class="form-text text-muted">Mobile number must be 10 digits</small>
                                    </div>

                                    <div class="form-group">
                                        <label for="note">Notes <small class="text-muted">(Max 255 characters)</small></label>
                                        <textarea class="form-control" id="note" name="note" rows="4" maxlength="255" required></textarea>
                                        <small id="charCount" class="form-text text-muted">0/255 characters</small>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary" id="submitBtn">Submit Application</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- External JavaScripts -->
                <script src="${pageContext.request.contextPath}/assets/js/jquery.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/js/popper.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/js/bootstrap.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/magnific-popup/magnific-popup.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/counter/waypoints-min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/counter/counterup.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/imagesloaded/imagesloaded.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/masonry/masonry.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/masonry/filter.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/owl-carousel/owl.carousel.js"></script>
                <script src="${pageContext.request.contextPath}/assets/js/functions.js"></script>
                <script src="${pageContext.request.contextPath}/assets/js/contact.js"></script>
                <script src='${pageContext.request.contextPath}/assets/vendors/switcher/switcher.js'></script>
                <!-- Revolution JavaScripts Files -->
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/jquery.themepunch.tools.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/jquery.themepunch.revolution.min.js"></script>
                <!-- Slider revolution 5.0 Extensions  (Load Extensions only on Local File Systems !  The following part can be removed on Server for On Demand Loading) -->
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.actions.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.carousel.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.kenburn.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.layeranimation.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.migration.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.navigation.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.parallax.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.slideanims.min.js"></script>
                <script src="${pageContext.request.contextPath}/assets/vendors/revolution/js/extensions/revolution.extension.video.min.js"></script>
                <script>
                    jQuery(document).ready(function () {
                        var ttrevapi;
                        var tpj = jQuery;
                        if (tpj("#rev_slider_486_1").revolution == undefined) {
                            revslider_showDoubleJqueryError("#rev_slider_486_1");
                        } else {
                            ttrevapi = tpj("#rev_slider_486_1").show().revolution({
                                sliderType: "standard",
                                jsFileLocation: "assets/vendors/revolution/js/",
                                sliderLayout: "fullwidth",
                                dottedOverlay: "none",
                                delay: 9000,
                                navigation: {
                                    keyboardNavigation: "on",
                                    keyboard_direction: "horizontal",
                                    mouseScrollNavigation: "off",
                                    mouseScrollReverse: "default",
                                    onHoverStop: "on",
                                    touch: {
                                        touchenabled: "on",
                                        swipe_threshold: 75,
                                        swipe_min_touches: 1,
                                        swipe_direction: "horizontal",
                                        drag_block_vertical: false
                                    }
                                    ,
                                    arrows: {
                                        style: "uranus",
                                        enable: true,
                                        hide_onmobile: false,
                                        hide_onleave: false,
                                        tmp: '',
                                        left: {
                                            h_align: "left",
                                            v_align: "center",
                                            h_offset: 10,
                                            v_offset: 0
                                        },
                                        right: {
                                            h_align: "right",
                                            v_align: "center",
                                            h_offset: 10,
                                            v_offset: 0
                                        }
                                    },

                                },
                                viewPort: {
                                    enable: true,
                                    outof: "pause",
                                    visible_area: "80%",
                                    presize: false
                                },
                                responsiveLevels: [1240, 1024, 778, 480],
                                visibilityLevels: [1240, 1024, 778, 480],
                                gridwidth: [1240, 1024, 778, 480],
                                gridheight: [768, 600, 600, 600],
                                lazyType: "none",
                                parallax: {
                                    type: "scroll",
                                    origo: "enterpoint",
                                    speed: 400,
                                    levels: [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 46, 47, 48, 49, 50, 55],
                                    type: "scroll",
                                },
                                shadow: 0,
                                spinner: "off",
                                stopLoop: "off",
                                stopAfterLoops: -1,
                                stopAtSlide: -1,
                                shuffle: "off",
                                autoHeight: "off",
                                hideThumbsOnMobile: "off",
                                hideSliderAtLimit: 0,
                                hideCaptionAtLimit: 0,
                                hideAllCaptionAtLilmit: 0,
                                debugMode: false,
                                fallbacks: {
                                    simplifyAll: "off",
                                    nextSlideOnWindowFocus: "off",
                                    disableFocusListener: false,
                                }
                            });
                        }
                    });
                </script>

                <!-- Auto-hide alerts after 5 seconds -->
                <script>
                    $(document).ready(function () {
                        // Auto-hide alerts after 5 seconds
                        setTimeout(function () {
                            $('.alert').alert('close');
                        }, 5000);
                    });
                </script>

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
                                    // Remove toast attributes from session after displaying
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
                    });
                </script>

                <!-- Add JavaScript validation -->
                <script>
                    $(document).ready(function () {
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

                        // Mobile number validation
                        $('#mobile').on('input', function () {
                            var mobile = $(this).val();
                            // Remove any non-digit characters
                            $(this).val(mobile.replace(/\D/g, ''));

                            // Check if valid (10 digits)
                            if ($(this).val().length === 10) {
                                $(this).removeClass('is-invalid').addClass('is-valid');
                                $('#mobileHelp').removeClass('text-danger').addClass('text-success');
                            } else {
                                $(this).removeClass('is-valid').addClass('is-invalid');
                                $('#mobileHelp').removeClass('text-success').addClass('text-danger');
                            }
                        });

                        // Form validation before submit
                        $('#eventRegistrationForm').on('submit', function (e) {
                            var isValid = true;
                            var errorMessage = '';

                            // Validate note length
                            var noteLength = $('#note').val().length;
                            if (noteLength > 255) {
                                isValid = false;
                                errorMessage = 'Note cannot exceed 255 characters';
                            }

                            // Validate mobile number
                            var mobileNumber = $('#mobile').val();
                            if (mobileNumber.length !== 10 || !/^\d+$/.test(mobileNumber)) {
                                isValid = false;
                                errorMessage = errorMessage ? errorMessage + '<br>Mobile number must be 10 digits' : 'Mobile number must be 10 digits';
                            }

                            // Prevent form submission if validation fails
                            if (!isValid) {
                                e.preventDefault();
                                // Display error message
                                iziToast.error({
                                    title: 'Error',
                                    message: errorMessage,
                                    position: 'topRight',
                                    timeout: 2000
                                });
                            }
                        });
                    });
                </script>
                </body>

                </html>
