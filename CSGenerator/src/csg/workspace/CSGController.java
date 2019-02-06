/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.CSGeneratorProp;
import static csg.CSGeneratorProp.CHANGE_START_END_TIME_MESSAGE;
import static csg.CSGeneratorProp.CHANGE_START_END_TIME_TITLE;
import static csg.CSGeneratorProp.INVALID_TIME_MESSAGE;
import static csg.CSGeneratorProp.INVALID_TIME_TITLE;
import static csg.CSGeneratorProp.MISSING_TA_EMAIL_MESSAGE;
import static csg.CSGeneratorProp.MISSING_TA_EMAIL_TITLE;
import static csg.CSGeneratorProp.MISSING_TA_NAME_MESSAGE;
import static csg.CSGeneratorProp.MISSING_TA_NAME_TITLE;
import static csg.CSGeneratorProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static csg.CSGeneratorProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import csg.data.CSGData;
import csg.data.CourseInfo;
import csg.data.ProjectData;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.file.TimeSlot;
import csg.jtps.AddRecitationToTable_Transaction;
import csg.jtps.AddScheduleItemToTable_Transaction;
import csg.jtps.AddStudentToTable_Transaction;
import csg.jtps.AddTAToTable_Transaction;
import csg.jtps.AddTeamToTable_Transaction;
import csg.jtps.ChangeExportDirectory_Transaction;
import csg.jtps.ChangeStartAndEndTime_Transaction;
import csg.jtps.CommitCourseInfo_Transaction;
import csg.jtps.DeleteRecitationFromTable_Transaction;
import csg.jtps.DeleteScheduleItemFromTable_Transaction;
import csg.jtps.DeleteStudentFromTable_Transaction;
import csg.jtps.DeleteTAFromTable_Transaction;
import csg.jtps.DeleteTeamFromTable_Transaction;
import csg.jtps.EditRecitationFromTable_Transaction;
import csg.jtps.EditScheduleItemFromTable_Transaction;
import csg.jtps.EditStudentFromTable_Transaction;
import csg.jtps.EditTaFromTable_Transaction;
import csg.jtps.EditTeamFromTable_Transaction;
import csg.jtps.SelectTemplateDirectory_Transaction;
import csg.jtps.ToggleTAFromGrid_Transaction;
import csg.jtps.jTPS;
import csg.jtps.jTPS_Transaction;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static csg.style.CSGStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static csg.style.CSGStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import static djf.settings.AppPropertyType.EXPORT_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.EXPORT_ERROR_TITLE;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.File;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jose
 */
public class CSGController {// THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
// THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED

    CSGeneratorApp app;
    private EmailValidator emailValidator;
    public jTPS jTPS;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CSGController(CSGeneratorApp initApp, jTPS jTPS) {
        // KEEP THIS FOR LATER

        app = initApp;
        emailValidator = new EmailValidator();
        this.jTPS = jTPS;
    }

    /**
     * This helper method should be called every time an edit happens.
     */
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);

    }

    /**
     * This method responds to when the user requests to add a new TA via the
     * UI. Note that it must first do some validation to make sure a unique name
     * and email address has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace csgw = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgw.getTaPane();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        TAData data = csgData.getTaData();

        TeachingAssistant ta = (TeachingAssistant) workspace.getTaTable().getSelectionModel().getSelectedItem();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (workspace.getAddButton().getText().equals(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()))) {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (name.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (email.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (name.equals(ta.getName()) && email.equals(ta.getEmail())) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
            } else if (!emailValidator.validate(email)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // EVERYTHING IS FINE, Edit TA
            else {
                // data.editTA(name, email);

                //add a transcation that happened
                String oldName = ta.getName();
                String oldemail = ta.getEmail();
                jTPS_Transaction transaction = new EditTaFromTable_Transaction(name, email, oldName, oldemail, app);
                jTPS.addTransaction(transaction);

                workspace.getAddButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

                // CLEAR THE TEXT FIELDS
                nameTextField.setText("");
                emailTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                nameTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                workspace.getTaTable().refresh();
            }

        } else {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (name.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (email.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (data.containsTA(name, email)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
            } else if (!emailValidator.validate(email)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // EVERYTHING IS FINE, ADD A NEW TA
            else {
                // ADD THE NEW TA TO THE DATA
                //data.addTA(name, email);

                jTPS_Transaction transaction = new AddTAToTable_Transaction(name, email, app);
                jTPS.addTransaction(transaction);

                //add a transcation that happened
                // CLEAR THE TEXT FIELDS
                nameTextField.setText("");
                emailTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                nameTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleAddRecitation() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane workspace = csgW.getRecitationPane();
        TextField dayTimeTextField = workspace.getDayTimeField();
        TextField instructorTextField = workspace.getInstructorField();
        TextField locationTextField = workspace.getLocationField();
        TextField sectionTextField = workspace.getSectionField();
        String dayTime = dayTimeTextField.getText();
        String instructor = instructorTextField.getText();
        String location = locationTextField.getText();
        String section = sectionTextField.getText();
        System.out.println();
        String ta1 = (String) workspace.getTa1Combo().getValue();
        String ta2 = (String) workspace.getTa2Combo().getValue();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        RecitationData data = csgData.getRecitationData();

        Recitation re = (Recitation) workspace.getRecitationTable().getSelectionModel().getSelectedItem();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (workspace.getAddUpdateButton().getText().equals(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()))) {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (section.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (location.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (instructor.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (dayTime.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // data.editTA(name, email);

                //add a transcation that happened
                String oldDayTime = re.getDayTime();
                String oldInstructor = re.getInstructor();
                String oldLocation = re.getLocation();
                String oldSection = re.getSection();
                String oldTa1 = re.getTa1();
                String oldTa2 = re.getTa2();
                jTPS_Transaction transaction = new EditRecitationFromTable_Transaction(dayTime, instructor, location, section,
                        ta1, ta2, oldSection, oldInstructor,
                        oldDayTime, oldLocation, oldTa1, oldTa2, app);
                jTPS.addTransaction(transaction);

                workspace.getAddUpdateButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

                // CLEAR THE TEXT FIELDS
                dayTimeTextField.setText("");
                instructorTextField.setText("");
                locationTextField.setText("");
                sectionTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                dayTimeTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                workspace.getRecitationTable().refresh();
            }

        } else {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (section.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (location.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (instructor.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (dayTime.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // ADD THE NEW TA TO THE DATA
                //data.addTA(name, email);

                jTPS_Transaction transaction = new AddRecitationToTable_Transaction(section, instructor, dayTime, location, ta1, ta2, app);
                jTPS.addTransaction(transaction);

                //add a transcation that happened
                // CLEAR THE TEXT FIELDS
                // CLEAR THE TEXT FIELDS
                dayTimeTextField.setText("");
                instructorTextField.setText("");
                locationTextField.setText("");
                sectionTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                dayTimeTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleAddScheduleItem() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        SchedulePane workspace = csgW.getSchedulePane();
        TextField titleTextField = workspace.getTitleField();
        TextField topicTextField = workspace.getTopicField();
        TextField linkTextField = workspace.getLinkField();
        TextField criteriaTextField = workspace.getCriteriaField();
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();
        String date = workspace.getDatePicker().getValue().toString();
        String type = (String) workspace.getTypeCombo().getSelectionModel().getSelectedItem();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        ScheduleData data = csgData.getScheduleData();

        ScheduleItem si = (ScheduleItem) workspace.getScheduleItemTable().getSelectionModel().getSelectedItem();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (workspace.getAddUpdateButton().getText().equals(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()))) {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (topic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (link.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (criteria.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // data.editTA(name, email);
                String oldCriteria = si.getCriteria();
                String oldDate = si.getDate();
                String oldLink = si.getLink();
                String oldTitle = si.getTitle();
                String oldTopic = si.getTopic();
                String oldType = si.getType();
                //add a transcation that happened

                jTPS_Transaction transaction = new EditScheduleItemFromTable_Transaction(criteria, date, link, title,
                        topic, type,
                        oldCriteria, oldDate,
                        oldLink, oldTitle, oldTopic, oldType, app);
                jTPS.addTransaction(transaction);

                workspace.getAddUpdateButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

                // CLEAR THE TEXT FIELDS
                workspace.getTitleField().setText("");
                workspace.getTopicField().setText("");
                workspace.getLinkField().setText("");
                workspace.getCriteriaField().setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                workspace.getTitleField().requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                workspace.getScheduleItemTable().refresh();
            }

        } else {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (topic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (link.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (criteria.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // ADD THE NEW TA TO THE DATA
                //data.addTA(name, email);

                jTPS_Transaction transaction = new AddScheduleItemToTable_Transaction(criteria, date, link, title,
                        topic, type, app);
                jTPS.addTransaction(transaction);

                //add a transcation that happened
                // CLEAR THE TEXT FIELDS
                workspace.getTitleField().setText("");
                workspace.getTopicField().setText("");
                workspace.getLinkField().setText("");
                workspace.getCriteriaField().setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                workspace.getTitleField().requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleAddTeam() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        String name = workspace.getNameField().getText();
        String color = workspace.getColorPicker().getValue().toString();
        String textColor = workspace.getTextColorPicker().getValue().toString();
        String link = workspace.getLinkField().getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        ProjectData data = csgData.getProjectData();

        Team team = (Team) workspace.getTeamTable().getSelectionModel().getSelectedItem();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (workspace.getTeamAddUpdateButton().getText().equals(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()))) {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (name.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (color.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (textColor.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (link.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // data.editTA(name, email);
                String oldColor = team.getColor();
                String oldLink = team.getLink();
                String oldName = team.getName();
                String oldTextColor = team.getTextColor();
                //add a transcation that happened

                jTPS_Transaction transaction = new EditTeamFromTable_Transaction(color, link, name, textColor,
                        oldColor, oldLink, oldName, oldTextColor, app);
                jTPS.addTransaction(transaction);

                workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

                // CLEAR THE TEXT FIELDS
                workspace.getNameField().setText("");
                workspace.getLinkField().setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                workspace.getNameField().requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                updateTeamList();
                workspace.getTeamTable().refresh();
            }

        } else {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (name.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (color.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (textColor.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (link.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // ADD THE NEW TA TO THE DATA
                //data.addTA(name, email);

                jTPS_Transaction transaction = new AddTeamToTable_Transaction(color, link, name, textColor, app);
                jTPS.addTransaction(transaction);

                // CLEAR THE TEXT FIELDS
                workspace.getNameField().setText("");
                workspace.getLinkField().setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                workspace.getNameField().requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                updateTeamList();
                workspace.getTeamTable().refresh();
            }
        }
    }

    public void handleAddStudent() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        String firstName = workspace.getFirstNameField().getText();
        String lastName = workspace.getLastNameField().getText();
        String team = (String) workspace.getTeamCombo().getValue();
        String role = workspace.getRoleField().getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        ProjectData data = csgData.getProjectData();

        Student student = (Student) workspace.getStudentsTable().getSelectionModel().getSelectedItem();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (workspace.getTeamAddUpdateButton().getText().equals(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()))) {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (firstName.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (lastName.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (team.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (role.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // data.editTA(name, email);
                String oldFirstName = student.getFirstName();
                String oldLastName = student.getLastName();
                String oldRole = student.getRole();
                String oldTeam = student.getTeam();
                //add a transcation that happened

                jTPS_Transaction transaction = new EditStudentFromTable_Transaction(firstName, lastName, role, team, oldFirstName, oldLastName, oldRole, oldTeam, app);
                jTPS.addTransaction(transaction);

                workspace.getAddUpdateStudentButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

                // CLEAR THE TEXT FIELDS
                workspace.getFirstNameField().setText("");
                workspace.getLastNameField().setText("");
                workspace.getRoleField().setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                workspace.getFirstNameBox().requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                workspace.getTeamTable().refresh();
                updateTAList();
            }

        } else {
            // DID THE USER NEGLECT TO PROVIDE A TA NAME?
            if (firstName.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(CSGeneratorProp.MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
            } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
            else if (lastName.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (team.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else if (role.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
            } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
            else {
                // ADD THE NEW TA TO THE DATA
                //data.addTA(name, email);

                jTPS_Transaction transaction = new AddStudentToTable_Transaction(firstName, lastName, role, team, app);
                jTPS.addTransaction(transaction);

                // CLEAR THE TEXT FIELDS
                workspace.getFirstNameField().setText("");
                workspace.getLastNameField().setText("");
                workspace.getRoleField().setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                workspace.getFirstNameBox().requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                workspace.getTeamTable().refresh();
                updateTAList();
            }
        }

    }

    /**
     * This function provides a response for when the user presses a keyboard
     * key. Note that we're only responding to Delete, to remove a TA.
     *
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
            TAPane workspace = csgW.getTaPane();
            TableView taTable = workspace.getTaTable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant) selectedItem;
                String taName = ta.getName();
                CSGData csgData = (CSGData) app.getDataComponent();
                TAData data = csgData.getTaData();
                String taEmail = ta.getEmail();
                data.removeTA(taName);
                ArrayList<String> ids = new ArrayList<String>();
                /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
                 */
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {

                    if (label.getText().equals(taName)
                            || (label.getText().contains(taName + "\n"))
                            || (label.getText().contains("\n" + taName))) {
                        data.removeTAFromCell(label.textProperty(), taName);
                        ids.add(label.getId());

                    }
                }
                jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName, taEmail, ids, app);
                jTPS.addTransaction(transaction);

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                updateTAList();

            }
        }

    }

    public void handleDeleteRecitation(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
            RecitationPane workspace = csgW.getRecitationPane();
            TableView taTable = workspace.getRecitationTable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Recitation re = (Recitation) selectedItem;
                String dayTime = re.getDayTime();
                String instructor = re.getInstructor();
                String location = re.getLocation();
                String section = re.getSection();
                String ta1 = re.getTa1();
                String ta2 = re.getTa2();

                CSGData csgData = (CSGData) app.getDataComponent();
                RecitationData data = csgData.getRecitationData();
                /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
                 */
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                jTPS_Transaction transaction = new DeleteRecitationFromTable_Transaction(dayTime, instructor, location, section, ta1, ta2, app);
                jTPS.addTransaction(transaction);

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleDeleteRecitation() {
        // DID THE USER PRESS THE DELETE KEY?

        // GET THE TABLE
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane workspace = csgW.getRecitationPane();
        TableView taTable = workspace.getRecitationTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            Recitation re = (Recitation) selectedItem;
            String dayTime = re.getDayTime();
            String instructor = re.getInstructor();
            String location = re.getLocation();
            String section = re.getSection();
            String ta1 = re.getTa1();
            String ta2 = re.getTa2();

            CSGData csgData = (CSGData) app.getDataComponent();
            RecitationData data = csgData.getRecitationData();
            /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
             */
            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            jTPS_Transaction transaction = new DeleteRecitationFromTable_Transaction(dayTime, instructor, location, section, ta1, ta2, app);
            jTPS.addTransaction(transaction);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }

    }

    public void handleDeleteScheduleItem(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
            SchedulePane workspace = csgW.getSchedulePane();
            TableView siTable = workspace.getScheduleItemTable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = siTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                ScheduleItem re = (ScheduleItem) selectedItem;
                String criteria = re.getCriteria();
                String date = re.getDate();
                String link = re.getLink();
                String title = re.getTitle();
                String topic = re.getTopic();
                String type = re.getType();

                CSGData csgData = (CSGData) app.getDataComponent();
                ScheduleData data = csgData.getScheduleData();
                data.removeScheduleItem(criteria, date, link, title, topic, type);
                /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
                 */
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                jTPS_Transaction transaction = new DeleteScheduleItemFromTable_Transaction(criteria, date, link, title, topic, type, app);
                jTPS.addTransaction(transaction);

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleDeleteScheduleItem() {
        // DID THE USER PRESS THE DELETE KEY?

        // GET THE TABLE
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        SchedulePane workspace = csgW.getSchedulePane();
        TableView siTable = workspace.getScheduleItemTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = siTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            ScheduleItem re = (ScheduleItem) selectedItem;
            String criteria = re.getCriteria();
            String date = re.getDate();
            String link = re.getLink();
            String title = re.getTitle();
            String topic = re.getTopic();
            String type = re.getType();

            CSGData csgData = (CSGData) app.getDataComponent();
            ScheduleData data = csgData.getScheduleData();
            data.removeScheduleItem(criteria, date, link, title, topic, type);
            /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
             */
            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            jTPS_Transaction transaction = new DeleteScheduleItemFromTable_Transaction(criteria, date, link, title, topic, type, app);
            jTPS.addTransaction(transaction);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }

    }

    public void handleDeleteTeam(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
            ProjectPane workspace = csgW.getProjectPane();
            TableView teamTable = workspace.getTeamTable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Team team = (Team) selectedItem;
                String color = team.getColor();
                String link = team.getLink();
                String name = team.getName();
                String textColor = team.getTextColor();

                CSGData csgData = (CSGData) app.getDataComponent();
                ProjectData data = csgData.getProjectData();
                data.removeTeam(color, link, name, textColor);
                /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
                 */
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                jTPS_Transaction transaction = new DeleteTeamFromTable_Transaction(color, link, name, textColor, app);
                jTPS.addTransaction(transaction);

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
                updateTeamList();
            }
        }
    }

    public void handleDeleteTeam() {

        // GET THE TABLE
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        TableView teamTable = workspace.getTeamTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            Team team = (Team) selectedItem;
            String color = team.getColor();
            String link = team.getLink();
            String name = team.getName();
            String textColor = team.getTextColor();

            CSGData csgData = (CSGData) app.getDataComponent();
            ProjectData data = csgData.getProjectData();
            data.removeTeam(color, link, name, textColor);
            /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
             */
            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            jTPS_Transaction transaction = new DeleteTeamFromTable_Transaction(color, link, name, textColor, app);
            jTPS.addTransaction(transaction);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
            updateTeamList();
        }

    }

    public void handleDeleteStudent(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
            ProjectPane workspace = csgW.getProjectPane();
            TableView studentsTable = workspace.getStudentsTable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = studentsTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                Student student = (Student) selectedItem;
                String firstName = student.getFirstName();
                String lastName = student.getLastName();
                String role = student.getRole();
                String team = student.getTeam();

                CSGData csgData = (CSGData) app.getDataComponent();
                ProjectData data = csgData.getProjectData();
                data.removeStudent(firstName, lastName, role, team);
                /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
                 */
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                jTPS_Transaction transaction = new DeleteStudentFromTable_Transaction(firstName, lastName, role, team, app);
                jTPS.addTransaction(transaction);

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    public void handleDeleteStudent() {
        // DID THE USER PRESS THE DELETE KEY?

        // GET THE TABLE
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        TableView studentsTable = workspace.getStudentsTable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA AND REMOVE IT
            Student student = (Student) selectedItem;
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            String role = student.getRole();
            String team = student.getTeam();

            CSGData csgData = (CSGData) app.getDataComponent();
            ProjectData data = csgData.getProjectData();
            data.removeStudent(firstName, lastName, role, team);
            /*jTPS_Transaction transaction = new DeleteTAFromTable_Transaction(taName,taEmail, app);
                jTPS.addTransaction(transaction);
             */
            // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
            jTPS_Transaction transaction = new DeleteStudentFromTable_Transaction(firstName, lastName, role, team, app);
            jTPS.addTransaction(transaction);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }

    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
     *
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgW.getTaPane();
        TableView taTable = workspace.getTaTable();
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            CSGData csgData = (CSGData) app.getDataComponent();
            TAData data = csgData.getTaData();
            String cellKey = pane.getId();

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            //data.toggleTAOfficeHours(cellKey, taName);
            jTPS_Transaction transaction = new ToggleTAFromGrid_Transaction(taName, cellKey, app);
            jTPS.addTransaction(transaction);

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }

    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        CSGData csgData = (CSGData) app.getDataComponent();
        TAData data = csgData.getTaData();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgW.getTaPane();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        CSGData csgData = (CSGData) app.getDataComponent();
        TAData data = csgData.getTaData();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgW.getTaPane();

        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }

    public void handleEditTA(TableView taTable) {
        TeachingAssistant ta = (TeachingAssistant) taTable.getSelectionModel().getSelectedItem();
        String email = ta.getEmail();
        String name = ta.getName();
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgW.getTaPane();
        TextField emailTextField = workspace.getEmailTextField();
        TextField nameTextField = workspace.getNameTextField();
        emailTextField.setText(email);
        nameTextField.setText(name);

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getAddButton().setText(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()));

    }

    public void handleEditRecitation(TableView recitationTable) {
        Recitation re = (Recitation) recitationTable.getSelectionModel().getSelectedItem();
        String dayTime = re.getDayTime();
        String instructor = re.getInstructor();
        String location = re.getLocation();
        String section = re.getSection();
        String ta1 = re.getTa1();
        String ta2 = re.getTa2();
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane workspace = csgW.getRecitationPane();
        TextField dayTimeTextField = workspace.getDayTimeField();
        TextField instructorTextField = workspace.getInstructorField();
        TextField locationTextField = workspace.getLocationField();
        TextField sectionTextField = workspace.getSectionField();
        workspace.getTa1Combo().setValue(ta1);
        workspace.getTa2Combo().setValue(ta2);
        dayTimeTextField.setText(dayTime);
        instructorTextField.setText(instructor);
        locationTextField.setText(location);
        sectionTextField.setText(section);

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getAddUpdateButton().setText(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()));
    }

    public void handleEditStudent(TableView studentTable) {
        Student student = (Student) studentTable.getSelectionModel().getSelectedItem();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String role = student.getRole();
        String team = student.getTeam();

        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        workspace.getFirstNameField().setText(firstName);
        workspace.getLastNameField().setText(lastName);
        workspace.getTeamCombo().setValue(team);
        workspace.getRoleField().setText(role);

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        workspace.getAddUpdateStudentButton().setText(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()));
    }

    public void handleEditTeam(TableView teamTable) {
        Team team = (Team) teamTable.getSelectionModel().getSelectedItem();
        String color = team.getColor();
        String link = team.getLink();
        String name = team.getName();
        String textColor = team.getTextColor();

        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        workspace.getColorPicker().setValue(Color.web(color));
        workspace.getLinkField().setText(link);
        workspace.getNameField().setText(name);
        workspace.getTextColorPicker().setValue(Color.web(textColor));

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()));
        updateTeamList();
    }

    public void handleEditScheduleItem(TableView teamTable) {
        ScheduleItem si = (ScheduleItem) teamTable.getSelectionModel().getSelectedItem();
        String criteria = si.getCriteria();
        String date = si.getDate();
        String link = si.getLink();
        String title = si.getTitle();
        String topic = si.getTopic();
        String type = si.getType();

        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        SchedulePane workspace = csgW.getSchedulePane();
        workspace.getCriteriaField().setText(criteria);
        workspace.getLinkField().setText(link);
        workspace.getTitleField().setText(title);
        workspace.getTopicField().setText(topic);
        workspace.getTypeCombo().setValue(type);

        PropertiesManager props = PropertiesManager.getPropertiesManager();

        workspace.getAddUpdateButton().setText(props.getProperty(CSGeneratorProp.UPDATE_BUTTON_TEXT.toString()));
    }

    public void handleClearTA() {
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgW.getTaPane();
        // CLEAR THE TEXT FIELDS
        workspace.getNameTextField().setText("");
        workspace.getEmailTextField().setText("");

        // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
        workspace.getNameTextField().requestFocus();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getAddButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

    }

    public void handleClearRecitation() {
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane workspace = csgW.getRecitationPane();
        // CLEAR THE TEXT FIELDS
        workspace.getDayTimeField().setText("");
        workspace.getSectionField().setText("");
        workspace.getInstructorField().setText("");
        workspace.getLocationField().setText("");

        // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
        workspace.getDayTimeField().requestFocus();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getAddUpdateButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));
    }

    public void handleClearSchedule() {
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        SchedulePane workspace = csgW.getSchedulePane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getAddUpdateButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

        // CLEAR THE TEXT FIELDS
        workspace.getTitleField().setText("");
        workspace.getTopicField().setText("");
        workspace.getLinkField().setText("");
        workspace.getCriteriaField().setText("");

        // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
        workspace.getTitleField().requestFocus();

        // WE'VE CHANGED STUFF
        markWorkAsEdited();
        workspace.getScheduleItemTable().refresh();

    }

    public void handleClearTeam() {
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getTeamAddUpdateButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

        // CLEAR THE TEXT FIELDS
        workspace.getNameField().setText("");
        workspace.getLinkField().setText("");

        // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
        workspace.getNameField().requestFocus();

        // WE'VE CHANGED STUFF
        markWorkAsEdited();
        workspace.getTeamTable().refresh();
    }

    public void handleClearStudent() {
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane workspace = csgW.getProjectPane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        workspace.getAddUpdateStudentButton().setText(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString()));

        // CLEAR THE TEXT FIELDS
        workspace.getFirstNameField().setText("");
        workspace.getLastNameField().setText("");
        workspace.getRoleField().setText("");

        // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
        workspace.getFirstNameBox().requestFocus();

        // WE'VE CHANGED STUFF
        markWorkAsEdited();
        workspace.getTeamTable().refresh();
    }

    public void handleChangeTime() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGWorkspace csgW = (CSGWorkspace) app.getWorkspaceComponent();
        TAPane workspace = csgW.getTaPane();
        ComboBox startTime = workspace.getStartTime();
        ComboBox endTime = workspace.getEndTime();
        String startStr = (String) startTime.getValue();
        String endStr = (String) endTime.getValue();
        String[] splitStartHr = startStr.split(":");
        String[] splitEndHr = endStr.split(":");
        int startInt = Integer.parseInt(splitStartHr[0]);
        if (startInt == 12) {
            startInt = 0;
        }
        if (splitStartHr[1].contains("pm")) {

            startInt += 12;

        }
        int endInt = Integer.parseInt(splitEndHr[0]);
        if (splitEndHr[1].contains("pm") && endInt != 12) {
            endInt += 12;
        }
        if (startInt > endInt) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TIME_TITLE), props.getProperty(INVALID_TIME_MESSAGE));
        } else {
            CSGData csgData = (CSGData) app.getDataComponent();
            TAData data = csgData.getTaData();
            if (startInt > data.getStartHour() || endInt < data.getEndHour()) {
                AppYesNoCancelDialogSingleton warning = AppYesNoCancelDialogSingleton.getSingleton();
                warning.show(props.getProperty(CHANGE_START_END_TIME_TITLE), props.getProperty(CHANGE_START_END_TIME_MESSAGE));
                if (warning.getSelection().contains("Yes")) {
                    jTPS_Transaction transaction = new ChangeStartAndEndTime_Transaction(startInt, endInt, data.getStartHour(), data.getEndHour(), app);
                    jTPS.addTransaction(transaction);
                    ArrayList<TimeSlot> officeHoursCopy = TimeSlot.buildOfficeHoursList(data);
                    workspace.resetWorkspace();
                    data.ChangeTime(startInt, endInt);
                    workspace.reloadOfficeHoursGrid(data);
                    markWorkAsEdited();
                    data.repopulateGrid(officeHoursCopy);
                }
            } else {

                jTPS_Transaction transaction = new ChangeStartAndEndTime_Transaction(startInt, endInt, data.getStartHour(), data.getEndHour(), app);
                jTPS.addTransaction(transaction);
                ArrayList<TimeSlot> officeHoursCopy = TimeSlot.buildOfficeHoursList(data);
                workspace.resetWorkspace();
                data.ChangeTime(startInt, endInt);
                workspace.reloadOfficeHoursGrid(data);
                markWorkAsEdited();
                data.repopulateGrid(officeHoursCopy);
            }
        }

    }

    public void HandleUndo() {
        jTPS.undoTransaction();
        markWorkAsEdited();
    }

    public void HandleRedo() {
        jTPS.doTransaction();
        markWorkAsEdited();
    }

    public void updateTAList() {
        CSGData dataManager = (CSGData) app.getDataComponent();
        TAData data = dataManager.getTaData();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane pane = workspace.getRecitationPane();
        ObservableList<TeachingAssistant> teachingAssistants = data.getTeachingAssistants();
        pane.getTa1Combo().getItems().clear();
        pane.getTa2Combo().getItems().clear();
        for (TeachingAssistant ta : teachingAssistants) {
            pane.getTa1Combo().getItems().add(ta.getName());
            pane.getTa2Combo().getItems().add(ta.getName());
        }
    }

    public void updateTeamList() {
        CSGData dataManager = (CSGData) app.getDataComponent();
        ProjectData data = dataManager.getProjectData();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane pane = workspace.getProjectPane();
        ObservableList<Team> teams = data.getTeams();
        pane.getTeamCombo().getItems().clear();
        for (Team team : teams) {
            pane.getTeamCombo().getItems().add(team.getName());
        }

    }
    

    public void commitCourseInfo() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CourseDetailsPane pane = workspace.getCourseDetailsPane();
        String subject = pane.getSubjectField().getText();
        String semester = pane.getSemesterField().getText();
        String number = pane.getNumberField().getText();
        String year = pane.getYearField().getText();
        String title = pane.getTitleField().getText();
        String instrName = pane.getInstrNameField().getText();
        String instrHome = pane.getInstrHomeField().getText();
        CSGData data = (CSGData) app.getDataComponent();
        CourseInfo courseInfo = data.getCourseDetailsData().getCourseInfo();
        String oldInstrHome = courseInfo.getInstrHome();
        String oldInstrName = courseInfo.getInstrName();
        String oldNumber = courseInfo.getNumber();
        String oldSemester = courseInfo.getSemester();
        String oldSubject = courseInfo.getSubject();
        String oldTitle = courseInfo.getTitle();
        String oldYear = courseInfo.getYear();
        jTPS_Transaction transaction = new CommitCourseInfo_Transaction(subject, semester, number, year, title, instrName, instrHome,
                oldSubject, oldSemester, oldNumber, oldYear, oldTitle, oldInstrName, oldInstrHome, app);
        jTPS.addTransaction(transaction);
        pane.reloadWorkspace(data);
        markWorkAsEdited();
    }

    public void changeExportDirectory() {
        CSGData data = (CSGData) app.getDataComponent();
        String oldDirectory = data.getCourseDetailsData().getCourseInfo().getExportDir();
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(app.getGUI().getWindow());
        if (selectedDirectory != null) {

            jTPS_Transaction transaction = new ChangeExportDirectory_Transaction(oldDirectory, selectedDirectory.toString(), app);
            jTPS.addTransaction(transaction);
        }
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetailsPane().reloadWorkspace(data);
        markWorkAsEdited();
    }

    public void selectTemplateDirectory() {
        CSGData data = (CSGData) app.getDataComponent();
        String oldTemplateDirectory = data.getCourseDetailsData().getSiteTemplate().getTemplateDirectory();
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(app.getGUI().getWindow());
        if (selectedDirectory != null) {

            jTPS_Transaction transaction = new SelectTemplateDirectory_Transaction(oldTemplateDirectory, selectedDirectory.toString(), app);
            jTPS.addTransaction(transaction);
        }
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetailsPane().reloadWorkspace(data);
        markWorkAsEdited();
    }

    public void changeBannerSchoolImage() {

    }

    public void changeLeftFooter() {

    }

    public void changeRightFooter() {

    }

    public void checkCaledarDates() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        DatePicker endDate = workspace.getSchedulePane().getEndDate();
        DatePicker startDate = workspace.getSchedulePane().getStartDate();
        if ((endDate.getValue().getYear() < startDate.getValue().getYear())
                || (endDate.getValue().getMonthValue() < startDate.getValue().getMonthValue())
                || endDate.getValue().getDayOfYear() < startDate.getValue().getDayOfYear()) {

            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TIME_TITLE), props.getProperty(INVALID_TIME_MESSAGE));

        }

    }

}
