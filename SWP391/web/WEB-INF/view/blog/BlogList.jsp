<!DOCTYPE html>
<html lang="en">
    <%@ page import="java.util.List, model.entity.Post" %>
    <%@ page import="java.text.SimpleDateFormat" %>
    <%@ page import="java.util.Date" %>

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
        <title>Blogs</title>

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
            <header class="header rs-nav">
                <jsp:include page="../component/header.jsp"></jsp:include>
                    <!-- Search Box ==== -->
                    <div class="nav-search-bar">
                        <form action="#">
                            <input name="search" value="" type="text" class="form-control" placeholder="Type to search">
                            <span><i class="ti-search"></i></span>
                        </form>
                        <span id="search-remove"><i class="ti-close"></i></span>
                    </div>
                    <!-- Navigation Menu ==== -->
                    <div class="menu-links navbar-collapse collapse justify-content-start" id="menuDropdown">
                        <div class="menu-logo">
                            <a href="index.html"><img src="assets/images/logo.png" alt=""></a>
                        </div>
                        <ul class="nav navbar-nav">	
                            <li class=""><a href="home">Home</a></li>
                        </ul>

                    </div>
                    <!-- Navigation Menu END ==== -->
            </div>
        </div>
    </div>
</header>
<!-- header END ==== -->
<div class="page-content bg-white">
    <!-- Inner Page Banner -->

<%
List<Post> blogs = (List<Post>) request.getAttribute("blogs");
int currentPage = (int) request.getAttribute("currentPage");
int totalPages = (int) request.getAttribute("totalPages");
%>
<!-- Blog Content -->
<div class="content-block section-area section-sp1">
    <div class="container">
        <div class="row">
            <!-- Left Column -->
            <div class="col-lg-8">
                <%
                    //List<Post> blogs = (List<Post>) request.getAttribute("blogs");
                    //if (blogs != null) {
                        for (Post blog : blogs) {
                %>
                <div class="blog-post blog-md clearfix">
                    <div class="ttr-post-media">
                        <a href="BlogDetail?post_id=<%= blog.getPostId() %>" ><img src="<%= blog.getImageUrl() %>" alt=""></a>
                    </div>
                    <div class="ttr-post-info">
                        <ul class="media-post">
                            <li>
                                <%
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // ??nh d?ng YYYY-MM-DD
                                    String formattedDate = dateFormat.format(blog.getPostedAt());
                                %>
                                <i class="fa fa-calendar"></i> <%= formattedDate %>
                            </li>
                            <li><i class="fa fa-user"></i> By <%= blog.getAuthor() %></li>
                            <li><i class="fa fa-building"></i> Club: <%= (blog.getClubName() != null) ? blog.getClubName() : "Unknown" %></li>

                        </ul>
                        <h5 class="post-title"><a href="BlogDetail?post_id=<%= blog.getPostId() %>"><%= blog.getTitle() %></a></h5>
                        <div class="post-extra">
                            <a href="BlogDetail?post_id=<%= blog.getPostId() %>" class="btn-link">READ MORE</a>
                        </div>
                    </div>
                </div>
                <% 
                      }
                                       
                %>


            </div>

            <!-- Right Sidebar -->
            <div class="col-lg-4 sticky-top">
                <aside class="side-bar">
                    <!-- Search Box -->
                    <div class="widget">
                        <h6 class="widget-title">Search</h6>
                        <div class="search-bx style-1">
                            <form role="search" method="post">
                                <div class="input-group">
                                    <input name="text" class="form-control" placeholder="Enter your keywords..." type="text">
                                    <span class="input-group-btn">
                                        <button type="submit" class="fa fa-search text-primary"></button>
                                    </span> 
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Recent Posts -->
                    <div class="widget recent-posts-entry">
                        <h6 class="widget-title">Recent Posts</h6>
                        <div class="widget-post-bx">
                            <% 
                                List<Post> recentBlogs = (List<Post>) request.getAttribute("recentBlogs");
                                if (recentBlogs != null && !recentBlogs.isEmpty()) {
                                    for (Post blog : recentBlogs) {
                            %>
                            <div class="widget-post clearfix">
                                <div class="ttr-post-media">
                                    <img src="<%= blog.getImageUrl() %>" width="200" height="143" alt="">
                                </div>
                                <div class="ttr-post-info">
                                    <h6 class="post-title">
                                        <a href="BlogDetail?post_id=<%= blog.getPostId() %>">
                                            <%= blog.getTitle() %>
                                        </a>
                                    </h6>
                                    <ul class="media-post">
                                        <li>
                                            <%
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // ??nh d?ng YYYY-MM-DD
                                                String formattedDate = dateFormat.format(blog.getPostedAt());
                                            %>
                                            <i class="fa fa-calendar"></i> <%= formattedDate %>
                                        </li>
                                        <li><a href="#"><i class="fa fa-user"></i> <%= blog.getAuthor() %></a></li>
                                    </ul>
                                </div>
                            </div>
                            <% 
                                    }
                                } else { 
                            %>
                            <p>No recent posts available.</p>
                            <% 
                                } 
                            %>
                        </div>
                    </div>



                    <!-- Gallery -->
                    <!--                    <div class="widget widget_gallery gallery-grid-4">
                                            <h6 class="widget-title">Recent activites</h6>
                                            <ul>
                                                <li><a href="#"><img src="assets/images/gallery/g1.jpg" alt=""></a></li>
                                                <li><a href="#"><img src="assets/images/gallery/g2.jpg" alt=""></a></li>
                                                <li><a href="#"><img src="assets/images/gallery/g3.jpg" alt=""></a></li>
                                                <li><a href="#"><img src="assets/images/gallery/g4.jpg" alt=""></a></li>
                    
                                            </ul>
                                        </div>-->
                </aside>
            </div>
        </div>


        <!-- Phân trang -->
        <div class="pagination-bx rounded-sm gray clearfix">
            <ul class="pagination">
                <% if (currentPage > 1) { %>
                <li class="previous">
                    <a href="BlogList?page=<%= currentPage - 1 %>">Prev</a>
                </li>
                <% } %>

                <% for (int i = 1; i <= totalPages; i++) { %>
                <li class="<%= (i == currentPage) ? "active" : "" %>">
                    <a href="BlogList?page=<%= i %>"><%= i %></a>
                </li>
                <% } %>

                <% if (currentPage < totalPages) { %>
                <li class="next">
                    <a href="BlogList?page=<%= currentPage + 1 %>">Next</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</div>
</div>

<!-- Side bar END -->
</div>
</div>
</div>
</div>
</div>
<!-- Content END-->
<!-- Footer ==== -->
<footer>
    <div class="footer-top">
        <div class="pt-exebar">
            <div class="container">
                <div class="d-flex align-items-stretch">
                    <div class="pt-logo mr-auto">
                        <a href="index.html"><img src="assets/images/logo-white.png" alt=""/></a>
                    </div>
                    <div class="pt-social-link">
                        <ul class="list-inline m-a0">
                            <li><a href="#" class="btn-link"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="#" class="btn-link"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#" class="btn-link"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="#" class="btn-link"><i class="fa fa-google-plus"></i></a></li>
                        </ul>
                    </div>
                    <div class="pt-btn-join">
                        <a href="#" class="btn ">Join Now</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-4 col-md-12 col-sm-12 footer-col-4">
                    <div class="widget">
                        <h5 class="footer-title">Sign Up For A Newsletter</h5>
                        <p class="text-capitalize m-b20">Weekly Breaking news analysis and cutting edge advices on job searching.</p>
                        <div class="subscribe-form m-b20">
                            <form class="subscription-form" action="http://educhamp.themetrades.com/demo/assets/script/mailchamp.php" method="post">
                                <div class="ajax-message"></div>
                                <div class="input-group">
                                    <input name="email" required="required"  class="form-control" placeholder="Your Email Address" type="email">
                                    <span class="input-group-btn">
                                        <button name="submit" value="Submit" type="submit" class="btn"><i class="fa fa-arrow-right"></i></button>
                                    </span> 
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-lg-5 col-md-7 col-sm-12">
                    <div class="row">
                        <div class="col-4 col-lg-4 col-md-4 col-sm-4">
                            <div class="widget footer_widget">
                                <h5 class="footer-title">Company</h5>
                                <ul>
                                    <li><a href="index.html">Home</a></li>
                                    <li><a href="about-1.html">About</a></li>
                                    <li><a href="faq-1.html">FAQs</a></li>
                                    <li><a href="contact-1.html">Contact</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-4 col-lg-4 col-md-4 col-sm-4">
                            <div class="widget footer_widget">
                                <h5 class="footer-title">Get In Touch</h5>
                                <ul>
                                    <li><a href="http://educhamp.themetrades.com/admin/index.html">Dashboard</a></li>
                                    <li><a href="blog-classic-grid.html">Blog</a></li>
                                    <li><a href="portfolio.html">Portfolio</a></li>
                                    <li><a href="event.html">Event</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-4 col-lg-4 col-md-4 col-sm-4">
                            <div class="widget footer_widget">
                                <h5 class="footer-title">Courses</h5>
                                <ul>
                                    <li><a href="courses.html">Courses</a></li>
                                    <li><a href="courses-details.html">Details</a></li>
                                    <li><a href="membership.html">Membership</a></li>
                                    <li><a href="profile.html">Profile</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-lg-3 col-md-5 col-sm-12 footer-col-4">
                    <div class="widget widget_gallery gallery-grid-4">
                        <h5 class="footer-title">Our Gallery</h5>
                        <ul class="magnific-image">
                            <li><a href="assets/images/gallery/pic1.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic1.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic2.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic2.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic3.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic3.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic4.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic4.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic5.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic5.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic6.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic6.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic7.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic7.jpg" alt=""></a></li>
                            <li><a href="assets/images/gallery/pic8.jpg" class="magnific-anchor"><img src="assets/images/gallery/pic8.jpg" alt=""></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 text-center"> <a target="_blank" href="https://www.templateshub.net">Templates Hub</a></div>
            </div>
        </div>
</footer>
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