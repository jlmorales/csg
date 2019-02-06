/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jose
 */
public class SitePages<E extends Comparable<E>> implements Comparable<E> {

    private final BooleanProperty use;
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;

    public SitePages(Boolean use,
            String navbarTitle,
            String fileName,
            String script) {
        this.use=new SimpleBooleanProperty(use);
        this.navbarTitle=new SimpleStringProperty(navbarTitle);
        this.fileName=new SimpleStringProperty(fileName);
        this.script=new SimpleStringProperty(script);

    }

    public Boolean getUse() {
        return use.get();
    }

    public void setUse(boolean use) {
        this.use.set(use);
    }

    public String getNavbarTitle() {
        return navbarTitle.get();
    }

    public void setNavbarTitle(String navbarTitle) {
        this.navbarTitle.set(navbarTitle);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getScript() {
        return script.get();
    }

    public void setScript(String script) {
        this.script.set(script);
    }

    @Override
    public int compareTo(E o) {
         return this.getNavbarTitle().compareTo(((SitePages)o).getNavbarTitle());
    }

}
