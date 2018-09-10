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

    private int id;
    private String barcode;
    private Date dateAdded;
    private String condition;
    private String orderNumber;
    private String status;
    private String description;
    private ArrayList<FuncItemValue> itemValues;
    private int typeId;

    //Constructor used to set all initial values.
    public FuncItem(int id, String barcode, Date dateAdded, String condition, String orderNumber, String status, String description) {
        this.id = id;
        this.barcode = barcode;
        this.dateAdded = dateAdded;
        this.condition = condition;
        this.orderNumber = orderNumber;
        this.status = status;
        this.description = description;
        itemValues = new ArrayList<>();
    }

    //FUNCTIONS
    //Adds a new FuncItemValue into currently list of values.
    public void addItemValue(FuncItemValue item) {
        itemValues.add(item);
    }

    //Finds primary value based on number in Array
    public String findPrimaryValue(int i) {
        try {
            return itemValues.get(i).getPrimary();
        } catch (Exception e) {
            return "";
        }
    }

    //GETTERS
    public int getId() {
        return id;
    }

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

    public int getTypeId() {
        return typeId;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

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

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
