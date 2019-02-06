/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.TAData;


/**
 *
 * @author Jose
 */
public class EditTaFromTable_Transaction implements jTPS_Transaction {

    String taName;
    String email;
    String oldTAName;
    String oldEmail;
    CSGeneratorApp app;

    public EditTaFromTable_Transaction(String initTAName, String initEmail, String initOldTAName, String initOldEmail, CSGeneratorApp initApp) {
        taName = initTAName;
        email = initEmail;
        oldTAName = initOldTAName;
        oldEmail = initOldEmail;
        app = initApp;
    }

    public void doTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.editTA(taName, email);
    }

    public void undoTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.editTA(oldTAName, oldEmail);
    }
}
