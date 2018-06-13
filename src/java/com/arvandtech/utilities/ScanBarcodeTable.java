/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

/**
 *
 * @author User
 */
public class ScanBarcodeTable {
    int id;
    String barcode;
    String status;
    String condition;
    String description;
    String descriptionExists;
    
    //GETTERS

    public int getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getStatus() {
        return status;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionExists() {
        return descriptionExists;
    }
    
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDescriptionExists(String descriptionExists) {
        this.descriptionExists = descriptionExists;
    }

    
}
