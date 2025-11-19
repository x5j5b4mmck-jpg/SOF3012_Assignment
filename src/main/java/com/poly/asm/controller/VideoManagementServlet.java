package com.poly.asm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.beanutils.BeanUtils;
import com.poly.asm.dao.VideoDAO;
import com.poly.asm.entity.Video;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet("/admin/video-management")
public class VideoManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;

    public VideoManagementServlet() {
        super();
        this.videoDAO = new VideoDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String videoId = request.getParameter("id");
        
        String formState = "create"; 
        Video formVideo = null;

        if (action != null) {
            switch (action) {
                case "edit":
                    // [LOGIC EDIT]: Lấy thông tin video từ DB đổ lên Form
                    formVideo = videoDAO.findById(Video.class, videoId);
                    if (formVideo != null) {
                        formState = "edit"; // Chuyển trạng thái sang Edit
                    }
                    break;
                    
                case "delete":
                    try {
                        Video videoToDelete = new Video();
                        videoToDelete.setId(videoId);
                        
                        // Lúc này nó sẽ gọi hàm delete mới trong VideoDAO
                        videoDAO.delete(videoToDelete);
                        
                        request.setAttribute("message", "Xóa video thành công!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("message", "Lỗi: Không thể xóa video này!");
                    }
                    formState = "create"; 
                    break;
                    
                case "reset":
                    formState = "create";
                    formVideo = new Video(); // Reset form về rỗng
                    break;
            }
        }
        
        // Nếu formVideo null (khi chạy lần đầu hoặc sau khi reset), khởi tạo mới để tránh lỗi JSP
        if (formVideo == null) {
            formVideo = new Video();
            formVideo.setPoster("placeholder.jpg"); // Ảnh mặc định
        }
        
        request.setAttribute("formVideo", formVideo);
        request.setAttribute("formState", formState);

        // --- PHÂN TRANG ---
        int pageSize = 5;
        long totalVideos = videoDAO.count();
        int totalPages = (int) Math.ceil((double) totalVideos / pageSize);
        
        String pageParam = request.getParameter("page");
        int currentPage = 1;
        try {
            if (pageParam != null) currentPage = Integer.parseInt(pageParam);
        } catch (Exception e) {}
        
        List<Video> videoList = videoDAO.findAll(currentPage, pageSize);
        
        request.setAttribute("videoList", videoList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalVideos", totalVideos);

        request.setAttribute("view", "/admin/video-management.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action"); // "create" hoặc "update"
        
        if (action != null) {
            try {
                Video video = new Video();
                BeanUtils.populate(video, request.getParameterMap());
                
                // [XỬ LÝ ẢNH]
                Part filePart = request.getPart("cover");
                
                if (filePart != null && filePart.getSize() > 0) {
                    // TRƯỜNG HỢP 1: Có chọn ảnh mới -> Lưu ảnh mới
                    String realPath = request.getServletContext().getRealPath("/logos");
                    if (!new File(realPath).exists()) new File(realPath).mkdirs();
                    
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    filePart.write(realPath + File.separator + fileName);
                    video.setPoster(fileName);
                } else {
                    // TRƯỜNG HỢP 2: Không chọn ảnh mới -> Giữ lại ảnh cũ
                    // Nếu là Update, ta phải tìm video cũ trong DB để lấy lại tên ảnh
                    if (action.equals("update")) {
                        Video oldVideo = videoDAO.findById(Video.class, video.getId());
                        if (oldVideo != null) {
                            video.setPoster(oldVideo.getPoster());
                        }
                    }
                }

                // [XỬ LÝ RADIO BUTTON]
                video.setActive(request.getParameter("active") != null && request.getParameter("active").equals("true"));

                // [THỰC HIỆN CSDL]
                if (action.equals("create")) {
                    if (videoDAO.findById(Video.class, video.getId()) != null) {
                        request.setAttribute("message", "Lỗi: ID Video đã tồn tại!");
                    } else {
                        videoDAO.create(video);
                        request.setAttribute("message", "Thêm mới thành công!");
                    }
                } else if (action.equals("update")) {
                    videoDAO.update(video);
                    request.setAttribute("message", "Cập nhật thành công!");
                    request.setAttribute("formState", "edit"); // Giữ trạng thái edit để người dùng thấy kết quả
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            }
        }
        
        doGet(request, response);
    }
}