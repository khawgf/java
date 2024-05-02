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
import model.MggToProductMODEL;
import model.quanlyhoadonMODEL;
import model.quanlymggMODEL;
import model.quanlyuserMODEL;
import model.spcMGGMODEL;
import util.DBUtil;

/**
 *
 * @author DELL
 */
public class quanlymggDAO {
     private final DBUtil dbUtil = new DBUtil();

    public List<quanlymggMODEL> getData(int page, int recordsPerPage) throws Exception {
        List<quanlymggMODEL> mggList = new ArrayList<>();
        String query = "SELECT * FROM `magiamgia` LIMIT ?, ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int startIndex = (page - 1) * recordsPerPage;
            preparedStatement.setInt(1, startIndex);
            preparedStatement.setInt(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                quanlymggMODEL mgg = new quanlymggMODEL();
                mgg.setId(resultSet.getInt("id"));
                mgg.setDiscount(resultSet.getString("discount"));
                mgg.setCode(resultSet.getString("code"));
                mggList.add(mgg);
                }
            }
        }
        return mggList;
    }
    
     public List<quanlymggMODEL> getDataALL() throws Exception {
        List<quanlymggMODEL> mggList = new ArrayList<>();
        String query = "SELECT * FROM `magiamgia`";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                quanlymggMODEL mgg = new quanlymggMODEL();
                mgg.setId(resultSet.getInt("id"));
                mgg.setDiscount(resultSet.getString("discount"));
                mgg.setCode(resultSet.getString("code"));
                mggList.add(mgg);
                }
            }
        }
        return mggList;
    }
    
     public int getTotalDiscountCount() throws Exception {
        int totalRecords = 0;
        String query = "SELECT COUNT(*) as totalRecords FROM `magiamgia`";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt("totalRecords");
            }
        }
        return totalRecords;
    }
     
        public List<quanlymggMODEL> searchDiscounts(String keyword, int currentPage, int recordsPerPage) throws Exception {
           List<quanlymggMODEL> searchResults = new ArrayList<>();
           String query = "SELECT * FROM `magiamgia` WHERE (discount LIKE ? OR code LIKE ?) LIMIT ?, ?";
           int offset = (currentPage - 1) * recordsPerPage;

           try (Connection connection = dbUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, "%" + keyword + "%");
               preparedStatement.setString(2, "%" + keyword + "%");
               preparedStatement.setInt(3, offset);
               preparedStatement.setInt(4, recordsPerPage);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   while (resultSet.next()) {
                      quanlymggMODEL mgg = new quanlymggMODEL();
                        mgg.setId(resultSet.getInt("id"));
                        mgg.setDiscount(resultSet.getString("discount"));
                        mgg.setCode(resultSet.getString("code"));
                       searchResults.add(mgg);
                   }
               }
           }
           return searchResults;
       }
                    
                       public int getTotalSearchDiscountCount(String keyword) throws Exception {
                        int totalRecords = 0;
                        String query = "SELECT COUNT(*) FROM `magiamgia` WHERE discount LIKE ? OR code LIKE ?";

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
            String query = "SELECT MAX(id) FROM magiamgia";

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
    
    public int getIDCTMGG() throws Exception {
            int maxId = 0;
            String query = "SELECT MAX(id) FROM chitietmagiamgia";

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
    
      public List<spcMGGMODEL> getCBBSPC() throws Exception {
        List<spcMGGMODEL> spcList = new ArrayList<>();
        String query = "SELECT *,spncc.name as spname FROM `sanphamchinh`,`spncc`,`brand` WHERE sanphamchinh.id_spncc = spncc.id AND spncc.id_ctbrand = brand.id";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                spcMGGMODEL spc = new spcMGGMODEL();
                spc.setId(resultSet.getInt("id"));
                spc.setName(resultSet.getString("spname"));
                spc.setQuantity(resultSet.getString("quantity"));
                spc.setType(resultSet.getString("name"));
                spc.setProducer(resultSet.getString("id_ctbrand"));
                spc.setDescription(resultSet.getString("description"));
                spc.setImg(resultSet.getString("image"));
                spc.setPrice(resultSet.getString("price"));
                spcList.add(spc);
            }
        }
        return spcList;
    }
    
        public boolean isCodeExist(String code) throws Exception {
            String query = "SELECT `id`, `discount`, `code` FROM `magiamgia` WHERE code = ?";
            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, code);
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
        
         public boolean CheckProductHasMGG(String id_spc) throws Exception {
            String query = "SELECT * FROM `chitietmagiamgia` WHERE id_sanphamchinh = ?";
            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, id_spc);
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
      
     public boolean addMGG(quanlymggMODEL mgg) throws Exception {
        String query = "INSERT INTO `magiamgia`(`id`, `discount`, `code`) VALUES (?, ?, ?)";

            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, mgg.getId());
                statement.setString(2, mgg.getDiscount());
                statement.setString(3, mgg.getCode());


                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
     
      public boolean updateMGG(quanlymggMODEL mgg) throws Exception {
    String query = "UPDATE `magiamgia` SET `discount`= ?,`code`= ? WHERE `id` = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, mgg.getDiscount());
        statement.setString(2, mgg.getCode());
        statement.setInt(3, mgg.getId());
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
       public boolean addMGGTOPRODUCT(MggToProductMODEL mggToP) throws Exception {
        String query = "INSERT INTO `chitietmagiamgia`(`id`, `id_magiamgia`, `id_sanphamchinh`) VALUES (?, ?, ?)";

            try (Connection connection = dbUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, mggToP.getId());
                statement.setString(2, mggToP.getId_mgg());
                statement.setString(3, mggToP.getId_product());

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
       
          public boolean deleteMGG(int id) throws Exception {
    String query = "DELETE magiamgia, chitietmagiamgia FROM magiamgia LEFT JOIN chitietmagiamgia ON magiamgia.id = chitietmagiamgia.id_magiamgia WHERE magiamgia.id = ?";

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
       
}
