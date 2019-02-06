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
public class ScheduleDataTest {

    public ScheduleDataTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetScheduleItems() {
        System.out.println("testGetScheduleItems()");
        CSGData dataManager = TestSave.testLoad();
        ScheduleData data = dataManager.getScheduleData();
        ObservableList<ScheduleItem> scheduleItems= data.getScheduleItems();
        System.out.println("test getTitle()");
        assertEquals("SnowDay",scheduleItems.get(0).getTitle());
        System.out.println("test getTopic()");
        assertEquals("",scheduleItems.get(0).getTopic());
        System.out.println("test getType()");
        assertEquals("Holiday",scheduleItems.get(0).getType());
        System.out.println("test getDate()");
        assertEquals("2/9/2017",scheduleItems.get(0).getDate());
    }

}
