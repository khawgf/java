/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.quanlyuserMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class updateInfoDAO {
        private final DBUtil dbUtil = new DBUtil();
     public boolean updateInfoUser(quanlyuserMODEL user) throws Exception {
    String query = "UPDATE user SET phone = ?,name = ?, updatedAt = NOW() WHERE id = ?";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(3, user.getId());
        statement.setString(1, user.getPhone());
        statement.setString(2, user.getName());
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
   
}
     
       public boolean updatePassUser(quanlyuserMODEL user) throws Exception {
    String query = "UPDATE user SET pass = ?, updatedAt = NOW() WHERE id = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, user.getPass());
        statement.setInt(2, user.getId());
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
       }
}
