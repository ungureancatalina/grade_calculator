package com.example.grades.validator;

import com.example.grades.domain.Procentaje;
import java.util.List;

public class ProcentajeValidator implements Validator<Procentaje> {
    @Override
    public void validate(Procentaje entity) throws ValidationException {
        validateWithExisting(entity, List.of());
    }

    public void validateWithExisting(Procentaje entity, List<Procentaje> existente) throws ValidationException {
        StringBuilder errors = new StringBuilder();

        if (entity.getProcent() <= 0) {
            errors.append("Procentajul trebuie să fie mai mare decât 0.\n");
        }
        if (entity.getProcent() > 100) {
            errors.append("Procentajul trebuie să fie mai mic sau egal cu 100.\n");
        }
        if (entity.getNota() < 0 || entity.getNota() > 10) {
            errors.append("Nota trebuie să fie între 0 și 10.\n");
        }

        double sumaProcentaje = entity.getProcent();
        for (Procentaje p : existente) {
            sumaProcentaje += p.getProcent();
        }
        if (sumaProcentaje > 100) {
            errors.append("Procentajul total pentru această materie nu poate depăși 100%.\n");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString());
        }
    }
}
