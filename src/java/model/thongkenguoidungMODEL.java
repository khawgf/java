/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class thongkenguoidungMODEL {
    private String phoneUser;
    private String nameUser;
    private String totalOrder;
    private String totalCash;

    public thongkenguoidungMODEL() {
    }

    public thongkenguoidungMODEL(String phoneUser, String nameUser, String totalOrder, String totalCash) {
        this.phoneUser = phoneUser;
        this.nameUser = nameUser;
        this.totalOrder = totalOrder;
        this.totalCash = totalCash;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }
    
    
    
}
