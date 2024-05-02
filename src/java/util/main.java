/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dao.loginDAO;
import dao.quanlyhoadonDAO;
import dao.quanlymggDAO;
import dao.quanlyquyenDAO;
import dao.quanlyuserDAO;
import dao.thongkeSPDAO;
import dao.thongkedoanhthuDAO;
import dao.updateInfoDAO;
import java.util.List;
import model.cthdMODEL;
import model.loginMODEL;
import model.quanlyhoadonMODEL;
import model.quanlymggMODEL;
import model.quanlyquyenMODEL;
import model.quanlyuserMODEL;
import model.thongkedoanhthuMODEL;
import model.thongkesanphamMODEL;

/**
 *
 * @author DELL
 */
public class main {
  public static void main(String[] args) {
        // Replace with actual username and password for testing
        String userName = "0789123789";
        String password = "123456";

        // Create an instance of the class containing the checkLoginAndGetUserName method
        loginDAO loginDAO = new loginDAO();

        try {
            // Call the checkLoginAndGetUserName method
            loginMODEL user = loginDAO.checkLoginAndGetUserName(userName, password);

            // Check the result
            if (user != null) {
                System.out.println("Login successful:");
                System.out.println("ID: " + user.getId());
                System.out.println("Phone: " + user.getPhone());
                System.out.println("Name: " + user.getName());
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
