/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGeneratorApp;
import csg.workspace.CSGWorkspace;
import csg.workspace.RecitationPane;
import csg.workspace.SchedulePane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose
 */
public class ScheduleData {

    private ObservableList<ScheduleItem> scheduleItems;
    private CalendarBoundaries calendarBoudnaries;
    CSGeneratorApp app;

    ScheduleData(CSGeneratorApp app) {
        this.app = app;
        calendarBoudnaries = new CalendarBoundaries();
        scheduleItems = FXCollections.observableArrayList();

    }

    public void addScheduleItem(String type, String date, String title, String topic, String link, String criteria) {
        ScheduleItem si = new ScheduleItem(type, date, title, topic, link, criteria);

        // ADD THE TA
        if (!containsScheduleItems(type, date, title, topic, link, criteria)) {
            scheduleItems.add(si);
        }
    }

    public boolean containsScheduleItems(String type, String date, String title, String topic, String link, String criteria) {
        for (ScheduleItem si : scheduleItems) {
            if (si.getTitle().equals(title)) {
                return true;
            }
            
        }
        return false;
    }

    public void changeCalendarBoundaries() {

    }

    public ObservableList<ScheduleItem> getScheduleItems() {
        return scheduleItems;
    }

    public void setScheduleItems(ObservableList<ScheduleItem> scheduleItems) {
        this.scheduleItems = scheduleItems;
    }

    public CalendarBoundaries getCalendarBoudnaries() {
        return calendarBoudnaries;
    }

    public void setCalendarBoudnaries(CalendarBoundaries calendarBoudnaries) {
        this.calendarBoudnaries = calendarBoudnaries;
    }

    public void removeScheduleItem(String criteria, String date, String link, String title, String topic, String type) {
        for (ScheduleItem re : scheduleItems) {
            if (title.equals(re.getTitle())) {
                scheduleItems.remove(re);
                return;
            }
        }
    }

    public void editReciation(String criteria, String date, String link, String title, String topic, String type) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        SchedulePane rePane = workspace.getSchedulePane();

        ScheduleItem re = (ScheduleItem) rePane.getScheduleItemTable().getSelectionModel().getSelectedItem();
        re.setCriteria(criteria);
        re.setDate(date);
        re.setLink(link);
        re.setTitle(title);
        re.setTopic(topic);
        re.setType(type);
        // AND BE SURE TO EDIT ALL THE TA'S OFFICE HOURS

        //Collections.sort(teachingAssistants);
        rePane.getScheduleItemTable().refresh();
    }

    

    void resetData() {
        this.scheduleItems.clear();
    }

}
