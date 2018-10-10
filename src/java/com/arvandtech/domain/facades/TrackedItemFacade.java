/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.TrackedItem;
import com.arvandtech.domain.entities.TrackedItemId;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class TrackedItemFacade extends AbstractFacade<TrackedItem> {

    public TrackedItem returnedCreate(TrackedItem item) {
        em.persist(item);
        return item;
    }
    
    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public TrackedItemFacade() {
        super(TrackedItem.class);
    }
    
}
