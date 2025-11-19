<%@ page contentType="text-html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="share-container">
    
    <div class="share-header">
        <h4>SEND VIDEO TO YOUR FRIEND</h4>
    </div>
    
    <div class="share-body">
        
        <c:if test="${not empty message}">
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
        </c:if>

        <p>Video bạn đang chia sẻ: <strong>${video.title}</strong></p>

        <form action="<c:url value='/share'/>" method="post">
            <input type="hidden" name="videoId" value="${video.id}">
            
            <div class="form-group">
                <label>YOUR FRIEND'S EMAIL?</label>
                <input type="text" class="form-control" name="emails" 
                       placeholder="email1@example.com, email2@example.com, ..." 
                       required>
                <small class="form-text text-muted">
                    Các email cách nhau bằng dấu phẩy (,) hoặc chấm phẩy (;)
                </small>
            </div>
            <br>
            
            <button type="submit" class="btn btn-form-submit">Send</button>
        </form>
    </div>
</div>