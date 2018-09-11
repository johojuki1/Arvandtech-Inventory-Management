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
 * This class manages the 'inventory management table'. Completes all relevant functions and sorting.
 * dConverter - class used to complete conversion operations of Tracked items into FuncItems.
 * inventoryItems - stores all current inventory items that is stored.
 * displayedItems - stores all inventory items that is to be displayed to the user(after sorting).
 * maxColNum - stores maximum columns to be displayed by table.
 * @author Jonathan
 */
@Named("manageTable")
@SessionScoped
public class ManagementTableController implements Serializable {

    @EJB
    private TrackedFacade trackedFacade;

    private DatabaseConverter dConverter;
    private ArrayList<FuncItemType> inventoryItems;
    private ArrayList<FuncItemType> displayedItems;
    private int maxColNum;

    public ManagementTableController() {
        dConverter = new DatabaseConverter();
    }

    /*
    
    Sorting Classes- These functions determine the initial database searched and to be displayed.
    
     */
    public boolean findAll() {
        castToFuncItem(new ArrayList<>(trackedFacade.findAll()));
        displayedItems = inventoryItems;
        return true;
    }

    /*
    
    Functional Classes- These classes are used for core functions of the Management Controller.
    
     */
    
    //This function caasts the Tracked object into the FunctItemType. Also sets maxColNum
    private void castToFuncItem(ArrayList<Tracked> trackedItems) {
        inventoryItems = dConverter.sortToFunc(trackedItems);
        maxColNum = findMaxColSpan(inventoryItems);
    }

    //Determines if a column is to be rendered on the tabled. Based on maxColNum.
    public boolean columnRendered(int colNo) {
        if (maxColNum - 1 < colNo) {
            return false;
        } else {
            return true;
        }
    }

    /*Takes a list of FunctItemType and finds the largest numbers of attributes within all contained items.
    The largest number of attributes is returned.
     */
    private int findMaxColSpan(ArrayList<FuncItemType> items) {
        int colNum = 0;
        for (FuncItemType item : items) {
            if (item.getAttributeNames().size() > colNum) {
                colNum = item.getAttributeNames().size();
            }
        }
        return colNum;
    }

    //GETTERS
    public ArrayList<FuncItemType> getDisplayedItems() {
        return displayedItems;
    }

    //SETTERS
    public void setDisplayedItems(ArrayList<FuncItemType> displayedItems) {
        this.displayedItems = displayedItems;
    }
}
