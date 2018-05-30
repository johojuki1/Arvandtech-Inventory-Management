/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.controllers;

import com.arvandtech.domain.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named("stockSetup")
@SessionScoped
public class StockSetupController implements Serializable {

    @EJB
    private ItemTypeFacade itemTypeFacade;

    @EJB
    private AttributeFacade attributeFacade;

    @EJB
    private SelectableBoxFacade selectableFacade;

    @EJB
    private SecondaryAttributeFacade secondaryFacade;

    private final String NAVIGATION_LOCATION = "pageElements/";

    private List<ItemType> itemTypes;

    private ItemType item;

    private Attribute attribute;

    private SelectableBox selectable;

    private SecondaryAttribute secondary;


    /*Navigational states. These are in here instead of the navigation class because
    these states should be removed when this session is initiated or destroyed.
     */
    private int itemTypeState;
    private int attributeState;
    private int selectableState;
    private int secondaryState;

    public StockSetupController() {
        resetStates();
    }

    private void resetObject(int identifier, boolean cascade) {
        switch (identifier) {
            case 1:
                item = new ItemType();
                if (!cascade) {
                    return;
                }
            case 2:
                attribute = new Attribute();
                if (!cascade) {
                    return;
                }
            case 3:
                selectable = new SelectableBox();
                if (!cascade) {
                    return;
                }
            case 4:
                secondary = new SecondaryAttribute();
            default:
        }
    }

    public void loadItems() {
        itemTypes = itemTypeFacade.findAll();
    }

    private void resetStates() {
        itemTypeState = 0;
        attributeState = 0;
        selectableState = 0;
        secondaryState = 0;
    }

    public void updateItem() {
        item = itemTypeFacade.find(item.getItemTypeId());
    }

    public void updateAtt() {
        attribute = attributeFacade.find(attribute.getAttributeId());
    }

    public void updateSelect() {
        selectable = selectableFacade.find(selectable.getSelectableBoxId());
    }

    /*
    
    Navigation Functions
    
     */
    public String getItemTypeLink() {
        switch (itemTypeState) {
            case 0:
                return NAVIGATION_LOCATION + "itemTypeSelectableBox.xhtml";
            case 1:
                return NAVIGATION_LOCATION + "itemTypeAdd.xhtml";
            case 2:
                return NAVIGATION_LOCATION + "itemTypeEdit.xhtml";
            default:
                itemTypeState = 0;
                return NAVIGATION_LOCATION + "itemTypeSelectableBox.xhtml";
        }
    }

    public String getAttributeLink() {
        switch (attributeState) {
            case 0:
                return NAVIGATION_LOCATION + "attributeSelectableBox.xhtml";
            case 1:
                return NAVIGATION_LOCATION + "attributeAdd.xhtml";
            case 2:
                return NAVIGATION_LOCATION + "attributeEdit.xhtml";
            default:
                itemTypeState = 0;
                return NAVIGATION_LOCATION + "attributeSelectableBox.xhtml";
        }
    }

    public String getSelectableLink() {
        switch (selectableState) {
            case 0:
                return NAVIGATION_LOCATION + "selectableSelectableBox.xhtml";
            case 1:
                return NAVIGATION_LOCATION + "selectableAdd.xhtml";
            case 2:
                return NAVIGATION_LOCATION + "selectableEdit.xhtml";
            default:
                itemTypeState = 0;
                return NAVIGATION_LOCATION + "selectableSelectableBox.xhtml";
        }
    }

    public String getSecondaryLink() {
        switch (secondaryState) {
            case 0:
                return NAVIGATION_LOCATION + "secondarySelectableBox.xhtml";
            case 1:
                return NAVIGATION_LOCATION + "secondaryAdd.xhtml";
            case 2:
                return NAVIGATION_LOCATION + "secondaryEdit.xhtml";
            default:
                itemTypeState = 0;
                return NAVIGATION_LOCATION + "secondarySelectableBox.xhtml";
        }
    }

    //State change and item checking.
    private boolean checkSelected(String errorBoxName, String noSelectError, String failureError, int identifier) {
        try {
            if (checkItemSelected(identifier)) {
                return true;
            } else {
                FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", noSelectError));
                return false;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", failureError));
            return false;
        }
    }

    public boolean checkItemSelected(int identifier) {
        try {
            switch (identifier) {
                case 1:
                    if (item.getItemTypeId() != 0) {
                        return true;
                    }
                case 2:
                    if (attribute.getAttributeId() != 0) {
                        return true;
                    }
                case 3:
                    if (selectable.getSelectableBoxId() != 0) {
                        return true;
                    }
                case 4:
                    if (secondary.getSecondaryAttributeId() != 0) {
                        return true;
                    }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /*

    Item Functions
    
     */
    public int addItem(String name) {
        try {
            resetObject(1, false);
            ItemType tmpType = new ItemType();
            tmpType.setTypeName(name);
            tmpType.setDeleteable(true);
            tmpType.setItemTypeId(0);
            tmpType.setAttribute(new ArrayList<>());
            tmpType.setItemOrder(itemTypes.size() + 1);
            itemTypeFacade.safeCreate(tmpType);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public int editItem() {
        try {
            itemTypeFacade.safeEdit(item.getItemTypeId(), item.getTypeName());
            return 0;
        } catch (Exception e) {
            return 2;
        }
    }

    public void deleteItem(String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No item is selected for delete.", "Database Update Failed.", 1)) {
                for (Attribute tmpAttribute : item.getAttribute()) {
                    attribute = tmpAttribute;
                    deleteAttribute("errorBoxName");
                }
                itemTypeFacade.safeDelete(item.getItemTypeId());
                item = new ItemType();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
        resetObject(2, true);
        resetStates();
    }

    public void changeItemOrder(String direction, String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No item is selected for moving.", "Database Update Failed.", 1)) {
                if (direction.equals("up")) {
                    itemTypeFacade.moveUp(item.getItemTypeId());
                } else {
                    itemTypeFacade.moveDown(item.getItemTypeId());
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
    }

    public void navigateItemEdit(String errorBoxName, int state) {
        if (checkSelected(errorBoxName, "No item is selected for edit.", "Database Update Failed.", 1)) {
            setItemTypeState(state);
        }
    }

    public void itemSelected() {
        resetObject(2, true);
        resetStates();
    }

    /*
    
    Attribute Functions
    
     */
    public int addAttribute(String name, boolean state) {
        resetObject(2, false);
        try {
            Attribute tmpAttribute = attributeFacade.returnedCreate(name, state, new ArrayList<>());
            itemTypeFacade.addAttribute(item.getItemTypeId(), tmpAttribute);
            item = itemTypeFacade.find(item.getItemTypeId());
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public void deleteAttribute(String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No attribute is selected for delete.", "Database Update Failed.", 2)) {
                item = itemTypeFacade.find(item.getItemTypeId());
                if (itemTypeFacade.removeAttribute(item.getItemTypeId(), attribute.getAttributeId())) {
                    for (SelectableBox tmpSelectable : attribute.getSelectableBox()) {
                        selectable = tmpSelectable;
                        deleteSelectable("errorBoxName");
                    }
                    attributeFacade.safeDelete(attribute.getAttributeId(), item.getAttribute());
                } else {
                    FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
                }
                item = itemTypeFacade.find(item.getItemTypeId());
                resetObject(3, true);
                resetStates();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
    }

    public int editAttribute() {
        try {
            attributeFacade.safeEdit(attribute.getAttributeId(), attribute.getAttributeName(), attribute.isSelectable());
            return 0;
        } catch (Exception e) {
            return 2;
        }
    }

    public void changeAttributeOrder(String direction, String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No attribute is selected for moving.", "Database Update Failed.", 2)) {
                Attribute attribute2 = new Attribute();
                int tmpOrder = 0;
                if (direction.equals("up")) {
                    if (!(attribute.getAttributeOrder() <= 1)) {
                        tmpOrder = attribute.getAttributeOrder() - 1;
                    }
                } else {
                    if (!(attribute.getAttributeOrder() >= item.getAttribute().size())) {
                        tmpOrder = attribute.getAttributeOrder() + 1;
                    }
                }
                if (tmpOrder != 0) {
                    for (Attribute tmpAtt : item.getAttribute()) {
                        if (tmpAtt.getAttributeOrder() == tmpOrder) {
                            attribute2 = tmpAtt;
                        }
                    }
                    if (attribute2.getAttributeId() != 0) {
                        attributeFacade.swapOrder(attribute.getAttributeId(), attribute2.getAttributeId());
                    } else {
                        FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Item with correct index was not found. Order Swapping failed."));
                    }
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
        item = itemTypeFacade.find(item.getItemTypeId());
    }

    public void navigateAttributeEdit(String errorBoxName, int state) {
        if (checkSelected(errorBoxName, "No item is selected for edit.", "Database Update Failed.", 1) && checkSelected(errorBoxName, "No attribute is selected for edit.", "Database Update Failed.", 2)) {
            setAttributeState(state);
        }
    }

    public boolean checkAttributeSelected() {
        try {
            if (attribute.getAttributeId() != 0) {
                return attribute.isSelectable();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void attributeSelected() {
        resetObject(3, true);
        resetStates();
    }

    /*
    
    Selectable Functions
    
     */
    public int addSelectable(String name, boolean secondary, String secondaryName) {
        resetObject(3, false);
        try {
            SelectableBox tmpSelectable = selectableFacade.returnedCreate(name, secondary, secondaryName, new ArrayList<>());
            attributeFacade.addSelectable(attribute.getAttributeId(), tmpSelectable);
            attribute = attributeFacade.find(attribute.getAttributeId());
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public boolean showSecondary(boolean b) {
        return b;
    }

    public void deleteSelectable(String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No selectable item is selected for delete.", "Database Update Failed.", 3)) {
                attribute = attributeFacade.find(attribute.getAttributeId());
                if (attributeFacade.removeSelectable(attribute.getAttributeId(), selectable.getSelectableBoxId())) {
                    for (SecondaryAttribute tmpSecondary : selectable.getSecondaryAttribute()) {
                        secondary = tmpSecondary;
                        deleteSecondary("errorBoxName");
                    }
                    selectableFacade.safeDelete(selectable.getSelectableBoxId(), attribute.getSelectableBox());
                } else {
                    FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
                }
                attribute = attributeFacade.find(attribute.getAttributeId());
                resetObject(4, true);
                resetStates();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
    }

    public void changeStockOrder(String direction, String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No selectable item is selected for moving.", "Database Update Failed.", 3)) {
                SelectableBox selectable2 = new SelectableBox();
                int tmpOrder = 0;
                if (direction.equals("up")) {
                    if (!(selectable.getSelectableOrder() <= 1)) {
                        tmpOrder = selectable.getSelectableOrder() - 1;
                    }
                } else {
                    if (!(selectable.getSelectableOrder() >= attribute.getSelectableBox().size())) {
                        tmpOrder = selectable.getSelectableOrder() + 1;
                    }
                }
                if (tmpOrder != 0) {
                    for (SelectableBox tmpSelect : attribute.getSelectableBox()) {
                        if (tmpSelect.getSelectableOrder() == tmpOrder) {
                            selectable2 = tmpSelect;
                        }
                    }
                    if (selectable2.getSelectableBoxId() != 0) {
                        selectableFacade.swapOrder(selectable.getSelectableBoxId(), selectable2.getSelectableBoxId());
                    } else {
                        FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Selectable item with correct index was not found. Order Swapping failed."));
                    }
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
        item = itemTypeFacade.find(item.getItemTypeId());
    }

    public int editSelectable() {
        try {
            selectableFacade.safeEdit(selectable.getSelectableBoxId(), selectable.getName(), selectable.isSecondary(), selectable.getSecondaryName());
            return 0;
        } catch (Exception e) {
            return 2;
        }
    }

    public void navigateSelectableEdit(String errorBoxName, int state) {
        if (checkSelected(errorBoxName, "No selectable item is selected for edit.", "Database Update Failed.", 2) && checkSelected(errorBoxName, "No selectable item is selected for edit.", "Database Update Failed.", 3)) {
            setSelectableState(state);
        }
    }

    public boolean checkSelectableSelected() {
        try {
            if (selectable.getSelectableBoxId() != 0) {
                return selectable.isSecondary();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectableSelected() {
        resetObject(4, true);
        resetStates();
    }

    /*
    
    Secondary Attribute Functions
    
     */
    public int addSecondary(String name) {
        resetObject(4, false);
        try {
            SecondaryAttribute tmpSecondary = secondaryFacade.returnedCreate(name);
            selectableFacade.addSecondary(selectable.getSelectableBoxId(), tmpSecondary);
            selectable = selectableFacade.find(selectable.getSelectableBoxId());
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public void deleteSecondary(String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No secondary item is selected for delete.", "Database Update Failed.", 4)) {
                selectable = selectableFacade.find(selectable.getSelectableBoxId());
                if (selectableFacade.removeSecondary(selectable.getSelectableBoxId(), secondary.getSecondaryAttributeId())) {
                    secondaryFacade.safeDelete(secondary.getSecondaryAttributeId(), selectable.getSecondaryAttribute());
                } else {
                    FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
                }
                selectable = selectableFacade.find(selectable.getSelectableBoxId());
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
    }

    public void changeSecondaryOrder(String direction, String errorBoxName) {
        try {
            if (checkSelected(errorBoxName, "No secondary attribute is selected for moving.", "Database Update Failed.", 4)) {
                SecondaryAttribute secondary2 = new SecondaryAttribute();
                int tmpOrder = 0;
                if (direction.equals("up")) {
                    if (!(secondary.getSecondaryOrder() <= 1)) {
                        tmpOrder = secondary.getSecondaryOrder() - 1;
                    }
                } else {
                    if (!(secondary.getSecondaryOrder() >= selectable.getSecondaryAttribute().size())) {
                        tmpOrder = secondary.getSecondaryOrder() + 1;
                    }
                }
                if (tmpOrder != 0) {
                    for (SecondaryAttribute tmpAtt : selectable.getSecondaryAttribute()) {
                        if (tmpAtt.getSecondaryOrder() == tmpOrder) {
                            secondary2 = tmpAtt;
                        }
                    }
                    if (secondary2.getSecondaryAttributeId() != 0) {
                        secondaryFacade.swapOrder(secondary.getSecondaryAttributeId(), secondary2.getSecondaryAttributeId());
                    } else {
                        FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Secondary Attribute with correct index was not found. Order Swapping failed."));
                    }
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
        item = itemTypeFacade.find(item.getItemTypeId());
    }

    public void secondarySelected() {
        resetObject(5, true);
        resetStates();
    }

    public void navigateSecondaryEdit(String errorBoxName, int state) {
        if (checkSelected(errorBoxName, "No secondary attribute item is selected for edit.", "Database Update Failed.", 3) && checkSelected(errorBoxName, "No selectable item is selected for edit.", "Database Update Failed.", 4)) {
            setSecondaryState(state);
        }
    }

    public int editSecondary() {
        try {
            secondaryFacade.safeEdit(secondary.getSecondaryAttributeId(), secondary.getSecondaryAttributeName());
            return 0;
        } catch (Exception e) {
            return 2;
        }
    }

    //GETTERS
    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public int getItemTypeState() {
        return itemTypeState;
    }

    public ItemType getItem() {
        return item;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getAttributeState() {
        return attributeState;
    }

    public int getSelectableState() {
        return selectableState;
    }

    public SelectableBox getSelectable() {
        return selectable;
    }

    public int getSecondaryState() {
        return secondaryState;
    }

    public SecondaryAttribute getSecondary() {
        return secondary;
    }

    //SETTERS
    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public void setItemTypeState(int itemTypeState) {
        resetStates();
        this.itemTypeState = itemTypeState;
    }

    public void setItem(ItemType item) {
        this.item = item;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setAttributeState(int attributeState) {
        resetStates();
        this.attributeState = attributeState;
    }

    public void setSelectableState(int selectableState) {
        resetStates();
        this.selectableState = selectableState;
    }

    public void setSelectable(SelectableBox selectable) {
        this.selectable = selectable;
    }

    public void setSecondaryState(int secondaryState) {
        resetStates();
        this.secondaryState = secondaryState;
    }

    public void setSecondary(SecondaryAttribute secondary) {
        this.secondary = secondary;
    }
}
