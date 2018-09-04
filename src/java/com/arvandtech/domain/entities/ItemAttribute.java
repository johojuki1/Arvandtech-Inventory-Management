/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class is for the design and implementation of the 
 * @author Jonathan Ho
 */
@Entity
@Table(name = "itemAttribute")
@NamedQuery(
        name = "ItemAttribute.findSimilarItems",
        query = "SELECT a FROM ItemAttribute a WHERE a.attributeName LIKE :attName AND a.attributeValue LIKE :attValue AND a.secondaryName LIKE :secName AND a.secondaryValue LIKE :secValue"
)
public class ItemAttribute implements Serializable {

    private int id;
    private String attributeName;
    private String attributeValue;
    private String secondaryName;
    private String secondaryValue;
    private List<TrackedItem> items;

    //GETTERS
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    @Column(nullable = false)
    public String getAttributeName() {
        return attributeName;
    }

    @Column(nullable = false)
    public String getAttributeValue() {
        return attributeValue;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public String getSecondaryValue() {
        return secondaryValue;
    }

    @OneToMany(mappedBy = "attribute")
    @MapsId("trackedItemId")
    public List<TrackedItem> getItems() {
        return items;
    }
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public void setSecondaryValue(String secondaryValue) {
        this.secondaryValue = secondaryValue;
    }

    public void setItems(List<TrackedItem> items) {
        this.items = items;
    }

}