package com.example.grades.service;

import com.example.grades.domain.Materie;
import com.example.grades.domain.Procentaje;
import com.example.grades.repository.MaterieRepository;
import com.example.grades.repository.ProcenteRepository;
import com.example.grades.validator.MaterieValidator;
import com.example.grades.validator.ProcentajeValidator;
import com.example.grades.validator.ValidationException;

import java.util.List;

public class MaterieService {
    private final MaterieRepository repository;
    private final ProcenteRepository procenteRepository;
    private final MaterieValidator materieValidator;

    public MaterieService(MaterieRepository repository, ProcenteRepository procenteRepository) {
        this.repository = repository;
        this.procenteRepository = procenteRepository;
        this.materieValidator = new MaterieValidator();
    }

    public boolean existaMaterie(int userId, String nume) {
        return repository.getMateriiByUser(userId).stream()
                .anyMatch(m -> m.getNume().equalsIgnoreCase(nume));
    }

    public void adaugaMaterie(int userId, String nume, int numarCredite) {
        if (nume == null || nume.isEmpty()) {
            throw new ValidationException("Numele materiei nu poate fi gol!");
        }
        repository.addMaterie(userId, nume, numarCredite);
    }

    public List<Materie> getMateriiForUser(int userId) {
        List<Materie> materii = repository.getMateriiByUser(userId);

        for (Materie materie : materii) {
            double media = calculeazaMedia(materie.getId());
            materie.setMedia(media);
        }

        return materii;
    }

    public void stergeMaterie(int userId, int materieId) {
        repository.removeMaterie(userId, materieId);
    }

    public double calculeazaMedia(int materieId) {
        List<Procentaje> procentaje = procenteRepository.getProcentajeByMaterie(materieId);
        double suma = 0;
        double totalProcente = 0;

        for (Procentaje p : procentaje) {
            suma += p.getNota() * (p.getProcent() / 100.0);
            totalProcente += p.getProcent();
        }
        return totalProcente > 0 ? suma : 0;
    }

    public double calculeazaMediaFinala(List<Materie> materii) {
        double sumaPonderata = 0;
        double totalCredite = 0;

        for (Materie materie : materii) {
            double mediaMaterie = calculeazaMedia(materie.getId());
            int credite = materie.getNumarCredite();
            sumaPonderata += Math.round(mediaMaterie) * credite;
            totalCredite += credite;
        }

        return (totalCredite > 0) ? sumaPonderata / totalCredite : 0;
    }
}
