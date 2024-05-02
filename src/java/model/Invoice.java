package model;

import dao.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author rumi
 */
public class Invoice {

    private int Id;
    private String Phone;
    private String Address;
    private TimeUnit CreateAt;
    private TimeUnit UpdateAt;
    private int Status;

    public Invoice() {

    }

    public Invoice(int Id, String Phone, String Address, TimeUnit CreateAt, TimeUnit UpdateAt, int Status) {
        this.Id = Id;
        this.Phone = Phone;
        this.Address = Address;
        this.CreateAt = CreateAt;
        this.UpdateAt = UpdateAt;
        this.Status = Status;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public TimeUnit getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(TimeUnit CreateAt) {
        this.CreateAt = CreateAt;
    }

    public TimeUnit getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(TimeUnit UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

}
