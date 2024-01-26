package org.Server.GUI.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerStatisticsController implements Initializable {

    @FXML
    private PieChart user_status;
    @FXML
    private PieChart user_gender;
    @FXML
    private PieChart user_country;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> statusChart = FXCollections.observableArrayList(
          new PieChart.Data("Online", 30),
          new PieChart.Data("Offline", 50)
        );

        user_status.setData(statusChart);

        ObservableList<PieChart.Data> genderChart = FXCollections.observableArrayList(
                new PieChart.Data("Male", 60),
                new PieChart.Data("Female", 70)
        );

        user_gender.setData(genderChart);

        ObservableList<PieChart.Data> countryChart = FXCollections.observableArrayList(
                new PieChart.Data("Egypt", 30),
                new PieChart.Data("Bangladesh", 20)
        );

        user_country.setData(countryChart);
    }
}
