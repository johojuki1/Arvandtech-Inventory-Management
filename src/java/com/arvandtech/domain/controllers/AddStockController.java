/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.controllers;

import com.arvandtech.domain.AttributeFacade;
import com.arvandtech.domain.ItemType;
import com.arvandtech.domain.ItemTypeFacade;
import com.arvandtech.domain.SecondaryAttributeFacade;
import com.arvandtech.domain.SelectableBoxFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Controller used to manage all functions used by the add stock function.
 *
 * @author User
 */
@Named("addStock")
@SessionScoped
public class AddStockController implements Serializable {

    @EJB
    private ItemTypeFacade itemFacade;
    
    private List<ItemType> allItems;
    private ItemType item;
    
    public List<ItemType> findAllItems()
    {
        return itemFacade.findAll();
    }
    
    public void clearItem() {
        item = new ItemType();
    }
    
    //GETTERS
    public ItemType getItem() {
        return item;
    }

    public List<ItemType> getAllItems() {
        return allItems;
    }
    

    //SETTERS
    public void setItem(ItemType item) {
        this.item = item;
    }

    public void setAllItems(List<ItemType> allItems) {
        this.allItems = allItems;
    }
}
