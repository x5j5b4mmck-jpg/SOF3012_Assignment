package com.poly.asm.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import com.poly.asm.dao.UserDAO;
import com.poly.asm.entity.User;

@WebServlet("/admin/user-management")
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public UserManagementServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String userId = request.getParameter("id");
        
        // Trạng thái form: "create" (nghĩa là [Khởi đầu]) hoặc "edit"
        String formState = "create"; 
        User formUser = null; // User đang được edit

        if (action != null) {
            if (action.equals("edit")) {
                formUser = userDAO.findById(User.class, userId);
                formState = "edit";
            }
            // (Không cần action=delete ở đây, chúng ta sẽ xử lý ở doPost)
        }
        
        request.setAttribute("formUser", formUser); 
        request.setAttribute("formState", formState); // Quyết định nút nào bị disable

        // Xử lý phân trang (giống hệt Video)
        int pageSize = 10; // Giống spec
        long totalUsers = userDAO.count();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
        
        String pageParam = request.getParameter("page");
        int currentPage = 1;
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }
        
        List<User> userList = userDAO.findAll(currentPage, pageSize);
        
        request.setAttribute("userList", userList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalUsers", totalUsers);

        // Chuyển đến trang JSP
        request.setAttribute("view", "/admin/user-management.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if (action != null) {
            try {
                User user = new User();
                BeanUtils.populate(user, request.getParameterMap());
                
                // Xử lý 'admin' (Role)
                user.setAdmin(request.getParameter("admin").equals("true"));

                if (action.equals("update")) {
                    userDAO.update(user);
                    request.setAttribute("message", "Cập nhật thành công!");
                    // Giữ trạng thái [Edit]
                    request.setAttribute("formUser", user);
                    request.setAttribute("formState", "edit");
                    
                } else if (action.equals("delete")) {
                    userDAO.delete(user);
                    request.setAttribute("message", "Xóa người dùng thành công!");
                    // Chuyển về trạng thái [Khởi đầu]
                    request.setAttribute("formState", "create");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Lỗi: " + e.getMessage());
            }
        }
        
        // Tải lại trang (và danh sách)
        doGet(request, response);
    }
}