/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities.inventory;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "LocationGroup")
public class LocationGroup implements Serializable{
    private int id;
    private String group;
    private String location;
    
    //GETTERS
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public String getLocation() {
        return location;
    }
    
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    
}
