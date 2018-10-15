/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities.outinventory;

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
@Table(name = "outTracked_outItemAttribute")
public class OutTrackedItem implements Serializable {

    private OutTrackedItemId id;
    private OutTracked tracked;
    private OutItemAttribute attribute;
    private int itemOrder;

    //GETTERS
    @EmbeddedId
    public OutTrackedItemId getId() {
        return id;
    }

    @ManyToOne
    @MapsId("trackedId")
    public OutTracked getTracked() {
        return tracked;
    }

    @ManyToOne
    @MapsId("attributeId")
    public OutItemAttribute getAttribute() {
        return attribute;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    //SETTERS
    public void setId(OutTrackedItemId id) {
        this.id = id;
    }

    public void setTracked(OutTracked tracked) {
        this.tracked = tracked;
    }

    public void setAttribute(OutItemAttribute attribute) {
        this.attribute = attribute;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

}
