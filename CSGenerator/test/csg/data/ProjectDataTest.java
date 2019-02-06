/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGeneratorApp;
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
public class ProjectDataTest {

    public ProjectDataTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getTeams method, of class ProjectData.
     */
    @Test
    public void testGetTeams() {
        System.out.println("testGetTeams()");
        CSGData dataManager = TestSave.testLoad();
        ProjectData data = dataManager.getProjectData();
        ObservableList<Team> teams = data.getTeams();
        System.out.println("test getName()");
        assertEquals("Atomic Comic",teams.get(0).getName());
        System.out.println("test getColor()");
        assertEquals("552211",teams.get(0).getColor());
        System.out.println("test getTextColor");
        assertEquals("ffffff",teams.get(0).getTextColor());
        System.out.println("test getLink");
        assertEquals("http://atomicomic.com",teams.get(0).getLink());
    }

    /**
     * Test of getStudents method, of class ProjectData.
     */
    @Test
    public void testGetStudents() {
        System.out.println("testgetStudents()"); 
        CSGData dataManager = TestSave.testLoad();
        ProjectData instance = dataManager.getProjectData();
        ObservableList<Student> students = instance.getStudents();
        System.out.println("test getFirstName()");
        assertEquals("Jane",students.get(0).getFirstName());
        System.out.println("test getLastName()");
        assertEquals("Doe",students.get(0).getLastName());
        System.out.println("test getTeam()");
        assertEquals("Atomic Comic",students.get(0).getTeam());
        System.out.println("test getRole()");
        assertEquals("Lead Programmer",students.get(0).getRole());
    }

}
