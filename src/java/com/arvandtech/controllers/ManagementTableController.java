/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.Tracked;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.utilities.DatabaseConverter;
import com.arvandtech.utilities.entities.FuncItemType;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named("manageTable")
@SessionScoped
public class ManagementTableController implements Serializable {

    @EJB
    private TrackedFacade trackedFacade;

    private DatabaseConverter dConverter;
    private ArrayList<FuncItemType> inventoryItems;

    public ManagementTableController() {
        dConverter = new DatabaseConverter();
    }

    public boolean findAll() {
        ArrayList<Tracked> trackedItems = new ArrayList<>(trackedFacade.findAll());
        inventoryItems = dConverter.sortToFunc(trackedItems);
        return true;
    }

    //GETTERS
    public ArrayList<FuncItemType> getInventoryItems() {
        return inventoryItems;
    }

    //SETTERS
    public void setInventoryItems(ArrayList<FuncItemType> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
