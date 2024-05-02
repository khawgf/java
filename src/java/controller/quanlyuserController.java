/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.quanlyuserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.quanlyuserMODEL;
import model.quanlyuserStatusMODEL;
import model.quanlyuserTypeMODEL;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author DELL
 */
public class quanlyuserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");

    try {
        quanlyuserDAO userDAO = new quanlyuserDAO();
        int currentPage = 1;  // Default to the first page
        int recordsPerPage = 10;  // Number of records per page

        // Get the current page from the request
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            currentPage = Integer.parseInt(pageStr);
        }

        // Get the search keyword from the request
        String keyword = request.getParameter("searchUser");

        List<quanlyuserMODEL> userList;

        if (keyword != null && !keyword.isEmpty()) {
            // If a search keyword is provided, perform a search
            userList = userDAO.searchUsers(keyword, currentPage, recordsPerPage);
        } else {
            // Otherwise, display the default user list with pagination
            userList = userDAO.getUsersForPage(currentPage, recordsPerPage);
        }

        int totalRecords = keyword != null && !keyword.isEmpty()
                ? userDAO.getTotalSearchUserCount(keyword)
                : userDAO.getTotalUserCount();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // Set attributes for rendering in the JSP
        request.setAttribute("userList", userList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);

        // Get the maximum ID
        int maxId;
        try {
            maxId = userDAO.getMaxId();
        } catch (Exception e) {
            e.printStackTrace();
            maxId = 0; // Handle the exception as needed
        }
        request.setAttribute("maxId", maxId);

        List<quanlyuserStatusMODEL> userStatusList = userDAO.getDataCBBStatus();
        request.setAttribute("userStatusList", userStatusList);

        List<quanlyuserTypeMODEL> userTypeList = userDAO.getDataCBBType();
        request.setAttribute("userTypeList", userTypeList);

        request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    String action = request.getParameter("action"); // Xác định hành động (add hoặc update)
if ("add".equals(action)) {
            // Biểu thức chính quy kiểm tra số điện thoại theo chuẩn Việt Nam: bắt đầu bằng 0 và có 10 chữ số.
            String phoneNumberPattern = "^0\\d{9}";
            // Lấy thông tin từ request
            int id = Integer.parseInt(request.getParameter("txtadminid"));
            String phone = request.getParameter("txtadminuser");
            String pass = request.getParameter("txtadminpass");
            String name = request.getParameter("txtadminemail");
            String status = request.getParameter("status");
            String type = request.getParameter("type");

              if (id == 0 || phone.isEmpty() || pass.isEmpty() || name.isEmpty() || status.isEmpty() || type.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin người dùng.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
            // Tạo một Pattern từ biểu thức chính quy
            Pattern pattern = Pattern.compile(phoneNumberPattern);
            // Kiểm tra số điện thoại
            Matcher matcher = pattern.matcher(phone);
            // Nếu số điện thoại hợp lệ
            if (matcher.matches()) {
                // Tạo đối tượng quanlyuserMODEL với thông tin người dùng
                quanlyuserMODEL user = new quanlyuserMODEL();
                user.setId(id);
                user.setPhone(phone);
                user.setPass(pass);
                user.setName(name);
                user.setStatus(status);
                user.setType(type);
                // Thực hiện lưu thông tin người dùng vào cơ sở dữ liệu (DAO)
                quanlyuserDAO userDAO = new quanlyuserDAO();
                boolean userAdded;
                try {
                    userAdded = userDAO.addOrUpdateUser(user);
                    if (userAdded) {
                        // User added successfully, set a success message as a request attribute
                        String successMessage = "User added successfully";
                        request.setAttribute("successMessage", successMessage);
                    } else {
                        // User not added, set an error message as a request attribute
                        String errorMessage = "Failed to add user";
                        request.setAttribute("errorMessage", errorMessage);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Số điện thoại không hợp lệ
                String errorMessage = "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại hợp lệ.";
                request.setAttribute("errorMessage", errorMessage);
            }
            request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
        } else if ("update".equals(action)) {
    int id = Integer.parseInt(request.getParameter("txtadminidUpdate"));
    String phone = request.getParameter("txtadminuserUpdate");
    String pass = request.getParameter("txtadminpassUpdate");
    String name = request.getParameter("txtadminemailUpdate");
    String status = request.getParameter("statusUpdate");
    String type = request.getParameter("typeUpdate");
 String phoneNumberPattern = "^0\\d{9}";
    
            if (id == 0 || phone.isEmpty() || pass.isEmpty() || name.isEmpty() || status.isEmpty() || type.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin người dùng.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
              
              Pattern pattern = Pattern.compile(phoneNumberPattern);
            // Kiểm tra số điện thoại
            Matcher matcher = pattern.matcher(phone);
            // Nếu số điện thoại hợp lệ
            if (matcher.matches()) {
        quanlyuserDAO userDAO = new quanlyuserDAO();

        try {
            
            // Kiểm tra xem số điện thoại đã tồn tại hay chưa
            if (userDAO.isPhoneExist(phone)) {
                String errorMessage = "Phone tồn tại";
                request.setAttribute("errorMessage", errorMessage);
            } else {
                // Tạo đối tượng quanlyuserMODEL với thông tin người dùng
                quanlyuserMODEL user = new quanlyuserMODEL();
                user.setId(id);
                user.setPhone(phone);
                user.setPass(pass);
                user.setName(name);
                user.setStatus(status);
                user.setType(type);
                
                boolean userUpdated;
                try {
                    userUpdated = userDAO.updateUser(user);
                    
                    if (userUpdated) {
                        // User updated successfully, set a success message as a request attribute
                        String successMessage = "User updated successfully";
                        request.setAttribute("successMessage", successMessage);
                    } else {
                        // User not updated, set an error message as a request attribute
                        String errorMessage = "Failed to update user";
                        request.setAttribute("errorMessage", errorMessage);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   } catch (Exception ex) {
            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
        }} else {
                // Số điện thoại không hợp lệ
                String errorMessage = "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại hợp lệ.";
                request.setAttribute("errorMessage", errorMessage);
            }

    request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
}else if ("delete".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("userId");
        // Thực hiện lưu thông tin người dùng vào cơ sở dữ liệu (DAO)
        quanlyuserDAO userDAO = new quanlyuserDAO();
        boolean userDeleted;
        try {
            userDeleted = userDAO.deleteUser(id);

            if (userDeleted) {
                // User deleted successfully, set a success message as a request attribute
                String successMessage = "User deleted successfully";
                request.setAttribute("successMessage", successMessage);
            } else {
                // User not deleted, set an error message as a request attribute
                String errorMessage = "Failed to delete user";
                request.setAttribute("errorMessage", errorMessage);
            }
            request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
