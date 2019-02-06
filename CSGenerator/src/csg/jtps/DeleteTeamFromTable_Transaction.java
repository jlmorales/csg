/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.workspace.CSGWorkspace;

/**
 *
 * @author Jose
 */
public class DeleteTeamFromTable_Transaction implements jTPS_Transaction {
    String color;
    String link;
    String name;
    String textColor;
    CSGeneratorApp app;

    public DeleteTeamFromTable_Transaction(String color, String link, String name, String textColor, CSGeneratorApp app) {
        this.color = color;
        this.link = link;
        this.name = name;
        this.textColor = textColor;
        this.app = app;
    }

    

    @Override
    public void doTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        ProjectData data= csgD.getProjectData();
        data.removeTeam(color,link,name,textColor);
        CSGWorkspace workspace=(CSGWorkspace) app.getWorkspaceComponent();
        workspace.getController().updateTeamList();
    }

    @Override
    public void undoTransaction() {
        CSGData csgD=(CSGData) app.getDataComponent();
        ProjectData data= csgD.getProjectData();
        data.addTeam(color,link,name,textColor);
        CSGWorkspace workspace=(CSGWorkspace) app.getWorkspaceComponent();
        workspace.getController().updateTeamList();
    }
    
}
