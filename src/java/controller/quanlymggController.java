/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.quanlyhoadonDAO;
import dao.quanlymggDAO;
import dao.quanlyquyenDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MggToProductMODEL;
import model.quanlyhoadonMODEL;
import model.quanlymggMODEL;
import model.quanlyquyenMODEL;
import model.quanlyuserMODEL;
import model.spcMGGMODEL;

/**
 *
 * @author DELL
 */
public class quanlymggController extends HttpServlet {

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
            quanlymggDAO mggDAO = new quanlymggDAO();
                int currentPage = 1;  // Default to the first page
             int recordsPerPage = 5;  // Number of records per page

             // Get the current page from the request
             String pageStr = request.getParameter("page");
             if (pageStr != null) {
                 currentPage = Integer.parseInt(pageStr);
             }

             // Get the search keyword from the request
              String keyword = request.getParameter("searchDiscount");

             List<quanlymggMODEL> mggList;

             if (keyword != null && !keyword.isEmpty()) {
                 // If a search keyword is provided, perform a search
                 mggList = mggDAO.searchDiscounts(keyword, currentPage, recordsPerPage);
             } else {
                 // Otherwise, display the default user list with pagination
                 mggList = mggDAO.getData(currentPage, recordsPerPage);
             }

             int totalRecords = keyword != null && !keyword.isEmpty()
                     ? mggDAO.getTotalSearchDiscountCount(keyword)
                     : mggDAO.getTotalDiscountCount();
             int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

             // Set attributes for rendering in the JSP
             request.setAttribute("mggList", mggList);
             request.setAttribute("totalPages", totalPages);
             request.setAttribute("currentPage", currentPage);

             int maxIdCTMGG;
            try {
                maxIdCTMGG = mggDAO.getIDCTMGG();
            } catch (Exception e) {
                e.printStackTrace();
                maxIdCTMGG = 0; // Handle the exception as needed
            }
            request.setAttribute("maxIdCTMGG", maxIdCTMGG);
            
                     int maxId;
            try {
                maxId = mggDAO.getMaxId();
            } catch (Exception e) {
                e.printStackTrace();
                maxId = 0; // Handle the exception as needed
            }
            request.setAttribute("maxId", maxId);        
            
            List<spcMGGMODEL> spcList = mggDAO.getCBBSPC();
             request.setAttribute("spcList", spcList);
             
             List<quanlymggMODEL> mggcbbList = mggDAO.getDataALL();
             request.setAttribute("mggcbbList", mggcbbList);

            request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
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
        if ("add".equals(action)) {
    int id = Integer.parseInt(request.getParameter("txtmggID"));
    String discount = request.getParameter("discountPercent");
    String code = request.getParameter("txtDiscountCode");

    if (discount.isEmpty() || code.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin mã giảm giá.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
    
    quanlymggDAO mggDAO = new quanlymggDAO();

                try {
                    // Kiểm tra xem tên đã tồn tại hay chưa
                    if (mggDAO.isCodeExist(code)) {
                        String errorMessage = "Code đã tồn tại";
                        request.setAttribute("errorMessage", errorMessage);
                    } else {
                        // Tạo đối tượng quanlyquyenMODEL với thông tin quyền
                        quanlymggMODEL mgg = new quanlymggMODEL();
                        mgg.setId(id);
                        mgg.setDiscount(discount);
                        mgg.setCode(code);
                        
                        boolean mggAdded;
                        try {
                            mggAdded = mggDAO.addMGG(mgg);
                            
                            if (mggAdded) {
                                // Permission added successfully, set a success message as a request attribute
                                String successMessage = "Discount added successfully";
                                request.setAttribute("successMessage", successMessage);
                            } else {
                                // Permission not added, set an error message as a request attribute
                                String errorMessage = "Failed to add discount";
                                request.setAttribute("errorMessage", errorMessage);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }           } catch (Exception ex) {
                    Logger.getLogger(quanlyQuyenController.class.getName()).log(Level.SEVERE, null, ex);
                }

    request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
        }else if ("update".equals(action)) {
   int id = Integer.parseInt(request.getParameter("txtmggUpdate"));
    String discount = request.getParameter("updateSelectPercent");
    String code = request.getParameter("txtDiscountCodeUpdate");

    if (discount.isEmpty() || code.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin mã giảm giá.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
    
    quanlymggDAO mggDAO = new quanlymggDAO();
       try {
                    // Kiểm tra xem tên đã tồn tại hay chưa
                    if (mggDAO.isCodeExist(code)) {
                        String errorMessage = "Code đã tồn tại";
                        request.setAttribute("errorMessage", errorMessage);
                    } else {
                        // Tạo đối tượng quanlyquyenMODEL với thông tin quyền
                        quanlymggMODEL mgg = new quanlymggMODEL();
                        mgg.setId(id);
                        mgg.setDiscount(discount);
                        mgg.setCode(code);
                        
                        boolean mggUpdated;
                        try {
                            mggUpdated = mggDAO.updateMGG(mgg);
                            
                            if (mggUpdated) {
                                // Permission added successfully, set a success message as a request attribute
                                String successMessage = "Discount updated successfully";
                                request.setAttribute("successMessage", successMessage);
                            } else {
                                // Permission not added, set an error message as a request attribute
                                String errorMessage = "Failed to updated discount";
                                request.setAttribute("errorMessage", errorMessage);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }           } catch (Exception ex) {
                    Logger.getLogger(quanlyQuyenController.class.getName()).log(Level.SEVERE, null, ex);
                }

    request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
}else if ("addDiscountToProduct".equals(action)) {
    int id = Integer.parseInt(request.getParameter("txtPDiscountid"));
    String product = request.getParameter("selectProduct_discount");
    String code = request.getParameter("selectDiscount_product");

    if (product.isEmpty() || code.isEmpty()) {
                String errorMessage = "Vui lòng điền đầy đủ thông tin.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
                return; // Ngừng xử lý nếu có trường rỗng
            }
    quanlymggDAO mggDAO = new quanlymggDAO();

                try {
                    // Kiểm tra xem tên đã tồn tại hay chưa
                    if (mggDAO.CheckProductHasMGG(product)) {
                        String errorMessage = "Sản phẩm này đã có mã giảm giá";
                        request.setAttribute("errorMessage", errorMessage);
                    } else {
                        // Tạo đối tượng quanlyquyenMODEL với thông tin quyền
                        MggToProductMODEL mggToproduct = new MggToProductMODEL();
                        mggToproduct.setId(id);
                        mggToproduct.setId_product(product);
                        mggToproduct.setId_mgg(code);
                        
                        boolean mggProductAdded;
                        try {
                            mggProductAdded = mggDAO.addMGGTOPRODUCT(mggToproduct);
                            
                            if (mggProductAdded) {
                                // Permission added successfully, set a success message as a request attribute
                                String successMessage = "Product added Discount successfully";
                                request.setAttribute("successMessage", successMessage);
                            } else {
                                // Permission not added, set an error message as a request attribute
                                String errorMessage = "Failed to add discount to product";
                                request.setAttribute("errorMessage", errorMessage);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(quanlyuserController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }           } catch (Exception ex) {
                    Logger.getLogger(quanlyQuyenController.class.getName()).log(Level.SEVERE, null, ex);
                }

    request.getRequestDispatcher("quanlymagiamgia.jsp").forward(request, response);
        }else if ("delete".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        quanlymggDAO mggDAO = new quanlymggDAO();
        boolean mggDeleted;
        try {
            mggDeleted = mggDAO.deleteMGG(id);

            if (mggDeleted) {
                // User deleted successfully, set a success message as a request attribute
                String successMessage = "Discount deleted successfully";
                request.setAttribute("successMessage", successMessage);
            } else {
                // User not deleted, set an error message as a request attribute
                String errorMessage = "Failed to delete Discount";
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
