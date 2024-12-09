package com.example.grades.domain;

public class MaterieValidator implements Validator<Materie> {

    @Override
    public void validate(Materie entity) throws ValidationException {
        String errors="";
        if(entity.getNume().isEmpty())
            errors+="Numele materiei nu poate fi asa de scurt";
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }
}
