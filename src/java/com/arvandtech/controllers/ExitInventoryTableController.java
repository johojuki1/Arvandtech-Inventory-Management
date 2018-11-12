/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.inventory.Tracked;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.utilities.DatabaseConverter;
import com.arvandtech.utilities.entities.FuncItem;
import com.arvandtech.utilities.entities.FuncItemType;
import com.arvandtech.utilities.entities.FuncItemValue;
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

    private String searchType;
    private DatabaseConverter dConverter;
    private ArrayList<FuncItemType> inventoryItems;
    private ArrayList<FuncItemType> displayedItems;
    private int maxColNum;
    private String searchField;
    private ArrayList<String> itemType;
    private ArrayList<String> itemAttribute;
    private ArrayList<String> itemValue;
    private ArrayList<String> selectedItemType;
    private ArrayList<String> selectedItemAttribute;
    private ArrayList<String> selectedItemValue;

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
        itemAttribute = new ArrayList<>();
        itemType = new ArrayList<>();
        itemValue = new ArrayList<>();
        selectedItemAttribute = new ArrayList<>();
        selectedItemType = new ArrayList<>();
        selectedItemValue = new ArrayList<>();
        findItemType(inventoryItems);
        findItemAttribute(inventoryItems);
        return true;
    }

    /*
    Function used to find all values in itemtype and sets value 'itemType' when triggered.
    
     */
    private void findItemType(ArrayList<FuncItemType> searchedItemTypes) {
        for (FuncItemType item : searchedItemTypes) {
            //check names of the item type for match.
            if (!itemType.contains(item.getTypeName())) {
                itemType.add(item.getTypeName());
            }
        }
    }

    /*
    Used to find all attribute values in 'item type' and registers all unique attribute values.
    */
    private void findItemAttribute(ArrayList<FuncItemType> searchedItemTypes) {
        for (FuncItemType item : searchedItemTypes) {
            //check names of the item attribute for match.
            for (String attributeName : item.getAttributeNames()) {
                if (!itemAttribute.contains(attributeName)) {
                    itemAttribute.add(attributeName);
                }
            }
        }
    }
    
    /*
    
    */

    private void findItemAttribute(FuncItemType searchedItem) {
        //check names of the item attribute for match.
        for (String attributeName : searchedItem.getAttributeNames()) {
            if (!itemAttribute.contains(attributeName)) {
                itemAttribute.add(attributeName);
            }
        }
    }

    private void findItemValue(FuncItem searchedItem, int valLocation) {
        //check names of the item values for match.
        if (!itemValue.contains(searchedItem.getItemValues().get(valLocation).getPrimary())) {
            itemValue.add(searchedItem.getItemValues().get(valLocation).getPrimary());
        }
    }

    public void itemTypeChange() {
        itemAttribute.clear();
        selectedItemAttribute.clear();
        itemValue.clear();
        selectedItemValue.clear();

        if (selectedItemType.isEmpty()) {
            findItemAttribute(inventoryItems);
        }
        for (String type : selectedItemType) {
            for (FuncItemType item : inventoryItems) {
                //check names of the item type for match.
                if (item.getTypeName().equals(type)) {
                    findItemAttribute(item);
                }
            }
        }
    }

    public void attributeTypeChange() {
        itemValue.clear();
        selectedItemValue.clear();

        ArrayList<String> tmpItemType;
        if (selectedItemType.isEmpty()) {
            tmpItemType = itemType;
        } else {
            tmpItemType = selectedItemType;
        }

        for (FuncItemType item : inventoryItems) {
            for (String type : tmpItemType) {
                for (String attribute : selectedItemAttribute) {
                    //check names of the item type for match.
                    if (item.getTypeName().equals(type)) {
                        if (item.getAttributeNames().contains(attribute)) {
                            int attId = item.getAttributeNames().indexOf(attribute);
                            for (FuncItem iItem : item.getItems()) {
                                findItemValue(iItem, attId);
                            }
                        }
                    }
                }
            }
        }
    }

    public String valueCheckBoxDisplayed() {
        if (selectedItemAttribute.isEmpty()) {
            return "hidden";
        }
        return "visible";
    }
    
    public void findAllAndSearch() {
        castToFuncItem(new ArrayList<>(trackedFacade.findAll()));
        search();
    }

    public void search() {
        boolean isAndSearch = false;
        if (searchType.equals("and")) {
            isAndSearch = true;
        }
        ArrayList<FuncItemType> interSearchArray = new ArrayList<>();
        interSearchArray.clear();
        interSearchArray.addAll(searchByType(inventoryItems, selectedItemType));
        if (interSearchArray.isEmpty()) {
            interSearchArray.addAll(inventoryItems);
        }
        if (!selectedItemAttribute.isEmpty()) {
            interSearchArray = searchByAttribute(interSearchArray, selectedItemAttribute, isAndSearch);
        }
        if (!selectedItemValue.isEmpty()) {
            interSearchArray = searchByValue(interSearchArray, selectedItemValue, isAndSearch);
        }
        if (!searchField.isEmpty()) {
            interSearchArray = searchByField(interSearchArray, searchField);
        }
        displayedItems.clear();
        displayedItems.addAll(interSearchArray);
        maxColNum = findMaxColSpan(displayedItems);
    }

    private ArrayList<FuncItemType> searchByValue(ArrayList<FuncItemType> items, ArrayList<String> values, boolean isAndSearch) {
        ArrayList<FuncItemType> interSearchArray = new ArrayList<>();
        for (FuncItemType item : items) {
            for (FuncItem tmpItem : item.getItems()) {
                boolean boolAddItem = false;
                if (isAndSearch && andSearchByValue(tmpItem, values)) {
                    boolAddItem = true;
                } else if (!isAndSearch && orSearchByValue(tmpItem, values)) {
                    boolAddItem = true;
                }
                if (boolAddItem) {
                    int index = findTypeInList(interSearchArray, item);
                    if (index == -1) {
                        FuncItemType itemToAdd = new FuncItemType();
                        itemToAdd.setId(item.getId());
                        itemToAdd.setTypeName(item.getTypeName());
                        itemToAdd.setAttributeNames(item.getAttributeNames());
                        interSearchArray.add(itemToAdd);
                        index = interSearchArray.indexOf(itemToAdd);
                    }
                    //Add new FuncItem to final list under the correct ItemType.
                    ArrayList<FuncItem> tmpFuncItems = new ArrayList<>();
                    tmpFuncItems.clear();
                    tmpFuncItems.addAll(interSearchArray.get(index).getItems());
                    tmpFuncItems.add(tmpItem);
                    interSearchArray.get(index).setItems(tmpFuncItems);
                }
            }

        }

        return interSearchArray;
    }

    private boolean andSearchByValue(FuncItem funcItem, ArrayList<String> values) {
        for (String value : values) {
            boolean boolValueFound = false;
            for (FuncItemValue tmpItemValue : funcItem.getItemValues()) {
                if (tmpItemValue.getPrimary().equals(value)) {
                    boolValueFound = true;
                }
            }
            if (!boolValueFound) {
                return false;
            }
        }
        return true;
    }

    private boolean orSearchByValue(FuncItem funcItem, ArrayList<String> values) {
        for (FuncItemValue tmpItemValue : funcItem.getItemValues()) {
            if (values.contains(tmpItemValue.getPrimary())) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<FuncItemType> searchByAttribute(ArrayList<FuncItemType> items, ArrayList<String> attributes, boolean isAndSearch) {
        ArrayList<FuncItemType> interSearchArray = new ArrayList<>();
        for (FuncItemType item : items) {
            if (isAndSearch && andSearchByAttribute(item, attributes)) {
                interSearchArray.add(item);
            } else if (!isAndSearch && orSearchByAttribute(item, attributes)) {
                interSearchArray.add(item);
            }
        }
        return interSearchArray;
    }

    private boolean orSearchByAttribute(FuncItemType item, ArrayList<String> attributes) {
        for (String attribute : attributes) {
            if (item.getAttributeNames().contains(attribute)) {
                return true;
            }
        }
        return false;
    }

    private boolean andSearchByAttribute(FuncItemType item, ArrayList<String> attributes) {
        for (String attribute : attributes) {
            if (item.getAttributeNames().contains(attribute)) {
            } else {
                return false;
            }
        }
        return true;
    }

    private ArrayList<FuncItemType> searchByType(ArrayList<FuncItemType> items, ArrayList<String> types) {
        ArrayList<FuncItemType> interSearchArray = new ArrayList<>();
        for (FuncItemType item : items) {
            if (types.contains(item.getTypeName())) {
                interSearchArray.add(item);
            }
        }
        return interSearchArray;
    }

    private ArrayList<FuncItemType> searchByField(ArrayList<FuncItemType> searchItemList, String searchField) {
        ArrayList<FuncItemType> tmpFinalSearchList = new ArrayList<>();
        ArrayList<FuncItemType> tmpInventoryItems = new ArrayList<>();
        ArrayList<FuncItemType> modifiedTmpInventoryItems = new ArrayList<>();

        //clear all lists(done due to memory leak issue we had.)
        tmpFinalSearchList.clear();
        tmpInventoryItems.clear();
        modifiedTmpInventoryItems.clear();

        //prepare temperory items.
        tmpInventoryItems.addAll(searchItemList);
        modifiedTmpInventoryItems.addAll(searchItemList);
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
                boolean itemFound = tmpItem.getBarcode().equals(searchField) || tmpItem.getOrderNumber().equals(searchField) || checkContainsString(tmpItem.toString(), searchField);
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
        dConverter.sort(tmpFinalSearchList);
        return tmpFinalSearchList;
    }

    /*
    
    Functional Classes- These classes are used for core functions of the Management Controller.
    
     */
    //This function caasts the Tracked object into the FunctItemType. Also sets maxColNum
    private void castToFuncItem(ArrayList<Tracked> trackedItems) {
        inventoryItems = dConverter.convertToFuncItemMultiple(trackedItems);
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
    public String getSearchType() {
        return searchType;
    }

    public ArrayList<FuncItemType> getDisplayedItems() {
        return displayedItems;
    }

    public String getSearchField() {
        return searchField;
    }

    public ArrayList<String> getItemType() {
        return itemType;
    }

    public ArrayList<String> getItemAttribute() {
        return itemAttribute;
    }

    public ArrayList<String> getSelectedItemType() {
        return selectedItemType;
    }

    public ArrayList<String> getSelectedItemAttribute() {
        return selectedItemAttribute;
    }

    public ArrayList<String> getSelectedItemValue() {
        return selectedItemValue;
    }

    public ArrayList<String> getItemValue() {
        return itemValue;
    }

    //SETTERS
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setDisplayedItems(ArrayList<FuncItemType> displayedItems) {
        this.displayedItems = displayedItems;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public void setItemType(ArrayList<String> itemType) {
        this.itemType = itemType;
    }

    public void setItemAttribute(ArrayList<String> itemAttribute) {
        this.itemAttribute = itemAttribute;
    }

    public void setSelectedItemType(ArrayList<String> selectedItemType) {
        this.selectedItemType = selectedItemType;
    }

    public void setSelectedItemAttribute(ArrayList<String> selectedItemAttribute) {
        this.selectedItemAttribute = selectedItemAttribute;
    }

    public void setSelectedItemValue(ArrayList<String> selectedItemValue) {
        this.selectedItemValue = selectedItemValue;
    }

    public void setItemValue(ArrayList<String> itemValue) {
        this.itemValue = itemValue;
    }
}
