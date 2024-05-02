/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author ASUS
 */
public class ProductDAO {
    Connection conn = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public ProductDAO(){
        
    }
    
    
    public List<Product> getRelatedProducts(String idSP) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT sanphamchinh.*, spncc.*, ctbrand.*, category.name AS category_name, brand.name AS brand_name " +
                       "FROM sanphamchinh " +
                       "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                       "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                       "INNER JOIN category ON ctbrand.id_category = category.id " +
                       "INNER JOIN brand ON ctbrand.id_brand = brand.id " +
                       "WHERE ctbrand.id = (SELECT id_ctbrand FROM spncc WHERE id = ?) AND sanphamchinh.id != ?";

        try {
            conn = new DBUtil().getConnection(); // Mở kết nối đến cơ sở dữ liệu
            stm = conn.prepareStatement(query);
            stm.setString(1, idSP); // Đặt id sản phẩm làm tham số
            stm.setString(2, idSP); // Đặt id sản phẩm làm tham số
            rs = stm.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setIdSP(rs.getString("id"));
                product.setPriceSP(rs.getDouble("price"));
                product.setQuantitySP(rs.getInt("quantity"));
                product.setId_spnccSP(rs.getString("id_spncc"));
                product.setNameSP(rs.getString("name"));
                product.setImageSP(rs.getString("image"));
                product.setImageSP1(rs.getString("image1"));
                product.setImageSP2(rs.getString("image2"));
                product.setImageSP3(rs.getString("image3"));
                product.setCategorySP(rs.getString("category_name"));
                product.setBrandSP(rs.getString("brand_name"));
                product.setDescriptionSP(rs.getString("description"));
                product.setRatingSP(rs.getDouble("rating"));
                product.setDiscountSP(rs.getInt("discount"));
                product.setId_producer(rs.getInt("id_producer"));
                list.add(product);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
        }
        return list;
    }
    
    public List<Product> getPhukien() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT sanphamchinh.*, spncc.*, ctbrand.*, category.name AS category_name, brand.name AS brand_name " +
                       "FROM sanphamchinh " +
                       "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                       "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                       "INNER JOIN category ON ctbrand.id_category = category.id " +
                       "INNER JOIN brand ON ctbrand.id_brand = brand.id " +
                       "AND category.name = 'Phụ kiện'";

        try {
            conn = new DBUtil().getConnection(); // Mở kết nối đến cơ sở dữ liệu
            stm = conn.prepareStatement(query);
            rs = stm.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setIdSP(rs.getString("id"));
                product.setPriceSP(rs.getDouble("price"));
                product.setQuantitySP(rs.getInt("quantity"));
                product.setId_spnccSP(rs.getString("id_spncc"));
                product.setNameSP(rs.getString("name"));
                product.setImageSP(rs.getString("image"));
                product.setImageSP1(rs.getString("image1"));
                product.setImageSP2(rs.getString("image2"));
                product.setImageSP3(rs.getString("image3"));
                product.setCategorySP(rs.getString("category_name"));
                product.setBrandSP(rs.getString("brand_name"));
                product.setDescriptionSP(rs.getString("description"));
                product.setRatingSP(rs.getDouble("rating"));
                product.setDiscountSP(rs.getInt("discount"));
                product.setId_producer(rs.getInt("id_producer"));
                list.add(product);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
        }
        return list;
    }

    
    public List<Product> getProductsByPage(int pageNumber, int pageSize, String name, String category, List<String> selectedBrands, double minPrice, double maxPrice, List<Integer> selectedRatings) {
        List<Product> list = new ArrayList<>();
        String baseQuery = "SELECT sanphamchinh.*, spncc.*, ctbrand.*, category.name AS category_name, brand.name AS brand_name " +
                "FROM sanphamchinh " +
                "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                "INNER JOIN category ON ctbrand.id_category = category.id " +
                "INNER JOIN brand ON ctbrand.id_brand = brand.id ";

        StringBuilder whereClause = new StringBuilder();
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            whereClause.append("AND spncc.name LIKE ? ");
            params.add("%" + name + "%");
        }

        if (category != null && !category.isEmpty()) {
            whereClause.append("AND category.name = ? ");
            params.add(category);
        }
        
        if (!selectedBrands.isEmpty()) {
            whereClause.append("AND (");
            boolean firstBrand = true;

            // Iterate through the selected brands
            for (String selectedBrand : selectedBrands) {
                if (!firstBrand) {
                    whereClause.append("OR ");
                }

                whereClause.append("brand.name = ? ");
                params.add(selectedBrand);

                firstBrand = false;
            }

            whereClause.append(")");
        }

        if ((minPrice > 0 || maxPrice < Double.MAX_VALUE) || (minPrice > 0 && maxPrice == 0) || (minPrice == 0 && maxPrice > 0)) {
            whereClause.append("AND sanphamchinh.price >= ? AND sanphamchinh.price <= ? ");
            params.add(minPrice);
            params.add(maxPrice);
        }
        
        if (!selectedRatings.isEmpty()) {
            whereClause.append("AND (");
            boolean firstRating = true;

            for (Integer rating : selectedRatings) {
                if (!firstRating) {
                    whereClause.append("OR ");
                }
                if (null != rating) switch (rating) {
                    case 5:
                        // If 5 Stars is selected,  filter for ratings greater than or equal to 9.0
                        whereClause.append("sanphamchinh.rating >= ? ");
                        params.add(9.0);
                        break;
                    case 4:
                        // For 4 Stars, filter for ratings between 7.5 and 8.9
                        whereClause.append("sanphamchinh.rating >= ? AND sanphamchinh.rating < ? ");
                        params.add(7.5);
                        params.add(9.0);
                        break;
                    case 3:
                        // For 3 Stars, filter for ratings between 5.9 and 7.4
                        whereClause.append("sanphamchinh.rating >= ? AND sanphamchinh.rating < ? ");
                        params.add(5.9);
                        params.add(7.5);
                        break;
                    case 2:
                        // For 2 Stars, filter for ratings between 3.5 and 5.8
                        whereClause.append("sanphamchinh.rating >= ? AND sanphamchinh.rating < ? ");
                        params.add(3.5);
                        params.add(5.9);
                        break;
                    default:
                        break;
                }

                firstRating = false;
            }

            whereClause.append(")");
        }
        
        String query = baseQuery + (whereClause.length() > 0 ? "WHERE 1 " + whereClause.toString() : "") +
                "LIMIT " + ((pageNumber - 1) * pageSize) + ", " + pageSize;

        try {
            conn = new DBUtil().getConnection(); // Open a connection to SQL
            stm = conn.prepareStatement(query);

            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setIdSP(rs.getString("id"));
                product.setPriceSP(rs.getDouble("price"));
                product.setQuantitySP(rs.getInt("quantity"));
                product.setId_spnccSP(rs.getString("id_spncc"));
                product.setNameSP(rs.getString("name"));
                product.setImageSP(rs.getString("image"));
                product.setImageSP1(rs.getString("image1"));
                product.setImageSP2(rs.getString("image2"));
                product.setImageSP3(rs.getString("image3"));
                product.setCategorySP(rs.getString("category_name"));
                product.setBrandSP(rs.getString("brand_name"));
                product.setDescriptionSP(rs.getString("description"));
                product.setRatingSP(rs.getDouble("rating"));
                product.setDiscountSP(rs.getInt("discount"));
                product.setId_producer(rs.getInt("id_producer"));
                
                list.add(product);
            }
        } catch (Exception e) {
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                
            }
        }
        return list;
    }
    
    public int getTotalProductCount(String name, String category, List<String> selectedBrands, double minPrice, double maxPrice, List<Integer> selectedRatings) {
        int totalCount = 0;
        String baseQuery = "SELECT COUNT(*) " +
                          "FROM sanphamchinh " +
                          "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                          "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                          "INNER JOIN category ON ctbrand.id_category = category.id " +
                          "INNER JOIN brand ON ctbrand.id_brand = brand.id ";

        // Build the WHERE clause based on search criteria
        StringBuilder whereClause = new StringBuilder();
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            whereClause.append("AND spncc.name LIKE ? ");
            params.add("%" + name + "%");
        }

        if (category != null && !category.isEmpty()) {
            whereClause.append("AND category.name = ? ");
            params.add(category);
        }

        if (!selectedBrands.isEmpty()) {
            whereClause.append("AND (");
            boolean firstBrand = true;

            for (String selectedBrand : selectedBrands) {
                if (!firstBrand) {
                    whereClause.append("OR ");
                }

                whereClause.append("brand.name = ? ");
                params.add(selectedBrand);

                firstBrand = false;
            }
            whereClause.append(")");
        }

        if ((minPrice > 0 || maxPrice < Double.MAX_VALUE) || (minPrice > 0 && maxPrice == 0) || (minPrice == 0 && maxPrice > 0)) {
            // Search for products within the price range
            whereClause.append("AND sanphamchinh.price >= ? AND sanphamchinh.price <= ? ");
            params.add(minPrice);
            params.add(maxPrice);
        }

        if (!selectedRatings.isEmpty()) {
            whereClause.append("AND (");
            boolean firstRating = true;

            for (Integer rating : selectedRatings) {
                if (!firstRating) {
                    whereClause.append("OR ");
                }

                if (rating == 5) {
                    // If 5 Stars is selected, filter for ratings greater than or equal to 9.0
                    whereClause.append("sanphamchinh.rating >= ? ");
                    params.add(9.0);
                } else if (rating == 4) {
                    // For 4 Stars, filter for ratings between 7.5 and 8.9
                    whereClause.append("sanphamchinh.rating >= ? AND sanphamchinh.rating < ? ");
                    params.add(7.5);
                    params.add(9.0);
                } else if (rating == 3) {
                    // For 3 Stars, filter for ratings between 5.9 and 7.4
                    whereClause.append("sanphamchinh.rating >= ? AND sanphamchinh.rating < ? ");
                    params.add(5.9);
                    params.add(7.5);
                } else if (rating == 2) {
                    // For 2 Stars, filter for ratings between 3.5 and 5.8
                    whereClause.append("sanphamchinh.rating >= ? AND sanphamchinh.rating < ? ");
                    params.add(3.5);
                    params.add(5.9);
                }
                firstRating = false;
            }

            whereClause.append(")");
        }

        String query = baseQuery + (whereClause.length() > 0 ? "WHERE 1 " + whereClause.toString() : "");

        try {
            conn = new DBUtil().getConnection(); // Open a connection to SQL
            stm = conn.prepareStatement(query);

            // Set parameters for search criteria
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            rs = stm.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1); // Get the total count from the result set
            }
        } catch (Exception e) {
            // Handle exceptions here (e.g., log or throw)
        } finally {
            // Close resources (connection, statement, result set) in a finally block
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                // Handle closing exceptions if necessary
            }
        }
        return totalCount;
    }
    
    
    public Product getProductById(String productId) {
        String query = "SELECT sanphamchinh.*, spncc.*, ctbrand.*, category.name AS category_name, brand.name AS brand_name " +
                       "FROM sanphamchinh " +
                       "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                       "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                       "INNER JOIN category ON ctbrand.id_category = category.id " +
                       "INNER JOIN brand ON ctbrand.id_brand = brand.id " +
                       "WHERE sanphamchinh.id = ?"; 

        try {
            conn = new DBUtil().getConnection(); // Open a connection to SQL
            stm = conn.prepareStatement(query);
            stm.setString(1, productId); // Set the product ID as a parameter
            rs = stm.executeQuery();

            if (rs.next()) {
                // Create and populate a Product object with the retrieved data
                Product product = new Product();
                product.setIdSP(rs.getString("id"));
                product.setPriceSP(rs.getDouble("price"));
                product.setQuantitySP(rs.getInt("quantity"));
                product.setId_spnccSP(rs.getString("id_spncc"));
                product.setNameSP(rs.getString("name"));
                product.setImageSP(rs.getString("image"));
                product.setImageSP1(rs.getString("image1"));
                product.setImageSP2(rs.getString("image2"));
                product.setImageSP3(rs.getString("image3"));
                product.setCategorySP(rs.getString("category_name"));
                product.setBrandSP(rs.getString("brand_name"));
                product.setDescriptionSP(rs.getString("description"));
                product.setRatingSP(rs.getDouble("rating"));
                product.setDiscountSP(rs.getInt("discount"));
                product.setId_producer(rs.getInt("id_producer"));

                return product; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null; 
    }
    
    
}
