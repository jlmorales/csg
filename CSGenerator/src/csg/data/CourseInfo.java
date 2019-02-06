/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jose
 */
public class CourseInfo {

    private StringProperty subject;
    private StringProperty semester;
    private StringProperty number;
    private StringProperty year;
    private StringProperty title;
    private StringProperty instrName;
    private StringProperty instrHome;
    private StringProperty exportDir;

    public CourseInfo(String subject, String semester, String number, String year, String title, String instrName, String instrHome, String exportDir) {
        this.subject = new SimpleStringProperty(subject);
        this.semester = new SimpleStringProperty(semester);
        this.number = new SimpleStringProperty(number);
        this.year = new SimpleStringProperty(year);
        this.title = new SimpleStringProperty(title);
        this.instrName = new SimpleStringProperty(instrName);
        this.instrHome = new SimpleStringProperty(instrHome);
        this.exportDir = new SimpleStringProperty(exportDir);
    }

    public CourseInfo() {
        this.subject = new SimpleStringProperty("");
        this.semester = new SimpleStringProperty("");
        this.number = new SimpleStringProperty("");
        this.year = new SimpleStringProperty("");
        this.title = new SimpleStringProperty("");
        this.instrName = new SimpleStringProperty("");
        this.instrHome = new SimpleStringProperty("");
        this.exportDir = new SimpleStringProperty("");
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getSemester() {
        return semester.get();
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getYear() {
        return year.get();
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getInstrName() {
        return instrName.get();
    }

    public void setInstrName(String instrName) {
        this.instrName.set(instrName);
    }

    public String getInstrHome() {
        return instrHome.get();
    }

    public void setInstrHome(String instrHome) {
        this.instrHome.set(instrHome);
    }

    public String getExportDir() {
        return exportDir.get();
    }

    public void setExportDir(String exportDir) {
        this.exportDir.set(exportDir);
    }

}
