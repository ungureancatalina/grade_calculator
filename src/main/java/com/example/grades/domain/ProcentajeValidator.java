package com.example.grades.domain;

public class ProcentajeValidator implements Validator<Procentaje> {

    @Override
    public void validate(Procentaje entity) throws ValidationException {
        String errors="";
        if(entity.getProcent()<=0)
            errors+="Procentajul trebuie sa fie mai mare decat 0";
        if(entity.getProcent()>100)
            errors+="Procentajul trebuie sa fie mai mic sau egal cu 100";
        if(entity.getNota()<0)
            errors+="Nota trebuie sa fie mai mare decat 0";
        if(entity.getNota()>10)
            errors+="Nota trebuie sa fie mai mica sau egala cu 10";
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }
}
