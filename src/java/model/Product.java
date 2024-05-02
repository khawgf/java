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
public class Product {
    private String idSP;
    private double priceSP;
    private int quantitySP;
    private String id_spnccSP;
    private String nameSP;
    private String categorySP;
    private String brandSP;
    private String descriptionSP;
    private String imageSP;
    private String imageSP1;
    private String imageSP2;
    private String imageSP3;
    private double ratingSP;
    private int discountSP;
    private int id_producer;
    

    public Product() {
    }

    public Product(String idSP, double priceSP, int quantitySP, String id_spnccSP, String nameSP, String categorySP, String brandSP, String descriptionSP, String imageSP, String imageSP1, String imageSP2, String imageSP3, double ratingSP, int discountSP, int id_producer) {
        this.idSP = idSP;
        this.priceSP = priceSP;
        this.quantitySP = quantitySP;
        this.id_spnccSP = id_spnccSP;
        this.nameSP = nameSP;
        this.categorySP = categorySP;
        this.brandSP = brandSP;
        this.descriptionSP = descriptionSP;
        this.imageSP = imageSP;
        this.imageSP1 = imageSP1;
        this.imageSP2 = imageSP2;
        this.imageSP3 = imageSP3;
        this.ratingSP = ratingSP;
        this.discountSP = discountSP;
        this.id_producer = id_producer;
    }

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }

    public double getPriceSP() {
        return priceSP;
    }

    public void setPriceSP(double priceSP) {
        this.priceSP = priceSP;
    }

    public int getQuantitySP() {
        return quantitySP;
    }

    public void setQuantitySP(int quantitySP) {
        this.quantitySP = quantitySP;
    }

    public String getId_spnccSP() {
        return id_spnccSP;
    }

    public void setId_spnccSP(String id_spnccSP) {
        this.id_spnccSP = id_spnccSP;
    }

    public String getNameSP() {
        return nameSP;
    }

    public void setNameSP(String nameSP) {
        this.nameSP = nameSP;
    }

    public String getCategorySP() {
        return categorySP;
    }

    public void setCategorySP(String categorySP) {
        this.categorySP = categorySP;
    }

    public String getBrandSP() {
        return brandSP;
    }

    public void setBrandSP(String brandSP) {
        this.brandSP = brandSP;
    }

    public String getDescriptionSP() {
        return descriptionSP;
    }

    public void setDescriptionSP(String descriptionSP) {
        this.descriptionSP = descriptionSP;
    }

    public String getImageSP() {
        return imageSP;
    }

    public void setImageSP(String imageSP) {
        this.imageSP = imageSP;
    }

    public String getImageSP1() {
        return imageSP1;
    }

    public void setImageSP1(String imageSP1) {
        this.imageSP1 = imageSP1;
    }

    public String getImageSP2() {
        return imageSP2;
    }

    public void setImageSP2(String imageSP2) {
        this.imageSP2 = imageSP2;
    }

    public String getImageSP3() {
        return imageSP3;
    }

    public void setImageSP3(String imageSP3) {
        this.imageSP3 = imageSP3;
    }

    public double getRatingSP() {
        return ratingSP;
    }

    public void setRatingSP(double ratingSP) {
        this.ratingSP = ratingSP;
    }

    public int getDiscountSP() {
        return discountSP;
    }

    public void setDiscountSP(int discountSP) {
        this.discountSP = discountSP;
    }

    public int getId_producer() {
        return id_producer;
    }

    public void setId_producer(int id_producer) {
        this.id_producer = id_producer;
    }

}
