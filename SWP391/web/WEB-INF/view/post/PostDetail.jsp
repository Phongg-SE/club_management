<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Post Details</title>
        <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
    </head>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <body>

        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->
                <div class="header-bar d-flex justify-content-between border-bottom">
                    <div class="d-flex align-items-center">
                        <a href="#" class="logo-icon">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-icon.png" height="30" class="small" alt="">
                        <span class="big">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-dark.png" height="24" class="logo-light-mode" alt="">
                            <img src="${pageContext.request.contextPath}/assets/images/logo-light.png" height="24" class="logo-dark-mode" alt="">
                        </span>
                    </a>
                    <a id="close-sidebar" class="btn btn-icon btn-pills btn-soft-primary ms-2" href="#">
                        <i class="uil uil-bars"></i>
                    </a>
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
<!--                                        <small class="text-muted">${account.role_name}</small>-->
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
                                    
            <div class="main-panel">
                <div class="container">
                    <div class="page-inner">
                        <div class="page-header">
                            <h3 class="fw-bold mb-3">
                                Post Details
                            </h3>
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
                                    <a href="PostList">Post List</a>
                                </li>
                                <li class="separator">
                                    <i class="icon-arrow-right"></i>
                                </li>
                                <li class="nav-item">
                                    <a href="#">Post Details</a>
                                </li>
                            </ul>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h4 class="card-title">Post Information</h4>
                                    </div>
                                    <div class="card-body">
                                        <form action="UpdatePost" method="post" enctype="multipart/form-data">
                                            <c:if test="${not empty post}">


                                                <div class="form-group mb-3">

                                                    <input type="hidden" class="form-control" value="${post.userId}"name="userId" readonly />
                                                </div>
                                                <div class="form-group mb-3">

                                                    <input type="hidden" class="form-control" value="${post.clubId}" name="clubId" readonly />
                                                </div>
                                                <div class="form-group mb-3">

                                                    <input type="hidden" class="form-control" value="${post.postId}"name="postId" readonly />
                                                </div>
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Club Name</label>
                                                    <input type="text" class="form-control" value="${post.getClubName()}" name="clubName" readonly/>
                                                </div>



                                                <!-- Post Title -->
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Title</label>
                                                    <input type="text" class="form-control ${not empty requestScope.errors.title ? 'is-invalid' : ''}" 
                                                           name="title" 
                                                           value="${not empty param.title ? param.title : (not empty requestScope.post ? requestScope.post.title : '')}" />
                                                    <div class="invalid-feedback">${requestScope.errors.title}</div>
                                                </div>
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Category</label>
                                                    <input type="text" class="form-control" value="${post.category}"name="category" readonly />
                                                </div>

                                                <!-- Post Image -->
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Image</label>
                                                    <br>
                                                    <!-- Ảnh xem trước -->
                                                    <img id="previewImage" src="${post.imageUrl}" alt="Post Image" class="img-thumbnail" width="250">

                                                    <!-- Input file cho người dùng chọn ảnh -->
                                                    <input type="file" class="form-control mt-2" id="imageUpload" name="imageFile" accept="image/*" />

                                                    <!-- Ẩn đường dẫn ảnh cũ để lưu nếu không chọn ảnh mới -->
                                                    <input type="hidden" name="imageUrl" id="imageUrl" value="${post.imageUrl}" />
                                                </div>


                                                <!-- Description -->
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Description</label>
                                                    <textarea class="form-control ${not empty requestScope.errors.description ? 'is-invalid' : ''}" 
                                                              rows="5" name="description">${not empty param.description ? param.description : (not empty requestScope.post ? requestScope.post.description : '')}</textarea>
                                                    <div class="invalid-feedback">${requestScope.errors.description}</div>
                                                </div>
                                                <!-- Author -->
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Author</label>
                                                    <input type="text" class="form-control" value="${post.author}" name="author" readonly />
                                                </div>


                                                <!-- Status -->
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Status</label><br>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="status" value="Published" ${post.status == 'Published' ? 'checked' : ''}>
                                                        <label class="form-check-label">Published</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="status" value="Unpublished" ${post.status == 'Unpublished' ? 'checked' : ''}>
                                                        <label class="form-check-label">Unpublished</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="status" value="Draft" ${post.status == 'Draft' ? 'checked' : ''}>
                                                        <label class="form-check-label">Draft</label>
                                                    </div>
                                                </div>

                                                <!-- Posted Date -->
                                                <div class="form-group mb-3">
                                                    <label class="form-label">Posted At</label>
                                                    <input type="text" class="form-control" value="${post.postedAt}" name="postedAt" readonly/>
                                                </div>
                                            </c:if>

                                            <div class="form-group text-start">
                                                <a href="PostList?user_Id=${post.getUserId()}" class="btn btn-secondary">Back to Posts</a>
                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <footer class="footer">
                    <div class="container-fluid d-flex justify-content-between">
                        <nav class="pull-left">
                            <ul class="nav">
                                <li class="nav-item"><a class="nav-link" href="#">Help</a></li>
                                <li class="nav-item"><a class="nav-link" href="#">Licenses</a></li>
                            </ul>
                        </nav>
                        <div class="copyright">
                            2024, made with <i class="fa fa-heart heart text-danger"></i>.
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script>
            document.getElementById("imageUpload").addEventListener("change", function (event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        document.getElementById("previewImage").src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            });
        </script>

        <!-- JS Files -->
        <script src="assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>
    </body>
</html>
