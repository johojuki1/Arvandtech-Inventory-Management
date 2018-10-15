/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.controllers;

import com.arvandtech.domain.entities.settings.Accounts;
import com.arvandtech.domain.facades.AccountsFacade;
import com.arvandtech.utilities.Security;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Controller used to manage all functions concerning account management. Used
 * by Admin or superusers.
 *
 * @author Jonathan
 */
//password - temporary storage of password, used in password edit function of edit account. 
//passwordConfirm - storage of confirmation password, used for confirming passwords. 
//List<Accounts> - temporary storage of list of account shown in the manage accounts page. 
//accounts - storage of account being edited/added. Since this class is session scoped care must be taken to remove this at the right times. 
@Named("accountsController")
@SessionScoped
public class AccountsController implements Serializable {

    @EJB
    private AccountsFacade accountsFacade;

    private String password;
    private String passwordConfirm;
    private List<Accounts> accounts;
    private Accounts account;

    public AccountsController() {
        account = new Accounts();
        account.setActive(true);
    }

    /*
    Initiates stored values.
     */
    public void initAccounts() {
        account = new Accounts();
        account.setActive(true);
        passwordConfirm = "";
    }

    /*
    Creates a new account.
    Returns page index to determine next page. 0 - returns to account management page. 1 - remains on create account page.
     */
    public int createAccount() {
        if (passwordConfirm.equals(account.getPassword())) {
            try {
                account.setPassword(new Security().hash256(account.getPassword()));
                accountsFacade.create(account);
                return 0;
            } catch (EJBException e) {
                //value 1 is the breate account page. If there are errors remain on the create account page.
                FacesContext.getCurrentInstance().addMessage("username", new FacesMessage("Username already exists."));
            } catch (NoSuchAlgorithmException e) {
                FacesContext.getCurrentInstance().addMessage("addUser", new FacesMessage("Password encryption failed."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("confirmPassword", new FacesMessage("Confirmation password does not match password."));
        }
        return 1;
    }

    /*
    Edits account. Updates account in database with the one stored locally.
     */
    public void editAccount() {
        try {
            accountsFacade.edit(account);
        } catch (Exception e) {
            //error page
        }
    }

    /*
    Change password. If passwordConfirm and password are correct, it will change the password in the locally stored account object.
    Returns page index to determine next page. 2 - returns to edit accounts page. 3 - remains on change password page.
     */
    public int changePassword() {
        if (passwordConfirm.equals(password)) {
            try {
                account.setPassword(new Security().hash256(password));
                FacesContext.getCurrentInstance().addMessage("edit", new FacesMessage("Password has been changed"));
                //If there are no errors return to edit page.
                return 2;
            } catch (NoSuchAlgorithmException e) {
                FacesContext.getCurrentInstance().addMessage("confirmPassword", new FacesMessage("Password encryption failed."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("confirmPassword", new FacesMessage("Confirmation password does not match password."));
        }
        //if error occurs, remain on the chage password page.
        return 3;
    }

    /*
    Deletes the locally stored account object from database.
     */
    public void deleteAccount() {
        if (account != null) {
            accountsFacade.remove(account);
        } else {
            FacesContext.getCurrentInstance().addMessage("accountsTable", new FacesMessage("No account is selected for deleteion."));
        }
    }

    /*
    Updates the list of accounts from values in database.
     */
    public void updateAccounts() {
        try {
            initAccounts();
            accounts = accountsFacade.findAll();
        } catch (EJBException e) {
            //FacesContext.getCurrentInstance().addMessage("username", new FacesMessage("Username already exists."));
        }
    }

    /*
    Checks if account has been selected. Used in Edit function
    If not selected stays on management page, if selected goes to accountedit page.
     */
    public int editAccountSelected() {
        if (account == null) {
            FacesContext.getCurrentInstance().addMessage("accountsTable", new FacesMessage("No account is selected for edit."));
            return 0;
        } else {
            return 2;
        }
    }

    //GETTERS
    public Accounts getAccount() {
        return account;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public String getPassword() {
        return password;
    }

    //SETTERS
    public void setAccount(Accounts account) {
        this.account = account;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
