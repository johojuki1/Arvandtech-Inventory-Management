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
        itemTypeState = 0;
        attributeState = 0;
        selectableState = 0;
    }

    public void init() {
        itemTypes = itemTypeFacade.findAll();
    }

    public void resetAtt() {
        attribute = new Attribute();
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
            ItemType tmpType = new ItemType();
            tmpType.setTypeName(name);
            tmpType.setDeleteable(true);
            tmpType.setItemTypeId(0);
            tmpType.setAttribute(new ArrayList<>());
            tmpType.setItemOrder(itemTypes.size() + 1);
            itemTypeFacade.safeCreate(tmpType);
            item = new ItemType();
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
            init();
            if (checkSelected(errorBoxName, "No item is selected for delete.", "Database Update Failed.", 1)) {
                itemTypeFacade.safeDelete(item.getItemTypeId());
                item = new ItemType();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
    }

    public void changeItemOrder(String direction, String errorBoxName) {
        init();
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


    /*
    
    Attribute Functions
    
     */
    public int addAttribute(String name, boolean state) {
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
                    attributeFacade.safeDelete(attribute.getAttributeId(), item.getAttribute());
                } else {
                    FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
                }
                item = itemTypeFacade.find(item.getItemTypeId());
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
            init();
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

    /*
    
    Selectable Functions
    
     */
    public int addSelectable(String name, boolean secondary, String secondaryName) {
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
                    selectableFacade.safeDelete(selectable.getSelectableBoxId(), attribute.getSelectableBox());
                } else {
                    FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
                }
                attribute = attributeFacade.find(attribute.getAttributeId());
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(errorBoxName, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Database Update Failed."));
        }
    }

    public void changeStockOrder(String direction, String errorBoxName) {
        try {
            init();
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
        if (checkSelected(errorBoxName, "No selectable item is selected for edit.", "Database Update Failed.", 1) && checkSelected(errorBoxName, "No selectable item is selected for edit.", "Database Update Failed.", 2)) {
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

    /*
    
    Secondary Attribute Functions
    
     */
    public int addSecondary(String name) {
        try {
            SecondaryAttribute tmpSecondary = secondaryFacade.returnedCreate(name);
            selectableFacade.addSecondary(selectable.getSelectableBoxId(), tmpSecondary);
            attribute = attributeFacade.find(attribute.getAttributeId());
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
        this.itemTypeState = itemTypeState;
    }

    public void setItem(ItemType item) {
        this.item = item;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setAttributeState(int attributeState) {
        this.attributeState = attributeState;
    }

    public void setSelectableState(int selectableState) {
        this.selectableState = selectableState;
    }

    public void setSelectable(SelectableBox selectable) {
        this.selectable = selectable;
    }

    public void setSecondaryState(int secondaryState) {
        this.secondaryState = secondaryState;
    }

    public void setSecondary(SecondaryAttribute secondary) {
        this.secondary = secondary;
    }
}
