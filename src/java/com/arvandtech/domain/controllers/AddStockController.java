/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.controllers;

import com.arvandtech.domain.Attribute;
import com.arvandtech.domain.ItemType;
import com.arvandtech.domain.ItemTypeFacade;
import com.arvandtech.domain.SelectableBox;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Controller used to manage all functions used by the add stock function.
 *
 * @author User
 */
@Named("addStock")
@SessionScoped
public class AddStockController implements Serializable {

    @EJB
    private ItemTypeFacade itemFacade;

    private ItemType item;
    private ArrayList<Boolean> attExists;
    private String tmpEntry;

    public List<ItemType> findAllItems() {
        return itemFacade.findAll();
    }

    public boolean checkAttributeExist() {
        attExists = new ArrayList<>(11);
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    item.getAttribute().get(i);
                    attExists.add(true);
                } catch (Exception e) {
                    attExists.add(false);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String findTitle(int i)
    {
        return item.getAttribute().get(i-1).getAttributeName();
    }
    
    public void clearItem() {
        item = new ItemType();
    }

    public boolean attributeExists(int i) {
        try {
            item.getAttribute().get(i - 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String findLink(int i) {
        if (attExists.get(i-1)) {
            Attribute tmpAtt = item.getAttribute().get(i - 1);
            if (tmpAtt.isSelectable()) {
                return "layouts/oneSelection.xhtml";
            } else {
                return "layouts/entry.xhtml";
            }
        }
        return "";
    }
    
    public List<SelectableBox> findSelectables(int i) {
            Attribute tmpAtt = item.getAttribute().get(i - 1);
            return tmpAtt.getSelectableBox();
    }

    //GETTERS
    public ItemType getItem() {
        return item;
    }

    public String getTmpEntry() {
        return tmpEntry;
    }

    //SETTERS
    public void setItem(ItemType item) {
        this.item = item;
    }

    public void setTmpEntry(String tmpEntry) {
        this.tmpEntry = tmpEntry;
    }
}
