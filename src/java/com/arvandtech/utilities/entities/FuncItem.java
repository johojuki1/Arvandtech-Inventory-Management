/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities.entities;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author User
 */
public class FuncItem {
    private String barcode;
    private Date dateAdded;
    private String condition;
    private String orderNumber;
    private String status;
    private String description;
    private ArrayList<FuncItemValue> itemValues;
    
    
    //GETTERS

    public String getBarcode() {
        return barcode;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getCondition() {
        return condition;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<FuncItemValue> getItemValues() {
        return itemValues;
    }

    //SETTERS
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemValues(ArrayList<FuncItemValue> itemValues) {
        this.itemValues = itemValues;
    }
    
    
}
