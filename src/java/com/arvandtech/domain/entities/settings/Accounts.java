package com.arvandtech.domain.entities.settings;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Creates accounts. Used for management of users, user roles and login.
 * username - username for user. password - password, encrypted with SHA-265 by
 * container by default. role - used for account management by container,
 * contains user access permissions. email - email of user, used for sending
 * emails to user. contacntNo - contact number of user. active - determines of
 * account can be used to login. NOTE: not yet used, always set to true.
 * passwordReset - determines of software will ask user for new password when
 * they log in.
 *
 * @author Jonathan
 */

@Entity
public class Accounts implements Serializable {

    private String username;
    private String password;
    private String role;
    private String email;
    private String contactNo;
    private boolean active;
    private boolean passwordReset;

    /*Getters*/
    @Id
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPasswordReset() {
        return passwordReset;
    }

    /*Setters*/
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPasswordReset(boolean passwordReset) {
        this.passwordReset = passwordReset;
    }

}
