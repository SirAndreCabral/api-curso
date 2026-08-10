package org.example.apicursos.exception;

public class InvalidCourseNameException extends RuntimeException {
    public InvalidCourseNameException() {
        super("Nome do curso inválido.");
    }

    public InvalidCourseNameException(String message) {
        super(message);
    }
}
