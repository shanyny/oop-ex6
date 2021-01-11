package oop.ex6.textparsers.exceptions;

/**
 * This exception is thrown whenever trying to call a method with arguments that don't fit its parameters.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodCallParametersNotCompatibleException extends MethodCallException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to call a method with arguments that don't " +
            "fit its parameters.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
