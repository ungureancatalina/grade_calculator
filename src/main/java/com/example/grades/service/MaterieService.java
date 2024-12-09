package com.example.grades.service;

import com.example.grades.domain.Materie;
import com.example.grades.domain.Procentaje;
import com.example.grades.domain.ValidationException;
import com.example.grades.repository.MaterieRepository;

import java.util.List;

public class MaterieService {
    private final MaterieRepository repository;

    public MaterieService(MaterieRepository repository) {
        this.repository = repository;
    }

    public int getIdMaterieNou()
    {
        List<Materie> materii=getAllMaterii();
        if(materii.isEmpty())
            return 1;
        int id_nr=0;
        for(Materie materie:materii) {
            id_nr++;
        }
        id_nr++;
        return id_nr;
    }

    public void adaugaMaterie(String nume) throws IllegalArgumentException {
        if(nume==null || nume.length()==0)
            throw new IllegalArgumentException("Numele nu poate fi vid");
        int id=getIdMaterieNou();
        Materie materie=new Materie(id,nume);
        try {
            repository.addMaterie(materie);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean stergeMaterie(String nume) throws IllegalArgumentException {
        if(nume==null || nume.length()==0)
            throw new IllegalArgumentException("Numele nu poate fi vid");
        Materie materie=getMaterieByNume(nume);
        return repository.removeMaterie(materie);
    }

    public boolean adaugaProcentaj(int idMaterie, Procentaje procentaj) throws IllegalArgumentException {
        if(getMaterieById(idMaterie)==null)
            throw new IllegalArgumentException("Nu exista materia");
        return repository.addProcentaj(idMaterie, procentaj);
    }

    public boolean stergeProcentaj(int materieId, double procentaj, double nota) throws IllegalArgumentException {
        if(getMaterieById(materieId)==null)
            throw new IllegalArgumentException("Nu exista materia");
        return repository.removeProcentaj(materieId, procentaj, nota);
    }

    public List<Materie> getAllMaterii() {
        return repository.getAllMaterii();
    }

    public Materie getMaterieById(int id) {
        return repository.findMaterieById(id);
    }

    public Materie getMaterieByNume(String nume) {
        return repository.findMaterieByNume(nume);
    }

    public List<Procentaje> getMaterieProcentaje(int idMaterie) {
        return repository.getProcentajeByMaterie(idMaterie);
    }

    public double calculeazaMedia(int id) throws IllegalArgumentException{
        Materie materie = repository.findMaterieById(id);
        if (materie == null) {
            throw new IllegalArgumentException("Materia nu exista.");
        }
        List<Procentaje> procentaje = repository.getProcentajeByMaterie(id);
        double suma = 0;
        int totalProcente = 0;

        for (Procentaje p : procentaje) {
            suma += p.getNota() * p.getProcent();
            totalProcente += p.getProcent();
        }
        if (totalProcente > 0) {
            return suma / totalProcente;
        } else {
            return 0;
        }
    }
}
