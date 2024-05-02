/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.quanlyspnccDAO;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author hp
 */
@MultipartConfig
@WebServlet(name = "UploadExcelServlet", urlPatterns = {"/UploadExcelServlet"})
public class UploadExcelServlet extends HttpServlet {

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
            out.println("<title>Servlet UploadExcelServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UploadExcelServlet at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    quanlyspnccDAO dao = new quanlyspnccDAO();
    Set<String> processedRows = new HashSet<>(); // Sử dụng Set để theo dõi dòng đã được xử lý

    InputStream fileContent = null;
    Workbook workbook = null;

    try {
        Part filePart = request.getPart("excelFile");
        fileContent = filePart.getInputStream();

        workbook = new XSSFWorkbook(fileContent);
        Sheet sheet = workbook.getSheetAt(0); // Đọc từ sheet đầu tiên

        for (Row row : sheet) {
            String rowData = "";
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    rowData += cell.toString() + "|"; // Chuyển cell thành chuỗi
                } else {
                    rowData += "|";
                }
            }

            if (processedRows.contains(rowData)) {
                continue; // Bỏ qua nếu dòng đã được xử lý
            }

            processedRows.add(rowData);

            String name = row.getCell(0).getStringCellValue();
            int idCTBrand = (int) row.getCell(1).getNumericCellValue();
            String description = row.getCell(2).getStringCellValue();
            String origin = row.getCell(3).getStringCellValue();
            int quantity = (int) row.getCell(4).getNumericCellValue();
            double price = row.getCell(5).getNumericCellValue();
            String image = row.getCell(6).getStringCellValue();
            int idProducer = (int) row.getCell(7).getNumericCellValue();
            String image1 = row.getCell(8).getStringCellValue();
            String image2 = row.getCell(9).getStringCellValue();
            String image3 = row.getCell(10).getStringCellValue();
             response.getWriter().println(name);

            // Gọi phương thức addSPNCC từ controller
            dao.addSPNCC(name, idCTBrand, description, origin, quantity, price, image, idProducer, image1, image2, image3);
        }

        response.getWriter().println("Upload successfully");
    } catch (Exception e) {
        response.getWriter().println("error: " + e.getMessage());
    } finally {
        if (workbook != null) {
           
        }
        if (fileContent != null) {
            fileContent.close();
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
