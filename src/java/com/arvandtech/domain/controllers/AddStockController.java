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
    private String tmpEntry;

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

    public void updatePrimaryAttribute(int i, String select) {
        SelectableBox tmpSelect = selectFacade.find(Integer.valueOf(select));
        ItemAttribute tmpAtt = new ItemAttribute();
        tmpAtt.setAttributeName(item.getAttribute().get(i - 1).getAttributeName());
        tmpAtt.setAttributeValue(tmpSelect.getName());
        if (tmpSelect.isSecondary()) {
            tmpAtt.setSecondaryName(tmpSelect.getSecondaryName());
        }
        attributes.set(i - 1, tmpAtt);
        selections.set(i - 1, tmpSelect);
    }

    public String findLink(int i) {
        if (attributeExists(i)) {
            Attribute tmpAtt = item.getAttribute().get(i - 1);
            if (tmpAtt.isSelectable()) {
                try {
                    if (selections.get(i - 1).getName() != null && selections.get(i - 1).isSecondary()) {
                        return "layouts/twoSelections.xhtml";
                    }
                } catch (Exception e) {
                }
                return "layouts/oneSelection.xhtml";
            } else {
                return "layouts/entry.xhtml";
            }
        }
        return "";
    }

    public List<SelectableBox> findSelectables(int i) {
        Attribute tmpAtt = item.getAttribute().get(i - 1);
        tmpAtt.getSelectableBox().sort(Comparator.comparing(SelectableBox::getSelectableOrder));
        return tmpAtt.getSelectableBox();
    }

    public List<SecondaryAttribute> findSecondaries(int i) {
        selections.get(i-1).getSecondaryAttribute().sort(Comparator.comparing(SecondaryAttribute::getSecondaryOrder));
        return selections.get(i-1).getSecondaryAttribute();
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
