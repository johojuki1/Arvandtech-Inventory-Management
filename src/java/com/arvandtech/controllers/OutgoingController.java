/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.inventory.Tracked;
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
    private String barcode;
    private String outStatus;
    private String customerNo;
    private String addedDescription;
    private ArrayList<ScanBarcodeTable> outgoingItems;
    private ScanBarcodeTable selectedItem;
    private int dialogNavigator;
    private final String OUTGOINGLINK = "/addStock/pageElements/";

    public OutgoingController() {
        outgoingItems = new ArrayList<>();
        barcode = "";
    }

    public List<String> fetchStatusDropdown() {
        return new Settings().getOutgoingStatus();
    }

    public void addBarcodeItem() {
        ArrayList<Tracked> foundItems = trackedFacade.findFromBarcode(barcode);
        Tracked trackedFoundItem = manageFoundResults(foundItems);
        if (trackedFoundItem != null) {
            ScanBarcodeTable foundItem = new ScanBarcodeTable(trackedFoundItem);
            foundItem.setDescription(trackedFoundItem.attributesToString());
            outgoingItems.add(foundItem);
            barcode = "";
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
            default:
                return "";
        }
    }

    public void clearBarcode() {
        barcode = "";
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
}
