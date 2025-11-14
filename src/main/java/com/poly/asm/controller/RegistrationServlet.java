package com.poly.asm.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poly.asm.dao.UserDAO;
import com.poly.asm.entity.User;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public RegistrationServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("view", "/site/user/register.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        
        // (Bạn nên validate dữ liệu kỹ hơn: check trùng username, email, pass mạnh...)
        
        try {
            User user = new User();
            user.setId(username);
            user.setPassword(pass);
            user.setFullname(fullname);
            user.setEmail(email);
            user.setAdmin(false); // Mặc định là user thường
            
            userDAO.create(user);
            
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            // Chuyển về trang login
            response.sendRedirect(request.getContextPath() + "/login");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: Tên đăng nhập hoặc email đã tồn tại.");
            request.setAttribute("view", "/site/user/register.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
        }
    }
}