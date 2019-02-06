/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.jtps.jTPS;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Jose
 */
public class CSGWorkspace extends AppWorkspaceComponent {

    private CSGeneratorApp app;
    private CSGController controller;
    private static jTPS jTPS = new jTPS();
    private TabPane tabPane;
    private Tab courseDetailsTab;
    private CourseDetailsPane courseDetailsPane;
    private Tab taTab;

    private TAPane taPane;
    private Tab recitationTab;

    private RecitationPane recitationPane;
    private Tab scheduleTab;
    private SchedulePane schedulePane;
    private Tab projectTab;
    private ProjectPane projectPane;

    public CSGWorkspace(CSGeneratorApp app) {
        this.app = app;
        controller = new CSGController(app, CSGWorkspace.jTPS);
        workspace = new BorderPane();
        tabPane = new TabPane();
        courseDetailsTab = new Tab("Course Details");
        courseDetailsPane = new CourseDetailsPane(app);
        courseDetailsPane.setController(controller);
        courseDetailsTab.setContent(courseDetailsPane);
        taTab = new Tab("TA Data");
        taPane = new TAPane(app);
        taPane.setController(controller);
        taTab.setContent(taPane);
        recitationTab = new Tab("Recitation Data");
        recitationPane = new RecitationPane(app);
        recitationPane.setController(controller);
        recitationTab.setContent(recitationPane);
        scheduleTab = new Tab("Schedule Data");
        schedulePane = new SchedulePane(app);
        schedulePane.setController(controller);
        scheduleTab.setContent(schedulePane);
        projectTab = new Tab("Project Data");
        projectPane = new ProjectPane(app);
        projectPane.setController(controller);
        projectTab.setContent(projectPane);
        tabPane.getTabs().addAll(courseDetailsTab, taTab, recitationTab, scheduleTab, projectTab);

        // AND PUT EVERYTHING IN THE WORKSPACE
        ((BorderPane) workspace).setCenter(tabPane);

    }

    @Override
    public void resetWorkspace() {
        taPane.resetWorkspace();
        

    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        taPane.reloadWorkspace(dataComponent);
        courseDetailsPane.reloadWorkspace(dataComponent);
        recitationPane.reloadWorkspace(dataComponent);
        projectPane.reloadWorkspace(dataComponent);
        controller.updateTeamList();
        controller.updateTAList();
        //schedulePane.reloadWorkspace(dataComponent);

    }

    public CSGeneratorApp getApp() {
        return app;
    }

    public void setApp(CSGeneratorApp app) {
        this.app = app;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Tab getCourseDetailsTab() {
        return courseDetailsTab;
    }

    public void setCourseDetailsTab(Tab courseDetailsTab) {
        this.courseDetailsTab = courseDetailsTab;
    }

    public CourseDetailsPane getCourseDetailsPane() {
        return courseDetailsPane;
    }

    public void setCourseDetailsPane(CourseDetailsPane courseDetailsPane) {
        this.courseDetailsPane = courseDetailsPane;
    }

    public Tab getTaTab() {
        return taTab;
    }

    public void setTaTab(Tab taTab) {
        this.taTab = taTab;
    }

    public TAPane getTaPane() {
        return taPane;
    }

    public void setTaPane(TAPane taPane) {
        this.taPane = taPane;
    }

    public Tab getRecitationTab() {
        return recitationTab;
    }

    public void setRecitationTab(Tab recitationTab) {
        this.recitationTab = recitationTab;
    }

    public RecitationPane getRecitationPane() {
        return recitationPane;
    }

    public void setRecitationPane(RecitationPane recitationPane) {
        this.recitationPane = recitationPane;
    }

    public Tab getScheduleTab() {
        return scheduleTab;
    }

    public void setScheduleTab(Tab scheduleTab) {
        this.scheduleTab = scheduleTab;
    }

    public SchedulePane getSchedulePane() {
        return schedulePane;
    }

    public void setSchedulePane(SchedulePane schedulePane) {
        this.schedulePane = schedulePane;
    }

    public Tab getProjectTab() {
        return projectTab;
    }

    public void setProjectTab(Tab projectTab) {
        this.projectTab = projectTab;
    }

    public ProjectPane getProjectPane() {
        return projectPane;
    }

    public void setProjectPane(ProjectPane projectPane) {
        this.projectPane = projectPane;
    }

    public static jTPS getjTPS() {
        return jTPS;
    }

    public static void setjTPS(jTPS jTPS) {
        CSGWorkspace.jTPS = jTPS;
    }

    public CSGController getController() {
        return controller;
    }

    public void setController(CSGController controller) {
        this.controller = controller;
    }

}
