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
import model.quanlyuserMODEL;
import model.quanlyuserStatusMODEL;
import model.quanlyuserTypeMODEL;
import org.apache.catalina.User;
import util.DBUtil;

public class quanlyuserDAO {
    private final DBUtil dbUtil = new DBUtil();

     public List<quanlyuserMODEL> getUsersForPage(int page, int recordsPerPage) throws Exception {
        List<quanlyuserMODEL> userList = new ArrayList<>();
        String query = "SELECT *,permission.name as permissionName,trangthai.name as trangthai FROM `user`,`permission`,`trangthai` WHERE user.type = permission.id and user.status = trangthai.id and user.status = '1' LIMIT ?, ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int startIndex = (page - 1) * recordsPerPage;
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    quanlyuserMODEL user = new quanlyuserMODEL();
                              user.setId(resultSet.getInt("id"));
                user.setPhone(resultSet.getString("phone"));
                user.setPass(resultSet.getString("pass")); 
                user.setName(resultSet.getString("name"));
                user.setStatus(resultSet.getString("permissionName"));
                user.setType(resultSet.getString("trangthai"));   
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    public int getTotalUserCount() throws Exception {
        int totalRecords = 0;
        String query = "SELECT COUNT(*) as totalRecords FROM `user`,`permission`,`trangthai` WHERE user.type = permission.id and user.status = trangthai.id and user.status = '1'";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt("totalRecords");
            }
        }
        return totalRecords;
    }
    
                    public List<quanlyuserMODEL> searchUsers(String keyword, int currentPage, int recordsPerPage) throws Exception {
           List<quanlyuserMODEL> searchResults = new ArrayList<>();
           String query = "SELECT *, permission.name as permissionName, trangthai.name as trangthai " +
                          "FROM `user`, `permission`, `trangthai` " +
                          "WHERE user.type = permission.id and user.status = trangthai.id " +
                          "AND user.status = '1' " +
                          "AND (user.phone LIKE ? OR user.name LIKE ?) " +
                          "LIMIT ?, ?";

           int offset = (currentPage - 1) * recordsPerPage;

           try (Connection connection = dbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, "%" + keyword + "%");
               preparedStatement.setString(2, "%" + keyword + "%");
               preparedStatement.setInt(3, offset);
               preparedStatement.setInt(4, recordsPerPage);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   while (resultSet.next()) {
                       quanlyuserMODEL user = new quanlyuserMODEL();
                       user.setId(resultSet.getInt("id"));
                       user.setPhone(resultSet.getString("phone"));
                       user.setPass(resultSet.getString("pass"));
                       user.setName(resultSet.getString("name"));
                       user.setStatus(resultSet.getString("permissionName"));
                       user.setType(resultSet.getString("trangthai"));
                       searchResults.add(user);
                   }
               }
           }
           return searchResults;
       }
                    
                    public int getTotalSearchUserCount(String keyword) throws Exception {
                        int totalRecords = 0;
                        String query = "SELECT COUNT(*) FROM `user` WHERE phone LIKE ? OR name LIKE ?";

                        try (Connection connection = dbUtil.getConnection();
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, "%" + keyword + "%");
                            preparedStatement.setString(2, "%" + keyword + "%");

                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    totalRecords = resultSet.getInt(1);
                                }
                            }
                        }

                        return totalRecords;
                    }


        public int getMaxId() throws Exception {
            int maxId = 0;
            String query = "SELECT MAX(id) FROM user ";

            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxId = resultSet.getInt(1); // Get the maximum id
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return maxId + 1;
        }
        
         public List<quanlyuserStatusMODEL> getDataCBBStatus() throws Exception {
        List<quanlyuserStatusMODEL> userStatusList = new ArrayList<>();
        String query = "SELECT * FROM `trangthai`";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyuserStatusMODEL userStatus = new quanlyuserStatusMODEL();
                userStatus.setId(resultSet.getInt("id"));
                userStatus.setName(resultSet.getString("name"));
                userStatusList.add(userStatus);
            }
        }
        return userStatusList;
    }
     
       public List<quanlyuserTypeMODEL> getDataCBBType() throws Exception {
        List<quanlyuserTypeMODEL> userTypeList = new ArrayList<>();
        String query = "SELECT * FROM `permission`";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyuserTypeMODEL userType = new quanlyuserTypeMODEL();
                userType.setId(resultSet.getInt("id"));
                userType.setName(resultSet.getString("name"));
                userTypeList.add(userType);
            }
        }
        return userTypeList;
    }
       
        public boolean addOrUpdateUser(quanlyuserMODEL user) throws Exception {
            // Kiểm tra xem số điện thoại đã tồn tại trong cơ sở dữ liệu chưa
            if (isPhoneExist(user.getPhone())) {
                // Nếu số điện thoại đã tồn tại, thì cập nhật trạng thái thành 1
                return updateStatus(user);
            } else {
                // Nếu số điện thoại chưa tồn tại, thêm bản ghi mới vào cơ sở dữ liệu
                return insertNewUser(user);
            }
        }

        public boolean isPhoneExist(String phone) throws Exception {
            String query = "SELECT COUNT(*) FROM user WHERE phone = ? AND status = '0'";
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

        public boolean updateStatus(quanlyuserMODEL user) throws Exception {
            String query = "UPDATE user SET pass = ?, name = ?, updatedAt = NOW(), status = '1', type = ? WHERE phone = ?";
            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(4, user.getPhone());
                statement.setString(1, user.getPass());
                statement.setString(2, user.getName());
                statement.setString(3, user.getType());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean insertNewUser(quanlyuserMODEL user) throws Exception {
            String query = "INSERT INTO user (id, phone, pass, name, createdAt, updatedAt, status, type) VALUES (?, ?, ?, ?, NOW(), NOW(), ?, ?)";
            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, user.getId());
                statement.setString(2, user.getPhone());
                statement.setString(3, user.getPass());
                statement.setString(4, user.getName());
                statement.setString(5, user.getStatus());
                statement.setString(6, user.getType());

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

    public boolean updateUser(quanlyuserMODEL user) throws Exception {
    // Trước khi cập nhật, kiểm tra xem số điện thoại đã tồn tại chưa
    if (isPhoneExist(user.getPhone())) {
        // Số điện thoại đã tồn tại, không cho phép cập nhật
        return false;
    }
    
    String query = "UPDATE user SET phone = ?, pass = ?, name = ?, updatedAt = NOW(), status = ?, type = ? WHERE id = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(6, user.getId());
        statement.setString(1, user.getPhone());
        statement.setString(2, user.getPass());
        statement.setString(3, user.getName());
        statement.setString(4, user.getStatus());
        statement.setString(5, user.getType());

        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

       public boolean deleteUser(int userId)throws Exception {
        String query = "UPDATE user SET status = ? WHERE id = ?";
         try (Connection connection = dbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setInt(1, 0);
             statement.setInt(2, userId);
            
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
 
    }
        
    public static void main(String[] args) {
        
    }
}

