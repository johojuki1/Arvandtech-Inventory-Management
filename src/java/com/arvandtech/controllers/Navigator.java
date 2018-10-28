package com.arvandtech.controllers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 * The Navigator module remembers the location and pages the user is using for
 * all the tabs and variants of pages of each tab.
 *
 * @author Jonathan
 */
@Named("navigator")
@SessionScoped
public class Navigator implements Serializable {

    private final String SETTINGSLINK = "/settings/";
    private final String ADDSTOCKLINK = "/addStock/";
    private final String INVENTORYMANAGEMENTLINK = "/inventoryManagement/";
    private final String OUTGOINGLINK = "/outgoing/";
    private int mainPageIndex;
    private int settingsIndex;
    private int accountIndex;
    private int addStockIndex;
    private int outgoingIndex;
    private int inventoryManagementIndex;

    /*
    Storage of current location of main tab system. (The one that is permanately visible of left of all pages.
     */
    public void mainTabChange(TabChangeEvent event) {
        TabView tabView = (TabView) event.getComponent();
        mainPageIndex = tabView.getChildren().indexOf(event.getTab());
    }

    /*
    Storage of current location of main tab system. (The one that is permanately visible of left of all pages.
     */
    public void settingsTabChange(TabChangeEvent event) {
        TabView tabView = (TabView) event.getComponent();
        settingsIndex = tabView.getChildren().indexOf(event.getTab());
    }

    /*
    Returns user's current location within the account management system. 
     */
    public String getAccountLink() {
        switch (accountIndex) {
            case 0:
                return SETTINGSLINK + "accountManagement/accountManagement.xhtml";
            case 1:
                return SETTINGSLINK + "accountManagement/createAccount.xhtml";
            case 2:
                return SETTINGSLINK + "accountManagement/editAccount.xhtml";
            case 3:
                return SETTINGSLINK + "accountManagement/changePassword.xhtml";
            default:
                accountIndex = 0;
                return SETTINGSLINK + "accountManagement/accountManagement.xhtml";
        }
    }

    /*
    Returns user's current location within the outgoing pages. 
     */
    public String getOutgoingLink() {
        switch (outgoingIndex) {
            case 0:
                return OUTGOINGLINK + "pageElements/chooseAttributes.xhtml";
            case 1:
                return OUTGOINGLINK + "pageElements/scanStock.xhtml";
            default:
                addStockIndex = 0;
                return OUTGOINGLINK + "pageElements/chooseAttributes.xhtml";
        }
    }

    /*
    Returns user's current location within the add stock pages. 
     */
    public String getAddStockLink() {
        switch (addStockIndex) {
            case 0:
                return ADDSTOCKLINK + "pageElements/typeSelect.xhtml";
            case 1:
                return ADDSTOCKLINK + "pageElements/chooseAttributes.xhtml";
            case 2:
                return ADDSTOCKLINK + "pageElements/scanStock.xhtml";
            case 3:
                return ADDSTOCKLINK + "pageElements/addSuccessiful.xhtml";
            default:
                addStockIndex = 0;
                return ADDSTOCKLINK + "pageElements/typeSelect.xhtml";
        }
    }

    /*
    Returns user's current location within the inventory management pages.
     */
    public String getInventoryManagementLink() {
        switch (inventoryManagementIndex) {
            case 0:
                return INVENTORYMANAGEMENTLINK + "pageElements/managementTable.xhtml";
            default:
                inventoryManagementIndex = 0;
                return INVENTORYMANAGEMENTLINK + "pageElements/managementTable.xhtml";
        }
    }

    public void setAddStockIndex(int addStockIndex, boolean b) {
        if (b) {
            this.addStockIndex = addStockIndex;
        }
    }

    //GETTERS
    public int getMainPageIndex() {
        return mainPageIndex;
    }

    public int getSettingsIndex() {
        return settingsIndex;
    }

    public int getOutgoingIndex() {
        return outgoingIndex;
    }

    //SETTERS
    public void setMainPageIndex(int mainPageIndex) {
        this.mainPageIndex = mainPageIndex;
    }

    public void setSettingsIndex(int settingsIndex) {
        this.settingsIndex = settingsIndex;
    }

    public void setAccountIndex(int accountIndex) {
        this.accountIndex = accountIndex;
    }

    public void setAddStockIndex(int addStockIndex) {
        this.addStockIndex = addStockIndex;
    }

    public void setInventoryManagementIndex(int inventoryManagementIndex) {
        this.inventoryManagementIndex = inventoryManagementIndex;
    }

    public void setOutgoingIndex(int outgoingIndex) {
        this.outgoingIndex = outgoingIndex;
    }
}
