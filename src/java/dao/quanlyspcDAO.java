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
import model.quanlyspcMODEL;
import model.supplierMODEL;

import util.DBUtil;

/**
 *
 * @author DELL
 */
public class quanlyspcDAO {

    private final DBUtil dbUtil = new DBUtil();

   public List<quanlyspcMODEL> getData(int page, int recordsPerPage) throws Exception {
    List<quanlyspcMODEL> spcList = new ArrayList<>();
    String query = "SELECT sanphamchinh.*, spncc.name AS name, spncc.description AS description, " +
                   "spncc.image AS image, ctbrand.id_brand AS id_brand, ctbrand.id_category AS id_category, " +
                   "brand.name AS brand_name, category.name AS category_name " +
                   "FROM sanphamchinh " +
                   "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                   "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                   "LEFT JOIN brand ON ctbrand.id_brand = brand.id " +
                   "LEFT JOIN category ON ctbrand.id_category = category.id " +
                   "LIMIT ?, ?";
    
    int startIndex = (page - 1) * recordsPerPage;

    try (Connection connection = dbUtil.getConnection(); 
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, startIndex);
        preparedStatement.setInt(2, recordsPerPage);
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyspcMODEL spc = new quanlyspcMODEL();
                spc.setId(resultSet.getInt("id"));
                spc.setName(resultSet.getString("name")); // Use the name from spncc table
                spc.setQuantity(resultSet.getString("quantity"));
                spc.setCategory(resultSet.getString("category_name"));
                spc.setBrand(resultSet.getString("brand_name"));
                spc.setDescription(resultSet.getString("description"));
                spc.setImg(resultSet.getString("image"));
                spc.setPrice(resultSet.getString("price"));
                spc.setDiscount(resultSet.getString("discount"));
                spc.setRating(resultSet.getString("rating"));
                spcList.add(spc);
            }
        }
    }
    return spcList;
}
public int getTotalnccCount() throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) as totalRecords FROM sanphamchinh";
    
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
            totalRecords = resultSet.getInt("totalRecords");
        }
    }
    return totalRecords;
}
public int getTotalSearchOrderCount(String keyword) throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) FROM sanphamchinh " +
                   "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                   "LEFT JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                   "LEFT JOIN brand ON ctbrand.id_brand = brand.id " +
                   "LEFT JOIN category ON ctbrand.id_category = category.id " +
                   "WHERE spncc.name LIKE ? OR brand.name LIKE ? OR category.name LIKE ?";
    
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        String searchKeyword = "%" + keyword + "%";
        preparedStatement.setString(1, searchKeyword);
        preparedStatement.setString(2, searchKeyword);
        preparedStatement.setString(3, searchKeyword);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt(1);
            }
        }
    }
    return totalRecords;
}

public List<quanlyspcMODEL> searchOrders(String keyword, int currentPage, int recordsPerPage) throws Exception {
    List<quanlyspcMODEL> searchResults = new ArrayList<>();
    String query = "SELECT sanphamchinh.*, spncc.name AS name, spncc.description AS description, " +
                   "spncc.image AS image, ctbrand.id_brand AS id_brand, ctbrand.id_category AS id_category, " +
                   "brand.name AS brand_name, category.name AS category_name " +
                   "FROM sanphamchinh " +
                   "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                   "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
                   "LEFT JOIN brand ON ctbrand.id_brand = brand.id " +
                   "LEFT JOIN category ON ctbrand.id_category = category.id " +
                   "WHERE spncc.name LIKE ? OR brand.name LIKE ? OR category.name LIKE ? LIMIT ?, ?";
    
    int offset = (currentPage - 1) * recordsPerPage;

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        String searchKeyword = "%" + keyword + "%";
        preparedStatement.setString(1, searchKeyword);
        preparedStatement.setString(2, searchKeyword);
        preparedStatement.setString(3, searchKeyword);
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, recordsPerPage);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyspcMODEL spc = new quanlyspcMODEL();
                spc.setId(resultSet.getInt("id"));
                spc.setName(resultSet.getString("name"));
                spc.setQuantity(resultSet.getString("quantity"));
                spc.setCategory(resultSet.getString("category_name"));
                spc.setBrand(resultSet.getString("brand_name"));
                spc.setDescription(resultSet.getString("description"));
                spc.setImg(resultSet.getString("image"));
                spc.setPrice(resultSet.getString("price"));
                spc.setDiscount(resultSet.getString("discount"));
                spc.setRating(resultSet.getString("rating"));
                searchResults.add(spc);
            }
        }
    }
    return searchResults;
}



    public List<supplierMODEL> getAllSuppliers() throws Exception {
        List<supplierMODEL> spcList = new ArrayList<>();
        String query = "SELECT id, name FROM spncc";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                supplierMODEL spc = new supplierMODEL();
                spc.setId(resultSet.getInt("id"));
                spc.setName(resultSet.getString("name"));

                spcList.add(spc);
            }
        }
        return spcList;
    }

    public int getProductPrice(int id_spncc) throws Exception {
        int price = 0; // Giá trị ban đầu

        String query = "SELECT price FROM spncc WHERE id = ?";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_spncc); // Thiết lập giá trị tham số

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    price = resultSet.getInt("price"); // Lấy giá sản phẩm
                }
            }
        }

        return price;
    }

  public void addCTPX(int id_spncc, int quantity, int price) throws Exception {
    int newIdPhieuXuat = findMaxIdPhieuXuat() + 1;

    String query = "INSERT INTO ctpx (id_phieuxuat, id_spncc, quantity, price) VALUES (?, ?, ?, ?)";

    try (Connection connection = dbUtil.getConnection(); 
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, newIdPhieuXuat);
        preparedStatement.setInt(2, id_spncc);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setInt(4, price);

        preparedStatement.executeUpdate();

        // Thêm dòng vào bảng phieuxuat với id mới tạo
        addPhieuXuat(newIdPhieuXuat);
    }
}

public void addPhieuXuat(int idPhieuXuat) throws Exception {
    String query = "INSERT INTO phieuxuat (id, createdAt) VALUES (?, NOW())";

    try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, idPhieuXuat);

        preparedStatement.executeUpdate();
    }
}


// Phương thức để tìm giá trị lớn nhất của id_phieuxuat trong bảng ctpx
private int findMaxIdPhieuXuat() throws Exception {
    String query = "SELECT MAX(id_phieuxuat) FROM ctpx";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0; // Trả về 0 nếu không có dòng nào trong bảng ctpx
    }
}

    

    public void updateSPNCCQuantity(int id_spncc, int quantity) throws Exception {
        String query = "UPDATE spncc SET quantity = quantity - ? WHERE id = ?";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id_spncc);

            preparedStatement.executeUpdate();
        }
    }

    public boolean isID_SPNCCInSanPhamChinh(int id_spncc) throws Exception {
        String query = "SELECT id_spncc FROM sanphamchinh WHERE id_spncc = ?";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_spncc);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public void updateSanPhamChinh(int id_spncc, int quantity, int price) throws Exception {
        String query = "UPDATE sanphamchinh SET quantity = quantity + ?, price = ? WHERE id_spncc = ?";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, price);
            preparedStatement.setInt(3, id_spncc);

            preparedStatement.executeUpdate();
        }
    }

    public void insertSanPhamChinh(int id_spncc, int quantity, int price) throws Exception {
        String query = "INSERT INTO sanphamchinh (id_spncc, quantity, price,discount,rating) VALUES (?, ?, ?,0,0)";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_spncc);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, price);

            

            preparedStatement.executeUpdate();
        }
    }
public int checkQuantity(int id_spncc) throws Exception {
        int quantity = -1; // Giá trị mặc định nếu không tìm thấy

        String query = "SELECT quantity FROM spncc WHERE id = ?";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_spncc);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    quantity = resultSet.getInt("quantity");
                }
            }
        }

        return quantity;
    }

 public void updateSPChinh(int id, int quantity, double price, int rating, double discount) {
        String query = "UPDATE sanphamchinh SET quantity=?, price=?, rating=?, discount=? WHERE id=?";
        
         try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, rating);
            preparedStatement.setDouble(4, discount);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }
 
 public int getIdSPNCCBySPC(int id_spc) throws Exception {
    String query = "SELECT id_spncc FROM sanphamchinh WHERE id = ?";

    try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, id_spc);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("id_spncc");
            }
        }
    }

    // Trả về một giá trị mặc định nếu không tìm thấy kết quả
    return -1; // Hoặc bất kỳ giá trị mặc định nào phù hợp với trường hợp của bạn
}
 
 public int getQuantitySPC(int id_spc) throws Exception {
    String query = "SELECT quantity FROM sanphamchinh WHERE id = ?";

    try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, id_spc);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            }
        }
    }

    // Trả về một giá trị mặc định nếu không tìm thấy kết quả
    return -1; // Hoặc bất kỳ giá trị mặc định nào phù hợp với trường hợp của bạn
}
public void deleteSPC(int idspc) throws Exception {
    String query = "DELETE FROM sanphamchinh WHERE id = ?";

    try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, idspc);
        preparedStatement.executeUpdate();
    }
}
public String getProductName(int id_spncc) throws Exception {
    String name = ""; // Giá trị mặc định

    String query = "SELECT name FROM spncc WHERE id = ?";

    try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, id_spncc); // Thiết lập giá trị tham số

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                name = resultSet.getString("name"); // Lấy tên sản phẩm
            }
        }
    }

    return name;
}
public String getProductName_update(int id_spncc) {
    String name = "";

    String query = "SELECT spncc.name " +
                   "FROM sanphamchinh " +
                   "INNER JOIN spncc ON sanphamchinh.id_spncc = spncc.id " +
                   "WHERE sanphamchinh.id = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setInt(1, id_spncc);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return name;
}




   public static void main(String[] args) {
    quanlyspcDAO dao = new quanlyspcDAO();

    try {
        int id_spncc = 21; // Thay thế bằng giá trị thích hợp
        int quantity = 4; // Thay thế bằng giá trị thích hợp
        int price = 100; // Thay thế bằng giá trị thích hợp

      
        String name = dao.getProductName_update(23);
        System.out.println("thành công"+ name);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi khi thêm mới.");
    }
}

}
