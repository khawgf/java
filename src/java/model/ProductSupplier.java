/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class ProductSupplier {
    private String id;
    private String name;
    private int type_id;
    private String description;
    private Date updatedAt;
    private String origin;
    private int quantity;
    private double price;
    private String image;
    private String id_producer;

    public ProductSupplier() {
    }

    public ProductSupplier(String id, String name, int type_id, String description, Date updatedAt, String origin, int quantity, double price, String image, String id_producer) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.description = description;
        this.updatedAt = updatedAt;
        this.origin = origin;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.id_producer = id_producer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updateAt) {
        this.updatedAt = updateAt;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_producer() {
        return id_producer;
    }

    public void setId_producer(String id_producer) {
        this.id_producer = id_producer;
    }
}
