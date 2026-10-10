<%-- 
    Document   : setting_details
    Created on : Feb 3, 2025, 11:28:42 AM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Setting Details</title>
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
        <!-- iziToast CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/izitoast/dist/css/iziToast.min.css">
        <script src="https://cdn.jsdelivr.net/npm/izitoast/dist/js/iziToast.min.js"></script>
        <script src="assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/plugins.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/toast.css" />

        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />

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
        <link href="${pageContext.request.contextPath}/assets/css/remixicon.css" rel="stylesheet" type="text/type" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- Css -->
        <link href="${pageContext.request.contextPath}/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />
    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->

                <div class="header-bar d-flex justify-content-between border-bottom">
                    <!-- Header content remains unchanged -->
                    <!-- (Existing header code) -->
                </div>
                <div class="main-panel">                    
                    <div class="container-fluid">
                        <div class="page-inner">
                            <div class="page-header">
                                <h3 class="fw-bold mb-3">Update Setting</h3>
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
                                        <a href="settingList">Settings Management</a>
                                    </li>
                                    <li class="separator">
                                        <i class="icon-arrow-right"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#">Update Setting</a>
                                    </li>
                                </ul>
                            </div>
                            <!--update Setting-->
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div>
                                            <h4 style="color: red" >${msg}</h4>
                                    </div>
                                    <div class="card-header">
                                        <h4 class="card-title">Setting Details </h4>
                                    </div>
                                    <div class="card-body">
                                        <!-- Form cập nhật setting -->
                                        <form action="updateSetting" method="POST">
                                            <input type="hidden" name="id" value="${setting.getSetting_id()}">
                                            <div class="row">
                                                <div class="col-md-12 mb-3">
                                                    <label for="name" class="form-label fw-bold">Name <span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="name" name="name" value="${setting.name}" required>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12 mb-3">
                                                    <label for="description" class="form-label fw-bold">Description <span class="text-danger">*</span></label>
                                                    <textarea class="form-control" id="description" name="description" rows="4" required>${setting.description}</textarea>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 mb-3">
                                                    <label for="type_id" class="form-label fw-bold">Type <span class="text-danger">*</span></label>
                                                    <select class="form-select" id="type_id" name="type_id" required>
                                                        <option value="1" ${setting.type_id eq 1 ? 'selected' : ''}>Role</option>
                                                        <option value="2" ${setting.type_id eq 2 ? 'selected' : ''}>Club Category</option>
<!--                                                        <option value="3" ${setting.type_id eq 3 ? 'selected' : ''}>Club Member</option>
                                                        <option value="4" ${setting.type_id eq 4 ? 'selected' : ''}>User</option>-->
                                                    </select>
                                                </div>
                                                <div class="col-md-6 mb-3">
                                                    <label for="value" class="form-label fw-bold">Value <span class="text-danger">*</span></label>
                                                    <input type="text" class="form-control" id="value" name="value" value="${setting.value}" required>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6 mb-3">
                                                    <label for="priority" class="form-label fw-bold">Priority <span class="text-danger">*</span></label>
                                                    <input type="number" class="form-control" id="priority" name="priority"
                                                           value="${setting.priority}" required min="1" max="10" 
                                                           onkeydown="return false;" onpaste="return false;">
                                                </div>
                                                <div class="col-md-6 mb-3">
                                                    <label for="status" class="form-label fw-bold">Status <span class="text-danger">*</span></label>
                                                    <select class="form-select" id="status" name="status" required>
                                                        <option value="Active" ${setting.status eq 'Active' ? 'selected' : ''}>Active</option>
                                                        <option value="Inactive" ${setting.status eq 'Inactive' ? 'selected' : ''}>Inactive</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-12 text-end">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="fas fa-save"></i> Save Changes
                                                    </button>
                                                    <a href="settingList?page=${sessionScope.currentPage}&type=${sessionScope.type}&status=${sessionScope.status}&searchName=${sessionScope.searchName}" class="btn btn-secondary">
                                                        Cancel
                                                    </a>

                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <footer class="footer">
                        <!-- Footer content remains unchanged -->
                        <!-- (Existing footer code) -->
                    </footer>
                </div>
            </div>
        </div>

        <!-- JS Files -->
        <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>

        <!-- Retain all existing scripts from the original file -->
        <!-- jQuery UI -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>

        <!-- Bootstrap Notify -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/bootstrap-notify/bootstrap-notify.min.js"></script>

        <!-- Sweet Alert -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/sweetalert/sweetalert.min.js"></script>

        <!-- Form validation -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/jquery.validate/jquery.validate.min.js"></script>

        <!-- Select2 -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/select2/select2.full.min.js"></script>

        <!-- Flatpickr -->
        <script src="${pageContext.request.contextPath}/assets/js/plugin/flatpickr/flatpickr.min.js"></script>

        <!-- Custom JS -->
        <script src="${pageContext.request.contextPath}/assets/js/kaiadmin.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/demo.js"></script>

        <!-- iziToast JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/js/iziToast.min.js"></script>

        <!-- Toast message display -->
        <c:if test="${not empty sessionScope.toastMessage}">
            <script>
            $(document).ready(function () {
                // Lấy dữ liệu từ session
                var toastMessage = "${sessionScope.toastMessage}";
                var toastType = "${sessionScope.toastType}";

                if (toastMessage) {
                    console.log("DEBUG: Toast Message =", toastMessage);
                    console.log("DEBUG: Toast Type =", toastType);

                    // Hiển thị iziToast
                    iziToast.show({
                        title: toastType === 'success' ? 'Success' : 'Error',
                        message: toastMessage,
                        position: 'topRight',
                        color: toastType === 'success' ? 'green' : 'red',
                        timeout: 1500,
                        onClosing: function () {
                            // Gửi request đến servlet để xóa session
                            fetch('${pageContext.request.contextPath}/remove-toast', {
                                method: 'POST'
                            }).then(response => {
                                if (response.ok) {
                                    console.log('Toast session removed successfully');
                                } else {
                                    console.error('Failed to remove toast attributes');
                                }
                            }).catch(error => {
                                console.error('Error:', error);
                            });
                        }
                    });
                }
            });
            </script>
        </c:if>
    </body>
</html>