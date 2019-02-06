/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGeneratorApp;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jose
 */
public class CSGData implements AppDataComponent {

    private CSGeneratorApp app;
    private CourseDetailsData courseDetailsData;
    private TAData taData;
    private ScheduleData scheduleData;
    private RecitationData recitationData;
    private ProjectData projectData;

    public CSGData(CSGeneratorApp app) {
        this.app = app;
        courseDetailsData= new CourseDetailsData(app);
        taData = new TAData(app);
        scheduleData = new ScheduleData(app);
        recitationData = new RecitationData(app);
        projectData = new ProjectData(app);
    }

    public CSGeneratorApp getApp() {

        return app;
    }

    public void setApp(CSGeneratorApp app) {
        this.app = app;
    }

    public TAData getTaData() {
        return taData;
    }

    public void setTaData(TAData taData) {
        this.taData = taData;
    }

    @Override
    public void resetData() {
        taData.resetData();
        recitationData.resetData();
        courseDetailsData.resetData();
        scheduleData.resetData();
        projectData.resetData();
    }

    public ScheduleData getScheduleData() {
        return scheduleData;
    }

    public void setScheduleData(ScheduleData scheduleData) {
        this.scheduleData = scheduleData;
    }

    public RecitationData getRecitationData() {
        return recitationData;
    }

    public void setRecitationData(RecitationData recitationData) {
        this.recitationData = recitationData;
    }

    public ProjectData getProjectData() {
        return projectData;
    }

    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;
    }

    public CourseDetailsData getCourseDetailsData() {
        return courseDetailsData;
    }

    public void setCourseDetailsData(CourseDetailsData courseDetailsData) {
        this.courseDetailsData = courseDetailsData;
    }

}
