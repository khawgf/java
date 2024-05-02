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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.cthdMODEL;
import model.thongkenguoidungMODEL;

/**
 *
 * @author DELL
 */
public class cthdController extends HttpServlet {

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
                int cthdID = Integer.parseInt(request.getParameter("id"));
                        quanlyhoadonDAO orderDAO = new quanlyhoadonDAO();
                List<cthdMODEL> cthdList = orderDAO.getCTHDByID(cthdID);
                
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
                int totalBill = 0;
                // Cài đặt font cho các đoạn văn bản
                Font contentFont = new Font(baseFont, 13);
                 
               Paragraph title = new Paragraph("Chi tiết hóa đơn:" + cthdList.get(0).getNamekh(), font);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(Chunk.NEWLINE); // Dòng trắng

                Paragraph phoneParagraph = new Paragraph("Số điện thoại người dùng: " + cthdList.get(0).getPhoneKH(), contentFont);
                document.add(phoneParagraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                Paragraph addressParagraph = new Paragraph("Địa chỉ: " + cthdList.get(0).getAddress(), contentFont);
                document.add(addressParagraph);
                  document.add(Chunk.NEWLINE); // Dòng trắng
                 Paragraph paragraph_slide = new Paragraph("<------------------------------------------------------------------------------------>", contentFont );
                    paragraph_slide.setAlignment(Element.ALIGN_CENTER);
                     document.add(paragraph_slide);
                       Paragraph paragraph_product = new Paragraph("Sản phẩm", font );
                    paragraph_product.setAlignment(Element.ALIGN_CENTER);
                     document.add(paragraph_product);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    
                  for (cthdMODEL cthd : cthdList) {
                  Paragraph paragraph = new Paragraph("Tên sản phẩm: " + cthd.getNamesp(), contentFont);
                document.add(paragraph);
                  document.add(Chunk.NEWLINE);
                 paragraph = new Paragraph("Số lượng: " + cthd.getQuantity(), contentFont);
                document.add(paragraph);
                  document.add(Chunk.NEWLINE);
                paragraph = new Paragraph("Giá sản phẩm: " + cthd.getPrice(), contentFont);
                document.add(paragraph);
                  document.add(Chunk.NEWLINE);
                  
                  int tichSanPham = Integer.parseInt(cthd.getQuantity()) * Integer.parseInt(cthd.getPrice());
                    paragraph = new Paragraph("Tổng tiền: " +tichSanPham , contentFont );
                    document.add(paragraph);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    paragraph_slide = new Paragraph("<------------------------------------------------------------------------------------>", contentFont );
                    paragraph_slide.setAlignment(Element.ALIGN_CENTER);
                     document.add(paragraph_slide);
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    document.add(Chunk.NEWLINE); // Dòng trắng
                    
                     totalBill += tichSanPham;
                }
                  Paragraph totalBillParagraph = new Paragraph("Tổng Bill: " + totalBill, font);
                  totalBillParagraph.setAlignment(Element.ALIGN_RIGHT);
                    document.add(totalBillParagraph);
                  
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
                 request.setAttribute("cthdList", cthdList);  
                 request.getRequestDispatcher("showcthd.jsp").forward(request, response);
            }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
