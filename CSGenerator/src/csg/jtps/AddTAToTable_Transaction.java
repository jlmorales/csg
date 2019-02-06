/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;


import csg.jtps.jTPS_Transaction;
import csg.data.TAData;
/**
 *
 * @author Jose
 */
public class AddTAToTable_Transaction implements jTPS_Transaction{
    private String name;
    private String email;
    CSGeneratorApp app;
    
    public AddTAToTable_Transaction(String initName,String initEmail,CSGeneratorApp initApp) {
        name=initName;
        email=initEmail;
        app=initApp;
    }
    public void doTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.addTA(name, email);
        
        
    }
    public void undoTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.removeTA(name);
    }
}
