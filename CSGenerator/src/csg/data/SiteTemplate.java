/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose
 */
public class SiteTemplate {

    private StringProperty templateDirectory;
    private ObservableList<SitePages> sitePages;
    
    public SiteTemplate() {
        templateDirectory = new SimpleStringProperty();
        sitePages = FXCollections.observableArrayList();
        sitePages.add(new SitePages(true, "Home", "index.html", "HomeBuilder.js"));
        sitePages.add(new SitePages(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        sitePages.add(new SitePages(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
        sitePages.add(new SitePages(true, "HWs", "hws.html", "HWsBuilder.js"));
        sitePages.add(new SitePages(false, "Projects", "projects.html", "ProjectsBuilder.js"));
    }

    public String getTemplateDirectory() {
        return templateDirectory.get();
    }

    

    public void setTemplateDirectory(String templateDirectory) {
        this.templateDirectory.set(templateDirectory);
    }

    public ObservableList<SitePages> getSitePages() {
        return sitePages;
    }

    public void setSitePages(ObservableList<SitePages> sitePages) {
        this.sitePages = sitePages;
    }

}
