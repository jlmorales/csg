/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.file;

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
import static csg.file.TAFiles.JSON_NAME;
import csg.workspace.CSGWorkspace;
import csg.workspace.CourseDetailsPane;
import csg.workspace.RecitationPane;
import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author Jose
 */
public class CSGFiles implements AppFileComponent {

    CSGeneratorApp app;
    //TAFiles taFiles
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";
    //ScheduleFile
    static final String JSON_HOLIDAY_MONTH = "month";
    static final String JSON_HOLIDAY_DAY = "day";
    static final String JSON_HOLIDAY_TYPE = "type";
    static final String JSON_HOLIDAY_DATE = "date";
    static final String JSON_HOLIDAY_TITLE = "title";
    static final String JSON_HOLIDAY_LINK = "link";
    static final String JSON_HOLIDAY_TOPIC = "topic";
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    //recitation strings
    static final String JSON_RECITATION_SECTION = "section";
    static final String JSON_RECITATION_INSTRUCTOR = "instructor";
    static final String JSON_RECITATION_DAY_TIME = "day_time";
    static final String JSON_RECITATION_LOCATION = "location";
    static final String JSON_RECITATION_TA1 = "ta1";
    static final String JSON_RECITATION_TA2 = "ta2";
    static final String JSON_RECITATION = "recitation";
    static final String JSON_RECITATIONS = "recitations";
    //project data
    static final String JSON_STUDENT_FIRST_NAME = "first_name";
    static final String JSON_STUDENT_LAST_NAME = "last_name";
    static final String JSON_STUDENT_ROLE = "role";
    static final String JSON_STUDENT_TEAM = "team";
    static final String JSON_STUDENTS = "students";
    static final String JSON_TEAMS = "teams";
    static final String JSON_TEAM_COLOR = "color";
    static final String JSON_TEAM_TEXT_COLOR = "text_color";
    static final String JSON_TEAM_NAME = "name";
    static final String JSON_TEAM_LINK = "link";
    private String JSON_COURSE = "course";
    private String JSON_COURSE_EXPORT_DIR = "export_dir";
    private String JSON_COURSE_INSTRUCTOR_HOME = "instructor_home";
    private String JSON_COURSE_INSTRUCTOR_NAME = "instructor_name";
    private String JSON_COURSE_NUMBER = "number";
    private String JSON_COURSE_SEMESTER = "semester";
    private String JSON_COURSE_SUBJECT = "subject";
    private String JSON_COURSE_TITLE = "title";
    private String JSON_COURSE_YEAR = "year";
    private String JSON_SITE_TEMPLATE_DIRECTORY = "template_directory";
    private String JSON_SITE_PAGES = "site_pages";
    private String JSON_SITE_SCRIPT = "script";
    private String JSON_SITE_USE = "use";
    private String JSON_SITE_NAVBAR = "navbar";
    private String JSON_SITE_PAGE_FILE_NAME = "file_name";

    public CSGFiles(CSGeneratorApp app) {
        this.app = app;
        //  this.taFiles = new TAFiles(app);
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // taFiles.saveData(data, filePath);
        // GET THE DATA
        CSGData csgData = (CSGData) data;

        TAData dataManager = csgData.getTaData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
         
        //scheduleData
        ScheduleData scheduledataManager = csgData.getScheduleData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder siArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> sis = scheduledataManager.getScheduleItems();
        for (ScheduleItem si : sis) {
            JsonObject siJson = Json.createObjectBuilder()
                    .add(JSON_HOLIDAY_TYPE, si.getType())
                    .add(JSON_HOLIDAY_DATE, si.getDate())
                    .add(JSON_HOLIDAY_TITLE, si.getTitle())
                    .add(JSON_HOLIDAY_TOPIC, si.getTopic())
                    .build();
            siArrayBuilder.add(siJson);
        }
        JsonArray scheduleItemsArray = siArrayBuilder.build();
        //recitation data
        RecitationData recitationDataManager = csgData.getRecitationData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder reArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = recitationDataManager.getRecitations();
        for (Recitation re : recitations) {
            JsonObject siJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION, re.getSection())
                    .add(JSON_RECITATION_INSTRUCTOR, re.getInstructor())
                    .add(JSON_RECITATION_DAY_TIME, re.getDayTime())
                    .add(JSON_RECITATION_LOCATION, re.getLocation())
                    .add(JSON_RECITATION_TA1, re.getTa1())
                    .add(JSON_RECITATION_TA2, re.getTa2())
                    .build();
            reArrayBuilder.add(siJson);
        }
        JsonArray recitationsArray = reArrayBuilder.build();
        //Project Data
        ProjectData projData = csgData.getProjectData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = projData.getStudents();
        for (Student student : students) {
            JsonObject stuJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_FIRST_NAME, student.getFirstName())
                    .add(JSON_STUDENT_LAST_NAME, student.getLastName())
                    .add(JSON_STUDENT_ROLE, student.getRole())
                    .add(JSON_STUDENT_TEAM, student.getTeam())
                    .build();
            studentsArrayBuilder.add(stuJson);
        }
        JsonArray studentsArray = studentsArrayBuilder.build();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = projData.getTeams();
        for (Team team : teams) {
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_COLOR, team.getColor())
                    .add(JSON_TEAM_TEXT_COLOR, team.getTextColor())
                    .add(JSON_TEAM_NAME, team.getName())
                    .add(JSON_TEAM_LINK, team.getLink())
                    .build();
            teamsArrayBuilder.add(teamJson);
        }
        JsonArray teamsArray = teamsArrayBuilder.build();
        //course details data
        CourseDetailsData courseDetailsData = csgData.getCourseDetailsData();
        CourseInfo courseInfo = courseDetailsData.getCourseInfo();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder courseInfoArrayBuilder = Json.createArrayBuilder();

        JsonObject courseInfoJson = Json.createObjectBuilder()
                .add(JSON_COURSE_SUBJECT, courseInfo.getSubject().toString())
                .add(JSON_COURSE_SEMESTER, courseInfo.getSemester())
                .add(JSON_COURSE_YEAR, courseInfo.getYear())
                .add(JSON_COURSE_TITLE, courseInfo.getTitle())
                .add(JSON_COURSE_NUMBER, courseInfo.getNumber())
                .add(JSON_COURSE_INSTRUCTOR_NAME, courseInfo.getInstrName())
                .add(JSON_COURSE_INSTRUCTOR_HOME, courseInfo.getInstrHome())
                .add(JSON_COURSE_EXPORT_DIR, courseInfo.getExportDir())
                .build();
        courseInfoArrayBuilder.add(courseInfoJson);

        JsonArray courseInfoArray = courseInfoArrayBuilder.build();

        SiteTemplate siteTemplate = courseDetailsData.getSiteTemplate();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder siteTemplateArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePages> sitePages = siteTemplate.getSitePages();
        for (SitePages sitePage : sitePages) {
            JsonObject siteTemplateJson = Json.createObjectBuilder()
                    .add(JSON_SITE_PAGE_FILE_NAME, sitePage.getFileName())
                    .add(JSON_SITE_NAVBAR, sitePage.getNavbarTitle())
                    .add(JSON_SITE_SCRIPT, sitePage.getScript())
                    .add(JSON_SITE_USE, sitePage.getUse())
                    .build();
            siteTemplateArrayBuilder.add(siteTemplateJson);
        }
        JsonArray siteTemplateArray = siteTemplateArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_SCHEDULE_ITEMS, scheduleItemsArray)
                .add(JSON_RECITATION, recitationsArray)
                .add(JSON_STUDENTS, studentsArray)
                .add(JSON_TEAMS, teamsArray)
                .add(JSON_COURSE, courseInfoArray)
                .add(JSON_SITE_TEMPLATE_DIRECTORY, siteTemplate.getTemplateDirectory())
                .add(JSON_SITE_PAGES, siteTemplateArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT

    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;
        TAData taData = dataManager.getTaData();

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        taData.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            taData.addTA(name, email);
        }
        //Recitations
        RecitationData recitationData = dataManager.getRecitationData();
        ObservableList<Recitation> recitations = recitationData.getRecitations();
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATION);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_RECITATION_SECTION);
            String instructor = jsonRecitation.getString(JSON_RECITATION_INSTRUCTOR);
            String dayTime = jsonRecitation.getString(JSON_RECITATION_DAY_TIME);
            String location = jsonRecitation.getString(JSON_RECITATION_LOCATION);
            String ta1 = jsonRecitation.getString(JSON_RECITATION_TA1);
            String ta2 = jsonRecitation.getString(JSON_RECITATION_TA2);
            recitations.add(new Recitation(section, instructor, dayTime, location, ta1, ta2));
        }
        //Schedule Items
        ScheduleData scheduleData = dataManager.getScheduleData();
        ObservableList<ScheduleItem> scheduleItems = scheduleData.getScheduleItems();
        JsonArray jsonScheduleItemsArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleItemsArray.size(); i++) {
            JsonObject jsonScheduleItem = jsonScheduleItemsArray.getJsonObject(i);
            String type = jsonScheduleItem.getString(JSON_HOLIDAY_TYPE);
            String date = jsonScheduleItem.getString(JSON_HOLIDAY_DATE);
            String title = jsonScheduleItem.getString(JSON_HOLIDAY_TITLE);
            String topic = jsonScheduleItem.getString(JSON_HOLIDAY_TOPIC);
            scheduleItems.add(new ScheduleItem(type, date, title, topic, "", ""));
        }
        //ProjectData
        ProjectData projectData = dataManager.getProjectData();
        ObservableList<Student> students = projectData.getStudents();
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentsArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_STUDENT_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_STUDENT_LAST_NAME);
            String role = jsonStudent.getString(JSON_STUDENT_ROLE);
            String team = jsonStudent.getString(JSON_STUDENT_TEAM);
            students.add(new Student(firstName, lastName, team,role));
        }
        ObservableList<Team> teams = projectData.getTeams();
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamsArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamsArray.getJsonObject(i);
            String color = jsonTeam.getString(JSON_TEAM_COLOR);
            String textColor = jsonTeam.getString(JSON_TEAM_TEXT_COLOR);
            String name = jsonTeam.getString(JSON_TEAM_NAME);
            String link = jsonTeam.getString(JSON_TEAM_LINK);
            teams.add(new Team(name, color, textColor, link));
        }
        //courseDetailsData
        CourseDetailsData courseDetailsData = dataManager.getCourseDetailsData();
        CourseInfo courseInfo = courseDetailsData.getCourseInfo();
        JsonArray jsonCourseInfoArray = json.getJsonArray(JSON_COURSE);
        for (int i = 0; i < jsonCourseInfoArray.size(); i++) {
            JsonObject jsonCourseInfo = jsonCourseInfoArray.getJsonObject(i);
            courseInfo.setExportDir(jsonCourseInfo.getString(JSON_COURSE_EXPORT_DIR));
            courseInfo.setInstrHome(jsonCourseInfo.getString(JSON_COURSE_INSTRUCTOR_HOME));
            courseInfo.setInstrName(jsonCourseInfo.getString(JSON_COURSE_INSTRUCTOR_NAME));
            courseInfo.setNumber(jsonCourseInfo.getString(JSON_COURSE_NUMBER));
            courseInfo.setSemester(jsonCourseInfo.getString(JSON_COURSE_SEMESTER));
            courseInfo.setSubject(jsonCourseInfo.getString(JSON_COURSE_SUBJECT));
            courseInfo.setTitle(jsonCourseInfo.getString(JSON_COURSE_TITLE));
            courseInfo.setYear(jsonCourseInfo.getString(JSON_COURSE_YEAR));
        }
        SiteTemplate siteTemplate = courseDetailsData.getSiteTemplate();
        String templateDirectory = json.getString(JSON_SITE_TEMPLATE_DIRECTORY);
        siteTemplate.setTemplateDirectory(templateDirectory);
        //
        ObservableList<SitePages> sitePages = siteTemplate.getSitePages();
        sitePages.clear();
        JsonArray jsonSitePagesArray = json.getJsonArray(JSON_SITE_PAGES);
        for (int i = 0; i < jsonSitePagesArray.size(); i++) {
            JsonObject jsonSitePage = jsonSitePagesArray.getJsonObject(i);
            Boolean use = jsonSitePage.getBoolean(JSON_SITE_USE);
            String script = jsonSitePage.getString(JSON_SITE_SCRIPT);
            String navbar = jsonSitePage.getString(JSON_SITE_NAVBAR);
            String fileName = jsonSitePage.getString(JSON_SITE_PAGE_FILE_NAME);
            sitePages.add(new SitePages(use, navbar, fileName, script));
        }
        //cour

        // AND THEN ALL THE OFFICE HOURS
        
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            taData.addOfficeHoursReservation(day, time, name);
        }
         
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CourseDetailsPane cdp = workspace.getCourseDetailsPane();
        cdp.reloadWorkspace(data);
        RecitationPane re=workspace.getRecitationPane();
        re.reloadWorkspace(data);
        workspace.getController().updateTeamList();
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
        String officeHoursGridDataDirectory = filePath + "/public_html/" + "js/OfficeHoursGridData.json";
        CSGData csgData = (CSGData) data;
        TAData dataManager = csgData.getTaData();
        //TA DATA
        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
         
        JsonObject taDataJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray).build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(taDataJSO);
        jsonWriter.close();

        // INIT THE WRITER
        //String taDataJSOpath = fileToExport + "/js/OfficeHoursGridData.json";
        OutputStream os = new FileOutputStream(officeHoursGridDataDirectory);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(taDataJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(officeHoursGridDataDirectory);
        pw.write(prettyPrinted);
        pw.close();
        
        
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void testLoadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;
        TAData taData = dataManager.getTaData();

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        //taData.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        //app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            taData.addTA(name, email);
        }
        //Recitations
        RecitationData recitationData = dataManager.getRecitationData();
        ObservableList<Recitation> recitations = recitationData.getRecitations();
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATION);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_RECITATION_SECTION);
            String instructor = jsonRecitation.getString(JSON_RECITATION_INSTRUCTOR);
            String dayTime = jsonRecitation.getString(JSON_RECITATION_DAY_TIME);
            String location = jsonRecitation.getString(JSON_RECITATION_LOCATION);
            String ta1 = jsonRecitation.getString(JSON_RECITATION_TA1);
            String ta2 = jsonRecitation.getString(JSON_RECITATION_TA2);
            recitations.add(new Recitation(section, instructor, dayTime, location, ta1, ta2));
        }
        //Schedule Items
        ScheduleData scheduleData = dataManager.getScheduleData();
        ObservableList<ScheduleItem> scheduleItems = scheduleData.getScheduleItems();
        JsonArray jsonScheduleItemsArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleItemsArray.size(); i++) {
            JsonObject jsonScheduleItem = jsonScheduleItemsArray.getJsonObject(i);
            String type = jsonScheduleItem.getString(JSON_HOLIDAY_TYPE);
            String date = jsonScheduleItem.getString(JSON_HOLIDAY_DATE);
            String title = jsonScheduleItem.getString(JSON_HOLIDAY_TITLE);
            String topic = jsonScheduleItem.getString(JSON_HOLIDAY_TOPIC);
            scheduleItems.add(new ScheduleItem(type, date, title, topic, "", ""));
        }
        //ProjectData
        ProjectData projectData = dataManager.getProjectData();
        ObservableList<Student> students = projectData.getStudents();
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentsArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_STUDENT_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_STUDENT_LAST_NAME);
            String role = jsonStudent.getString(JSON_STUDENT_ROLE);
            String team = jsonStudent.getString(JSON_STUDENT_TEAM);
            students.add(new Student(firstName, lastName, role, team));
        }
        ObservableList<Team> teams = projectData.getTeams();
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamsArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamsArray.getJsonObject(i);
            String color = jsonTeam.getString(JSON_TEAM_COLOR);
            String textColor = jsonTeam.getString(JSON_TEAM_TEXT_COLOR);
            String name = jsonTeam.getString(JSON_TEAM_NAME);
            String link = jsonTeam.getString(JSON_TEAM_LINK);
            teams.add(new Team(color, textColor, name, link));
        }
        //courseDetailsData
        CourseDetailsData courseDetailsData = dataManager.getCourseDetailsData();
        CourseInfo courseInfo = courseDetailsData.getCourseInfo();
        JsonArray jsonCourseInfoArray = json.getJsonArray(JSON_COURSE);
        for (int i = 0; i < jsonCourseInfoArray.size(); i++) {
            JsonObject jsonCourseInfo = jsonCourseInfoArray.getJsonObject(i);
            courseInfo.setExportDir(jsonCourseInfo.getString(JSON_COURSE_EXPORT_DIR));
            courseInfo.setInstrHome(jsonCourseInfo.getString(JSON_COURSE_INSTRUCTOR_HOME));
            courseInfo.setInstrName(jsonCourseInfo.getString(JSON_COURSE_INSTRUCTOR_NAME));
            courseInfo.setNumber(jsonCourseInfo.getString(JSON_COURSE_NUMBER));
            courseInfo.setSemester(jsonCourseInfo.getString(JSON_COURSE_SEMESTER));
            courseInfo.setSubject(jsonCourseInfo.getString(JSON_COURSE_SUBJECT));
            courseInfo.setTitle(jsonCourseInfo.getString(JSON_COURSE_TITLE));
            courseInfo.setYear(jsonCourseInfo.getString(JSON_COURSE_YEAR));
        }
        SiteTemplate siteTemplate = courseDetailsData.getSiteTemplate();
        String templateDirectory = json.getString(JSON_SITE_TEMPLATE_DIRECTORY);
        siteTemplate.setTemplateDirectory(templateDirectory);
        //
        ObservableList<SitePages> sitePages = siteTemplate.getSitePages();
        JsonArray jsonSitePagesArray = json.getJsonArray(JSON_SITE_PAGES);
        for (int i = 0; i < jsonSitePagesArray.size(); i++) {
            JsonObject jsonSitePage = jsonSitePagesArray.getJsonObject(i);
            Boolean use = jsonSitePage.getBoolean(JSON_SITE_USE);
            String script = jsonSitePage.getString(JSON_SITE_SCRIPT);
            String navbar = jsonSitePage.getString(JSON_SITE_NAVBAR);
            String fileName = jsonSitePage.getString(JSON_SITE_PAGE_FILE_NAME);
            sitePages.add(new SitePages(use, navbar, fileName, script));
        }
        //cour

        // AND THEN ALL THE OFFICE HOURS
        /*
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
         */
    }

    public void testSaveData(AppDataComponent data, String filePath) throws IOException {
        // taFiles.saveData(data, filePath);
        // GET THE DATA
        CSGData csgData = (CSGData) data;

        TAData dataManager = csgData.getTaData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        /*JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
         */
        //scheduleData
        ScheduleData scheduledataManager = csgData.getScheduleData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder siArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItem> sis = scheduledataManager.getScheduleItems();
        for (ScheduleItem si : sis) {
            JsonObject siJson = Json.createObjectBuilder()
                    .add(JSON_HOLIDAY_TYPE, si.getType())
                    .add(JSON_HOLIDAY_DATE, si.getDate())
                    .add(JSON_HOLIDAY_TITLE, si.getTitle())
                    .add(JSON_HOLIDAY_TOPIC, si.getTopic())
                    .build();
            siArrayBuilder.add(siJson);
        }
        JsonArray scheduleItemsArray = siArrayBuilder.build();
        //recitation data
        RecitationData recitationDataManager = csgData.getRecitationData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder reArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = recitationDataManager.getRecitations();
        for (Recitation re : recitations) {
            JsonObject siJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION, re.getSection())
                    .add(JSON_RECITATION_INSTRUCTOR, re.getInstructor())
                    .add(JSON_RECITATION_DAY_TIME, re.getDayTime())
                    .add(JSON_RECITATION_LOCATION, re.getLocation())
                    .add(JSON_RECITATION_TA1, re.getTa1())
                    .add(JSON_RECITATION_TA2, re.getTa2())
                    .build();
            reArrayBuilder.add(siJson);
        }
        JsonArray recitationsArray = reArrayBuilder.build();
        //Project Data
        ProjectData projData = csgData.getProjectData();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = projData.getStudents();
        for (Student student : students) {
            JsonObject stuJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_FIRST_NAME, student.getFirstName())
                    .add(JSON_STUDENT_LAST_NAME, student.getLastName())
                    .add(JSON_STUDENT_ROLE, student.getRole())
                    .add(JSON_STUDENT_TEAM, student.getTeam())
                    .build();
            studentsArrayBuilder.add(stuJson);
        }
        JsonArray studentsArray = studentsArrayBuilder.build();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = projData.getTeams();
        for (Team team : teams) {
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_NAME, team.getName())
                    .add(JSON_TEAM_COLOR, team.getColor())
                    .add(JSON_TEAM_TEXT_COLOR, team.getTextColor())
                    .add(JSON_TEAM_LINK, team.getLink())
                    .build();
            teamsArrayBuilder.add(teamJson);
        }
        JsonArray teamsArray = teamsArrayBuilder.build();
        //course details data
        CourseDetailsData courseDetailsData = csgData.getCourseDetailsData();
        CourseInfo courseInfo = courseDetailsData.getCourseInfo();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder courseInfoArrayBuilder = Json.createArrayBuilder();

        JsonObject courseInfoJson = Json.createObjectBuilder()
                .add(JSON_COURSE_SUBJECT, courseInfo.getSubject().toString())
                .add(JSON_COURSE_SEMESTER, courseInfo.getSemester())
                .add(JSON_COURSE_YEAR, courseInfo.getYear())
                .add(JSON_COURSE_TITLE, courseInfo.getTitle())
                .add(JSON_COURSE_NUMBER, courseInfo.getNumber())
                .add(JSON_COURSE_INSTRUCTOR_NAME, courseInfo.getInstrName())
                .add(JSON_COURSE_INSTRUCTOR_HOME, courseInfo.getInstrHome())
                .add(JSON_COURSE_EXPORT_DIR, courseInfo.getExportDir())
                .build();
        courseInfoArrayBuilder.add(courseInfoJson);

        JsonArray courseInfoArray = courseInfoArrayBuilder.build();

        SiteTemplate siteTemplate = courseDetailsData.getSiteTemplate();

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder siteTemplateArrayBuilder = Json.createArrayBuilder();
        ObservableList<SitePages> sitePages = siteTemplate.getSitePages();
        for (SitePages sitePage : sitePages) {
            JsonObject siteTemplateJson = Json.createObjectBuilder()
                    .add(JSON_SITE_PAGE_FILE_NAME, sitePage.getFileName())
                    .add(JSON_SITE_NAVBAR, sitePage.getNavbarTitle())
                    .add(JSON_SITE_SCRIPT, sitePage.getScript())
                    .add(JSON_SITE_USE, sitePage.getUse())
                    .build();
            siteTemplateArrayBuilder.add(siteTemplateJson);
        }
        JsonArray siteTemplateArray = siteTemplateArrayBuilder.build();
        //Page Style Saving
        PageStyle pageStyle = courseDetailsData.getPageStyle();
        
        //
        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_SCHEDULE_ITEMS, scheduleItemsArray)
                .add(JSON_RECITATION, recitationsArray)
                .add(JSON_STUDENTS, studentsArray)
                .add(JSON_TEAMS, teamsArray)
                .add(JSON_COURSE, courseInfoArray)
                .add(JSON_SITE_TEMPLATE_DIRECTORY, siteTemplate.getTemplateDirectory())
                .add(JSON_SITE_PAGES, siteTemplateArray)
                //.add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }

}
