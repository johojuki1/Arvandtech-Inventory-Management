/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.entities.inventory;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author User
 */
@Embeddable
public class TrackedItemId implements Serializable {

    private int trackedId;
    private int attributeId;

    public void setId(int trackedId, int attributeId) {
        this.trackedId = trackedId;
        this.attributeId = attributeId;
    }

    //GETTERS
    @Column(name = "tracked_id")
    public int getTrackedId() {
        return trackedId;
    }

    @Column(name = "attribute_id")
    public int getAttributeId() {
        return attributeId;
    }
    
    //SETTERS

    public void setTrackedId(int trackedId) {
        this.trackedId = trackedId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }
    
}
