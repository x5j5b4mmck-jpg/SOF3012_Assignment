<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3 style="color: #2e8b57; font-weight: bold;">QUẢN LÝ VIDEO</h3>
<hr>

<c:if test="${not empty message}">
    <div class="alert alert-info">${message}</div>
</c:if>

<div class="admin-form-container">
    <form action="<c:url value='/admin/video-management'/>" method="post" enctype="multipart/form-data" class="admin-form">
        
        <div class="form-left">
            <div class="poster-box">
                <img id="poster-preview" 
                     src="${not empty formVideo.poster ? pageContext.request.contextPath.concat('/logos/').concat(formVideo.poster) : 'https://placehold.co/300x200?text=No+Image'}" 
                     style="width: 100%; height: 200px; object-fit: cover; border: 2px dashed #ff9900; margin-bottom: 10px;">
                
                <input type="file" name="cover" accept="image/*" onchange="previewImage(this)" style="width: 100%;">
            </div>
            <div class="form-group">
                <label>DESCRIPTION</label>
                <textarea name="description" class="form-control" rows="4">${formVideo.description}</textarea>
            </div>
        </div>
        
        <div class="form-right">
            <input type="hidden" name="action" value="${formState == 'edit' ? 'update' : 'create'}">
            
            <div class="form-group">
                <label>YOUTUBE ID</label>
                <input type="text" name="id" class="form-control" value="${formVideo.id}" ${formState == 'edit' ? 'readonly style="background-color: #e9ecef;"' : ''}>
            </div>
            
            <div class="form-group">
                <label>VIDEO TITLE</label>
                <input type="text" name="title" class="form-control" value="${formVideo.title}">
            </div>
            
            <div class="form-group">
                <label>VIEW COUNT</label>
                <input type="number" name="views" class="form-control" value="${formVideo.views}">
            </div>
            
            <div class="form-group-radio">
                <input type="radio" id="active" name="active" value="true" ${formVideo.active ? 'checked' : ''}> <label for="active">Active</label>
                <input type="radio" id="inactive" name="active" value="false" ${!formVideo.active ? 'checked' : ''}> <label for="inactive">Inactive</label>
            </div>
        </div>
        
        <div class="form-actions" style="width: 100%; margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee;">
            <button type="submit" class="btn-action btn-create" ${formState == 'edit' ? 'disabled' : ''}>Create</button>
            
            <button type="submit" class="btn-action btn-update" ${formState == 'create' ? 'disabled' : ''}>Update</button>
            
            <a href="<c:url value='/admin/video-management?action=delete&id=${formVideo.id}'/>" 
               class="btn-action btn-delete ${formState == 'create' ? 'disabled' : ''}" 
               onclick="return confirm('Bạn có chắc chắn muốn xóa video này không?');">Delete</a>
               
            <a href="<c:url value='/admin/video-management?action=reset'/>" class="btn-action btn-reset">Reset</a>
        </div>
    </form>
</div>

<div class="admin-table-container" style="margin-top: 30px;">
    <table class="admin-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Views</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${videoList}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.title}</td>
                    <td>${item.views}</td>
                    <td>${item.active ? 'Active' : 'Inactive'}</td>
                    <td>
                        <a href="<c:url value='/admin/video-management?action=edit&id=${item.id}'/>" style="font-weight: bold; color: blue;">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>

<script>
    function previewImage(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('poster-preview').src = e.target.result;
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>