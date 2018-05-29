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
import javax.ws.rs.NotFoundException;

/**
 *
 * @author User
 */
@Stateless
public class ItemTypeFacade extends AbstractFacade<ItemType> {

    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    public void safeEdit(int id, String name) throws Exception {
        ItemType tmpItem = find(id);
        tmpItem.setTypeName(name);
        edit(tmpItem);
    }

    public void safeCreate(ItemType newItem) throws Exception {
        newItem.setItemOrder(count() + 1);
        create(newItem);
    }

    public void addAttribute(int id, Attribute attribute) {
        ItemType tmpItem = find(id);
        attribute.setAttributeOrder(tmpItem.getAttribute().size() + 1);
        List tmpAttList = tmpItem.getAttribute();
        tmpAttList.add(attribute);
        tmpItem.setAttribute(tmpAttList);
        edit(tmpItem);
        em.merge(attribute);
        String s = "s";
    }

    public boolean removeAttribute(int itemId, int attributeId) {
        try {
            ItemType tmpItem = find(itemId);
            List<Attribute> tmpAttList = tmpItem.getAttribute();
            for (Attribute tmpAttribute : tmpAttList) {
                if (tmpAttribute.getAttributeId() == attributeId) {
                    tmpAttList.remove(tmpAttribute);
                    tmpItem.setAttribute(tmpAttList);
                    edit(tmpItem);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void safeDelete(int id) throws Exception {
        ItemType item = find(id);
        for (ItemType tmpItem : findAll()) {
            if (tmpItem.getItemOrder() > item.getItemOrder()) {
                tmpItem.setItemOrder(tmpItem.getItemOrder() - 1);
                edit(tmpItem);
            }
        }
        remove(item);
    }

    public void moveUp(int id) throws Exception {
        ItemType item1 = find(id);
        ItemType item2 = new ItemType();
        if (!(item1.getItemOrder() <= 1)) {
            for (ItemType tmpItem : findAll()) {
                if (tmpItem.getItemOrder() == item1.getItemOrder() - 1) {
                    item2 = tmpItem;
                }
            }
            if (item2.getItemTypeId() != 0) {
                swapOrder(item1, item2);
            } else {
                throw new NotFoundException("Item with one lower index was not found. Order Swapping failed.");
            }
        }
    }

    public void moveDown(int id) throws Exception {
        ItemType item1 = find(id);
        ItemType item2 = new ItemType();
        if (!(item1.getItemOrder() >= count())) {
            for (ItemType tmpItem : findAll()) {
                if (tmpItem.getItemOrder() == item1.getItemOrder() + 1) {
                    item2 = tmpItem;
                }
            }
            if (item2.getItemTypeId() != 0) {
                swapOrder(item1, item2);
            } else {
                throw new NotFoundException("Item with one lower index was not found. Order Swapping failed.");
            }
        }
    }

    private void swapOrder(ItemType item1, ItemType item2) throws Exception {
        int tmpOrder = item1.getItemOrder();
        item1.setItemOrder(item2.getItemOrder());
        item2.setItemOrder(tmpOrder);
        edit(item1);
        edit(item2);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemTypeFacade() {
        super(ItemType.class);
    }

}
