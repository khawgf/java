/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Brand;
import model.Category;
import model.Product;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

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
            CategoryDAO cateDAO = new CategoryDAO();
            List<Category> listCategory = cateDAO.getAllCategory();
            BrandDAO brandDAO = new BrandDAO();
            List<Brand> listBrand = brandDAO.getAllBrand();

            
            String page = request.getParameter("page");
            if(page == null) {
                    page="1";
            }
            int pageNumber = Integer.parseInt(page);
            int pageSize = 6;// Number of products per page
            
            String name = request.getParameter("name"); // Search by name
            
            String category = request.getParameter("category"); // Search by category
            
            String[] brandFilterParam = request.getParameterValues("brand");
            List<String> selectedBrands = new ArrayList<>();
            if (brandFilterParam != null) {
                for (String brandFilter : brandFilterParam) {
                    selectedBrands.add(brandFilter);
                }
            }
            
            //nhấn cào category nào sẽ hiện brand đó
            List<Brand> brandsByCategory = brandDAO.getBrandsByCategory(category);
            
            // Search by price
            double minPrice = 0.0;
            String minPriceStr = request.getParameter("minPrice");
            if (minPriceStr != null && !minPriceStr.isEmpty()) {
                try {
                    minPrice = Double.parseDouble(minPriceStr);
                } catch (NumberFormatException e) {
                }
            }

            double maxPrice = Double.MAX_VALUE; 
            String maxPriceStr = request.getParameter("maxPrice");
            if (maxPriceStr != null && !maxPriceStr.isEmpty()) {
                try {
                    maxPrice = Double.parseDouble(maxPriceStr);
                } catch (NumberFormatException e) {
                }
            } 
            
            
            
            String[] ratingFilterParam = request.getParameterValues("ratingFilter");
            List<Integer> selectedRatings = new ArrayList<>();
            if (ratingFilterParam != null) {
                for (String rating : ratingFilterParam) {
                    selectedRatings.add(Integer.parseInt(rating));
                }
            }
            
            ProductDAO proDAO = new ProductDAO();
            List<Product> listProduct = proDAO.getProductsByPage(pageNumber, pageSize, name, category, selectedBrands, minPrice, maxPrice, selectedRatings);
           
            int allProduct = proDAO.getTotalProductCount(name, category, selectedBrands, minPrice, maxPrice, selectedRatings); //.countAllProduct();
            int endPage = (int) Math.ceil((double) allProduct / pageSize);

            
            out.println("<div class=\"webgridview-contenttop\" >\n" +
"                <span class=\"webgridview-text TextBase\">\n" +
"                  <span>Results: <span id=\"resultCount\">"+allProduct+"</span> items</span>\n" +
"                </span>\n" +
"                <div class=\"webgridview-btngroup\">\n" +
"                    <button id=\"webgridview-buttonbtngroup\"><i class=\"fas fa-bars fa-lg\"></i></button>\n" +
"                  <button id=\"webgridview-buttonbtngroup1\" ><i class=\"fas fa-grip-horizontal fa-lg\"></i></button>\n" +
"                </div>\n" +
"                <span class=\"webgridview-text002 TextBase\">\n" +
"                    <span><a href=\"index\">Clear all filter</a></span>\n" +
"                </span>\n" +
"            </div>");
            
            out.println("<div class=\"webgridview-layoutproductcard horizontal\" id=\"content\">");
            
            for (Product o : listProduct) {
                
                double discountSP = o.getDiscountSP();
                double priceSP = o.getPriceSP();

                double OriginalPrice;

                if (discountSP != 0) {
                    OriginalPrice = priceSP / ((100 - discountSP) / 100);
                } else {
                    OriginalPrice = priceSP;
                }
                String formattedPrice = String.format("%,.0f đ", OriginalPrice).replace(",", "."); // Định dạng giá trị số tiền
                String PriceSP = String.format("%,.0f đ", o.getPriceSP()).replace(",", ".");
                
                out.println("<div class=\"col-md-4 mb-4\" >\n" +
"                    <div class=\"card\">\n" +
"                        <div class=\"card-body\" >\n" +
"                            <div class=\"card-img\">\n" +
"                                <img\n" +
"                                  src=\""+o.getImageSP()+"\"\n" +
"                                  alt=\"image33I239\"\n" +
"                                  class=\"webgridview-image33\"\n" +
"                                />\n" +
"                            </div>\n" +
"                            <div class=\"webgridview-content\">\n" +
"                                <div class=\"webgridview-group1000\">\n" +
"                                    <span class=\"webgridview-text022 TitleH5\">\n" +
"                                         "+PriceSP+"\n" +
"                                      \n" +
"                                    </span>\n"); 
                if(o.getDiscountSP()!=0){
                    out.println("<span class=\"webgridview-text024\"><span>\n" +
"                                            "+formattedPrice+"\n" +
"                                    </span></span>\n");
                }
                     out.println("</div>\n" +
"                                <div class=\"webgridview-rating\">\n");          
                if (o.getRatingSP() >= 9) {
                    out.println("<img\n" +
"                                                    src=\"image/index_img/miscrating9092-b6.svg\"\n" +
"                                                    alt=\"Miscrating9092\"\n" +
"                                                    class=\"webgridview-miscrating\"\n" +
"                                                  />\n" +
"                                            <span class=\"webgridview-text018 TextBase\"><span>"+o.getRatingSP()+"</span></span>\n");
                    
                } else if (o.getRatingSP() >= 7.5) {
                    out.println("<img\n" +
"                                                src=\"image/index_img/miscrating9092-f8m.svg\"\n" +
"                                                alt=\"Miscrating9092\"\n" +
"                                                class=\"webgridview-miscrating\"\n" +
"                                              />\n" +
"                                            <span class=\"webgridview-text018 TextBase\"><span>"+o.getRatingSP()+"</span></span>\n");
                }
                else if (o.getRatingSP() >= 5.9) {
                    out.println("<img\n" +
"                                                src=\"image/index_img/miscrating9092-fcp.svg\"\n" +
"                                                alt=\"Miscrating9092\"\n" +
"                                                class=\"webgridview-miscrating\"\n" +
"                                              />\n" +
"                                            <span class=\"webgridview-text018 TextBase\"><span>"+o.getRatingSP()+"</span></span>\n");
                }
                else if (o.getRatingSP() >= 3.5) {
                    out.println("<img\n" +
"                                                src=\"image/index_img/miscrating9092-ielc.svg\"\n" +
"                                                alt=\"Miscrating9092\"\n" +
"                                                class=\"webgridview-miscrating\"\n" +
"                                              />\n" +
"                                            <span class=\"webgridview-text018 TextBase\"><span>"+o.getRatingSP()+"</span></span>\n");
                }
                
                
                out.println("</div>\n");
                out.println("<button class=\"webgridview-buttonbtnbasic\">\n" +
"                                    <i class=\"far fa-heart fa-lg\" ></i>\n" +
"                                </button>\n" +
"                                <span class=\"webgridview-titleproduct\">\n" +
"                                    "+o.getNameSP()+"\n" +
"                                </span>\n" +
"                                <a href=\""+ request.getContextPath() + "/details?productId="+o.getIdSP()+"\"><button class=\"webgridview-buttonbtnbasic01\">\n" +
"                                    <i class=\"fa-solid fa-chevron-right\"></i>\n" +
"                                    <span class=\"text-view-details\">View details</span>\n" +
"                                    </button></a>\n" +
"                            </div>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                </div>");
                
            }
                
            out.println("<input type=\"hidden\" id=\"pageNumber\" value=\"" +pageNumber+ "\">");
            out.println("<input type=\"hidden\" id=\"endPage\" value=\"" +endPage+ "\">");
            out.println("<div class=\"col-12 col-md-4 text-center\">");
            out.println("<div aria-label=\"Page navigation example\">");
            out.println("<div class=\"webgridview-Page\">");
            out.println("<div class=\"webgridview-frame273\">");
            
            if (pageNumber > 1) {
                out.println("<div class=\"page-item\" id=\"previous-page\">\n" +
"                                <div class=\"page-link\">\n" +
"                                    <i class=\"fas fa-chevron-left\"></i>\n" +
"                                </div>\n" +
"                            </div>");
            }

            if (endPage > 1) {
                int minValue = (pageNumber - 1 > 0) ? (pageNumber - 1) : 1;
                int maxValue = (minValue + 2 > endPage) ? endPage : (minValue + 2);

                for (int i = minValue; i <= maxValue; i++) {
                    if(i == pageNumber){
                        out.println("<div class=\"page-item active current-page\" data-page=\""+i+"\">\n" +
"                                            <a class=\"page-link\">"+i+"</a>\n" +
"                                        </div>");
                   }else{
                        out.println("<div class=\"page-item current-page\" data-page=\""+i+"\">\n" +
"                                            <div class=\"page-link\">"+i+"</div>\n" +
"                                        </div>");
                    }
                }
            }

            if (pageNumber < endPage) {
                out.println("<div class=\"page-item\" id=\"next-page\">\n" +
"                                <div class=\"page-link\">\n" +
"                                    <i class=\"fas fa-chevron-right\"></i>\n" +
"                                </div>\n" +
"                            </div>");

            }

            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>"); 
            out.println("</div>");

           
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
