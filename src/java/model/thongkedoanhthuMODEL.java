/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class thongkedoanhthuMODEL {
        private String total_nhap;
    private String total_ban;
    private String total_ketqua;

    public thongkedoanhthuMODEL() {
    }

    public thongkedoanhthuMODEL(String total_nhap, String total_ban, String total_ketqua) {
        this.total_nhap = total_nhap;
        this.total_ban = total_ban;
        this.total_ketqua = total_ketqua;
    }

    public String getTotal_nhap() {
        return total_nhap;
    }

    public void setTotal_nhap(String total_nhap) {
        this.total_nhap = total_nhap;
    }

    public String getTotal_ban() {
        return total_ban;
    }

    public void setTotal_ban(String total_ban) {
        this.total_ban = total_ban;
    }

    public String getTotal_ketqua() {
        return total_ketqua;
    }

    public void setTotal_ketqua(String total_ketqua) {
        this.total_ketqua = total_ketqua;
    }

   
}
