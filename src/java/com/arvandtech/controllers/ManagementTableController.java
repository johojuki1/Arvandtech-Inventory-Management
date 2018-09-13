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
 * This class manages the 'inventory management table'. Completes all relevant
 * functions and sorting. dConverter - class used to complete conversion
 * operations of Tracked items into FuncItems. inventoryItems - stores all
 * current inventory items that is stored. displayedItems - stores all inventory
 * items that is to be displayed to the user(after sorting). maxColNum - stores
 * maximum columns to be displayed by table.
 *
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
    private String searchField;

    public ManagementTableController() {
        dConverter = new DatabaseConverter();
    }

    /*
    
    Sorting Classes- These functions determine the initial database searched and to be displayed.
    
     */
    public boolean findAll() {
        castToFuncItem(new ArrayList<>(trackedFacade.findAll()));
        displayedItems = new ArrayList<>();
        displayedItems.addAll(inventoryItems);
        return true;
    }

    public void smartSearch() {
        ArrayList<FuncItemType> tmpFinalSearchList = new ArrayList<>();
        ArrayList<FuncItemType> tmpInventoryItems = new ArrayList<>();
        ArrayList<FuncItemType> modifiedTmpInventoryItems = new ArrayList<>();

        //clear all lists(done due to memory leak issue we had.)
        tmpFinalSearchList.clear();
        tmpInventoryItems.clear();
        modifiedTmpInventoryItems.clear();

        //prepare temperory items.
        tmpInventoryItems.addAll(inventoryItems);
        modifiedTmpInventoryItems.addAll(inventoryItems);
        //Loop to check if item type or any parameters match.
        //If it does, immediately add all items into list. Then delete all items from tmpInventory, which will be used for later more detailed searches.
        for (FuncItemType item : modifiedTmpInventoryItems) {
            //check names of the item type for match.
            if (checkContainsString(item.getTypeName(), searchField) || checkContainsString(item.getAttributeNames(), searchField)) {
                //if found, add all items available to final list. Then delete the entire item from tmp.
                tmpFinalSearchList.add(item);
                tmpInventoryItems.remove(item);
            }
        }
        //Check individual items and attributes to see if any match.
        //Reset secondTmpInventoryItems to match with tmpInventoryItems. All items confirmed to be searched and found does not need to be searched again.
        modifiedTmpInventoryItems.clear();
        modifiedTmpInventoryItems.addAll(tmpInventoryItems);
        for (FuncItemType item : modifiedTmpInventoryItems) {
            for (FuncItem tmpItem : item.getItems()) {
                boolean itemFound = tmpItem.getBarcode().equals(searchField) || tmpItem.getOrderNumber().equals(searchField)||checkContainsString(tmpItem.toString(), searchField);
                if (itemFound) {
                    //if found, check if ItemType is in final searched list. If not Add to new list and also add the found item
                    int index = findTypeInList(tmpFinalSearchList, item);
                    //Adding ItemType if not found.
                    if (index == -1) {
                        FuncItemType itemToAdd = new FuncItemType();
                        itemToAdd.setId(item.getId());
                        itemToAdd.setTypeName(item.getTypeName());
                        itemToAdd.setAttributeNames(item.getAttributeNames());
                        tmpFinalSearchList.add(itemToAdd);
                        index = tmpFinalSearchList.indexOf(itemToAdd);
                    }
                    //Add new FuncItem to final list under the correct ItemType.
                    ArrayList<FuncItem> tmpFuncItems = new ArrayList<>();
                    tmpFuncItems.clear();
                    tmpFuncItems.addAll(tmpFinalSearchList.get(index).getItems());
                    tmpFuncItems.add(tmpItem);
                    tmpFinalSearchList.get(index).setItems(tmpFuncItems);
                }
            }
        }
        dConverter.sortFuncList(tmpFinalSearchList);
        displayedItems.clear();
        displayedItems.addAll(tmpFinalSearchList);
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

    //Checks the object a to see if the values of string b can be found in any form in object a.
    private boolean checkContainsString(Object a, String b) {
        //Convert object to string and capatilise both strings.
        String convertedA = String.valueOf(a).toUpperCase();
        b = b.toUpperCase();
        return convertedA.contains(b);
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

    //checks to see if an item type exists in a particular list of items.
    private int findTypeInList(ArrayList<FuncItemType> items, FuncItemType item) {
        for (FuncItemType tmpItem : items) {
            if (item.getTypeName().equals(tmpItem.getTypeName()) && item.getAttributeNames().equals(tmpItem.getAttributeNames())) {
                return items.indexOf(tmpItem);
            }
        }
        return -1;
    }

    //GETTERS
    public ArrayList<FuncItemType> getDisplayedItems() {
        return displayedItems;
    }

    public String getSearchField() {
        return searchField;
    }

    //SETTERS
    public void setDisplayedItems(ArrayList<FuncItemType> displayedItems) {
        this.displayedItems = displayedItems;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
}
