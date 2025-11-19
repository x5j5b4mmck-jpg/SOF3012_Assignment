<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
    .video-card {
        border: 2px solid #ff9900;
        border-radius: 8px;
        overflow: hidden;
        transition: transform 0.2s; /* Hiệu ứng hover nhẹ */
    }
    .video-card:hover {
        transform: translateY(-5px); /* Nổi lên khi di chuột */
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    }
    
    /* --- SỬA CHIỀU CAO TẠI ĐÂY --- */
    .poster-box {
        height: 320px; /* Tăng chiều cao lên 320px cho đẹp */
        overflow: hidden;
        background-color: #f0f0f0;
    }
    
    .video-card .card-img-top {
        width: 100%;
        height: 100%;
        object-fit: cover; /* Cắt ảnh vừa khung */
        object-position: center;
    }
    /* ----------------------------- */

    .video-card .card-body {
        background-color: #e6f7e6;
        padding: 10px;
    }
    .video-card .card-title {
        font-size: 1.1rem;
        font-weight: bold;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis; 
    }
    .video-card .card-title a {
        text-decoration: none;
        color: #333;
    }
    .video-card .card-title a:hover {
        color: #007bff;
    }
</style>

<div class="row">
    <c:forEach var="video" items="${videos}">
        <div class="col-md-4 mb-4"> 
            <div class="card video-card">
                
                <a href="<c:url value='/detail?id=${video.id}'/>">
                    <div class="poster-box">
                        <c:choose>
                            <%-- Trường hợp 1: Ảnh online --%>
                            <c:when test="${fn:substring(video.poster, 0, 4) == 'http'}">
                                <img class="card-img-top" 
                                     src="${video.poster}" 
                                     alt="${video.title}">
                            </c:when>
                            
                            <%-- Trường hợp 2: Ảnh upload trong /logos/ --%>
                            <c:otherwise>
                                <img class="card-img-top" 
                                     src="${pageContext.request.contextPath}/logos/${video.poster}" 
                                     alt="${video.title}"
                                     onerror="this.src='https://placehold.co/600x400?text=No+Image'"> 
                            </c:otherwise>
                        </c:choose>
                    </div>
                </a>
                
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="<c:url value='/detail?id=${video.id}'/>">${video.title}</a>
                    </h5>
                    
                    <a href="<c:url value='/like?id=${video.id}'/>" class="btn btn-success btn-sm">Like</a>
                    <a href="<c:url value='/share?id=${video.id}'/>" class="btn btn-warning btn-sm">Share</a>
                </div>
            </div> 
        </div> 
    </c:forEach>
</div>

<nav aria-label="Page navigation" class="d-flex justify-content-center mt-4">
    <ul class="pagination">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="<c:url value='/index?page=1'/>">|&lt;</a>
        </li>
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="<c:url value='/index?page=${currentPage - 1}'/>">&lt;&lt;</a>
        </li>
        
        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${currentPage == i ? 'active' : ''}">
                <a class="page-link" href="<c:url value='/index?page=${i}'/>">${i}</a>
            </li>
        </c:forEach>

        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
            <a class="page-link" href="<c:url value='/index?page=${currentPage + 1}'/>">&gt;&gt;</a>
        </li>
        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
            <a class="page-link" href="<c:url value='/index?page=${totalPages}'/>">&gt;|</a>
        </li>
    </ul>
</nav>