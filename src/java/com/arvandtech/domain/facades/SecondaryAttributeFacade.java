/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.settings.SecondaryAttribute;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author User
 */
@Stateless
public class SecondaryAttributeFacade extends AbstractFacade<SecondaryAttribute> {

    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecondaryAttribute returnedCreate(String name) {
        SecondaryAttribute tmpSecondary = new SecondaryAttribute();
        tmpSecondary.setSecondaryAttributeName(name);
        create(tmpSecondary);
        return tmpSecondary;
    }

    public SecondaryAttributeFacade() {
        super(SecondaryAttribute.class);
    }

    public void safeDelete(int secondaryId, List<SecondaryAttribute> secondaryList) throws Exception {
        SecondaryAttribute secondary = find(secondaryId);
        for (SecondaryAttribute tmpSecondary : secondaryList) {
            if (tmpSecondary.getSecondaryAttributeId() > secondary.getSecondaryAttributeId()) {
                tmpSecondary.setSecondaryOrder(tmpSecondary.getSecondaryOrder() - 1);
                edit(tmpSecondary);
            }
        }
        remove(secondary);
    }

    public void swapOrder(int id1, int id2) throws Exception {
        SecondaryAttribute secondary1 = find(id1);
        SecondaryAttribute secondary2 = find(id2);
        int tmpOrder = secondary1.getSecondaryOrder();
        secondary1.setSecondaryOrder(secondary2.getSecondaryOrder());
        secondary2.setSecondaryOrder(tmpOrder);
        edit(secondary1);
        edit(secondary2);
    }

    public void safeEdit(int id, String name) throws Exception {
        SecondaryAttribute secondary = find(id);
        secondary.setSecondaryAttributeName(name);
        edit(secondary);
    }
}
