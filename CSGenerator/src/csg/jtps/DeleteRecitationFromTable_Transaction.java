/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.RecitationData;

/**
 *
 * @author Jose
 */
public class DeleteRecitationFromTable_Transaction implements jTPS_Transaction {

    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    CSGeneratorApp app;

    public DeleteRecitationFromTable_Transaction(String dayTime, String instructor, String location, String section, String ta1, String ta2, CSGeneratorApp app) {
        this.instructor = instructor;
        this.dayTime = dayTime;
        this.location = location;
        this.section=section;
        this.ta1 = ta1;
        this.ta2 = ta2;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        RecitationData data= csgD.getRecitationData();
        data.removeRecitation(section,instructor,dayTime,location,ta1,ta2);
    }

    @Override
    public void undoTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        RecitationData data= csgD.getRecitationData();
        data.addRecitation(section,instructor,dayTime,location,ta1,ta2);
    }

}
