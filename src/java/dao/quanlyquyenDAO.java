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
import model.quanlyhoadonMODEL;
import model.quanlyquyenMODEL;
import model.quanlyquyenStatusMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class quanlyquyenDAO {
      private final DBUtil dbUtil = new DBUtil();

    public List<quanlyquyenMODEL> getData(int page, int recordsPerPage) throws Exception {
        List<quanlyquyenMODEL> quyenList = new ArrayList<>();
        String query = "SELECT *,trangthai.name as nametrangthai FROM `permission`,`trangthai` WHERE permission.status = trangthai.id LIMIT ?, ?";
         try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int startIndex = (page - 1) * recordsPerPage;
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                quanlyquyenMODEL quyen = new quanlyquyenMODEL();
                quyen.setId(resultSet.getInt("id"));
                quyen.setName(resultSet.getString("name"));
                quyen.setStatus(resultSet.getString("nametrangthai"));
                quyenList.add(quyen);
                }
            }
        }
        return quyenList;
    }
    
     public int getTotalPermissionCount() throws Exception {
        int totalRecords = 0;
        String query = "SELECT COUNT(*) as totalRecords FROM `permission`,`trangthai` WHERE permission.status = trangthai.id ";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt("totalRecords");
            }
        }
        return totalRecords;
    }
     
        public List<quanlyquyenMODEL> searchPermissions(String keyword, int currentPage, int recordsPerPage) throws Exception {
           List<quanlyquyenMODEL> searchResults = new ArrayList<>();
           String query = "SELECT *,trangthai.name as nametrangthai FROM `permission`,`trangthai` WHERE permission.status = trangthai.id AND (permission.name LIKE ?) LIMIT ?, ?";
           int offset = (currentPage - 1) * recordsPerPage;

           try (Connection connection = dbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, "%" + keyword + "%");
               preparedStatement.setInt(2, offset);
               preparedStatement.setInt(3, recordsPerPage);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   while (resultSet.next()) {
                     quanlyquyenMODEL quyen = new quanlyquyenMODEL();
                        quyen.setId(resultSet.getInt("id"));
                        quyen.setName(resultSet.getString("name"));
                        quyen.setStatus(resultSet.getString("nametrangthai"));
                       searchResults.add(quyen);
                   }
               }
           }
           return searchResults;
       }
                    
                       public int getTotalSearchPermissonCount(String keyword) throws Exception {
                        int totalRecords = 0;
                        String query = "SELECT COUNT(*) FROM `permission` WHERE name LIKE ?";

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
    
            public int getMaxId() throws Exception {
            int maxId = 0;
            String query = "SELECT MAX(id) FROM permission";

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
            
        public boolean addPermission(quanlyquyenMODEL quyen) throws Exception {
               String query = "INSERT INTO `permission`(`id`, `name`, `status`) VALUES (?, ?,?)";

                   try (Connection connection = dbUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(query)) {
                       statement.setInt(1, quyen.getId());
                       statement.setString(2, quyen.getName());
                       statement.setString(3, "1");

                       int rowsInserted = statement.executeUpdate();
                       return rowsInserted > 0;
                   } catch (SQLException e) {
                       e.printStackTrace();
                       return false;
                   }
           }
        
         public List<quanlyquyenStatusMODEL> getDataCBBStatus() throws Exception {
        List<quanlyquyenStatusMODEL> quyenStatusList = new ArrayList<>();
        String query = "SELECT * FROM `trangthai`";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyquyenStatusMODEL quyenStatus = new quanlyquyenStatusMODEL();
                quyenStatus.setId(resultSet.getInt("id"));
                quyenStatus.setName(resultSet.getString("name"));
                quyenStatusList.add(quyenStatus);
            }
        }
        return quyenStatusList;
    }
         
                 public boolean isNameExist(String name) throws Exception {
            String query = "SELECT COUNT(*) FROM permission WHERE name = ?";
            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
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
         
          public boolean updatePermission(quanlyquyenMODEL quyen) throws Exception {
        String query = "UPDATE permission SET name= ? ,status = ? WHERE id = ?";

            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(3, quyen.getId());
                statement.setString(1, quyen.getName());
                statement.setString(2, quyen.getStatus());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
          
        
       public boolean lockOnlyPermission(int id) throws Exception {
        String query = "UPDATE `permission` SET status='0' WHERE id = ?";

            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
       
       public boolean lockPermissionUser(int id) throws Exception {
        String query = "UPDATE user INNER JOIN permission ON user.type = permission.id SET user.type = 3, permission.status = 0 WHERE permission.id = ?";

            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
       
public boolean lockPermissionAndUser(int id) throws Exception {
    String selectQuery = "SELECT user.type FROM user INNER JOIN permission ON user.type = permission.id WHERE permission.id = ?";
    
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
        selectStatement.setInt(1, id);
        ResultSet resultSet = selectStatement.executeQuery();
        
        if (resultSet.next()) {
            int userType = resultSet.getInt("type");
            if (userType == id) {
                // Gọi lockPermissionUser khi có sự khớp
                return lockPermissionUser(id);
            } else {
                // Gọi lockOnlyPermission khi không có sự khớp
                return lockOnlyPermission(id);
            }
        } else {
            // Truy vấn không thành công, gọi lockOnlyPermission
            return lockOnlyPermission(id);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
