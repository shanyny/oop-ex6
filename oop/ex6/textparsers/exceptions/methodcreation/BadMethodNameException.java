package oop.ex6.textparsers.exceptions.methodcreation;

/**
 * This exception is thrown when creating a method with a bad name.
 * @author Shany Gindi and Roy Urbach
 */
public class BadMethodNameException extends MethodCreationException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to create a method with a bad name.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
