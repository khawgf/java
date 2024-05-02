/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

/**
 *
 * @author ASUS
 */
public class InvoiceDAO {

    public static int[] getIdsInvoiceBByPhoneNumber(String phoneNumber) {
        List<Integer> idList = new ArrayList<>();

        try (Connection conn = MySQLConnect.getConnection()) {
            String sql = "SELECT id FROM hoadon WHERE phone = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, phoneNumber);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                idList.add(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list to an array
        int[] idArray = new int[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            idArray[i] = idList.get(i);
        }

        return idArray;
    }

    public static Invoice getInvoiceById(int invoiceId) {
        Invoice invoice = null;

        try (Connection conn = MySQLConnect.getConnection()) {
            String sql = "SELECT * FROM hoadon WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, invoiceId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                // Tạo đối tượng Invoice
                invoice = new Invoice();
                // Lấy thông tin từ ResultSet và tạo đối tượng Invoice
                invoice.setId(rs.getInt("id"));
                invoice.setPhone(rs.getString("phone"));
                invoice.setAddress(rs.getString("address"));    
                invoice.setStatus(rs.getInt("status"));
                // Lấy thêm các trường khác cần thiết từ ResultSet

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return invoice;
    }

    public static int[] getProductIDsByInvoice(int invoiceId) {
        List<Integer> idList = new ArrayList<>();

        try (Connection conn = MySQLConnect.getConnection()) {
            String sql = "SELECT id_spc FROM cthd WHERE id_hoadon = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, Integer.toString(invoiceId));
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_spc");
                idList.add(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert the list to an array
        int[] idArray = new int[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            idArray[i] = idList.get(i);
        }

        return idArray;
    }

    public static int getProductQuantityByInvoice(int productId, int invoiceId) {
        try (Connection conn = MySQLConnect.getConnection()) {
            String sql = "SELECT quantity FROM cthd WHERE id_hoadon = ? AND id_spc = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, invoiceId);
            statement.setInt(2, productId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                return quantity;
            } else {
                // Handle the case where no matching record is found
                return 0; // or another appropriate default value
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Indicate an error condition
    }

}
