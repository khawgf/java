package dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nguyen Quoc Hung
 */
public class InvoiceDetail {
    private int id;
    private int id_hoadon;
    private int id_spc;
    private int id_user;
    private int quantity;
    private int price;
    
    public InvoiceDetail(){
        
    }

    public InvoiceDetail(int id_spc, int id_user, int quantity, int price) {
        this.id_spc = id_spc;
        this.id_user = id_user;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hoadon() {
        return id_hoadon;
    }

    public void setId_hoadon(int id_hoadon) {
        this.id_hoadon = id_hoadon;
    }

    public int getId_spc() {
        return id_spc;
    }

    public void setId_spc(int id_spc) {
        this.id_spc = id_spc;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
