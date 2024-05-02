package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.InvoiceDAO;
import dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.servlet.http.Cookie;
import model.Invoice;

public class PrintInvoiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int invoiceId = Integer.parseInt(request.getParameter("invoiceID"));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=invoice.pdf");

        // Create a Document instance
        Document document = new Document();

        try {
            // Create PdfWriter instance to write to response's output stream
            PdfWriter.getInstance(document, response.getOutputStream());

            // Open the document for writing
            document.open();

            Cookie[] cookies = request.getCookies();
            String phone = "";
            String name = "";

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("PhoneNumber")) {
                        phone = cookie.getValue();
                    } else if (cookie.getName().equals("username")) {
                        name = cookie.getValue();
                    }
                }
            }

            Invoice invoice = dao.InvoiceDAO.getInvoiceById(invoiceId);

            String address = invoice.getAddress();

            // Add Invoice Information
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph header = new Paragraph();
            header.setFont(font);
            header.add("Invoice ID: " + invoiceId + "\n");
            document.add(header);
            document.add(new Paragraph("\n"));

            // Add User Information
            document.add(new Paragraph("User Information:"));
            document.add(new Paragraph("Name: " + name));
            document.add(new Paragraph("Phone: " + phone));
            document.add(new Paragraph("Address: " + address));
            document.add(new Paragraph("\n"));

            // Add Table for Product Details
            PdfPTable table = new PdfPTable(new float[]{1, 4, 2, 2}); // Adjust column widths as needed
            table.setWidthPercentage(100);
            table.addCell("No.");
            table.addCell("Product");
            table.addCell("Quantity");
            table.addCell("Price");

            int[] productIds = InvoiceDAO.getProductIDsByInvoice(invoiceId);
            DecimalFormat priceFormat = new DecimalFormat("#,##0.00");

            for (int i = 0; i < productIds.length; i++) {
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.getProductById(Integer.toString(productIds[i]));

                int quantity = InvoiceDAO.getProductQuantityByInvoice(productIds[i], invoiceId);
                BigDecimal productTotal = BigDecimal.valueOf(product.getPriceSP()).multiply(BigDecimal.valueOf(quantity));

                table.addCell(Integer.toString(i + 1));
                table.addCell(product.getNameSP());
                table.addCell(Integer.toString(quantity));
                table.addCell(priceFormat.format(productTotal));
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Calculate and Add Total Amount
            int[] productIdsForTotal = InvoiceDAO.getProductIDsByInvoice(invoiceId);
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (int productId : productIdsForTotal) {
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.getProductById(Integer.toString(productId));
                int quantity = InvoiceDAO.getProductQuantityByInvoice(productId, invoiceId);

                BigDecimal productTotal = BigDecimal.valueOf(product.getPriceSP()).multiply(BigDecimal.valueOf(quantity));
                totalAmount = totalAmount.add(productTotal);
            }

            String formattedTotal = priceFormat.format(totalAmount);
            document.add(new Paragraph("Total Amount: " + formattedTotal));

        } catch (Exception e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
            // You may want to return a user-friendly error message here
        } finally {
            // Close the document to finalize the PDF creation
            document.close();
        }
    }
}
