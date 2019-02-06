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
public class CalendarBoundaries {
    private StringProperty startingMondayYear;
    private StringProperty startingMondayMonth;
    private StringProperty startingMondayDay;
    private StringProperty endingFridayYear;
    private StringProperty endingFridayMonth;
    private StringProperty endingFridayDay;

    public CalendarBoundaries(String startingMondayYear, String startingMondayMonth,
            String startingMondayDay, String endingFridayYear,
            String endingFridayMonth, String endingFridayDay) {
        this.startingMondayYear = new SimpleStringProperty(startingMondayYear);
        this.startingMondayMonth = new SimpleStringProperty(startingMondayMonth);
        this.startingMondayDay = new SimpleStringProperty(startingMondayDay);
        this.endingFridayYear = new SimpleStringProperty(endingFridayYear);
        this.endingFridayMonth = new SimpleStringProperty(endingFridayMonth);
        this.endingFridayDay = new SimpleStringProperty(endingFridayDay);
    }

    public CalendarBoundaries() {
        this.startingMondayYear = new SimpleStringProperty("2012");
        this.startingMondayMonth = new SimpleStringProperty("4");
        this.startingMondayDay = new SimpleStringProperty("22");
        this.endingFridayYear = new SimpleStringProperty("2012");
        this.endingFridayMonth = new SimpleStringProperty("4");
        this.endingFridayDay = new SimpleStringProperty("22");
    }

    

    public String getStartingMondayYear() {
        return startingMondayYear.get();
    }

    public void setStartingMondayYear(String startingMondayYear) {
        this.startingMondayYear.set(startingMondayYear);
    }

    public String getStartingMondayMonth() {
        return startingMondayMonth.get();
    }

    public void setStartingMondayMonth(String startingMondayMonth) {
        this.startingMondayMonth.set(startingMondayMonth);
    }

    public String getStartingMondayDay() {
        return startingMondayDay.get();
    }

    public void setStartingMondayDay(String startingMondayDay) {
        this.startingMondayDay.set(startingMondayDay);
    }

    public String getEndingFridayYear() {
        return endingFridayYear.get();
    }

    public void setEndingFridayYear(String endingFridayYear) {
        this.endingFridayYear.set(endingFridayYear);
    }

    public String getEndingFridayMonth() {
        return endingFridayMonth.get();
    }

    public void setEndingFridayMonth(String endingFridayMonth) {
        this.endingFridayMonth.set(endingFridayMonth);
    }

    public String getEndingFridayDay() {
        return endingFridayDay.get();
    }

    public void setEndingFridayDay(String endingFridayDay) {
        this.endingFridayDay.set(endingFridayDay);
    }
    
}
