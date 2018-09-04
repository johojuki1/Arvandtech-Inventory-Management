/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arvandtech.utilities;

/**
 * Class used to store and display inventory items in a table.
 * id : id of item in table.
 * itemId : id of item in database.
 * type : type of item that is displayed in table.
 * backgroundColor : background color of row displayed in table.
 * col1 - col10 : string to be displayed in the table.
 * @author Jonathan
 */
public class SearchItem {

    int id;
    int itemId;
    String type;
    String backgroundColor;
    String col1;
    String col2;
    String col3;
    String col4;
    String col5;
    String col6;
    String col7;
    String col8;
    String col9;
    String col10;

    //GETTERS

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getType() {
        return type;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getCol1() {
        return col1;
    }

    public String getCol2() {
        return col2;
    }

    public String getCol3() {
        return col3;
    }

    public String getCol4() {
        return col4;
    }

    public String getCol5() {
        return col5;
    }

    public String getCol6() {
        return col6;
    }

    public String getCol7() {
        return col7;
    }

    public String getCol8() {
        return col8;
    }

    public String getCol9() {
        return col9;
    }

    public String getCol10() {
        return col10;
    }
    
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }

    public void setCol6(String col6) {
        this.col6 = col6;
    }

    public void setCol7(String col7) {
        this.col7 = col7;
    }

    public void setCol8(String col8) {
        this.col8 = col8;
    }

    public void setCol9(String col9) {
        this.col9 = col9;
    }

    public void setCol10(String col10) {
        this.col10 = col10;
    }
}
