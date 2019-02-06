/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;

/**
 *
 * @author Jose
 */
public class ChangeExportDirectory_Transaction implements jTPS_Transaction {
    String oldDirectory;
    String selectedDirectory;
    CSGeneratorApp app;

    public ChangeExportDirectory_Transaction(String oldDirectory, String selectedDirectory, CSGeneratorApp app) {
        this.oldDirectory = oldDirectory;
        this.selectedDirectory = selectedDirectory;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData data=(CSGData) app.getDataComponent();
        data.getCourseDetailsData().getCourseInfo().setExportDir(selectedDirectory);
         CSGWorkspace workspace=(CSGWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(data);
    }

    @Override
    public void undoTransaction() {
        CSGData data=(CSGData) app.getDataComponent();
        data.getCourseDetailsData().getCourseInfo().setExportDir(oldDirectory);
         CSGWorkspace workspace=(CSGWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(data);
    }
    
}
