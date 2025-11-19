package com.poly.asm.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poly.asm.dao.FavoriteDAO;
import com.poly.asm.dao.VideoDAO;
import com.poly.asm.entity.Favorite;
import com.poly.asm.entity.User;
import com.poly.asm.entity.Video;

@WebServlet(urlPatterns = {"/favorites", "/like", "/unlike"})
public class FavoriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FavoriteDAO favoriteDAO;
    private VideoDAO videoDAO;

    public FavoriteServlet() {
        super();
        this.favoriteDAO = new FavoriteDAO();
        this.videoDAO = new VideoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");
        
        if (uri.contains("/favorites")) {
        	
        	// 1. Lấy danh sách Favorite từ DAO
            List<Favorite> favorites = favoriteDAO.findByUser(user.getId());
            
            // 2. Gửi danh sách này sang JSP
            request.setAttribute("favoritesList", favorites); 
            
            // 3. Chỉ định view là favorites.jsp
            request.setAttribute("view", "/site/home/favorites.jsp");
            
            // Hiển thị trang "My Favorites"
  
            request.setAttribute("videos", favorites); // Gửi danh sách favorites
            request.setAttribute("view", "/site/home/favorites.jsp");
            
        } else if (uri.contains("/like")) {
            // Xử lý "Like" video
            handleLike(request, response, user);
            // Quay lại trang chi tiết (hoặc trang chủ)
            response.sendRedirect(request.getContextPath() + "/detail?id=" + request.getParameter("id"));
            return; // Dừng lại để tránh forward
            
        } else if (uri.contains("/unlike")) {
        	// Cập nhật: Sau khi unlike, quay lại trang favorites
        	response.sendRedirect(request.getContextPath() + "/favorites?t=" + System.currentTimeMillis());
            return; // Dừng lại để tránh forward
            
        }

        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Có thể dùng doPost cho Like/Unlike nếu dùng form
        doGet(request, response);
    }

    private void handleLike(HttpServletRequest request, HttpServletResponse response, User user) {
        String videoId = request.getParameter("id");
        if (videoId == null) return;
        
        Video video = videoDAO.findById(Video.class, videoId);
        
        // Kiểm tra xem đã like chưa
        if (!favoriteDAO.isFavorited(user.getId(), videoId)) {
            Favorite fav = new Favorite();
            fav.setUser(user);
            fav.setVideo(video);
            favoriteDAO.create(fav);
        }
    }
    
    private void handleUnlike(HttpServletRequest request, HttpServletResponse response, User user) {
        String videoId = request.getParameter("id");
        if (videoId == null) return;
        
        Favorite fav = favoriteDAO.findFavorite(user.getId(), videoId);
        if (fav != null) {
            favoriteDAO.delete(fav);
        }
    }
}