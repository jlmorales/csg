/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.TAData;
import javafx.beans.property.StringProperty;


/**
 *
 * @author Jose
 */
public class ToggleTAFromGrid_Transaction implements jTPS_Transaction {

    private String taName;
    private String cellKey;
    CSGeneratorApp app;

    public ToggleTAFromGrid_Transaction(String initTAName, String initCellKey, CSGeneratorApp initApp) {
        taName = initTAName;
        cellKey = initCellKey;
        app = initApp;
    }

    public void doTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
       /* StringProperty cellProp = data.getOfficeHours().get(cellKey);
        String cellText = cellProp.getValue();

        // IF IT ALREADY HAS THE TA, Don't remove it
        if (!(cellText.contains(taName))) {
            */
            data.toggleTAOfficeHours(cellKey, taName);
        

    }

    public void undoTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.toggleTAOfficeHours(cellKey, taName);
    }
}
