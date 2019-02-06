/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.data.RecitationData;

/**
 *
 * @author Jose
 */
public class EditStudentFromTable_Transaction implements jTPS_Transaction  {

    String firstName;
    String lastName;
    String role;
    String team;
    String oldFirstName;
    String oldLastName;
    String oldRole;
    String oldTeam;
    CSGeneratorApp app;

    public EditStudentFromTable_Transaction(String fristName, String lastName, String role, String team, String oldFirstName, String oldLastName, String oldRole, String oldTeam, CSGeneratorApp app) {
        this.firstName = fristName;
        this.lastName = lastName;
        this.role = role;
        this.team = team;
        this.oldFirstName = oldFirstName;
        this.oldLastName = oldLastName;
        this.oldRole = oldRole;
        this.oldTeam = oldTeam;
        this.app = app;
    }
    

    @Override
    public void doTransaction() {
        CSGData csgD = (CSGData) app.getDataComponent();
        ProjectData data = csgD.getProjectData();
        data.editStudent(firstName,lastName,role,team);
    }

    @Override
    public void undoTransaction() {
        CSGData csgD = (CSGData) app.getDataComponent();
        ProjectData data = csgD.getProjectData();
        data.editStudent(oldFirstName,oldLastName,oldRole,oldTeam);
    }
}
