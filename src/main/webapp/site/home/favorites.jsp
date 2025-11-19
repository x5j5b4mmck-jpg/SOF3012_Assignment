<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h3 style="color: #2e8b57; font-weight: bold; margin-bottom: 15px;">
    TRANG YÊU THÍCH
</h3>
<hr>

<div class="row">
    
    <%-- 
      Trường hợp 1: Người dùng chưa thích video nào.
      Kiểm tra xem 'favoritesList' có rỗng (empty) hay không.
    --%>
    <c:if test="${empty favoritesList}">
        <div class="col-12">
            <p style="text-align: center; font-size: 1.1rem; color: #555;">
                Bạn chưa thích video nào.
            </p>
        </div>
    </c:if>
    
    <%-- 
      Trường hợp 2: Hiển thị danh sách video đã thích.
      Lặp qua 'favoritesList'. Mỗi phần tử 'fav' là một đối tượng Favorite.
      Chúng ta truy cập video thông qua 'fav.video'.
    --%>
    <c:forEach var="fav" items="${favoritesList}">
        
        <%-- Mỗi video chiếm 4 cột trên grid (giống trang chủ) --%>
        <div class="col-md-4 mb-4">
            
            <%-- 
              Tái sử dụng class 'video-card' từ style.css 
              để có giao diện đồng bộ (viền cam, nền xanh lá).
            --%>
            <div class="card video-card">
                
                <%-- 
                  [Poster].Click: Bấm vào poster hoặc title 
                  sẽ đi đến trang chi tiết video.
                --%>
                <a href="<c:url value='/detail?id=${fav.video.id}'/>">
                    <div class="poster-box">
                         <img src="<c:url value='${fav.video.poster}'/>" 
                              alt="${fav.video.title}">
                    </div>
                </a>
                
                <%-- Thân card (chứa title và các nút) --%>
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="<c:url value='/detail?id=${fav.video.id}'/>">
                            ${fav.video.title}
                        </a>
                    </h5>
                    
                    <%-- 
                      [Unlike].Click: Bỏ ghi nhận yêu thích.
                      Yêu cầu: Login (đã được AuthFilter xử lý).
                    --%>
                    <a href="<c:url value='/unlike?id=${fav.video.id}'/>" 
                       class="btn btn-unlike">Unlike</a>
                       
                    <%-- 
                      [Share].Click: Chuyển đến trang chia sẻ.
                      Yêu Cầu: Login (đã được AuthFilter xử lý).
                    --%>
                    <a href="<c:url value='/share?id=${fav.video.id}'/>" 
                       class="btn btn-share">Share</a>
                </div>
            </div> </div> </c:forEach>
</div> 