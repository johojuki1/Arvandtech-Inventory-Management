/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

import com.arvandtech.domain.entities.ItemAttribute;
import com.arvandtech.domain.entities.Tracked;
import com.arvandtech.domain.entities.TrackedItem;
import com.arvandtech.utilities.entities.FuncItemType;
import com.arvandtech.utilities.entities.FuncItemValue;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class DatabaseConverter {

    public void sort(ArrayList<Tracked> databaseItem) {
        ArrayList<FuncItemType> items = new ArrayList<>();
        for (Tracked dItem : databaseItem) {
            int i = findItemInArray(dItem, items);
            if (i == 9999) {
                FuncItemType newItem = new FuncItemType();
                newItem.setTypeName(dItem.getItemTypeName());
                ArrayList<FuncItemValue> newAtt = new ArrayList<>();
                for (TrackedItem dAtt: dItem.getAttributes()) {
                    FuncItemValue itemValue = new FuncItemValue();
                    itemValue.setPrimary(dAtt.getAttribute().getAttributeName());
                    itemValue.setSecondary(dAtt.getAttribute().getSecondaryName());
                    newAtt.add(itemValue);
                }
                newItem.setAttributeNames(newAtt);
                items.add(newItem);
            }
        }
        String s = "AllDone";
    }

    private int findItemInArray(Tracked dItem, ArrayList<FuncItemType> fItems) {
        for (FuncItemType fItem : fItems) {
            boolean b = similarityTest(dItem, fItem);
            if (similarityTest(dItem, fItem)) {
                String s = "Similar";
                return 1;
            }
        }
        
        return 9999;
    }

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
