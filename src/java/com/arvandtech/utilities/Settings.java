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
        groups.add("Showroom");
        groups.add("Mezzanine");
        groups.add("Laptop");
        groups.add("Office");
        groups.add("P1");
        groups.add("P2");
        groups.add("P3");
        groups.add("P4");
        groups.add("P5");
        groups.add("P6");
        groups.add("P7");
        groups.add("P8");
        groups.add("P9");
        groups.add("P10");
        groups.add("P11");
        groups.add("P12");
        groups.add("P13");
        groups.add("P14");
        groups.add("P15");
        groups.add("P16");
        groups.add("P17");
        groups.add("P18");
        groups.add("P19");
        groups.add("P20");
        groups.add("P21");
        groups.add("P22");
        groups.add("P23");
        groups.add("P24");
        groups.add("P25");
        groups.add("P26");
        groups.add("P27");
        groups.add("P28");
        groups.add("P29");
        groups.add("P30");
        groups.add("P31");
        groups.add("P32");
        groups.add("P33");
        groups.add("P34");
        groups.add("P35");
        groups.add("P36");
        groups.add("P37");
        groups.add("P38");
        groups.add("P39");
        groups.add("P40");
        groups.add("P41");
        groups.add("P42");
        groups.add("P43");
        groups.add("P44");
        groups.add("P45");
        groups.add("P46");
        groups.add("P47");
        groups.add("P48");
        groups.add("P49");
        groups.add("P50");
        groups.add("P51");
        groups.add("P52");
        groups.add("P53");
        groups.add("P54");
        groups.add("P55");
        groups.add("P56");
        groups.add("P57");
        groups.add("P58");
        groups.add("P59");
        groups.add("P60");
        groups.add("P61");
        groups.add("P62");
        groups.add("P63");
        groups.add("P64");
        groups.add("P65");
        groups.add("P66");
        groups.add("P67");
        groups.add("P68");
        groups.add("P69");
        groups.add("P70");
        return groups;
    }
    
    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        locations.add("Unknown");
        locations.add("Showroom");
        locations.add("Mezzanine");
        locations.add("Laptop");
        locations.add("Office");
        locations.add("W1U");
        locations.add("W1M");
        locations.add("W1D");
        locations.add("W2U");
        locations.add("W2M");
        locations.add("W2D");
        locations.add("W3U");
        locations.add("W3M");
        locations.add("W3D");
        locations.add("W4U");
        locations.add("W4M");
        locations.add("W4D");
        locations.add("W5U");
        locations.add("W5M");
        locations.add("W5D");
        locations.add("W6U");
        locations.add("W6M");
        locations.add("W6D");
        locations.add("W7U");
        locations.add("W7M");
        locations.add("W7D");
        locations.add("W8U");
        locations.add("W8M");
        locations.add("W8D");
        return locations;
    }

    //GETTERS
    public int getMAX_ATTRIBUTES() {
        return MAX_ATTRIBUTES;
    }

    //SETTERS
}
