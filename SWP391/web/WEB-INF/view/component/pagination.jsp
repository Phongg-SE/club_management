
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- pagination -->
<!-- pagination -->
<section id="page-navigation" class="d-flex justify-content-center">
    <ul class="pagination">
        <!-- Home (first page) -->
        <c:if test="${pageControl.page > 1}">
            <li class="page-item">
                <a class="page-link prev-arrow" href="${pageControl.urlPattern}page=1">
                    <i class="fa fa-home" aria-hidden="true"></i>
                </a>
            </li>
        </c:if>

        <!-- Previous -->
        <c:choose>
            <c:when test="${pageControl.page == 1}">
                <li class="page-item disabled">
                    <a class="page-link prev-arrow">
                        <i class="fa fa-long-arrow-left" aria-hidden="true"></i>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link prev-arrow" href="${pageControl.urlPattern}page=${pageControl.page - 1}">
                        <i class="fa fa-long-arrow-left" aria-hidden="true"></i>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>

        <!-- Pages -->
        <c:forEach begin="1" end="${pageControl.totalPage}" var="pageNumber">
            <li class="page-item">
                <a class="page-link <c:if test='${pageControl.page == pageNumber}'>active</c:if>'"
                   href="${pageControl.urlPattern}page=${pageNumber}">
                    ${pageNumber}
                </a>
            </li>
        </c:forEach>

        <!-- Ellipsis (if needed) -->
        <c:if test="${pageControl.totalPage > 5}">
            <li class="page-item dot-dot">
                <a class="page-link">
                    <i class="fa fa-ellipsis-h" aria-hidden="true"></i>
                </a>
            </li>
        </c:if>

        <!-- Next -->
        <c:choose>
            <c:when test="${pageControl.page == pageControl.totalPage}">
                <li class="page-item disabled">
                    <a class="page-link next-arrow">
                        <i class="fa fa-long-arrow-right" aria-hidden="true"></i>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link next-arrow" href="${pageControl.urlPattern}page=${pageControl.page + 1}">
                        <i class="fa fa-long-arrow-right" aria-hidden="true"></i>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</section>
