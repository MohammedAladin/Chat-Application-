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
    public static final List<String> NICE_COLORS = Arrays.asList(
            "#3498db", "#2ecc71", "#e74c3c", "#f39c12", "#9b59b6",
            "#34495e", "#1abc9c", "#d35400", "#c0392b", "#7f8c8d",
            "#16a085", "#27ae60", "#e67e22", "#8e44ad", "#2c3e50",
            "#2980b9", "#2c3e50", "#95a5a6", "#e74c3c", "#f39c12",
            "#d35400", "#1abc9c", "#3498db", "#9b59b6", "#27ae60",
            "#8e44ad", "#16a085", "#34495e", "#f1c40f", "#e74c3c"
    );


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