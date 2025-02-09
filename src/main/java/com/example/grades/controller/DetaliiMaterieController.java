package com.example.grades.controller;

import com.example.grades.domain.Materie;
import com.example.grades.domain.Procentaje;
import com.example.grades.observer.MaterieObserver;
import com.example.grades.service.MaterieService;
import com.example.grades.service.ProcenteService;
import com.example.grades.validator.ValidationException;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DetaliiMaterieController {
    @FXML
    private Label materieLabel;
    @FXML
    private TableView<Procentaje> detaliiTableView;
    @FXML
    private TableColumn<Procentaje, String> procentajColumn;
    @FXML
    private TableColumn<Procentaje, String> notaColumn;
    @FXML
    private TextField notaField;
    @FXML
    private TextField procentajField;
    @FXML
    private Button adaugaNotaBtn;
    @FXML
    private Button stergeNotaBtn;

    private MaterieService materieService;
    private ProcenteService procenteService;
    private Materie materie;
    private MaterieObserver observer;

    public void setService(MaterieService materieService, ProcenteService procenteService, Materie materie, MaterieObserver observer) {
        this.materieService = materieService;
        this.procenteService = procenteService;
        this.materie = materie;
        this.observer = observer;

        if (this.procenteService == null) {
            System.out.println("EROARE: procenteService NU a fost transmis corect!");
        } else {
            System.out.println("procenteService a fost transmis corect!");
        }

        materieLabel.setText(materie.getNume());
        configureTable();
        actualizeazaDetaliiMaterie();
    }



    private void configureTable() {
        procentajColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProcent())));
        notaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNota())));
    }

    private void actualizeazaDetaliiMaterie() {
        if (procenteService == null) {
            System.out.println("EROARE: procenteService NU este inițializat!");
            return;
        }
        detaliiTableView.getItems().clear();
        detaliiTableView.getItems().addAll(procenteService.getMaterieProcentaje(materie.getId()));
    }

    @FXML
    public void adaugaNotaProcentaj() {
        String notaText = notaField.getText().trim();
        String procentajText = procentajField.getText().trim();

        if (notaText.isEmpty() || procentajText.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Introduceți atât nota cât și procentajul!");
            return;
        }

        try {
            double nota = Double.parseDouble(notaText);
            double procentaj = Double.parseDouble(procentajText);

            procenteService.adaugaProcentaj(materie.getId(), procentaj, nota);
            actualizeazaDetaliiMaterie();
            notaField.clear();
            procentajField.clear();

            double mediaNoua = procenteService.calculeazaMedia(materie.getId());
            if (mediaNoua > 0) {
                observer.onMediaUpdated();
            }

        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Nota și procentajul trebuie să fie numere valide.");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void stergeNotaProcentaj() {
        Procentaje procentajSelectat = detaliiTableView.getSelectionModel().getSelectedItem();

        if (procentajSelectat == null) {
            MessageAlert.showErrorMessage(null, "Selectați un procentaj pentru ștergere!");
            return;
        }

        procenteService.stergeProcentaj(materie.getId(), procentajSelectat.getProcent(), procentajSelectat.getNota());
        actualizeazaDetaliiMaterie();

        double media = procenteService.calculeazaMedia(materie.getId());
        observer.onMediaUpdated();
    }

}
