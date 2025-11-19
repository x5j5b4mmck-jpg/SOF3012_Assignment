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

@WebFilter(urlPatterns = {"/admin/*"}) // Chặn tất cả các URL bắt đầu bằng /admin
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); 
        
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // 1. Chưa đăng nhập
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (!user.getAdmin()) {
            // 2. Đã đăng nhập nhưng không phải Admin
            req.setAttribute("message", "Bạn không có quyền truy cập trang này!");
            // Chuyển về trang chủ (hoặc trang lỗi)
            resp.sendRedirect(req.getContextPath() + "/index"); 
        } else {
            // 3. Là Admin -> Cho phép đi tiếp
            chain.doFilter(request, response);
        }
    }
}