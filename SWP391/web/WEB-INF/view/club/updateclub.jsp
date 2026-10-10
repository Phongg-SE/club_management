

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Update Club</title>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />
    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->

                <div class="main-panel">
                    <div class="container-fluid">
                        <div class="page-inner">
                            <div class="page-header">
                                <h3 class="fw-bold mb-3">Update Club</h3>
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
                                        <a href="clublist">Club Management</a>
                                    </li>
                                    <li class="separator">
                                        <i class="icon-arrow-right"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#">Update Club</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-body">
                                            <form action="updateclub" method="post" enctype="multipart/form-data">
                                                <div class="row g-3">
                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <input type="hidden" name="id" value="${club.club_id}">
                                                        <label for="clubName" class="form-label">Club Name</label>
                                                        <input type="text" class="form-control" value="${club != null ? club.name : ''}" id="clubName" name="clubName" placeholder="Enter Club Name" required>
                                                        <c:forEach var="msg" items="${msgList}">
                                                            <c:if test="${msg eq 'Club name already exists.' || msg eq 'Club name cannot be empty or spaces only.' || msg eq 'Club name must not exceed 50 characters.'}">
                                                                <span class="text-danger">${msg}</span><br>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="categoryName" class="form-label">Category</label>
                                                        <select class="form-select" id="categoryName" name="categoryId" required>
                                                            <option value="">Select Category</option>
                                                            <c:forEach var="setting" items="${setting}">
                                                                <option value="${setting.settingId}" ${club != null && club.category_id == setting.settingId ? "selected" : ""}>
                                                                    ${setting.value}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="clubLeader" class="form-label">Club Leader</label>
                                                        <select class="form-select" id="clubLeader" name="clubLeaderId">
                                                            <option value="">Select Leader</option>
                                                            <c:forEach var="leader" items="${users}">
                                                                <option value="${leader.user_id}" 
                                                                        ${club != null && club.leader_id == leader.user_id ? "selected" : ""}>
                                                                    ${leader.full_name}
                                                                </option>
                                                            </c:forEach>
                                                        </select>

                                                        <!-- Input ẩn để lưu leader ID hiện tại -->
                                                        <input type="hidden" id="oldLeaderId" name="oldLeaderId" value="${club.leader_id}">
                                                    </div>
                                                </div>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="clubLogo" class="form-label">Upload Logo</label>
                                                        <input type="file" class="form-control" id="clubLogo" name="clubLogo" accept="image/*">
                                                        <img id="previewImage" src="#" alt="Preview Image" class="img-thumbnail mt-2" style="display: none; max-width: 200px;">
                                                        <c:forEach var="msg" items="${msgList}">
                                                            <c:if test="${msg eq 'Invalid image URL.'}">
                                                                <span class="text-danger">${msg}</span><br>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                    <input type="hidden" name="oldLogoUrl" value="${club.club_logo_url}">
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="slogan" class="form-label">Slogan</label>
                                                        <input type="text" class="form-control" value="${club != null ? club.slogan : ''}" id="slogan" name="slogan" placeholder="Enter Slogan" required>
                                                        <c:forEach var="msg" items="${msgList}">
                                                            <c:if test="${msg eq 'Slogan must not exceed 150 characters.' || msg eq 'Sologan cannot be empty or spaces only.'}">
                                                                <span class="text-danger">${msg}</span><br>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label for="clubEmail" class="form-label">Club Email</label>
                                                        <input type="email" class="form-control" value="${club != null ? club.club_email : ''}" id="clubEmail" name="clubEmail" placeholder="Enter Club Email" required>
                                                        <c:forEach var="msg" items="${msgList}">
                                                            <c:if test="${msg eq 'Invalid email address.' || msg eq 'Email is already in use by another club.'}">
                                                                <span class="text-danger">${msg}</span><br>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="col-md-12">
                                                    <div class="form-group">
                                                        <label for="description" class="form-label">Description</label>
                                                        <textarea class="form-control" id="description" name="description" rows="2" placeholder="Enter Club Description" required>${club != null ? club.description : ''}</textarea>
                                                        <c:forEach var="msg" items="${msgList}">
                                                            <c:if test="${msg eq 'Description cannot be empty or spaces only.'}">
                                                                <span class="text-danger">${msg}</span><br>
                                                            </c:if>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label class="form-label">Is Public</label><br>
                                                            <div class="form-check form-check-inline">
                                                                <input type="radio" id="publicYes" name="isPublic" value="Yes" class="form-check-input"
                                                                       ${club != null && "Yes".equalsIgnoreCase(club.is_public) ? "checked" : ""}>
                                                                <label for="publicYes" class="form-check-label">Public</label>
                                                            </div>
                                                            <div class="form-check form-check-inline">
                                                                <input type="radio" id="publicNo" name="isPublic" value="No" class="form-check-input"
                                                                       ${club != null && "No".equalsIgnoreCase(club.is_public) ? "checked" : ""}>
                                                                <label for="publicNo" class="form-check-label">Private</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="form-group">
                                                            <label class="form-label">Status</label><br>
                                                            <div class="form-check form-check-inline">
                                                                <input type="radio" id="statusActive" name="status" value="Active" class="form-check-input"
                                                                       ${club != null && "Active".equalsIgnoreCase(club.status) ? "checked" : ""}>
                                                                <label for="statusActive" class="form-check-label">Active</label>
                                                            </div>
                                                            <div class="form-check form-check-inline">
                                                                <input type="radio" id="statusInactive" name="status" value="Inactive" class="form-check-input"
                                                                       ${club != null && "Inactive".equalsIgnoreCase(club.status) ? "checked" : ""}>
                                                                <label for="statusInactive" class="form-check-label">Inactive</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-12 text-center mt-3">
                                                    <button type="submit" class="btn btn-primary">Update Club</button>
                                                    <a href="clublist" class="btn btn-secondary">Cancel</a>
                                                </div>
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

            <!-- JS Files -->
            <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
            <!--preview image-->
            <script>
            document.getElementById('clubLogo').addEventListener('change', function (event) {
                var file = event.target.files[0]; // Lấy file được chọn
                var preview = document.getElementById('previewImage');

                if (file) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result; // Hiển thị ảnh
                        preview.style.display = "block"; // Hiện ảnh xem trước
                    };
                    reader.readAsDataURL(file);
                } else {
                    preview.style.display = "none"; // Ẩn nếu không có file
                }
            });
            </script>



    </body>
</html>

