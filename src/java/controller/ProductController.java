/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ProductController", urlPatterns = {"/index"})
public class ProductController extends HttpServlet {

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
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> listCategory = cateDAO.getAllCategory();
            
            String page = request.getParameter("page");
            if(page == null) {
                    page="1";
            }
            int pageNumber = Integer.parseInt(page);
            int pageSize = 6;// Number of products per page
            
            String name = request.getParameter("name"); // Search by name
            
            String category = request.getParameter("category"); // Search by category
            
            List<String> selectedBrands = new ArrayList<>();
            
            double minPrice = 0.0;

            double maxPrice = Double.MAX_VALUE; // Set a high default value for max price
            
            List<Integer> selectedRatings = new ArrayList<>();
            
            ProductDAO proDAO = new ProductDAO();
            List<Product> listProduct = proDAO.getProductsByPage(pageNumber, pageSize, name, category, selectedBrands, minPrice, maxPrice, selectedRatings);

            int allProduct = proDAO.getTotalProductCount(name, category, selectedBrands, minPrice, maxPrice, selectedRatings); 
            int endPage = (int) Math.ceil((double) allProduct / pageSize);
           
            request.setAttribute("tag", pageNumber);
            request.setAttribute("endPage", endPage);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("listCategory", listCategory);
            request.setAttribute("listProduct", listProduct);
            request.setAttribute("allProduct", allProduct);
            request.getRequestDispatcher("index.jsp").forward(request, response);
           
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
