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
import model.cthdMODEL;
import model.quanlyhoadonMODEL;
import model.quanlyhoadonStatusMODEL;
import model.quanlyuserMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class quanlyhoadonDAO {
    private final DBUtil dbUtil = new DBUtil();

    public List<quanlyhoadonMODEL> getData(int page, int recordsPerPage) throws Exception {
        List<quanlyhoadonMODEL> hoadonList = new ArrayList<>();
        String query = "SELECT *,trangthaidonhang.name as nametrangthai FROM `hoadon`,`trangthaidonhang` WHERE hoadon.status = trangthaidonhang.id LIMIT ?, ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int startIndex = (page - 1) * recordsPerPage;
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                quanlyhoadonMODEL hoadon = new quanlyhoadonMODEL();
                hoadon.setId(resultSet.getInt("id"));
                hoadon.setPhone(resultSet.getString("phone"));
                hoadon.setAddress(resultSet.getString("address"));
                hoadon.setStatus(resultSet.getString("nametrangthai"));
                hoadonList.add(hoadon);
                }
            }
        }
        return hoadonList;
    }
    
     public int getTotalOrderCount() throws Exception {
        int totalRecords = 0;
        String query = "SELECT COUNT(*) as totalRecords FROM `hoadon`,`trangthaidonhang` WHERE hoadon.status = trangthaidonhang.id";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt("totalRecords");
            }
        }
        return totalRecords;
    }
     
      public List<quanlyhoadonMODEL> searchOrders(String keyword, int currentPage, int recordsPerPage) throws Exception {
           List<quanlyhoadonMODEL> searchResults = new ArrayList<>();
           String query = "SELECT *,trangthaidonhang.name as nametrangthai FROM `hoadon`,`trangthaidonhang` WHERE hoadon.status = trangthaidonhang.id AND (hoadon.phone LIKE ?) LIMIT ?, ?";

           int offset = (currentPage - 1) * recordsPerPage;

           try (Connection connection = dbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, "%" + keyword + "%");
               preparedStatement.setInt(2, offset);
               preparedStatement.setInt(3, recordsPerPage);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   while (resultSet.next()) {
                        quanlyhoadonMODEL hoadon = new quanlyhoadonMODEL();
                        hoadon.setId(resultSet.getInt("id"));
                        hoadon.setPhone(resultSet.getString("phone"));
                        hoadon.setAddress(resultSet.getString("address"));
                        hoadon.setStatus(resultSet.getString("nametrangthai"));
                       searchResults.add(hoadon);
                   }
               }
           }
           return searchResults;
       }
      
       public int getTotalSearchOrderCount(String keyword) throws Exception {
                        int totalRecords = 0;
                        String query = "SELECT COUNT(*) FROM `hoadon` WHERE phone LIKE ?";

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
       
       public List<quanlyhoadonMODEL> searchOrdersDateTime(String datetimeStart, String datetimeEnd, int currentPage, int recordsPerPage) throws Exception {
           List<quanlyhoadonMODEL> searchResults = new ArrayList<>();
           String query = "SELECT *,trangthaidonhang.name as nametrangthai FROM `hoadon`,`trangthaidonhang` WHERE hoadon.status = trangthaidonhang.id AND hoadon.updatedAt BETWEEN ? AND ? LIMIT ?, ?";

           int offset = (currentPage - 1) * recordsPerPage;

           try (Connection connection = dbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, datetimeStart);
               preparedStatement.setString(2, datetimeEnd);
               preparedStatement.setInt(3, offset);
               preparedStatement.setInt(4, recordsPerPage);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   while (resultSet.next()) {
                        quanlyhoadonMODEL hoadon = new quanlyhoadonMODEL();
                        hoadon.setId(resultSet.getInt("id"));
                        hoadon.setPhone(resultSet.getString("phone"));
                        hoadon.setAddress(resultSet.getString("address"));
                        hoadon.setStatus(resultSet.getString("nametrangthai"));
                       searchResults.add(hoadon);
                   }
               }
           }
           return searchResults;
       }
       
        public int getTotalSearchOrderCountDateTime(String datetimeStart, String datetimeEnd) throws Exception {
                        int totalRecords = 0;
                        String query = "SELECT COUNT(*) FROM `hoadon` WHERE hoadon.updatedAt BETWEEN ? AND ?";

                        try (Connection connection = dbUtil.getConnection();
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                                    preparedStatement.setString(1, datetimeStart);
                                    preparedStatement.setString(2, datetimeEnd);
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    totalRecords = resultSet.getInt(1);
                                }
                            }
                        }

                        return totalRecords;
                    }
    
     public List<quanlyhoadonStatusMODEL> getDataCBBStatus() throws Exception {
        List<quanlyhoadonStatusMODEL> orderStatusList = new ArrayList<>();
        String query = "SELECT * FROM `trangthaidonhang`";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyhoadonStatusMODEL orderStatus = new quanlyhoadonStatusMODEL();
                orderStatus.setId(resultSet.getInt("id"));
                orderStatus.setName(resultSet.getString("name"));
                orderStatusList.add(orderStatus);
            }
        }
        return orderStatusList;
    }
     
    public boolean updateOrder(quanlyhoadonMODEL order) throws Exception {
    String query = "UPDATE `hoadon` SET `phone`= ?,`address`= ?,`status` = ?,`updatedAt`=Now() WHERE id = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(4, order.getId());
        statement.setString(1, order.getPhone());
        statement.setString(2, order.getAddress());
        statement.setString(3, order.getStatus());
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public boolean deleteOrder(int id) throws Exception {
    String query = "DELETE cthd, hoadon FROM cthd JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE cthd.id_hoadon = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, id);
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
          public List<cthdMODEL> getCTHDByID(int cthdID) throws Exception {
        List<cthdMODEL> cthdList = new ArrayList<>();
        String query = "SELECT hoadon.phone, hoadon.address, spncc.name as namesp, cthd.quantity, cthd.price, spncc.image, user.name FROM cthd JOIN hoadon ON cthd.id_hoadon = hoadon.id JOIN sanphamchinh ON cthd.id_spc = sanphamchinh.id JOIN spncc ON sanphamchinh.id_spncc = spncc.id JOIN user ON cthd.id_user = user.id WHERE cthd.id_hoadon = ?";
        try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, cthdID);
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                cthdMODEL cthd = new cthdMODEL();
                cthd.setPhoneKH(resultSet.getString("phone"));
                cthd.setAddress(resultSet.getString("address")); 
                cthd.setNamesp(resultSet.getString("namesp"));
                cthd.setQuantity(resultSet.getString("quantity"));
                cthd.setImg(resultSet.getString("image"));
                cthd.setPrice(resultSet.getString("price"));
                cthd.setNamekh(resultSet.getString("name"));
                cthdList.add(cthd);
            }
        }
    }
    
    return cthdList;
    }
    
}
