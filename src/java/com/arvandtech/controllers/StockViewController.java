/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.Tracked;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.utilities.DatabaseConverter;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author User
 */
@Named("stockView")
@SessionScoped
public class StockViewController implements Serializable{
    
    @EJB
    private TrackedFacade trackedFacade;
    
    private DatabaseConverter dConverter = new DatabaseConverter();
    private ArrayList<Tracked> trackedItems;
    
    public boolean findAll()
    {
        trackedItems = new ArrayList<>(trackedFacade.findAll());
        dConverter.sort(trackedItems);
        return true;
    }
}
