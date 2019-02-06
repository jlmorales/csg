/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.CSGeneratorProp;
import csg.data.CSGData;
import csg.data.CalendarBoundaries;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.data.SitePages;
import djf.components.AppDataComponent;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class SchedulePane extends ScrollPane {

    CSGController controller;
    private VBox entirePane;
    private Label schduleHeader;
    private VBox calendarBoundariesPane;
    private Label calendarBoundariesLabel;
    private HBox calendarBoundariesBox;
    private DatePicker startDate;
    private DatePicker endDate;
    //
    private VBox schedulePane;
    private HBox scheduleHeaderBox;
    private Label scheduleItemsHeader;
    private Button deleteScheduleItem;
    private TableView<ScheduleItem> scheduleItemTable;
    private TableColumn<ScheduleItem, String> typeColumn;
    private TableColumn<ScheduleItem, String> dateColumn;
    private TableColumn<ScheduleItem, String> titleColumn;
    private TableColumn<ScheduleItem, String> topicColumn;

    //
    private Label addEditHeader;
    //
    private HBox typeBox;
    private Label typeLabel;
    private ComboBox typeCombo;
    //
    private HBox dateBox;
    private Label dateLabel;
    private DatePicker datePicker;
    //
    private HBox titleBox;
    private Label titleLabel;
    private TextField titleField;
    //
    private HBox topicBox;
    private Label topicLabel;
    private TextField topicField;
    //
    private HBox linkBox;
    private Label linkLabel;
    private TextField linkField;
    //
    //
    private HBox criteriaBox;
    private Label criteriaLabel;
    private TextField criteriaField;
    //
    private HBox buttonbox;
    private Button addUpdateButton;
    private Button clearButton;

    public SchedulePane(CSGeneratorApp app) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData csgData = (CSGData) app.getDataComponent();
        ScheduleData data = csgData.getScheduleData();
        entirePane = new VBox();
        schduleHeader = new Label();
        //
        CalendarBoundaries cal = data.getCalendarBoudnaries();
        calendarBoundariesPane = new VBox();
        calendarBoundariesLabel = new Label(props.getProperty(CSGeneratorProp.CALENDAR_BOUNDARIES_TEXT));
        calendarBoundariesBox = new HBox();
        Label startingMondayText = new Label(props.getProperty(CSGeneratorProp.STARTING_MONDAYS_TEXT));
        startDate = new DatePicker();
        startDate.setValue(LocalDate.of(Integer.parseInt(cal.getStartingMondayYear()),
                Integer.parseInt(cal.getStartingMondayMonth()),
                Integer.parseInt(cal.getStartingMondayDay())));

        Label endingFridayText = new Label(props.getProperty(CSGeneratorProp.ENDING_FRIDAY_TEXT));
        endDate = new DatePicker();
        endDate.setValue(LocalDate.of(Integer.parseInt(cal.getEndingFridayYear()),
                Integer.parseInt(cal.getEndingFridayMonth()),
                Integer.parseInt(cal.getEndingFridayDay())));
        calendarBoundariesBox.getChildren().addAll(startingMondayText, startDate, endingFridayText, endDate);
        calendarBoundariesPane.getChildren().addAll(calendarBoundariesLabel, calendarBoundariesBox);

        //
        schedulePane = new VBox();
        //
        scheduleHeaderBox = new HBox();
        scheduleItemsHeader = new Label(props.getProperty(CSGeneratorProp.SCHEDULE_ITEMS_HEADER));
        deleteScheduleItem = new Button("-");
        scheduleHeaderBox.getChildren().addAll(scheduleItemsHeader, deleteScheduleItem);

        scheduleItemTable = new TableView();

        ObservableList<ScheduleItem> tableData = data.getScheduleItems();
        scheduleItemTable.setItems(tableData);
        typeColumn = new TableColumn(props.getProperty(CSGeneratorProp.TYPE_TEXT));
        dateColumn = new TableColumn(props.getProperty(CSGeneratorProp.DATE_TEXT));
        titleColumn = new TableColumn(props.getProperty(CSGeneratorProp.TITLE_TEXT));
        topicColumn = new TableColumn(props.getProperty(CSGeneratorProp.TOPIC_TEXT));
        //
        typeColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("topic"));

        //
        scheduleItemTable.getColumns().addAll(typeColumn, dateColumn, titleColumn, topicColumn);

        //
        addEditHeader = new Label(props.getProperty(CSGeneratorProp.ADD_EDIT_HEADER));
        //
        typeBox = new HBox();
        typeLabel = new Label(props.getProperty(CSGeneratorProp.TYPE_TEXT) + ":");
        typeCombo = new ComboBox();
        typeCombo.getItems().add("Holiday");
        typeCombo.getItems().add("Lecture");
        typeBox.getChildren().addAll(typeLabel, typeCombo);

        //
        dateBox = new HBox();
        dateLabel = new Label(props.getProperty(CSGeneratorProp.DATE_TEXT) + ":");
        datePicker = new DatePicker();
        dateBox.getChildren().addAll(dateLabel, datePicker);
        //
        titleBox = new HBox();
        titleLabel = new Label(props.getProperty(CSGeneratorProp.TITLE_TEXT) + ":");
        titleField = new TextField();
        titleBox.getChildren().addAll(titleLabel, titleField);
        //
        topicBox = new HBox();
        topicLabel = new Label(props.getProperty(CSGeneratorProp.TOPIC_TEXT) + ":");
        topicField = new TextField();
        topicBox.getChildren().addAll(topicLabel, topicField);
        //
        linkBox = new HBox();
        linkLabel = new Label(props.getProperty(CSGeneratorProp.LINK_TEXT) + ":");
        linkField = new TextField();
        linkBox.getChildren().addAll(linkLabel, linkField);
        //
        //
        criteriaBox = new HBox();
        criteriaLabel = new Label(props.getProperty(CSGeneratorProp.CRITERIA_TEXT) + ":");
        criteriaField = new TextField();
        criteriaBox.getChildren().addAll(criteriaLabel, criteriaField);
        //
        buttonbox = new HBox();
        addUpdateButton = new Button(props.getProperty(CSGeneratorProp.ADD_BUTTON_TEXT));
        clearButton = new Button(props.getProperty(CSGeneratorProp.CLEAR_BUTTON_TEXT));
        buttonbox.getChildren().addAll(addUpdateButton, clearButton);
        //
        schedulePane.getChildren().addAll(scheduleHeaderBox, scheduleItemTable, addEditHeader, typeBox, dateBox, titleBox,
                 topicBox, linkBox, criteriaBox, buttonbox);

        entirePane.getChildren().add(calendarBoundariesPane);
        entirePane.getChildren().add(schedulePane);
        this.setContent(entirePane);
        this.setFitToWidth(true);
        KeyCodeCombination ctrlZ = new KeyCodeCombination(KeyCode.Z,
                KeyCombination.CONTROL_DOWN);
        //ctrlY
        KeyCodeCombination ctrlY = new KeyCodeCombination(KeyCode.Y,
                KeyCombination.CONTROL_DOWN);
        addUpdateButton.setOnAction(e -> {
            controller.handleAddScheduleItem();
        });
        clearButton.setOnAction(e -> {
            controller.handleClearSchedule();
        });
        scheduleItemTable.setFocusTraversable(true);
        this.deleteScheduleItem.setOnAction(e -> {
            controller.handleDeleteScheduleItem();
        });
        
        scheduleItemTable.setOnKeyPressed(e -> {
            controller.handleDeleteScheduleItem(e.getCode());
        });
        scheduleItemTable.setOnMouseClicked(e -> {
            controller.handleEditScheduleItem(scheduleItemTable);
        });
        this.startDate.setOnAction(e -> {
            controller.checkCaledarDates();
        });
        this.endDate.setOnAction(e -> {
            controller.checkCaledarDates();
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

    public Label getSchduleHeader() {
        return schduleHeader;
    }

    public void setSchduleHeader(Label schduleHeader) {
        this.schduleHeader = schduleHeader;
    }

    public VBox getCalendarBoundariesPane() {
        return calendarBoundariesPane;
    }

    public void setCalendarBoundariesPane(VBox calendarBoundariesPane) {
        this.calendarBoundariesPane = calendarBoundariesPane;
    }

    public Label getCalendarBoundariesLabel() {
        return calendarBoundariesLabel;
    }

    public void setCalendarBoundariesLabel(Label calendarBoundariesLabel) {
        this.calendarBoundariesLabel = calendarBoundariesLabel;
    }

    public HBox getCalendarBoundariesBox() {
        return calendarBoundariesBox;
    }

    public void setCalendarBoundariesBox(HBox calendarBoundariesBox) {
        this.calendarBoundariesBox = calendarBoundariesBox;
    }

    public DatePicker getStartDate() {
        return startDate;
    }

    public void setStartDate(DatePicker startDate) {
        this.startDate = startDate;
    }

    public DatePicker getEndDate() {
        return endDate;
    }

    public void setEndDate(DatePicker endDate) {
        this.endDate = endDate;
    }

    public VBox getSchedulePane() {
        return schedulePane;
    }

    public void setSchedulePane(VBox schedulePane) {
        this.schedulePane = schedulePane;
    }

    public HBox getScheduleHeaderBox() {
        return scheduleHeaderBox;
    }

    public void setScheduleHeaderBox(HBox scheduleHeaderBox) {
        this.scheduleHeaderBox = scheduleHeaderBox;
    }

    public Label getScheduleItemsHeader() {
        return scheduleItemsHeader;
    }

    public void setScheduleItemsHeader(Label scheduleItemsHeader) {
        this.scheduleItemsHeader = scheduleItemsHeader;
    }

    public Button getDeleteScheduleItem() {
        return deleteScheduleItem;
    }

    public void setDeleteScheduleItem(Button deleteScheduleItem) {
        this.deleteScheduleItem = deleteScheduleItem;
    }

    public TableView getScheduleItemTable() {
        return scheduleItemTable;
    }

    public void setScheduleItemTable(TableView scheduleItemTable) {
        this.scheduleItemTable = scheduleItemTable;
    }

    public TableColumn getTypeColumn() {
        return typeColumn;
    }

    public void setTypeColumn(TableColumn typeColumn) {
        this.typeColumn = typeColumn;
    }

    public TableColumn getDateColumn() {
        return dateColumn;
    }

    public void setDateColumn(TableColumn dateColumn) {
        this.dateColumn = dateColumn;
    }

    public TableColumn getTitleColumn() {
        return titleColumn;
    }

    public void setTitleColumn(TableColumn titleColumn) {
        this.titleColumn = titleColumn;
    }

    public TableColumn getTopicColumn() {
        return topicColumn;
    }

    public void setTopicColumn(TableColumn topicColumn) {
        this.topicColumn = topicColumn;
    }

    public Label getAddEditHeader() {
        return addEditHeader;
    }

    public void setAddEditHeader(Label addEditHeader) {
        this.addEditHeader = addEditHeader;
    }

    public HBox getTypeBox() {
        return typeBox;
    }

    public void setTypeBox(HBox typeBox) {
        this.typeBox = typeBox;
    }

    public Label getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(Label typeLabel) {
        this.typeLabel = typeLabel;
    }

    public ComboBox getTypeCombo() {
        return typeCombo;
    }

    public void setTypeCombo(ComboBox typeCombo) {
        this.typeCombo = typeCombo;
    }

    public HBox getDateBox() {
        return dateBox;
    }

    public void setDateBox(HBox dateBox) {
        this.dateBox = dateBox;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(Label dateLabel) {
        this.dateLabel = dateLabel;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public HBox getTitleBox() {
        return titleBox;
    }

    public void setTitleBox(HBox titleBox) {
        this.titleBox = titleBox;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public void setTitleField(TextField dayTimeField) {
        this.titleField = dayTimeField;
    }

    public HBox getTopicBox() {
        return topicBox;
    }

    public void setTopicBox(HBox topicBox) {
        this.topicBox = topicBox;
    }

    public Label getTopicLabel() {
        return topicLabel;
    }

    public void setTopicLabel(Label topicLabel) {
        this.topicLabel = topicLabel;
    }

    public TextField getTopicField() {
        return topicField;
    }

    public void setTopicField(TextField locationField) {
        this.topicField = locationField;
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

    public HBox getCriteriaBox() {
        return criteriaBox;
    }

    public void setCriteriaBox(HBox criteriaBox) {
        this.criteriaBox = criteriaBox;
    }

    public Label getCriteriaLabel() {
        return criteriaLabel;
    }

    public void setCriteriaLabel(Label criteriaLabel) {
        this.criteriaLabel = criteriaLabel;
    }

    public TextField getCriteriaField() {
        return criteriaField;
    }

    public void setCriteriaField(TextField criteriaField) {
        this.criteriaField = criteriaField;
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

    void reloadWorkspace(AppDataComponent dataComponent) {
    }

}
