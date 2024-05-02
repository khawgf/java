/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.InvoiceDetail;
import dao.PaymentDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Product;

@WebServlet(name = "PaymentController", urlPatterns = {"/Payment"})
public class PaymentController extends HttpServlet {

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
            out.println("<title>Servlet PaymentController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentController at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        String productIDsParam = request.getParameter("productIDs");
        String quantitiesParam = request.getParameter("quantities");

        String[] productIDStrings = productIDsParam.split(",");
        String[] quantityStrings = quantitiesParam.split(",");

        List<Integer> productIDs = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (int i = 0; i < productIDStrings.length; i++) {
            try {
                int productID = Integer.parseInt(productIDStrings[i]);
                int quantity = Integer.parseInt(quantityStrings[i]);
                productIDs.add(productID);
                quantities.add(quantity);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
//        System.out.print(productIDs);
//        System.out.print(quantities);

        PaymentDAO dao = new PaymentDAO();
        String result = "";

        try {
            result = dao.checkQuantity(productIDs, quantities);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        if ("Thanh toán thành công".equals(result)) {
            //delete cart from session
            HttpSession session = request.getSession(false);
            if (session != null) {
                Map<String, Integer> cartItems = (Map<String, Integer>) session.getAttribute("cart");
                if (cartItems != null) {
                    cartItems.clear();
                }
            }

            Cookie[] cookies = request.getCookies();
            String phone = "";
            String id = "";

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("PhoneNumber")) {
                        phone = cookie.getValue();
                    } else if (cookie.getName().equals("userid")) {
                        id = cookie.getValue();
                    }
                }
            }

            String[] addressvalue = request.getParameterValues("address");
            String address = String.join("", addressvalue);

            dao.insertInvoice(phone, address);
            int idhoadon = dao.getIdInvoiceByPhoneNumber(phone);

            List<InvoiceDetail> details = new ArrayList<>();

            for (int i = 0; i < productIDStrings.length; i++) {
                try {
                    int productIDInvoice = Integer.parseInt(productIDStrings[i]);
                    int quantityInvoice = Integer.parseInt(quantityStrings[i]);
                    System.out.print(productIDInvoice);
                    System.out.print(quantityInvoice);
                    String productId = Integer.toString(productIDInvoice);

                    // Call the ProductService to fetch the product details by productId
                    ProductDAO proDAO = new ProductDAO();
                    Product product = proDAO.getProductById(productId);

                    details.add(new InvoiceDetail(productIDInvoice, Integer.parseInt(id), quantityInvoice, (int) product.getPriceSP())); // Sản phẩm 1

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            dao.insertInvoiceDetail(idhoadon, details);

            out.println("alert('Thanh toán thành công');");
            out.println("window.location.href = 'Cart';");

        } else {
            out.println("alert('Sản phẩm không đủ số lượng');");
            out.println("window.location.href = 'Cart';");
        }
        out.println("</script>");
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
