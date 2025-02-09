package com.example.grades.controller;

import com.example.grades.domain.Materie;
import com.example.grades.observer.MaterieObserver;
import com.example.grades.service.MaterieService;
import com.example.grades.service.ProcenteService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class MediiController implements MaterieObserver {
    @FXML
    private TableView<Materie> materiiTableView;
    @FXML
    private TableColumn<Materie, String> materieColumn;
    @FXML
    private TableColumn<Materie, String> mediaColumn;
    @FXML
    private TableColumn<Materie, String> crediteColumn;
    @FXML
    private TextField numeMaterieField;
    @FXML
    private TextField numarCrediteField;
    @FXML
    private Label mediaFinalaLabel;

    private MaterieService materieService;
    private ProcenteService procenteService;
    private int loggedInUserId;

    @FXML
    public void initialize() {
        materieColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNume()));
        crediteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumarCredite())));
        mediaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getMedia())));

        materiiTableView.setRowFactory(tv -> {
            TableRow<Materie> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    deschideDetaliiMaterie(row.getItem());
                }
            });
            return row;
        });
    }

    public void setService(MaterieService materieService, ProcenteService procenteService, int loggedInUserId) {
        this.materieService = materieService;
        this.procenteService = procenteService;
        this.loggedInUserId = loggedInUserId;
        actualizeazaListaMaterii();
    }

    @FXML
    public void adaugaMaterie(ActionEvent event) {
        String numeMaterie = numeMaterieField.getText().trim();
        String crediteText = numarCrediteField.getText().trim();

        if (numeMaterie.isEmpty() || crediteText.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Introduceți numele materiei și numărul de credite!");
            return;
        }

        try {
            int numarCredite = Integer.parseInt(crediteText);

            if (materieService.existaMaterie(loggedInUserId, numeMaterie)) {
                MessageAlert.showErrorMessage(null, "Materia există deja!");
                return;
            }

            materieService.adaugaMaterie(loggedInUserId, numeMaterie, numarCredite);
            actualizeazaListaMaterii();

            numeMaterieField.clear();
            numarCrediteField.clear();
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Numărul de credite trebuie să fie un număr întreg valid!");
        }
    }

    @FXML
    public void stergeMaterie(ActionEvent event) {
        String numeMaterie = numeMaterieField.getText().trim();
        if (numeMaterie.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Introduceți numele materiei pe care doriți să o ștergeți!");
            return;
        }

        Optional<Materie> materieSelectata = materieService.getMateriiForUser(loggedInUserId)
                .stream()
                .filter(m -> m.getNume().equalsIgnoreCase(numeMaterie))
                .findFirst();

        if (materieSelectata.isPresent()) {
            materieService.stergeMaterie(loggedInUserId, materieSelectata.get().getId());
            actualizeazaListaMaterii();
        } else {
            MessageAlert.showErrorMessage(null, "Materia nu a fost găsită!");
        }
    }

    @FXML
    public void calculeazaMediaFinala() {
        double mediaFinala = materieService.calculeazaMediaFinala(materieService.getMateriiForUser(loggedInUserId));
        mediaFinalaLabel.setText("Media finală: " + String.format("%.2f", mediaFinala));
    }

    @FXML
    public void deschideDetaliiMaterie(Materie materieSelectata) {
        if (materieSelectata == null) {
            MessageAlert.showErrorMessage(null, "Selectați o materie pentru a vedea detaliile.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/procente.fxml"));
            Scene scene = new Scene(loader.load());
            DetaliiMaterieController controller = loader.getController();

            controller.setService(materieService, procenteService, materieSelectata, this); 

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Detalii Materie - " + materieSelectata.getNume());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, "Eroare la încărcarea ferestrei cu procentaje.");
        }
    }


    private void actualizeazaListaMaterii() {
        materiiTableView.getItems().clear();
        materiiTableView.getItems().addAll(materieService.getMateriiForUser(loggedInUserId));
    }

    @Override
    public void onMediaUpdated() {
        actualizeazaListaMaterii();
    }
}
