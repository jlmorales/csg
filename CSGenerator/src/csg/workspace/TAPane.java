/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.CSGeneratorProp;
import csg.data.CSGData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.style.CSGStyle;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jose
 */
public class TAPane extends BorderPane {

    private CSGeneratorApp app;
    CSGController controller;

    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    //ctlZ
    private KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z,
            KeyCombination.CONTROL_DOWN);
    //ctrlY
    private KeyCodeCombination ctrlY = new KeyCodeCombination(KeyCode.Y,
            KeyCombination.CONTROL_DOWN);

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    // FOR THE HEADER ON THE LEFT
    private HBox tasHeaderBox;
    private Label tasHeaderLabel;

    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;

    // THE TA INPUT
    private HBox addBox;
    private TextField nameTextField;
    private TextField emailTextField;
    private Button addButton;
    private Button clearButton;

    // THE HEADER ON THE RIGHT
    private HBox officeHoursHeaderBox;
    private Label officeHoursHeaderLabel;

    // THE OFFICE HOURS GRID
    private GridPane officeHoursGridPane;
    private HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    private HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    private HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    private HashMap<String, Label> officeHoursGridDayHeaderLabels;
    private HashMap<String, Pane> officeHoursGridTimeCellPanes;
    private HashMap<String, Label> officeHoursGridTimeCellLabels;
    private HashMap<String, Pane> officeHoursGridTACellPanes;
    private HashMap<String, Label> officeHoursGridTACellLabels;

    //ComboBox
    private ComboBox startTime;
    private ComboBox endTime;
    private Button submitTime;

    TAPane(CSGeneratorApp app) {
        this.app = app;
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CSGeneratorProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData csgData= (CSGData) app.getDataComponent();
        TAData data = csgData.getTaData();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(CSGeneratorProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGeneratorProp.EMAIL_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CSGeneratorProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(CSGeneratorProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(CSGeneratorProp.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button(clearButtonText);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(CSGeneratorProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);

        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        //ComboBox
        startTime = new ComboBox();
        startTime.setPromptText(props.getProperty(CSGeneratorProp.START_TIME_TEXT.toString()));
        endTime = new ComboBox();
        endTime.setPromptText(props.getProperty(CSGeneratorProp.END_TIME_TEXT.toString()));
        submitTime = new Button(props.getProperty(CSGeneratorProp.SUBMIT_BUTTON_TEXT));
        

        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);
        leftPane.getChildren().add(taTable);
        leftPane.getChildren().add(addBox);
        /* VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
         */

 /*
        VBox centerPane = new VBox();
        HBox rightrightPane = new HBox();
        HBox rightPane = new HBox();
        centerPane.getChildren().add(officeHoursHeaderBox);
        centerPane.getChildren().add(officeHoursGridPane);
        rightrightPane.getChildren().add(startTime);
        rightrightPane.getChildren().add(endTime);
        rightrightPane.getChildren().add(submitTime);
        rightPane.getChildren().addAll(centerPane, rightrightPane);
         */
        VBox centerPane = new VBox();
        VBox rightrightPane = new VBox();
        HBox startendpane = new HBox();
        HBox rightPane = new HBox();
        centerPane.getChildren().add(officeHoursHeaderBox);
        centerPane.getChildren().add(officeHoursGridPane);
        startendpane.getChildren().add(startTime);
        startendpane.getChildren().add(endTime);
        rightrightPane.getChildren().add(startendpane);
        rightrightPane.getChildren().add(submitTime);
        rightPane.getChildren().addAll(centerPane, rightrightPane);

        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));

        // AND PUT EVERYTHING IN THE WORKSPACE
        this.setCenter(sPane);

        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        taTable.prefHeightProperty().bind(this.heightProperty().multiply(1.9));

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        //taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));
        
        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();
        });
        clearButton.setOnAction(e -> {
            controller.handleClearTA();
        });

        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        
        taTable.setOnMouseClicked(e -> {
            controller.handleEditTA(taTable);
        });
    
        submitTime.setOnMousePressed(e->{
            controller.handleChangeTime();
        });
        app.getGUI().getAppPane().setOnKeyPressed(e->{
           if( ctrlZ.match(e)){
              controller.HandleUndo();
           }
        });
        
        app.getGUI().getPrimaryScene().setOnKeyPressed(e->{
           if( ctrlY.match(e)){
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

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        if (militaryHour == 0) {
            hour = 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }

        return cellText;
    }

    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }
     public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();

        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();

    }

    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData data = (CSGData) dataComponent;
        TAData taData = data.getTaData();
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            int init = i;
            //if(i==0)
            //    i=12;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
            col++;
            //if(init==0)
            //    i=0;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row + 1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }
         
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        CSGStyle csgStyle = (CSGStyle) app.getStyleComponent();
        csgStyle.initOfficeHoursGridStyle();
        //
        CSGData csgData= (CSGData) app.getDataComponent();
        TAData data= csgData.getTaData();
        data.createHourList();

    }

    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);

        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);

        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);

        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());
    }

    public CSGeneratorApp getApp() {
        return app;
    }

    public void setApp(CSGeneratorApp app) {
        this.app = app;
    }

    public KeyCodeCombination getCtrlZ() {
        return ctrlZ;
    }

    public void setCtrlZ(KeyCodeCombination ctrlZ) {
        this.ctrlZ = ctrlZ;
    }

    public KeyCodeCombination getCtrlY() {
        return ctrlY;
    }

    public void setCtrlY(KeyCodeCombination ctrlY) {
        this.ctrlY = ctrlY;
    }

    public HBox getTasHeaderBox() {
        return tasHeaderBox;
    }

    public void setTasHeaderBox(HBox tasHeaderBox) {
        this.tasHeaderBox = tasHeaderBox;
    }

    public Label getTasHeaderLabel() {
        return tasHeaderLabel;
    }

    public void setTasHeaderLabel(Label tasHeaderLabel) {
        this.tasHeaderLabel = tasHeaderLabel;
    }

    public TableView getTaTable() {
        return taTable;
    }

    public void setTaTable(TableView taTable) {
        this.taTable = taTable;
    }

    public TableColumn getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn nameColumn) {
        this.nameColumn = nameColumn;
    }

    public TableColumn getEmailColumn() {
        return emailColumn;
    }

    public void setEmailColumn(TableColumn emailColumn) {
        this.emailColumn = emailColumn;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public void setAddBox(HBox addBox) {
        this.addBox = addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public void setClearButton(Button clearButton) {
        this.clearButton = clearButton;
    }

    public HBox getOfficeHoursHeaderBox() {
        return officeHoursHeaderBox;
    }

    public void setOfficeHoursHeaderBox(HBox officeHoursHeaderBox) {
        this.officeHoursHeaderBox = officeHoursHeaderBox;
    }

    public Label getOfficeHoursHeaderLabel() {
        return officeHoursHeaderLabel;
    }

    public void setOfficeHoursHeaderLabel(Label officeHoursHeaderLabel) {
        this.officeHoursHeaderLabel = officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public void setOfficeHoursGridPane(GridPane officeHoursGridPane) {
        this.officeHoursGridPane = officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public void setOfficeHoursGridTimeHeaderPanes(HashMap<String, Pane> officeHoursGridTimeHeaderPanes) {
        this.officeHoursGridTimeHeaderPanes = officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public void setOfficeHoursGridTimeHeaderLabels(HashMap<String, Label> officeHoursGridTimeHeaderLabels) {
        this.officeHoursGridTimeHeaderLabels = officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public void setOfficeHoursGridDayHeaderPanes(HashMap<String, Pane> officeHoursGridDayHeaderPanes) {
        this.officeHoursGridDayHeaderPanes = officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public void setOfficeHoursGridDayHeaderLabels(HashMap<String, Label> officeHoursGridDayHeaderLabels) {
        this.officeHoursGridDayHeaderLabels = officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public void setOfficeHoursGridTimeCellPanes(HashMap<String, Pane> officeHoursGridTimeCellPanes) {
        this.officeHoursGridTimeCellPanes = officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public void setOfficeHoursGridTimeCellLabels(HashMap<String, Label> officeHoursGridTimeCellLabels) {
        this.officeHoursGridTimeCellLabels = officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public void setOfficeHoursGridTACellPanes(HashMap<String, Pane> officeHoursGridTACellPanes) {
        this.officeHoursGridTACellPanes = officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }

    public void setOfficeHoursGridTACellLabels(HashMap<String, Label> officeHoursGridTACellLabels) {
        this.officeHoursGridTACellLabels = officeHoursGridTACellLabels;
    }

    public ComboBox getStartTime() {
        return startTime;
    }

    public void setStartTime(ComboBox startTime) {
        this.startTime = startTime;
    }

    public ComboBox getEndTime() {
        return endTime;
    }

    public void setEndTime(ComboBox endTime) {
        this.endTime = endTime;
    }

    public Button getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Button submitTime) {
        this.submitTime = submitTime;
    }

}
