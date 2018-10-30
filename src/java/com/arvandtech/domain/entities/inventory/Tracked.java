/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities.inventory;

import com.arvandtech.domain.entities.settings.Attribute;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author User
 */
@Entity
@Table(name = "tracked")
@NamedQuery(
        name = "Tracked.findFromBarcode",
        query = "SELECT a FROM Tracked a WHERE a.barcode LIKE :barcode"
)
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

    //FUNCTIONS
    public String attributesToString() {
        String attString = "";
        attributes.sort(Comparator.comparing(TrackedItem::getItemOrder));
        for (TrackedItem attribute : attributes) {
            attString = attString + attribute.getAttribute().getAttributeValue() + ", ";
        }
        return attString.substring(0, attString.length() - 2);
    }

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
        if (attributes != null) {
            attributes.sort(Comparator.comparing(TrackedItem::getItemOrder));
        }
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
