/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import test_bed.TestSave;

/**
 *
 * @author Jose
 */
public class CourseDetailsDataTest {
    
    public CourseDetailsDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getCourseInfo method, of class CourseDetailsData.
     */
    @Test
    public void testGetCourseInfo() {
        System.out.println("testGetCourseInfo()");
        CSGData dataManager = TestSave.testLoad();
        CourseDetailsData courseDetailsData = dataManager.getCourseDetailsData();
        CourseInfo courseInfo = courseDetailsData.getCourseInfo();
        System.out.println("test getSemester()");
        assertEquals("Fall", courseInfo.getSemester());
        System.out.println("test getSubject()");
        assertEquals("CSE", courseInfo.getSubject());
        System.out.println("test getTitle()");
        assertEquals("Computer Science III", courseInfo.getTitle());
        System.out.println("test getYear()");
        assertEquals("2017", courseInfo.getYear());
        System.out.println("test getInstrName()");
        assertEquals("Richard Mckenna", courseInfo.getInstrName());
        System.out.println("test GetInstrHome()");
        assertEquals("http://www.cs.stonybrook.edu/richard", courseInfo.getInstrHome());
        System.out.println("test getExportDir()");
        assertEquals("./Courses/CSE219/Summer2017/public", courseInfo.getExportDir());
        
    }


    /**
     * Test of getSiteTemplate method, of class CourseDetailsData.
     */
    @Test
    public void testGetSiteTemplate() {
        System.out.println("testGetSiteTemplate()");
        CSGData dataManager = TestSave.testLoad();
        CourseDetailsData courseDetailsData = dataManager.getCourseDetailsData();
        SiteTemplate siteTemplate = courseDetailsData.getSiteTemplate();
        System.out.println("test GetTemplateDirectory()");
        assertEquals("./templates/CSE219", siteTemplate.getTemplateDirectory());
        ObservableList<SitePages> sitePages = siteTemplate.getSitePages();
        SitePages sitePage= sitePages.get(0);
        System.out.println("test getUse()");
        assertEquals(true, sitePage.getUse());
        System.out.println("test getNavbarTitle()");
        assertEquals("Home", sitePage.getNavbarTitle());
        System.out.println("test getFileName()");
        assertEquals("index.html", sitePage.getFileName());
        System.out.println("test getScript()");
        assertEquals("HomeBuilder.js", sitePage.getScript());
        
        
    }

    

    
}
