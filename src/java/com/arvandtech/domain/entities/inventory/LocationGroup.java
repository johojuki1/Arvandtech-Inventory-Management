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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "LocationGroup")
@NamedQuery(
        name = "LocationGroup.findObject",
        query = "SELECT a FROM LocationGroup a WHERE a.locationGroup LIKE :group"
)
public class LocationGroup implements Serializable{
    private int id;
    private String locationGroup;
    private String location;
    
    public LocationGroup() {
        id = 0;
        locationGroup = "Unknown";
        location = "Unknown";
    }
    
    //GETTERS
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getLocationGroup() {
        return locationGroup;
    }

    public String getLocation() {
        return location;
    }
    
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setLocationGroup(String locationgroup) {
        this.locationGroup = locationgroup;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
