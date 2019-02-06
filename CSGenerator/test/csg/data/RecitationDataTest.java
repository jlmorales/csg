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
public class RecitationDataTest {
    
    public RecitationDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getRecitations method, of class RecitationData.
     */
    @Test
    public void testGetRecitations() {
        System.out.println("testGetRecitations()");
        CSGData dataManager = TestSave.testLoad();
        RecitationData data = dataManager.getRecitationData();
        ObservableList<Recitation> recitations= data.getRecitations();
        System.out.println("test getInstructor()");
        assertEquals("Banerjee",recitations.get(0).getInstructor());
        System.out.println("test getDayTime()");
        assertEquals("Tues 5:30pm-6:23pm",recitations.get(0).getDayTime());
        System.out.println("test getSection()");
        assertEquals("R05",recitations.get(0).getSection());
        System.out.println("test getLocation");
        assertEquals("old CS 2114",recitations.get(0).getLocation());
        System.out.println("test getTa1");
        assertEquals("Joe Schmo",recitations.get(0).getTa1());
        System.out.println("test getTa2");
        assertEquals("Jane Doe",recitations.get(0).getTa2());
    }

    
}
