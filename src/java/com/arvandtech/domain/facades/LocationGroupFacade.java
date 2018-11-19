/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.inventory.LocationGroup;
import com.arvandtech.utilities.Settings;
import com.arvandtech.utilities.entities.FuncGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class LocationGroupFacade extends AbstractFacade<LocationGroup> {

    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void addOrEdit(String group, String location) {
        ArrayList<LocationGroup> foundItems = findLocationFromGroup(group); //Find item with group
        if (foundItems.isEmpty()) {
            LocationGroup locationGroup = new LocationGroup();
            locationGroup.setLocationGroup(group);
            locationGroup.setLocation(location);
            create(locationGroup);
        } else {
            LocationGroup locationGroup = foundItems.get(0);
            locationGroup.setLocation(location);
            edit(locationGroup);
        }
    }

    public LocationGroup returnAndAdd(String group) {
        ArrayList<LocationGroup> foundItems = findLocationFromGroup(group); //Find item with group
        LocationGroup locationGroup;
        if (foundItems.isEmpty()) {
            locationGroup = new LocationGroup();
            locationGroup.setLocationGroup(group);
            locationGroup.setLocation("Unknown");
            create(locationGroup);
        } else {
            locationGroup = foundItems.get(0);
        }
        return (locationGroup);
    }

    public String findLocation(String group) {
        ArrayList<LocationGroup> foundItems = findLocationFromGroup(group);
        if (foundItems.isEmpty()) {
            return "Unknown";
        } else {
            return foundItems.get(0).getLocation();
        }
    }

    //Returns list of groups set in FuncGroup format.
    public HashMap<String, String> returnFuncGroup() {
        HashMap<String, String> returnItems = new LinkedHashMap();
        for (String group : new Settings().getGroups()) {
            returnItems.put("       " + group, "group" + group);
        }
        return returnItems;
    }

    //Returns list of locations set in FuncGorup format
    public HashMap<String, String> returnFuncLocation() {
        HashMap<String, String> returnItems = new LinkedHashMap();
        for (String location : new Settings().getLocations()) {
            returnItems.put("       " + location, "location" + location);
        }
        return returnItems;
    }

    private ArrayList<LocationGroup> findLocationFromGroup(String group) {
        ArrayList<LocationGroup> foundItems = new ArrayList<>();
        foundItems.addAll(em.createNamedQuery("LocationGroup.findObject")
                .setParameter("group", group)
                .getResultList());
        return foundItems;
    }

    public LocationGroupFacade() {
        super(LocationGroup.class);
    }

}
