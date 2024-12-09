package com.example.grades.domain;

public interface Validator<T> {
    void validate(T entity)
            throws ValidationException;
}