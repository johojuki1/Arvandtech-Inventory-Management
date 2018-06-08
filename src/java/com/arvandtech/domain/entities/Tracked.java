/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
@Table(name = "tracked")
public class Tracked implements Serializable {

    private int trackedId;
    private String itemTypeName;
    private String barcode;
    private String status;
    private String itemCondition;
    private Date dateAdded;
    private String description;
    private String orderNum;
    private List<TrackedItem> attributes;

    //GETTERS
    @Id
    @GeneratedValue
    public int getTrackedId() {
        return trackedId;
    }

    @Column(nullable = false)
    public String getItemTypeName() {
        return itemTypeName;
    }

    @Column(nullable = false)
    public String getBarcode() {
        return barcode;
    }

    @Column(nullable = false)
    public String getStatus() {
        return status;
    }

    @Column(nullable = false)
    public String getItemCondition() {
        return itemCondition;
    }

    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDateAdded() {
        return dateAdded;
    }

    @Column(nullable = false)
    public String getOrderNum() {
        return orderNum;
    }

    public String getDescription() {
        return description;
    }

    @OneToMany(mappedBy = "tracked")
    @MapsId("trackedItemId")
    public List<TrackedItem> getAttributes() {
        return attributes;
    }

    //SETTERS
    public void setTrackedId(int trackedId) {
        this.trackedId = trackedId;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttributes(List<TrackedItem> attributes) {
        this.attributes = attributes;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
