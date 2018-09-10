/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities.entities;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class FuncItemType {

    int id;
    private String typeName;
    private ArrayList<FuncItemValue> attributeNames;
    private ArrayList<FuncItem> items;

    public FuncItemType() {
        attributeNames = new ArrayList<>();
        items = new ArrayList<>();
    }

    //FUNCTIONS
    public String findAttributeNames(int i) {
        try {
            return attributeNames.get(i).getPrimary();
        } catch(Exception e) {
            return "";
        }
    }

    //GETTERS
    
    public int getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public ArrayList<FuncItemValue> getAttributeNames() {
        return attributeNames;
    }

    public ArrayList<FuncItem> getItems() {
        return items;
    }

    //SETTERS

    public void setId(int id) {
        this.id = id;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setAttributeNames(ArrayList<FuncItemValue> attributeNames) {
        this.attributeNames = attributeNames;
    }

    public void setItems(ArrayList<FuncItem> items) {
        this.items = items;
    }
}
