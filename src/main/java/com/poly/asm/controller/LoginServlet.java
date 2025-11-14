package com.poly.asm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.poly.asm.dao.UserDAO;
import com.poly.asm.entity.User;
// Bạn cần tự tạo CookieUtil
// import com.poly.asm.util.CookieUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public LoginServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // (Xử lý lấy "Remember me" từ cookie nếu có)
        // String savedUserId = CookieUtil.get("userId", request);
        // if(savedUserId != null) {
        //     request.setAttribute("userId", savedUserId);
        // }
        
        request.setAttribute("view", "/site/user/login.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userId = request.getParameter("username");
        String pass = request.getParameter("password");
        String remember = request.getParameter("remember");
        
        User user = userDAO.checkLogin(userId, pass);
        
        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Lưu user vào session
            
            // Xử lý "Remember me"
            if (remember != null) {
                // Lưu cookie 1 ngày (bạn tự tạo CookieUtil.add)
                // CookieUtil.add("userId", userId, 24, response);
            } else {
                // Xóa cookie
                // CookieUtil.add("userId", userId, 0, response);
            }
            
            // (Xử lý quay lại trang trước đó nếu có)
            
            response.sendRedirect(request.getContextPath() + "/index");
            
        } else {
            // Đăng nhập thất bại
            request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            request.setAttribute("view", "/site/user/login.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
        }
    }
}