/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class MggToProductMODEL {
    private int id;
    private String id_product;
    private String id_mgg;

    public MggToProductMODEL() {
    }

    public MggToProductMODEL(int id, String id_product, String id_mgg) {
        this.id = id;
        this.id_product = id_product;
        this.id_mgg = id_mgg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_mgg() {
        return id_mgg;
    }

    public void setId_mgg(String id_mgg) {
        this.id_mgg = id_mgg;
    }
    
    
}
