/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.inventory.ItemAttribute;
import com.arvandtech.domain.entities.outinventory.OutItemAttribute;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class OutItemAttributeFacade extends AbstractFacade<OutItemAttribute> {

    public OutItemAttribute convertToOut(ItemAttribute item) {
        OutItemAttribute outAtt = new OutItemAttribute();
        outAtt.setAttributeName(item.getAttributeName());
        outAtt.setAttributeValue(item.getAttributeValue());
        outAtt.setSecondaryName(item.getSecondaryName());
        outAtt.setSecondaryValue(item.getSecondaryValue());
        return outAtt;
    }
    
    public OutItemAttribute checkAndAdd(OutItemAttribute item) {
        if (item.getSecondaryName() == null) {
            item.setSecondaryName("");
        }
        if (item.getSecondaryValue() == null) {
            item.setSecondaryValue("");
        }
        List<OutItemAttribute> foundItems = em.createNamedQuery("OutItemAttribute.findSimilarItems")
                .setParameter("attName", item.getAttributeName())
                .setParameter("attValue", item.getAttributeValue())
                .setParameter("secName", item.getSecondaryName())
                .setParameter("secValue", item.getSecondaryValue())
                .getResultList();
        if (foundItems.isEmpty()) {
            item.setId(0);
            create(item);
            return item;
        } else {
            return foundItems.get(0);
        }
    }
    
    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutItemAttributeFacade() {
        super(OutItemAttribute.class);
    }
    
}
