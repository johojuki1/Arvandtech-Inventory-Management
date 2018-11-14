/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.inventory.ItemAttribute;
import com.arvandtech.domain.entities.inventory.Tracked;
import com.arvandtech.domain.entities.inventory.TrackedItem;
import com.arvandtech.domain.entities.outinventory.OutItemAttribute;
import com.arvandtech.domain.entities.outinventory.OutTracked;
import com.arvandtech.domain.entities.outinventory.OutTrackedItem;
import com.arvandtech.domain.facades.ItemAttributeFacade;
import com.arvandtech.domain.facades.OutItemAttributeFacade;
import com.arvandtech.domain.facades.OutTrackedFacade;
import com.arvandtech.domain.facades.OutTrackedItemFacade;
import com.arvandtech.domain.facades.TrackedFacade;
import com.arvandtech.utilities.ScanBarcodeTable;
import com.arvandtech.utilities.Settings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author User
 */
@Named("outgoing")
@SessionScoped
public class OutgoingController implements Serializable {

    @EJB
    TrackedFacade trackedFacade;

    @EJB
    ItemAttributeFacade attFacade;

    @EJB
    OutTrackedFacade outTrackedFacade;

    @EJB
    OutTrackedItemFacade outTrackedItemFacade;

    @EJB
    OutItemAttributeFacade outAttFacade;

    private String barcode;
    private String outStatus;
    private String customerNo;
    private String addedDescription;
    private ArrayList<ScanBarcodeTable> outgoingItems;
    private ScanBarcodeTable selectedItem;
    private int dialogNavigator;
    private final String OUTGOINGLINK = "/addStock/pageElements/";
    ArrayList<Tracked> foundItems;

    public void init() {
        barcode = "";
        outStatus = "";
        customerNo = "";
        addedDescription = "";
        outgoingItems = new ArrayList();
        selectedItem = new ScanBarcodeTable();
        dialogNavigator = 0;
        foundItems = new ArrayList();
    }

    public OutgoingController() {
        outgoingItems = new ArrayList<>();
        barcode = "";
    }

    public List<String> fetchStatusDropdown() {
        return new Settings().getOutgoingStatus();
    }

    public void addBarcodeItem() {
        foundItems = trackedFacade.findFromBarcode(barcode);
        Tracked trackedFoundItem = manageFoundResults(foundItems);
        if (trackedFoundItem != null) {
            addItemToTable(trackedFoundItem.getTrackedId());
        }
    }

    public void refreshTable() {
        for (ScanBarcodeTable tableItem : outgoingItems) {
            int index = outgoingItems.indexOf(tableItem);
            Tracked trackedFoundItem = trackedFacade.find(tableItem.getId());
            ScanBarcodeTable tempTableItem = new ScanBarcodeTable(trackedFoundItem);
            tempTableItem.setDescription(trackedFoundItem.attributesToString());
            outgoingItems.set(index, tempTableItem);
        }
    }

    public void deleteBarcodeItem() {
        outgoingItems.remove(selectedItem);
    }

    private Tracked manageFoundResults(ArrayList<Tracked> foundItems) {
        if (foundItems.isEmpty()) {
            PrimeFaces current = PrimeFaces.current();
            dialogNavigator = 2;
            current.executeScript("PF('outEditItemDialog').show();");
        } else if (foundItems.size() > 1) {
            PrimeFaces current = PrimeFaces.current();
            dialogNavigator = 5;
            current.executeScript("PF('outEditItemDialog').show();");
        } else {
            return foundItems.get(0);
        }
        return null;
    }

    public void addSuccess(boolean success) {
        if (success) {
            dialogNavigator = 0;
            addBarcodeItem();
        } else {
            FacesContext.getCurrentInstance().addMessage("outgoingTable", new FacesMessage(FacesMessage.SEVERITY_WARN, "Error:", "Item was not successifully added to database."));
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('outEditItemDialog').hide();");

    }

    public void setDialogAndShow(int i) {
        PrimeFaces current = PrimeFaces.current();
        dialogNavigator = i;
        current.executeScript("PF('outEditItemDialog').show();");
    }

    public String findDialogLink() {
        switch (dialogNavigator) {
            case 1:
                return "/outgoing/pageElements/editItem.xhtml";
            case 2:
                return "/outgoing/pageElements/notFound.xhtml";
            case 3:
                return "/outgoing/pageElements/layouts/addTypeList.xhtml";
            case 4:
                return "/outgoing/pageElements/layouts/addItem.xhtml";
            case 5:
                return "/outgoing/pageElements/layouts/multipleItems.xhtml";
            default:
                return "";
        }
    }

    public void clearBarcode() {
        barcode = "";
        PrimeFaces current = PrimeFaces.current();
        current.ajax().update("barcodeInput");
    }

    public void addItemToTable(int id) {
        Tracked trackedItem = trackedFacade.find(id);
        ScanBarcodeTable foundItem = new ScanBarcodeTable(trackedItem);
        foundItem.setDescription(trackedItem.attributesToString());
        outgoingItems.add(foundItem);
        barcode = "";
    }

    //This function removes old items from main database and adds to outgoing database.
    //function also returns navigation integer so the site navigates correctly.
    public int outgoingProcess() {
        int errors = 0;
        //check if empty, give error if so.
        if (outgoingItems.isEmpty()) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "No items have been scanned.", null);
            FacesContext.getCurrentInstance().addMessage("outgoingTable", facesMessage);
            return 1;
        }
        //loop through all outgoing items.
        for (ScanBarcodeTable outgoing : outgoingItems) {
            try {
                Tracked outgoingTracked = trackedFacade.find(outgoing.getId());
                removeTrackedItem(outgoingTracked);
                addOutTrackedItem(outgoingTracked);
            } catch (Exception e) {
                errors = errors + 1;
            }
        }
        FacesMessage facesMessage;
        if (errors == 0) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Items have been successifully moved.", null);
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Operation has been completed with " + errors + " errors.", null);
        }
        FacesContext.getCurrentInstance().addMessage("outgoingTable", facesMessage);
        init();
        return 0;
    }

    public void addOutTrackedItem(Tracked tracked) throws Exception {
        //First create objects with all converted values.
        //create outTracked
        OutTracked outTracked = outTrackedFacade.convertTrackedItem(tracked, customerNo, outStatus);
        outTracked.setAttributes(new ArrayList<>());
        outTracked = outTrackedFacade.returnedCreate(outTracked);
        //create outTrackedItem and itemAttributes
        for (TrackedItem trackedItem : tracked.getAttributes()) {
            OutItemAttribute outAtt = outAttFacade.convertToOut(trackedItem.getAttribute());
            outAtt = outAttFacade.checkAndAdd(outAtt);
            OutTrackedItem outItem = outTrackedItemFacade.convertToOut(trackedItem, outTracked, outAtt);
            outItem = outTrackedItemFacade.returnedCreate(outItem);
            if (outAtt.getItems() == null) {
                outAtt.setItems(new ArrayList<>());
            }
            outAtt.getItems().add(outItem);
            outAttFacade.edit(outAtt);
            outTracked.getAttributes().add(outItem);
        }
        outTracked.setDescription(outTracked.getDescription() + "\n" + addedDescription);
        outTrackedFacade.edit(outTracked);
    }

    //This function removes old item form database
    public void removeTrackedItem(Tracked outgoingTracked) throws Exception {
        //remove trackeditems from attribute.
        List<TrackedItem> attributeList = new ArrayList<>();
        attributeList.addAll(outgoingTracked.getAttributes());

        //Remove the Tracked item to be delete from ItemAttribute
        for (TrackedItem attribute : attributeList) {
            ItemAttribute itemAtt = attribute.getAttribute();
            itemAtt.getItems().remove(attribute);
            attFacade.edit(itemAtt);
        }
        trackedFacade.remove(outgoingTracked);
    }

    //GETTERS
    public String getOutStatus() {
        return outStatus;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public String getAddedDescription() {
        return addedDescription;
    }

    public ArrayList<ScanBarcodeTable> getOutgoingItems() {
        return outgoingItems;
    }

    public ScanBarcodeTable getSelectedItem() {
        return selectedItem;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getOUTGOINGLINK() {
        return OUTGOINGLINK;
    }

    public ArrayList<Tracked> getFoundItems() {
        return foundItems;
    }

    //SETTERS
    public void setOutStatus(String outStatus) {
        this.outStatus = outStatus;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public void setAddedDescription(String addedDescription) {
        this.addedDescription = addedDescription;
    }

    public void setOutgoingItems(ArrayList<ScanBarcodeTable> outgoingItems) {
        this.outgoingItems = outgoingItems;
    }

    public void setSelectedItem(ScanBarcodeTable selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setDialogNavigator(int dialogNavigator) {
        this.dialogNavigator = dialogNavigator;
    }

    public void setFoundItems(ArrayList<Tracked> foundItems) {
        this.foundItems = foundItems;
    }
}
