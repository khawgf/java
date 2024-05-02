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
import dao.thongkenguoidungDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.thongkenguoidungMODEL;

/**
 *
 * @author DELL
 */
public class thongkeuserController extends HttpServlet {

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
        String datetimeStart = request.getParameter("datetimeStart");
        String datetimeEnd = request.getParameter("datetimeEnd");
        
        try {
            thongkenguoidungDAO thongkendDAO = new thongkenguoidungDAO();
            List<thongkenguoidungMODEL> thongkeNDList;

            if (datetimeStart != null && datetimeEnd != null && !datetimeStart.isEmpty() && !datetimeEnd.isEmpty()) {
                thongkeNDList = thongkendDAO.searchData(datetimeStart, datetimeEnd);
            } else {
                thongkeNDList = thongkendDAO.getData();
            }
            
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
                Paragraph title = new Paragraph("Thống kê người dùng", font);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                 document.add(Chunk.NEWLINE); // Dòng trắng
                // Cài đặt font cho các đoạn văn bản
                Font contentFont = new Font(baseFont, 13);
                 Paragraph khoangThoiGianParagraph = new Paragraph("Thống kê trong khoảng: " + datetimeStart + " - " + datetimeEnd,contentFont);  
                 document.add(khoangThoiGianParagraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    document.add(Chunk.NEWLINE); // Dòng trắng
            for (thongkenguoidungMODEL thongkend : thongkeNDList) {
                    Paragraph paragraph = new Paragraph("Số điện thoại người dùng: " + thongkend.getPhoneUser(), contentFont);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    paragraph = new Paragraph("Tên người dùng: " + thongkend.getNameUser(), contentFont);
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    paragraph = new Paragraph("Tổng tiền đặt hàng: " + thongkend.getTotalCash(), contentFont);
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
              document.add(Chunk.NEWLINE); // Dòng trắng
            paragraph.add(new Chunk("Người tạo phiếu: " + username, contentFont));
            document.add(paragraph);
            document.add(Chunk.NEWLINE); // Dòng trắng

                document.close();
            
            }else {
            request.setAttribute("thongkeNDList", thongkeNDList);
            request.getRequestDispatcher("thongkenguoidung.jsp").forward(request, response);
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
        processRequest(request, response);
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
