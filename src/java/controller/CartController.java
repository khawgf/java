package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Product;
import dao.ProductDAO;

@WebServlet(name = "CartController", urlPatterns = { "/Cart" })
public class CartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Get the current session or create a new one if it doesn't exist
            HttpSession session = request.getSession(true);

            if (session != null) {
                // Retrieve the product IDs and quantities stored in the session
                Map<String, Integer> cartItems = (Map<String, Integer>) session.getAttribute("cart");

                if (cartItems != null) {
                    // Create a map to store product models with quantities
                    Map<Product, Integer> productsInCart = new HashMap<>();

                    // Retrieve product details for each product ID in the cart
                    ProductDAO productDAO = new ProductDAO();

                    for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
                        String productId = entry.getKey();
                        Integer quantity = entry.getValue();

                        Product product = productDAO.getProductById(productId);

                        if (product != null) {
                            productsInCart.put(product, quantity);
                        }
                    }

                    // Set the map of product models with quantities as an attribute in the request
                    request.setAttribute("productsInCart", productsInCart);

                    // Forward the request to Cart.jsp to display the cart contents
                    request.getRequestDispatcher("Cart.jsp").forward(request, response);
                }
            }
        } catch (ServletException | IOException e) {
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
        String removeProductId = request.getParameter("removeProduct");

        // Check if the removeProduct parameter is not null
        if (removeProductId != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Retrieve the cartItems map from the session
                Map<String, Integer> cartItems = (Map<String, Integer>) session.getAttribute("cart");

                if (cartItems != null) {
                    // Remove the product ID from the map
                    cartItems.remove(removeProductId);
                    // Update the session's cart
                    session.setAttribute("cart", cartItems);
                    // response.sendRedirect(request.getRequestURI());
                }
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Cart Controller";
    }
}
