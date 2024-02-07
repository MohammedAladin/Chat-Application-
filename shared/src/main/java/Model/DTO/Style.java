package Model.DTO;

import javafx.scene.paint.Color;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Style implements Serializable {
    private Integer fontSize ;
    private String []fontStyle;
    private String color;
    private String  backgroundColor;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public Style(Integer fontSize, String[] fontStyle , String color , String backgroundColor) {
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.color = color;
        this.backgroundColor = backgroundColor;
    }

    public Style() {
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String  backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String[] getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String[] fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
