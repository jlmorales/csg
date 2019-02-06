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
public class ScheduleItem {
    private StringProperty type;
    private StringProperty date;
    private StringProperty title;
    private StringProperty topic;
    private StringProperty link;
    private StringProperty criteria;

    public ScheduleItem(String type, String date, String title, String topic, String link, String criteria) {
        
        this.type= new SimpleStringProperty(type);
        this.date= new SimpleStringProperty(date);
        this.title= new SimpleStringProperty(title);;
        this.topic= new SimpleStringProperty(topic);
        this.link= new SimpleStringProperty(link);
        this.criteria= new SimpleStringProperty(criteria);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public String getCriteria() {
        return criteria.get();
    }

    public void setCriteria(String criteria) {
        this.criteria.set(criteria);
    }
    
}
