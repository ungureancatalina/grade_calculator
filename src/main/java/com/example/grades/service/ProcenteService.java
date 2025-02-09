package com.example.grades.service;

import com.example.grades.domain.Materie;
import com.example.grades.domain.Procentaje;
import com.example.grades.repository.MaterieRepository;
import com.example.grades.repository.ProcenteRepository;
import com.example.grades.validator.MaterieValidator;
import com.example.grades.validator.ProcentajeValidator;
import com.example.grades.validator.ValidationException;

import java.util.List;

public class ProcenteService {
    private final MaterieRepository repository;
    private final ProcenteRepository procenteRepository;
    private final ProcentajeValidator procentajValidator;

    public ProcenteService(MaterieRepository repository,ProcenteRepository procenteRepository) {
        this.repository = repository;
        this.procenteRepository = procenteRepository;
        this.procentajValidator = new ProcentajeValidator();
    }

    public boolean adaugaProcentaj(int idMaterie, double procentaj, double nota) throws ValidationException {
        Materie materie = repository.findMaterieById(idMaterie);
        if (materie == null) {
            throw new ValidationException("Materia nu există.");
        }

        List<Procentaje> procentajeExistente = procenteRepository.getProcentajeByMaterie(idMaterie);
        Procentaje procentajObj = new Procentaje(procentaj, nota);

        procentajValidator.validateWithExisting(procentajObj, procentajeExistente);

        boolean added = procenteRepository.addProcentaj(idMaterie, procentaj, nota);
        double mediaNoua = calculeazaMedia(idMaterie);
        materie.setMedia(mediaNoua);

        return added;
    }

    public void stergeProcentaj(int materieId, double procentaj, double nota) {
        procenteRepository.removeProcentaj(materieId, procentaj, nota);
    }

    public List<Procentaje> getMaterieProcentaje(int materieId) {
        return procenteRepository.getProcentajeByMaterie(materieId);
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
}
