package org.Server.GUI.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ServerStatisticsController implements Initializable {
    @FXML
    private PieChart user_status;
    @FXML
    private PieChart user_gender;
    @FXML
    private PieChart user_country;
    UserRepository userRepository = new UserRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);

                    ObservableList<PieChart.Data> userStatusData = FXCollections.observableArrayList();
                    userRepository.getAllUsersStatus().forEach((userStatus, count) -> {
                        userStatusData.add(new PieChart.Data(userStatus, count));
                    });

                    ObservableList<PieChart.Data> userGenderData = FXCollections.observableArrayList();
                    userRepository.getAllUsersGenders().forEach((gender, count) -> {
                        userGenderData.add(new PieChart.Data(gender, count));
                    });

                    ObservableList<PieChart.Data> userCountryData = FXCollections.observableArrayList();
                    userRepository.getAllUsersCountryCount().forEach((country, count) -> {
                        userCountryData.add(new PieChart.Data(country, count));
                    });

                    Platform.runLater(() -> {
                        user_status.setData(userStatusData);
                        user_gender.setData(userGenderData);
                        user_country.setData(userCountryData);
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}