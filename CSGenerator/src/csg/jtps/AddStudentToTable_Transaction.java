/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.data.TAData;

/**
 *
 * @author Jose
 */
public class AddStudentToTable_Transaction implements jTPS_Transaction {

    String firstName;
    String lastName;
    String role;
    String team;
    CSGeneratorApp app;

    public AddStudentToTable_Transaction(String firstName, String lastName, String role, String team, CSGeneratorApp app) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.team = team;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        ProjectData data= csgD.getProjectData();
        data.addStudent(firstName,lastName,role,team);
    }

    @Override
    public void undoTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        ProjectData data= csgD.getProjectData();
        data.removeStudent(firstName,lastName,role,team);
    }
}
