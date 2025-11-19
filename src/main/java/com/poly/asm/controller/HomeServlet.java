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

// Mapping 2 đường dẫn:
// "/index": Đường dẫn chuẩn

@WebServlet({"/index", ""}) 
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    public HomeServlet() {
        super();
        this.videoDAO = new VideoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Lấy dữ liệu từ CSDL
        List<Video> videos = videoDAO.findAll(1, 6); // Ví dụ lấy 6 video trang 1
        // Nếu bạn chưa viết hàm findAll phân trang, dùng tạm: videoDAO.findAll(Video.class);
        
        // 2. Đẩy dữ liệu sang JSP
        request.setAttribute("videos", videos);
        
        // 3. Thiết lập view nội dung là trang chủ
        request.setAttribute("view", "/site/home/index.jsp"); 
        
        // 4. Forward về trang layout chính
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}