/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "tracked_itemAttribute")
public class TrackedItem implements Serializable {

    private TrackedItemId id;
    private Tracked tracked;
    private ItemAttribute attribute;
    private int itemOrder;

    //GETTERS
    @EmbeddedId
    public TrackedItemId getId() {
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
    public void setId(TrackedItemId id) {
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
