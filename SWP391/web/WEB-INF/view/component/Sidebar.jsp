<%-- 
    Document   : Sidebar
    Created on : Oct 28, 2024, 11:33:10 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar" data-background-color="white">
    <div class="sidebar-logo">
        <!-- Logo Header -->
        <div class="logo-header" data-background-color="white">
            <!--<a class="navbar-brand logo_h" href="home"><img src="assets/img/fpt.png" alt=""></a>-->
            <div class="nav-toggle">
                <button class="btn btn-toggle toggle-sidebar">
                    <i class="gg-menu-right"></i>
                </button>
                <button class="btn btn-toggle sidenav-toggler">
                    <i class="gg-menu-left"></i>
                </button>
            </div>
            <button class="topbar-toggler more">
                <i class="gg-more-vertical-alt"></i>
            </button>
        </div>
        <!-- End Logo Header -->
    </div>
    <div class="sidebar-wrapper scrollbar scrollbar-inner">
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

        <div class="sidebar-content">
            <ul class="nav nav-secondary">
                <c:if test="${role == admin}">
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#dashboard" class="collapsed" aria-expanded="false">
                            <i class="fas fa-home"></i>
                            <p>Dashboard</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="dashboard">
                            <ul class="nav nav-collapse">
                                <li><a href="clublist"><span class="sub-item">Management Club</span></a></li>
                                <li><a href="AccountList"><span class="sub-item">Management Account</span></a></li>
                                <li><a href="settingList"><span class="sub-item">Management Setting</span></a></li>
                            </ul>
                        </div>
                    </li>
                </c:if>
                <c:if test="${clubPermission}">
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#club" class="collapsed" aria-expanded="false">
                            <i class="fas fa-users"></i>

                            <p>Club</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="club">
                            <ul class="nav nav-collapse">
                                <li><a href="memberofficiallist"><span class="sub-item">Club Member</span></a></li>
                                <li><a href="FinanceList"><span class="sub-item">Finances</span></a></li>
                                <li><a href="taskList"><span class="sub-item">Task</span></a></li>
                                <li><a href="ProjectList"><span class="sub-item">Project</span></a></li>
                                <li><a href="PostList"><span class="sub-item">Post</span></a></li>
                            </ul>
                        </div>
                    </li>
                </c:if>
                <c:if test="${registratrionPermission}">
                    <li class="nav-item">
                        <a data-bs-toggle="collapse" href="#event" class="collapsed" aria-expanded="false">
                            <i class="fas fa-calendar-alt"></i>
                            <p>Event</p>
                            <span class="caret"></span>
                        </a>
                        <div class="collapse" id="event">
                            <ul class="nav nav-collapse">
                                <li><a href="EventList"><span class="sub-item">Manage Event</span></a></li>
                                <li><a href="registrationEventList"><span class="sub-item">Registration List</span></a></li>
                                
                            </ul>
                        </div>
                    </li>     
                </c:if>
                <li class="nav-item">
                    <a href="logout">
                        <i class="fas fa-sign-out-alt"></i>
                        <p>Logout</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
