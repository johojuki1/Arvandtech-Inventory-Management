package com.arvandtech.domain.controllers;

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

    private final String WEBLINK = "/settings/";
    private int mainPageIndex;
    private int settingsIndex;
    private int accountIndex;

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
    Stores current page of account management system.
     */
    public void setAccountIndex(int index) {
        accountIndex = index;
    }

    /*
    Returns user's current location within the account management system. 
     */
    public String getAccountLink() {
        switch (accountIndex) {
            case 0:
                return WEBLINK + "accountManagement/accountManagement.xhtml";
            case 1:
                return WEBLINK + "accountManagement/createAccount.xhtml";
            case 2:
                return WEBLINK + "accountManagement/editAccount.xhtml";
            case 3:
                return WEBLINK + "accountManagement/changePassword.xhtml";
            default:
                accountIndex = 0;
                return WEBLINK + "accountManagement/accountManagement.xhtml";
        }
    }

    //GETTERS
    public int getMainPageIndex() {
        return mainPageIndex;
    }

    public int getSettingsIndex() {
        return settingsIndex;
    }

    //SETTERS
    public void setMainPageIndex(int mainPageIndex) {
        this.mainPageIndex = mainPageIndex;
    }

    public void setSettingsIndex(int settingsIndex) {
        this.settingsIndex = settingsIndex;
    }
}
