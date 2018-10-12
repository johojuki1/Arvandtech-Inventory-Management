package com.arvandtech.domain.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class is part of the system which allows administrators to add, edit or remove item types.
 * Stores the type of item the user wants to add as well as which attributes are attached to the item.
 * itemTypeId: id of itemType.
 * typeName: name of the type of item user wants to add. Eg Computers, Laptops.
 * itemOrder: Order in which this item appears to users who are adding to the inventory.
 * attribute: links to connected attributes to this item. contains Attribute class.
 * @author Jonathan
 */
@Entity
public class ItemType implements Serializable {

    private int itemTypeId;
    private String typeName;
    private boolean deleteable;
    private int itemOrder;
    private List<Attribute> attribute;

    //GETTERS
    @Id
    @GeneratedValue
    public int getItemTypeId() {
        return itemTypeId;
    }

    @Column(nullable = false)
    public String getTypeName() {
        return typeName;
    }

    @Column(nullable = false)
    public boolean isDeleteable() {
        return deleteable;
    }

    @OneToMany
    public List<Attribute> getAttribute() {
        return attribute;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    //SETTERS
    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setDeleteable(boolean deleteable) {
        this.deleteable = deleteable;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }
}
