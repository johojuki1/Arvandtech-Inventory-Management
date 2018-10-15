/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.inventory.ItemAttribute;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class ItemAttributeFacade extends AbstractFacade<ItemAttribute> {

    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    public ItemAttribute checkAndAdd(ItemAttribute item) {
        if (item.getSecondaryName() == null) {
            item.setSecondaryName("");
        }
        if (item.getSecondaryValue() == null) {
            item.setSecondaryValue("");
        }
        List<ItemAttribute> foundItems = em.createNamedQuery("ItemAttribute.findSimilarItems")
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemAttributeFacade() {
        super(ItemAttribute.class);
    }

}
