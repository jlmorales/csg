/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectData;

/**
 *
 * @author Jose
 */
public class EditTeamFromTable_Transaction implements jTPS_Transaction {

    String color;
    String link;
    String name;
    String textColor;
    String oldColor;
    String oldLink;
    String oldName;
    String oldTextColor;
    CSGeneratorApp app;

    public EditTeamFromTable_Transaction(String Color, String link, String name, String textColor, String oldColor, String oldLink, String oldName, String oldTextColor, CSGeneratorApp app) {
        this.color = Color;
        this.link = link;
        this.name = name;
        this.textColor = textColor;
        this.oldColor = oldColor;
        this.oldLink = oldLink;
        this.oldName = oldName;
        this.oldTextColor = oldTextColor;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData csgD = (CSGData) app.getDataComponent();
        ProjectData data = csgD.getProjectData();
        data.editTeam(color,link,name,textColor);
    }

    @Override
    public void undoTransaction() {
        CSGData csgD = (CSGData) app.getDataComponent();
        ProjectData data = csgD.getProjectData();
        data.editTeam(color,link,name,textColor);
    }
    

}
