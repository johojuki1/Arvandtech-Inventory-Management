/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.Tracked;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.utilities.DatabaseConverter;
import com.arvandtech.utilities.entities.FuncItem;
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
    private ArrayList<Integer> typeRowSpan;

    public ManagementTableController() {
        dConverter = new DatabaseConverter();
        typeRowSpan = new ArrayList<>();
    }

    public boolean findAll() {
        ArrayList<Tracked> trackedItems = new ArrayList<>(trackedFacade.findAll());
        inventoryItems = dConverter.sortToFunc(trackedItems);
        int maxRowSpan = findMaxRowSpan(inventoryItems);
        typeRowSpan = new ArrayList<>();
        for (FuncItemType item : inventoryItems) {
            int tmpAttSize = item.getAttributeNames().size();
            double tmpDouble = maxRowSpan / tmpAttSize;
            typeRowSpan.add((int) (tmpDouble));
        }
        return true;
    }

    /*
     Function finds the how large each row ought to be for a particular item.
     */
    public int findRowSpan(int i) {
        return typeRowSpan.get(i);
    }

    
    public boolean columnRendered2(int colNo, FuncItemType item) {
        if (colNo > item.getAttributeNames().size() - 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean columnRendered(int colNo, FuncItemType item) {
        if (colNo > item.getAttributeNames().size() - 1) {
            return false;
        } else {
            return true;
        }
    }

    private FuncItemType findItemFromId(int id) {
        for (FuncItemType item : inventoryItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    /*Takes a list of FunctItemType and finds the largest numbers of attributes within all contained items.
    The largest number of attributes is returned.
     */
    private int findMaxRowSpan(ArrayList<FuncItemType> items) {
        int tempMaxRow = 0;
        for (FuncItemType item : items) {
            if (item.getAttributeNames().size() > tempMaxRow) {
                tempMaxRow = item.getAttributeNames().size();
            }
        }
        return tempMaxRow;
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
