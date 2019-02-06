/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.CourseDetailsData;
import csg.data.CourseInfo;
import csg.data.PageStyle;
import csg.data.ProjectData;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.data.SitePages;
import csg.data.SiteTemplate;
import csg.data.Student;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.file.CSGFiles;
import csg.style.CSGStyle;
import csg.workspace.CSGWorkspace;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static djf.settings.AppStartupConstants.APP_PROPERTIES_FILE_NAME;
import static djf.settings.AppStartupConstants.PATH_DATA;
import static djf.settings.AppStartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import djf.ui.AppMessageDialogSingleton;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import properties_manager.InvalidXMLFileFormatException;
import properties_manager.PropertiesManager;

/**
 *
 * @author Jose
 */
public class TestSave {

    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method
     * inherited from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
        testSave();
    }

    public static void testSave() {
        loadProperties(APP_PROPERTIES_FILE_NAME);
        String filePath = "./work/SiteSaveTest.json";
        CSGData dataManager = new CSGData(null);
        CSGFiles fileManager = new CSGFiles(null);
        //CSGeneratorApp app= new CSGeneratorApp();  
        //app.buildAppComponentsHook();

        //CSGData dataManager = (CSGData) app.getDataComponent();
        //CSGFiles fileManager= (CSGFiles) app.getFileComponent();
        TAData taData = dataManager.getTaData();
        //Add TAs to Table
        taData.addTA("a", "a@gmail.com");
        taData.addTA("b", "b@gmail.com");
        taData.addTA("c", "c@gmail.com");
        taData.addTA("d", "d@gmail.com");
        //get ScheduleData
        ScheduleData scheduleData = dataManager.getScheduleData();
        ObservableList<ScheduleItem> scheduleItems = scheduleData.getScheduleItems();
        scheduleItems.add(new ScheduleItem("Holiday", "2/9/2017", "SnowDay", "", "", ""));
        scheduleItems.add(new ScheduleItem("Lecture", "2/14/2017", "Lecture 3", "Event Programming", "", ""));
        //get recitationData
        RecitationData recitationData = dataManager.getRecitationData();
        ObservableList<Recitation> recitations = recitationData.getRecitations();
        recitations.add(new Recitation("R05", "Banerjee", "Tues 5:30pm-6:23pm", "old CS 2114", "Joe Schmo", "Jane Doe"));
        recitations.add(new Recitation("R01", "McKenna", "Mon 4:30pm-5:23pm", "old CS 2114", "Joe Schmo", "Jane Doe"));
        //get project data
        ProjectData projectData = dataManager.getProjectData();
        ObservableList<Student> students = projectData.getStudents();
        students.add(new Student("Jane", "Doe", "Atomic Comics", "Lead Programmer"));
        ObservableList<Team> teams = projectData.getTeams();
        teams.add(new Team("Atomic Comic", "552211", "ffffff", "http://atomicomic.com"));
        // get course data
        CourseDetailsData courseDetailsData = dataManager.getCourseDetailsData();
        CourseInfo courseInfo = courseDetailsData.getCourseInfo();
        courseInfo.setSubject("CSE");
        courseInfo.setSemester("Fall");
        courseInfo.setNumber("219");
        courseInfo.setYear("2017");
        courseInfo.setTitle("Computer Science III");
        courseInfo.setInstrName("Richard Mckenna");
        courseInfo.setInstrHome("http://www.cs.stonybrook.edu/richard");
        courseInfo.setExportDir("./Courses/CSE219/Summer2017/public");
        //
        SiteTemplate siteTemplate = courseDetailsData.getSiteTemplate();
        ObservableList<SitePages> sitePages = siteTemplate.getSitePages();
        sitePages.add(new SitePages(true, "Home", "index.html", "HomeBuilder.js"));
        sitePages.add(new SitePages(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        sitePages.add(new SitePages(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
        sitePages.add(new SitePages(true, "HWs", "hws.html", "HWsBuilder.js"));
        sitePages.add(new SitePages(false, "Projects", "projects.html", "ProjectsBuilder.js"));
        siteTemplate.setTemplateDirectory("./templates/CSE219");
        
        PageStyle pageStyle= courseDetailsData.getPageStyle();
        
        pageStyle.setStyleSheet("sea_wolf.css");
        
        
        

        try {
            fileManager.testSaveData(dataManager, filePath);
        } catch (IOException ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static CSGData testLoad() {
        loadProperties(APP_PROPERTIES_FILE_NAME);
        String filePath = "./work/SiteSaveTest.json";
        CSGData dataManager = new CSGData(null);
        CSGFiles fileManager = new CSGFiles(null);
        try {
            fileManager.testLoadData(dataManager, filePath);
            //System.out.println("Done");
            return dataManager;
        } catch (IOException ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static boolean loadProperties(String propertiesFileName) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            // LOAD THE SETTINGS FOR STARTING THE APP
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
        } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
            return false;
        }
    }

}
