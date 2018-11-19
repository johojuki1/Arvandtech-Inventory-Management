/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

import com.arvandtech.domain.entities.inventory.Tracked;
import com.arvandtech.domain.entities.inventory.TrackedItem;
import com.arvandtech.domain.entities.outinventory.OutTracked;
import com.arvandtech.domain.entities.outinventory.OutTrackedItem;
import com.arvandtech.utilities.entities.FuncItem;
import com.arvandtech.utilities.entities.FuncItemType;
import com.arvandtech.utilities.entities.FuncItemValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author User
 */
public class DatabaseConverter {

    /*
    This function takes in a list of 'Tracked' database items. It will sort the list into classes and cast all itmes into a more usable format.
    All website functions should use the output format as opposed to directly from database as it is easier to manage and use.
     */
    public ArrayList<FuncItemType> convertToFuncItemMultiple(ArrayList<Tracked> databaseItem) {
        ArrayList<FuncItemType> items = new ArrayList<>();
        for (Tracked dItem : databaseItem) {
            int i = findItemInArray(dItem, items);
            /*If 'dItem''s class cannot be found in list of 'items', create a new class within list 'items'*/
            if (i == -1) {
                FuncItemType newItem = new FuncItemType();
                //set type name.
                newItem.setTypeName(dItem.getItemTypeName());
                ArrayList<String> newAtt = new ArrayList<>();
                //set all attributes, both primary and secondary.
                for (TrackedItem dAtt : dItem.getAttributes()) {
                    newAtt.add(dAtt.getAttribute().getAttributeName());
                }
                newItem.setAttributeNames(newAtt);
                newItem.setId(items.size());
                items.add(newItem);
                //assign i with the index of the item just created.
                i = items.indexOf(newItem);
            }
            //add item into array based on index i.
            FuncItem newfItem = new FuncItem(dItem.getTrackedId(), dItem.getBarcode(), dItem.getDateAdded(), dItem.getItemCondition(), dItem.getOrderNum(), dItem.getStatus(), dItem.getDescription());
            try {
                newfItem.setGroup(dItem.getLocation().getLocationGroup());
                newfItem.setLocation(dItem.getLocation().getLocation());
            } catch (Exception e) {
                newfItem.setGroup("");
                newfItem.setLocation("");
            }
            //Set attributes of the class in the correct place, in correct order for 'newfItem'.
            for (String fName : items.get(i).getAttributeNames()) {
                for (TrackedItem dAtt : dItem.getAttributes()) {
                    if (fName.equals(dAtt.getAttribute().getAttributeName())/* && fName.getSecondary().equals(dAtt.getAttribute().getSecondaryName())*/) {
                        FuncItemValue tmpFuncValue = new FuncItemValue();
                        tmpFuncValue.setPrimary(dAtt.getAttribute().getAttributeValue());
                        tmpFuncValue.setSecondary(dAtt.getAttribute().getSecondaryValue());
                        tmpFuncValue.setSecondaryType(dAtt.getAttribute().getSecondaryName());
                        newfItem.addItemValue(tmpFuncValue);
                    }
                }
            }
            //Add the new item into final 'items' list.
            items.get(i).getItems().add(newfItem);
        }
        Collections.sort(items, sortItemsByType);
        return items;
    }

    /*
    This function takes in a list of 'OutTracked' database items. It will sort the list into classes and cast all itmes into a more usable format.
    All website functions should use the output format as opposed to directly from database as it is easier to manage and use.
     */
    public ArrayList<FuncItemType> convertOutToFuncItemMultiple(ArrayList<OutTracked> databaseItem) {
        ArrayList<FuncItemType> items = new ArrayList<>();
        for (OutTracked dItem : databaseItem) {
            int i = findItemInArray(dItem, items);
            /*If 'dItem''s class cannot be found in list of 'items', create a new class within list 'items'*/
            if (i == -1) {
                FuncItemType newItem = new FuncItemType();
                //set type name.
                newItem.setTypeName(dItem.getItemTypeName());
                ArrayList<String> newAtt = new ArrayList<>();
                //set all attributes, both primary and secondary.
                for (OutTrackedItem dAtt : dItem.getAttributes()) {
                    newAtt.add(dAtt.getAttribute().getAttributeName());
                }
                newItem.setAttributeNames(newAtt);
                newItem.setId(items.size());
                items.add(newItem);
                //assign i with the index of the item just created.
                i = items.indexOf(newItem);
            }
            //add item into array based on index i.
            FuncItem newfItem = new FuncItem(dItem.getTrackedId(), dItem.getBarcode(), dItem.getDateAdded(), dItem.getItemCondition(), dItem.getOrderNum(), dItem.getStatus(), dItem.getDescription());
            newfItem.setInvoiceNumber(dItem.getInvoiceNum());
            //Set attributes of the class in the correct place, in correct order for 'newfItem'.
            for (String fName : items.get(i).getAttributeNames()) {
                for (OutTrackedItem dAtt : dItem.getAttributes()) {
                    if (fName.equals(dAtt.getAttribute().getAttributeName())/* && fName.getSecondary().equals(dAtt.getAttribute().getSecondaryName())*/) {
                        FuncItemValue tmpFuncValue = new FuncItemValue();
                        tmpFuncValue.setPrimary(dAtt.getAttribute().getAttributeValue());
                        tmpFuncValue.setSecondary(dAtt.getAttribute().getSecondaryValue());
                        tmpFuncValue.setSecondaryType(dAtt.getAttribute().getSecondaryName());
                        newfItem.addItemValue(tmpFuncValue);
                    }
                }
            }
            //Add the new item into final 'items' list.
            items.get(i).getItems().add(newfItem);
        }
        Collections.sort(items, sortItemsByType);
        return items;
    }

    /**
     * Converts single tracked item into FuncItem. All object specific values
     * are placed into array index 0.
     *
     * @param databaseItem - Tracked item to be converted into FuncItem.
     * @return FuncItemType - FuncItem converted from Tracked item.
     */
    public FuncItemType convertToFuncItemSingle(Tracked databaseItem) {
        //Create FuncItemType
        FuncItemType newItem = new FuncItemType();
        //set type name.
        newItem.setTypeName(databaseItem.getItemTypeName());
        ArrayList<String> newAtt = new ArrayList<>();
        //set all attributes, both primary and secondary.
        for (TrackedItem dAtt : databaseItem.getAttributes()) {
            newAtt.add(dAtt.getAttribute().getAttributeName());
        }
        newItem.setAttributeNames(newAtt);
        newItem.setId(0);
        //CreateFuncItem
        FuncItem newfItem = new FuncItem(databaseItem.getTrackedId(), databaseItem.getBarcode(), databaseItem.getDateAdded(), databaseItem.getItemCondition(), databaseItem.getOrderNum(), databaseItem.getStatus(), databaseItem.getDescription());
        //Set attributes of the class in the correct place, in correct order for 'newfItem'.
        for (TrackedItem dAtt : databaseItem.getAttributes()) {
            FuncItemValue tmpFuncValue = new FuncItemValue();
            tmpFuncValue.setPrimary(dAtt.getAttribute().getAttributeValue());
            tmpFuncValue.setSecondary(dAtt.getAttribute().getSecondaryValue());
            tmpFuncValue.setSecondaryType(dAtt.getAttribute().getSecondaryName());
            newfItem.addItemValue(tmpFuncValue);
        }

        //Add the new item into final 'items' list.
        newItem.setItems(new ArrayList<>());
        newItem.getItems().add(0, newfItem);

        return newItem;
    }

    public ArrayList<FuncItemType> sort(ArrayList<FuncItemType> ftList) {
        Collections.sort(ftList, sortItemsByType);
        return ftList;
    }

    /*
    Comparator used to compare array of FuncItemType. Will compare based on alphetical order of TypeName in FuncItemType.
     */
    private static final Comparator<FuncItemType> sortItemsByType = new Comparator<FuncItemType>() {
        @Override
        public int compare(FuncItemType item1, FuncItemType item2) {
            String itemType1 = item1.getTypeName().toUpperCase();
            String itemType2 = item2.getTypeName().toUpperCase();
            return itemType1.compareTo(itemType2);
        }
    };

    /*
    Function tests if database item 'dItem' fits into any class of items currently stored in funcitnal items list, 'fItems'.
    Returns array index of corresponding fItem, if a similar class of item is found in 'fItems' list.
    Returns -1 if dItem is of a new class of items currently not in list 'fItems'.
     */
    private int findItemInArray(Tracked dItem, ArrayList<FuncItemType> fItems) {
        for (FuncItemType fItem : fItems) {
            if (similarityTest(dItem, fItem)) {
                return (fItems.indexOf(fItem));
            }
        }
        return -1;
    }

    /*
    Function tests if database item 'dItem' fits into any class of items currently stored in funcitnal items list, 'fItems'.
    Returns array index of corresponding fItem, if a similar class of item is found in 'fItems' list.
    Returns -1 if dItem is of a new class of items currently not in list 'fItems'.
     */
    private int findItemInArray(OutTracked dItem, ArrayList<FuncItemType> fItems) {
        for (FuncItemType fItem : fItems) {
            if (similarityTest(dItem, fItem)) {
                return (fItems.indexOf(fItem));
            }
        }
        return -1;
    }

    /*
    This function tests of a database item, 'dType' and functional item 'fType' is of the same class of items.
    Functuion checks name, primary attribute name and secondary attribute name.
    Return true if items are of the same type.
    Return false if they are not of same type.
     */
    private boolean similarityTest(Tracked dItem, FuncItemType fItem) {
        //Check if item type name is the same, then also check if number of attributes are same.
        if (dItem.getItemTypeName().equals(fItem.getTypeName()) && dItem.getAttributes().size() == fItem.getAttributeNames().size()) {
            //Cycle through all attributes and match with the one in items. Check if all attributes match.
            for (TrackedItem dAtt : dItem.getAttributes()) {
                //Bit created to check if similar item found.
                Boolean similarFoundBit = false;
                for (String fAtt : fItem.getAttributeNames()) {
                    if (fAtt.equals(dAtt.getAttribute().getAttributeName())/*&& fAtt.getSecondary().equals(dAtt.getAttribute().getSecondaryName())*/) {
                        similarFoundBit = true;
                    }
                }
                //If Bit is still false, similar item has not been found. Break and report.
                if (!similarFoundBit) {
                    return false;
                }
            }
            //If all items are cycled through and the loop has not broken. Item is similar. Return true.
            return true;
        }
        return false;
    }

    /*
    This function tests of a database item, 'dType' and functional item 'fType' is of the same class of items.
    Functuion checks name, primary attribute name and secondary attribute name.
    Return true if items are of the same type.
    Return false if they are not of same type.
     */
    private boolean similarityTest(OutTracked dItem, FuncItemType fItem) {
        //Check if item type name is the same, then also check if number of attributes are same.
        if (dItem.getItemTypeName().equals(fItem.getTypeName()) && dItem.getAttributes().size() == fItem.getAttributeNames().size()) {
            //Cycle through all attributes and match with the one in items. Check if all attributes match.
            for (OutTrackedItem dAtt : dItem.getAttributes()) {
                //Bit created to check if similar item found.
                Boolean similarFoundBit = false;
                for (String fAtt : fItem.getAttributeNames()) {
                    if (fAtt.equals(dAtt.getAttribute().getAttributeName())/*&& fAtt.getSecondary().equals(dAtt.getAttribute().getSecondaryName())*/) {
                        similarFoundBit = true;
                    }
                }
                //If Bit is still false, similar item has not been found. Break and report.
                if (!similarFoundBit) {
                    return false;
                }
            }
            //If all items are cycled through and the loop has not broken. Item is similar. Return true.
            return true;
        }
        return false;
    }
}
