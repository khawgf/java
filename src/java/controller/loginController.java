/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.loginDAO;
import dao.quanlyuserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.loginMODEL;
import model.quanlyuserMODEL;

/**
 *
 * @author DELL
 */
public class loginController extends HttpServlet {

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
            out.println("<title>Servlet loginController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginController at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
         String action = request.getParameter("action"); // Xác định hành động (add hoặc update)
    if ("register".equals(action)) {
        String phoneNumberPattern = "^0\\d{9}";
        String phone = request.getParameter("newuserphone");
        String name = request.getParameter("newusername");
        String pass = request.getParameter("newuserpassword");
        String confirmPass = request.getParameter("confirmuserpassword");
        loginDAO loginDAO = new loginDAO();
        if (phone.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || name.isEmpty()) {
            String errorMessage = "Vui lòng điền đầy đủ thông tin người dùng.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return; // Ngừng xử lý nếu có trường rỗng
        }
        Pattern phonePattern = Pattern.compile(phoneNumberPattern);
        Matcher phoneMatcher = phonePattern.matcher(phone);
        if (pass.length() > 5 && pass.equals(confirmPass) && phoneMatcher.matches()) {
            try {
                if (loginDAO.isPhoneExist(phone)) {
                    String errorMessage = "Số điện thoại đã tồn tại. Vui lòng chọn số điện thoại khác.";
                    request.setAttribute("errorMessage", errorMessage);
                      request.getRequestDispatcher("Login.jsp").forward(request, response);
                } else {
                    quanlyuserMODEL user = new quanlyuserMODEL();
                    user.setPhone(phone);
                    user.setPass(pass);
                    user.setName(name);
                    user.setType(confirmPass);
                    try {
                        loginDAO.register(user);
                        String successMessage = "Register successfully";
                        request.setAttribute("successMessage", successMessage);
                         request.getRequestDispatcher("Login.jsp").forward(request, response);
                    } catch (Exception ex) {
                        Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Số điện thoại không hợp lệ hoặc mật khẩu không đủ điều kiện
            String errorMessage = "Số điện thoại không hợp lệ hoặc mật khẩu không đủ điều kiện.";
            request.setAttribute("errorMessage", errorMessage);
             request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
       
    } else if("login".equals(action)) {
    try {
        String userName = request.getParameter("user-name");
        String password = request.getParameter("user-password");
        
          if (userName.isEmpty() || password.isEmpty()) {
            String errorMessage = "Vui lòng điền đầy đủ thông tin người dùng.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return; // Ngừng xử lý nếu có trường rỗng
        }
        
        loginDAO loginDAO = new loginDAO();
        loginMODEL userNameFromDAO = loginDAO.checkLoginAndGetUserName(userName, password);
         loginMODEL employeeNameFromDAO = loginDAO.checkLoginAndGetEmployeeName(userName, password);
         loginMODEL adminNameFromDAO = loginDAO.checkLoginAndGetAdminName(userName, password);
        if (userNameFromDAO != null) {
            Cookie useridCookie = new Cookie("userid", userNameFromDAO.getId());
                useridCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(useridCookie);
               Cookie usernphoneCookie = new Cookie("PhoneNumber", userNameFromDAO.getPhone());
                usernphoneCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(usernphoneCookie);
                Cookie usernameCookie = new Cookie("username", URLEncoder.encode(userNameFromDAO.getName(), "UTF-8"));

                usernameCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(usernameCookie);
                Cookie typeCookie = new Cookie("type", "customer");
                typeCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(typeCookie);
              String successMessage = "Đăng nhập thành công.";
            request.setAttribute("successMessageLogin", successMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else if(adminNameFromDAO != null){
            Cookie useridCookie = new Cookie("userid", adminNameFromDAO.getId());
                useridCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(useridCookie);
               Cookie usernphoneCookie = new Cookie("PhoneNumber", adminNameFromDAO.getPhone());
                usernphoneCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(usernphoneCookie);
                 Cookie usernameCookie = new Cookie("username", "admin");
                usernameCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(usernameCookie);
                 Cookie typeCookie = new Cookie("type", "admin");
                typeCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(typeCookie);
            String successMessage = "Đăng nhập thành công.";
            request.setAttribute("successMessageLogin", successMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }else if (employeeNameFromDAO != null) {
             Cookie useridCookie = new Cookie("userid", employeeNameFromDAO.getId());
                useridCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(useridCookie);
               Cookie usernphoneCookie = new Cookie("PhoneNumber", employeeNameFromDAO.getPhone());
                usernphoneCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(usernphoneCookie);
                 Cookie usernameCookie = new Cookie("username", employeeNameFromDAO.getName());
                usernameCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(usernameCookie);
                Cookie typeCookie = new Cookie("type", "employee");
                typeCookie.setMaxAge(3600); // Số giây cookie tồn tại (ví dụ: 1 giờ)
                response.addCookie(typeCookie);
            String successMessage = "Đăng nhập thành công.";
            request.setAttribute("successMessageLogin", successMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
        
         else {
            String errorMessage = "Tài khoản hoặc mật khẩu không chính xác.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("Login.jsp");
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
