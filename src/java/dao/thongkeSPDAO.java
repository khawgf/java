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
import model.quanlymggMODEL;
import model.thongkesanphamMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class thongkeSPDAO {
    private final DBUtil dbUtil = new DBUtil();
    
      public List<thongkesanphamMODEL> getData() throws Exception {
        List<thongkesanphamMODEL> thongkeSPList = new ArrayList<>();
        String query = "SELECT spncc.name AS namesp,brand.name,CASE WHEN COUNT(*) >= 2 THEN SUM(cthd.quantity) ELSE MAX(cthd.quantity) END AS total_quantity,MAX(cthd.price) AS price FROM cthd JOIN sanphamchinh ON cthd.id_spc = sanphamchinh.id JOIN spncc ON sanphamchinh.id_spncc = spncc.id JOIN brand ON spncc.id_ctbrand = brand.id JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE hoadon.status = '4' GROUP BY spncc.name, brand.name ORDER BY total_quantity DESC LIMIT 10";
       try (java.sql.Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                thongkesanphamMODEL thongkeSP = new thongkesanphamMODEL();
                thongkeSP.setNamesp(resultSet.getString("namesp"));
                thongkeSP.setBrand(resultSet.getString("name"));
                thongkeSP.setQuantity(resultSet.getString("total_quantity"));
                thongkeSP.setPrice(resultSet.getString("price"));
                thongkeSPList.add(thongkeSP);
                }
        }
        return thongkeSPList;
    }
      
        public List<thongkesanphamMODEL> searchData(String datetimeStart, String datetimeEnd) throws Exception {
        List<thongkesanphamMODEL> thongkeSPList = new ArrayList<>();
        String query = "SELECT spncc.name AS namesp,brand.name,CASE WHEN COUNT(*) >= 2 THEN SUM(cthd.quantity) ELSE MAX(cthd.quantity) END AS total_quantity,MAX(cthd.price) AS price FROM cthd JOIN sanphamchinh ON cthd.id_spc = sanphamchinh.id JOIN spncc ON sanphamchinh.id_spncc = spncc.id JOIN brand ON spncc.id_ctbrand = brand.id JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE hoadon.status = '4' AND hoadon.updatedAt BETWEEN ? AND ? GROUP BY spncc.name, brand.name ORDER BY total_quantity DESC LIMIT 10";
      try (java.sql.Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, datetimeStart);
        preparedStatement.setString(2, datetimeEnd);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                thongkesanphamMODEL thongkeSP = new thongkesanphamMODEL();
                thongkeSP.setNamesp(resultSet.getString("namesp"));
                thongkeSP.setBrand(resultSet.getString("name"));
                thongkeSP.setQuantity(resultSet.getString("total_quantity"));
                thongkeSP.setPrice(resultSet.getString("price"));
                thongkeSPList.add(thongkeSP);
            }
        }
    }
    return thongkeSPList;
}
      
     
}
