/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities.settings;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class is part of the system which allows administrators to add, edit or remove item types.
 * Stores attributes of items, which will eventually be shown when a user attempts to add, remove or edit items.
 * For example item 'Computer' may have attribute 'Ram'.
 * attributeId: id for attribute item.
 * attributeName: name of the attribute. eg. Ram
 * selectable: determines if the attribute will use selectable boxes or have direct user input.
 * attributeOrder: determines order which this attribute will be displayed when adding items.
 * selectableBox: contains Id to selectable box items. Determines values to be viewed in selectable boxes. Only utilised if selectable table is used.
 * @author User
 */
@Entity
public class Attribute implements Serializable {

    private int attributeId;
    private String attributeName;
    private boolean selectable;
    private int attributeOrder;
    private List<SelectableBox> selectableBox;

    //GETTERS
    @Id
    @GeneratedValue
    public int getAttributeId() {
        return attributeId;
    }

    @Column(nullable = false)
    public String getAttributeName() {
        return attributeName;
    }

    @Column(nullable = false)
    public boolean isSelectable() {
        return selectable;
    }

    public int getAttributeOrder() {
        return attributeOrder;
    }

    @OneToMany
    public List<SelectableBox> getSelectableBox() {
        return selectableBox;
    }

    //SETTERS
    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public void setAttributeOrder(int attributeOrder) {
        this.attributeOrder = attributeOrder;
    }

    public void setSelectableBox(List<SelectableBox> selectableBox) {
        this.selectableBox = selectableBox;
    }
}
