/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Settings {

    private final int MAX_ATTRIBUTES = 10;

    public List<String> getStatus() {
        List<String> status = new ArrayList<>();
        status.add("Processed");
        status.add("Unprocessed");
        return status;
    }

    public List<String> getCondition() {
        List<String> status = new ArrayList<>();
        status.add("Good Condition (A)");
        status.add("Ok Condition (B)");
        status.add("Clearance (C)");
        status.add("Missing Parts");
        status.add("Faulty");
        status.add("E-Waste");
        return status;
    }

    //GETTERS
    public int getMAX_ATTRIBUTES() {
        return MAX_ATTRIBUTES;
    }

    //SETTERS
}
