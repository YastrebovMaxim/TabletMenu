package ru.myastrebov.services.exception;

/**
 * Created by myastrebov on 12/19/15.
 */
public class TagDoesNotExistException extends RuntimeException {
    public TagDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagDoesNotExistException(String message) {
        super(message);
    }

    public TagDoesNotExistException() {
    }
}
