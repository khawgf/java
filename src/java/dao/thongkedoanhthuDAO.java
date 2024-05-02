/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.thongkedoanhthuMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class thongkedoanhthuDAO {
     private final DBUtil dbUtil = new DBUtil();
    
      public List<thongkedoanhthuMODEL> getData() throws Exception {
        List<thongkedoanhthuMODEL> thongkeDTList = new ArrayList<>();
        String query = "SELECT (SELECT SUM(cthd.price * cthd.quantity) FROM cthd JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE hoadon.status = '4') AS total_ban, (SELECT SUM(ctpn.price * ctpn.quantity) FROM ctpn JOIN phieunhap ON ctpn.id_phieunhap = phieunhap.id) AS total_nhap, ((SELECT SUM(cthd.price * cthd.quantity) FROM cthd JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE hoadon.status = '4') - (SELECT SUM(ctpn.price * ctpn.quantity) FROM ctpn JOIN phieunhap ON ctpn.id_phieunhap = phieunhap.id)) AS total_ketqua";
       try (java.sql.Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                thongkedoanhthuMODEL thongkeDT = new thongkedoanhthuMODEL();
                thongkeDT.setTotal_nhap(resultSet.getString("total_nhap"));
                thongkeDT.setTotal_ban(resultSet.getString("total_ban"));
                 thongkeDT.setTotal_ketqua(resultSet.getString("total_ketqua"));
                thongkeDTList.add(thongkeDT);
                }
        }
        return thongkeDTList;
    }
      
       public List<thongkedoanhthuMODEL> searchData(String datetimeStart, String datetimeEnd) throws Exception {
        List<thongkedoanhthuMODEL> thongkeDTList = new ArrayList<>();
        String query = "SELECT (SELECT SUM(cthd.price * cthd.quantity) FROM cthd JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE hoadon.status = '4' AND hoadon.updatedAt BETWEEN ? AND ?) AS total_ban, (SELECT SUM(ctpn.price * ctpn.quantity) FROM ctpn JOIN phieunhap ON ctpn.id_phieunhap = phieunhap.id WHERE phieunhap.createdAT BETWEEN ? AND ?) AS total_nhap,((SELECT SUM(cthd.price * cthd.quantity) FROM cthd JOIN hoadon ON cthd.id_hoadon = hoadon.id WHERE hoadon.status = '4' AND hoadon.updatedAt BETWEEN ? AND ?) - (SELECT SUM(ctpn.price * ctpn.quantity) FROM ctpn JOIN phieunhap ON ctpn.id_phieunhap = phieunhap.id WHERE phieunhap.createdAT BETWEEN ? AND ?)) AS total_ketqua";
      try (java.sql.Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, datetimeStart);
        preparedStatement.setString(2, datetimeEnd);
        preparedStatement.setString(3, datetimeStart);
        preparedStatement.setString(4, datetimeEnd);
        preparedStatement.setString(5, datetimeStart);
        preparedStatement.setString(6, datetimeEnd);
        preparedStatement.setString(7, datetimeStart);
        preparedStatement.setString(8, datetimeEnd);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
               thongkedoanhthuMODEL thongkeDT = new thongkedoanhthuMODEL();
                thongkeDT.setTotal_nhap(resultSet.getString("total_nhap"));
                thongkeDT.setTotal_ban(resultSet.getString("total_ban"));
                 thongkeDT.setTotal_ketqua(resultSet.getString("total_ketqua"));
                thongkeDTList.add(thongkeDT);
            }
        }
    }
    return thongkeDTList;
}
}
