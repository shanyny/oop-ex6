package oop.ex6.textparsers.exceptions;

/**
 * This exception is abstract and represents all method calls exceptions.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodAlreadyExistsException extends MethodCreationException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to create a method which already exists.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
