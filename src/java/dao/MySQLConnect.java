package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class MySQLConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/qlbddt";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Đăng ký JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thiết lập kết nối
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Kết nối thành công đến cơ sở dữ liệu.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Lỗi kết nối đến cơ sở dữ liệu: " + e.getMessage());
        }

        return connection;
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Đóng kết nối đến cơ sở dữ liệu.");
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }

    // Main method để kiểm tra kết nối (có thể bỏ đi nếu không cần)
    public static void main(String[] args) {
        Connection connection = getConnection();

        // Thực hiện các thao tác với cơ sở dữ liệu ở đây...

        closeConnection(connection);
    }

    public PreparedStatement prepareStatement(String sql) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
