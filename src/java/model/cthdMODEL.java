/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class cthdMODEL {
    private String phoneKH;
    private String address;
    private String namesp;
    private String quantity;
    private String img;
    private String price;
    private String namekh;

    public cthdMODEL() {
    }

    public cthdMODEL(String phoneKH, String address, String namesp, String quantity, String img, String price, String namekh) {
        this.phoneKH = phoneKH;
        this.address = address;
        this.namesp = namesp;
        this.quantity = quantity;
        this.img = img;
        this.price = price;
        this.namekh = namekh;
    }

    public String getPhoneKH() {
        return phoneKH;
    }

    public void setPhoneKH(String phoneKH) {
        this.phoneKH = phoneKH;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNamesp() {
        return namesp;
    }

    public void setNamesp(String namesp) {
        this.namesp = namesp;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNamekh() {
        return namekh;
    }

    public void setNamekh(String namekh) {
        this.namekh = namekh;
    }

}
