/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;
import dao.loginDAO;
import dao.updateInfoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.quanlyuserMODEL;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author DELL
 */
public class forgetPassController extends HttpServlet {

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
            out.println("<title>Servlet forgetPassController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet forgetPassController at " + request.getContextPath() + "</h1>");
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
        String phoneNumberPattern = "^0\\d{9}";
        String emailUserPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        String phone = request.getParameter("txtadminpass");
        String email = request.getParameter("txtadminpassconfirm");

        if (phone.isEmpty() || email.isEmpty()) {
            String errorMessage = "Vui lòng điền đầy đủ thông tin.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return; // Ngừng xử lý nếu có trường rỗng
        }

        // Kiểm tra số điện thoại
        Pattern phonePattern = Pattern.compile(phoneNumberPattern);
        Matcher phoneMatcher = phonePattern.matcher(phone);

        // Kiểm tra email
        Pattern emailPattern = Pattern.compile(emailUserPattern);
        Matcher emailMatcher = emailPattern.matcher(email);
         loginDAO loginDAO = new loginDAO();
        // Kiểm tra độ dài của mật khẩu và xác nhận mật khẩu
        if (phoneMatcher.matches() && emailMatcher.matches()) {
            try {
                if (loginDAO.isPhoneExist(phone)) {
                      String randomPassword = generateRandomPassword(6);
                  quanlyuserMODEL user = new quanlyuserMODEL();
                    user.setPhone(phone);
                    user.setPass(randomPassword);
            try {
                if (loginDAO.updatePassUser(user)) {
                      sendPasswordEmail(email, randomPassword);
                        String successMessage = "Mật khẩu đã được cấp lại thành công";
                        request.setAttribute("successMessage", successMessage);
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            else {
                    String errorMessage = "Đã xảy ra lỗi vui lòng kiểm tra lại";
                    request.setAttribute("errorMessage", errorMessage);
                        request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
                } else {
                     String errorMessage = "Không tồn tại số điện thoại này.";
            request.setAttribute("errorMessage", errorMessage);
             request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Số điện thoại không hợp lệ hoặc mật khẩu không đủ điều kiện
            String errorMessage = "Số điện thoại hoặc email không hợp lệ.";
            request.setAttribute("errorMessage", errorMessage);
             request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
        private String generateRandomPassword(int length) {
            String digits = "0123456789";
            Random random = new Random();
            StringBuilder password = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(digits.length());
                password.append(digits.charAt(index));
            }

            return password.toString();
        }
        
            private void sendPasswordEmail(String toEmail, String password) {
        final String username = "namhai1617@gmail.com"; // Thay bằng email của bạn
        final String passwordEmail = "nwmu qfby izia kfed"; // Thay bằng mật khẩu của bạn
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, passwordEmail);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Password Reset");
            message.setText("Your new password is: " + password);
            Transport.send(message);
            System.out.println("Password reset email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
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
