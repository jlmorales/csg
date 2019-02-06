/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGeneratorApp;
import csg.CSGeneratorProp;
import csg.data.CSGData;
import csg.data.CourseDetailsData;
import csg.data.CourseInfo;
import csg.data.PageStyle;
import csg.data.SitePages;
import csg.data.SiteTemplate;
import djf.components.AppDataComponent;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jose
 */
public class CourseDetailsPane extends ScrollPane {
    CSGController controller;

    private VBox entirePane;
    //
    private VBox courseInfoPane;
    //
    private HBox courseInfoHeaderBox;
    private Label courseInfoHeader;
    //
    private HBox subjectBox;
    private Label subjectLabel;
    private TextField subjectField;
    //
    private HBox numberBox;
    private Label numberLabel;
    private TextField numberField;
    //
    private HBox semesterBox;
    private Label semesterLabel;
    private TextField semesterField;
    //
    private HBox yearBox;
    private Label yearLabel;
    private TextField yearField;
    //
    private HBox titleBox;
    private Label titleLabel;
    private TextField titleField;
    //
    private HBox instrNameBox;
    private Label instrNameLabel;
    private TextField instrNameField;
    //
    private HBox instrHomeBox;
    private Label instrHomeLabel;
    private TextField instrHomeField;
    private Button commitBtn;
    //
    private HBox exportBox;
    private Label exportLabel;
    private Label exportDirectory;
    private Button changeDirButton;
    //Site Template Pane
    private VBox siteTemplatePane;
    private Label siteTemplateLabel;
    private Label siteTemplateDescription;
    private Label siteTemplateDir;
    private Button selectTemplateDirButton;
    private Label sitePagesHeader;
    private TableView<SitePages> sitePagesTable;
    private TableColumn<SitePages, Boolean> useColumn;
    private TableColumn<SitePages, String> navbarTitleColumn;
    private TableColumn<SitePages, String> fileNameColumn;
    private TableColumn<SitePages, String> scriptColumn;
    private ObservableList<SitePages> tableData = FXCollections.observableArrayList();
    //Page Style Pane
    private VBox pageStylePane;
    //
    private Label pageStyleHeader;
    private HBox bannerSchoolImageBox;
    private Label bannerSchoolImageLabel;
    private ImageView bannerImage;
    private Button changeBannerButton;
    //
    private HBox leftFooterBox;
    private Label leftFooterLabel;
    private ImageView leftFooterImage;
    private Button changeLeftFooter;
    //
    private HBox rightFooterBox;
    private Label rightFooterLabel;
    private ImageView rightFooterImage;
    private Button changeRightFooter;
    //
    private HBox styleSheetBox;
    private Label stylesheetLabel;
    private ComboBox stylesheetCombo;

    private HBox semesterYearBox;

    public CourseDetailsPane(CSGeneratorApp app) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData dataManager=(CSGData) app.getDataComponent();
        CourseDetailsData courseDetailsData=dataManager.getCourseDetailsData();
        CourseInfo courseInfo=courseDetailsData.getCourseInfo();
        //
        courseInfoPane = new VBox();

        //
        courseInfoHeaderBox = new HBox();
        courseInfoHeader = new Label(props.getProperty(CSGeneratorProp.COURSE_INFO_HEADER_TEXT));
        courseInfoHeaderBox.getChildren().add(courseInfoHeader);
        courseInfoPane.getChildren().add(courseInfoHeaderBox);
        //
        HBox subjectNumberBox = new HBox();
        subjectBox = new HBox();
        subjectBox.setSpacing(20);
        subjectLabel = new Label(props.getProperty(CSGeneratorProp.SUBJECT_TEXT));
        subjectField = new TextField(courseInfo.getSubject().toString());
        subjectBox.getChildren().addAll(subjectLabel, subjectField);
        //courseInfoPane.getChildren().add(subjectBox);
        //
        numberBox = new HBox();
        numberBox.setSpacing(20);
        numberLabel = new Label(props.getProperty(CSGeneratorProp.NUMBER_TEXT));
        numberField = new TextField();
        numberBox.getChildren().addAll(numberLabel, numberField);
        subjectNumberBox.getChildren().addAll(subjectBox, numberBox);
        courseInfoPane.getChildren().add(subjectNumberBox);
        //courseInfoPane.getChildren().add(numberBox);
        //
        semesterYearBox = new HBox();
        semesterBox = new HBox();
        semesterBox.setSpacing(20);
        semesterLabel = new Label(props.getProperty(CSGeneratorProp.SEMESTER_TEXT));
        semesterField = new TextField();
        semesterBox.getChildren().addAll(semesterLabel, semesterField);
        courseInfoPane.getChildren().add(semesterBox);
        //
        yearBox = new HBox();
        yearBox.setSpacing(20);
        yearLabel = new Label(props.getProperty(CSGeneratorProp.YEAR_TEXT));
        yearField = new TextField();
        yearBox.getChildren().addAll(yearLabel, yearField);
        semesterYearBox.getChildren().addAll(semesterBox, yearBox);
        courseInfoPane.getChildren().add(semesterYearBox);
        //courseInfoPane.getChildren().add(yearBox);
        //
        titleBox = new HBox();
        titleLabel = new Label(props.getProperty(CSGeneratorProp.TITLE_TEXT)+":");
        titleField = new TextField();
        titleField.setPrefWidth(500);
        titleBox.getChildren().addAll(titleLabel, titleField);
        courseInfoPane.getChildren().add(titleBox);
        //
        instrNameBox = new HBox();
        instrNameLabel = new Label(props.getProperty(CSGeneratorProp.INSTRUCTOR_NAME_TEXT));
        instrNameField = new TextField();
        instrNameField.setPrefWidth(500);
        instrNameBox.getChildren().addAll(instrNameLabel, instrNameField);
        courseInfoPane.getChildren().add(instrNameBox);
        //
        instrHomeBox = new HBox();
        instrHomeLabel = new Label(props.getProperty(CSGeneratorProp.INSTRUCTOR_HOME_TEXT));
        instrHomeField = new TextField();
        instrHomeField.setPrefWidth(500);
        commitBtn= new Button("Commit");
        instrHomeBox.getChildren().addAll(instrHomeLabel, instrHomeField,commitBtn);
        courseInfoPane.getChildren().add(instrHomeBox);
        //
        exportBox = new HBox();
        exportLabel = new Label(props.getProperty(CSGeneratorProp.EXPORT_DIR_TEXT));
        exportDirectory = new Label();
        changeDirButton = new Button(props.getProperty(CSGeneratorProp.CHANGE_BUTTON_TEXT));
        exportBox.getChildren().addAll(exportLabel, exportDirectory, changeDirButton);
        courseInfoPane.getChildren().add(exportBox);
        //
        //Site Template Pane
        siteTemplatePane = new VBox();
        siteTemplateLabel = new Label(props.getProperty(CSGeneratorProp.SITE_TEMPLATE_TEXT));
        siteTemplateDescription = new Label(props.getProperty(CSGeneratorProp.SITE_TEMPLATE_DISC_TEXT));
        siteTemplateDir = new Label();
        selectTemplateDirButton = new Button(props.getProperty(CSGeneratorProp.SELECT_TEMPLATE_DIR_BUTTON_TEXT));
        sitePagesHeader = new Label(props.getProperty(CSGeneratorProp.SITE_PAGES_TEXT));
        // MAKE THE TABLE AND SETUP THE DATA MODEL

        sitePagesTable = new TableView();
        SiteTemplate data = courseDetailsData.getSiteTemplate();
        ObservableList<SitePages> tableData = data.getSitePages();
        sitePagesTable.setItems(tableData);
        sitePagesTable.setEditable(true);
        sitePagesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        sitePagesTable.setItems(tableData);
        useColumn = new TableColumn(props.getProperty(CSGeneratorProp.USE_COLUMN_TEXT));
        navbarTitleColumn = new TableColumn(props.getProperty(CSGeneratorProp.NAVBAR_COLUMN_TEXT));
        fileNameColumn = new TableColumn(props.getProperty(CSGeneratorProp.FILE_COLUMN_TEXT));
        scriptColumn = new TableColumn(props.getProperty(CSGeneratorProp.SCRIPT_COLUMN_TEXT));
        useColumn.setCellValueFactory(new PropertyValueFactory<SitePages, Boolean>("use"));
        useColumn.setCellFactory(column -> new CheckBoxTableCell());
        navbarTitleColumn.setCellValueFactory(new PropertyValueFactory<SitePages, String>("navbarTitle"));
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<SitePages, String>("fileName"));
        scriptColumn.setCellValueFactory(new PropertyValueFactory<SitePages, String>("script"));
        sitePagesTable.getColumns().addAll(useColumn, navbarTitleColumn, fileNameColumn, scriptColumn);

        siteTemplatePane.getChildren().addAll(siteTemplateLabel,
                siteTemplateDescription,
                siteTemplateDir,
                selectTemplateDirButton,
                sitePagesHeader,
                sitePagesTable);

        //Page Style Pane
        PageStyle pageStyle=courseDetailsData.getPageStyle();
        pageStylePane = new VBox();

        pageStyleHeader = new Label(props.getProperty(CSGeneratorProp.PAGE_STYLE_TEXT));
        //
        bannerSchoolImageBox = new HBox();
        bannerSchoolImageLabel = new Label(props.getProperty(CSGeneratorProp.BANNER_SCHOOL_IMAGE_TEXT));
        bannerImage = new ImageView();
        
        //bannerImage.setImage(pageStyle.getBannerSchoolImage());
        changeBannerButton = new Button(props.getProperty(CSGeneratorProp.CHANGE_BUTTON_TEXT));
        bannerSchoolImageBox.getChildren().addAll(bannerSchoolImageLabel, bannerImage, changeBannerButton);
        //

        leftFooterBox = new HBox();
        leftFooterLabel = new Label(props.getProperty(CSGeneratorProp.LEFT_FOOTER_TEXT));
        leftFooterImage = new ImageView();
        changeLeftFooter = new Button(props.getProperty(CSGeneratorProp.CHANGE_BUTTON_TEXT));
        leftFooterBox.getChildren().addAll(leftFooterLabel, leftFooterImage, changeLeftFooter);
        //
        rightFooterBox = new HBox();
        rightFooterLabel = new Label(props.getProperty(CSGeneratorProp.RIGHT_FOOTER_TEXT));
        rightFooterImage = new ImageView();
        changeRightFooter = new Button(props.getProperty(CSGeneratorProp.CHANGE_BUTTON_TEXT));
        rightFooterBox.getChildren().addAll(rightFooterLabel, rightFooterImage, changeRightFooter);
        //
        styleSheetBox = new HBox();
        stylesheetLabel = new Label(props.getProperty(CSGeneratorProp.STYLESHEET_TEXT));
        stylesheetCombo = new ComboBox();
        styleSheetBox.getChildren().addAll(stylesheetLabel, stylesheetCombo);
        //
        pageStylePane.getChildren().addAll(pageStyleHeader, bannerSchoolImageBox, leftFooterBox, rightFooterBox, styleSheetBox);
        entirePane = new VBox();
        entirePane.getChildren().add(courseInfoPane);
        entirePane.getChildren().add(siteTemplatePane);
        entirePane.getChildren().add(pageStylePane);
        //entirePane.setTop(courseInfoPane);
        //entirePane.setCenter(siteTemplatePane);
        //entirePane.setBottom(pageStylePane);
        //entirePane.setPrefWidth(1000);
        //this.setContent(entirePane);
        entirePane.setFillWidth(true);
        this.setContent(entirePane);
        this.setFitToWidth(true);
        this.commitBtn.setOnAction(e -> {
            controller.commitCourseInfo();
        });
        this.selectTemplateDirButton.setOnAction(e -> {
            controller.selectTemplateDirectory();
        });
        this.changeDirButton.setOnAction(e -> {
            controller.changeExportDirectory();
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

    public VBox getCourseInfoPane() {
        return courseInfoPane;
    }

    public void setCourseInfoPane(VBox courseInfoPane) {
        this.courseInfoPane = courseInfoPane;
    }

    public HBox getCourseInfoHeaderBox() {
        return courseInfoHeaderBox;
    }

    public void setCourseInfoHeaderBox(HBox courseInfoHeaderBox) {
        this.courseInfoHeaderBox = courseInfoHeaderBox;
    }

    public Label getCourseInfoHeader() {
        return courseInfoHeader;
    }

    public void setCourseInfoHeader(Label courseInfoHeader) {
        this.courseInfoHeader = courseInfoHeader;
    }

    public HBox getSubjectBox() {
        return subjectBox;
    }

    public void setSubjectBox(HBox subjectBox) {
        this.subjectBox = subjectBox;
    }

    public Label getSubjectLabel() {
        return subjectLabel;
    }

    public void setSubjectLabel(Label subjectLabel) {
        this.subjectLabel = subjectLabel;
    }

    public TextField getSubjectField() {
        return subjectField;
    }

    public void setSubjectField(TextField subjectField) {
        this.subjectField = subjectField;
    }

    public HBox getNumberBox() {
        return numberBox;
    }

    public void setNumberBox(HBox numberBox) {
        this.numberBox = numberBox;
    }

    public Label getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(Label numberLabel) {
        this.numberLabel = numberLabel;
    }

    public TextField getNumberField() {
        return numberField;
    }

    public void setNumberField(TextField numberField) {
        this.numberField = numberField;
    }

    public HBox getSemesterBox() {
        return semesterBox;
    }

    public void setSemesterBox(HBox semesterBox) {
        this.semesterBox = semesterBox;
    }

    public Label getSemesterLabel() {
        return semesterLabel;
    }

    public void setSemesterLabel(Label semesterLabel) {
        this.semesterLabel = semesterLabel;
    }

    public TextField getSemesterField() {
        return semesterField;
    }

    public void setSemesterField(TextField semesterField) {
        this.semesterField = semesterField;
    }

    public HBox getYearBox() {
        return yearBox;
    }

    public void setYearBox(HBox yearBox) {
        this.yearBox = yearBox;
    }

    public Label getYearLabel() {
        return yearLabel;
    }

    public void setYearLabel(Label yearLabel) {
        this.yearLabel = yearLabel;
    }

    public TextField getYearField() {
        return yearField;
    }

    public void setYearField(TextField yearField) {
        this.yearField = yearField;
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

    public void setTitleField(TextField titleField) {
        this.titleField = titleField;
    }

    public HBox getInstrNameBox() {
        return instrNameBox;
    }

    public void setInstrNameBox(HBox instrNameBox) {
        this.instrNameBox = instrNameBox;
    }

    public Label getInstrNameLabel() {
        return instrNameLabel;
    }

    public void setInstrNameLabel(Label instrNameLabel) {
        this.instrNameLabel = instrNameLabel;
    }

    public TextField getInstrNameField() {
        return instrNameField;
    }

    public void setInstrNameField(TextField instrNameField) {
        this.instrNameField = instrNameField;
    }

    public HBox getInstrHomeBox() {
        return instrHomeBox;
    }

    public void setInstrHomeBox(HBox instrHomeBox) {
        this.instrHomeBox = instrHomeBox;
    }

    public Label getInstrHomeLabel() {
        return instrHomeLabel;
    }

    public void setInstrHomeLabel(Label instrHomeLabel) {
        this.instrHomeLabel = instrHomeLabel;
    }

    public TextField getInstrHomeField() {
        return instrHomeField;
    }

    public void setInstrHomeField(TextField instrHomeField) {
        this.instrHomeField = instrHomeField;
    }

    public HBox getExportBox() {
        return exportBox;
    }

    public void setExportBox(HBox exportBox) {
        this.exportBox = exportBox;
    }

    public Label getExportLabel() {
        return exportLabel;
    }

    public void setExportLabel(Label exportLabel) {
        this.exportLabel = exportLabel;
    }

    public Label getExportDirectory() {
        return exportDirectory;
    }

    public void setExportDirectory(Label exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    public Button getChangeDirButton() {
        return changeDirButton;
    }

    public void setChangeDirButton(Button changeDirButton) {
        this.changeDirButton = changeDirButton;
    }

    public VBox getSiteTemplatePane() {
        return siteTemplatePane;
    }

    public void setSiteTemplatePane(VBox siteTemplatePane) {
        this.siteTemplatePane = siteTemplatePane;
    }

    public Label getSiteTemplateLabel() {
        return siteTemplateLabel;
    }

    public void setSiteTemplateLabel(Label siteTemplateLabel) {
        this.siteTemplateLabel = siteTemplateLabel;
    }

    public Label getSiteTemplateDescription() {
        return siteTemplateDescription;
    }

    public void setSiteTemplateDescription(Label siteTemplateDescription) {
        this.siteTemplateDescription = siteTemplateDescription;
    }

    public Label getSiteTemplateDir() {
        return siteTemplateDir;
    }

    public void setSiteTemplateDir(Label siteTemplateDir) {
        this.siteTemplateDir = siteTemplateDir;
    }

    public Button getSelectTemplateDirButton() {
        return selectTemplateDirButton;
    }

    public void setSelectTemplateDirButton(Button selectTemplateDirButton) {
        this.selectTemplateDirButton = selectTemplateDirButton;
    }

    public Label getSitePagesHeader() {
        return sitePagesHeader;
    }

    public void setSitePagesHeader(Label sitePagesHeader) {
        this.sitePagesHeader = sitePagesHeader;
    }

    public TableView<SitePages> getSitePagesTable() {
        return sitePagesTable;
    }

    public void setSitePagesTable(TableView<SitePages> sitePagesTable) {
        this.sitePagesTable = sitePagesTable;
    }

    public TableColumn<SitePages, Boolean> getUseColumn() {
        return useColumn;
    }

    public void setUseColumn(TableColumn<SitePages, Boolean> useColumn) {
        this.useColumn = useColumn;
    }

    public TableColumn<SitePages, String> getNavbarTitleColumn() {
        return navbarTitleColumn;
    }

    public void setNavbarTitleColumn(TableColumn<SitePages, String> navbarTitleColumn) {
        this.navbarTitleColumn = navbarTitleColumn;
    }

    public TableColumn<SitePages, String> getFileNameColumn() {
        return fileNameColumn;
    }

    public void setFileNameColumn(TableColumn<SitePages, String> fileNameColumn) {
        this.fileNameColumn = fileNameColumn;
    }

    public TableColumn<SitePages, String> getScriptColumn() {
        return scriptColumn;
    }

    public void setScriptColumn(TableColumn<SitePages, String> scriptColumn) {
        this.scriptColumn = scriptColumn;
    }

    public ObservableList<SitePages> getTableData() {
        return tableData;
    }

    public void setTableData(ObservableList<SitePages> tableData) {
        this.tableData = tableData;
    }

    public VBox getPageStylePane() {
        return pageStylePane;
    }

    public void setPageStylePane(VBox pageStylePane) {
        this.pageStylePane = pageStylePane;
    }

    public Label getPageStyleHeader() {
        return pageStyleHeader;
    }

    public void setPageStyleHeader(Label pageStyleHeader) {
        this.pageStyleHeader = pageStyleHeader;
    }

    public HBox getBannerSchoolImageBox() {
        return bannerSchoolImageBox;
    }

    public void setBannerSchoolImageBox(HBox bannerSchoolImageBox) {
        this.bannerSchoolImageBox = bannerSchoolImageBox;
    }

    public Label getBannerSchoolImageLabel() {
        return bannerSchoolImageLabel;
    }

    public void setBannerSchoolImageLabel(Label bannerSchoolImageLabel) {
        this.bannerSchoolImageLabel = bannerSchoolImageLabel;
    }

    public ImageView getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(ImageView bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Button getChangeBannerButton() {
        return changeBannerButton;
    }

    public void setChangeBannerButton(Button changeBannerButton) {
        this.changeBannerButton = changeBannerButton;
    }

    public HBox getLeftFooterBox() {
        return leftFooterBox;
    }

    public void setLeftFooterBox(HBox leftFooterBox) {
        this.leftFooterBox = leftFooterBox;
    }

    public Label getLeftFooterLabel() {
        return leftFooterLabel;
    }

    public void setLeftFooterLabel(Label leftFooterLabel) {
        this.leftFooterLabel = leftFooterLabel;
    }

    public ImageView getLeftFooterImage() {
        return leftFooterImage;
    }

    public void setLeftFooterImage(ImageView leftFooterImage) {
        this.leftFooterImage = leftFooterImage;
    }

    public Button getChangeLeftFooter() {
        return changeLeftFooter;
    }

    public void setChangeLeftFooter(Button changeLeftFooter) {
        this.changeLeftFooter = changeLeftFooter;
    }

    public HBox getRightFooterBox() {
        return rightFooterBox;
    }

    public void setRightFooterBox(HBox rightFooterBox) {
        this.rightFooterBox = rightFooterBox;
    }

    public Label getRightFooterLabel() {
        return rightFooterLabel;
    }

    public void setRightFooterLabel(Label rightFooterLabel) {
        this.rightFooterLabel = rightFooterLabel;
    }

    public ImageView getRightFooterImage() {
        return rightFooterImage;
    }

    public void setRightFooterImage(ImageView rightFooterImage) {
        this.rightFooterImage = rightFooterImage;
    }

    public Button getChangeRightFooter() {
        return changeRightFooter;
    }

    public void setChangeRightFooter(Button changeRightFooter) {
        this.changeRightFooter = changeRightFooter;
    }

    public HBox getStyleSheetBox() {
        return styleSheetBox;
    }

    public void setStyleSheetBox(HBox styleSheetBox) {
        this.styleSheetBox = styleSheetBox;
    }

    public Label getStylesheetLabel() {
        return stylesheetLabel;
    }

    public void setStylesheetLabel(Label stylesheetLabel) {
        this.stylesheetLabel = stylesheetLabel;
    }

    public ComboBox getStylesheetCombo() {
        return stylesheetCombo;
    }

    public void setStylesheetCombo(ComboBox stylesheetCombo) {
        this.stylesheetCombo = stylesheetCombo;
    }

    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData data= (CSGData) dataComponent;
        CourseDetailsData courseDetailsData=data.getCourseDetailsData();
        CourseInfo courseInfo=courseDetailsData.getCourseInfo();
        numberField.setText(courseInfo.getNumber());
        semesterField.setText(courseInfo.getSemester());
        this.getInstrNameField().setText(courseInfo.getInstrName());
        this.getInstrHomeField().setText(courseInfo.getInstrHome());
        this.getSubjectField().setText(courseInfo.getSubject());
        this.getTitleField().setText(courseInfo.getTitle());
        this.getYearField().setText(courseInfo.getYear());
        this.getExportDirectory().setText(courseInfo.getExportDir());
        SiteTemplate st=courseDetailsData.getSiteTemplate();
        this.getSiteTemplateDir().setText(st.getTemplateDirectory());
        
    }

}
