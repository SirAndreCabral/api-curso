package org.example.apicursos.exception;

public class InvalidCourseCategoryException extends RuntimeException {
    public InvalidCourseCategoryException() {
        super("Categoria do curso inválido.");
    }

    public InvalidCourseCategoryException(String message) {
        super(message);
    }
}
