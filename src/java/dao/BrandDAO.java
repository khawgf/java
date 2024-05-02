/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
import util.DBUtil;

/**
 *
 * @author ASUS
 */
public class BrandDAO {
    Connection conn = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public BrandDAO() {
    }

    
    public List<Brand> getAllBrand() {
        List<Brand> list = new ArrayList<>();
        String query = "SELECT * FROM brand";
        try {
            conn = new DBUtil().getConnection();//mo ket noi voi sql
            stm = conn.prepareStatement(query);
            rs = stm.executeQuery();
            while (rs.next()) {
                Brand brand = new Brand();
                brand.setIdBrand(rs.getString("id"));
                brand.setNameBrand(rs.getString("name"));
                
                list.add(brand);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public List<Brand> getBrandsByCategory(String category_name) {
        List<Brand> brands = new ArrayList<>();

        String query = "SELECT brand.* " +
                       "FROM ctbrand " +
                       "INNER JOIN category ON category.id = ctbrand.id_category " +
                       "INNER JOIN brand ON brand.id = ctbrand.id_brand " +
                       "WHERE ctbrand.id_category = (SELECT id FROM category WHERE name = ?)";

        try {
            conn = new DBUtil().getConnection();
            stm = conn.prepareStatement(query);
            stm.setString(1, category_name); // Set the categoryId as a parameter
            rs = stm.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand();
                brand.setIdBrand(rs.getString("id"));
                brand.setNameBrand(rs.getString("name"));
                brands.add(brand);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return brands;
    }
    
}
