package com.example.grades.validator;

public interface Validator<T> {
    void validate(T entity)
            throws ValidationException;
}