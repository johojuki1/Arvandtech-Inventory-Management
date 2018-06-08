/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.Attribute;
import com.arvandtech.domain.entities.SelectableBox;
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
public class AttributeFacade extends AbstractFacade<Attribute> {

    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    public void swapOrder(int id1, int id2) throws Exception {
        Attribute att1 = find(id1);
        Attribute att2 = find(id2);
        int tmpOrder = att1.getAttributeOrder();
        att1.setAttributeOrder(att2.getAttributeOrder());
        att2.setAttributeOrder(tmpOrder);
        edit(att1);
        edit(att2);
    }

    public Attribute returnedCreate(String name, boolean selectable, ArrayList<SelectableBox> selectableList) {
        Attribute tmpAttribute = new Attribute();
        tmpAttribute.setAttributeName(name);
        tmpAttribute.setSelectable(selectable);
        tmpAttribute.setSelectableBox(selectableList);
        create(tmpAttribute);
        return tmpAttribute;
    }

    public void safeDelete(int attributeId, List<Attribute> attList) throws Exception {
        Attribute attribute = find(attributeId);
        for (Attribute tmpAttribute : attList) {
            if (tmpAttribute.getAttributeOrder() > attribute.getAttributeOrder()) {
                tmpAttribute.setAttributeOrder(tmpAttribute.getAttributeOrder() - 1);
                edit(tmpAttribute);
            }
        }
        remove(attribute);
    }

    public void safeEdit(int id, String name, boolean selectable) throws Exception {
        Attribute attribute = find(id);
        attribute.setAttributeName(name);
        attribute.setSelectable(selectable);
        edit(attribute);
    }

    public void addSelectable(int id, SelectableBox selectable) {
        Attribute tmpAttribute = find(id);
        selectable.setSelectableOrder(tmpAttribute.getSelectableBox().size() + 1);
        List tmpSelectableList = tmpAttribute.getSelectableBox();
        tmpSelectableList.add(selectable);
        tmpAttribute.setSelectableBox(tmpSelectableList);
        edit(tmpAttribute);
        em.merge(selectable);
    }

    public boolean removeSelectable(int attributeId, int selectableId) {
        try {
            Attribute tmpAtt = find(attributeId);
            List<SelectableBox> tmpSelectList = tmpAtt.getSelectableBox();
            for (SelectableBox tmpSelectable : tmpSelectList) {
                if (tmpSelectable.getSelectableBoxId() == selectableId) {
                    tmpSelectList.remove(tmpSelectable);
                    tmpAtt.setSelectableBox(tmpSelectList);
                    edit(tmpAtt);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttributeFacade() {
        super(Attribute.class);
    }

}
