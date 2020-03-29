package group10.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

/**
 * This class includes exception and handlers for API.
 */
public class ApiException {
    public static class UserNotFound extends RuntimeException {
        public UserNotFound(String message, Long id) {
            super(message + " " + id.toString());
        }
    }

    @ControllerAdvice
    public static class UserNotFoundHandler extends EntityNotFoundException {
        @ExceptionHandler(ApiException.UserNotFound.class)
        public ResponseEntity<Object> handleUserExists(ApiException.UserNotFound e) {
            Error error = new Error(HttpStatus.NOT_FOUND,e.getMessage());

            return new ResponseEntity<>(error, error.getStatus());
        }
    }


    public static class BadRequest extends EntityNotFoundException {
        public BadRequest(String message) {
            super(message);
        }
    }


    @ControllerAdvice
    public static class BadRequestHandler extends EntityNotFoundException
    {
        @ExceptionHandler(ApiException.BadRequest.class)
        public ResponseEntity<Object> badRequestHandler(ApiException.BadRequest e) {
            Error error = new Error(HttpStatus.BAD_REQUEST,e.getMessage());
            return new ResponseEntity<>(error, error.getStatus());
        }
    }


    public static class UserAlreadyExists extends EntityExistsException {
        public UserAlreadyExists(String message) {
            super(message);
        }
    }


    @ControllerAdvice
    public static class UserAlreadyExistsHandler extends EntityExistsException {
        @ExceptionHandler(ApiException.UserAlreadyExists.class)
        public ResponseEntity<Object> userExistsHandler(ApiException.UserAlreadyExists e) {
            Error error = new Error(HttpStatus.CONFLICT,e.getMessage());
            return new ResponseEntity<>(error, error.getStatus());
        }
    }


    public static class ScoreNotFound extends RuntimeException {
        public ScoreNotFound(String message, Long id) {
            super(message + " " + id.toString());
        }
    }

    @ControllerAdvice
    public static class ScoreNotFoundHandler extends EntityNotFoundException {
        @ExceptionHandler(ApiException.ScoreNotFound.class)
        public ResponseEntity<Object> handleUserExists(ApiException.ScoreNotFound e) {
            Error error = new Error(HttpStatus.NOT_FOUND,e.getMessage());

            return new ResponseEntity<>(error, error.getStatus());
        }
    }
}
