/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Jonathan Ho
 */
@Entity
public class ItemAttribute implements Serializable{
    private int id;
    private String AttributeName;
    private String AttributeValue;
    private String SecondaryName;
    private String SecondaryValue;
    
    //GETTERS
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public String getAttributeValue() {
        return AttributeValue;
    }

    public String getSecondaryName() {
        return SecondaryName;
    }

    public String getSecondaryValue() {
        return SecondaryValue;
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

}
