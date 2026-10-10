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

    <body>
        <!-- Toast Notification -->
        <div class="position-fixed" style="top: 80px; right: 20px; z-index: 9999;">

            <div id="toastSuccess" class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        Post added successfully!
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </div>

        <div class="wrapper">
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <div class="main-panel">
                    <div class="container">
                        <div class="page-inner">
                            <div class="page-header">
                                <h3 class="fw-bold mb-3">Post Details</h3>
                                <ul class="breadcrumbs mb-3">
                                    <li class="nav-home">
                                        <a href="home"><i class="icon-home"></i></a>
                                    </li>
                                    <li class="separator"><i class="icon-arrow-right"></i></li>
                                    <a href="PostList?user_Id=${post.getUserId()}">Posts Management</a>
                                <li class="separator"><i class="icon-arrow-right"></i></li>
                                <li class="nav-item"><a href="#">Post Details</a></li>
                            </ul>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h4 class="card-title">Post Information</h4>
                                    </div>
                                    <div class="card-body">
                                        <form action="AddPost" method="post" enctype="multipart/form-data"> 
                                            <c:if test="${not empty error}">
                                                <h4 class="text-danger">${error}</h4>
                                            </c:if>
                                            <div class="form-group mb-3">

                                                <input type="hidden" class="form-control" name="userId" value="${userId}" readonly />
                                            </div>


                                            <div class="form-group mb-3">
                                                <label class="form-label">Club Name</label>
                                                <select class="form-control" name="clubId">
                                                    <c:forEach var="club" items="${clubs}">
                                                        <option value="${club.club_id}" ${club.club_id == clubId ? 'selected' : ''}>
                                                            ${club.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group mb-3">
                                                <label class="form-label">Title</label>
                                                <input type="text" class="form-control" name="title" value="${title}" />
                                                <c:if test="${not empty error}">
                                                    <div class="text-danger">${error}</div>
                                                </c:if>
                                            </div>
                                            <div class="form-group mb-3">
                                                <label class="form-label">Category</label>
                                                <select name="category">
                                                    <c:forEach var="category" items="${categories}">
                                                        <option value="${category.settingId}" ${category.settingId == categoryId ? 'selected' : ''}>
                                                            ${category.value}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group mb-3">
                                                <label class="form-label">Image</label>
                                                <br>
                                                <!-- Hiển thị ảnh đã tải lên nếu có, hoặc ảnh mặc định nếu không có -->
                                                <img id="previewImage" alt="Post Image" class="img-thumbnail" width="250" 
                                                     src="${imageUrl != null ? '/yourAppName/' + imageUrl : '/yourAppName/uploads/default.jpg'}">

                                                <!-- Chọn ảnh mới -->
                                                <input type="file" class="form-control mt-2" id="imageUpload" name="imageFile" accept="image/*" onchange="previewImage(event)" />

                                                <!-- Lưu lại URL của ảnh đã chọn -->
                                                <input type="hidden" name="imageUrl" id="imageUrl" value="${imageUrl != null ? imageUrl : ''}" />
                                            </div>
                                            <div class="form-group mb-3">
                                                <label class="form-label">Description</label>
                                                <textarea class="form-control" rows="5" name="description">${description != null ? description : ''}</textarea>
                                            </div>

                                            <div class="form-group mb-3">
                                                <label class="form-label">Status</label><br>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="status" value="Published" ${status == 'Published' ? 'checked' : ''}>
                                                    <label class="form-check-label">Published</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="status" value="Unpublished" ${status == 'Unpublished' ? 'checked' : ''}>
                                                    <label class="form-check-label">Unpublished</label>
                                                </div>
                                                <div class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="status" value="Draft" ${status == 'Draft' ? 'checked' : ''}>
                                                    <label class="form-check-label">Draft</label>
                                                </div>
                                            </div>


                                            <div class="form-group text-start">
                                                <a href="PostList?user_Id=${userId}" class="btn btn-secondary">Back to Posts</a>
                                                <button type="submit" class="btn btn-primary">Add Post</button>
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


        <script src="assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>
    </body>
</html>
