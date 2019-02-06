/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGeneratorApp;
import csg.workspace.CSGWorkspace;
import csg.workspace.RecitationPane;
import csg.workspace.TAPane;
import java.util.Collections;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

/**
 *
 * @author Jose
 */
public class RecitationData {

    private ObservableList<Recitation> recitations;
    private CSGeneratorApp app;

    public RecitationData(CSGeneratorApp app) {
        this.recitations = FXCollections.observableArrayList();
        this.app = app;
    }

    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }

    public void setRecitations(ObservableList<Recitation> recitations) {
        this.recitations = recitations;
    }

    public void addRecitation(String section, String instructor, String dayTime, String location, String ta1, String ta2) {
        Recitation recitation = new Recitation(section, instructor, dayTime, location, ta1, ta2);

        // ADD THE TA
        if (!containsRecitation(recitation)) {
            recitations.add(recitation);
        }

        // SORT THE TAS
        //Collections.sort(recitations);
    }

    public boolean containsRecitation(Recitation recitation) {
        for (Recitation re : recitations) {
            if (re.getDayTime().equals(recitation.getDayTime())) {
                return true;
            }

        }
        return false;
    }

    public void removeRecitation(String section, String instructor, String dayTime, String location, String ta1, String ta2) {
        for (Recitation re : recitations) {
            if (section.equals(re.getSection())){
                recitations.remove(re);
                return;
            }
        }
    }

    public void editReciation(String section, String instructor, String dayTime, String location, String ta1, String ta2) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationPane rePane = workspace.getRecitationPane();

        Recitation re = (Recitation) rePane.getRecitationTable().getSelectionModel().getSelectedItem();
        re.setDayTime(dayTime);
        re.setInstructor(instructor);
        re.setLocation(location);
        re.setSection(section);
        re.setTa1(ta1);
        re.setTa2(ta2);
        // AND BE SURE TO EDIT ALL THE TA'S OFFICE HOURS

        //Collections.sort(teachingAssistants);
        rePane.getRecitationTable().refresh();
    }

    

    public void removeTA(String dayTime, String instructor, String location, String section, String ta1, String ta2) {
        for (Recitation re : recitations) {
            if (dayTime.equals(re.getDayTime())
                    && instructor.equals(re.getInstructor())
                    && location.equals(re.getLocation())
                    && section.equals(re.getSection())
                    && ta1.equals(re.getTa1())
                    && ta2.equals(re.getTa2())) {
                recitations.remove(re);
                return;
            }
        }
    }

    void resetData() {
        this.recitations.clear();
    }

}
