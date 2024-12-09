package com.example.grades.controller;

import com.example.grades.domain.Materie;
import com.example.grades.domain.MaterieValidator;
import com.example.grades.domain.Procentaje;
import com.example.grades.repository.MaterieRepository;
import com.example.grades.service.MaterieService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class MediiController {
    @FXML
    private ListView<Materie> materiiListView;

    @FXML
    private ListView<String> detaliiListView;

    @FXML
    private Label mediaLabel;

    @FXML
    private TextField nume;

    @FXML
    private TextField notaField;

    @FXML
    private TextField procentajField;

    public static final String url = "jdbc:postgresql://localhost:5432/grades";
    public static final String user = "postgres";
    public static final String password = "catalina";
    private MaterieValidator materieValidator=new MaterieValidator();
    private MaterieRepository materieRepository=new MaterieRepository(materieValidator,url,user,password);
    private MaterieService materieService=new MaterieService(materieRepository);

    public MediiController() {
        materieService = new MaterieService(materieRepository);
    }


    @FXML
    public void adaugaMaterie(ActionEvent event) {
        String numeMaterie = nume.getText().trim();

        if (numeMaterie.isEmpty()) {
            MessageAlert.showErrorMessage(null,"Nu ati selectat materia");
        }
        try
        {
            materieService.adaugaMaterie(numeMaterie);
            Materie materieAdaugata = materieService.getMaterieByNume(numeMaterie);
            materiiListView.getItems().add(materieAdaugata);
            actualizeazaListaMaterii();
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void stergeMaterie(ActionEvent event) {
        Materie materieSelectata = materiiListView.getSelectionModel().getSelectedItem();
        if (materieSelectata != null) {
            try{
                materieService.stergeMaterie(materieSelectata.getNume());
                actualizeazaListaMaterii();
            }
            catch(IllegalArgumentException e){
                e.printStackTrace();
            }
        } else {
            MessageAlert.showErrorMessage(null,"Nu ati selectat materia");
        }
    }

    @FXML
    public void adaugaNotaProcentaj(ActionEvent event) {
        Materie materieSelectata = materiiListView.getSelectionModel().getSelectedItem();
        if (materieSelectata == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat nici o materie");
            return;
        }

        String notaText = notaField.getText().trim();
        String procentajText = procentajField.getText().trim();
        if (notaText.isEmpty() || procentajText.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Va rog sa completati nota si procentajul");
            return;
        }

        try {
            double nota = Double.parseDouble(notaText);
            double procentaj = Double.parseDouble(procentajText);
            double sumaProcentaje = 0.0;
            for (Procentaje procent : materieService.getMaterieProcentaje(materieSelectata.getId())) {
                sumaProcentaje += procent.getProcent();
            }

            if (sumaProcentaje + procentaj > 100) {
                MessageAlert.showErrorMessage(null, "Procentajul total nu poate depăși 100%");
                return;
            }

            Procentaje procentajObj = new Procentaje(procentaj, nota);
            materieService.adaugaProcentaj(materieSelectata.getId(), procentajObj);
            actualizeazaDetaliiMaterie(materieSelectata);
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Nota si procentajul trebuie sa fie numere valide");
        }
    }


    @FXML
    public void stergeNotaProcentaj(ActionEvent event) {
        Materie materieSelectata = materiiListView.getSelectionModel().getSelectedItem();
        String procentajSelectat = detaliiListView.getSelectionModel().getSelectedItem();

        if (materieSelectata == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat nicio materie");
            return;
        }
        if (procentajSelectat == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat niciun procentaj");
            return;
        }
        try {
            String[] parts = procentajSelectat.split(" - ");
            System.out.println("Parts length: " + parts.length);
            String procentajString = parts[0].replace("%", "").trim();
            double procentaj = Double.parseDouble(procentajString);
            String notaString = parts[1].split(": ")[1].trim();
            double nota = Double.parseDouble(notaString);
            materieService.stergeProcentaj(materieSelectata.getId(), procentaj, nota);
            actualizeazaDetaliiMaterie(materieSelectata);
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null, "Eroare la procesarea datelor");
        }
    }



    private void actualizeazaDetaliiMaterie(Materie materie) {
        detaliiListView.getItems().clear();
        materie.getProcentaje().clear();
        for (Procentaje procentaj : materieService.getMaterieProcentaje(materie.getId())) {
            materie.getProcentaje().add(procentaj);
            detaliiListView.getItems().add(procentaj.toString());
        }
        double media = materieService.calculeazaMedia(materie.getId());
        mediaLabel.setText("Media: " + String.format("%.2f", media));
    }

    private void actualizeazaListaMaterii() {
        materiiListView.getItems().clear();
        materiiListView.getItems().addAll(materieService.getAllMaterii());
    }

    @FXML
    public void initialize() {
        materiiListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizeazaDetaliiMaterie(newValue);
            }
        });
        actualizeazaListaMaterii();
    }

}
