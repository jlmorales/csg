/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jose
 */
public class PageStyle {

    private StringProperty bannerSchoolImage;
    private StringProperty leftFooterImage;
    private StringProperty rightFooterImage;
    private StringProperty styleSheet;

    public PageStyle() {
        bannerSchoolImage = new SimpleStringProperty("file:./images/CSLogo.png");
        leftFooterImage = new SimpleStringProperty("file:./images/CSLogo.png");
        rightFooterImage = new SimpleStringProperty("file:./images/CSLogo.png");
        styleSheet = new SimpleStringProperty("");
    }

    public String getStyleSheet() {
        return styleSheet.get();
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet.set(styleSheet);
    }

}
