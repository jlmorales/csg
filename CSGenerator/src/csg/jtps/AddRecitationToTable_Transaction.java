package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.RecitationData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose
 */
public class AddRecitationToTable_Transaction implements jTPS_Transaction{
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    CSGeneratorApp app;

    public AddRecitationToTable_Transaction(String section, String instructor, String dayTime, String location, String ta1, String ta2, CSGeneratorApp app) {
        this.section = section;
        this.instructor = instructor;
        this.dayTime = dayTime;
        this.location = location;
        this.ta1 = ta1;
        this.ta2 = ta2;
        this.app = app;
    }
    
    
    public void doTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        RecitationData data= csgD.getRecitationData();
        data.addRecitation(section,instructor,dayTime,location,ta1,ta2);
        
        
    }
    public void undoTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        RecitationData data= csgD.getRecitationData();
        data.removeRecitation(section,instructor,dayTime,location,ta1,ta2);
    }
}
