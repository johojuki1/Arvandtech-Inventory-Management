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
import com.arvandtech.domain.ItemAttribute;
import com.arvandtech.domain.SecondaryAttribute;
import com.arvandtech.domain.SelectableBoxFacade;
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

    private ItemType item;
    private ArrayList<ItemAttribute> attributes;
    private ArrayList<SelectableBox> selections;

    public List<ItemType> findAllItems() {
        return itemFacade.findAll();
    }

    public boolean checkAttributeExist() {
        attributes = new ArrayList<>();
        selections = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                try {
                    item.getAttribute().get(i);
                    attributes.add(new ItemAttribute());
                    selections.add(new SelectableBox());
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
        return attributes.get(i - 1).getSecondaryName();
    }

    public void clearItem() {
        item = new ItemType();
    }

    public boolean attributeExists(int i) {
        if (i <= attributes.size()) {
            return true;
        } else {
            return false;
        }
    }

    //Function is pointless and only used to trigger onchange.
    public void listener() {
    }

    public void updateSecondaryAttribute(int i, String select) {
        String s = "s";
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
            }
            else if (id != 0) {
                SelectableBox tmpsb = selectFacade.find(id);
                selections.set(i - 1, tmpsb);
            }
            if (selections.get(i - 1).getName() != null && selections.get(i - 1).isSecondary()) {
                return "secondarySelection.xhtml";
            }
        } catch (Exception e) {
        }
        return "";

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

    //GETTERS
    public ItemType getItem() {
        return item;
    }

    //SETTERS
    public void setItem(ItemType item) {
        item.getAttribute().sort(Comparator.comparing(Attribute::getAttributeOrder));
        this.item = item;
    }

}
