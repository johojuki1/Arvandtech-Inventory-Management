/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.settings.Attribute;
import com.arvandtech.domain.entities.inventory.ItemAttribute;
import com.arvandtech.domain.entities.settings.ItemType;
import com.arvandtech.domain.entities.settings.SecondaryAttribute;
import com.arvandtech.domain.entities.settings.SelectableBox;
import com.arvandtech.domain.entities.inventory.Tracked;
import com.arvandtech.domain.entities.inventory.TrackedItem;
import com.arvandtech.domain.entities.outinventory.OutTracked;
import com.arvandtech.domain.facades.ItemAttributeFacade;
import com.arvandtech.domain.facades.ItemTypeFacade;
import com.arvandtech.domain.facades.OutTrackedFacade;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.domain.facades.TrackedItemFacade;
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

    @EJB
    private TrackedItemFacade tItemFacade;

    @EJB
    private ItemAttributeFacade attributeFacade;

    @EJB
    private OutTrackedFacade outTrackedFacade;

    private Tracked selectedItem;

    private OutTracked selectedOutgoingItem;

    public EditItemController() {
        selectedItem = new Tracked();
        selectedOutgoingItem = new OutTracked();
    }

    /**
     *
     * @param id - id of the funcItem selected.
     */
    public void findSelectedItem(int id) {
        selectedItem = new Tracked();
        selectedItem = trackedFacade.find(id);
    }

    public void findSelectedOutgoingItem(int id) {
        selectedOutgoingItem = new OutTracked();
        selectedOutgoingItem = outTrackedFacade.find(id);
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

    public boolean attributeOutExists(int i) {
        try {
            return i <= selectedOutgoingItem.getAttributes().size();
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

    /*
    Function edits the trackeditem object. Edits both direct attributes and also all connected values to the user's new value.
     */
    public void editTrackedItem() {
        //Retrieve attribtues of selected item that is editted
        List<TrackedItem> newAttributes = selectedItem.getAttributes();
        //Retrieve attributes of old item from database.
        List<TrackedItem> oldAttributes = trackedFacade.find(selectedItem.getTrackedId()).getAttributes();
        //Loop for all available attributes.
        for (int i = 0; i < newAttributes.size(); i++) {
            //Determiens if attributes are not the same. If they are different, the attribute gets processed to conform with new values.
            if (!compareAttributes(newAttributes.get(i).getAttribute(), oldAttributes.get(i).getAttribute())) {
                //remove tracked from itemAttribute relationship.
                oldAttributes.get(i).getAttribute().getItems().remove(oldAttributes.get(i));
                attributeFacade.edit(oldAttributes.get(i).getAttribute());
                //Find new itemAttribute item for new attribute. (Function automatically determines if one already exists)
                ItemAttribute newAttribute = attributeFacade.checkAndAdd(newAttributes.get(i).getAttribute());
                TrackedItem tmpAttribute = new TrackedItem();
                tmpAttribute.setItemOrder(oldAttributes.get(i).getItemOrder());
                tmpAttribute.setTracked(oldAttributes.get(i).getTracked());
                tmpAttribute.setAttribute(newAttribute);
                //Since the primary key is mapped directly to attribute, attribute cannot be editted.
                //This function will entirely remove the old attribute and return a new replaced one.
                tmpAttribute = tItemFacade.returnedCreate(tmpAttribute);
                selectedItem.getAttributes().set(i, tmpAttribute);
                tItemFacade.remove(oldAttributes.get(i));
            }
        }
        trackedFacade.edit(selectedItem);
    }
    
    public void editOutDescription() {
        outTrackedFacade.edit(selectedOutgoingItem);
    }

    private boolean compareAttributes(ItemAttribute att1, ItemAttribute att2) {
        return (att1.getId() == att2.getId())
                && (att1.getAttributeName().equals(att2.getAttributeName()))
                && (att1.getAttributeValue().equals(att2.getAttributeValue()))
                && (att1.getSecondaryName().equals(att2.getSecondaryName()))
                && (att1.getSecondaryValue().equals(att2.getSecondaryValue()));
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
    
    public String findOutSecondaryLink(int i) {
        try {
            if (selectedOutgoingItem.getAttributes().get(i - 1).getAttribute().getSecondaryName().isEmpty()) {
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

    public OutTracked getSelectedOutgoingItem() {
        return selectedOutgoingItem;
    }

    //SETTERS
    public void setSelectedItem(Tracked selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setSelectedOutgoingItem(OutTracked selectedOutgoingItem) {
        this.selectedOutgoingItem = selectedOutgoingItem;
    }

}
