/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.ScheduleData;

/**
 *
 * @author Jose
 */
public class DeleteScheduleItemFromTable_Transaction implements jTPS_Transaction {

    String criteria;
    String date;
    String link;
    String title;
    String topic;
    String type;
    CSGeneratorApp app;

    public DeleteScheduleItemFromTable_Transaction(String criteria, String date, String link, String title, String topic, String type, CSGeneratorApp app) {
        this.criteria = criteria;
        this.date = date;
        this.link = link;
        this.title = title;
        this.topic = topic;
        this.type = type;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData csgData = (CSGData) app.getDataComponent();
        ScheduleData data = csgData.getScheduleData();
        data.removeScheduleItem(criteria, date, link, title, topic, type);
    }

    @Override
    public void undoTransaction() {
        CSGData csgData = (CSGData) app.getDataComponent();
        ScheduleData data = csgData.getScheduleData();
        data.addScheduleItem(type,date,title,topic,link,criteria);
    }
}
