/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

import com.arvandtech.domain.entities.Tracked;
import com.arvandtech.domain.entities.TrackedItem;
import com.arvandtech.utilities.entities.FuncItem;
import com.arvandtech.utilities.entities.FuncItemType;
import com.arvandtech.utilities.entities.FuncItemValue;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class DatabaseConverter {

    /*
    This function takes in a list of 'Tracked' database items. It will sort the list into classes and cast all itmes into a more usable format.
    All website functions should use the output format as opposed to directly from database as it is easier to manage and use.
    */
    public ArrayList<FuncItemType> sortToFunc(ArrayList<Tracked> databaseItem) {
        ArrayList<FuncItemType> items = new ArrayList<>();
        for (Tracked dItem : databaseItem) {
            int i = findItemInArray(dItem, items);
            /*If 'dItem''s class cannot be found in list of 'items', create a new class within list 'items'*/
            if (i == -1) {
                FuncItemType newItem = new FuncItemType();
                //set type name.
                newItem.setTypeName(dItem.getItemTypeName());
                ArrayList<FuncItemValue> newAtt = new ArrayList<>();
                //set all attributes, both primary and secondary.
                for (TrackedItem dAtt : dItem.getAttributes()) {
                    FuncItemValue itemValue = new FuncItemValue();
                    itemValue.setPrimary(dAtt.getAttribute().getAttributeName());
                    itemValue.setSecondary(dAtt.getAttribute().getSecondaryName());
                    newAtt.add(itemValue);
                }
                newItem.setAttributeNames(newAtt);
                newItem.setId(items.size());
                items.add(newItem);
                //assign i with the index of the item just created.
                i = items.indexOf(newItem);
            }
            //add item into array based on index i.
            FuncItem newfItem = new FuncItem(dItem.getTrackedId(), dItem.getBarcode(), dItem.getDateAdded(), dItem.getItemCondition(), dItem.getOrderNum(), dItem.getStatus(), dItem.getDescription());
            ArrayList<FuncItemValue> fItemNames = items.get(i).getAttributeNames();
            //Set attributes of the class in the correct place, in correct order for 'newfItem'.
            for (FuncItemValue fName : fItemNames) {
                for (TrackedItem dAtt : dItem.getAttributes()) {
                    if (fName.getPrimary().equals(dAtt.getAttribute().getAttributeName()) && fName.getSecondary().equals(dAtt.getAttribute().getSecondaryName())) {
                        FuncItemValue tmpFuncValue = new FuncItemValue();
                        tmpFuncValue.setPrimary(dAtt.getAttribute().getAttributeValue());
                        tmpFuncValue.setSecondary(dAtt.getAttribute().getSecondaryValue());      
                        newfItem.addItemValue(tmpFuncValue);
                        newfItem.setTypeId(items.get(i).getId());
                    }
                }
            }
            //Add the new item into final 'items' list.
            items.get(i).getItems().add(newfItem);
        }
        return items;
    }

    /*
    Function tests if database item 'dItem' fits into any class of items currently stored in funcitnal items list, 'fItems'.
    Returns array index of corresponding fItem, if a similar class of item is found in 'fItems' list.
    Returns -1 if dItem is of a new class of items currently not in list 'fItems'.
     */
    private int findItemInArray(Tracked dItem, ArrayList<FuncItemType> fItems) {
        for (FuncItemType fItem : fItems) {
            boolean b = similarityTest(dItem, fItem);
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
                for (FuncItemValue fAtt : fItem.getAttributeNames()) {
                    if (fAtt.getPrimary().equals(dAtt.getAttribute().getAttributeName()) && fAtt.getSecondary().equals(dAtt.getAttribute().getSecondaryName())) {
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
