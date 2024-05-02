/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.loginMODEL;
import model.quanlyuserMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class loginDAO {
        private final DBUtil dbUtil = new DBUtil();
          public void register(quanlyuserMODEL user) throws Exception {
         String query = "INSERT INTO user (phone, pass, name, createdAt,updatedAt, status, type) VALUES (?, ?, ?, NOW(),NOW(), '1', '3')";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    preparedStatement.setString(1, user.getPhone());
        preparedStatement.setString(2, user.getPass());
        preparedStatement.setString(3, user.getName());
            preparedStatement.executeUpdate();
        }
    }
          public boolean isPhoneExist(String phone) throws Exception {
            String query = "SELECT COUNT(*) FROM user WHERE phone = ? AND status = '1'";
            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, phone);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
          
             public loginMODEL checkLoginAndGetUserName(String userName, String password) throws Exception {
    String query = "SELECT * FROM user WHERE phone = ? AND pass = ? and status = '1' and type = '3'";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loginMODEL user = new loginMODEL();
                    user.setId(resultSet.getString("id"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setName(resultSet.getString("name"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
           public loginMODEL checkLoginAndGetEmployeeName(String userName, String password) throws Exception {
   String query = "SELECT * FROM user WHERE phone = ? AND pass = ? and status = '1' and type = '2'";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loginMODEL user = new loginMODEL();
                    user.setId(resultSet.getString("id"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setName(resultSet.getString("name"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
          
            public loginMODEL checkLoginAndGetAdminName(String userName, String password) throws Exception {
        String query = "SELECT * FROM user WHERE phone = ? AND pass = ? and status = '1' and type = '1'";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    loginMODEL user = new loginMODEL();
                    user.setId(resultSet.getString("id"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setName(resultSet.getString("name"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
            
                   public boolean updatePassUser(quanlyuserMODEL user) throws Exception {
    String query = " UPDATE `user` SET `pass`= ? WHERE `phone` = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, user.getPass());
        statement.setString(2, user.getPhone());
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
       }
}
