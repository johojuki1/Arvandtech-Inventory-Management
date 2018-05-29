package com.arvandtech.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author User
 */
@Entity
public class SecondaryAttribute implements Serializable {

    private int secondaryAttributeId;
    private String secondaryAttributeName;
    private int secondaryOrder;
    
    //GETTERS

    @Id
    @GeneratedValue
    public int getSecondaryAttributeId() {
        return secondaryAttributeId;
    }

    @Column(nullable = false)
    public String getSecondaryAttributeName() {
        return secondaryAttributeName;
    }

    public int getSecondaryOrder() {
        return secondaryOrder;
    }
    
    //SETTERS

    public void setSecondaryAttributeId(int secondaryAttributeId) {
        this.secondaryAttributeId = secondaryAttributeId;
    }

    public void setSecondaryAttributeName(String secondaryAttributeName) {
        this.secondaryAttributeName = secondaryAttributeName;
    }

    public void setSecondaryOrder(int secondaryOrder) {
        this.secondaryOrder = secondaryOrder;
    }
}
