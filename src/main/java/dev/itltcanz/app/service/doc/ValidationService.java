package dev.itltcanz.app.service.doc;

import jakarta.validation.ValidationException;

public interface ValidationService {

    /**
     * Валидирует объект любого типа
     *
     * @param message объект любого типа
     * @throws ValidationException в случае несоответствия правилам валидации
     */
    <O> void validate(O message) throws ValidationException;
}