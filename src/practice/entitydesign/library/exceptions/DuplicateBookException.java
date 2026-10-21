package practice.entitydesign.library.exceptions;

public class DuplicateBookException extends RuntimeException {

    public DuplicateBookException(String message) {
        super(message);
    }
}