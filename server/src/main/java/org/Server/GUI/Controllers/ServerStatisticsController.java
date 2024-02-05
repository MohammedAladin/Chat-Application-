package org.Server.GUI.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.Server.Repository.UserRepository;
import org.Server.ServerApplication;
import org.Server.ServerModels.ServerEntities.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerStatisticsController implements Initializable {
    @FXML
    private PieChart user_status;
    @FXML
    private PieChart user_gender;
    @FXML
    private PieChart user_country;
    UserRepository userRepository = new UserRepository();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startUpdatingCharts();
    }
    private void updateCharts() {
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
    }

    public void startUpdatingCharts() {
        scheduler.scheduleAtFixedRate(this::updateCharts, 0, 5, TimeUnit.SECONDS);
    }

    public void stopUpdatingCharts() {
        scheduler.shutdown();
    }
}