package com.poly.asm.controller;

import java.io.IOException;
import java.util.List; // Thêm import
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poly.asm.dao.VideoDAO;
import com.poly.asm.entity.Video;

@WebServlet("/detail")
public class VideoDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    public VideoDetailServlet() {
        super();
        this.videoDAO = new VideoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String videoId = request.getParameter("id");
        
        if (videoId != null) {
            Video video = videoDAO.findById(Video.class, videoId);
            
            if (video != null) {
                // 1. TĂNG LƯỢT XEM (theo spec [Poster].Click)
                video.setViews(video.getViews() + 1);
                videoDAO.update(video);
                
                request.setAttribute("video", video);
                
                // 2. LẤY VIDEO LIÊN QUAN (Sidebar)
                // Lấy 5 video nhiều lượt xem nhất, loại trừ video hiện tại
                List<Video> relatedVideos = videoDAO.findTop5Related(videoId);
                request.setAttribute("relatedVideos", relatedVideos);

                // 3. XỬ LÝ "ĐÃ XEM" (Lấy từ cookie) - Chức năng nâng cao
                // (Bạn có thể thêm logic đọc/ghi cookie "viewed" ở đây)
                
                request.setAttribute("view", "/site/home/detail.jsp");
            } else {
                // Không tìm thấy video
                response.sendRedirect(request.getContextPath() + "/index");
                return;
            }
        } else {
            // Không có id
            response.sendRedirect(request.getContextPath() + "/index");
            return;
        }
        
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}