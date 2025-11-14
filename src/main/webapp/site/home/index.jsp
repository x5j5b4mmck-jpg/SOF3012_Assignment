<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    .video-card {
        border: 2px solid #ff9900; /* Viền cam */
        border-radius: 8px;
        overflow: hidden; /* Đảm bảo bo góc */
    }
    .video-card .card-img-top {
        height: 180px; /* Đồng bộ chiều cao poster */
        object-fit: cover; /* Giữ tỷ lệ ảnh */
    }
    .video-card .card-body {
        background-color: #e6f7e6; /* Nền xanh lá nhạt */
        padding: 10px;
    }
    .video-card .card-title {
        font-size: 1.1rem;
        font-weight: bold;
        /* Giới hạn 1 dòng + dấu ... */
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
    .video-card .btn-like {
        background-color: #90ee90; /* Màu xanh lá (Like) */
        border: none;
        color: #333;
    }
    .video-card .btn-share {
        background-color: #ffb733; /* Màu cam (Share) */
        border: none;
        color: #333;
    }
</style>

<div class="row">
    <c:forEach var="video" items="${videos}">
        
        <div class="col-md-4 mb-4"> <div class="card video-card">
                
                <a href="<c:url value='/detail?id=${video.id}'/>">
                    <img class="card-img-top" 
                         src="<c:url value='${video.poster}'/>" 
                         alt="${video.title}">
                </a>
                
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="<c:url value='/detail?id=${video.id}'/>">${video.title}</a>
                    </h5>
                    
                    <a href="<c:url value='/like?id=${video.id}'/>" 
                       class="btn btn-primary btn-like">Like</a>
                       
                    <a href="<c:url value='/share?id=${video.id}'/>" 
                       class="btn btn-primary btn-share">Share</a>
                </div>
            </div> </div> </c:forEach>
</div> <nav aria-label="Page navigation" class="d-flex justify-content-center">
    <ul class="pagination">
        <li class="page-item"><a class="page-link" href="#">|&lt;</a></li>
        <li class="page-item"><a class="page-link" href="#">&lt;&lt;</a></li>
        <li class="page-item active"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">&gt;&gt;</a></li>
        <li class="page-item"><a class="page-link" href="#">&gt;|</a></li>
    </ul>
</nav>