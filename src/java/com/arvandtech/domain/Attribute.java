/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
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
