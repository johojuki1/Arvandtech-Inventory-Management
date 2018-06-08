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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jonathan Ho
 */
@Entity
@Table(name = "itemAttribute")
public class ItemAttribute implements Serializable{
    private int id;
    private String AttributeName;
    private String AttributeValue;
    private String SecondaryName;
    private String SecondaryValue;
    private List<TrackedItem> items;
    
    //GETTERS
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    @Column(nullable = false)
    public String getAttributeName() {
        return AttributeName;
    }

    @Column(nullable = false)
    public String getAttributeValue() {
        return AttributeValue;
    }

    public String getSecondaryName() {
        return SecondaryName;
    }

    public String getSecondaryValue() {
        return SecondaryValue;
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

    public void setAttributeName(String AttributeName) {
        this.AttributeName = AttributeName;
    }

    public void setAttributeValue(String AttributeValue) {
        this.AttributeValue = AttributeValue;
    }

    public void setSecondaryName(String SecondaryName) {
        this.SecondaryName = SecondaryName;
    }

    public void setSecondaryValue(String SecondaryValue) {
        this.SecondaryValue = SecondaryValue;
    }

    public void setItems(List<TrackedItem> items) {
        this.items = items;
    }

}
