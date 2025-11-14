package com.poly.asm.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.poly.asm.entity.User;

// Áp dụng filter cho các trang yêu cầu đăng nhập
@WebFilter(urlPatterns = { "/favorites", "/like", "/unlike", "/share", "/logout", 
                           "/change-password", "/edit-profile" })
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false); // false: không tự động tạo session mới
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // Chưa đăng nhập
            req.setAttribute("message", "Vui lòng đăng nhập để sử dụng chức năng này.");
            // Lưu lại trang đang bị chặn để quay lại sau khi đăng nhập thành công
            // (Bạn cần xử lý logic này ở LoginServlet)
            String requestedUri = req.getRequestURI();
            req.getSession().setAttribute("redirectUri", requestedUri);
            
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Đã đăng nhập, cho phép đi tiếp
            chain.doFilter(request, response);
        }
    }
}