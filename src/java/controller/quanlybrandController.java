/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.quanlybrandDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.brandMODEL;

/**
 *
 * @author hp
 */
@WebServlet(name = "quanlybrandController", urlPatterns = {"/quanlybrandController"})
public class quanlybrandController extends HttpServlet {

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
            quanlybrandDAO spcDAO = new quanlybrandDAO();
           int currentPage = 1; 
           int recordsPerPage = 5; 
           String pageStr = request.getParameter("page");
           if (pageStr != null) {
               currentPage = Integer.parseInt(pageStr);
           }
           String keyword = request.getParameter("searchUser");
            List<brandMODEL> spcList;
           if (keyword != null && !keyword.isEmpty()) {
               spcList = spcDAO.searchOrders(keyword, currentPage, recordsPerPage);
           } else {
               // Otherwise, display the default user list with pagination
               spcList = spcDAO.getData(currentPage, recordsPerPage);
           }
           int totalRecords = keyword != null && !keyword.isEmpty()
                   ? spcDAO.getTotalSearchOrderCount(keyword)
                   : spcDAO.getTotalBrandCount();
           int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
           request.setAttribute("spcList", spcList);
           request.setAttribute("totalPages", totalPages);
           request.setAttribute("currentPage", currentPage);
           request.getRequestDispatcher("quanlybrand.jsp").forward(request, response);
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

        if ("addNCC".equals(action)) {
            try {
                
                String name = request.getParameter("txtadminname");
                
                

                quanlybrandDAO dao = new quanlybrandDAO();
                if (dao.checkNamebrand(name) == true) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                } else {
                    dao.addbrand(name);
                    response.setStatus(HttpServletResponse.SC_OK);
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("updateNCC".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("txtadminname");
                
                

                 quanlybrandDAO dao = new quanlybrandDAO();
                dao.updatebrand(id, name);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if ("deleteProduct".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                quanlybrandDAO dao = new quanlybrandDAO();
                dao.deletebrand(id);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            processRequest(request, response);
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
