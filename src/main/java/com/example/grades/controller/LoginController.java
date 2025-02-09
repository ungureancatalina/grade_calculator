package com.example.grades.controller;

import com.example.grades.domain.User;
import com.example.grades.repository.UserRepository;
import com.example.grades.service.MaterieService;
import com.example.grades.service.ProcenteService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private UserRepository userRepository;
    private MaterieService materieService;
    private ProcenteService procenteService;

    public void setProcenteService(ProcenteService procenteService) {
        this.procenteService = procenteService;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setMaterieService(MaterieService materieService) {
        this.materieService = materieService;
    }

    @FXML
    public void login(ActionEvent event) {
        if (userRepository == null) {
            errorLabel.setText("Eroare internă");
            return;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Introduceți username și parola.");
            return;
        }

        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            errorLabel.setText("User inexistent!");
            return;
        }

        if (!user.getPassword().equals(password)) {
            errorLabel.setText("Parolă incorectă!");
            return;
        }

        openPaginaPrincipala(user);
    }

    @FXML
    public void register(ActionEvent event) {
        if (userRepository == null) {
            errorLabel.setText("Eroare internă");
            return;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Introduceți username și parola.");
            return;
        }

        if (userRepository.findUserByUsername(username) != null) {
            errorLabel.setText("Username-ul există deja.");
            return;
        }

        User newUser = new User(0, username, password);
        userRepository.addUser(newUser);
        openPaginaPrincipala(newUser);
    }

    private void openPaginaPrincipala(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pagina-principala.fxml"));
            Scene scene = new Scene(loader.load());

            MediiController mediiController = loader.getController();
            mediiController.setService(materieService, procenteService, user.getId());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
