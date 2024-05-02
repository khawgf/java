/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BrandDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Brand;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "BrandsByCategoryController", urlPatterns = {"/BrandsByCategoryController"})
public class BrandsByCategoryController extends HttpServlet {

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
            
            String category = request.getParameter("category"); // Search by category

            BrandDAO brandDAO = new BrandDAO();
            List<Brand> brandsByCategory = brandDAO.getBrandsByCategory(category);
                    
            for (Brand b : brandsByCategory) {
        	out.println("<div class=\"webgridview-formselectioncheckboxoff04\">\n" +
"\n" +
"                                     <input type=\"checkbox\" \n" +
"                                            class=\"webgridview-checkbox04\" \n" +
"                                            name=\"brandCheckbox\" \n" +
"                                            id=\""+b.getNameBrand()+"\"\n" +
"                                            value=\""+b.getNameBrand()+"\"\n" +
                                             "onchange=\"handleBrandSelection(this)\""+
"                                            />\n" +
"                                     <label>"+b.getNameBrand()+"</label>\n" +
"\n" +
"                                 </div>");
            }
            out.println("<span class=\"webgridview-Category\" style=\"color: blue; \">See all</span>");
            
 
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
