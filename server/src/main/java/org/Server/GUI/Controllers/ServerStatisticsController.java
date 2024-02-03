package org.Server.GUI.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.Server.Repository.UserRepository;
import org.Server.ServerModels.ServerEntities.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerStatisticsController implements Initializable {
    @FXML
    private PieChart user_status;
    @FXML
    private PieChart user_gender;
    @FXML
    private PieChart user_country;

    public static ObservableList<User> activeUsers;

    static {
        try {
            activeUsers = FXCollections.observableArrayList(new UserRepository().findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user_status.setData(FXCollections.observableArrayList(
                new PieChart.Data("Online", activeUsers.stream().filter(User::isOnline).count()),
                new PieChart.Data("Offline", activeUsers.stream().filter(user -> !user.isOnline()).count())
        ));

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
