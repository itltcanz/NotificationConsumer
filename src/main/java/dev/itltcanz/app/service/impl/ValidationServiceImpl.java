package dev.itltcanz.app.service.impl;

import dev.itltcanz.app.service.doc.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

  private final Validator validator;

  public <O> void validate(O object) throws ConstraintViolationException {
    Set<ConstraintViolation<O>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(
          "Ошибка валидации %s".formatted(object.getClass().getSimpleName()),
          violations
      );
    }
  }
}