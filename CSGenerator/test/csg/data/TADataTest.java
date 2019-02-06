/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.file.TimeSlot;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
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
public class TADataTest {
    
    public TADataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

  
    /**
     * Test of getTeachingAssistants method, of class TAData.
     */
    @Test
    public void testGetTeachingAssistants() {
        System.out.println("testGetTeachingAssistants()");
        CSGData dataManager = TestSave.testLoad();
        TAData data = dataManager.getTaData();
        ObservableList<TeachingAssistant> tas=data.getTeachingAssistants();
        System.out.println("testGetName()");
        assertEquals("a",tas.get(0).getName());
        System.out.println("testGetEmail()");
        assertEquals("a@gmail.com",tas.get(0).getEmail());
    }

    
}
