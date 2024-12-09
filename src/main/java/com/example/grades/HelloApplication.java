package com.example.grades;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application
{
    public static final String url = "jdbc:postgresql://localhost:5432/grades";
    public static final String user = "postgres";
    public static final String password = "catalina";

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("S-a conectat la baza de date");
        } catch (SQLException e) {
            System.out.println("Eroare la conectarea la baze de date");
            e.printStackTrace();
        }
        initLoginView(stage);
        stage.setWidth(1500);
        stage.setHeight(800);
        stage.setX(220);
        stage.setY(130);
        stage.setTitle("Calculator de medii");
        stage.show();
    }


    private void initLoginView(Stage primaryStage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/pagina-principala.fxml"));
        Scene loginScene = new Scene(loginLoader.load());
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


