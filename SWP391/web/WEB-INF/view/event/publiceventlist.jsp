<!DOCTYPE html>
<html lang="en">
    <%@ page import="java.util.List, model.entity.Event" %>
    <%@ page import="java.text.SimpleDateFormat" %>
    <%@ page import="java.util.Date" %>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Public events</title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="assets/js/html5shiv.min.js"></script>
        <script src="assets/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">

    </head>
    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>

            <!-- Header Top ==== -->
            <jsp:include page="../component/header.jsp"></jsp:include>
                <!-- header END ==== -->

                <div class="page-content bg-white">
                    <!-- Inner Page Banner -->
                <%
    List<Event> events = (List<Event>) request.getAttribute("events");
    int currentPage = (int) request.getAttribute("currentPage");
    int totalPages = (int) request.getAttribute("totalPages");
                %>

                <div class="content-block section-area section-sp1">
                    <div class="container">
                        <div class="row">
                            <!-- Main Event List -->
                            <div class="col-lg-8">
                                <div class="heading-bx mb-4">
                                    <h2 class="title-head">Public Events</h2>
                                    <div class="ttr-separator seprator-primary"></div>

                                    <!-- Form lọc sự kiện theo câu lạc bộ -->
                                    <form action="PublicEventList" method="get" class="d-flex flex-wrap align-items-center gap-3">
                                        <div class="col-md-4 d-flex flex-column">
                                            <label for="clubid" class="form-label fw-bold mb-1">Filter By Club</label>
                                            <select id="clubid" class="form-select" name="clubid" onchange="this.form.submit()" 
                                                    style="font-weight: ${!empty param.clubid ? '700' : '500'};
                                                    color: ${!empty param.clubid ? '#4c1864' : '#333'};
                                                    border: 1px solid #4c1864;">
                                                <option value="" style="font-weight: normal;">Select Club</option>
                                                <c:forEach var="club" items="${clubsToFilter}">
                                                    <option value="${club.getClub_id()}" 
                                                            ${param.clubid eq club.getClub_id().toString() ? 'selected' : ''} 
                                                            style="${param.clubid eq club.getClub_id().toString() ? 'font-weight: 700; color: #4c1864; background-color: #f0e6ff;' : 'font-weight: normal;'}">
                                                        ${club.getName()}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6 d-flex flex-column">
                                            <label for="search" class="form-label fw-bold mb-1">Search Event</label>
                                            <div class="input-group">
                                                <input type="text" name="search" id="search" value="${param.search}" placeholder="Search By Event Name" class="form-control">
                                                <button type="submit" class="btn btn-primary">
                                                    <i class="fa fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>

                                <% if (events != null && !events.isEmpty()) { %>
                                <div class="row">
                                    <% for (Event event : events) { %>
                                    <div class="col-md-12">
                                        <div class="card event-card mb-4 overflow-hidden">
                                            <div class="row no-gutters">
                                                <div class="col-md-4">
                                                    <a href="publiceventdetail?event_id=<%= event.getEvent_id() %>" class="event-img-link">
                                                        <img src="<%= event.getAvatar_url() %>" class="card-img h-100 event-thumbnail" alt="<%= event.getName() %>">
                                                        <div class="event-date-badge">
                                                            <% String[] dateParts = event.getStart_time().split(" ")[0].split("/");
                                                               String day = dateParts[0];
                                                               String month = dateParts[1]; %>
                                                            <span class="date-day"><%= day %></span>
                                                            <span class="date-month"><%= month %></span>
                                                        </div>
                                                    </a>
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-body py-3">
                                                        <h5 class="card-title font-weight-bold mb-2">
                                                            <a href="publiceventdetail?event_id=<%= event.getEvent_id() %>" class="text-primary">
                                                                <%= event.getName() %>
                                                            </a>
                                                        </h5>
                                                        <div class="event-meta mb-2">
                                                            <span class="event-info mr-3">
                                                                <i class="fa fa-calendar-o text-primary"></i> <%= event.getStart_time() %>
                                                            </span>
                                                            <span class="event-info mr-3">
                                                                <i class="fa fa-clock-o text-primary"></i> <%= event.getEnd_time() %>
                                                            </span>
                                                            <span class="event-info">
                                                                <i class="fa fa-map-marker text-primary"></i> <%= event.getLocation() %>
                                                            </span>
                                                        </div>
                                                        <a href="publiceventdetail?event_id=<%= event.getEvent_id() %>" class="btn button-md btn-primary rounded-pill">View More <i class="fa fa-arrow-right ml-1"></i></a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <% } %>
                                </div>

                                <!-- Pagination Centered -->
                                <nav class="d-flex justify-content-center mt-4">
                                    <ul class="pagination pagination-sm">
                                        <c:if test="${currentPage > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="?clubid=${param.clubid}&search=${param.search}&page=${currentPage - 1}">Previous</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="?clubid=${param.clubid}&search=${param.search}&page=${i}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${currentPage < totalPages}">
                                            <li class="page-item">
                                                <a class="page-link" href="?clubid=${param.clubid}&search=${param.search}&page=${currentPage + 1}">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>

                                <% } else { %>
                                <div class="alert alert-info text-center p-4">
                                    <i class="fa fa-info-circle fa-2x mb-3"></i>
                                    <h5>No events available at the moment</h5>
                                    <p>Check back soon for upcoming events!</p>
                                </div>
                                <% } %>
                            </div>

                            <!-- Upcoming Events Sidebar -->
                            <div class="col-lg-4">
                                <div class="heading-bx mb-4">
                                    <h3 class="title-head">Upcoming Events</h3>
                                    <div class="ttr-separator seprator-primary"></div>
                                </div>
                                <% if (events != null && !events.isEmpty()) { %>
                                <% for (Event event : events) { %>
                                <div class="upcoming-event-item shadow-sm p-3 mb-3 bg-white rounded d-flex align-items-center">
                                    <div class="upcoming-date-box text-center me-3">
                                        <div class="month"><%= event.getStart_time().split(" ")[0].split("/")[1] %></div>
                                        <div class="day"><%= event.getStart_time().split(" ")[0].split("/")[0] %></div>
                                    </div>
                                    <div class="upcoming-info">
                                        <h6 class="mb-1 fw-semibold text-dark">
                                            <a href="publiceventdetail?event_id=<%= event.getEvent_id() %>" class="text-decoration-none text-dark">
                                                <%= event.getName() %>
                                            </a>
                                        </h6>
                                        <div class="text-muted small"><i class="fa fa-clock-o me-1"></i> <%= event.getStart_time() %></div>
                                        <div class="text-muted small"><i class="fa fa-map-marker me-1"></i> <%= event.getLocation() %></div>
                                    </div>
                                </div>
                                <% } %>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

                </aside>
            </div>
        </div>
    </div>
</div>

<!-- Additional CSS to be added to your style.css -->
<style>
    .event-card {
        transition: transform 0.3s, box-shadow 0.3s;
        border: none;
        box-shadow: 0 5px 15px rgba(0,0,0,0.05);
    }

    .event-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 25px rgba(0,0,0,0.1);
    }

    .event-img-link {
        position: relative;
        display: block;
        height: 100%;
        overflow: hidden;
    }

    .event-thumbnail {
        object-fit: cover;
        transition: transform 0.5s;
    }

    .event-img-link:hover .event-thumbnail {
        transform: scale(1.1);
    }

    .event-date-badge {
        position: absolute;
        top: 15px;
        left: 15px;
        background-color: rgba(255,255,255,0.95);
        border-radius: 5px;
        padding: 5px 12px;
        text-align: center;
        box-shadow: 0 3px 10px rgba(0,0,0,0.1);
    }

    .date-day {
        display: block;
        font-size: 20px;
        font-weight: 700;
        line-height: 1;
        color: #f7b205;
    }

    .date-month {
        display: block;
        font-size: 12px;
        text-transform: uppercase;
        font-weight: 600;
        color: #333;
    }

    .event-meta {
        color: #777;
        font-size: 14px;
    }

    .event-description {
        color: #666;
        font-size: 15px;
        line-height: 1.6;
    }

    .upcoming-event-img {
        position: relative;
        width: 80px;
        height: 80px;
    }

    .event-mini-date {
        position: absolute;
        bottom: 0;
        right: 0;
        background-color: #f7b205;
        color: white;
        padding: 2px 6px;
        border-radius: 3px;
        font-size: 10px;
        line-height: 1;
        text-align: center;
    }

    .event-mini-date span {
        display: block;
    }

    .event-mini-date span:first-child {
        font-weight: 700;
        font-size: 12px;
    }

    .category-list .list-group-item:hover {
        background-color: #f8f9fa;
    }

    .pagination .page-link {
        color: #4c1864;
        border-radius: 3px;
        margin: 0 3px;
    }

    .pagination .page-item.active .page-link {
        background-color: #4c1864;
        border-color: #4c1864;
    }

    .pagination .page-link:hover {
        background-color: #f0e6f7;
    }

    .upcoming-event-info {
        flex: 1;
    }
</style>




<!-- Side bar END -->

<!-- Content END-->
<!-- Footer ==== -->
<jsp:include page="../component/footer.jsp"></jsp:include>
<!-- Footer END ==== -->
<!-- scroll top button -->
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
</body>

</html>
<style>
    /* Upcoming Events Section */
    .upcoming-event-item {
        display: flex;
        align-items: center;
        padding: 12px;
        background-color: #fff;
        border: 1px solid #eee;
        border-left: 5px solid #4c1864;
        border-radius: 8px;
        transition: all 0.3s ease;
    }

    .upcoming-event-item:hover {
        background-color: #fafafa;
        border-left: 5px solid #f7b205;
    }

    .upcoming-date-box {
        width: 60px;
        min-width: 60px;
        background-color: #4c1864;
        color: white;
        padding: 10px 5px;
        border-radius: 6px;
        text-align: center;
    }

    .upcoming-date-box .day {
        font-size: 22px;
        font-weight: bold;
    }

    .upcoming-date-box .month {
        font-size: 13px;
        text-transform: uppercase;
    }

    /* Tạo khoảng cách giữa ô thời gian và phần nội dung */
    .upcoming-info {
        margin-left: 15px; /* Tăng khoảng cách giữa ngày tháng và nội dung */
        flex-grow: 1;
    }

    .upcoming-info h6 {
        font-size: 16px;
        margin-bottom: 6px;
    }

    .upcoming-info .text-muted {
        font-size: 14px;
    }

    .upcoming-info .text-muted i {
        margin-right: 5px;
    }

    .text-dark:hover {
        color: #4c1864 !important;
        text-decoration: underline;
    }

</style>