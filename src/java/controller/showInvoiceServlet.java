/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import model.Invoice;

/**
 *
 * @author rumi
 */
@WebServlet(name = "showInvoiceServlet", urlPatterns = {"/showInvoice"})
public class showInvoiceServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Cookie[] cookies = request.getCookies();
            String phone = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("PhoneNumber")) {
                        phone = cookie.getValue();
                    }
                }
            }
            int[] invoiceIds = dao.InvoiceDAO.getIdsInvoiceBByPhoneNumber(phone);

            // Create a list to store Invoice objects
            List<Invoice> invoices = new ArrayList<>();

            for (int id : invoiceIds) {
                Invoice invoice = dao.InvoiceDAO.getInvoiceById(id);
                if (invoice != null) {
                    invoices.add(invoice);
                    System.out.print(id);
                }
            }
            request.setAttribute("invoices", invoices);

            // Forward the request to showInvoice.jsp
            request.getRequestDispatcher("showInvoice.jsp").forward(request, response);

            
        } catch (Exception e) {
            // Handle the exception gracefully, e.g., log it or redirect to an error page.
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Show InvoiceServlet";
    }
}
