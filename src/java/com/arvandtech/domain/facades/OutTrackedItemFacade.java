/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.inventory.TrackedItem;
import com.arvandtech.domain.entities.outinventory.OutItemAttribute;
import com.arvandtech.domain.entities.outinventory.OutTracked;
import com.arvandtech.domain.entities.outinventory.OutTrackedItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class OutTrackedItemFacade extends AbstractFacade<OutTrackedItem> {

    //Function creates itemAttribute objects does not attach any relationships yet.
    public OutTrackedItem convertToOut(TrackedItem trackedItem, OutTracked outTracked, OutItemAttribute outAtt) {
        OutTrackedItem outItem = new OutTrackedItem();
        outItem.setAttribute(outAtt);
        outItem.setTracked(outTracked);
        outItem.setItemOrder(trackedItem.getItemOrder());
        return outItem;
    }
    
    public OutTrackedItem returnedCreate(OutTrackedItem item) {
        em.persist(item);
        return item;
    }
    
    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutTrackedItemFacade() {
        super(OutTrackedItem.class);
    }
    
}
