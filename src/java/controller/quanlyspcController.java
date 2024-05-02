/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import dao.quanlyspcDAO;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.quanlyspcMODEL;
import model.supplierMODEL;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;

/**
 *
 * @author DELL
 */
@WebServlet(name = "quanlyspcController", urlPatterns = {"/quanlyspcController"})
public class quanlyspcController extends HttpServlet {

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

            quanlyspcDAO spcDAO = new quanlyspcDAO();
            List<supplierMODEL> suppliers = spcDAO.getAllSuppliers();
            int currentPage = 1;
            int recordsPerPage = 5;
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                currentPage = Integer.parseInt(pageStr);
            }
            String keyword = request.getParameter("searchUser");
            List<quanlyspcMODEL> spcList;
            if (keyword != null && !keyword.isEmpty()) {
                spcList = spcDAO.searchOrders(keyword, currentPage, recordsPerPage);
            } else {
                // Otherwise, display the default user list with pagination
                spcList = spcDAO.getData(currentPage, recordsPerPage);
            }
            int totalRecords = keyword != null && !keyword.isEmpty()
                    ? spcDAO.getTotalSearchOrderCount(keyword)
                    : spcDAO.getTotalnccCount();
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
            request.setAttribute("spcList", spcList);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("suppliers", suppliers);

            request.getRequestDispatcher("quanlysanpham.jsp").forward(request, response);
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

        String action = request.getParameter("action");

        if ("getProductPrice".equals(action)) {
            // Lấy id_spncc từ request
            int id_spncc = Integer.parseInt(request.getParameter("id_spncc"));

            // Gọi DAO để lấy giá sản phẩm
            quanlyspcDAO dao = new quanlyspcDAO();
            try {
                int price = dao.getProductPrice(id_spncc);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(String.valueOf(price));
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            processRequest(request, response);
        }
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
        String action = request.getParameter("action");

        if ("addProductToCTPX".equals(action)) {
            try {
                int id_spncc = Integer.parseInt(request.getParameter("spncc"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int price = Integer.parseInt(request.getParameter("price"));

                quanlyspcDAO dao = new quanlyspcDAO();
                int spnccQuantity = dao.checkQuantity(id_spncc);
                if (spnccQuantity < quantity) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } else {
                    dao.addCTPX(id_spncc, quantity, price);

                    // Trừ `spncc.quantity` tương ứng
                    dao.updateSPNCCQuantity(id_spncc, quantity);

                    // Kiểm tra xem `id_spncc` có trong `sanphamchinh` không
                    if (dao.isID_SPNCCInSanPhamChinh(id_spncc)) {
                        // Nếu có, cập nhật `sanphamchinh`
                        dao.updateSanPhamChinh(id_spncc, quantity, price);
                    } else {
                        // Nếu không, thêm dòng mới vào `sanphamchinh`
                        dao.insertSanPhamChinh(id_spncc, quantity, price);
                    }

                    // Không cần chuyển hướng trong trường hợp này
                    // Không cần gửi phản hồi dữ liệu, chỉ cần phản hồi trạng thái HTTP 200 OK
                    response.setStatus(HttpServletResponse.SC_OK);
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("updateProduct".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                double price = Double.parseDouble(request.getParameter("price"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                double discount = Double.parseDouble(request.getParameter("discount"));

                quanlyspcDAO dao = new quanlyspcDAO();
                int id_spncc = dao.getIdSPNCCBySPC(id);
                int spnccQuantity = dao.checkQuantity(id_spncc);
                int q_new = quantity - dao.getQuantitySPC(id);
                if (spnccQuantity < q_new) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } else {
                    dao.addCTPX(id_spncc, q_new, (int) price);

                    dao.updateSPNCCQuantity(id_spncc, q_new);
                    dao.updateSPChinh(id, quantity, price, rating, discount);

                    response.setStatus(HttpServletResponse.SC_OK);
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("deleteProduct".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                quanlyspcDAO dao = new quanlyspcDAO();
                dao.deleteSPC(id);

                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("exportProductToCTPX".equals(action)) {
            String spncc = request.getParameter("spncc");
            String quantity = request.getParameter("quantity");
            String price = request.getParameter("price");
            
            // Gọi phương thức tạo PDF
            createPDF(spncc, quantity, price, request,response);
        } 
        else if ("exportUpdatedProductInfo".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id")) ;
                String quantity =request.getParameter("quantity");
                String price = request.getParameter("price");
                String rating = request.getParameter("rating");
                String discount = request.getParameter("discount");
            
            // Gọi phương thức tạo PDF
            updatePDF(id, quantity, price,rating, discount,request ,response);
        } else {
            processRequest(request, response);
        }
    }

    private void createPDF(String spncc, String quantity, String price,HttpServletRequest request, HttpServletResponse response) {

    try {
        
        int id_spncc = Integer.parseInt(spncc);
        quanlyspcDAO dao = new quanlyspcDAO();
        String name = dao.getProductName(id_spncc);
      
        String desktopPath = System.getProperty("user.home") + "\\Desktop";

        // Đường dẫn tới tệp PDF
        String filePath = desktopPath + "\\" + spncc + ".pdf";

        // Tạo một Document mới với iText
        Document document = new Document();

        // Tạo PdfWriter để ghi PDF vào tệp
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        // Mở Document
        document.open();

        // Sử dụng font Times New Roman
        BaseFont timesNewRoman = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(timesNewRoman, 14, Font.BOLD);
        Font contentFont = new Font(timesNewRoman, 12);

        // Tiêu đề: THÔNG TIN SẢN PHẨM
        Paragraph title = new Paragraph("THÔNG TIN PHIẾU XUẤT SẢN PHẨM", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Thông tin sản phẩm
        Paragraph product = new Paragraph("Sản phẩm: " + name, contentFont);
        Paragraph quantityInfo = new Paragraph("Số lượng: " + quantity, contentFont);
        Paragraph priceInfo = new Paragraph("Giá: " + price, contentFont);

        document.add(product);
        document.add(quantityInfo);
        document.add(priceInfo);

        // Lấy ngày giờ hiện tại
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);

        // Thêm ngày giờ vào PDF
        Paragraph dateInfo = new Paragraph("Ngày giờ: " + formattedDate, contentFont);
        document.add(dateInfo);

                        Cookie[] cookies = request.getCookies();
                String username = null;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            username = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        } 
                    }
                }

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            paragraph.add(new Chunk("Người tạo phiếu: " + username, contentFont));
            
            document.add(paragraph);
            document.add(Chunk.NEWLINE); // Dòng trắng
        
        // Đóng Document
        document.close();
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    }   catch (Exception ex) {
            Logger.getLogger(quanlyspcController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    private void updatePDF(int id, String quantity, String price, String rating, String discount, HttpServletRequest request,HttpServletResponse response) {
    try {
        
        quanlyspcDAO dao = new quanlyspcDAO();
        String name = dao.getProductName_update(id);

        String desktopPath = System.getProperty("user.home") + "\\Desktop";

        // Đường dẫn tới tệp PDF
        String filePath = desktopPath + "\\" + id + "_updated.pdf";

        // Tạo một Document mới với iText
        Document document = new Document();

        // Tạo PdfWriter để ghi PDF vào tệp
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        // Mở Document
        document.open();

        // Sử dụng font Times New Roman
        BaseFont timesNewRoman = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(timesNewRoman, 14, Font.BOLD);
        Font contentFont = new Font(timesNewRoman, 12);

        // Tiêu đề: THÔNG TIN SẢN PHẨM
        Paragraph title = new Paragraph("THÔNG TIN PHIẾU CẬP NHẬT SẢN PHẨM", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Thông tin sản phẩm
        Paragraph product = new Paragraph("Sản phẩm: " + name, contentFont);
        Paragraph quantityInfo = new Paragraph("Số lượng: " + quantity, contentFont);
        Paragraph priceInfo = new Paragraph("Giá: " + price, contentFont);
        Paragraph ratingInfo = new Paragraph("Rating: " + rating, contentFont);
        Paragraph discountInfo = new Paragraph("Discount: " + discount, contentFont);

        document.add(product);
        document.add(quantityInfo);
        document.add(priceInfo);
        document.add(ratingInfo);
        document.add(discountInfo);

        // Lấy ngày giờ hiện tại
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);

        // Thêm ngày giờ vào PDF
        Paragraph dateInfo = new Paragraph("Ngày giờ: " + formattedDate, contentFont);
        document.add(dateInfo);

                 Cookie[] cookies = request.getCookies();
                String username = null;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            username = URLDecoder.decode(cookie.getValue(), "UTF-8");
                        } 
                    }
                }

            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            paragraph.add(new Chunk("Người tạo phiếu: " + username, contentFont));
            document.add(paragraph);
            document.add(Chunk.NEWLINE); // Dòng trắng
        // Đóng Document
        document.close();
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    } catch (Exception ex) {
        Logger.getLogger(quanlyspcController.class.getName()).log(Level.SEVERE, null, ex);
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
