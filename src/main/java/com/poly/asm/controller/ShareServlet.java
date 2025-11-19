package com.poly.asm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poly.asm.dao.ShareDAO;
import com.poly.asm.dao.VideoDAO;
import com.poly.asm.entity.Share;
import com.poly.asm.entity.User;
import com.poly.asm.entity.Video;

@WebServlet("/share")
public class ShareServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ShareDAO shareDAO;
    private VideoDAO videoDAO;

    public ShareServlet() {
        super();
        this.shareDAO = new ShareDAO();
        this.videoDAO = new VideoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // (AuthFilter đã chặn nếu chưa login)
        
        String videoId = request.getParameter("id");
        if (videoId != null) {
            Video video = videoDAO.findById(Video.class, videoId);
            if (video != null) {
                request.setAttribute("video", video); // Gửi video qua để hiển thị
            }
        }
        
        request.setAttribute("view", "/site/home/share.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // (AuthFilter cũng đã chặn nếu chưa login)
        
        User user = (User) request.getSession().getAttribute("user");
        String videoId = request.getParameter("videoId");
        String emails = request.getParameter("emails");
        
        Video video = videoDAO.findById(Video.class, videoId);
        
        if (video != null && user != null && emails != null && !emails.isEmpty()) {
            // Lưu lịch sử share vào CSDL
            Share share = new Share();
            share.setUser(user);
            share.setVideo(video);
            share.setEmails(emails); // Lưu danh sách email đã share
            shareDAO.create(share);
            
            // (Đây là nơi bạn gọi API gửi mail thật nếu có)
            
            request.setAttribute("message", "Gửi link thành công đến: " + emails);
        } else {
            request.setAttribute("message", "Lỗi: Không thể gửi link.");
        }
        
        // Tải lại trang với thông báo
        request.setAttribute("video", video); // Gửi lại video
        request.setAttribute("view", "/site/home/share.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}