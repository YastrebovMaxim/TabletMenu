package ru.myastrebov.services.exception;

/**
 * Created by myastrebov on 12/19/15.
 */
public class DishDoesNotExistException extends RuntimeException {
    public DishDoesNotExistException() {
    }

    public DishDoesNotExistException(String message) {
        super(message);
    }

    public DishDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
