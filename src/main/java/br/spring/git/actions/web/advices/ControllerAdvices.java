package br.spring.git.actions.web.advices;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerAdvices implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> violationErrorHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation ->
                errors.add(constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<String> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex, WebRequest request) {
        ex.printStackTrace();

        String errorMessage = String.format("Problemas na instrucao SQL: %s.", ex.getMessage());

        // Return a ResponseEntity with appropriate status and error message
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        ex.printStackTrace();

        // Create a meaningful error response
        String errorMessage = String.format("SQL, erros de integridade (\"Constraint Violation\"): %s.", ex.getMessage());


        // Return a ResponseEntity with appropriate status and error message
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex, WebRequest request) {
        // Log the exception details
        ex.printStackTrace();

        // Create a meaningful error response
        String errorMessage = String.format("Ocorreram erros no banco de dados: %s.", ex.getMessage());

        // Return a ResponseEntity with appropriate status and error message
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> violationErrorHandler(BindException ex) {
        return new ResponseEntity(ex.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}

