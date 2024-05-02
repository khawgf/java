/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

public class PaymentDAO {

    public String checkQuantity(List<Integer> productID, List<Integer> quantities) throws SQLException {
        Connection conn = null;
        PreparedStatement statementSanphamChinh = null;
        PreparedStatement statementSanphamNhaCungCap = null;
        List<Integer> insufficientProducts = new ArrayList<>(); // Danh sách sản phẩm không đủ số lượng

        try {
            conn = new MySQLConnect().getConnection();
            conn.setAutoCommit(false); // Tắt chế độ tự động commit để cập nhật số lượng sản phẩm

            for (int i = 0; i < productID.size(); i++) {
                int productId = productID.get(i);
                int quantity = quantities.get(i);

                // Kiểm tra số lượng sản phẩm trong bảng sanphamchinh
                String querySanphamChinh = "SELECT spc.id_spncc AS product_id, spc.quantity AS available_quantity "
                        + "FROM sanphamchinh spc "
                        + "WHERE spc.id = ?";
                statementSanphamChinh = conn.prepareStatement(querySanphamChinh);
                statementSanphamChinh.setInt(1, productId);

                try (ResultSet rs = statementSanphamChinh.executeQuery()) {
                    if (rs.next()) {
                        int availableQuantity = rs.getInt("available_quantity");
                        if (availableQuantity < quantity) {
                            insufficientProducts.add(productId); // Thêm sản phẩm không đủ số lượng vào danh sách
                        } else {
                            // Cập nhật số lượng sản phẩm trong bảng sanphamchinh
                            int newQuantitySanphamChinh = availableQuantity - quantity;
                            String updateQuerySanphamChinh = "UPDATE sanphamchinh SET quantity = ? WHERE id = ?";
                            PreparedStatement updateStatementSanphamChinh = conn
                                    .prepareStatement(updateQuerySanphamChinh);
                            updateStatementSanphamChinh.setInt(1, newQuantitySanphamChinh);
                            updateStatementSanphamChinh.setInt(2, productId);
                            updateStatementSanphamChinh.executeUpdate();
                        }
                    } else {
                        return "Không tìm thấy sản phẩm có ID " + productId + " trong bảng sanphamchinh.";
                    }
                }

                // Kiểm tra số lượng sản phẩm trong bảng sanphamnhacungcap
                String querySanphamNhaCungCap = "SELECT snc.id AS product_id, snc.quantity AS available_quantity "
                        + "FROM spncc snc "
                        + "WHERE snc.id = ?";
                statementSanphamNhaCungCap = conn.prepareStatement(querySanphamNhaCungCap);
                statementSanphamNhaCungCap.setInt(1, productId);

                try (ResultSet rs = statementSanphamNhaCungCap.executeQuery()) {
                    if (rs.next()) {
                        int availableQuantity = rs.getInt("available_quantity");
                        if (availableQuantity < quantity) {
                            insufficientProducts.add(productId); // Thêm sản phẩm không đủ số lượng vào danh sách
                        } else {
                            // Cập nhật số lượng sản phẩm trong bảng sanphamnhacungcap
                            int newQuantitySanphamNhaCungCap = availableQuantity - quantity;
                            String updateQuerySanphamNhaCungCap = "UPDATE spncc SET quantity = ? WHERE id = ?";
                            PreparedStatement updateStatementSanphamNhaCungCap = conn
                                    .prepareStatement(updateQuerySanphamNhaCungCap);
                            updateStatementSanphamNhaCungCap.setInt(1, newQuantitySanphamNhaCungCap);
                            updateStatementSanphamNhaCungCap.setInt(2, productId);
                            updateStatementSanphamNhaCungCap.executeUpdate();
                        }
                    } else {
                        return "Không tìm thấy sản phẩm có ID " + productId + " trong bảng sanphamnhacungcap.";
                    }
                }
            }

            if (!insufficientProducts.isEmpty()) {
                // Tạo thông báo về sản phẩm không đủ số lượng
                StringBuilder insufficientProductMessage = new StringBuilder("Sản phẩm không đủ số lượng có ID: ");
                for (int productId : insufficientProducts) {
                    insufficientProductMessage.append(productId).append(", ");
                }
                insufficientProductMessage.deleteCharAt(insufficientProductMessage.length() - 2); // Xóa dấu phẩy cuối
                // cùng
                return insufficientProductMessage.toString();
            }

            conn.commit(); // Thực hiện commit để lưu các cập nhật vào cơ sở dữ liệu
            return "Thanh toán thành công";
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback nếu có lỗi xảy ra
            }
            e.printStackTrace();
            return "Lỗi khi kiểm tra số lượng sản phẩm.";
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Bật lại chế độ tự động commit
                conn.close();
            }
        }
    }

    public static void insertInvoice(String phoneNumber, String address) {
        try (Connection conn = MySQLConnect.getConnection()) {
            String getMaxIdQuery = "SELECT MAX(id) FROM hoadon";
            PreparedStatement statement = conn.prepareStatement(getMaxIdQuery);
            ResultSet rs = statement.executeQuery();

            int currentMaxId = 0;

            if (rs.next()) {
                currentMaxId = rs.getInt(1);
            }

            int newID = currentMaxId + 1;

            String sql = "INSERT INTO hoadon (id, phone, address, createdAt, updatedAt, status) VALUES (?, ?, ?, NOW(), NOW(), 1)";

            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, newID);
                st.setString(2, phoneNumber);
                st.setString(3, address);

                int rowsInserted = st.executeUpdate(); // Thực hiện câu lệnh SQL

                if (rowsInserted > 0) {
                    System.out.println("Thanh toán thành công, ID của hóa đơn mới là: " + newID);
                } else {
                    System.out.println("Thanh toán thất bại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Đăng ký tài khoản thất bại");
        }
    }

    public static void insertInvoiceDetail(int id_hoadon, List<InvoiceDetail> details) {
        try (Connection conn = MySQLConnect.getConnection()) {
            String getMaxIdQuery = "SELECT MAX(id) FROM cthd";
            PreparedStatement statement = conn.prepareStatement(getMaxIdQuery);
            ResultSet rs = statement.executeQuery();

            int currentMaxId = 0;

            if (rs.next()) {
                currentMaxId = rs.getInt(1);
            }

            currentMaxId++;
            String sql = "INSERT INTO cthd (id, id_hoadon, id_spc, id_user, quantity, price) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (InvoiceDetail detail : details) {
                    st.setInt(1, currentMaxId);
                    st.setInt(2, id_hoadon);
                    st.setInt(3, detail.getId_spc());
                    st.setInt(4, detail.getId_user());
                    st.setInt(5, detail.getQuantity());
                    st.setInt(6, detail.getPrice());
                    st.addBatch();

                    currentMaxId++;
                }

                int[] rowsInserted = st.executeBatch(); // Thực hiện câu lệnh SQL

                if (rowsInserted.length == details.size()) {
                    System.out.println("Thêm chi tiết hóa đơn thành công:");
                } else {
                    System.out.println("Thêm chi tiết hóa đơn thất bại.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Thêm chi tiết hóa đơn thất bại");
        }
    }

    public static int getIdInvoiceByPhoneNumber(String phoneNumber) {
        try (Connection conn = MySQLConnect.getConnection()) {
            String sql = "SELECT id FROM hoadon WHERE phone = ? ORDER BY id DESC LIMIT 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, phoneNumber);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
