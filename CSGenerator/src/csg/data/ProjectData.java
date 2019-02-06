/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGeneratorApp;
import csg.workspace.CSGWorkspace;
import csg.workspace.ProjectPane;
import csg.workspace.RecitationPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose
 */
public class ProjectData {

    private ObservableList<Team> teams;
    private ObservableList<Student> students;
    private CSGeneratorApp app;

    ProjectData(CSGeneratorApp app) {
        this.app = app;
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
    }

    public ObservableList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ObservableList<Team> teams) {
        this.teams = teams;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void setStudents(ObservableList<Student> students) {
        this.students = students;
    }

    public CSGeneratorApp getApp() {
        return app;
    }

    public void setApp(CSGeneratorApp app) {
        this.app = app;
    }


    public void removeStudent(String firstName, String lastName, String role, String team) {
        for (Student student : students) {
            if (firstName.equals(student.getFirstName()) &&  lastName.equals(student.getLastName()
            )) {
                students.remove(student);
                return;
            }
        }
    }

    public void addStudent(String firstName, String lastName, String role, String team) {
        Student si = new Student(firstName,lastName,role,team);

        // ADD THE TA
        if (!containsStudent(firstName,lastName,role,team)) {
            students.add(si);
        }
    }

    public boolean containsStudent(String firstName, String lastName, String role, String team) {
        for (Student student : students) {
            if (student.getFirstName().equals(firstName)) {
                return true;
            }
            if (student.getLastName().equals(lastName)) {
                return true;
            }
        }
        return false;
    }

    public void addTeam(String color, String link, String name, String textColor) {
        Team si = new Team(name,color,textColor,link);

        // ADD THE TA
        if (!containsTeam(color,link,name,textColor)) {
            teams.add(si);
        }
    }
    public boolean containsTeam(String color,String link,String name,String textColor) {
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeTeam(String color, String link, String name, String textColor) {
         for (Team team : teams) {
            if (team.getName().equals(name)) {
                teams.remove(team);
                return;
            }
        }
    }

    public void editStudent(String firstName, String lastName, String role, String team) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane rePane = workspace.getProjectPane();

        Student re = (Student) rePane.getStudentsTable().getSelectionModel().getSelectedItem();
        re.setFirstName(firstName);
        re.setLastName(lastName);
        re.setRole(role);
        re.setTeam(team);
        // AND BE SURE TO EDIT ALL THE TA'S OFFICE HOURS

        //Collections.sort(teachingAssistants);
        rePane.getStudentsTable().refresh();
    }

    public void editTeam(String color, String link, String name, String textColor) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectPane rePane = workspace.getProjectPane();

        Team re = (Team) rePane.getTeamTable().getSelectionModel().getSelectedItem();
        re.setColor(color);
        re.setLink(link);
        re.setName(name);
        re.setTextColor(textColor);
        // AND BE SURE TO EDIT ALL THE TA'S OFFICE HOURS

        //Collections.sort(teachingAssistants);
        rePane.getTeamTable().refresh();
    }

    void resetData() {
        this.students.clear();
        this.teams.clear();
    }

}
