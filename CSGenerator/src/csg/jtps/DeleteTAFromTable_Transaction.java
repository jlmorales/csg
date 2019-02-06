/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;


import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.workspace.CSGWorkspace;
import csg.workspace.TAPane;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;


/**
 *
 * @author Jose
 */
public class DeleteTAFromTable_Transaction implements jTPS_Transaction{
    private String taName;
    private String taEmail;
    CSGeneratorApp app;
    private ArrayList<String> ids;
    
    public DeleteTAFromTable_Transaction(String initTAName, String initEmail,ArrayList<String> initIds, CSGeneratorApp initApp) {
        taName=initTAName;
        taEmail=initEmail;
        ids=initIds;
        app=initApp;
    }
    public void doTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.removeTA(taName);
        CSGWorkspace csgw= (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace= csgw.getTaPane();

                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    
                    if (label.getText().equals(taName)
                            || (label.getText().contains(taName + "\n"))
                            || (label.getText().contains("\n" + taName))) {
                        data.removeTAFromCell(label.textProperty(), taName);
                        
                    }
                }
                
        
        
    }
    public void undoTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        data.addTA(taName, taEmail);
        CSGWorkspace csgw= (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace= csgw.getTaPane();
        
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    for(String id: ids){
                        if(label.getId().equals(id)){
                            data.toggleTAOfficeHours(id, taName);
                        }
                    }
                }
    }
}
