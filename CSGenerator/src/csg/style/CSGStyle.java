/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.CSGeneratorApp;
import csg.data.TeachingAssistant;

import csg.workspace.CSGWorkspace;
import csg.workspace.CourseDetailsPane;
import csg.workspace.ProjectPane;
import csg.workspace.RecitationPane;
import csg.workspace.SchedulePane;
import csg.workspace.TAPane;
import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jose
 */
public class CSGStyle extends AppStyleComponent {

    CSGeneratorApp app;
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";

    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";
    public static String CLASS_CLEAR_BUTTON = "clear_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // FOR HIGHLIGHTING CELLS, COLUMNS, AND ROWS
    public static String CLASS_HIGHLIGHTED_GRID_CELL = "highlighted_grid_cell";
    public static String CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN = "highlighted_grid_row_or_column";
    //
    public static String CLASS_COURSE_BORDERED_PANE = "course_bordered_pane";

    public CSGStyle(CSGeneratorApp app) {

        this.app = app;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();
        initTAWorkspaceStyle();
        initCourseDetailsWorkspaceStyle();
        initProjectWorkspaceStyle();
        initRecitationWorkspaceStyle();
        initScheduleworkspaceStyle();
    }

    private void initCourseDetailsWorkspaceStyle() {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        CourseDetailsPane cdPane = workspaceComponent.getCourseDetailsPane();
        cdPane.getCourseInfoPane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        cdPane.getCourseInfoHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        cdPane.getPageStylePane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        cdPane.getPageStyleHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        cdPane.getSiteTemplatePane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        cdPane.getSiteTemplateLabel().getStyleClass().add(CLASS_HEADER_LABEL);

    }

    /**
     * This function specifies all the style classes for all user interface
     * controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane taPane = workspaceComponent.getTaPane();
        taPane.getTasHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        taPane.getTasHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = taPane.getTaTable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        taPane.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        taPane.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        taPane.getEmailTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        taPane.getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        taPane.getClearButton().getStyleClass().add(CLASS_CLEAR_BUTTON);

        // RIGHT SIDE - THE HEADER
        taPane.getOfficeHoursHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        taPane.getOfficeHoursHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
    }

    /**
     * This method initializes the style for all UI components in the office
     * hours grid. Note that this should be called every time a new TA Office
     * Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspaceComponent = workspace.getTaPane();
        workspaceComponent.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }

    public void initProjectWorkspaceStyle() {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane pane = workspaceComponent.getProjectPane();
        pane.getStudentsPane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        pane.getTeamPane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        pane.getStudentHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        pane.getTeamHeader().getStyleClass().add(CLASS_HEADER_LABEL);
    }

    public void initRecitationWorkspaceStyle() {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane pane = workspaceComponent.getRecitationPane();
        pane.getRecitationPane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        pane.getAddEditBox().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        pane.getRecitationHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        pane.getAddEditHeader().getStyleClass().add(CLASS_HEADER_LABEL);
    }

    public void initScheduleworkspaceStyle() {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        SchedulePane pane = workspaceComponent.getSchedulePane();
        pane.getCalendarBoundariesPane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        pane.getSchedulePane().getStyleClass().add(CLASS_COURSE_BORDERED_PANE);
        pane.getCalendarBoundariesLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        pane.getSchduleHeader().getStyleClass().add(CLASS_HEADER_LABEL);
        
    }

    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node) nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }

    public CSGeneratorApp getApp() {
        return app;
    }

    public void setApp(CSGeneratorApp app) {
        this.app = app;
    }

}
