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
        status.add("Prepared");
        status.add("Unprocessed");
        return status;
    }
    
    public List<String> getOutgoingStatus() {
        List<String> status = new ArrayList<>();
        status.add("Sold");
        status.add("E-Waste");
        status.add("Other");
        return status;
    }

    public List<String> getCondition() {
        List<String> status = new ArrayList<>();
        status.add("Good (A)");
        status.add("Ok (B)");
        status.add("Clearance (C)");
        status.add("Missing Parts");
        status.add("Faulty");
        status.add("E-Waste");
        return status;
    }
    
    public List<String> getGroups() {
        List<String> groups = new ArrayList<>();
        groups.add("Unknown");
        groups.add("G1");
        groups.add("G2");
        groups.add("G3");
        groups.add("G4");
        groups.add("G5");
        groups.add("G6");
        groups.add("G7");
        groups.add("G8");
        groups.add("G9");
        groups.add("G10");
        groups.add("G11");
        groups.add("G12");
        groups.add("G13");
        groups.add("G14");
        groups.add("G15");
        groups.add("G16");
        groups.add("G17");
        groups.add("G18");        
        groups.add("G19");  
        groups.add("G20");  
        return groups;
    }
    
    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        locations.add("Unknown");
        locations.add("L1");
        locations.add("L2");
        locations.add("L3");
        locations.add("L4");
        locations.add("L5");
        locations.add("L6");
        locations.add("L7");
        locations.add("L8");
        locations.add("L9");
        locations.add("L10");
        locations.add("L11");
        locations.add("L12");
        locations.add("L13");
        locations.add("L14");
        locations.add("L15");
        locations.add("L16");
        locations.add("L17");
        locations.add("L18");        
        locations.add("L19");  
        locations.add("L20");  
        return locations;
    }

    //GETTERS
    public int getMAX_ATTRIBUTES() {
        return MAX_ATTRIBUTES;
    }

    //SETTERS
}
