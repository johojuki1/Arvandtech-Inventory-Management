/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain;

import java.util.ArrayList;
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
}
