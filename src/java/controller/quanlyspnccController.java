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
import dao.quanlyspnccDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.brandMODEL;
import model.categoryMODEL;
import model.nhacungcapMODEL;
import model.quanlyspcMODEL;
import model.quanlyspnccMODEL;
import model.supplierMODEL;

/**
 *
 * @author DELL
 */
@WebServlet(name = "quanlyspnccController", urlPatterns = {"/quanlyspnccController"})
public class quanlyspnccController extends HttpServlet {

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
            quanlyspnccDAO spnccDAO = new quanlyspnccDAO();
            List<categoryMODEL> category = spnccDAO.getcategoryList();
            List<brandMODEL> brand = spnccDAO.getBrandList();
            List<nhacungcapMODEL> ncc = spnccDAO.getNhacungcapList();

            int currentPage = 1;
            int recordsPerPage = 5;
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                currentPage = Integer.parseInt(pageStr);
            }
            String keyword = request.getParameter("searchUser");
            List<quanlyspnccMODEL> spnccList;
            if (keyword != null && !keyword.isEmpty()) {
                spnccList = spnccDAO.searchOrders(keyword, currentPage, recordsPerPage);
            } else {
                // Otherwise, display the default user list with pagination
                spnccList = spnccDAO.getData(currentPage, recordsPerPage);
            }
            int totalRecords = keyword != null && !keyword.isEmpty()
                    ? spnccDAO.getTotalSearchOrderCount(keyword)
                    : spnccDAO.getTotalnccCount();
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            request.setAttribute("brand", brand);
            request.setAttribute("category", category);
            request.setAttribute("ncc", ncc);
            request.setAttribute("spnccList", spnccList);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);

            request.getRequestDispatcher("quanlysanphamncc.jsp").forward(request, response);
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
                  request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("addProductToCTPN".equals(action)) {
            try {
                int ncc = Integer.parseInt(request.getParameter("selectnccproduct"));
                int category = Integer.parseInt(request.getParameter("selectcategoryproduct"));
                int brand = Integer.parseInt(request.getParameter("selectbrandproduct"));
                String name = request.getParameter("txtadminname");
                String origin = request.getParameter("txtadminorigin");
                int price = Integer.parseInt(request.getParameter("txtadminprices"));
                int quantity = Integer.parseInt(request.getParameter("txtadminquantity"));
                String description = request.getParameter("txtadmindescription");
                String fad = request.getParameter("fadminimg");
                String fad1 = request.getParameter("fadminimg1");
                String fad2 = request.getParameter("fadminimg2");
                String fad3 = request.getParameter("fadminimg3");

                quanlyspnccDAO dao = new quanlyspnccDAO();
                if (dao.checkNameSPNCC(name) == true) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } else {
                    dao.addCTBrand(brand, category);
                    dao.addCTPN(ncc, quantity, price);
                    int max_ctbrand = dao.findMaxCTBrandId();
                    dao.addSPNCC(name, max_ctbrand, description, origin, quantity, price, fad, ncc, fad1, fad2, fad3);

                    response.setStatus(HttpServletResponse.SC_OK);
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("updateProductToCTPN".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int ncc = Integer.parseInt(request.getParameter("selectnccproduct"));
                int category = Integer.parseInt(request.getParameter("selectcategoryproduct"));
                int brand = Integer.parseInt(request.getParameter("selectbrandproduct"));
                String name = request.getParameter("txtadminname");
                String origin = request.getParameter("txtadminorigin");
                int price = Integer.parseInt(request.getParameter("txtadminprices"));
                int quantity = Integer.parseInt(request.getParameter("txtadminquantity"));
                String description = request.getParameter("txtadmindescription");
                String fad = request.getParameter("fadminimg");
                String fad1 = request.getParameter("fadminimg1");
                String fad2 = request.getParameter("fadminimg2");
                String fad3 = request.getParameter("fadminimg3");

                quanlyspnccDAO dao = new quanlyspnccDAO();
                dao.addCTBrand(brand, category);
                dao.addCTPN(ncc, quantity, price);
                int max_ctbrand = dao.findMaxCTBrandId();
                dao.updateSPNCC(id, name, max_ctbrand, description, origin, quantity, price, fad, ncc, fad1, fad2, fad3);

                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("deleteProduct".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                quanlyspnccDAO dao = new quanlyspnccDAO();
                dao.deleteSPNCC(id);

                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("exportProductToCTPN".equals(action)) {
            String ncc = request.getParameter("selectnccproduct");
            String category = request.getParameter("selectcategoryproduct");
            String brand = request.getParameter("selectbrandproduct");
            String name = request.getParameter("txtadminname");
            String origin = request.getParameter("txtadminorigin");
            String price = request.getParameter("txtadminprices");
            String quantity = request.getParameter("txtadminquantity");
            String description = request.getParameter("txtadmindescription");
            String fad = request.getParameter("fadminimg");
            String fad1 = request.getParameter("fadminimg1");
            String fad2 = request.getParameter("fadminimg2");
            String fad3 = request.getParameter("fadminimg3");

            // Gọi hàm tạo PDF
            createPDF(ncc, category, brand, name, origin, price, quantity, description, fad, fad1, fad2, fad3, response,request);
        } else if ("actionupdateexport".equals(action)) {
            String id = request.getParameter("id");
            String ncc = request.getParameter("selectnccproduct");
            String category = request.getParameter("selectcategoryproduct");
            String brand = request.getParameter("selectbrandproduct");
            String name = request.getParameter("txtadminname");
            String origin = request.getParameter("txtadminorigin");
            String price = request.getParameter("txtadminprices");
            String quantity = request.getParameter("txtadminquantity");
            String description = request.getParameter("txtadmindescription");
            String fad = request.getParameter("fadminimg");
            String fad1 = request.getParameter("fadminimg1");
            String fad2 = request.getParameter("fadminimg2");
            String fad3 = request.getParameter("fadminimg3");

            // Gọi hàm updatePDF với các thông tin đã nhận được từ request
            updatePDF(id, ncc, category, brand, name, origin, price, quantity, description, fad, fad1, fad2, fad3, response,request);
        } else {
            processRequest(request, response);
        }
    }

    private void createPDF(
            String ncc, String category, String brand, String name, String origin, String price,
            String quantity, String description, String fad, String fad1, String fad2, String fad3,
            HttpServletResponse response,HttpServletRequest request
    ) {
        try {
            quanlyspnccDAO dao = new quanlyspnccDAO();

            int brandn = Integer.parseInt(brand);
            String thuonghieu = dao.getBrandName(brandn);

            int categoryn = Integer.parseInt(category);
            String danhmuc = dao.getCategoryName(categoryn);

            int NCCn = Integer.parseInt(ncc);
            String nhacungcap = dao.getCategoryName(NCCn);

            String desktopPath = System.getProperty("user.home") + "\\Desktop";
            String filePath = desktopPath + "\\" + name + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont timesNewRoman = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font contentFont = new Font(timesNewRoman, 12);
            Font titleFont = new Font(timesNewRoman, 14, Font.BOLD);

            Paragraph title = new Paragraph(" PHIẾU NHẬP THÔNG TIN SẢN PHẨM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph nccInfo = new Paragraph("Nhà cung cấp: " + nhacungcap, contentFont);
            Paragraph categoryInfo = new Paragraph("Danh mục: " + danhmuc, contentFont);
            Paragraph brandInfo = new Paragraph("Thương hiệu: " + thuonghieu, contentFont);
            Paragraph nameInfo = new Paragraph("Tên sản phẩm: " + name, contentFont);
            Paragraph originInfo = new Paragraph("Xuất xứ: " + origin, contentFont);
            Paragraph priceInfo = new Paragraph("Giá: " + price, contentFont);
            Paragraph quantityInfo = new Paragraph("Số lượng: " + quantity, contentFont);
            Paragraph descriptionInfo = new Paragraph("Mô tả: " + description, contentFont);
            Paragraph fadInfo = new Paragraph("Hình ảnh: " + fad, contentFont);
            Paragraph fad1Info = new Paragraph("Hình ảnh 1: " + fad1, contentFont);
            Paragraph fad2Info = new Paragraph("Hình ảnh 2: " + fad2, contentFont);
            Paragraph fad3Info = new Paragraph("Hình ảnh 3: " + fad3, contentFont);

            document.add(nccInfo);
            document.add(categoryInfo);
            document.add(brandInfo);
            document.add(nameInfo);
            document.add(originInfo);
            document.add(priceInfo);
            document.add(quantityInfo);
            document.add(descriptionInfo);
            document.add(fadInfo);
            document.add(fad1Info);
            document.add(fad2Info);
            document.add(fad3Info);

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);

            Paragraph dateInfo = new Paragraph("Ngày giờ hiện tại: " + formattedDate, contentFont);
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
              document.add(Chunk.NEWLINE); // Dòng trắng
            paragraph.add(new Chunk("Người tạo phiếu: " + username, contentFont));
            document.add(paragraph);
            document.add(Chunk.NEWLINE); // Dòng trắng
            
            document.close();

            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);

            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=" + ncc + ".pdf");
            response.setContentLength(data.length);
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException ex) {
            Logger.getLogger(quanlyspnccController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(quanlyspnccController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updatePDF(
        String id, String ncc, String category, String brand, String name, String origin,
        String price, String quantity, String description, String fad, String fad1, String fad2, String fad3,
        HttpServletResponse response,HttpServletRequest request
) {
    try {
        quanlyspnccDAO dao = new quanlyspnccDAO();

        int brandn = Integer.parseInt(brand);
        String thuonghieu = dao.getBrandName(brandn);

        int categoryn = Integer.parseInt(category);
        String danhmuc = dao.getCategoryName(categoryn);

        int NCCn = Integer.parseInt(ncc);
        String nhacungcap = dao.getCategoryName(NCCn);

        String desktopPath = System.getProperty("user.home") + "\\Desktop";
        String filePath = desktopPath + "\\" + name + "_updated.pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

         BaseFont timesNewRoman = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font contentFont = new Font(timesNewRoman, 12);
            Font titleFont = new Font(timesNewRoman, 14, Font.BOLD);

            Paragraph title = new Paragraph(" PHIẾU NHẬP CẬP NHẬT THÔNG TIN SẢN PHẨM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph nccInfo = new Paragraph("Nhà cung cấp: " + nhacungcap, contentFont);
            Paragraph categoryInfo = new Paragraph("Danh mục: " + danhmuc, contentFont);
            Paragraph brandInfo = new Paragraph("Thương hiệu: " + thuonghieu, contentFont);
            Paragraph nameInfo = new Paragraph("Tên sản phẩm: " + name, contentFont);
            Paragraph originInfo = new Paragraph("Xuất xứ: " + origin, contentFont);
            Paragraph priceInfo = new Paragraph("Giá: " + price, contentFont);
            Paragraph quantityInfo = new Paragraph("Số lượng: " + quantity, contentFont);
            Paragraph descriptionInfo = new Paragraph("Mô tả: " + description, contentFont);
            Paragraph fadInfo = new Paragraph("Hình ảnh: " + fad, contentFont);
            Paragraph fad1Info = new Paragraph("Hình ảnh 1: " + fad1, contentFont);
            Paragraph fad2Info = new Paragraph("Hình ảnh 2: " + fad2, contentFont);
            Paragraph fad3Info = new Paragraph("Hình ảnh 3: " + fad3, contentFont);

            document.add(nccInfo);
            document.add(categoryInfo);
            document.add(brandInfo);
            document.add(nameInfo);
            document.add(originInfo);
            document.add(priceInfo);
            document.add(quantityInfo);
            document.add(descriptionInfo);
            document.add(fadInfo);
            document.add(fad1Info);
            document.add(fad2Info);
            document.add(fad3Info);

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);

            Paragraph dateInfo = new Paragraph("Ngày giờ hiện tại: " + formattedDate, contentFont);
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
            
        document.close();

        // Mở file PDF và gửi nó dưới dạng phản hồi (response)
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fileInputStream.read(data);

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + ncc + ".pdf");
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (DocumentException ex) {
        Logger.getLogger(quanlyspnccController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(quanlyspnccController.class.getName()).log(Level.SEVERE, null, ex);
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
