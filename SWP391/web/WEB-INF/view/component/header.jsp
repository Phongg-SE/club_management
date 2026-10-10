<%-- 
    Document   : header
    Created on : Oct 27, 2024, 12:17:33 AM
    Author     : admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <style>
        .event-bx .action-box img {
            width: 100%;
            height: 250px;
            object-fit: cover;
        }

        .recent-news .action-box{
            width: 100%;
            height: 250px;
        }

        .cours-bx {
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
            border-radius: 4px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .cours-bx .action-box {
            position: relative;
            height: 200px; /* Fixed height for image container */
        }

        .cours-bx .action-box img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .cours-bx .info-bx {
            flex: 1;
            padding: 15px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .cours-bx .info-bx h5 {
            margin-bottom: 10px;
            font-size: 16px;
            line-height: 1.4;
        }

        .cours-bx .info-bx span {
            display: block;
            font-size: 14px;
        }

        .cours-bx .cours-more-info {
            padding: 15px;
            border-top: 1px solid #e9e9e9;
        }

        .upcoming-event-carousel .item {
            padding: 15px;
            height: 100%;
        }

        /* Dark Theme Styles */
        body.dark-theme {
            background-color: #121212 !important;
            color: #e0e0e0 !important;
        }
        body.dark-theme .sticky-header, 
        body.dark-theme .menu-bar, 
        body.dark-theme .card, 
        body.dark-theme .cours-bx {
            background-color: #1e1e1e !important;
            color: #ffffff !important;
            border-color: #333 !important;
        }
        body.dark-theme .sub-menu {
            background-color: #242424 !important;
        }
        body.dark-theme .sub-menu li a {
            color: #e0e0e0 !important;
        }
    </style>
    <meta charset="UTF-8">
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
    <title>FPT Clubs  </title>

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

    <!-- REVOLUTION SLIDER CSS ============================================= -->
    <link rel="stylesheet" type="text/css" href="assets/vendors/revolution/css/layers.css">
    <link rel="stylesheet" type="text/css" href="assets/vendors/revolution/css/settings.css">
    <link rel="stylesheet" type="text/css" href="assets/vendors/revolution/css/navigation.css">
    <!-- REVOLUTION SLIDER END -->	    
</head>
<body>
    <c:set var="admin" value="1"/>
    <c:set var="eventLeader" value="2"/>
    <c:set var="clubMember" value="3"/>
    <c:set var="clubLeader" value="4"/>
    <c:set var="projectLeader" value="5"/>
    <c:set var="webUser" value="6"/>
    <c:set var="clubRole" value="${sessionScope.clubRole}" />
    <c:set var="role" value="${sessionScope.role}" />

    <c:set var="eventPermission" value="false" />
    <c:forEach var="role" items="${clubRole}">
        <c:if test="${role == clubLeader or role == eventLeader}">
            <c:set var="eventPermission" value="true" />
        </c:if>
    </c:forEach>

    <c:set var="clubPermission" value="false" />
    <c:forEach var="role" items="${clubRole}">
        <c:if test="${role == clubLeader or role == eventLeader or role == clubMember or role == projectLeader}">
            <c:set var="clubPermission" value="true" />
        </c:if>
    </c:forEach>

    <c:set var="registratrionPermission" value="false" />
    <c:forEach var="role" items="${clubRole}">
        <c:if test="${role == eventLeader}">
            <c:set var="registratrionPermission" value="true" />
        </c:if>
    </c:forEach>

    <c:set var="projectPermission" value="false" />
    <c:forEach var="role" items="${clubRole}">
        <c:if test="${role == clubLeader or role == projectLeader}">
            <c:set var="projectPermission" value="true" />
        </c:if>
    </c:forEach>

    <header class="header rs-nav">
        <div class="top-bar">
            <div class="container">
                <div class="row d-flex justify-content-between">
                    <div class="topbar-left">
                        <ul>
                            <li><a href="faq-1.html"><i class="fa fa-question-circle"></i>Ask a Question</a></li>
                            <li><a href="javascript:;"><i class="fa fa-envelope-o"></i>Support@website.com</a></li>
                        </ul>
                    </div>
                    <div class="topbar-right">
                        <ul>
                            <c:if test="${empty sessionScope.account.user_id}">
                                <li><a href="login">Login</a></li>
                                </c:if>

                            <c:if test="${empty sessionScope.account.user_id}">
                                <li><a href="register">Register</a></li>
                                </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="sticky-header navbar-expand-lg">
            <div class="menu-bar clearfix">
                <div class="container clearfix">
                    <!-- Header Logo ==== -->
                    <div class="menu-logo">
                        <a href="home"><img src="assets/img/fpt.png" alt=""></a>
                    </div>

                    <!-- Navigation Menu ==== -->
                    <div class="menu-links navbar-collapse collapse justify-content-start" id="menuDropdown" style="font-size: 14px;">
                        <div class="menu-logo">
                            <a href="index.html">
                                <img src="assets/images/logo.png" alt="Logo" style="max-height: 40px;">
                            </a>
                        </div>

                        <c:if test="${empty sessionScope.account}">
                            <ul class="nav navbar-nav">
                                <li class="active"><a href="home">Home</a></li>
                                <li><a href="PublicEventList">Events</a></li>
                                <li><a href="PublicClub">Clubs</a></li>
                                <li><a href="BlogList">Public Post</a></li>
                            </ul>
                        </c:if>

                        <c:if test="${not empty sessionScope.account}">
                            <ul class="nav navbar-nav">
                                <li>
                                    <a href="javascript:;">Events <i class="fa fa-chevron-down"></i></a>
                                    <ul class="sub-menu">
                                        <li><a href="PublicEventList">Public Events</a></li>
                                            <c:if test="${clubPermission}">
                                            <li><a href="EventList">Event List</a></li>
                                            </c:if>

                                        <c:if test="${registratrionPermission}">
                                            <li><a href="registrationEventList">Registration List</a></li>
                                            </c:if>
                                    </ul>
                                </li>
                                <li>
                                    <a href="javascript:;">Clubs <i class="fa fa-chevron-down"></i></a>
                                    <ul class="sub-menu">
                                        <li><a href="PublicClub">Public Clubs</a></li>
                                            <c:if test="${clubPermission}">
                                            <li><a href="memberofficiallist">Club Member</a></li>
                                            <li><a href="FinanceList">Finance</a></li>
                                            <li><a href="taskList">Task</a></li>
                                            <li><a href="ProjectList">Project</a></li>
                                            </c:if>
                                    </ul>
                                </li>

                                <li>
                                    <a href="javascript:;">Blog <i class="fa fa-chevron-down"></i></a>
                                    <ul class="sub-menu">
                                        <li><a href="BlogList">Public Post</a></li>
                                            <c:if test="${clubPermission}">
                                            <li><a href="PostList">Post List</a></li>
                                            </c:if>
                                    </ul>
                                </li>

                                <c:if test="${role == admin}">
                                    <li class="nav-dashboard">
                                        <a href="javascript:;">Admin Dashboard <i class="fa fa-chevron-down"></i></a>
                                        <ul class="sub-menu">
                                            <li><a href="settingList">Setting Management</a></li>
                                            <li><a href="AccountList">Account Management</a></li>
                                            <li><a href="clublist">Club Management</a></li>
                                        </ul>
                                    </li>
                                </c:if>
                            </ul>
                        </c:if>

                        <c:if test="${not empty sessionScope.account}">
                            <ul class="nav navbar-nav ml-auto" style="margin-left: auto; display: flex; align-items: center;">
                                <li>
                                    <a href="club-calendar" title="Lịch hoạt động"><i class="fa fa-calendar"></i> Lịch</a>
                                </li>
                                <li>
                                    <a href="club-dashboard" title="Dashboard CLB"><i class="fa fa-chart-line"></i> Dashboard</a>
                                </li>
                                <li>
                                    <a href="Notification" title="Thông báo" style="position: relative;">
                                        <i class="fa fa-bell"></i>
                                        <span class="badge badge-danger" style="background:#dc3545; color:#fff; border-radius:50%; font-size:10px; padding:2px 5px; position:relative; top:-8px; left:-5px;">3</span>
                                    </a>
                                </li>
                                <li style="margin-right: 15px;">
                                    <button type="button" id="darkModeToggle" class="btn btn-sm btn-outline-secondary" style="border-radius:20px; font-size:12px; padding:4px 10px;" onclick="toggleDarkMode()">
                                        <i class="fa fa-moon" id="darkIcon"></i>
                                    </button>
                                </li>
                                <li class="nav-dashboard">
                                    <a href="javascript:;" style="display: flex; align-items: center;">
                                        <img src="${sessionScope.account.avatar_url}" 
                                             class="rounded-circle" 
                                             style="width: 35px; height: 35px; object-fit: cover; border: 2px solid #ddd; margin-right: 5px;">
                                        <i class="fa fa-chevron-down"></i>
                                    </a>
                                    <ul class="sub-menu">
                                        <li>
                                            <a href="userprofile">
                                                <i class="fa fa-user-circle"></i> <span>Profile Setting</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="logout">
                                                <i class="fa fa-sign-out"></i> <span>Log out</span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </c:if>
                    </div>

                    <!-- Navigation Menu END ==== -->
                </div>
            </div>
        </div>
    </header>

    <script>
        function toggleDarkMode() {
            document.body.classList.toggle('dark-theme');
            const isDark = document.body.classList.contains('dark-theme');
            localStorage.setItem('swp391_theme', isDark ? 'dark' : 'light');
            const icon = document.getElementById('darkIcon');
            if (icon) {
                icon.className = isDark ? 'fa fa-sun' : 'fa fa-moon';
            }
        }
        (function() {
            if (localStorage.getItem('swp391_theme') === 'dark') {
                document.body.classList.add('dark-theme');
            }
        })();
    </script>
</body>
