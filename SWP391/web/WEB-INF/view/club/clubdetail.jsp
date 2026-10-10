<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zxx" class="no-js">
    <head>
        <!-- Mobile Specific Meta -->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Favicon-->
        <link rel="shortcut icon" href="img/fav.png">
        <!-- Author Meta -->
        <meta name="author" content="CodePixar">
        <!-- Meta Description -->
        <meta name="description" content="">
        <!-- Meta Keyword -->
        <meta name="keywords" content="">
        <!-- meta character set -->
        <meta charset="UTF-8">
        <!-- Site Title -->
        <title>Karma Shop</title>

        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/linearicons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/themify-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nice-select.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nouislider.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />
    </head>

    <body>
        <!-- Start Header Area -->
        <jsp:include page="../common/component/header.jsp"></jsp:include>
            <!-- End Header Area -->

            <!-- Start Banner Area -->
        <jsp:include page="../common/component/banner.jsp"></jsp:include>
            <!-- End Banner Area -->

            <!--view club detail-->
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div>
                                <h4 style="color: red" >${msg}</h4>
                        </div>
                        <div class="card-header">
                            <h4 class="card-title">Club Details</h4>
                        </div>
                        <div class="card-body">
                            <form action="updateProduct" method="post" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <input type="text" name="clubId" value="${club.clubId}" style="display: none"/>
                                            <label for="clubName">Club Name</label>
                                            <input type="text" class="form-control" value="${club.clubName}" id="clubName" name="clubName" placeholder="Enter club name" required readonly>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">                   
                                            <img id="imagePreview" src="${club.clubLogo}" alt="Club Logo" style="max-width: 200px; max-height: 200px;" />
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="slogan">Slogan</label>
                                            <input type="text" class="form-control" value="${club.slogan}" id="slogan" name="slogan" placeholder="Enter slogan" required readonly>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="leaderName">Leader Name</label>
                                            <input type="text" class="form-control" value="${club.clubLeaderId}" id="leaderName" name="leaderName" placeholder="Enter leader name" required readonly>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="clubEmail">Club Email</label>
                                            <input type="email" class="form-control" value="${club.clubEmail}" id="clubEmail" name="clubEmail" placeholder="Enter club email" required readonly>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea class="form-control" id="description" name="description" rows="4" placeholder="Enter club description" required readonly>${club.description}</textarea>
                                </div>
                                <div class="form-group">
                                    <label for="isPublic">Is Public</label>
                                    <input type="text" class="form-control" id="isPublic" name="isPublic" value="${club.isPublic eq 'yes' ? 'Public' : 'Private'}" readonly>
                                </div>

                                <div class="form-group">
                                    <label for="status">Status</label>
                                    <input type="text" class="form-control" id="status" name="status" value="${club.status eq 'Active' ? 'Active' : 'Inactive'}" readonly>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end view club detail-->


            <!-- start footer Area -->
           
                <!-- End footer Area -->

                <!-- JS -->
                <script src="${pageContext.request.contextPath}/assets/js/vendor/jquery-2.2.4.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
            <script src="${pageContext.request.contextPath}/assets/js/vendor/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/jquery.ajaxchimp.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/jquery.nice-select.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/jquery.sticky.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/nouislider.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
            <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjCGmQ0Uq4exrzdcL6rvxywDDOvfAu6eE"></script>
            <script src="${pageContext.request.contextPath}/assets/js/gmaps.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    </body>
</html>