/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.inventory.Tracked;
import com.arvandtech.domain.entities.outinventory.OutTracked;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class OutTrackedFacade extends AbstractFacade<OutTracked> {

    //This function converts all items but leaves attribute field empty.
    public OutTracked convertTrackedItem(Tracked tracked, String invoiceNum, String status) {
        OutTracked returnOutTracked = new OutTracked();
        returnOutTracked.setBarcode(tracked.getBarcode());
        returnOutTracked.setItemTypeName(tracked.getItemTypeName());
        returnOutTracked.setStatus(status);
        returnOutTracked.setItemCondition(tracked.getItemCondition());
        returnOutTracked.setDateAdded(tracked.getDateAdded());
        returnOutTracked.setDescription(tracked.getDescription());
        returnOutTracked.setOrderNum(tracked.getOrderNum());
        returnOutTracked.setDateOut(new Date());
        returnOutTracked.setInvoiceNum(invoiceNum);
        return returnOutTracked;
    }
    
    //This function creates an out tracked item and returns the new created item.
    public OutTracked returnedCreate(OutTracked item) {
        em.persist(item);
        return item;
    }
    
    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutTrackedFacade() {
        super(OutTracked.class);
    }
    
}
