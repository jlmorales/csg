/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGeneratorApp;
import javafx.collections.ObservableList;

/**
 *
 * @author Jose
 */
public class CourseDetailsData {
      // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CSGeneratorApp app;
    public CourseInfo courseInfo;
    public SiteTemplate siteTemplate;
    public PageStyle pageStyle;

    public CourseDetailsData(CSGeneratorApp app) {
        this.app = app;
        courseInfo= new CourseInfo();
        siteTemplate= new SiteTemplate();
        pageStyle= new PageStyle();
        
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public SiteTemplate getSiteTemplate() {
        return siteTemplate;
    }

    public void setSiteTemplate(SiteTemplate siteTemplate) {
        this.siteTemplate = siteTemplate;
    }

    public PageStyle getPageStyle() {
        return pageStyle;
    }

    public void setPageStyle(PageStyle pageStyle) {
        this.pageStyle = pageStyle;
    }

    void resetData() {
        courseInfo= new CourseInfo();
        siteTemplate= new SiteTemplate();
        pageStyle= new PageStyle();
    }

   
    
    
    
    
}
