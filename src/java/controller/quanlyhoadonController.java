/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import dao.quanlyhoadonDAO;
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
import model.cthdMODEL;
import model.quanlyhoadonMODEL;
import model.quanlyhoadonStatusMODEL;
import model.quanlyuserMODEL;
import model.thongkenguoidungMODEL;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.net.URLDecoder;
/**
 *
 * @author DELL
 */
public class quanlyhoadonController extends HttpServlet {

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
            request.setCharacterEncoding("UTF-8");
          response.setCharacterEncoding("UTF-8");
                    String datetimeStart = request.getParameter("datetimeStart");
        String datetimeEnd = request.getParameter("datetimeEnd");
        try {
            quanlyhoadonDAO hoadonDAO = new quanlyhoadonDAO();
           int currentPage = 1;  // Default to the first page
        int recordsPerPage = 10;  // Number of records per page

        // Get the current page from the request
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            currentPage = Integer.parseInt(pageStr);
        }

        // Get the search keyword from the request
        String keyword = request.getParameter("searchUser");

        List<quanlyhoadonMODEL> hoadonList;

        if (keyword != null && !keyword.isEmpty()) {
            // If a search keyword is provided, perform a search
            hoadonList = hoadonDAO.searchOrders(keyword, currentPage, recordsPerPage);
        }else if (datetimeStart != null && datetimeEnd != null && !datetimeStart.isEmpty() && !datetimeEnd.isEmpty()) {
                hoadonList = hoadonDAO.searchOrdersDateTime(datetimeStart, datetimeEnd, currentPage, recordsPerPage);
            } else {
            // Otherwise, display the default user list with pagination
            hoadonList = hoadonDAO.getData(currentPage, recordsPerPage);
        }

            int totalRecords = 0;
            if (datetimeStart != null && datetimeEnd != null) {
                // Nếu tìm kiếm theo thời gian, lấy tổng số bản ghi dựa trên thời gian
                totalRecords = hoadonDAO.getTotalSearchOrderCountDateTime(datetimeStart, datetimeEnd);
            } else if (keyword != null && !keyword.isEmpty()) {
                // Nếu có từ khóa tìm kiếm, lấy tổng số bản ghi dựa trên từ khóa
                totalRecords = hoadonDAO.getTotalSearchOrderCount(keyword);
            } else {
                // Ngược lại, lấy tổng số bản ghi mặc định
                totalRecords = hoadonDAO.getTotalOrderCount();
            }
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // Set attributes for rendering in the JSP
        request.setAttribute("hoadonList", hoadonList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        
            
          List<quanlyhoadonStatusMODEL> orderStatusList = hoadonDAO.getDataCBBStatus();
         request.setAttribute("orderStatusList", orderStatusList);
         
           String exportPDF = request.getParameter("exportPDF");
            if (exportPDF != null && exportPDF.equals("true")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=thongkesanpham.pdf");

                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
                
                // Thiết lập font mặc định cho tệp PDF
                BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);;
                Font font = new Font(baseFont, 16, Font.BOLD);

                document.open();
                Paragraph title = new Paragraph("Thống kê hóa đơn", font);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                 document.add(Chunk.NEWLINE); // Dòng trắng
                // Cài đặt font cho các đoạn văn bản
                Font contentFont = new Font(baseFont, 13);
                 Paragraph khoangThoiGianParagraph = new Paragraph("" + datetimeStart + " - " + datetimeEnd,contentFont);
                                  khoangThoiGianParagraph.setAlignment(Element.ALIGN_RIGHT);    
                 document.add(khoangThoiGianParagraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                for (quanlyhoadonMODEL hoadon : hoadonList) {
                    Paragraph paragraph = new Paragraph("Số điện thoại : " + hoadon.getPhone(), contentFont);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    paragraph = new Paragraph("Địa chỉ: " + hoadon.getAddress(), contentFont);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    paragraph = new Paragraph("Trạng thái: " + hoadon.getStatus(), contentFont);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    Paragraph paragraph_slide = new Paragraph("<------------------------------------------------------------------------------------>", contentFont );
                    paragraph_slide.setAlignment(Element.ALIGN_CENTER);
                     document.add(paragraph_slide);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    document.add(Chunk.NEWLINE); // Dòng trắng
                }
                
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

                document.close();
            
            }else {
            request.getRequestDispatcher("quanlyhoadon.jsp").forward(request, response);
            }
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
          String action = request.getParameter("action");
        if ("update".equals(action)) {
    int id = Integer.parseInt(request.getParameter("txtadminid"));
    String phone = request.getParameter("txtOrderPhone");
    String address = request.getParameter("txtOrderAddress");
    String status = request.getParameter("selectOrderActivity");

     if (phone.isEmpty() || address.isEmpty() || status.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin hóa đơn.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
    
    quanlyhoadonDAO orderDAO = new quanlyhoadonDAO();
        try {
                // Tạo đối tượng quanlyuserMODEL với thông tin người dùng
                quanlyhoadonMODEL order = new quanlyhoadonMODEL();
                order.setId(id);
                order.setPhone(phone);
                order.setAddress(address);
                order.setStatus(status);
                boolean orderUpdated;
                try {
                    orderUpdated = orderDAO.updateOrder(order);
                    
                    if (orderUpdated) {
                        // User updated successfully, set a success message as a request attribute
                        String successMessage = "Order updated successfully";
                        request.setAttribute("successMessage", successMessage);
                    } else {
                        // User not updated, set an error message as a request attribute
                        String errorMessage = "Failed to update order";
                        request.setAttribute("errorMessage", errorMessage);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    catch (Exception ex) {
            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    request.getRequestDispatcher("quanlyhoadon.jsp").forward(request, response);
}else if ("delete".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        quanlyhoadonDAO orderDAO = new quanlyhoadonDAO();
        boolean orderDeleted;
        try {
            orderDeleted = orderDAO.deleteOrder(id);

            if (orderDeleted) {
                // User deleted successfully, set a success message as a request attribute
                String successMessage = "Order deleted successfully";
                request.setAttribute("successMessage", successMessage);
            } else {
                // User not deleted, set an error message as a request attribute
                String errorMessage = "Failed to delete Order";
                request.setAttribute("errorMessage", errorMessage);
            }
            request.getRequestDispatcher("quanlyhoadon.jsp").forward(request, response);
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
