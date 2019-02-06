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
public class Team<E extends Comparable<E>> implements Comparable<E> {

    private StringProperty name;
    private StringProperty color;
    private StringProperty textColor;
    private StringProperty link;

    public Team(String name, String color, String textColor, String link) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.textColor = new SimpleStringProperty(textColor);
        this.link = new SimpleStringProperty(link);
    }

    @Override
    public int compareTo(E o) {
        return 0;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getTextColor() {
        return textColor.get();
    }

    public void setTextColor(String textColor) {
        this.textColor.set(textColor);
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }

}
