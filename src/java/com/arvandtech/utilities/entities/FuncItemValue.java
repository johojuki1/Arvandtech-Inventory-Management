/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities.entities;

/**
 *
 * @author User
 */
public class FuncItemValue {

    private String primary;
    private String secondaryType;
    private String secondary;

    //determines what happens if this item is converted to string.
    @Override
    public String toString() {
        return primary + " " + secondaryType + " " + secondary + " ";
    }

    //GETTERS
    public String getPrimary() {
        return primary;
    }

    public String getSecondaryType() {
        return secondaryType;
    }

    public String getSecondary() {
        return secondary;
    }

    //SETTERS
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

}
