<%-- 
    Document   : category
    Created on : Oct 27, 2024, 12:22:47 AM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="sidebar-categories">
    <div class="head">Browse Categories</div>
    <c:forEach items="${listCategories}" var="listCategories">
        <ul class="main-categories">
            <li class="main-nav-list"><a href="home?action=category&categoryId=${listCategories.categoryId}" aria-expanded="false" aria-controls="fruitsVegetable"><span
                    class="lnr lnr-arrow-right"></span>${listCategories.categoryName}<span class="number"></span></a>
        </li>
    </ul>
    </c:forEach>
</div>