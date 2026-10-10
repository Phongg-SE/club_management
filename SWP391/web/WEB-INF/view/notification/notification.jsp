<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Trung Tâm Thông Báo - Club Management</title>
        <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/kaiadmin.min.css" />
    </head>
    <body>
        <div class="wrapper">
            <!-- Sidebar -->
            <jsp:include page="../component/Sidebar.jsp"></jsp:include>

            <div class="main-panel">
                <div class="container">
                    <div class="page-inner">
                        <div class="page-header d-flex justify-content-between align-items-center">
                            <div>
                                <h3 class="fw-bold mb-1">🔔 Trung Tâm Thông Báo</h3>
                                <ul class="breadcrumbs mb-0">
                                    <li class="nav-home"><a href="home"><i class="icon-home"></i></a></li>
                                    <li class="separator"><i class="icon-arrow-right"></i></li>
                                    <li class="nav-item"><span>Thông báo</span></li>
                                </ul>
                            </div>
                            <form action="Notification" method="POST">
                                <input type="hidden" name="action" value="markAll" />
                                <button type="submit" class="btn btn-outline-primary btn-sm">
                                    ✓ Đánh dấu tất cả đã đọc
                                </button>
                            </form>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header d-flex justify-content-between align-items-center">
                                        <div class="card-title">
                                            Danh Sách Thông Báo
                                            <c:if test="${unreadCount > 0}">
                                                <span class="badge bg-danger ms-2">${unreadCount} chưa đọc</span>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <c:if test="${empty notifications}">
                                            <div class="text-center py-5 text-muted">
                                                <h5>Bạn không có thông báo nào</h5>
                                                <p>Khi có công việc, sự kiện hoặc tài chính mới, bạn sẽ nhận được thông báo tại đây.</p>
                                            </div>
                                        </c:if>

                                        <div class="list-group list-group-flush">
                                            <c:forEach items="${notifications}" var="n">
                                                <div class="list-group-item d-flex align-items-start p-3 ${!n.read ? 'bg-light border-start border-4 border-primary' : ''}">
                                                    <div class="me-3 fs-3">
                                                        <c:choose>
                                                            <c:when test="${n.type == 'TASK'}">📋</c:when>
                                                            <c:when test="${n.type == 'EVENT'}">🎉</c:when>
                                                            <c:when test="${n.type == 'FINANCE'}">💰</c:when>
                                                            <c:when test="${n.type == 'MEMBER'}">👤</c:when>
                                                            <c:otherwise>🔔</c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="flex-grow-1">
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <h6 class="mb-1 ${!n.read ? 'fw-bold text-primary' : 'fw-bold'}">${n.title}</h6>
                                                            <small class="text-muted">${n.createdAt}</small>
                                                        </div>
                                                        <p class="mb-1 text-secondary">${n.message}</p>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.7.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/core/bootstrap.min.js"></script>
    </body>
</html>
