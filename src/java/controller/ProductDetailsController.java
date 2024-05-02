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
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.HashMap;
import model.Category;
import model.Product;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ProductDetailsController", urlPatterns = { "/details" })
public class ProductDetailsController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> listCategory = cateDAO.getAllCategory();

            String productId = request.getParameter("productId");

            if (productId != null) {
                // Call the ProductService to fetch the product details by productId
                ProductDAO proDAO = new ProductDAO();
                Product product = proDAO.getProductById(productId);
                List<Product> listRelatedProducts = proDAO.getRelatedProducts(productId);
                List<Product> listPhukien = proDAO.getPhukien();

                // If the product is found, set it as an attribute in the request
                request.setAttribute("product", product);
                request.setAttribute("listRelatedProducts", listRelatedProducts);
                request.setAttribute("listPhukien", listPhukien);
                request.setAttribute("listCategory", listCategory);

                if (product != null) {
                    HttpSession session = request.getSession();
                    String action = request.getParameter("action");
                    if (action != null && action.equals("addtocart")) {
                        // Retrieve the user's shopping cart from the session
                        Map<String, Integer> cartItems = (Map<String, Integer>) session.getAttribute("cart");

                        if (cartItems == null) {
                            // If the cart doesn't exist in the session, create a new one
                            cartItems = new HashMap<>();
                        }

                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        if (cartItems.containsKey(productId)) {
                            // Product is already in the cart, update the quantity
                            cartItems.put(productId, cartItems.get(productId) + quantity);
                        } else {
                            // Add the productId with quantity to the cart
                            cartItems.put(productId, quantity);
                        }

                        // Store the updated cart in the session
                        session.setAttribute("cart", cartItems);
                        String successMessage = "Thêm vào giỏ hàng thành công";
                        request.setAttribute("successMessage", successMessage);  
                    }
                }
                   
                // Forward the request to the product details JSP view
                request.getRequestDispatcher("product_details.jsp").forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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