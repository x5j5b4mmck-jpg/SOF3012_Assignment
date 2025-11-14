package com.poly.asm.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poly.asm.dao.VideoDAO;
import com.poly.asm.entity.Video;

@WebServlet("/index")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    public HomeServlet() {
        super();
        this.videoDAO = new VideoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy 6 video giảm dần theo lượt xem (như trong spec)
        List<Video> videos = videoDAO.findTop6ByViews(); 
        
        // (Bạn cũng cần xử lý logic phân trang ở đây)
        
        request.setAttribute("videos", videos);
        request.setAttribute("view", "/site/home/index.jsp"); // View sẽ được include
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}