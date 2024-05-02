/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.thongkenguoidungMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class thongkenguoidungDAO {
       private final DBUtil dbUtil = new DBUtil();

    public List<thongkenguoidungMODEL> getData() throws Exception {
        List<thongkenguoidungMODEL> thongkeNDList = new ArrayList<>();
        String query = "SELECT user.phone AS phoneuser,user.name AS nameuser,COUNT(cthd.id_user) AS totalcount,SUM(cthd.quantity * cthd.price) AS total_value FROM cthd, user, hoadon WHERE cthd.id_user = user.id and cthd.id_hoadon = hoadon.id and hoadon.status = '4' GROUP BY cthd.id_user, user.phone, user.name ORDER BY total_value DESC LIMIT 10";
        try (java.sql.Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                thongkenguoidungMODEL thongkend = new thongkenguoidungMODEL();
                thongkend.setPhoneUser(resultSet.getString("phoneuser"));
                thongkend.setNameUser(resultSet.getString("nameuser"));
                thongkend.setTotalOrder(resultSet.getString("totalcount"));
                thongkend.setTotalCash(resultSet.getString("total_value"));
                thongkeNDList.add(thongkend);
            }
        }
        return thongkeNDList;
    }
    
    public List<thongkenguoidungMODEL> searchData(String datetimeStart, String datetimeEnd) throws Exception {
        List<thongkenguoidungMODEL> thongkeNDList = new ArrayList<>();
        String query = "SELECT user.phone AS phoneuser,user.name AS nameuser,COUNT(cthd.id_user) AS totalcount,SUM(cthd.quantity * cthd.price) AS total_value FROM cthd, user, hoadon WHERE cthd.id_user = user.id and cthd.id_hoadon = hoadon.id and hoadon.status = '4' AND hoadon.updatedAt BETWEEN ? AND ? GROUP BY cthd.id_user, user.phone, user.name ORDER BY total_value DESC LIMIT 10";
      try (java.sql.Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, datetimeStart);
        preparedStatement.setString(2, datetimeEnd);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
               thongkenguoidungMODEL thongkend = new thongkenguoidungMODEL();
                thongkend.setPhoneUser(resultSet.getString("phoneuser"));
                thongkend.setNameUser(resultSet.getString("nameuser"));
                thongkend.setTotalOrder(resultSet.getString("totalcount"));
                thongkend.setTotalCash(resultSet.getString("total_value"));
                thongkeNDList.add(thongkend);
            }
        }
    }
    return thongkeNDList;
}
}
