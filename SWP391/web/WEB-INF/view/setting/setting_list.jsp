<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Settings Management</title>
        <style>
            /* Table Styling */
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }

            th, td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }

            /* Header of the table */
            th {
                background-color: #007bff;
                color: white;
                font-weight: bold;
                text-transform: uppercase;
            }

            /* Hover for rows */
            tr:hover {
                background-color: #f8f9fa;
            }

            /* Pagination */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 16px;
            }

            .pagination a {
                padding: 10px 16px;
                margin: 5px;
                text-decoration: none;
                border-radius: 6px;
                color: #007bff;
                border: 1px solid #007bff;
                transition: all 0.3s ease-in-out;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: white;
            }

            /* Active page */
            .pagination a.active {
                background-color: #0056b3;
                color: white;
                border-color: #0056b3;
                font-weight: bold;
            }

            .search-bar {
                display: flex;
                gap: 5px;
            }

            .search-bar input {
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #ccc;
            }

            .search-bar button {
                padding: 8px 12px;
                border: none;
                background-color: #007bff;
                color: white;
                border-radius: 5px;
                cursor: pointer;
            }

            .search-bar button:hover {
                background-color: #0056b3;
            }
        </style>

        <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
        <link rel="icon" href="${pageContext.request.contextPath}/assets/img/kaiadmin/favicon.ico" type="image/x-icon" />
        <!-- iziToast CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/izitoast/dist/css/iziToast.min.css">
        <script src="https://cdn.jsdelivr.net/npm/izitoast/dist/js/iziToast.min.js"></script>
        <script src="assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>
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
    </head>
    <body>

        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>
                <!-- End Sidebar -->

                <div class="main-panel">                    
                    <div class="container">
                        <div class="page-inner">
                            <div class="page-header">
                                <h3 class="fw-bold mb-3">Settings Management</h3>
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
                                        <a href="#">Settings Management</a>
                                    </li>
                                </ul>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card">
                                        <div class="card-header">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h4 class="card-title">Settings List</h4>

                                                <button type="button" class="btn btn-primary btn-sm" id="openAddSettingModal">
                                                    <i class="fas fa-plus"></i> Add New Setting
                                                </button>

                                            <%@ include file="setting_add.jsp" %>
                                        </div>
                                    </div>
                                    <!-- Modal -->
                                    <!-- Modal -->
                                    <div class="modal fade" id="addSettingModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="addModalLabel">Add New Setting</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <!-- ... existing form content ... -->
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                    <button type="submit" class="btn btn-primary" form="addSettingForm" onclick="validateForm()">Add</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Add Setting Modal -->


                                    <div class="card-body">
                                        <div class="col-md-6">
                                            <form method="get" action="settingList" class="d-flex align-items-center w-100 gap-2">
                                                <!-- Type Filter -->
                                                <div class="col-md-4">
                                                    <select id="type" class="form-select" name="type" onchange="this.form.submit()">
                                                        <option value="">All Types</option>
                                                        <option value="1" ${param.type eq '1' ? 'selected' : ''}>Role</option>
                                                        <option value="2" ${param.type eq '2' ? 'selected' : ''}>Club Category</option>
<!--                                                        <option value="3" ${param.type eq '3' ? 'selected' : ''}>Club Member</option>
                                                        <option value="4" ${param.type eq '4' ? 'selected' : ''}>User</option>-->
                                                    </select>
                                                </div>

                                                <!-- Status Filter -->
                                                <div class="col-md-4">
                                                    <select id="status" class="form-select" name="status" onchange="this.form.submit()">
                                                        <option value="">All Status</option>
                                                        <option value="Active" ${param.status eq 'Active' ? 'selected' : ''}>Active</option>
                                                        <option value="Inactive" ${param.status eq 'Inactive' ? 'selected' : ''}>Inactive</option>
                                                    </select>
                                                </div>

                                                <!-- Search Form -->
                                                <form action="settingList" method="get" class="d-flex">
                                                    <input type="hidden" name="page" value="1"> 
                                                    <input type="text" name="searchName" placeholder="Search by Name"
                                                           value="<%= request.getParameter("searchName") != null ? request.getParameter("searchName") : "" %>">
                                                    <button type="submit">Search</button>
                                                </form>
                                            </form>
                                        </div>

                                        <div class="table-responsive">
                                            <table id="settingTable" class="display table table-striped table-hover">
                                                <thead>
                                                    <tr>
                                                        <th class="text-center" style="width: 5%;">ID</th>
                                                        <th style="width: 15%; text-align: center;">Type</th>
                                                        <th style="width: 20%; text-align: center;">Name</th>
                                                        <th style="width: 20%; text-align: center;">Value</th>
                                                        <th class="text-center" style="width: 10%;">Priority</th>
                                                        <th class="text-center" style="width: 10%;">Status</th>
                                                        <th class="text-center" style="width: 20%;">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="setting" items="${settings}">
                                                        <tr>
                                                            <td class="text-center">${setting.setting_id}</td>
                                                            <td class="text-left pl-2">
                                                                <c:choose>
                                                                    <c:when test="${setting.type_id eq 1}">Role</c:when>
                                                                    <c:when test="${setting.type_id eq 2}">Club Category</c:when>
                                                                    <c:otherwise>${setting.type_id}</c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td class="text-left pl-2">${setting.name}</td>
                                                            <td class="text-left pl-2">${setting.value}</td>
                                                            <td class="text-center">${setting.priority}</td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${setting.status eq 'Active'}">
                                                                        <span class="badge badge-success"
                                                                              style="background-color: #28a745; color: white; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                                                                            Active
                                                                        </span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-danger"
                                                                              style="background-color: #dc3545; color: white; padding: 5px 10px; border-radius: 4px; font-size: 12px;">
                                                                            Inactive
                                                                        </span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td class="text-center">
                                                                <div class="d-flex align-items-center gap-2 justify-content-center">
                                                                    <!-- Nút Edit -->
                                                                    <a href="updateSetting?id=${setting.setting_id}" class="btn btn-sm btn-primary">
                                                                        <i class="fas fa-edit"></i>
                                                                    </a>

                                                                    <c:set var="currentQueryString" value="${pageContext.request.queryString}" />

                                                                    <c:choose>
                                                                        <c:when test="${setting.status eq 'Active'}">
                                                                            <form action="settingList?${currentQueryString}" method="post" style="display: inline;">
                                                                                <input type="hidden" name="action" value="deactivate">
                                                                                <input type="hidden" name="id" value="${setting.setting_id}">
                                                                                <button type="submit" class="btn btn-danger btn-sm"
                                                                                        onclick="return confirm('Are you sure you want to deactivate this setting?');">
                                                                                    <i class="fas fa-times"></i> Deactivate
                                                                                </button>
                                                                            </form>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <form action="settingList?${currentQueryString}" method="post" style="display: inline;">
                                                                                <input type="hidden" name="action" value="activate">
                                                                                <input type="hidden" name="id" value="${setting.setting_id}">
                                                                                <button type="submit" class="btn btn-success btn-sm"
                                                                                        onclick="return confirm('Are you sure you want to activate this setting?');">
                                                                                    <i class="fas fa-check"></i> Activate
                                                                                </button>
                                                                            </form>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>

                                        <!-- Pagination -->
                                        <div class="pagination-container mt-3">
                                            <nav>
                                                <ul class="pagination justify-content-center">
                                                    <!-- Nút Previous -->
                                                    <c:if test="${currentPage > 1}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="?page=${currentPage - 1}&type=${param.type}&status=${param.status}&searchName=${param.searchName}">
                                                                Previous
                                                            </a>
                                                        </li>
                                                    </c:if>

                                                    <!-- Hiển thị số trang -->
                                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                                            <a class="page-link" href="?page=${pageNum}&type=${param.type}&status=${param.status}&searchName=${param.searchName}">
                                                                ${pageNum}
                                                            </a>
                                                        </li>
                                                    </c:forEach>

                                                    <!-- Nút Next -->
                                                    <c:if test="${currentPage < totalPages}">
                                                        <li class="page-item">
                                                            <a class="page-link" href="?page=${currentPage + 1}&type=${param.type}&status=${param.status}&searchName=${param.searchName}">
                                                                Next
                                                            </a>
                                                        </li>
                                                    </c:if>
                                                </ul>
                                            </nav>
                                        </div>
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
                                    <a class="nav-link" href="http://www.themekita.com">
                                        ThemeKita
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#"> Help </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#"> Licenses </a>
                                </li>
                            </ul>
                        </nav>
                        <div class="copyright">
                            2024, made with <i class="fa fa-heart heart text-danger"></i> by
                            <a href="http://www.themekita.com">ThemeKita</a>
                        </div>
                        <div>
                            Distributed by
                            <a target="_blank" href="https://themewagon.com/">ThemeWagon</a>.
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <jsp:include page="setting_add.jsp"></jsp:include>

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

        <!--   Core JS Files   -->
        <script src="assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Kaiadmin JS -->
        <script src="assets/js/kaiadmin.min.js"></script>
        <!-- Kaiadmin DEMO methods, don't include it in your project! -->
        <script src="assets/js/setting-demo2.js"></script>

        <script>
                $(document).ready(function () {
                    $("#openAddSettingModal").click(function () {
                        $('#addSettingForm')[0].reset(); // Reset form when opening modal
                        $('.error').text(''); // Clear any previous error messages
                        $("#addSettingModal").modal("show");
                    });

                    // Ensure modal can be closed by close (x) button and cancel button
                    $('[data-dismiss="modal"]').click(function () {
                        $(this).closest('.modal').modal('hide');
                    });
                });
        </script>
    </body>
</html>