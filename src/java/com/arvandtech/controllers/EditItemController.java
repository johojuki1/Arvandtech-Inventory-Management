/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.Attribute;
import com.arvandtech.domain.entities.ItemType;
import com.arvandtech.domain.entities.SecondaryAttribute;
import com.arvandtech.domain.entities.SelectableBox;
import com.arvandtech.domain.entities.Tracked;
import com.arvandtech.domain.facades.ItemTypeFacade;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.utilities.Settings;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Controller used for editing items.
 *
 * @author User
 */
@Named("editItem")
@SessionScoped
public class EditItemController implements Serializable {

    @EJB
    private TrackedFacade trackedFacade;

    @EJB
    private ItemTypeFacade itemFacade;

    private Tracked selectedItem;

    public EditItemController() {
        selectedItem = new Tracked();
    }

    /**
     *
     * @param id - id of the funcItem selected.
     */
    public void findSelectedItem(int id) {
        selectedItem = new Tracked();
        selectedItem = trackedFacade.find(id);
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public List<String> fetchStatusDropdown() {
        return new Settings().getStatus();
    }

    public List<String> fetchConditionDropdown() {
        return new Settings().getCondition();
    }

    public boolean attributeExists(int i) {
        try {
            return i <= selectedItem.getAttributes().size();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> findSelectables(int i) {
        List<String> selectables = new ArrayList<>();
        try {
            List<ItemType> itemTypeList = itemFacade.findAll();
            for (ItemType itemType_item : itemTypeList) {
                if (itemType_item.getTypeName().equals(selectedItem.getItemTypeName())) {
                    for (Attribute itemType_attribute : itemType_item.getAttribute()) {
                        if (selectedItem.getAttributes().get(i - 1).getAttribute().getAttributeName().equals(itemType_attribute.getAttributeName())) {
                            List<SelectableBox> itemType_selectableBox = itemType_attribute.getSelectableBox();
                            itemType_selectableBox.sort(Comparator.comparing(SelectableBox::getSelectableOrder));
                            for (SelectableBox itemType_selectable : itemType_selectableBox) {
                                selectables = checkAndAdd(selectables, itemType_selectable.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return selectables;
    }

    public List<String> findSecondarySelectables(int i) {
        List<String> secSelectables = new ArrayList<>();
        try {
            List<ItemType> itemTypeList = itemFacade.findAll();
            for (ItemType itemType_item : itemTypeList) {
                if (itemType_item.getTypeName().equals(selectedItem.getItemTypeName())) {
                    for (Attribute itemType_attribute : itemType_item.getAttribute()) {
                        if (selectedItem.getAttributes().get(i - 1).getAttribute().getAttributeName().equals(itemType_attribute.getAttributeName())) {
                            List<SelectableBox> itemType_selectableBox = itemType_attribute.getSelectableBox();
                            itemType_selectableBox.sort(Comparator.comparing(SelectableBox::getSelectableOrder));
                            for (SelectableBox itemType_selectable : itemType_selectableBox) {
                                if (itemType_selectable.getSecondaryName().equals(selectedItem.getAttributes().get(i - 1).getAttribute().getSecondaryName())) {
                                    List<SecondaryAttribute> itemType_secondaryAttribute = itemType_selectable.getSecondaryAttribute();
                                    itemType_secondaryAttribute.sort(Comparator.comparing(SecondaryAttribute::getSecondaryOrder));
                                    for (SecondaryAttribute itemType_secAtt : itemType_secondaryAttribute) {
                                        secSelectables = checkAndAdd(secSelectables, itemType_secAtt.getSecondaryAttributeName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return secSelectables;
    }

    public String findSecondaryLink(int i) {
        try {
            if (selectedItem.getAttributes().get(i - 1).getAttribute().getSecondaryName().isEmpty()) {
                return "";
            }
            return "secondarySelection.xhtml";
        } catch (Exception e) {
            return "";
        }
    }

    private List<String> checkAndAdd(List<String> list, String item) {
        if (!list.contains(item)) {
            list.add(item);
        }
        return list;
    }

    //GETTERS
    public Tracked getSelectedItem() {
        return selectedItem;
    }

    //SETTERS
    public void setSelectedItem(Tracked selectedItem) {
        this.selectedItem = selectedItem;
    }

}
