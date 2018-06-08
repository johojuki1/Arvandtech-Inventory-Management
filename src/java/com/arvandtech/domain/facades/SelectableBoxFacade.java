package com.arvandtech.domain.facades;

import com.arvandtech.domain.entities.SecondaryAttribute;
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
public class SelectableBoxFacade extends AbstractFacade<SelectableBox> {

    @PersistenceContext(unitName = "arvandtechintranet_V1PU")
    private EntityManager em;

    public SelectableBox returnedCreate(String name, boolean secondary, String secondaryName, ArrayList<SecondaryAttribute> secondaryList) {
        SelectableBox tmpSelectable = new SelectableBox();
        tmpSelectable.setName(name);
        tmpSelectable.setSecondary(secondary);
        tmpSelectable.setSecondaryName(secondaryName);
        tmpSelectable.setSecondaryAttribute(secondaryList);
        create(tmpSelectable);
        return tmpSelectable;
    }

    public void safeDelete(int selectableId, List<SelectableBox> selectList) throws Exception {
        SelectableBox selectable = find(selectableId);
        for (SelectableBox tmpSelectable : selectList) {
            if (tmpSelectable.getSelectableBoxId() > selectable.getSelectableBoxId()) {
                tmpSelectable.setSelectableOrder(tmpSelectable.getSelectableOrder() - 1);
                edit(tmpSelectable);
            }
        }
        remove(selectable);
    }

    public void swapOrder(int id1, int id2) throws Exception {
        SelectableBox select1 = find(id1);
        SelectableBox select2 = find(id2);
        int tmpOrder = select1.getSelectableOrder();
        select1.setSelectableOrder(select2.getSelectableOrder());
        select2.setSelectableOrder(tmpOrder);
        edit(select1);
        edit(select2);
    }

    public void safeEdit(int id, String name, boolean secondary, String secondaryName) throws Exception {
        SelectableBox selectable = find(id);
        selectable.setName(name);
        selectable.setSecondary(secondary);
        selectable.setSecondaryName(secondaryName);
        edit(selectable);
    }

    public void addSecondary(int id, SecondaryAttribute secondary) {
        SelectableBox tmpSelectable = find(id);
        List tmpSecondaryList = tmpSelectable.getSecondaryAttribute();
        secondary.setSecondaryOrder(tmpSecondaryList.size() + 1);
        tmpSecondaryList.add(secondary);
        tmpSelectable.setSecondaryAttribute(tmpSecondaryList);
        edit(tmpSelectable);
        em.merge(secondary);
    }

    public boolean removeSecondary(int selectableId, int secondaryId) {
        try {
            SelectableBox tmpSelect = find(selectableId);
            List<SecondaryAttribute> tmpSecondaryList = tmpSelect.getSecondaryAttribute();
            for (SecondaryAttribute tmpSecondary : tmpSecondaryList) {
                if (tmpSecondary.getSecondaryAttributeId() == secondaryId) {
                    tmpSecondaryList.remove(tmpSecondary);
                    tmpSelect.setSecondaryAttribute(tmpSecondaryList);
                    edit(tmpSelect);
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

    public SelectableBoxFacade() {
        super(SelectableBox.class);
    }

}
