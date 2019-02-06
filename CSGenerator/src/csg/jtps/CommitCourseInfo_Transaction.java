/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.CourseInfo;
import csg.workspace.CSGWorkspace;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jose
 */
public class CommitCourseInfo_Transaction implements jTPS_Transaction {
    private String subject;
    private String semester;
    private String number;
    private String year;
    private String title;
    private String instrName;
    private String instrHome;
    private String oldSubject;
    private String oldSemester;
    private String oldNumber;
    private String oldYear;
    private String oldTitle;
    private String oldInstrName;
    private String oldInstrHome;
    CSGeneratorApp app;

    public CommitCourseInfo_Transaction(String subject, String semester, String number, String year, String title, String instrName, String instrHome, String oldSubject, String oldSemester, String oldNumber, String oldYear, String oldTitle, String oldInstrName, String oldInstrHome, CSGeneratorApp app) {
        this.subject = subject;
        this.semester = semester;
        this.number = number;
        this.year = year;
        this.title = title;
        this.instrName = instrName;
        this.instrHome = instrHome;
        this.oldSubject = oldSubject;
        this.oldSemester = oldSemester;
        this.oldNumber = oldNumber;
        this.oldYear = oldYear;
        this.oldTitle = oldTitle;
        this.oldInstrName = oldInstrName;
        this.oldInstrHome = oldInstrHome;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData data=(CSGData) app.getDataComponent();
        CourseInfo courseInfo=data.getCourseDetailsData().getCourseInfo();
        courseInfo.setInstrHome(instrHome);
        courseInfo.setInstrName(instrName);
        courseInfo.setNumber(number);
        courseInfo.setSemester(semester);
        courseInfo.setSubject(subject);
        courseInfo.setTitle(title);
        courseInfo.setYear(year);
        CSGWorkspace workspace=(CSGWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(data);
    }

    @Override
    public void undoTransaction() {
        CSGData data=(CSGData) app.getDataComponent();
        CourseInfo courseInfo=data.getCourseDetailsData().getCourseInfo();
        courseInfo.setInstrHome(oldInstrHome);
        courseInfo.setInstrName(oldInstrName);
        courseInfo.setNumber(oldNumber);
        courseInfo.setSemester(oldSemester);
        courseInfo.setSubject(oldSubject);
        courseInfo.setTitle(oldTitle);
        courseInfo.setYear(oldYear);
        CSGWorkspace workspace=(CSGWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(data);
    }
    
}
