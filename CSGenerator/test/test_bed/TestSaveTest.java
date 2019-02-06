/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import csg.data.CSGData;
import csg.data.ProjectData;
import csg.data.Student;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static sun.invoke.util.ValueConversions.ignore;

/**
 *
 * @author Jose
 */
public class TestSaveTest {

    /*
    public TestSaveTest() {
    }
     */
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetFirstName() {
        System.out.println("Test getFirstName()");
        CSGData dataManager = TestSave.testLoad();
        ProjectData projectData = dataManager.getProjectData();
        ObservableList<Student> students = projectData.getStudents(); 
        Student student1=students.get(0);
        System.out.println("Test that first student name "+student1.getFirstName()+"=Jane");
        assertEquals("Jane", student1.getFirstName());
        System.out.println("Test that first student name "+student1.getFirstName()+"!=Joe");
        assertFalse("Joe".equals(student1.getFirstName()));
        
    }
    @Test
    public void testGetLastName() {
        System.out.println("Test getLastName()");
        CSGData dataManager = TestSave.testLoad();
        ProjectData projectData = dataManager.getProjectData();
        ObservableList<Student> students = projectData.getStudents(); 
        Student student1=students.get(0);
        System.out.println("Test that last name "+student1.getLastName()+" = Doe");
        assertEquals("Doe", student1.getLastName());
        System.out.println("Test that last name "+student1.getLastName()+"!= Schmo");
        assertFalse("Schmo".equals(student1.getLastName()));
        
    }

}
