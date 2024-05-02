/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.updateInfoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.quanlyuserMODEL;

/**
 *
 * @author DELL
 */
public class updateInfoUser extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updateInfoUser</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateInfoUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
         String action = request.getParameter("action");
        if ("changeinfo".equals(action)) {
             int id = Integer.parseInt(request.getParameter("id_infoUser"));
            String phone = request.getParameter("phoneNumber");
            String username = request.getParameter("username");
            if (id == 0 || phone.isEmpty() || username.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin người dùng.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
            // Tạo đối tượng quanlyuserMODEL với thông tin người dùng
            quanlyuserMODEL user = new quanlyuserMODEL();
            user.setId(id);
            user.setPhone(phone);
            user.setName(username);

            updateInfoDAO infoDAO = new updateInfoDAO();
            try {
                if (infoDAO.updateInfoUser(user)) {
                     Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        String encodedUsername = URLEncoder.encode(username, "UTF-8");
                        // Update the value of the existing username cookie
                        cookie.setValue(encodedUsername);
                        response.addCookie(cookie);
                        break; // Stop searching for the cookie
                    }
                }
            }
                    String successMessage = "Cập nhật thông tin thành công";
                    request.setAttribute("successMessage", successMessage);
                     request.getRequestDispatcher("profile.jsp").forward(request, response);
                } else {
                    // Cập nhật không thành công
                    String errorMessage = "Cập nhật thông tin không thành công";
                    request.setAttribute("errorMessage", errorMessage);
                     request.getRequestDispatcher("profile.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else if ("changepass".equals(action)) {
             int id = Integer.parseInt(request.getParameter("txtadminid"));
            String pass = request.getParameter("txtadminpass");
            String confirmpass = request.getParameter("txtadminpassconfirm");
            if (id == 0 || pass.isEmpty() || confirmpass.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin người dùng.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
             if (!pass.equals(confirmpass)) {
        String errorMessage = "Mật khẩu và xác nhận mật khẩu không khớp.";
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
        return; // Ngừng xử lý nếu mật khẩu không khớp
    }
            
            // Tạo đối tượng quanlyuserMODEL với thông tin người dùng
            quanlyuserMODEL user = new quanlyuserMODEL();
            user.setId(id);
            user.setPass(pass);
            // Thực hiện cập nhật thông tin người dùng vào cơ sở dữ liệu (DAO)
            updateInfoDAO infoDAO = new updateInfoDAO();
            try {
                if (infoDAO.updatePassUser(user)) {
                    // Cập nhật thành công
                    String successMessage = "Cập nhật thông tin thành công";
                    request.setAttribute("successMessage", successMessage);
                                request.getRequestDispatcher("profile.jsp").forward(request, response);

                } else {
                    // Cập nhật không thành công
                    String errorMessage = "Cập nhật thông tin không thành công";
                    request.setAttribute("errorMessage", errorMessage);
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
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
