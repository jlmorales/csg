/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.CSGeneratorProp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.SitePages;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
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
public class RecitationPane extends ScrollPane {

    CSGController controller;
    private VBox entirePane;
    private VBox recitationPane;
    private HBox headerBox;
    private Label recitationHeader;
    private Button deleteRecitation;
    //
    private KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z,
            KeyCombination.CONTROL_DOWN);
    //ctrlY
    private KeyCodeCombination ctrlY = new KeyCodeCombination(KeyCode.Y,
            KeyCombination.CONTROL_DOWN);
    //
    private TableView<Recitation> recitationTable;
    private TableColumn<Recitation, String> sectionColumn;
    private TableColumn<Recitation, String> instructorColumn;
    private TableColumn<Recitation, String> dayTimeColumn;
    private TableColumn<Recitation, String> locationColumn;
    private TableColumn<Recitation, String> ta1Column;
    private TableColumn<Recitation, String> ta2Column;
    //
    private VBox addEditBox;
    private Label addEditHeader;
    //
    private HBox sectionBox;
    private Label sectionLabel;
    private TextField sectionField;
    //
    private HBox instructorBox;
    private Label instructorLabel;
    private TextField instructorField;
    //
    private HBox dayTimeBox;
    private Label dayTimeLabel;
    private TextField dayTimeField;
    //
    private HBox locationBox;
    private Label locationLabel;
    private TextField locationField;
    //
    private HBox ta1Box;
    private Label ta1Label;
    private ComboBox ta1Combo;
    //
    //
    private HBox ta2Box;
    private Label ta2Label;
    private ComboBox ta2Combo;
    //
    private HBox buttonbox;
    private Button addUpdateButton;
    private Button clearButton;

    public RecitationPane(CSGeneratorApp app) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData csgData = (CSGData) app.getDataComponent();
        RecitationData data = csgData.getRecitationData();

        entirePane = new VBox();
        recitationPane = new VBox();
        headerBox = new HBox();
        recitationHeader = new Label(props.getProperty(CSGeneratorProp.RECITATION_HEADER));
        deleteRecitation = new Button("-");
        headerBox.getChildren().addAll(recitationHeader, deleteRecitation);
        //
        recitationTable = new TableView();
        ObservableList<Recitation> tableData = data.getRecitations();
        recitationTable.setItems(tableData);
        sectionColumn = new TableColumn(props.getProperty(CSGeneratorProp.SECTION_TEXT));
        instructorColumn = new TableColumn(props.getProperty(CSGeneratorProp.INSTRUCTOR_TEXT));
        dayTimeColumn = new TableColumn(props.getProperty(CSGeneratorProp.DAY_TIME_TEXT));
        locationColumn = new TableColumn(props.getProperty(CSGeneratorProp.LOCATION_TEXT));
        ta1Column = new TableColumn(props.getProperty(CSGeneratorProp.TA_TEXT));
        ta2Column = new TableColumn(props.getProperty(CSGeneratorProp.TA_TEXT));
        //
        sectionColumn.setCellValueFactory(new PropertyValueFactory<Recitation, String>("section"));
        instructorColumn.setCellValueFactory(new PropertyValueFactory<Recitation, String>("instructor"));
        dayTimeColumn.setCellValueFactory(new PropertyValueFactory<Recitation, String>("dayTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Recitation, String>("location"));
        ta1Column.setCellValueFactory(new PropertyValueFactory<Recitation, String>("ta1"));
        ta2Column.setCellValueFactory(new PropertyValueFactory<Recitation, String>("ta2"));
        //
        recitationTable.getColumns().addAll(sectionColumn, instructorColumn,
                dayTimeColumn,
                locationColumn,
                ta1Column,
                ta2Column);
        //
        recitationPane.getChildren().addAll(headerBox, recitationTable);
        //
        addEditBox = new VBox();
        addEditHeader = new Label(props.getProperty(CSGeneratorProp.ADD_EDIT_HEADER));
        //
        sectionBox = new HBox();
        sectionLabel = new Label(props.getProperty(CSGeneratorProp.SECTION_TEXT) + ":");
        sectionField = new TextField();
        sectionBox.getChildren().addAll(sectionLabel, sectionField);
        //
        instructorBox = new HBox();
        instructorLabel = new Label(props.getProperty(CSGeneratorProp.INSTRUCTOR_TEXT) + ":");
        instructorField = new TextField();
        instructorBox.getChildren().addAll(instructorLabel, instructorField);
        //
        dayTimeBox = new HBox();
        dayTimeLabel = new Label(props.getProperty(CSGeneratorProp.DAY_TIME_TEXT) + ":");
        dayTimeField = new TextField();
        dayTimeBox.getChildren().addAll(dayTimeLabel, dayTimeField);
        //
        locationBox = new HBox();
        locationLabel = new Label(props.getProperty(CSGeneratorProp.LOCATION_TEXT) + ":");
        locationField = new TextField();
        locationBox.getChildren().addAll(locationLabel, locationField);
        //
        ta1Box = new HBox();
        ta1Label = new Label(props.getProperty(CSGeneratorProp.SUPERVISING_TA_TEXT));
        ta1Combo = new ComboBox();

        ta1Box.getChildren().addAll(ta1Label, ta1Combo);
        //
        //
        ta2Box = new HBox();
        ta2Label = new Label(props.getProperty(CSGeneratorProp.SUPERVISING_TA_TEXT));
        ta2Combo = new ComboBox();
        TAData taData = csgData.getTaData();
        ObservableList<TeachingAssistant> teachingAssistants = taData.getTeachingAssistants();

        for (TeachingAssistant ta : teachingAssistants) {
            ta1Combo.getItems().add(ta.getName());
            ta2Combo.getItems().add(ta.getName());
        }

        ta2Box.getChildren().addAll(ta2Label, ta2Combo);
        //
        buttonbox = new HBox();
        addUpdateButton = new Button(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT));
        clearButton = new Button(props.getProperty(CSGeneratorProp.CLEAR_BUTTON_TEXT));
        buttonbox.getChildren().addAll(addUpdateButton, clearButton);
        //
        addEditBox.getChildren().addAll(addEditHeader, sectionBox, instructorBox, dayTimeBox, locationBox, ta1Box, ta2Box, buttonbox);
        //
        recitationPane.setAlignment(Pos.CENTER);
        addEditBox.setAlignment(Pos.CENTER);
        entirePane.getChildren().add(recitationPane);
        entirePane.getChildren().add(addEditBox);
        this.setContent(entirePane);
        this.setFitToWidth(true);
        addUpdateButton.setOnAction(e -> {
            controller.handleAddRecitation();
        });
        clearButton.setOnAction(e -> {
            controller.handleClearRecitation();
        });
        
        recitationTable.setFocusTraversable(true);
        recitationTable.setOnKeyPressed(e -> {
            controller.handleDeleteRecitation(e.getCode());
        });
        this.deleteRecitation.setOnAction(e -> {
            controller.handleDeleteRecitation();
        });
        
        recitationTable.setOnMouseClicked(e -> {
            controller.handleEditRecitation(recitationTable);
        });
        app.getGUI().getAppPane().setOnKeyPressed(e -> {
            if (ctrlZ.match(e)) {
                controller.HandleUndo();
            }
        });

        app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
            if (ctrlY.match(e)) {
                controller.HandleRedo();
            }
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

    public VBox getRecitationPane() {
        return recitationPane;
    }

    public void setRecitationPane(VBox recitationPane) {
        this.recitationPane = recitationPane;
    }

    public HBox getHeaderBox() {
        return headerBox;
    }

    public void setHeaderBox(HBox headerBox) {
        this.headerBox = headerBox;
    }

    public Label getRecitationHeader() {
        return recitationHeader;
    }

    public void setRecitationHeader(Label recitationHeader) {
        this.recitationHeader = recitationHeader;
    }

    public Button getDeleteRecitation() {
        return deleteRecitation;
    }

    public void setDeleteRecitation(Button deleteRecitation) {
        this.deleteRecitation = deleteRecitation;
    }

    public TableView getRecitationTable() {
        return recitationTable;
    }

    public void setRecitationTable(TableView recitationTable) {
        this.recitationTable = recitationTable;
    }

    public TableColumn getSectionColumn() {
        return sectionColumn;
    }

    public void setSectionColumn(TableColumn sectionColumn) {
        this.sectionColumn = sectionColumn;
    }

    public TableColumn getInstructorColumn() {
        return instructorColumn;
    }

    public void setInstructorColumn(TableColumn instructorColumn) {
        this.instructorColumn = instructorColumn;
    }

    public TableColumn getDayTimeColumn() {
        return dayTimeColumn;
    }

    public void setDayTimeColumn(TableColumn dayTimeColumn) {
        this.dayTimeColumn = dayTimeColumn;
    }

    public TableColumn getLocationColumn() {
        return locationColumn;
    }

    public void setLocationColumn(TableColumn locationColumn) {
        this.locationColumn = locationColumn;
    }

    public TableColumn getTa1Column() {
        return ta1Column;
    }

    public void setTa1Column(TableColumn ta1Column) {
        this.ta1Column = ta1Column;
    }

    public TableColumn getTa2Column() {
        return ta2Column;
    }

    public void setTa2Column(TableColumn ta2Column) {
        this.ta2Column = ta2Column;
    }

    public VBox getAddEditBox() {
        return addEditBox;
    }

    public void setAddEditBox(VBox addEditBox) {
        this.addEditBox = addEditBox;
    }

    public Label getAddEditHeader() {
        return addEditHeader;
    }

    public void setAddEditHeader(Label addEditHeader) {
        this.addEditHeader = addEditHeader;
    }

    public HBox getSectionBox() {
        return sectionBox;
    }

    public void setSectionBox(HBox sectionBox) {
        this.sectionBox = sectionBox;
    }

    public Label getSectionLabel() {
        return sectionLabel;
    }

    public void setSectionLabel(Label sectionLabel) {
        this.sectionLabel = sectionLabel;
    }

    public TextField getSectionField() {
        return sectionField;
    }

    public void setSectionField(TextField sectionField) {
        this.sectionField = sectionField;
    }

    public HBox getInstructorBox() {
        return instructorBox;
    }

    public void setInstructorBox(HBox instructorBox) {
        this.instructorBox = instructorBox;
    }

    public Label getInstructorLabel() {
        return instructorLabel;
    }

    public void setInstructorLabel(Label instructorLabel) {
        this.instructorLabel = instructorLabel;
    }

    public TextField getInstructorField() {
        return instructorField;
    }

    public void setInstructorField(TextField instructorField) {
        this.instructorField = instructorField;
    }

    public HBox getDayTimeBox() {
        return dayTimeBox;
    }

    public void setDayTimeBox(HBox dayTimeBox) {
        this.dayTimeBox = dayTimeBox;
    }

    public Label getDayTimeLabel() {
        return dayTimeLabel;
    }

    public void setDayTimeLabel(Label dayTimeLabel) {
        this.dayTimeLabel = dayTimeLabel;
    }

    public TextField getDayTimeField() {
        return dayTimeField;
    }

    public void setDayTimeField(TextField dayTimeField) {
        this.dayTimeField = dayTimeField;
    }

    public HBox getLocationBox() {
        return locationBox;
    }

    public void setLocationBox(HBox locationBox) {
        this.locationBox = locationBox;
    }

    public Label getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(Label locationLabel) {
        this.locationLabel = locationLabel;
    }

    public TextField getLocationField() {
        return locationField;
    }

    public void setLocationField(TextField locationField) {
        this.locationField = locationField;
    }

    public HBox getTa1Box() {
        return ta1Box;
    }

    public void setTa1Box(HBox ta1Box) {
        this.ta1Box = ta1Box;
    }

    public Label getTa1Label() {
        return ta1Label;
    }

    public void setTa1Label(Label ta1Label) {
        this.ta1Label = ta1Label;
    }

    public ComboBox getTa1Combo() {
        return ta1Combo;
    }

    public void setTa1Combo(ComboBox ta1Combo) {
        this.ta1Combo = ta1Combo;
    }

    public HBox getTa2Box() {
        return ta2Box;
    }

    public void setTa2Box(HBox ta2Box) {
        this.ta2Box = ta2Box;
    }

    public Label getTa2Label() {
        return ta2Label;
    }

    public void setTa2Label(Label ta2Label) {
        this.ta2Label = ta2Label;
    }

    public ComboBox getTa2Combo() {
        return ta2Combo;
    }

    public void setTa2Combo(ComboBox ta2Combo) {
        this.ta2Combo = ta2Combo;
    }

    public HBox getButtonbox() {
        return buttonbox;
    }

    public void setButtonbox(HBox buttonbox) {
        this.buttonbox = buttonbox;
    }

    public Button getAddUpdateButton() {
        return addUpdateButton;
    }

    public void setAddUpdateButton(Button addUpdateButton) {
        this.addUpdateButton = addUpdateButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public void setClearButton(Button clearButton) {
        this.clearButton = clearButton;
    }

    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData dataManager = (CSGData) dataComponent;
        TAData data = dataManager.getTaData();
        this.getTa1Combo().getSelectionModel().selectFirst();
        this.getTa2Combo().getSelectionModel().selectFirst();
        ObservableList<TeachingAssistant> teachingAssistants = data.getTeachingAssistants();

        for (TeachingAssistant ta : teachingAssistants) {
            ta1Combo.getItems().add(ta.getName());
            ta2Combo.getItems().add(ta.getName());
        }
    }
}
