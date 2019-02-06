/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;


import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.file.TimeSlot;
import csg.workspace.CSGWorkspace;
import java.util.ArrayList;
import csg.jtps.jTPS_Transaction;
import csg.workspace.TAPane;


/**
 *
 * @author Jose
 */
public class ChangeStartAndEndTime_Transaction implements jTPS_Transaction {
    int startTime;
    int endTime;
    int oldStartTime;
    int oldEndTime;
    CSGeneratorApp app;
    public ChangeStartAndEndTime_Transaction(int initStartTime,int initEndTime,int initOldStartTime,int initOldEndTime ,CSGeneratorApp initApp){
        startTime=initStartTime;
        endTime=initEndTime;
        oldStartTime=initOldStartTime;
        oldEndTime=initOldEndTime;
        app=initApp;
    }
    public void doTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        ArrayList<TimeSlot> officeHoursCopy = TimeSlot.buildOfficeHoursList(data);
        CSGWorkspace csgw= (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace= csgw.getTaPane();
        workspace.resetWorkspace();
        data.ChangeTime(startTime, endTime);
        workspace.reloadOfficeHoursGrid(data);
        data.repopulateGrid(officeHoursCopy);
        
        
    }
    public void undoTransaction(){
        CSGData csgD=(CSGData) app.getDataComponent();
        TAData data= csgD.getTaData();
        ArrayList<TimeSlot> officeHoursCopy = TimeSlot.buildOfficeHoursList(data);
        CSGWorkspace csgw= (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace= csgw.getTaPane();
        workspace.resetWorkspace();
        data.ChangeTime(oldStartTime, oldEndTime);
        workspace.reloadOfficeHoursGrid(data);
        data.repopulateGrid(officeHoursCopy);
    }
}
