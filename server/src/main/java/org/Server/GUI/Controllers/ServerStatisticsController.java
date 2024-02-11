package org.Server.GUI.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import org.Server.Repository.UserRepository;
import org.Server.ServerApplication;
import org.Server.ServerModels.ServerEntities.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.Server.Service.ServerCallBacks.CallBackServicesImpl.clients;

public class ServerStatisticsController implements Initializable {
    @FXML
    private PieChart user_status;
    @FXML
    private PieChart user_gender;
    @FXML
    private PieChart user_country;
    @FXML
    public Label offlineLabel;
    @FXML
    public Label onlineLabel;
    UserRepository userRepository = new UserRepository();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ServerStatisticsController instance = null;

    public ServerStatisticsController(){
        instance = this;
    }

    public static ServerStatisticsController getInstance(){
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startUpdatingCharts();
        updateOnlineUsers();
    }
    private void updateCharts() {
        ObservableList<PieChart.Data> userGenderData = FXCollections.observableArrayList();
        userRepository.getAllUsersGenders().forEach((gender, count) -> {
            userGenderData.add(new PieChart.Data(gender, count));
        });

        ObservableList<PieChart.Data> userCountryData = FXCollections.observableArrayList();
        userRepository.getAllUsersCountryCount().forEach((country, count) -> {
            userCountryData.add(new PieChart.Data(country, count));
        });

        Platform.runLater(() -> {
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
    public void updateOnlineUsers(){
        ObservableList<PieChart.Data> userStatusData = FXCollections.observableArrayList();
        userRepository.getAllUsersStatus().forEach((userStatus, count) -> {
            userStatusData.add(new PieChart.Data(userStatus, count));
            offlineLabel.setText("Offline: " + count);
        });
        userStatusData.add(new PieChart.Data("Online", clients.size())); // Add "Online" data here
        Platform.runLater(() -> {
                    user_status.setData(userStatusData);
                    onlineLabel.setText("Online: " + clients.size());
                }
        );

    }
}