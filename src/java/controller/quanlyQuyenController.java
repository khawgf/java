/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.quanlyquyenDAO;
import dao.quanlyuserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.quanlyquyenMODEL;
import model.quanlyquyenStatusMODEL;
import model.quanlyuserMODEL;

/**
 *
 * @author DELL
 */
public class quanlyQuyenController extends HttpServlet {

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
         response.setContentType("text/html;charset=UTF-8");
        try {
            quanlyquyenDAO quyenDAO = new quanlyquyenDAO();
         int currentPage = 1;  // Default to the first page
        int recordsPerPage = 2;  // Number of records per page
        // Get the current page from the request
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            currentPage = Integer.parseInt(pageStr);
        }
        // Get the search keyword from the request
         String keyword = request.getParameter("searchPermission");
        List<quanlyquyenMODEL> quyenList;
        if (keyword != null && !keyword.isEmpty()) {
            // If a search keyword is provided, perform a search
            quyenList = quyenDAO.searchPermissions(keyword, currentPage, recordsPerPage);
        } else {
            // Otherwise, display the default user list with pagination
            quyenList = quyenDAO.getData(currentPage, recordsPerPage);
        }
        int totalRecords = keyword != null && !keyword.isEmpty()
                ? quyenDAO.getTotalSearchPermissonCount(keyword)
                : quyenDAO.getTotalPermissionCount();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        // Set attributes for rendering in the JSP
        request.setAttribute("quyenList", quyenList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        // Get the search keyword from the request
            int maxId;
            try {
                maxId = quyenDAO.getMaxId();
            } catch (Exception e) {
                e.printStackTrace();
                maxId = 0; // Handle the exception as needed
            }
            request.setAttribute("maxId", maxId);
            
         List<quanlyquyenStatusMODEL> quyenStatusList = quyenDAO.getDataCBBStatus();
         request.setAttribute("quyenStatusList", quyenStatusList);
            
            request.getRequestDispatcher("quanlyquyen.jsp").forward(request, response);
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
            String action = request.getParameter("action"); // Xác định hành động (add hoặc update)

   if ("add".equals(action)) {
    int id = Integer.parseInt(request.getParameter("txtquyenID"));
    String name = request.getParameter("txtquyenName");
    if (name.isEmpty()) {
                String errorMessage = "Vui lòng điền tên quyền.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlyquyen.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
    quanlyquyenDAO quyenDAO = new quanlyquyenDAO();
                try {
                    // Kiểm tra xem tên đã tồn tại hay chưa
                    if (quyenDAO.isNameExist(name)) {
                        String errorMessage = "Name tồn tại";
                        request.setAttribute("errorMessage", errorMessage);
                    } else {
                        // Tạo đối tượng quanlyquyenMODEL với thông tin quyền
                        quanlyquyenMODEL quyen = new quanlyquyenMODEL();
                        quyen.setId(id);
                        quyen.setName(name);
                        
                        boolean quyenAdded;
                        try {
                            quyenAdded = quyenDAO.addPermission(quyen);
                            
                            if (quyenAdded) {
                                // Permission added successfully, set a success message as a request attribute
                                String successMessage = "Permission added successfully";
                                request.setAttribute("successMessage", successMessage);
                            } else {
                                // Permission not added, set an error message as a request attribute
                                String errorMessage = "Failed to add permission";
                                request.setAttribute("errorMessage", errorMessage);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }           } catch (Exception ex) {
                    Logger.getLogger(quanlyQuyenController.class.getName()).log(Level.SEVERE, null, ex);
                }

    request.getRequestDispatcher("quanlyquyen.jsp").forward(request, response);
        }else if ("update".equals(action)) {
    int id = Integer.parseInt(request.getParameter("txtquyenIDUpdate"));
    String name = request.getParameter("txtquyenNameUpdate");
    String status = request.getParameter("selecttypePActivity");
     if (name.isEmpty() || status.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin quyền.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlyquyen.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
    quanlyquyenDAO quyenDAO = new quanlyquyenDAO();
    quanlyquyenMODEL quyen = new quanlyquyenMODEL();
    quyen.setId(id);
    quyen.setName(name);
    quyen.setStatus(status);
    boolean quyenUpdated;
    try {
          if (quyenDAO.isNameExist(name)) {
                        String errorMessage = "Name tồn tại";
                        request.setAttribute("errorMessage", errorMessage);
                    } else {
        quyenUpdated = quyenDAO.updatePermission(quyen);

        if (quyenUpdated) {
            // Permission updated successfully, set a success message as a request attribute
            String successMessage = "Permission updated successfully";
            request.setAttribute("successMessage", successMessage);
        } else {
            // Permission not updated, set an error message as a request attribute
            String errorMessage = "Failed to update Permission";
            request.setAttribute("errorMessage", errorMessage);
        }
    }} catch (Exception ex) {
        Logger.getLogger(quanlyQuyenController.class.getName()).log(Level.SEVERE, null, ex);
    }

    request.getRequestDispatcher("quanlynguoidung.jsp").forward(request, response);
}
else if ("delete".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("userId");
        // Thực hiện lưu thông tin người dùng vào cơ sở dữ liệu (DAO)
        quanlyquyenDAO quyenDAO = new quanlyquyenDAO();
        boolean quyenDeleted;
        try {
            quyenDeleted = quyenDAO.lockPermissionAndUser(id);

            if (quyenDeleted) {
                // User deleted successfully, set a success message as a request attribute
                String successMessage = "Permission locked successfully";
                request.setAttribute("successMessage", successMessage);
            } else {
                // User not deleted, set an error message as a request attribute
                String errorMessage = "Failed to locked user";
                request.setAttribute("errorMessage", errorMessage);
            }
            request.getRequestDispatcher("quanlyquyen.jsp").forward(request, response);
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
