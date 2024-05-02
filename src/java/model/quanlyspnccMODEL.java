/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hp
 */
public class quanlyspnccMODEL {
     private int id;
     private int id_producer;
     private int id_brand;
     private int id_category;
    private String name;
    private String quantity;
    private String brand;
    private String category;
    private String description;
    private String img;
    private String price;
    private String origin;
    private String namencc;
    private String img1;
    private String img2;
    private String img3;

    public quanlyspnccMODEL() {
    }

    public quanlyspnccMODEL(int id, int id_producer, int id_brand, int id_category, String name, String quantity, String brand, String category, String description, String img, String price, String origin, String namencc, String img1, String img2, String img3) {
        this.id = id;
        this.id_producer = id_producer;
        this.id_brand = id_brand;
        this.id_category = id_category;
        this.name = name;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.img = img;
        this.price = price;
        this.origin = origin;
        this.namencc = namencc;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_producer() {
        return id_producer;
    }

    public void setId_producer(int id_producer) {
        this.id_producer = id_producer;
    }

    public int getId_brand() {
        return id_brand;
    }

    public void setId_brand(int id_brand) {
        this.id_brand = id_brand;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNamencc() {
        return namencc;
    }

    public void setNamencc(String namencc) {
        this.namencc = namencc;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    
    

    

    
}
