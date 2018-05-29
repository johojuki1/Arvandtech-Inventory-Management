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
public class SelectableBox implements Serializable {

    private int selectableBoxId;
    private String name;
    private int selectableOrder;
    private boolean secondary;
    private String secondaryName;
    private List<SecondaryAttribute> secondaryAttribute;

    //GETTERS
    @Id
    @GeneratedValue
    public int getSelectableBoxId() {
        return selectableBoxId;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public int getSelectableOrder() {
        return selectableOrder;
    }

    @Column(nullable = false)
    public boolean isSecondary() {
        return secondary;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    @OneToMany
    public List<SecondaryAttribute> getSecondaryAttribute() {
        return secondaryAttribute;
    }

    //SETTERS
    public void setSelectableBoxId(int selectableBoxId) {
        this.selectableBoxId = selectableBoxId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelectableOrder(int selectableOrder) {
        this.selectableOrder = selectableOrder;
    }

    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public void setSecondaryAttribute(List<SecondaryAttribute> secondaryAttribute) {
        this.secondaryAttribute = secondaryAttribute;
    }
}
