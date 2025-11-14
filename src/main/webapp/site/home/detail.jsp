<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">

    <div class="col-md-8">
        <div class="video-detail-card">
            
            <div class="video-player-box">
                <iframe 
                    width="100%" 
                    height="450" 
                    src="https://www.youtube.com/embed/${video.id}" 
                    title="${video.title}" 
                    frameborder="0" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                    allowfullscreen>
                </iframe>
            </div>
            
            <div class="video-info-box">
                <h4>${video.title}</h4>
                
                <div class="video-actions">
                    <a href="<c:url value='/like?id=${video.id}'/>" class="btn btn-like-detail">Like</a>
                       
                    <a href="<c:url value='/share?id=${video.id}'/>" class="btn btn-share-detail">Share</a>
                </div>
                
                <hr>
                <h5>DESCRIPTION</h5>
                <p>${video.description}</p>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <h5 class="mb-3">Related Videos</h5>
        
        <c:forEach var="related" items="${relatedVideos}">
            <a href="<c:url value='/detail?id=${related.id}'/>" class="sidebar-video-item">
                <div class="sidebar-poster">
                    <img src="${related.poster}" alt="${related.title}">
                </div>
                <div class="sidebar-title">
                    ${related.title}
                </div>
            </a>
        </c:forEach>
        
        </div>
</div>