/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.CSGeneratorProp;
import csg.data.CSGData;
import csg.data.ProjectData;
import csg.data.SitePages;
import csg.data.Student;
import csg.data.TAData;
import csg.data.Team;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jose
 */
public class ProjectPane extends ScrollPane{
    CSGController controller;
    private VBox entirePane;
    private HBox teamHeaderBox;
    private Label teamHeader;
    private Button deleteButton;
    private TableView<Team> teamTable;
    private TableColumn<Team,String> nameColumn;
    private  TableColumn<Team,String> colorColumn;
    private TableColumn<Team,String> textColorColumn;
    private  TableColumn<Team,String> linkColumn;

    private  Label addEditHeader;
    private  HBox nameBox;
    private  Label nameLabel;
    private  TextField nameField;
    private  HBox colorBox;
    private Label colorLabel;
    private ColorPicker colorPicker;
    private Label textColorLabel;
    private ColorPicker textColorPicker;
    private  HBox linkBox;
    private  Label linkLabel;
    private  TextField linkField;
    private  HBox teamButtonBox;
    private  Button teamAddUpdateButton;
    private  Button teamClearButton;
    private  VBox studentsPane;
    private  HBox studentHeaderBox;
    private  Label studentHeader;
    private  Button deleteStudentButton;
    private  TableView<Student> studentsTable;
    private  TableColumn<Student,String> firstNameColumn;
    private  TableColumn<Student,String> lastNameColumn;
    private  TableColumn<Student,String> TeamColumn;
    private  TableColumn<Student,String> RoleColumn;
    private  Label addEditLabel;
    private  HBox firstNameBox;
    private  Label firstNameLabel;
    private  TextField firstNameField;
    private  HBox lastNameBox;
    private  Label lastNameLabel;
    private  TextField lastNameField;
    private  HBox teamBox;
    private  Label teamLabel;
    private  ComboBox teamCombo;
    private  HBox roleBox;
    private  Label roleLabel;
    private  TextField roleField;
    private  HBox studentButtonBox;
    private  Button addUpdateStudentButton;
    private  Button clearStudentButton;
    private  VBox teamPane;
    

    ProjectPane(CSGeneratorApp app) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData csgData= (CSGData) app.getDataComponent();
        ProjectData data = csgData.getProjectData();
        entirePane= new VBox();
        teamPane = new VBox();
        //
        teamHeaderBox = new HBox();
        teamHeader = new Label(props.getProperty(CSGeneratorProp.TEAM_TEXT));
        deleteButton = new Button("-");
        teamHeaderBox.getChildren().addAll(teamHeader, deleteButton);
        //
        teamTable = new TableView();
        ObservableList<Team> tableData = data.getTeams();
        teamTable.setItems(tableData);
        nameColumn = new TableColumn(props.getProperty(CSGeneratorProp.NAME_COLUMN_TEXT));
        colorColumn = new TableColumn(props.getProperty(CSGeneratorProp.COLOR_TEXT));
        textColorColumn = new TableColumn(props.getProperty(CSGeneratorProp.TEXT_COLOR_TEXT));
        linkColumn = new TableColumn(props.getProperty(CSGeneratorProp.LINK_TEXT));
        //
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("Name"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("color"));
        textColorColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("textColor"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<Team, String>("link"));

        teamTable.getColumns().addAll(nameColumn,
                colorColumn,
                textColorColumn,
                linkColumn);
        //

        addEditHeader = new Label(props.getProperty(CSGeneratorProp.ADD_EDIT_HEADER));
        //
        nameBox = new HBox();
        nameLabel = new Label(props.getProperty(CSGeneratorProp.NAME_COLUMN_TEXT)+":");
        nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);
        //
        colorBox = new HBox();
        colorLabel = new Label(props.getProperty(CSGeneratorProp.COLOR_TEXT)+":");
        colorPicker = new ColorPicker();
        textColorLabel = new Label(props.getProperty(CSGeneratorProp.TEXT_COLOR_TEXT)+":");
        textColorPicker = new ColorPicker();
        colorBox.getChildren().addAll(colorLabel, colorPicker, textColorLabel, textColorPicker);
        //
        linkBox = new HBox();
        linkLabel = new Label(props.getProperty(CSGeneratorProp.LINK_TEXT)+":");
        linkField = new TextField();
        linkBox.getChildren().addAll(linkLabel, linkField);
        //
        teamButtonBox = new HBox();
        teamAddUpdateButton = new Button(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT));
        teamClearButton = new Button(props.getProperty(CSGeneratorProp.CLEAR_BUTTON_TEXT));
        teamButtonBox.getChildren().addAll(teamAddUpdateButton, teamClearButton);
        teamPane.getChildren().addAll(teamHeaderBox, teamTable, addEditHeader, nameBox, colorBox, linkBox, teamButtonBox);

        //
        studentsPane = new VBox();

        //
        studentHeaderBox = new HBox();
        studentHeader = new Label(props.getProperty(CSGeneratorProp.STUDENTS_HEADER));
        deleteStudentButton = new Button("-");
        studentHeaderBox.getChildren().addAll(studentHeader, deleteStudentButton);
        //
        studentsTable = new TableView();
        ObservableList<Student> studentTableData = data.getStudents();
        studentsTable.setItems(studentTableData);
        firstNameColumn = new TableColumn(props.getProperty(CSGeneratorProp.FIRST_NAME_TEXT));
        lastNameColumn = new TableColumn(props.getProperty(CSGeneratorProp.LAST_NAME_TEXT));
        TeamColumn = new TableColumn(props.getProperty(CSGeneratorProp.TEAM_TEXT));
        RoleColumn = new TableColumn(props.getProperty(CSGeneratorProp.ROLE_TEXT));
        
        //
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        TeamColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("team"));
        RoleColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("role"));
        //
        studentsTable.getColumns().addAll(firstNameColumn, lastNameColumn, TeamColumn, RoleColumn);
        //
        addEditLabel = new Label(props.getProperty(CSGeneratorProp.ADD_EDIT_HEADER));
        //
        firstNameBox = new HBox();
        firstNameLabel = new Label(props.getProperty(CSGeneratorProp.FIRST_NAME_TEXT)+":");
        firstNameField = new TextField();
        firstNameBox.getChildren().addAll(firstNameLabel, firstNameField);
        //
        lastNameBox = new HBox();
        lastNameLabel = new Label(props.getProperty(CSGeneratorProp.LAST_NAME_TEXT)+":");
        lastNameField = new TextField();
        lastNameBox.getChildren().addAll(lastNameLabel, lastNameField);
        //
        teamBox = new HBox();
        teamLabel = new Label(props.getProperty(CSGeneratorProp.TEAM_TEXT)+":");
        teamCombo = new ComboBox();
        teamBox.getChildren().addAll(teamLabel, teamCombo);
        //
        roleBox = new HBox();
        roleLabel = new Label(props.getProperty(CSGeneratorProp.ROLE_TEXT)+":");
        roleField = new TextField();
        roleBox.getChildren().addAll(roleLabel, roleField);
        //
        studentButtonBox = new HBox();
        addUpdateStudentButton = new Button(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT));
        clearStudentButton = new Button(props.getProperty(CSGeneratorProp.CLEAR_BUTTON_TEXT));
        studentButtonBox.getChildren().addAll(addUpdateStudentButton, clearStudentButton);
        //
        studentsPane.getChildren().addAll(studentHeaderBox, studentsTable, addEditLabel, firstNameBox, lastNameBox,
                 teamBox, roleBox, studentButtonBox);
        //
        entirePane.getChildren().add(teamPane);
        entirePane.getChildren().add(studentsPane);
        this.setContent(entirePane);
        this.setFitToWidth(true);
        
        KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z,
                KeyCombination.CONTROL_DOWN);
        //ctrlY
        KeyCodeCombination ctrlY = new KeyCodeCombination(KeyCode.Y,
                KeyCombination.CONTROL_DOWN);
        teamAddUpdateButton.setOnAction(e -> {
            controller.handleAddTeam();
        });
        teamClearButton.setOnAction(e -> {
            controller.handleClearTeam();
        });
        teamTable.setFocusTraversable(true);
        teamTable.setOnKeyPressed(e -> {
            controller.handleDeleteTeam(e.getCode());
        });
        teamTable.setOnMouseClicked(e -> {
            controller.handleEditTeam(teamTable);
        });
        
             
        addUpdateStudentButton.setOnAction(e -> {
            controller.handleAddStudent();
        });
        clearStudentButton.setOnAction(e -> {
            controller.handleClearStudent();
        });
        studentsTable.setFocusTraversable(true);
        studentsTable.setOnKeyPressed(e -> {
            controller.handleDeleteStudent(e.getCode());
        });
        deleteButton.setOnAction(e -> {
            controller.handleDeleteTeam();
        });
        deleteStudentButton.setOnAction(e -> {
            controller.handleDeleteStudent();
        });
        studentsTable.setOnMouseClicked(e -> {
            controller.handleEditStudent(studentsTable);
        });

    }

    public CSGController getController() {
        return controller;
    }

    public void setController(CSGController controller) {
        this.controller = controller;
    }

    public VBox getEntirePane() {
        return entirePane;
    }

    public void setEntirePane(VBox entirePane) {
        this.entirePane = entirePane;
    }

    public HBox getTeamHeaderBox() {
        return teamHeaderBox;
    }

    public void setTeamHeaderBox(HBox teamHeaderBox) {
        this.teamHeaderBox = teamHeaderBox;
    }

    public Label getTeamHeader() {
        return teamHeader;
    }

    public void setTeamHeader(Label teamHeader) {
        this.teamHeader = teamHeader;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public TableView getTeamTable() {
        return teamTable;
    }

    public void setTeamTable(TableView teamTable) {
        this.teamTable = teamTable;
    }

    public TableColumn getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn nameColumn) {
        this.nameColumn = nameColumn;
    }

    public TableColumn getColorColumn() {
        return colorColumn;
    }

    public void setColorColumn(TableColumn colorColumn) {
        this.colorColumn = colorColumn;
    }

    public TableColumn getTextColorColumn() {
        return textColorColumn;
    }

    public void setTextColorColumn(TableColumn textColorColumn) {
        this.textColorColumn = textColorColumn;
    }

    public Label getAddEditHeader() {
        return addEditHeader;
    }

    public void setAddEditHeader(Label addEditHeader) {
        this.addEditHeader = addEditHeader;
    }

    public HBox getNameBox() {
        return nameBox;
    }

    public void setNameBox(HBox nameBox) {
        this.nameBox = nameBox;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public HBox getColorBox() {
        return colorBox;
    }

    public void setColorBox(HBox colorBox) {
        this.colorBox = colorBox;
    }

    public Label getColorLabel() {
        return colorLabel;
    }

    public void setColorLabel(Label colorLabel) {
        this.colorLabel = colorLabel;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    public Label getTextColorLabel() {
        return textColorLabel;
    }

    public void setTextColorLabel(Label textColorLabel) {
        this.textColorLabel = textColorLabel;
    }

    public ColorPicker getTextColorPicker() {
        return textColorPicker;
    }

    public void setTextColorPicker(ColorPicker textColorPicker) {
        this.textColorPicker = textColorPicker;
    }

    public HBox getLinkBox() {
        return linkBox;
    }

    public void setLinkBox(HBox linkBox) {
        this.linkBox = linkBox;
    }

    public Label getLinkLabel() {
        return linkLabel;
    }

    public void setLinkLabel(Label linkLabel) {
        this.linkLabel = linkLabel;
    }

    public TextField getLinkField() {
        return linkField;
    }

    public void setLinkField(TextField linkField) {
        this.linkField = linkField;
    }

    public HBox getTeamButtonBox() {
        return teamButtonBox;
    }

    public void setTeamButtonBox(HBox teamButtonBox) {
        this.teamButtonBox = teamButtonBox;
    }

    public Button getTeamAddUpdateButton() {
        return teamAddUpdateButton;
    }

    public void setTeamAddUpdateButton(Button teamAddUpdateButton) {
        this.teamAddUpdateButton = teamAddUpdateButton;
    }

    public Button getTeamClearButton() {
        return teamClearButton;
    }

    public void setTeamClearButton(Button teamClearButton) {
        this.teamClearButton = teamClearButton;
    }

    public VBox getStudentsPane() {
        return studentsPane;
    }

    public void setStudentsPane(VBox studentsPane) {
        this.studentsPane = studentsPane;
    }

    public HBox getStudentHeaderBox() {
        return studentHeaderBox;
    }

    public void setStudentHeaderBox(HBox studentHeaderBox) {
        this.studentHeaderBox = studentHeaderBox;
    }

    public Label getStudentHeader() {
        return studentHeader;
    }

    public void setStudentHeader(Label studentHeader) {
        this.studentHeader = studentHeader;
    }

    public Button getDeleteStudentButton() {
        return deleteStudentButton;
    }

    public void setDeleteStudentButton(Button deleteStudentButton) {
        this.deleteStudentButton = deleteStudentButton;
    }

    public TableView getStudentsTable() {
        return studentsTable;
    }

    public void setStudentsTable(TableView studentsTable) {
        this.studentsTable = studentsTable;
    }

    public TableColumn getFirstNameColumn() {
        return firstNameColumn;
    }

    public void setFirstNameColumn(TableColumn firstNameColumn) {
        this.firstNameColumn = firstNameColumn;
    }

    public TableColumn getLastNameColumn() {
        return lastNameColumn;
    }

    public void setLastNameColumn(TableColumn lastNameColumn) {
        this.lastNameColumn = lastNameColumn;
    }

    public TableColumn getTeamColumn() {
        return TeamColumn;
    }

    public void setTeamColumn(TableColumn TeamColumn) {
        this.TeamColumn = TeamColumn;
    }

    public TableColumn getRoleColumn() {
        return RoleColumn;
    }

    public void setRoleColumn(TableColumn RoleColumn) {
        this.RoleColumn = RoleColumn;
    }

    public Label getAddEditLabel() {
        return addEditLabel;
    }

    public void setAddEditLabel(Label addEditLabel) {
        this.addEditLabel = addEditLabel;
    }

    public HBox getFirstNameBox() {
        return firstNameBox;
    }

    public void setFirstNameBox(HBox firstNameBox) {
        this.firstNameBox = firstNameBox;
    }

    public Label getFirstNameLabel() {
        return firstNameLabel;
    }

    public void setFirstNameLabel(Label firstNameLabel) {
        this.firstNameLabel = firstNameLabel;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public void setFirstNameField(TextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public HBox getLastNameBox() {
        return lastNameBox;
    }

    public void setLastNameBox(HBox lastNameBox) {
        this.lastNameBox = lastNameBox;
    }

    public Label getLastNameLabel() {
        return lastNameLabel;
    }

    public void setLastNameLabel(Label lastNameLabel) {
        this.lastNameLabel = lastNameLabel;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public void setLastNameField(TextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public HBox getTeamBox() {
        return teamBox;
    }

    public void setTeamBox(HBox teamBox) {
        this.teamBox = teamBox;
    }

    public Label getTeamLabel() {
        return teamLabel;
    }

    public void setTeamLabel(Label teamLabel) {
        this.teamLabel = teamLabel;
    }

    public ComboBox getTeamCombo() {
        return teamCombo;
    }

    public void setTeamCombo(ComboBox teamCombo) {
        this.teamCombo = teamCombo;
    }

    public HBox getRoleBox() {
        return roleBox;
    }

    public void setRoleBox(HBox roleBox) {
        this.roleBox = roleBox;
    }

    public Label getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(Label roleLabel) {
        this.roleLabel = roleLabel;
    }

    public TextField getRoleField() {
        return roleField;
    }

    public void setRoleField(TextField roleField) {
        this.roleField = roleField;
    }

    public HBox getStudentButtonBox() {
        return studentButtonBox;
    }

    public void setStudentButtonBox(HBox studentButtonBox) {
        this.studentButtonBox = studentButtonBox;
    }

    public Button getAddUpdateStudentButton() {
        return addUpdateStudentButton;
    }

    public void setAddUpdateStudentButton(Button addUpdateStudentButton) {
        this.addUpdateStudentButton = addUpdateStudentButton;
    }

    public Button getClearStudentButton() {
        return clearStudentButton;
    }

    public void setClearStudentButton(Button clearStudentButton) {
        this.clearStudentButton = clearStudentButton;
    }

    public VBox getTeamPane() {
        return teamPane;
    }

    public void setTeamPane(VBox teamPane) {
        this.teamPane = teamPane;
    }

    public TableColumn getLinkColumn() {
        return linkColumn;
    }

    public void setLinkColumn(TableColumn linkColumn) {
        this.linkColumn = linkColumn;
    }

    void reloadWorkspace(AppDataComponent dataComponent) {
        
        controller.updateTeamList();
    }

}
