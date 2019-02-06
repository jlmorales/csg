/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.jtps;

import csg.CSGeneratorApp;
import csg.data.CSGData;
import csg.data.RecitationData;
import csg.data.ScheduleData;

/**
 *
 * @author Jose
 */
public class EditScheduleItemFromTable_Transaction implements jTPS_Transaction {

    String criteria;
    String date;
    String link;
    String title;
    String topic;
    String type;
    String oldCriteria;
    String oldDate;
    String oldLink;
    String oldTitle;
    String oldTopic;
    String oldType;
    CSGeneratorApp app;

    public EditScheduleItemFromTable_Transaction(String criteria, String date, String link, String title, String topic, String type, String oldCriteria, String oldDate, String oldLink, String oldTitle, String oldTopic, String oldType, CSGeneratorApp app) {
        this.criteria = criteria;
        this.date = date;
        this.link = link;
        this.title = title;
        this.topic = topic;
        this.type = type;
        this.oldCriteria = oldCriteria;
        this.oldDate = oldDate;
        this.oldLink = oldLink;
        this.oldTitle = oldTitle;
        this.oldTopic = oldTopic;
        this.oldType = oldType;
        this.app = app;
    }

    @Override
    public void doTransaction() {
        CSGData csgD = (CSGData) app.getDataComponent();
        ScheduleData data = csgD.getScheduleData();
        data.editReciation(criteria, date, link, title, topic, type);
    }

    @Override
    public void undoTransaction() {
        CSGData csgD = (CSGData) app.getDataComponent();
        RecitationData data = csgD.getRecitationData();
        data.editReciation(oldCriteria, oldDate, oldLink, oldTitle, oldTopic, oldType);
    }

}
