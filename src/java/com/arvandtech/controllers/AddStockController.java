/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.Attribute;
import com.arvandtech.domain.entities.ItemType;
import com.arvandtech.domain.facades.ItemTypeFacade;
import com.arvandtech.domain.entities.SelectableBox;
import com.arvandtech.domain.entities.ItemAttribute;
import com.arvandtech.domain.entities.SecondaryAttribute;
import com.arvandtech.domain.facades.SecondaryAttributeFacade;
import com.arvandtech.domain.facades.SelectableBoxFacade;
import com.arvandtech.utilities.Settings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
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

    @EJB
    private SelectableBoxFacade selectFacade;

    @EJB
    private SecondaryAttributeFacade secondaryFacade;

    private ItemType item;
    private ArrayList<ItemAttribute> attributes;
    private ArrayList<SelectableBox> selections;
    private ArrayList<SecondaryAttribute> secondaries;
    private String barcode;
    private String status;
    private String itemCondition;
    private String description;

    public List<ItemType> findAllItems() {
        return itemFacade.findAll();
    }

    public boolean checkAttributeExist() {
        attributes = new ArrayList<>();
        selections = new ArrayList<>();
        secondaries = new ArrayList<>();
        try {
            for (int i = 0; i < new Settings().getMAX_ATTRIBUTES(); i++) {
                try {
                    item.getAttribute().get(i);
                    attributes.add(new ItemAttribute());
                    selections.add(new SelectableBox());
                    secondaries.add(new SecondaryAttribute());
                } catch (Exception e) {
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String findTitle(int i) {
        return item.getAttribute().get(i - 1).getAttributeName();
    }

    public String findSecondaryTitle(int i) {
        return selections.get(i - 1).getSecondaryName();
    }

    public void clearItem() {
        item = new ItemType();
    }

    public boolean attributeExists(int i) {
        return i <= item.getAttribute().size();
    }

    /*
    Function is pointless and only used to trigger onchange.
    onchange needs to be triggered for the 'findSecondaryLink' function to recieve its correct id. Otherwise the id returns as 0 all of the time.
    I tried to use ajax to trigger the ID, but the attribute 'select' on the html does not persist until 'findSecondaryLink' triggers.
    Many solutions were trialed including having a saparate listener as an ajax command and having the 'onchange' command do soemthing.
    None worked due to load orders of HTML or 'select' attribute no longer existing.
    This non-sensical solution is the only one tested that works short of making a different attribute for every 'SelectableBox' object. 
     */
    public void listener() {
    }

    public void updateSecondaryAttribute(int i, int id) {
        if (id != 0) {
            SecondaryAttribute tmpsa = secondaryFacade.find(id);
            secondaries.set(i - 1, tmpsa);
        }
    }

    public String findLink(int i) {
        if (attributeExists(i)) {
            Attribute tmpAtt = item.getAttribute().get(i - 1);
            if (tmpAtt.isSelectable()) {
                return "layouts/selectable.xhtml";
            } else {
                return "layouts/entry.xhtml";
            }
        }
        return "";
    }

    public String findSecondaryLink(int i, int id) {
        try {
            if (id == -1) {
                selections.set(i - 1, new SelectableBox());
            } else if (id != 0) {
                SelectableBox tmpsb = selectFacade.find(id);
                selections.set(i - 1, tmpsb);
                ItemAttribute tmpAtt = new ItemAttribute();
                tmpAtt.setAttributeName(item.getAttribute().get(i - 1).getAttributeName());
                tmpAtt.setAttributeValue(tmpsb.getName());
                attributes.set(i - 1, tmpAtt);
            }
            if (selections.get(i - 1).getName() != null && selections.get(i - 1).isSecondary()) {
                return "secondarySelection.xhtml";
            }
        } catch (Exception e) {
        }
        return "";
    }

    public void moveToScan() {
        scanItem = item;
        int counter = 0;
        for (Attribute tmpAtt : scanItem.getAttribute()) {
            //SET ONE TRACKED ITEM OBJECT
        }

    }

    public List<SelectableBox> findSelectables(int i) {
        Attribute tmpAtt = item.getAttribute().get(i - 1);
        tmpAtt.getSelectableBox().sort(Comparator.comparing(SelectableBox::getSelectableOrder));
        return tmpAtt.getSelectableBox();
    }

    public List<SecondaryAttribute> findSecondaries(int i) {
        try {
            selections.get(i - 1).getSecondaryAttribute().sort(Comparator.comparing(SecondaryAttribute::getSecondaryOrder));
            return selections.get(i - 1).getSecondaryAttribute();
        } catch (Exception e) {
        }
        return null;
    }

    public List<String> fetchStatusDropdown() {
        return new Settings().getStatus();
    }

    public List<String> fetchConditionDropdown() {
        return new Settings().getCondition();
    }

    //GETTERS
    public ItemType getItem() {
        return item;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    //SETTERS
    public void setItem(ItemType item) {
        item.getAttribute().sort(Comparator.comparing(Attribute::getAttributeOrder));
        this.item = item;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }
}
