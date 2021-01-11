package oop.ex6.textparsers.exceptions.methodcreation;

/**
 * This exception is thrown when declaring a method in lower scope than allowed. That is - not global.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodNotInMainBlockException extends MethodCreationException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to declare a method in a lower hierarchy than allowed. " +
            "not Main block.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
