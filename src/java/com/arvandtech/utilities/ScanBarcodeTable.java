/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

import com.arvandtech.domain.entities.inventory.Tracked;

/**
 * Class used to store and display items in the scan barcode table. 
 * id: id of item in table.
 * barcode: barcode assigned to item.
 * condition: current condition of item. Is derived form selectable list.
 * description: description written by user concerning item.
 * descriptionExists: is "yes" or "no" tells user if description has been added to item.
 * backgroundColour: Colour displayed on the background of the row of this item. Used to tell user of there is duplicate barcode in table.
 * @author User
 */
public class ScanBarcodeTable {
    int id;
    String barcode;
    String itemType;
    String status;
    String condition;
    String description;
    String descriptionExists;
    String backgroundColour;
    
    public ScanBarcodeTable() {
        
    }
    
    public ScanBarcodeTable(Tracked item) {
        id = item.getTrackedId();
        barcode = item.getBarcode();
        itemType = item.getItemTypeName();
        status = item.getStatus();
        condition = item.getItemCondition();
        description = item.getDescription();
        if (description.isEmpty()) {
            descriptionExists = "No";
        } else {
            descriptionExists = "yes";
        }
        backgroundColour = "";
    }
    
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

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public String getItemType() {
        return itemType;
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

    public void setBackgroundColour(String backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
}
