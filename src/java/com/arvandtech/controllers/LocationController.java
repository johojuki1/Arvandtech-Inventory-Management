/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.facades.LocationGroupFacade;
import com.arvandtech.utilities.Settings;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named("location")
@SessionScoped
public class LocationController implements Serializable {

    @EJB
    private LocationGroupFacade locationFacade;
    
    private String group;
    private String loc;

    public List<String> getGroups() {
        return new Settings().getGroups();
    }
    
    public List<String> getLocations() {
        return new Settings().getLocations();
    }
    
    public void listener() {
        loc = locationFacade.findLocation(group);
    }
    
    public void set() {
        locationFacade.addOrEdit(group, loc);
    }

    //GETTERS
    public String getGroup() {
        return group;
    }

    public String getLoc() {
        return loc;
    }
    

    //SETTERS
    public void setGroup(String group) {
        this.group = group;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
