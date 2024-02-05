package org.Client.ClientEntities;

public class Style {
    private int fontSize ;
    private String fontStyle;
    private String color;
    private String  backgroundColor;


    public Style(int fontSize, String fontStyle , String color , String backgroundColor) {
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.color = color;
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
