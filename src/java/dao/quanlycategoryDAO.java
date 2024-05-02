/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.categoryMODEL;
import model.nccMODEL;
import model.quanlyspcMODEL;
import util.DBUtil;


/**
 *
 * @author hp
 */
public class quanlycategoryDAO {
     private final DBUtil dbUtil = new DBUtil();

   public List<categoryMODEL> getData(int page, int recordsPerPage) throws Exception {
    List<categoryMODEL> categoryList = new ArrayList<>();
    String query = "SELECT * FROM category LIMIT ?, ?";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        int startIndex = (page - 1) * recordsPerPage;
        preparedStatement.setInt(1, startIndex);
        preparedStatement.setInt(2, recordsPerPage);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                categoryMODEL category = new categoryMODEL();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categoryList.add(category);
            }
        }
    }
    return categoryList;
}
   public List<categoryMODEL> searchOrders(String keyword, int currentPage, int recordsPerPage) throws Exception {
    List<categoryMODEL> searchResults = new ArrayList<>();
    String query = "SELECT * FROM category WHERE name LIKE ? LIMIT ?, ?";
    int offset = (currentPage - 1) * recordsPerPage;

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, "%" + keyword + "%");
        preparedStatement.setInt(2, offset);
        preparedStatement.setInt(3, recordsPerPage);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                categoryMODEL category = new categoryMODEL();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                searchResults.add(category);
            }
        }
    }
    return searchResults;
}
public int getTotalSearchOrderCount(String keyword) throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) FROM category WHERE name LIKE ?";
    
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, "%" + keyword + "%");
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt(1);
            }
        }
    }

    return totalRecords;
}

public int getTotalcategoryCount() throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) as totalRecords FROM category";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
            totalRecords = resultSet.getInt("totalRecords");
        }
    }
    return totalRecords;
}

    public void addcategory(String name) throws Exception {
        String query = "INSERT INTO category (name) VALUES (?)";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }

    public void deletecategory(int idncc) throws Exception {
        String query = "DELETE FROM category WHERE id = ?";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idncc);
            preparedStatement.executeUpdate();
        }
    }

    public void updatecategory(int idncc, String name) throws Exception {
        String query = "UPDATE category SET name = ? WHERE id = ?";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            
            preparedStatement.setInt(2, idncc);
            preparedStatement.executeUpdate();
        }
    }
    
    public boolean checkNamecategory(String name) throws Exception {
    String query = "SELECT COUNT(*) FROM category WHERE name = ?";
    
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, name); // Thiết lập giá trị tham số

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Trả về true nếu có ít nhất một dòng trùng khớp
            }
        }
    }

    return false; // Trả về false nếu không có dòng nào trùng khớp
}
    
   
}
