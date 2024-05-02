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
import model.nccMODEL;
import model.quanlyspcMODEL;
import util.DBUtil;

/**
 *
 * @author hp
 */
public class quanlynccDAO {

    private final DBUtil dbUtil = new DBUtil();

    public List<nccMODEL> getData(int page, int recordsPerPage) throws Exception {
    List<nccMODEL> nccList = new ArrayList<>();
    String query = "SELECT * FROM nhacungcap WHERE status = 1 LIMIT ?, ?";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        int startIndex = (page - 1) * recordsPerPage;
        preparedStatement.setInt(1, startIndex);
        preparedStatement.setInt(2, recordsPerPage);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                nccMODEL ncc = new nccMODEL();
                ncc.setId(resultSet.getInt("id"));
                ncc.setName(resultSet.getString("name"));
                ncc.setPhone(resultSet.getString("phone"));
                ncc.setCreated(resultSet.getString("createdAt"));
                ncc.setUpdated(resultSet.getString("updatedAt"));
                nccList.add(ncc);
            }
        }
    }
    return nccList;
}
public List<nccMODEL> searchOrders(String keyword, int currentPage, int recordsPerPage) throws Exception {
    List<nccMODEL> searchResults = new ArrayList<>();
    String query = "SELECT * FROM nhacungcap WHERE status = 1 AND name LIKE ? LIMIT ?, ?";
    int offset = (currentPage - 1) * recordsPerPage;

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, "%" + keyword + "%");
        preparedStatement.setInt(2, offset);
        preparedStatement.setInt(3, recordsPerPage);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                nccMODEL ncc = new nccMODEL();
                ncc.setId(resultSet.getInt("id"));
                ncc.setName(resultSet.getString("name"));
                ncc.setPhone(resultSet.getString("phone"));
                ncc.setCreated(resultSet.getString("createdAt"));
                ncc.setUpdated(resultSet.getString("updatedAt"));
                searchResults.add(ncc);
            }
        }
    }
    return searchResults;
}
public int getTotalSearchOrderCount(String keyword) throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) FROM nhacungcap WHERE status = 1 AND name LIKE ?";
    
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
public int getTotalnccCount() throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) as totalRecords FROM nhacungcap WHERE status = 1";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
            totalRecords = resultSet.getInt("totalRecords");
        }
    }
    return totalRecords;
}


    public void addNhaCungCap(String name, String phone) throws Exception {
        String query = "INSERT INTO nhacungcap (name, phone, status, createdAt, updatedAt) VALUES (?, ?, 1, NOW(), NOW())";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.executeUpdate();
        }
    }

    public void deleteNhaCungCap(int idncc) throws Exception {
        String query = "UPDATE nhacungcap SET status = 0 WHERE id = ?";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idncc);
            preparedStatement.executeUpdate();
        }
    }

    public void updateNhaCungCap(int idncc, String name, String phone) throws Exception {
        String query = "UPDATE nhacungcap SET name = ?, phone = ?, createdAt = NOW(), updatedAt = NOW() WHERE id = ?";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setInt(3, idncc);
            preparedStatement.executeUpdate();
        }
    }
    
    public boolean checkNameNCC(String name) throws Exception {
    String query = "SELECT COUNT(*) FROM nhacungcap WHERE name = ?";
    
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
