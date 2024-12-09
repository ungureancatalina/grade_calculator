package com.example.grades.domain;

public class Procentaje{
    private double procent;
    private double nota;

    public Procentaje(double procent, double nota) {
        this.procent = procent;
        this.nota = nota;
    }

    public double getProcent() {
        return procent;
    }
    public void setProcent(double procent) {
        this.procent = procent;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return procent + "% - Nota: " + nota;
    }
}
