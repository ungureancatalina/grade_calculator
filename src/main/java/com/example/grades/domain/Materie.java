package com.example.grades.domain;

import java.util.ArrayList;
import java.util.List;

public class Materie {
    private int id;
    private String nume;
    private List<Procentaje> procentaje;

    public Materie(int id, String nume) {
        this.id = id;
        this.nume = nume;
        this.procentaje = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public List<Procentaje> getProcentaje() {
        return procentaje;
    }
    public void addProcentaj(Procentaje procentaj) {
        this.procentaje.add(procentaj);
    }

    public boolean removeProcentaj(Procentaje procentaj) {
        return this.procentaje.remove(procentaj);
    }

    @Override
    public String toString() {
        return "Materie: " + nume;
    }

}
