/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

/**
 *
 * @author User
 */
@Entity
public class TrackedItem implements Serializable {

    private int id;
    private Tracked tracked;
    private ItemAttribute attribute;
    private int itemOrder;

    //GETTERS
    @Id
    public int getId() {
        return id;
    }

    @ManyToOne
    @MapsId("trackedId")
    public Tracked getTracked() {
        return tracked;
    }

    @ManyToOne
    @MapsId("attributeId")
    public ItemAttribute getAttribute() {
        return attribute;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setTracked(Tracked tracked) {
        this.tracked = tracked;
    }

    public void setAttribute(ItemAttribute attribute) {
        this.attribute = attribute;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

}
