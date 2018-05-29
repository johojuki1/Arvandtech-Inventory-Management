package com.arvandtech.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
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
