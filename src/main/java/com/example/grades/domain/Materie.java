package com.example.grades.domain;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;
import java.util.List;

public class Materie {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nume;
    private final SimpleIntegerProperty numarCredite;
    private final SimpleDoubleProperty media;
    private final List<Procentaje> procentaje;

    public Materie(int id, String nume, int numarCredite) {
        this.id = new SimpleIntegerProperty(id);
        this.nume = new SimpleStringProperty(nume);
        this.numarCredite = new SimpleIntegerProperty(numarCredite);
        this.media = new SimpleDoubleProperty(0);
        this.procentaje = new ArrayList<>();
    }

    public int getId() {
        return id.get();
    }

    public String getNume() {
        return nume.get();
    }

    public int getNumarCredite() {
        return numarCredite.get();
    }

    public double getMedia() {
        return media.get();
    }

    public void setMedia(double valoareMedia) {
        this.media.set(valoareMedia);
    }

    public List<Procentaje> getProcentaje() {
        return procentaje;
    }

    public SimpleStringProperty numeProperty() {
        return nume;
    }

    public SimpleDoubleProperty mediaProperty() {
        return media;
    }
}
