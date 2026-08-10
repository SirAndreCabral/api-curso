package org.example.apicursos.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException() {
        super("Curso não encontrado.");
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
