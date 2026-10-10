
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.Club" %>
<%@ page import="model.entity.Setting" %>
<head>
    <link rel="icon" href="assets/img/logofpt.png" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/fpt.png" />
</head>
<%
    List<Club> clubList = (List<Club>) request.getAttribute("clubList");
    String search = (String) request.getAttribute("search");
    if (search == null) search = "";
    
    Integer categoryId = (Integer) request.getAttribute("categoryId");
    if (categoryId == null) categoryId = -1;
    
    int currentPage = (Integer) request.getAttribute("currentPage");
    int totalPages = (Integer) request.getAttribute("totalPages");
%>

<!DOCTYPE html>
<html lang="en">

    <body id="bg">
        <jsp:include page="../component/header.jsp"></jsp:include>

            <!-- Content -->

            <div class="container mt-5">

                <h1 class="text-center mb-4">Public Club List</h1>

                <!-- FORM BỘ LỌC -->
                <form method="GET" action="PublicClub" id="filterForm">
                    <div class="row mb-3 justify-content-end">
                        <!-- Thanh Search nằm trên cùng và căn phải -->
                        <div class="col-md-4 ms-auto">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control"
                                       value="<%= search %>"
                                placeholder="Search by club name">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-2">
                        <h5 class="mb-3">Category</h5>

                        <!-- Tùy chọn "All Categories" -->
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="category" value="-1"
                                   <%= (categoryId == -1) ? "checked" : "" %>
                                   onchange="this.form.submit()">
                            <label class="form-check-label">All Categories</label>
                        </div>

                        <!-- Hiển thị danh sách category từ DB -->
                        <% 
                            List<Setting> categoryList = (List<Setting>) request.getAttribute("categoryList");
                            if (categoryList != null) {
                                for (Setting category : categoryList) { 
                        %>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="category" value="<%= category.getSettingId() %>"
                                   <%= (categoryId == category.getSettingId()) ? "checked" : "" %>
                                   onchange="this.form.submit()">
                            <label class="form-check-label"><%= category.getValue() %></label>
                        </div>
                        <% } } %>
                    </div>

                    <div class="col-md-10">
                        <div class="row" id="club-list">
                            <!-- Kiểm tra nếu danh sách trống -->
                            <c:if test="${empty clubList}">
                                <p class="text-center">Không có CLB nào.</p>
                            </c:if>
                            <c:forEach var="club" items="${clubList}">
                                <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12 mb-4 club-item">
                                    <div class="card">
                                        <img src="${club.club_logo_url}" class="card-img-top" alt="Club Logo" style="height: 300px; object-fit: cover;">
                                        <div class="card-body">
                                            <h5 class="card-title">
                                                <a href="PublicClubDetail?id=${club.club_id}">${club.name}</a>
                                            </h5>
                                            <p class="card-text">${club.slogan}</p>
                                            <a href="PublicClubDetail?id=${club.club_id}" class="btn btn-primary btn-sm">Learn More</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- PHÂN TRANG -->
                        <% 
                            String queryString = "search=" + (search != null ? URLEncoder.encode(search, "UTF-8") : "") + 
                                                 "&category=" + categoryId;
                        %>

                        <div class="text-center mt-4">
                            <nav>
                                <ul class="pagination d-inline-flex">
                                    <%-- Nút Previous --%>
                                    <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                                        <a class="page-link" href="PublicClub?page=<%= currentPage - 1 %>&<%= queryString %>">Previous</a>
                                    </li>

                                    <%-- Duyệt qua từng số trang --%>
                                    <% for (int i = 1; i <= totalPages; i++) { %>
                                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                        <a class="page-link" href="PublicClub?page=<%= i %>&<%= queryString %>"><%= i %></a>
                                    </li>
                                    <% } %>

                                    <%-- Nút Next --%>
                                    <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                                        <a class="page-link" href="PublicClub?page=<%= currentPage + 1 %>&<%= queryString %>">Next</a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </form>

            <!-- Content END-->
            <!-- Footer ==== -->
            <!-- Footer END ==== -->
            <!-- scroll top button -->
            <button class="back-to-top fa fa-chevron-up" ></button>
        </div>
        <jsp:include page="../component/footer.jsp"></jsp:include>
        
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
    </body>

    <style>
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

    </style>


</html>
