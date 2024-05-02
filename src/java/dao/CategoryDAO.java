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
import model.Category;

/**
 *
 * @author ASUS
 */
public class CategoryDAO {
    Connection conn = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public CategoryDAO() {
    }

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category";
        try {
            conn = new DBUtil().getConnection();//mo ket noi voi sql
            stm = conn.prepareStatement(query);
            rs = stm.executeQuery();
            while (rs.next()) {
                Category cate = new Category();
                cate.setIdCate(rs.getInt("id"));
                cate.setNameCate(rs.getString("name"));
                list.add(cate);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
