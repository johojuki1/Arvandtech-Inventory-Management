package com.arvandtech.utilities.entities;

/**
 * Used to display and return the correct values for dropdown menus displaying the group and location values.
 * value - value in String. Contains either string of 'group' or 'location'
 * type - type of item 'value' is. Is either group or location.
 * @author User
 */
public class FuncGroup {
    private String name;
    private String type;
    
    public FuncGroup(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    //GETTERS

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
