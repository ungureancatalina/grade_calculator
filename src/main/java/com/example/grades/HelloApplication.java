package com.example.grades;

import com.example.grades.controller.LoginController;
import com.example.grades.repository.ProcenteRepository;
import com.example.grades.repository.UserRepository;
import com.example.grades.repository.MaterieRepository;
import com.example.grades.service.MaterieService;
import com.example.grades.service.ProcenteService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    private static final String URL = "jdbc:postgresql://localhost:5432/grades";
    private static final String USER = "postgres";
    private static final String PASSWORD = "catalina";

    @Override
    public void start(Stage stage) throws IOException {
        String URL = "jdbc:postgresql://localhost:5432/grades";
        String USER = "postgres";
        String PASSWORD = "catalina";

        UserRepository userRepository = new UserRepository(URL, USER, PASSWORD);
        MaterieRepository materieRepository = new MaterieRepository(URL, USER, PASSWORD);
        ProcenteRepository procenteRepository = new ProcenteRepository(URL, USER, PASSWORD);

        MaterieService materieService = new MaterieService(materieRepository, procenteRepository);
        ProcenteService procenteService = new ProcenteService(materieRepository, procenteRepository);

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load());

        LoginController loginController = loginLoader.getController();
        loginController.setUserRepository(userRepository);
        loginController.setMaterieService(materieService);
        loginController.setProcenteService(procenteService);

        stage.setScene(loginScene);
        stage.setTitle("Login");
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
