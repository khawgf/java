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
import model.brandMODEL;
import model.categoryMODEL;
import model.nhacungcapMODEL;

import model.quanlyspnccMODEL;

import util.DBUtil;

/**
 *
 * @author DELL
 */
public class quanlyspnccDAO {

    private final DBUtil dbUtil = new DBUtil();

public List<quanlyspnccMODEL> getData(int page, int recordsPerPage) throws Exception {
    List<quanlyspnccMODEL> spcList = new ArrayList<>();
    String query = "SELECT \n"
            + "    spncc.*,\n"
            + "    ctbrand.id_brand AS id_brand,\n"
            + "    ctbrand.id_category AS id_category,\n"
            + "    brand.name AS brand_name,\n"
            + "    category.name AS category_name,\n"
            + "    nhacungcap.name AS namencc\n"
            + "FROM \n"
            + "    spncc\n"
            + "INNER JOIN \n"
            + "    ctbrand ON spncc.id_ctbrand = ctbrand.id\n"
            + "LEFT JOIN \n"
            + "    brand ON ctbrand.id_brand = brand.id\n"
            + "LEFT JOIN \n"
            + "    category ON ctbrand.id_category = category.id\n"
            + "LEFT JOIN \n"
            + "    nhacungcap ON spncc.id_producer = nhacungcap.id\n"
            + "LIMIT ?, ?";
    
    int startIndex = (page - 1) * recordsPerPage;

    try (Connection connection = dbUtil.getConnection(); 
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, startIndex);
        preparedStatement.setInt(2, recordsPerPage);
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyspnccMODEL spc = new quanlyspnccMODEL();
                spc.setId(resultSet.getInt("id"));
                spc.setId_producer(resultSet.getInt("id_producer"));
                spc.setId_brand(resultSet.getInt("id_brand"));
                spc.setId_category(resultSet.getInt("id_category"));
                spc.setName(resultSet.getString("name"));
                spc.setQuantity(resultSet.getString("quantity"));
                spc.setCategory(resultSet.getString("category_name"));
                spc.setBrand(resultSet.getString("brand_name"));
                spc.setDescription(resultSet.getString("description"));
                spc.setImg(resultSet.getString("image"));
                spc.setPrice(resultSet.getString("price"));
                spc.setOrigin(resultSet.getString("origin"));
                spc.setNamencc(resultSet.getString("namencc"));
                spc.setImg1(resultSet.getString("image1"));
                spc.setImg2(resultSet.getString("image2"));
                spc.setImg3(resultSet.getString("image3"));
                
                spcList.add(spc);
            }
        }
    }
    return spcList;
}

public int getTotalnccCount() throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) as totalRecords FROM spncc";
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
            totalRecords = resultSet.getInt("totalRecords");
        }
    }
    return totalRecords;
}

public List<quanlyspnccMODEL> searchOrders(String keyword, int currentPage, int recordsPerPage) throws Exception {
    List<quanlyspnccMODEL> searchResults = new ArrayList<>();
      String query = "SELECT \n" +
            "    spncc.*,\n" +
            "    ctbrand.id_brand AS id_brand,\n" +
            "    ctbrand.id_category AS id_category,\n" +
            "    brand.name AS brand_name,\n" +
            "    category.name AS category_name,\n" +
            "    nhacungcap.name AS namencc\n" +
            "FROM \n" +
            "    spncc\n" +
            "INNER JOIN \n" +
            "    ctbrand ON spncc.id_ctbrand = ctbrand.id\n" +
            "LEFT JOIN \n" +
            "    brand ON ctbrand.id_brand = brand.id\n" +
            "LEFT JOIN \n" +
            "    category ON ctbrand.id_category = category.id\n" +
            "LEFT JOIN \n" +
            "    nhacungcap ON spncc.id_producer = nhacungcap.id\n" +
            "WHERE spncc.name LIKE ? OR brand.name LIKE ? OR category.name LIKE ? OR nhacungcap.name LIKE ? LIMIT ?, ?";
    
    int offset = (currentPage - 1) * recordsPerPage;

     try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        String searchKeyword = "%" + keyword + "%";
        preparedStatement.setString(1, searchKeyword);
        preparedStatement.setString(2, searchKeyword);
        preparedStatement.setString(3, searchKeyword);
        preparedStatement.setString(4, searchKeyword);
        preparedStatement.setInt(5, offset);
        preparedStatement.setInt(6, recordsPerPage);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                quanlyspnccMODEL spc = new quanlyspnccMODEL();
                spc.setId(resultSet.getInt("id"));
                spc.setId_producer(resultSet.getInt("id_producer"));
                spc.setId_brand(resultSet.getInt("id_brand"));
                spc.setId_category(resultSet.getInt("id_category"));
                spc.setName(resultSet.getString("name"));
                spc.setQuantity(resultSet.getString("quantity"));
                spc.setCategory(resultSet.getString("category_name"));
                spc.setBrand(resultSet.getString("brand_name"));
                spc.setDescription(resultSet.getString("description"));
                spc.setImg(resultSet.getString("image"));
                spc.setPrice(resultSet.getString("price"));
                spc.setOrigin(resultSet.getString("origin"));
                spc.setNamencc(resultSet.getString("namencc"));
                spc.setImg1(resultSet.getString("image1"));
                spc.setImg2(resultSet.getString("image2"));
                spc.setImg3(resultSet.getString("image3"));
                
                searchResults.add(spc);
            }
        }
    }
    return searchResults;
}


public int getTotalSearchOrderCount(String keyword) throws Exception {
    int totalRecords = 0;
    String query = "SELECT COUNT(*) " +
            "FROM spncc " +
            "INNER JOIN ctbrand ON spncc.id_ctbrand = ctbrand.id " +
            "LEFT JOIN brand ON ctbrand.id_brand = brand.id " +
            "LEFT JOIN category ON ctbrand.id_category = category.id " +
            "LEFT JOIN nhacungcap ON spncc.id_producer = nhacungcap.id " +
            "WHERE spncc.name LIKE ? OR brand.name LIKE ? OR category.name LIKE ? OR nhacungcap.name LIKE ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        String searchKeyword = "%" + keyword + "%";
        preparedStatement.setString(1, searchKeyword);
        preparedStatement.setString(2, searchKeyword);
        preparedStatement.setString(3, searchKeyword);
        preparedStatement.setString(4, searchKeyword);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                totalRecords = resultSet.getInt(1);
            }
        }
    }

    return totalRecords;
}



    public List<nhacungcapMODEL> getNhacungcapList() throws Exception {
        List<nhacungcapMODEL> nhacungcapList = new ArrayList<>();
        String query = "SELECT * FROM nhacungcap";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                nhacungcapMODEL nhacungcap = new nhacungcapMODEL();
                nhacungcap.setId(resultSet.getInt("id"));
                nhacungcap.setName(resultSet.getString("name"));
                nhacungcapList.add(nhacungcap);
            }
        }
        return nhacungcapList;
    }

    public List<brandMODEL> getBrandList() throws Exception {
        List<brandMODEL> brandList = new ArrayList<>();
        String query = "SELECT * FROM brand";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                brandMODEL brand = new brandMODEL();
                brand.setId(resultSet.getInt("id"));
                brand.setName(resultSet.getString("name"));
                brandList.add(brand);
            }
        }
        return brandList;
    }

    public List<categoryMODEL> getcategoryList() throws Exception {
        List<categoryMODEL> categoryList = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                categoryMODEL category = new categoryMODEL();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categoryList.add(category);
            }
        }
        return categoryList;
    }

    public void addCTPN(int id_ncc, int quantity, int price) throws Exception {
        int newIdPhieuNhap = findMaxIdPhieuNhap() + 1;

        String query = "INSERT INTO ctpn (id_phieunhap, quantity, price) VALUES ( ?, ?, ?)";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newIdPhieuNhap);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, price);

            preparedStatement.executeUpdate();

            // Thêm dòng vào bảng phieuxuat với id mới tạo
            addPhieuNhap(newIdPhieuNhap, id_ncc);
        }
    }

    public void addPhieuNhap(int idPhieuNhap, int idncc) throws Exception {
        String query = "INSERT INTO phieunhap (id,id_ncc, createdAt) VALUES (?,?, NOW())";

        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idPhieuNhap);
            preparedStatement.setInt(2, idncc);

            preparedStatement.executeUpdate();
        }
    }

    private int findMaxIdPhieuNhap() throws Exception {
        String query = "SELECT MAX(id_phieunhap) FROM ctpn";
        try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0; // Trả về 0 nếu không có dòng nào trong bảng ctpx
        }
    }
    
    public void addCTBrand(int idBrand, int idCategory) throws Exception {
    String query = "INSERT INTO ctbrand (id_brand, id_category) VALUES (?, ?)";

    try (Connection connection = dbUtil.getConnection(); 
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, idBrand);
        preparedStatement.setInt(2, idCategory);

        preparedStatement.executeUpdate();
    }
}
    public int findMaxCTBrandId() throws Exception {
    String query = "SELECT MAX(id) FROM ctbrand";
    int maxId = -1; // Giá trị mặc định nếu không tìm thấy

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        if (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }

        return maxId;
    }
}
public void addSPNCC(String name, int idCTBrand, String description, String origin, int quantity, double price, String image, int idProducer, String image1, String image2, String image3) throws Exception {
    String query = "INSERT INTO spncc (name, id_ctbrand, description, updatedAt, origin, quantity, price, image, id_producer, image1, image2, image3) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, idCTBrand);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, origin);
        preparedStatement.setInt(5, quantity);
        preparedStatement.setDouble(6, price);
        preparedStatement.setString(7, image);
        preparedStatement.setInt(8, idProducer);
        preparedStatement.setString(9, image1);
        preparedStatement.setString(10, image2);
        preparedStatement.setString(11, image3);

        preparedStatement.executeUpdate();
    }
}

public boolean checkNameSPNCC(String name) throws Exception {
    String query = "SELECT COUNT(*) FROM spncc WHERE name = ?";
    
    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, name); // Thiết lập giá trị tham số

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Trả về true nếu có ít nhất một dòng trùng khớp
            }
        }
    }

    return false; // Trả về false nếu không có dòng nào trùng khớp
}
public void updateSPNCC(int id, String name, int id_ctbrand, String description, String origin, int quantity, double price, String image, int id_producer, String image1, String image2, String image3) throws Exception {
    String query = "UPDATE spncc SET name = ?, id_ctbrand = ?, description = ?, updatedAt = NOW(), origin = ?, quantity = ?, price = ?, image = ?, id_producer = ?, image1 = ?, image2 = ?, image3 = ? WHERE id = ?";

    try (Connection connection = dbUtil.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, id_ctbrand);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, origin);
        preparedStatement.setInt(5, quantity);
        preparedStatement.setDouble(6, price);
        preparedStatement.setString(7, image);
        preparedStatement.setInt(8, id_producer);
        preparedStatement.setString(9, image1);
        preparedStatement.setString(10, image2);
        preparedStatement.setString(11, image3);
        preparedStatement.setInt(12, id);

        preparedStatement.executeUpdate();
    }
}
public void deleteSPNCC(int idspncc) throws Exception {
    String query = "DELETE FROM spncc WHERE id = ?";

    try (Connection connection = dbUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, idspncc);
        preparedStatement.executeUpdate();
    }
}
    public static void main(String[] args) {
        quanlyspnccDAO dao = new quanlyspnccDAO();
        try {
           int id = 2; // Thay thế id bằng giá trị id cụ thể bạn muốn cập nhật.
            String name = "NewName"; // Thay thế NewName bằng tên mới.
            int id_ctbrand = 2; // Thay thế 2 bằng id_ctbrand mới.
            String description = "NewDescription"; // Thay thế NewDescription bằng mô tả mới.
            String origin = "NewOrigin"; // Thay thế NewOrigin bằng nguồn gốc mới.
            int quantity = 10; // Thay thế 10 bằng số lượng mới.
            double price = 99; // Thay thế 99.99 bằng giá mới.
            String fad = "new_image.jpg"; // Thay thế new_image.jpg bằng tên hình ảnh mới.
            int id_producer = 3; // Thay thế 3 bằng id_producer mới.
            String fad1 = "new_image1.jpg"; // Thay thế new_image1.jpg bằng tên hình ảnh 1 mới.
            String fad2 = "new_image2.jpg"; // Thay thế new_image2.jpg bằng tên hình ảnh 2 mới.
            String fad3 = "new_image3.jpg"; // Thay thế 
        //dao.updateSPNCC(id, name, id_ctbrand, description, origin, quantity, price, image, id_producer, image1, image2, image3);
                     //dao.addCTBrand(1, 2);
                    dao.addCTPN(id_producer, quantity, (int) price);
                    //int max_ctbrand = dao.findMaxCTBrandId();
                    //dao.addSPNCC(name, max_ctbrand, description, origin, quantity, price, fad, id_producer, fad1, fad2, fad3);

        System.err.println("thành công ");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi thêm mới.");
        }
    }
    public String getBrandName(int id_spncc) throws Exception {
    String name = ""; // Giá trị mặc định

    String query = "SELECT name FROM brand WHERE id = ?";

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
    
    public String getCategoryName(int id_spncc) throws Exception {
    String name = ""; // Giá trị mặc định

    String query = "SELECT name FROM category WHERE id = ?";

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
    
    public String getNCCName(int id_spncc) throws Exception {
    String name = ""; // Giá trị mặc định

    String query = "SELECT name FROM nhacungcap WHERE id = ?";

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

}
