package org.Client.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import Model.DTO.Style;
import org.Client.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class StyleController implements Initializable {

    @FXML
    private ToggleButton italicID;

    @FXML
    private ToggleButton boldID;

    @FXML
    private ColorPicker fontColorID;

    @FXML
    private ColorPicker backGroundColorID;

    @FXML
    private ComboBox<Integer> sizeID;
    @FXML
    private Button resetID;
    private Style userStyle;
    private String[] fontStyle;

    Model model = Model.getInstance();

    public void saveStyle() {
        fontStyle = new String[2];
        fontStyle[0] = italicID.isSelected() ? "italic" : "normal";
        fontStyle[1] = boldID.isSelected() ? "bold" : "normal";
        String fontColor = toCssColor(fontColorID.getValue());
        String  backgroundColor = toCssColor(backGroundColorID.getValue());
        Integer fontSizeValue = sizeID.getValue();
        int fontSize = (fontSizeValue != null) ? fontSizeValue : 12;
        userStyle = new Style(fontSize, fontStyle, fontColor, backgroundColor);
        model.setStyle(userStyle);
        model.getViewFactory().getChatUserController().setStyle(userStyle);
       // model.getViewFactory().getGoupChatController().setStyle(userStyle);

    }


    public Style getUserStyle() {
        return userStyle;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 8; i <= 24; i++) {
            sizeID.getItems().add(i);
        }
        fontColorID.setValue(Color.BLACK); // Default color is black
        Color defaultBackgroundColor = Color.web("#EFF6FC");
        backGroundColorID.setValue(defaultBackgroundColor); // Default color is white

        italicID.selectedProperty().addListener((observable, oldValue, newValue) ->{
            saveStyle();
            refresh();
        });
        boldID.selectedProperty().addListener((observable, oldValue, newValue) ->{
            saveStyle();
            refresh();
        });
        fontColorID.valueProperty().addListener((observable, oldValue, newValue) -> {
            saveStyle();
            refresh();
        });
        backGroundColorID.valueProperty().addListener((observable, oldValue, newValue) -> {
            saveStyle();
            refresh();
        });
        sizeID.valueProperty().addListener((observable, oldValue, newValue) -> {
            saveStyle();
            refresh();
        });
        resetID.setOnAction(actionEvent -> {
            reset();
            refresh();
        });
    }
    public String toCssColor(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    public void reset(){
        // Reset the toggle buttons
        italicID.setSelected(false);
        boldID.setSelected(false);
        fontColorID.setValue(Color.BLACK); // Default color is black
        Color defaultBackgroundColor = Color.web("#EFF6FC");
        backGroundColorID.setValue(defaultBackgroundColor); // Default color is white
        model.getViewFactory().getChatUserController().setStyle(null);
       // model.getViewFactory().getGoupChatController().setStyle(null);
        model.getViewFactory().getChatUserController().getTextFieldID().setStyle(null);
    }
    public void refresh(){
        String originalText = Model.getInstance().getViewFactory().getChatUserController().getTextFieldID().getText();
        Model.getInstance().getViewFactory().getChatUserController().getTextFieldID().setText("");
        Model.getInstance().getViewFactory().getChatUserController().getTextFieldID().setText(originalText);

    }
}
