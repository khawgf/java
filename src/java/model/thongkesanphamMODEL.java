package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
public class thongkesanphamMODEL {
    private String namesp;
    private String brand;
    private String quantity;
    private String price;

    public thongkesanphamMODEL() {
    }

    public thongkesanphamMODEL(String namesp, String brand, String quantity, String price) {
        this.namesp = namesp;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
    }

    public String getNamesp() {
        return namesp;
    }

    public void setNamesp(String namesp) {
        this.namesp = namesp;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

  
}
